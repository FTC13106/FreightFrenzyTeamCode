package org.firstinspires.ftc.teamcode20142;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Autonomous Reset", group="Pushbot")
public class AutonomousReset extends LinearOpMode {
    Commands commands = new Commands();

    @Override
    public void runOpMode() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        commands.init(hardwareMap);

        // move claw out of the way
        commands.openClawMidway();

        // move floor to start position
        commands.elevatorMoveToFloor(1,5);

        // open claw fully
        commands.openClaw();
    }
}