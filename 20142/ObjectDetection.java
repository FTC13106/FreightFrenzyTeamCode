package org.firstinspires.ftc.teamcode20142;
import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;

import java.util.List;

public class ObjectDetection extends HardwareMapping {
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
    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_DM.tflite";
    private static final String[] LABELS = {
//            "Ball",
//            "Cube",
            "Duck",
            "Marker"
    };
    public float duckLocation =0;


    private static final String VUFORIA_KEY = "AQXDFuj/////AAABmVKd60bbfEvEoe2tGEzNEiB312vgHtihnIKjnUII+7eEo3+nl+Z6JvAbKxlIbWrnIkww+1rCMd7fvsnS5kAcmwlnukJziOiI7AaygpLSIpdTUbMsO3OuvSSx98ZkNfKhsFKU9B9ys6DQSK8PS6I+33IUM5F6Q9bav5OlkbKahByQl9xQlbH/YMJ2Sm0XGk83HnvX630lYQV42rY/91jA8l3uwR/DV4Cj3BTdu7RBJIGNFTS+9WUMkGBI8Sjxd00DIb8gzbTYgGE9NwNA1tOfQsLZwhunC4CobZQRDFGrKOZzjOrMDku4ww2qQQe44x4JiDXmjESw2vFCyIbUYsc+Q8Tc6HusKjDndUryJBWJGX+J";

    private VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;

    public ObjectDetection(){
    }

    public void init(HardwareMap ahwMap) {
        super.init(ahwMap);
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

            // removed zoom because we need maximum field of view
            //tfod.setZoom(2.5, 16.0/9.0);
        }
    }

    /**
     * Returns the duck position if it's found on during detection
     * @return the X position of the duck {-1 = Not found, value between 0-600}
     */
    public float getDuckPosition(){
        float duck = -1.0f;
        if (tfod != null) {
            // Using getRecognitions because it returns a value every time it's called
            List<Recognition> updatedRecognitions = tfod.getRecognitions();

            if (updatedRecognitions != null) {
                // step through the list of recognitions and get Duck position
                for (Recognition recognition : updatedRecognitions) {
                     if(recognition.getLabel().equals("Duck")){
                         // find the center of the duck recognition box
                         duck = (recognition.getLeft() + recognition.getRight()) / 2;
                    }
                }
            }
        }
        return duck;
    }


    public int getBarcodeFloor(ObjectDetection objectDetection){
        int state = objectDetection.getDuckState();
        duckLocation = objectDetection.getDuckPosition();
        for (int i = 0; i<= 4; i++){
            state = objectDetection.getDuckState();
            duckLocation = objectDetection.getDuckPosition();
            try {
                sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return state;
    }

    /**
     * Returns an assessment of the object detection based on duck position
     * @return the determined state {1 = level 1 (Default), 2= level 2, 3 = level 3}
     */
    public int getDuckState(){
        float duck = getDuckPosition();
        // Split the viewable screen (~600 wide) into 3 zones and if the duck is found in that zone return that zone number
        if (duck < 200)
            return 1;
        else if(duck < 400)
            return 2;
        else
            return 3;
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
        // reducing confidence to 50%
        tfodParameters.minResultConfidence = 0.5f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }
}
