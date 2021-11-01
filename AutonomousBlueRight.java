package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Autonomous Blue Right", group="Pushbot")
public class AutonomousBlueRight extends LinearOpMode {

    Commands commands = new Commands();

    @Override
    public void runOpMode() {

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        commands.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Robot will move forward about 25 degrees athen itll
        // turn facing the storage unit and move into the storage unit
        while (opModeIsActive()) {

            //go to shipping hub, drop off preload, drive to carosel, park in *warehouse?
            //get off the wall
            commands.moveForward(0.5, 7, 8);
            //face the shipping hub
            commands.rotateCounterClockwise(0.5, 25,7); // 25 is a guess, refine later
            //go to shipping hub
            commands.moveForward(0.5, 25, 8);

            //drop preload box


            //move away from the shipping hub
            commands.moveBackward(0.5,5, 8);
            //turn to face the carousel
            commands.rotateCounterClockwise(0.5, 10,7);
            //drive towards carousel
            commands.moveBackward(0.5,35, 8);
            //moving the robot out of the way
            commands.rotateCounterClockwise(0.5, 5,7);
            //turn on the duck spinner
            commands.duckCarouselClockwise(0.5);
            //wait for duck to fall off
            sleep(4000); // 4 seconds?
            // turn off Carousel motor
            commands.duckCarouselClockwise(0);
            //move the robot off the carousel
            commands.moveForward(0.5, 3, 8);
            //line up to storage unit
            commands.rotateClockwise(0.5, 18,7);
            //move into the storage unit
            commands.moveForward(0.5, 21, 8);
            //sleep



            // Go to storage unit :P
            //commands.moveForward(0.5, 25, 8);
            //commands.rotateClockwise(0.5, 15,7);
            //commands.moveForward(0.5, 30,7);
            sleep(30000);
        }
    }


}
