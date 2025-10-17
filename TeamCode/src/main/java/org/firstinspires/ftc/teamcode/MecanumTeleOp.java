package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@SuppressWarnings("unused")
@TeleOp
public class MecanumTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        DcMotor leftOuttake = hardwareMap.dcMotor.get("leftOuttake");
        DcMotor rightOuttake = hardwareMap.dcMotor.get("rightOuttake");
        DcMotor intake = hardwareMap.dcMotor.get("intake");

        double OuttakeSpeed = 0.000;

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        /*
        if(gamepad1.a){
            leftOuttake.setPower(1);
            rightOuttake.setPower(1);
        }
        if(gamepad1.b){
            leftOuttake.setPower(0);
            rightOuttake.setPower(0);
        }
````````*/

        if(gamepad1.left_bumper){
            OuttakeSpeed -= 0.001;
        }
        if(gamepad1.right_bumper){
            OuttakeSpeed += 0.001;
        }

        if(gamepad1.x){
            intake.setPower(1);
        }
        if(gamepad1.y){
            intake.setPower(0);
        }

        rightOuttake.setPower(OuttakeSpeed);
        leftOuttake.setPower(OuttakeSpeed);

        telemetry.addData("Outtake RPM: ", (OuttakeSpeed * 1000));
        telemetry.update();



        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = ((y + x + rx) / denominator) * -1;
            double backLeftPower = ((y - x + rx) / denominator);
            double frontRightPower = ((y - x - rx) / denominator);
            double backRightPower = ((y + x - rx) / denominator);

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
        }
    }
}