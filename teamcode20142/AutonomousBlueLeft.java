package org.firstinspires.ftc.teamcode20142;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Autonomous Blue Left", group="Pushbot")
public class AutonomousBlueLeft extends AutonomousBase {

    @Override
    public void runOpMode() {
        // Initialize the hardware variables.
        startupInit();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Read barcode, go to shipping hub, drop off preload, park in warehouse

            // get barcode floor
            int floor = readBarcodeFloor();

            // come out of package and move to floor
            initToFloor(floor);

            // face the shipping hub
            commands.rotateClockwiseGyro(0.2,-30,8);
            // move to the hub
            commands.moveForward(commands.AUTONOMOUS_DRIVE_SPEED,24,8);
            // drop the box
            commands.openClaw();

            // clear the hub
            commands.moveBackward(commands.AUTONOMOUS_DRIVE_SPEED,5,3);

            // reset the elevator if above floor 1
            if (floor>1) {
                homeElevator(6);
            }

            // turn to face the warehouse
            commands.rotateCounterClockwiseGyro(commands.AUTONOMOUS_TURN_SPEED,90,10);
            // go to the warehouse
            commands.moveForward(commands.AUTONOMOUS_DRIVE_SPEED,45,11);

            sleep(30000);
        }
    }
}