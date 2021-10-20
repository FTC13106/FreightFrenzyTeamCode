package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Commands extends HardwareMappingTank {

 static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
 static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
 static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
 static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
         (WHEEL_DIAMETER_INCHES * 3.1415);
 static final double     DRIVE_SPEED             = 0.6;
 static final double     TURN_SPEED              = 0.5;


 public void moveForward (double power){
    leftMotor.setPower(power);
    rightMotor.setPower(power);
   }

    public void moveBackward(){

    }
     public void rotateCounterClockwise(){

     }
    public void moveLeft(){

    }
    public void rotateClockwise(){

    }
    public void moveRight(){

    }
/*
 public void encoderDrive(double speed,
                          double leftInches, double rightInches,
                          double timeoutS) {
   int newLeftTarget;
   int newRightTarget;

   // Determine new target position, and pass to motor controller
   newLeftTarget = robot.leftDrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
   newRightTarget = robot.rightDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
   robot.leftDrive.setTargetPosition(newLeftTarget);
   robot.rightDrive.setTargetPosition(newRightTarget);

   // Turn On RUN_TO_POSITION
   robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
   robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

   // reset the timeout time and start motion.
   runtime.reset();
   robot.leftDrive.setPower(Math.abs(speed));
   robot.rightDrive.setPower(Math.abs(speed));

   // keep looping while we are still active, and there is time left, and both motors are running.
   // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
   // its target position, the motion will stop.  This is "safer" in the event that the robot will
   // always end the motion as soon as possible.
   // However, if you require that BOTH motors have finished their moves before the robot continues
   // onto the next step, use (isBusy() || isBusy()) in the loop test.
   while (opModeIsActive() &&
           (runtime.seconds() < timeoutS) &&
           (robot.leftDrive.isBusy() && robot.rightDrive.isBusy())) {

    // Display it for the driver.
    telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
    telemetry.addData("Path2",  "Running at %7d :%7d",
            robot.leftDrive.getCurrentPosition(),
            robot.rightDrive.getCurrentPosition());
    telemetry.update();
   }

   // Stop all motion;
   robot.leftDrive.setPower(0);
   robot.rightDrive.setPower(0);

   // Turn off RUN_TO_POSITION
   robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
   robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

   //  sleep(250);   // optional pause after each move
  }
 }
*/

}
