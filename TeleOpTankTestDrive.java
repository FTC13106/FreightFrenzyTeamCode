package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="TeleOp: Tank", group="Tank")
//@Disabled
public class TeleOpTankTestDrive extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareMapping robot           = new HardwareMapping();   // Use our hardware apping

    @Override
    public void runOpMode() {
        double driveRightSpeed;
        double driveLeftSpeed;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            driveLeftSpeed = gamepad1.left_stick_y;
            driveRightSpeed = gamepad1.right_stick_y;


            // Output the safe vales to the motor drives.
            robot.leftFront.setPower(driveLeftSpeed);
            robot.leftRear.setPower(driveLeftSpeed);
            robot.rightFront.setPower(driveRightSpeed);
            robot.rightRear.setPower(driveRightSpeed);

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}
