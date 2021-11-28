package org.firstinspires.ftc.teamcode20142;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Autonomous Blue Left", group="Pushbot")
public class AutonomousBlueLeft extends LinearOpMode {
    Commands commands = new Commands();
    ObjectDetection objectDetection = new ObjectDetection();

    @Override
    public void runOpMode() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        commands.init(hardwareMap);
        objectDetection.init(hardwareMap);
        telemetry.addData("webcam", "ready");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Read barcode, go to shipping hub, drop off preload, park in warehouse

            // get barcode floor
            telemetry.addData("Reading barcode", "...");
            telemetry.update();
            int floor = objectDetection.getBarcodeFloor(objectDetection);
            float duckLocation = objectDetection.duckLocation;
            telemetry.addData("floor ", floor);
            telemetry.addData("duck location", duckLocation);
            telemetry.update();

            // Come out of init, close claw
            commands.closeClaw();
            //get off the wall
            commands.moveForward(0.5, 7, 8);
            // move the elevator to correct position. floor1=3in,floor2=8.5in,floor3=14.75in,floor4=20.25
            commands.elevatorMoveToFloor(floor,5);
            //face the hub
            commands.rotateClockwiseGyro(0.2,-30,8);
            //kiss the hub
            commands.moveForward(0.5,24,8);
            //drop the box
            commands.openClaw();
            // clear the hub
            commands.moveBackward(0.5,5,3);
            //turn to face the warehouse
            commands.rotateCounterClockwiseGyro(0.2,90,10);
            //go to the warehouse
            commands.moveForward(0.5,45,11);

            sleep(30000);
        }
    }
}