package org.firstinspires.ftc.teamcode20142;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Autonomous Red Left", group="Pushbot")
public class AutonomousRedLeft extends AutonomousBase {

    @Override
    public void runOpMode() {
        // Initialize the hardware variables.
        startupInit();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // read barcode, go to shipping hub, drop off preload, drive to carousel, park in storage unit

            // get barcode floor
            int floor = readBarcodeFloor();

            // come out of package and move to floor
            initToFloor(floor);

            // face the shipping hub
            commands.rotateClockwiseGyro(commands.AUTONOMOUS_TURN_SPEED, -30,7);
            // go to shipping hub
            commands.moveForward(commands.AUTONOMOUS_DRIVE_SPEED, 20, 8);
            // drop preload box
            commands.openClaw();
            // move away from the shipping hub
            commands.moveBackward(commands.AUTONOMOUS_DRIVE_SPEED,18, 8);

            // reset the elevator if above floor 1
            if (floor>1) {
                homeElevator(6);
            }

            // turn to face the carousel
            commands.rotateCounterClockwiseGyro(commands.AUTONOMOUS_TURN_SPEED, 85,7);

            // drive towards carousel
            commands.moveForward(commands.AUTONOMOUS_DRIVE_SPEED,20, 8);
            // turn on the duck spinner
            commands.duckCarouselCounterClockwise(commands.CAROUSEL_SPEED);
            // wait for duck to fall off
            sleep(6000); // 4 seconds?
            // turn off Carousel motor
            commands.duckCarouselCounterClockwise(0);

            // move the robot off the carousel
            commands.moveBackward(commands.AUTONOMOUS_DRIVE_SPEED, 4, 8);
            // line up to storage unit
            commands.rotateClockwiseGyro(commands.AUTONOMOUS_TURN_SPEED, 25,7);
            //move into the storage unit
            commands.moveForward(commands.AUTONOMOUS_DRIVE_SPEED, 17, 8);
            // line up to storage unit
            commands.rotateClockwiseGyro(commands.AUTONOMOUS_TURN_SPEED, 0,7);
            // sleep
            sleep(30000);
        }
    }
}