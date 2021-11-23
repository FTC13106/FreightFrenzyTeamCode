package org.firstinspires.ftc.teamcode20142;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="A Vision Test", group="Pushbot")
public class Vision extends LinearOpMode {
    ObjectDetection objectDetection = new ObjectDetection();

    @Override
    public void runOpMode() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        objectDetection.init(hardwareMap);

        telemetry.addData("webcam", "ready");
        telemetry.update();
        // Trying to call objectDetection will not work before game start

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            int state = objectDetection.getDuckState();
            float duckLocation = objectDetection.getDuckPosition();
            for (int i = 0; i<= 60; i++){
                state = objectDetection.getDuckState();
                duckLocation = objectDetection.getDuckPosition();
                telemetry.addData("count ", i);
                telemetry.addData("state ", state);
                telemetry.addData("duck location", duckLocation);
                telemetry.update();
                sleep(500);
            }

            sleep(30000);
        }
    }
}
