package org.firstinspires.ftc.teamcode20142;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Commands extends HardwareMapping {

    static final double COUNTS_PER_MOTOR_REV = 960;    // eg: TETRIX Motor Encoder (40:1)
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    static final double ELEVATOR_WHEEL_DIAMETER_INCHES = 0.25;
    static final double ELEVATOR_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    static final double ELEVATOR_COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * ELEVATOR_GEAR_REDUCTION) /
            (ELEVATOR_WHEEL_DIAMETER_INCHES * 3.1415);

    static final double AUTONOMOUS_DRIVE_SPEED = .6;
    static final double AUTONOMOUS_TURN_SPEED = .2;
    static final double CAROUSEL_SPEED = .5;

    static final int ELEVATOR_MIN_HEIGHT = -3000;
    static final int ELEVATOR_FLOOR_1 = 100;
    static final int ELEVATOR_FLOOR_2 = 4300;
    static final int ELEVATOR_FLOOR_3 = 9800;
    static final int ELEVATOR_MAX_HEIGHT = 10000;

//    static final double FLOOR_1 = 3.0;
//    static final double FLOOR_2 = 8.5;
//    static final double FLOOR_3 = 14.75;
//    static final double FLOOR_4 = 20.25;

    private ElapsedTime runtime = new ElapsedTime();

    public void moveForward(double power, double distance, double timeout) {
        this.encoderDrive(power, distance, distance, timeout);
    }

    public void moveBackward(double power, double distance, double timeout) {
        this.encoderDrive(power, -distance, -distance, timeout);
    }

    /**
     * Init of the robot always zero's out the heading
     * This will turn the robot Clockwise direction until
     * the gyro is at this heading within HEADING_ERROR
     * @param power [-1..1]
     * @param heading [0..360] heading based on init of robot
     * @param timeout seconds until abort for Autonomous
     */
    public void rotateClockwiseGyro(double power,double heading,double timeout){
        this.gyroTurn(power,heading,timeout);
    }

    /**
     * Init of the robot always zero's out the heading
     * This will turn the robot Counter Clockwise direction until
     * the gyro is at this heading within HEADING_ERROR
     * @param power [-1..1]
     * @param heading [0..360] heading based on init of robot
     * @param timeout seconds until abort for Autonomous
     */
    public void rotateCounterClockwiseGyro(double power,double heading,double timeout){
        this.gyroTurn(-power,heading,timeout);
    }

    public void duckCarouselClockwise(double power) {
        carouselMotor.setPower(power);
    }

    public void duckCarouselCounterClockwise(double power) {
        carouselMotor.setPower(-power);
    }

    public void closeClaw() {
        clawServo.setPosition(1);
    }
    public void openClawMidway(){
        clawServo.setPosition(0.7);
    }
    public void openClaw(){
        clawServo.setPosition(0.4);
    }

    /* Elevator */
    public void elevatorUp(){
        elevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevatorMotor.setPower(1);
    }

    public void elevatorDown(){
        elevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevatorMotor.setPower(-1);
    }

    public void elevatorStop(){
        elevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevatorMotor.setPower(0);
    }

    public void elevatorHome(int timeoutS){
        elevatorMotor.setTargetPosition(0);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevatorMotor.setPower(1);
        runtime.reset();
        while (
                (runtime.seconds() < timeoutS) &&
                        (elevatorMotor.isBusy())) ;

        // Stop all motion;
        elevatorMotor.setPower(0);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // Move the elevator to correct floor. floor1=3in,floor2=8.5in,floor3=14.75in,floor4=20.25
    public void elevatorMoveToFloor(int floor,double timeout){
        if (floor == 2){
            elevatorPosition(0.5, ELEVATOR_FLOOR_2, timeout);
        }
        else if (floor == 3){
            elevatorPosition(0.5, ELEVATOR_FLOOR_3, timeout);
        }
        else{
            elevatorPosition(0.5, ELEVATOR_FLOOR_1, timeout);
        }
    }

    public void elevatorMoveToHeight(double power,double heightInches,double timeout){
        int encoderPosition = (int) (heightInches * ELEVATOR_COUNTS_PER_INCH);
        this.elevatorPosition(power, encoderPosition, timeout);
    }

    /**
     * Assume that the elevator encoder was reset at power up
     * so position is 0.
     * Move to the given position based on heightInches
     * This function will stay until complete
     * used by Autonomous
     *
     * @param speed double speed [-1..1]
     * @param encoderPosition int desired encoder position
     * @param timeoutS timeout in seconds used by Autonomous
     */
    private void elevatorPosition(double speed, int encoderPosition, double timeoutS) {

        // prevent the elevator from going too high
        if (encoderPosition > ELEVATOR_MAX_HEIGHT) {
            encoderPosition = ELEVATOR_MAX_HEIGHT;
        }
        // prevent the elevator from going too low
        if (encoderPosition < ELEVATOR_MIN_HEIGHT) {
            encoderPosition = ELEVATOR_MIN_HEIGHT;
        }
        // now we need to adjust for elevator angle
        // use ASA theorem

        elevatorMotor.setTargetPosition(encoderPosition);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        runtime.reset();

        elevatorMotor.setPower(Math.abs(speed));
        while (
                (runtime.seconds() < timeoutS) &&
                        (elevatorMotor.isBusy())
        );

        // Stop all motion;
        elevatorMotor.setPower(0);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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

    private void gyroTurn(double speed, double heading, double timeout){
        runtime.reset();

        while(Math.abs(getError(heading)) >= 5 && (runtime.seconds() < timeout)){
            leftFrontMotor.setPower(speed);
            leftRearMotor.setPower(speed);
            rightFrontMotor.setPower(-speed);
            rightRearMotor.setPower(-speed);
        }

        leftFrontMotor.setPower(0);
        leftRearMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightRearMotor.setPower(0);
    }

    public double getError(double targetAngle) {
        Orientation angles;
        double robotError;

        // calculate error in -179 to +180 range  (
        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        robotError = targetAngle - angles.firstAngle;
        return robotError;
    }
}