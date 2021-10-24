package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Autonomous Test", group="Pushbot")
public class AutonomousTest extends LinearOpMode {

    /* Declare OpMode members. */
    Commands commands = new Commands();

    @Override
    public void runOpMode() {

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        commands.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            commands.moveForward(0.5,10, 15);
            commands.moveBackward(0.65, 15, 20);
            commands.rotateCounterClockwise(0.5, 10,20 );
            sleep(30000);
        }
    }




}
