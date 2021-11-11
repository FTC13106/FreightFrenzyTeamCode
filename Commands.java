package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Commands extends HardwareMappingTank {

    static final double COUNTS_PER_MOTOR_REV = 1440;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;

    static final double ELEVATOR_WHEEL_DIAMETER_INCHES = 0.5;
    static final double ELEVATOR_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    static final double ELEVATOR_COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * ELEVATOR_GEAR_REDUCTION) /
            (ELEVATOR_WHEEL_DIAMETER_INCHES * 3.1415);

    static final double ELEVATOR_ANGLE = 1.0; // angle of Elevator as sin(degrees)


    private ElapsedTime runtime = new ElapsedTime();

    public void moveForward(double power, double distance, double timeout) {
        this.encoderDrive(power, distance, distance, timeout);
    }

    public void moveBackward(double power, double distance, double timeout) {
        this.encoderDrive(power, -distance, -distance, timeout);
    }

    public void rotateCounterClockwise(double power, double distance, double timeout) {
        this.encoderDrive(power, -distance, distance, timeout);

    }

    public void rotateClockwise(double power, double distance, double timeout) {
        this.encoderDrive(power, distance, -distance, timeout);
    }

    public void duckCarouselClockwise(double power) {
        carouselMotor.setPower(power);
    }

    public void duckCarouselCounterClockwise(double power) {
        carouselMotor.setPower(-power);
    }

    public void closeClaw(){
        clawServo.setPosition(1);
    }

    public void openClaw(){
        clawServo.setPosition(0);
    }

    public void elevatorUp(){
        elevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevatorMotor.setPower(0.5);
    }

    public void elevatorDown(){
        elevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevatorMotor.setPower(-0.5);
    }

    public void elevatorStop(){
        elevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevatorMotor.setPower(0);
    }

    public void elevatorMoveToFloor(double power,double heightInches,double timeout){
        this.elevatorPosition(power,heightInches,timeout);
    }

    public void intakeOn(){
        if(intakeServo != null){
            intakeServo.setPower(0.5);
        }
    }
    public void releaseIntakeServo(){
        if(intakeServo != null) {
            intakeServo.setPower(-0.5);
        }
    }
    public void stopIntakeServo(){
        if(intakeServo != null) {
            intakeServo.setPower(0);
        }
    }

    /**
     * Assume that the elevator encoder was reset at power up
     * so position is 0.
     * Move to the given position based on heightInches
     * This function will stay until complete
     * used by Autonomous
     *
     * @param speed double speed [-1..1]
     * @param heightInches double desired inches off the ground
     * @param timeoutS timeout in seconds used by Autonomous
     */
    private void elevatorPosition(double speed, double heightInches, double timeoutS) {
        int heightTarget = (int) (heightInches * ELEVATOR_COUNTS_PER_INCH);

        // now we need to adjust for elevator angle
        // use ASA theorem

        elevatorMotor.setTargetPosition(heightTarget);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        runtime.reset();

        elevatorMotor.setPower(Math.abs(speed));
        while (
                (runtime.seconds() < timeoutS) &&
                        (elevatorMotor.isBusy())) ;

        // Stop all motion;
        elevatorMotor.setPower(0);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    /**
     * Assume that the elevator encoder was reset at power up
     * so position is 0.
     * Move to the given position based on heightInches
     * This function will maintain and return true when complete
     * used by TeleOp
     *
     * @param speed double speed [-1..1]
     * @param heightInches double desired inches off the ground
     * @return boolean complete
     */
    public boolean teleopElevatorPosition(double speed, double heightInches) {
        int heightTarget = (int) (heightInches * ELEVATOR_COUNTS_PER_INCH);
        int currentPosition = elevatorMotor.getCurrentPosition();

        // now we need to adjust for elevator angle
        // use ASA theorem

        // if we are close complete
        if (Math.abs(heightTarget-currentPosition) <= 10) {
            elevatorMotor.setPower(0);
            elevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            return true;
        } else {
            elevatorMotor.setTargetPosition(heightTarget);
            elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        return false;
    }

    /**
     * Should be called to zero the encoder
     * Example at the start of Autonomous
     */
    public void resetElevatorPosition(){
        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void encoderDrive(double speed,
                              double leftInches, double rightInches,
                              double timeoutS) {
        int newLeftRearTarget;
        int newLeftFrontTarget;
        int newRightRearTarget;
        int newRightFrontTarget;

        // Determine new target position, and pass to motor controller
        newLeftRearTarget = leftRearMotor.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
        newLeftFrontTarget = leftFrontMotor.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
        newRightRearTarget = rightRearMotor.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
        newRightFrontTarget = rightFrontMotor.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
        leftRearMotor.setTargetPosition(newLeftRearTarget);
        leftFrontMotor.setTargetPosition(newLeftFrontTarget);
        rightRearMotor.setTargetPosition(newRightRearTarget);
        rightFrontMotor.setTargetPosition(newRightFrontTarget);

        // Turn On RUN_TO_POSITION
        leftRearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        runtime.reset();
        leftRearMotor.setPower(Math.abs(speed));
        leftFrontMotor.setPower(Math.abs(speed));
        rightRearMotor.setPower(Math.abs(speed));
        rightFrontMotor.setPower(Math.abs(speed));

        // keep looping while we are still active, and there is time left, and both motors are running.
        // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
        // its target position, the motion will stop.  This is "safer" in the event that the robot will
        // always end the motion as soon as possible.
        // However, if you require that BOTH motors have finished their moves before the robot continues
        // onto the next step, use (isBusy() || isBusy()) in the loop test.
        while (
                (runtime.seconds() < timeoutS) &&
                        (leftRearMotor.isBusy()
                                && leftFrontMotor.isBusy()
                                && rightRearMotor.isBusy()
                                && rightFrontMotor.isBusy())) {

        }

        // Stop all motion;
        leftRearMotor.setPower(0);
        leftFrontMotor.setPower(0);
        rightRearMotor.setPower(0);
        rightFrontMotor.setPower(0);

        // Turn off RUN_TO_POSITION
        leftRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
}


