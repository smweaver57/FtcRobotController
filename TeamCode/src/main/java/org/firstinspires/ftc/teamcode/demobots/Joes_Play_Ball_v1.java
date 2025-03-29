/*
FTC Team 4991 GearFreaks
Author(s): Alex Pereira and Charles Burometto
Date: 15 Feb 2019
*/
package org.firstinspires.ftc.teamcode.demobots;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name="Joes_Play_Ball_v1")
public class Joes_Play_Ball_v1 extends LinearOpMode {

    // Declare OpMode members.
    DcMotor Left;
    DcMotor Right;
    DcMotor Catapult;  // need to use encoder with this motor - neverest 60

    Float DCMotorSpeed;
    float Yvalue1;
    float Xvalue1;
    float Yvalue2;
    float Xvalue2;

    boolean buttonA;
    boolean buttonB;
    boolean buttonX;
    boolean buttonY;
    boolean dpad_up;
    boolean dpad_down;

    boolean start_game = false;
    int launch_position =0;

    ColorSensor color1; // color sensor

    boolean LEDState = true;
    //Tracks the mode of the color sensor; Active = true, Passive = false
    boolean colorDetected = false;

    private ElapsedTime     runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        Left = hardwareMap.dcMotor.get("left");
        Right = hardwareMap.dcMotor.get("right");
        Left.setDirection(DcMotor.Direction.REVERSE);

        Catapult = hardwareMap.dcMotor.get("catapult");
        //Catapult.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Catapult.setTargetPosition(Catapult.getCurrentPosition());
        Catapult.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        telemetry.addData("Initial Catapult: ", launch_position);

        color1 = hardwareMap.colorSensor.get("color1");
        color1.enableLed(true);
        //color1.setI2cAddress(I2cAddr.create8bit(0x3a));


        // set catapult to starting position
        launch_position = (Catapult.getCurrentPosition());
        launch_position += 1180;
        Catapult.setPower(1.00);
        Catapult.setTargetPosition(launch_position);
        telemetry.addData("Launch Position: ", launch_position);
        sleep(2000);
        Catapult.setPower(0.00);

        telemetry.addData("Robot Initiatized:", "Ready to Start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            //getJoystickSettings(joystick);
            Yvalue1 = gamepad1.left_stick_y;
            Yvalue2 = gamepad1.right_stick_y;

            buttonA = gamepad1.a;
            buttonB = gamepad1.b;
            buttonX = gamepad1.x;
            buttonY = gamepad1.y;
            dpad_down = gamepad1.dpad_down;
            dpad_up = gamepad1.dpad_up;

            Yvalue1 = Range.clip(Yvalue1, -1, 1);
            Yvalue2 = Range.clip(Yvalue2, -1, 1);

            Left.setPower(Yvalue1);
            Right.setPower(Yvalue2);

            //COLOR_READ = (color1.blue() < color1.red());
            telemetry.addData("Red", color1.red());
            telemetry.addData("Blue", color1.blue());
            telemetry.addData("Green", color1.green());
            telemetry.addData("MODE: ", start_game);
            telemetry.update();


            //  The A and B buttons are used to turn ON/OFF the automatic launching mode
            if(buttonA) {
                telemetry.addData("TURNING ON AUTO", "      A");
                telemetry.update();
                start_game = true; // auto mode
            }
            if(buttonB) {
                telemetry.addData("TURNING OFF AUTO", "           M");
                telemetry.update();
                start_game = false; // manual mode
            }

            // if AUTO mode is ON, then check color sensor and see if a ball is in bucket
            if( start_game ) {
                if( (color1.red() > 3) && (color1.blue() > 3) && (color1.green() > 3)) {
                    sleep(2000);
                    launch_position = (Catapult.getCurrentPosition());
                    launch_position += 500;
                    Catapult.setPower(1.00);
                    Catapult.setTargetPosition(launch_position);
                    telemetry.addData("AUTO Launching Ball: ", launch_position);

                    // give the catapult time to stop/settle
                    // then reset its position
                    sleep(4000);
                    launch_position = (Catapult.getCurrentPosition());
                    launch_position += 1180;
                    Catapult.setPower(1.00);
                    Catapult.setTargetPosition(launch_position);
                    telemetry.addData("AUTO Resetting Launcher: ", launch_position);
                    telemetry.update();
                    //while(Catapult.isBusy()) {
                    //    sleep(1000);
                    //}
                    // give the catapult a chance to settle/stop moving
                    // before checking the color sensor again
                    sleep(4000);
                    telemetry.addData("AUTO MODE", "launching ball");
                    telemetry.update();
                    Catapult.setPower(0.00);
                }
            }

            // button X is used to launch the ball in manual mode
            if(buttonX && !start_game) {
                launch_position = (Catapult.getCurrentPosition());
                launch_position += 500;
                Catapult.setPower(1.00);
                Catapult.setTargetPosition(launch_position);
                telemetry.addData("Manual Launching Ball: ", launch_position);
                telemetry.update();

                // give the catapult a chance to settle/stop moving before resetting
                sleep(4000);
                launch_position = (Catapult.getCurrentPosition());
                launch_position += 1180;
                Catapult.setPower(1.00);
                Catapult.setTargetPosition(launch_position);
                telemetry.addData("Manual Resetting Launcher: ", launch_position);
                telemetry.update();
                //while(Catapult.isBusy()) {
                //    sleep(1000);
                //}
                sleep(5000);
                Catapult.setPower(0.00);
            }
            if(dpad_down) {
                // increment the Catapult motor by 10;
                launch_position = (Catapult.getCurrentPosition());
                launch_position += 105;
                Catapult.setPower(1.00);
                Catapult.setTargetPosition(launch_position);
                telemetry.addData("INCREMENTING BY 10:", launch_position);
                telemetry.update();
                sleep(500);
                Catapult.setPower(0.00);
            }
            if(dpad_up) {
                // decrement the Catapult motor by 10;
                launch_position = (Catapult.getCurrentPosition());
                telemetry.addData("Starting down:", launch_position);
                telemetry.update();
                launch_position -= 105;
                Catapult.setPower(1.00);
                Catapult.setTargetPosition(launch_position);
                telemetry.addData("DECREMENTING by 10:", launch_position);
                telemetry.update();
                sleep(500);
                Catapult.setPower(0.00);
            }
        }

        //Prepares To End Match
        // set catapult to a non-tension position
        launch_position = (Catapult.getCurrentPosition());
        launch_position -= 500;
        Catapult.setPower(1.00);
        Catapult.setTargetPosition(launch_position);
        telemetry.addData("Catapult at Rest: ", launch_position);
        telemetry.update();


        Left.setPower(0.00);
        Right.setPower(0.00);
        Catapult.setPower(0.00);
        color1.enableLed(false);

        telemetry.addData("Program: ", "Complete");
        telemetry.update();
        idle();
    }

}