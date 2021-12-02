package org.firstinspires.ftc.teamcode13106;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Autonomous Red Right", group="Pushbot")
public class AutonomousRedRight extends LinearOpMode {
    Commands commands = new Commands();
    ObjectDetection objectDetection = new ObjectDetection();

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
            int floor = objectDetection.getBarcodeFloor(objectDetection);
            float duckLocation = objectDetection.duckLocation;
            telemetry.addData("floor ", floor);
            telemetry.addData("duck location", duckLocation);
            telemetry.update();

            //close claw
            commands.closeClaw();
            //raise intake
            commands.intakeUp();
            //get off the wall
            commands.moveForward(commands.AUTON_DRIVE_SPEED,5,5);
            //face the hub
            commands.rotateCounterClockwiseGyro(commands.AUTON_ROTATE_SPEED,30,8);
            //raise arm off floor
            commands.elevatorMoveToHeight(commands.AUTON_ELEVATOR_SPEED,200,3);
            //lower intake
            commands.intakeDown();
            //raise arm to predetermined floor
            commands.elevatorMoveToFloor(floor,5);
            //kiss the hub
            commands.moveForward(commands.AUTON_DRIVE_SPEED,20,8);

            //drop preload box
            commands.openClaw();

            // clear the hub
            commands.moveBackward(commands.AUTON_DRIVE_SPEED,5,3);
            //turn to face the warehouse
            commands.rotateClockwiseGyro(commands.AUTON_ROTATE_SPEED,-90,10);
            //lift the intake
            commands.intakeUp();
            // lower the elevator
            commands.elevatorMoveToFloor(1,8);
            //go to the warehouse
            commands.moveForward(commands.AUTON_DRIVE_SPEED,45,11);

            sleep(30000);
        }
    }
}