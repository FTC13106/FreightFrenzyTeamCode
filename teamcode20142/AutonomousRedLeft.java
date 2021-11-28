package org.firstinspires.ftc.teamcode20142;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Autonomous Red Left", group="Pushbot")
public class AutonomousRedLeft extends LinearOpMode {
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
            //read barcode, go to shipping hub, drop off preload, drive to carousel, park in storage unit

            // get barcode floor
            telemetry.addData("Reading barcode", "...");
            telemetry.update();
            int floor = objectDetection.getBarcodeFloor(objectDetection);
            float duckLocation = objectDetection.duckLocation;
            telemetry.addData("floor ", floor);
            telemetry.addData("duck location", duckLocation);
            telemetry.update();

            //close claw
            commands.closeClaw();
            //get off the wall
            commands.moveForward(0.5, 7, 8);
            // move the elevator to correct position. floor1=3in,floor2=8.5in,floor3=14.75in,floor4=20.25
            commands.elevatorMoveToFloor(floor,5);
            //face the shipping hub
            commands.rotateClockwiseGyro(0.2, -30,7);
            //go to shipping hub
            commands.moveForward(0.5, 20, 8);
            //drop preload box
            commands.openClaw();
            //move away from the shipping hub
            commands.moveBackward(0.5,5, 8);
            //turn to face the carousel
            commands.rotateClockwiseGyro(0.2, -70,7);
            //drive towards carousel
            commands.moveBackward(0.5,32, 8);
            //moving the robot out of the way
            commands.rotateCounterClockwiseGyro(0.2, 0,7);
            //back up to carousel
            commands.moveBackward(0.5,12, 8);

            //turn on the duck spinner
            commands.duckCarouselClockwise(0.5);
            //wait for duck to fall off
            sleep(6000); // 4 seconds?
            // turn off Carousel motor
            commands.duckCarouselClockwise(0);

            //move the robot off the carousel
            commands.moveForward(0.5, 7, 8);
            //line up to storage unit
            commands.rotateCounterClockwiseGyro(0.2, 0,7);
            //move into the storage unit
            commands.moveForward(0.5, 21, 8);
            //sleep
            sleep(30000);
        }
    }
}