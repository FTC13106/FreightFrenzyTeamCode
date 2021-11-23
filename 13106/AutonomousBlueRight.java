package org.firstinspires.ftc.teamcode13106;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Autonomous Blue Right", group="Pushbot")
public class AutonomousBlueRight extends LinearOpMode {
    Commands commands = new Commands();
    ObjectDetection objectDetection = new ObjectDetection();

    @Override
    public void runOpMode() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        commands.init(hardwareMap);
        objectDetection.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            int floor = objectDetection.getBarcodeFloor(objectDetection);
            float duckLocation = objectDetection.duckLocation;
            telemetry.addData("floor ", floor);
            telemetry.addData("duck location", duckLocation);
            telemetry.update();
            //go to shipping hub, drop off preload, drive to carosel, park in *warehouse?
            //get off the wall
            commands.moveForward(0.5, 7, 8);
            //face the shipping hub
            commands.rotateCounterClockwiseGyro(.2,30,10);
            //go to shipping hub
            commands.moveForward(0.5, 20, 8);

            // move the elevator to correct position. floor1=3in,floor2=8.5in,floor3=14.75in,floor4=20.25
            commands.elevatorMoveToFloor(floor,5);

            //drop preload box
            commands.openClaw();

            //move away from the shipping hub
            commands.moveBackward(0.5,5, 8);
            //turn to face the carousel
            commands.rotateCounterClockwiseGyro(.2,70,10);
            //drive towards carousel
            commands.moveBackward(0.5,35, 8);
            //moving the robot out of the way
            commands.rotateCounterClockwiseGyro(.2,90,10);
            //back up to carousel
            commands.moveBackward(0.5,9, 8);

            //turn on the duck spinner
            commands.duckCarouselClockwise(0.5);
            //wait for duck to fall off
            sleep(6000); // 6 seconds?
            // turn off Carousel motor
            commands.duckCarouselClockwise(0);

            //move the robot off the carousel
            commands.moveForward(0.5, 7, 8);
            //line up to storage unit
            commands.rotateClockwiseGyro(0.2, 0,7);
            //move into the storage unit
            commands.moveForward(0.5, 16, 8);
            //sleep
            sleep(30000);
        }
    }
}
