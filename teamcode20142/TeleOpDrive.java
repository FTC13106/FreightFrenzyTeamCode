package org.firstinspires.ftc.teamcode20142;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp(name="TeleOpDrive", group="TeleOp")
//@Disabled
public class TeleOpDrive extends LinearOpMode {

    static final double DRIVE_SPEED = 0.6;

    /* Declare OpMode members. */
    HardwareMapping robot = new HardwareMapping();   // Use our hardware mapping
    Commands commands = new Commands();
    int elevatorCurrentPosition;
    @Override
    public void runOpMode() {
        double driveRightSpeed;
        double driveLeftSpeed;
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        commands.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            elevatorCurrentPosition = robot.elevatorMotor.getCurrentPosition();
            //Driver controller ---------------------
            if (gamepad1.x){
                // Rotate the carousel for blue side
                commands.duckCarouselClockwise(50);
            }else if (gamepad1.b){
                // Rotate the carousel for red side
                commands.duckCarouselCounterClockwise(50);
            }else{
                commands.duckCarouselCounterClockwise(0);
            }
            
            // Arcade drive controls
            driveLeftSpeed = -(gamepad1.left_stick_y - gamepad1.left_stick_x);
            driveRightSpeed = -(gamepad1.left_stick_y + gamepad1.left_stick_x);

            // Output the safe vales to the motor drives.
            robot.rightRearMotor.setPower(driveRightSpeed * DRIVE_SPEED);
            robot.rightFrontMotor.setPower(driveRightSpeed * DRIVE_SPEED);
            robot.leftRearMotor.setPower(driveLeftSpeed * DRIVE_SPEED);
            robot.leftFrontMotor.setPower(driveLeftSpeed * DRIVE_SPEED);
           
            //Co-Driver controller -------------------
            if (gamepad2 != null){
                if (gamepad2.a){
                    commands.openClaw();
                }
                if (gamepad2.b){
                    commands.closeClaw();
                }
                if (gamepad2.y){
                    commands.openClawMidway();
                }
                if (gamepad2.left_bumper && gamepad2.right_bumper){
                    commands.resetElevatorPosition();
                    telemetry.addData("elevator",robot.elevatorMotor.getCurrentPosition());
                    telemetry.update();
                }

                if (gamepad2.start){
                    commands.openClawMidway();
                    commands.elevatorHome(10);
                    telemetry.addData("elevator homed",robot.elevatorMotor.getCurrentPosition());
                    telemetry.update();
                    commands.openClaw();
                }

                if (gamepad2.left_stick_y >= .1 || gamepad2.left_stick_y <= -.1) {
                    if (gamepad2.left_stick_y < -.1 &&
                            (robot.elevatorMotor.getCurrentPosition() < commands.ELEVATOR_MAX_HEIGHT || gamepad2.right_bumper)
                    ) {
                        commands.elevatorUp();
                    } else if (gamepad2.left_stick_y > .1 &&
                            (robot.elevatorMotor.getCurrentPosition() > commands.ELEVATOR_MIN_HEIGHT || gamepad2.right_bumper)
                    ) {
                            commands.elevatorDown();
                    }
                    else{
                        commands.elevatorStop();
                    }
                    telemetry.addData("elevator",robot.elevatorMotor.getCurrentPosition());
                    telemetry.update();
                }
                else {
                    commands.elevatorStop();
                }
            }

            // Pace this loop so jaw action is reasonable speed.
            idle();
            sleep(40);
        }
    }
}
