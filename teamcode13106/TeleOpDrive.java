package org.firstinspires.ftc.teamcode13106;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp(name="TeleOp", group="TeleOp")
//@Disabled
public class TeleOpDrive extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareMapping robot           = new HardwareMapping();   // Use our hardware mapping
    Commands commands = new Commands();  
    //FieldNavigation fieldNav = new FieldNavigation();

    @Override
    public void runOpMode() {
        double driveRightSpeed;
        double driveLeftSpeed;
        Orientation angles;
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        commands.init(hardwareMap);
        robot.elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //fieldNav.startTracking();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //Driver controller ---------------------

            // Rotate the carousel for blue side
            if (gamepad1.x){
                commands.duckCarouselClockwise(0.75);
            }else if(gamepad1.b){
                commands.duckCarouselCounterClockwise(0.75);
            }else{
                commands.duckCarouselCounterClockwise(0);
            }

            // arcade drive controls
            driveLeftSpeed = -(gamepad1.left_stick_y - gamepad1.left_stick_x);
            driveRightSpeed = -(gamepad1.left_stick_y + gamepad1.left_stick_x);

            // Output the safe vales to the motor drives.
            robot.rightRearMotor.setPower(driveRightSpeed);
            robot.rightFrontMotor.setPower(driveRightSpeed);
            robot.leftRearMotor.setPower(driveLeftSpeed);
            robot.leftFrontMotor.setPower(driveLeftSpeed);

            angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("leftstick - y ", gamepad1.left_stick_y);
            telemetry.addData("leftstick - x", gamepad1.left_stick_x);
            telemetry.addData("z" , angles.firstAngle);
            telemetry.addData("x" , angles.secondAngle);
            telemetry.addData("y" , angles.thirdAngle);
            telemetry.addData("arm", robot.elevatorMotor.getCurrentPosition());
            telemetry.update();

            // tank drive controls
            //driveLeftSpeed = -gamepad1.left_stick_y;
            //driveRightSpeed = -gamepad1.right_stick_y;


            //Co-Driver controller -------------------
            if (gamepad2 != null){
                if (gamepad2.a){
                    //move to floor 0
                }
                if (gamepad2.b){
                    //move to floor 1
                }
                if (gamepad2.x){
                    //move to floor 2
                }
                if (gamepad2.y){
                    //move to floor 3
                }

                if (gamepad2.right_stick_y >= .1 || gamepad2.right_stick_y <= -.1) {
                    if (gamepad2.right_stick_y < -.1) {
                        commands.elevatorUp();
                    } else if (gamepad2.right_stick_y > .1) {
                        commands.elevatorDown();
                    }
                } else {
                    commands.elevatorStop();
                }

                if (gamepad2.dpad_up) {
                    commands.openClaw();
                }else if (gamepad2.dpad_down) {
                    commands.closeClaw();
                }

                if (gamepad2.right_bumper ){
                    //intake
                    commands.intakeUp();
                }else if(gamepad2.left_bumper){
                    commands.intakeDown();
                }

                /*
                //intake In
                if (gamepad2.dpad_down ){
                    commands.intakeOn();
                }else{
                    commands.stopIntakeServo();
                }

                //intake Out
                if (gamepad2.dpad_up ){

                    commands.releaseIntakeServo();
                }else{
                    commands.stopIntakeServo();
                }
                */
            }
             

            // TODO restore fieldNav if we decide to use it
            /*
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
            */
            
            
            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }

        //fieldNav.stopTracking();
    }
}
