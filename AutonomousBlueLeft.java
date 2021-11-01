package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Autonomous Blue Left", group="Pushbot")
public class AutonomousBlueLeft extends LinearOpMode {

    Commands commands = new Commands();

    @Override
    public void runOpMode() {

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        commands.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // move forward 2 inch then turn 90 degrees counterclockwise then itll move 40 degrees into the warhouse
        while (opModeIsActive()) {
            // move of the wall 5
            commands.moveForward(0.5,5,5);
            //face the hub
            commands.rotateClockwise(0.5,40,8);
            //kiss the hub
            commands.moveForward(0.5,24,8);
            //drop the box
            // clear the hub
            commands.moveBackward(0.5,5,3);
            //turn to face the warehouse
            commands.rotateCounterClockwise(0.5,60,10);
            //go to the warehouse
            commands.moveForward(0.5,45,11);


//            commands.moveForward(0.5, 2,8);
//            commands.rotateCounterClockwise(0.5, 18, 5);
//            commands.moveForward(0.5, 40,8);
            sleep(30000);
        }
    }



}
