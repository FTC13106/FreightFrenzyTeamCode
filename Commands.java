package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Commands extends HardwareMappingTank {

 static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
 static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
 static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
 static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
         (WHEEL_DIAMETER_INCHES * 3.1415);
 static final double     DRIVE_SPEED             = 0.6;
 static final double     TURN_SPEED              = 0.5;

private ElapsedTime runtime = new ElapsedTime();

 public void moveForward (double power,double distance, double timeout){
    this.encoderDrive(power, distance, distance, timeout);
   }

    public void moveBackward(double power, double distance, double timeout){
     this.encoderDrive(power, -distance, -distance, timeout);
    }

     public void rotateCounterClockwise(double power, double distance, double timeout){
         this.encoderDrive(power, -distance, distance, timeout);

     }

    public void rotateClockwise(double power, double distance, double timeout){
        this.encoderDrive(power, distance, -distance, timeout);
    }

   public void duckCarouselClockwise(double power){
     carouselMotor.setPower(power);
 }

   public void duckCarouselCounterClockwise(double power){
        carouselMotor.setPower(-power);
   }

private void encoderDrive(double speed,
                          double leftInches, double rightInches,
                          double timeoutS) {
   int newLeftTarget;
   int newRightTarget;

   // Determine new target position, and pass to motor controller
   newLeftTarget = leftMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
   newRightTarget = rightMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
   leftMotor.setTargetPosition(newLeftTarget);
   rightMotor.setTargetPosition(newRightTarget);

   // Turn On RUN_TO_POSITION
   leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
   rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

   // reset the timeout time and start motion.
   runtime.reset();
   leftMotor.setPower(Math.abs(speed));
   rightMotor.setPower(Math.abs(speed));

   // keep looping while we are still active, and there is time left, and both motors are running.
   // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
   // its target position, the motion will stop.  This is "safer" in the event that the robot will
   // always end the motion as soon as possible.
   // However, if you require that BOTH motors have finished their moves before the robot continues
   // onto the next step, use (isBusy() || isBusy()) in the loop test.
   while (
           (runtime.seconds() < timeoutS) &&
           (leftMotor.isBusy() && rightMotor.isBusy())) {



   }

   // Stop all motion;
   leftMotor.setPower(0);
   rightMotor.setPower(0);

   // Turn off RUN_TO_POSITION
   leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
   rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

   //  sleep(250);   // optional pause after each move
  }
 }


//}
