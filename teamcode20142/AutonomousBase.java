package org.firstinspires.ftc.teamcode20142;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class AutonomousBase extends LinearOpMode {
    Commands commands = new Commands();
    ObjectDetection objectDetection = new ObjectDetection();

    public void startupInit() {
        commands.init(hardwareMap);
        commands.resetElevatorPosition();
        objectDetection.init(hardwareMap);
        telemetry.addData("webcam", "ready");
        telemetry.update();
    }

    public void homeElevator(int timeoutS) {
        commands.openClawMidway();
        commands.elevatorHome(timeoutS);
        commands.openClaw();
    }

    public int readBarcodeFloor() {
        telemetry.addData("Reading barcode", "...");
        telemetry.update();
        int floor = objectDetection.getBarcodeFloor(objectDetection);
        if (floor == 0) {
            floor = 1;
            telemetry.addData("no barcode found using floor ", floor);
        }
        telemetry.addData("floor ", floor);
        telemetry.update();
        return floor;
    }

    public void initToFloor(int floor) {
        // come out of init, close claw
        commands.closeClaw();
        // move off the wall
        commands.moveForward(commands.AUTONOMOUS_DRIVE_SPEED, 7, 8);
        // move the elevator to correct position. floor1=3in,floor2=8.5in,floor3=14.75in,floor4=20.25
        commands.elevatorMoveToFloor(floor, 5);
    }
}