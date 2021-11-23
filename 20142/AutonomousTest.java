package org.firstinspires.ftc.teamcode20142;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Autonomous Test", group="Pushbot")
@Disabled
public class AutonomousTest extends LinearOpMode {

    /* Declare OpMode members. */
    Commands commands = new Commands();

    @Override
    public void runOpMode() {

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        commands.init(hardwareMap);

        ObjectDetection obd = new ObjectDetection();
        telemetry.addLine("Starting Barcode Detection!");
        telemetry.update();
        int i = 0;
        int obdState = obd.getState();
        while(obdState == -1){ // loop until 10 times or until the object detection returns back a valid result
            if(i++ > 10){
                break;
            }
        }

        if(obdState == -1){
            telemetry.addLine("Could Not Detect Code");
        }else if(obdState == 1){
            telemetry.addLine("Bar Code A");
        }else if(obdState == 2){
            telemetry.addLine("Bar Code B");
        }else if(obdState == 3){
            telemetry.addLine("Bar Code C");
        }else{
            telemetry.addLine("Bar Code ERROR");
        }

        telemetry.addLine("Barcode Detection Complete!");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            commands.moveForward(0.5,10, 15);
            commands.moveBackward(0.65, 15, 20);

            commands.rotateCounterClockwise(0.5, 10,20 );

            commands.duckCarouselCounterClockwise(0.5);
            commands.duckCarouselClockwise(0.5);
            sleep(30000);
        }
    }




}
