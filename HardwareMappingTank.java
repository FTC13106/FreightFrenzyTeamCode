package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class HardwareMappingTank
{
    /* Public OpMode members. */
    public DcMotor  leftFrontMotor   = null;
    public DcMotor  leftRearMotor   = null;
    public DcMotor  rightFrontMotor  = null;
    public DcMotor  rightRearMotor  = null;
    public DcMotor  carouselMotor = null;
    public WebcamName webcamName = null;
    public DcMotor elevatorMotor = null;
    public CRServo intakeServo = null;
    public Servo clawServo = null;
    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareMappingTank(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftFrontMotor = hwMap.get(DcMotor.class, "leftFrontMotor");
        leftRearMotor = hwMap.get(DcMotor.class, "leftRearMotor");
        rightFrontMotor = hwMap.get(DcMotor.class, "rightFrontMotor");
        rightRearMotor = hwMap.get(DcMotor.class, "rightRearMotor");
        carouselMotor = hwMap.get(DcMotor.class, "carouselMotor");
        webcamName = hwMap.get(WebcamName.class, "Webcam 1");
        elevatorMotor = hwMap.get(DcMotor.class, "elevatorMotor");
        intakeServo = hwMap.get(CRServo.class, "intakeServo");
        clawServo = hwMap.get(Servo.class, "clawServo");

        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        leftRearMotor.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
        rightRearMotor.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
        carouselMotor.setDirection(DcMotor.Direction.FORWARD);
        elevatorMotor.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to zero power
        leftFrontMotor.setPower(0);
        leftRearMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightRearMotor.setPower(0);
        carouselMotor.setPower(0);
        elevatorMotor.setPower(0);
        clawServo.setPosition(0);
        intakeServo.setPower(0);
        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }
}
