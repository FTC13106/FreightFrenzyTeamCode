package org.firstinspires.ftc.teamcode;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleOp Mech", group="Mech")
@Disabled
public class TeleOpMechTestDrive extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareMapping robot           = new HardwareMapping();   // Use our hardware apping

    @Override
    public void runOpMode() {
        double threshold = 0.3; // deadzone of the gampad analog sticks
        double rightStickX; // driver gampad right stick X
        double leftStickX; // driver gampad left stick X
        double leftStickY; // driver gampad left stick Y

        // variables to calculate the wheel speed
        double rightFrontSpeed = 0.0;
        double rightRearSpeed = 0.0;
        double leftFrontSpeed = 0.0;
        double leftRearSpeed = 0.0;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            leftFrontSpeed = 0;
            leftRearSpeed = 0;
            rightRearSpeed = 0;
            rightFrontSpeed = 0;

            // capture the gamepad right and left analog stick values
            rightStickX = gamepad1.right_stick_x;
            leftStickX = gamepad1.left_stick_y;
            leftStickY = gamepad1.left_stick_x;

            // Output the safe vales to the motor drives.
            if (abs(gamepad1.left_stick_x) > threshold || abs(gamepad1.left_stick_y) > threshold) {
                rightFrontSpeed = -leftStickY - leftStickX;
                rightRearSpeed = leftStickY - leftStickX;
                leftFrontSpeed = leftStickY - leftStickX;
                leftRearSpeed = -leftStickY - leftStickX;
            }

            if (abs(gamepad1.right_stick_x) > threshold) {
                /*
                    rotate using the Mecanum wheels - used for doing circular turns
                */
                rightFrontSpeed = -rightStickX;
                rightRearSpeed = -rightStickX;
                leftFrontSpeed = rightStickX;
                leftRearSpeed = rightStickX;
            }


            // Set power to the motors
            robot.rightFront.setPower(rightFrontSpeed);
            robot.leftFront.setPower(leftFrontSpeed);
            robot.rightRear.setPower(rightRearSpeed);
            robot.leftRear.setPower(leftRearSpeed);

            telemetry.addData("leftFront Power ", leftFrontSpeed);
            telemetry.addData("leftRear Power ", leftRearSpeed);
            telemetry.addData("rightFront Power ", rightFrontSpeed);
            telemetry.addData("rightRear Power ", rightRearSpeed);

            telemetry.addData("gamepad1.right_stick_x", gamepad1.right_stick_x);
            telemetry.addData("gamepad1.left_stick_x", gamepad1.left_stick_x);
            telemetry.addData("gamepad1.left_stick_y", gamepad1.left_stick_y);
            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}
