package org.firstinspires.ftc.teamcode13106;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Autonomous Red Left", group="Pushbot")
public class AutonomousRedLeft extends LinearOpMode {
    Commands commands = new Commands();
    ObjectDetection objectDetection = new ObjectDetection();
    int defaultState = 3;
    @Override
    public void runOpMode() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        commands.init(hardwareMap);
        objectDetection.init(hardwareMap);
        commands.resetElevatorPosition();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            int floor = objectDetection.getBarcodeFloor(objectDetection, defaultState);
//            float duckLocation = objectDetection.duckLocation;
            telemetry.addData("floor ", floor);
            telemetry.update();
            //go to shipping hub, drop off preload, drive to carosel, park in *warehouse?
            //raise intake
            commands.intakeUp();
            sleep(250);
            //close claw
            commands.closeClaw();
            sleep(250);
            //raise arm off floor
            commands.elevatorMoveToHeight(commands.AUTON_ELEVATOR_SPEED,1000,3);
            //get off the wall
            commands.moveForward(commands.AUTON_DRIVE_SPEED, 7, 8);
            //face the shipping hub
            commands.rotateClockwiseGyro(commands.AUTON_ROTATE_SPEED, -30,7);
            //lower intake
            commands.intakeDown();
            //raise arm to predetermined floor
            commands.elevatorMoveToFloor(floor,5);
            //go to shipping hub
            commands.moveForward(commands.AUTON_DRIVE_SPEED, 20, 8);
            //drop preload box
            commands.openClaw();
            //move away from the shipping hub
            commands.moveBackward(commands.AUTON_DRIVE_SPEED,5, 8);
            //turn to face the carousel
            commands.rotateClockwiseGyro(commands.AUTON_ROTATE_SPEED, -80,7);
            //drive towards carousel
            commands.moveBackward(commands.AUTON_DRIVE_SPEED,32, 8);
            //moving the robot out of the way
            commands.rotateCounterClockwiseGyro(commands.AUTON_ROTATE_SPEED, 0,7);
            //lift the intake
            commands.intakeUp();
            // lower the elevator
            commands.elevatorMoveToFloor(0,8);
            //back up to carousel
            commands.moveBackward(commands.AUTON_DRIVE_SPEED,6, 8);
            //turn on the duck spinner
            commands.duckCarouselCounterClockwise(.75);
            //wait for duck to fall off
            sleep(6000); // 4 seconds?
            // turn off Carousel motor
            commands.duckCarouselClockwise(0);

            //move the robot off the carousel
            commands.moveForward(commands.AUTON_DRIVE_SPEED, 18, 8);
            //line up to storage unit
//            commands.rotateCounterClockwiseGyro(commands.AUTON_ROTATE_SPEED, 20,7);
//            //move into the storage unit
//            commands.moveForward(commands.AUTON_DRIVE_SPEED, 3, 8);
//
//            commands.rotateClockwiseGyro(commands.AUTON_ROTATE_SPEED, 0,7);
            //move into the storage unit

            //sleep
            sleep(30000);
        }
    }
}