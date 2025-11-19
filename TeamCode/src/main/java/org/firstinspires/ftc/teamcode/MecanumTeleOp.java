package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@SuppressWarnings("unused")
@TeleOp
public class MecanumTeleOp extends LinearOpMode {

    boolean leftBumperPreviouslyPressed = false;
    boolean rightBumperPreviouslyPressed = false;

    @Override
    public void runOpMode() {

        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        DcMotor leftOuttake = hardwareMap.dcMotor.get("leftOuttake");
        DcMotor rightOuttake = hardwareMap.dcMotor.get("rightOuttake");
        DcMotor intake = hardwareMap.dcMotor.get("intake");

        double OuttakeSpeed = 0.000;

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        
        rightOuttake.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = ((y + x + rx) / denominator);
            double backLeftPower = ((y - x + rx) / denominator);
            double frontRightPower = ((y - x - rx) / denominator);
            double backRightPower = ((y + x - rx) / denominator);

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);


            if(gamepad1.left_bumper && !leftBumperPreviouslyPressed){
                OuttakeSpeed -= 0.002;
            }
            if(gamepad1.right_bumper && !rightBumperPreviouslyPressed){
                OuttakeSpeed += 0.002;
            }

            leftBumperPreviouslyPressed = gamepad1.left_bumper;
            rightBumperPreviouslyPressed = gamepad1.right_bumper;

            if(OuttakeSpeed > 1.000){
                OuttakeSpeed = 1.000;
            }
            if(OuttakeSpeed < 0.000){
                OuttakeSpeed = 0.000;
            }

            if(gamepad1.x){
                intake.setPower(1);
            }
            if(gamepad1.y){
                intake.setPower(0);
            }
//idk lol
            rightOuttake.setPower(OuttakeSpeed);
            leftOuttake.setPower(OuttakeSpeed);

            telemetry.addData("Outtake RPM: ", (OuttakeSpeed * 6000));
            telemetry.update();
        }
    }
}
