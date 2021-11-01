package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="TeleOp: Tank", group="Tank")
//@Disabled
public class TeleOpTankTestDrive extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareMappingTank robot           = new HardwareMappingTank();   // Use our hardware apping
    FieldNavigation fieldNav = new FieldNavigation();

    @Override
    public void runOpMode() {
        double driveRightSpeed;
        double driveLeftSpeed;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        fieldNav.startTracking();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            driveLeftSpeed = -gamepad1.left_stick_y;
            driveRightSpeed = -gamepad1.right_stick_y;


            // Output the safe vales to the motor drives.
            robot.rightRearMotor.setPower(driveRightSpeed);
            robot.rightFrontMotor.setPower(driveRightSpeed);
            robot.leftRearMotor.setPower(driveLeftSpeed);
            robot.leftFrontMotor.setPower(driveLeftSpeed);
            if(gamepad1.a)
            robot.carouselMotor.setPower(0.25);
            else
            robot.carouselMotor.setPower(0);

            float[] navData = fieldNav.getLocation();
            if(navData != null){
                telemetry.addData("Pos (inches)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                        navData[0],navData[1],navData[2]);
                telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", navData[3],navData[4],navData[5]);
                telemetry.update();
            }else{
                telemetry.addData("Visible Target", "none");
                telemetry.update();
            }
            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }

        fieldNav.stopTracking();
    }
}
