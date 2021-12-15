package org.firstinspires.ftc.teamcode13106;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Autonomous Blue Left", group="Pushbot")
public class AutonomousBlueLeft extends LinearOpMode {
    Commands commands = new Commands();
    ObjectDetection objectDetection = new ObjectDetection();
    int defaultState = 1;
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
            int floor = objectDetection.getBarcodeFloor(objectDetection,defaultState);
//            float duckLocation = objectDetection.duckLocation;
            telemetry.addData("floor ", floor);
//            telemetry.addData("duck location", duckLocation);
            telemetry.update();

            //raise intake
            commands.intakeUp();
            sleep(250);
            //close claw
            commands.closeClaw();
            sleep(250);
            //raise arm off floor
            commands.elevatorMoveToHeight(commands.AUTON_ELEVATOR_SPEED,1000,3);
            // move off the wall 5
            commands.moveForward(commands.AUTON_DRIVE_SPEED,5,5);
            //face the hub
            commands.rotateClockwiseGyro(commands.AUTON_ROTATE_SPEED,-30,8);
            //lower intake
            commands.intakeDown();
            //raise arm to predetermined floor
            commands.elevatorMoveToFloor(floor,5);
            //kiss the hub
            commands.moveForward(commands.AUTON_DRIVE_SPEED,20,8);

            //drop the box
            commands.openClaw();

            // clear the hub
            commands.moveBackward(commands.AUTON_DRIVE_SPEED,3,3);

            //turn to face the warehouse
            commands.rotateCounterClockwiseGyro(commands.AUTON_ROTATE_SPEED,90,10);
            //lift the intake
            commands.intakeUp();
            // lower the elevator
            commands.elevatorMoveToFloor(1,8);
            //go to the warehouse
            commands.moveForward(commands.AUTON_DRIVE_SPEED,52,11);

            sleep(30000);
        }
    }
}