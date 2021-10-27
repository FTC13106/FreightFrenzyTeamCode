package org.firstinspires.ftc.teamcode;


import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public class ObjectDetection extends HardwareMappingTank {
    /* Note: This sample uses the all-objects Tensor Flow model (FreightFrenzy_BCDM.tflite), which contains
     * the following 4 detectable objects
     *  0: Ball,
     *  1: Cube,
     *  2: Duck,
     *  3: Marker (duck location tape marker)
     *
     *  Two additional model assets are available which only contain a subset of the objects:
     *  FreightFrenzy_BC.tflite  0: Ball,  1: Cube
     *  FreightFrenzy_DM.tflite  0: Duck,  1: Marker
     *  May only want to use the FreightFrenzy_DM.tflite to reduce the complexity of the scan
     */
    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    private static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };

    private static final String VUFORIA_KEY = "AQXDFuj/////AAABmVKd60bbfEvEoe2tGEzNEiB312vgHtihnIKjnUII+7eEo3+nl+Z6JvAbKxlIbWrnIkww+1rCMd7fvsnS5kAcmwlnukJziOiI7AaygpLSIpdTUbMsO3OuvSSx98ZkNfKhsFKU9B9ys6DQSK8PS6I+33IUM5F6Q9bav5OlkbKahByQl9xQlbH/YMJ2Sm0XGk83HnvX630lYQV42rY/91jA8l3uwR/DV4Cj3BTdu7RBJIGNFTS+9WUMkGBI8Sjxd00DIb8gzbTYgGE9NwNA1tOfQsLZwhunC4CobZQRDFGrKOZzjOrMDku4ww2qQQe44x4JiDXmjESw2vFCyIbUYsc+Q8Tc6HusKjDndUryJBWJGX+J";

    private VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;

    public ObjectDetection(){
        init();
    }

    private void init() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();
        initTfod();

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            tfod.setZoom(2.5, 16.0/9.0);
        }

    }

    /**
     * Returns an assessment of the object detection
     * @return int state of object detection {-1 = Invalid, 0 = Error, 1 = Low, 2 = Mid, 3 = High}
     */

    public int getState(){
        boolean foundTwoMarkersAndADuck = false;
        float firstMarker = 0.0f;
        float secondMarker = 0.0f;
        float duck = 0.0f;
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                // step through the list of recognitions and display boundary info.
                if(updatedRecognitions.size() < 3){
                    // if we don't see three objects 
                    // return invalid so the loop can try again
                    return -1;
                }
                for (Recognition recognition : updatedRecognitions) {
                    // hope to find at least 2 markers and 1 duck
                    // then parse through the markers and determine the distance between markers
                    // and the distance between marker and duck
                    // this will determine low level / mid level / high level
                    if(recognition.getLabel().equals(LABELS[3])){
                        if (firstMarker == 0.0){
                            firstMarker = recognition.getLeft();
                        }else{
                            secondMarker = recognition.getLeft();
                        }
                    }else if(recognition.getLabel().equals(LABELS[2])){
                        duck = recognition.getLeft();
                    }

                    if(firstMarker != 0.0 && secondMarker != 0.0 && duck != 0.0){
                        //exit
                        foundTwoMarkersAndADuck = true;
                        break;
                    }
                }

                if(foundTwoMarkersAndADuck){
                    //lets order the first marker to be the first in the FOV
                    if(firstMarker > secondMarker){
                        float tmp = firstMarker;
                        firstMarker = secondMarker;
                        secondMarker = tmp;
                    }
                    // understanding the possible combinations
                    // _D_M_M_
                    // _M_D_M_
                    // _M_M_D_
                    if(duck < firstMarker && duck < secondMarker){
                        return 1; // return the duck is on the first marker
                    }else if(firstMarker < duck && duck < secondMarker){
                        return 2; // return the duck is on the second marker
                    }else if(firstMarker < secondMarker && secondMarker < duck){
                        return 3; // return the duck is on the second marker
                    }else{
                        // don't know how we could get here unless everything was equal
                        return 0; // return an invalid state to try again
                    }
                }else {
                    return -1;
                }
            }
        }
        return -1;
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = webcamName;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hwMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hwMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }
}
