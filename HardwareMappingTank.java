package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
        leftFrontMotor = setupMotor("leftFrontMotor", DcMotor.Direction.REVERSE, 0, true);
        leftRearMotor = setupMotor("leftRearMotor", DcMotor.Direction.REVERSE, 0, true);
        rightFrontMotor = setupMotor("rightFrontMotor", DcMotor.Direction.FORWARD, 0, true);
        rightRearMotor = setupMotor("rightRearMotor", DcMotor.Direction.FORWARD, 0, true);
        webcamName = setupWebcam("Webcam 1");
        elevatorMotor = setupMotor("elevatorMotor", DcMotor.Direction.FORWARD, 0, true);
        carouselMotor = setupMotor("carouselMotor", DcMotor.Direction.FORWARD, 0, false);
        intakeServo = setupCRServo("intakeServo",  0);
        clawServo = setupServo("clawServo",  0);
    }

    /* Init Motor, set direction, initial power and encoder runmode (if applicable)
    * @return the configured DcMotor or null if the motor is not found
    */
    private DcMotor setupMotor(String name, DcMotorSimple.Direction direction, int initialPower, boolean useEncoder){
        try {
            DcMotor motor = hwMap.get(DcMotor.class, name);
            motor.setDirection(direction);
            motor.setPower(initialPower);
            if (useEncoder){
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            return motor;
        }
        catch(Exception e) {
            return null;
        }
    }

    /* Init CRServo and set initial power
     * @return the configured CRServo or null if the servo is not found
     */
    private CRServo setupCRServo(String name, int initialPower){
        try {
            CRServo servo = hwMap.get(CRServo.class, name);

            servo.setPower(initialPower);
            return servo;
        }
        catch(Exception e) {
            return null;
        }
    }

    /* Init Servo and set initial position
     * @return the configured Servo or null if the servo is not found
     */
    private Servo setupServo(String name, int initialPosition){
        try {
            Servo servo = hwMap.get(Servo.class, name);
            servo.setPosition(initialPosition);
            return servo;
        }
        catch(Exception e) {
            return null;
        }
    }

    /* Init WebcamName
     * @return the configured WebcamName or null if the webcam is not found
     */
    private WebcamName setupWebcam(String name){
        try {
            WebcamName webcamName = hwMap.get(WebcamName.class, name);
            return webcamName;
        }
        catch(Exception e) {
            return null;
        }
    }
}
