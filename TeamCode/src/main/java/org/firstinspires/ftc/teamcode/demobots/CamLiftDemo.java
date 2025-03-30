package org.firstinspires.ftc.teamcode.demobots;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Overview: This program operates the linear string lift with a cam mechanism for grabbing cones.
 * The following buttons are used: D-pad up, down for incrementally operating the linear
 * lift; D-pad left, right will open and close the cam mechanism; the "A" button provides a
 * short demonstration that lifts and drops the cone
 */
@TeleOp(name="CamLiftDemo")
public class CamLiftDemo extends LinearOpMode {

    //Declare a motor to control the lift and a servo to control the Cam mechanisms
    DcMotor lift = null;

    Servo cam = null;

    //declare variable to hold the position of the max height we want the cam to reach
    int Pos1 = 800;
    //declare variable that will determine the speed the lift moves
    double liftPower = 0.25;

    @Override

    public void runOpMode() {
        //add the motor and servo to the hardware map for the config
        lift = hardwareMap.get(DcMotor.class,"lift");
        cam  = hardwareMap.get(Servo.class,"cam");
        //Set motor to run with encoders
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //reverse the motor so it goes in the right direction
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        //add telemetry for the lift and cam positions (displays on driver station)
        telemetry.addData("Lift Position : ", lift.getCurrentPosition());
        telemetry.addData("Cam Position is at : ", cam.getPosition());

        //Start the Cam mechanism in an open position when the program starts
        cam.setPosition(1.00);
        // cam.getController().pwmEnable();
        waitForStart();

        while (opModeIsActive()){

            if (gamepad1.dpad_up) {
                //Bring the lift 50 higher than it's current position & hold at that position
                lift.setTargetPosition(lift.getCurrentPosition() + 50);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setPower(1);
                sleep(20);
//                lift.setPower(0);
//                lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            if (gamepad1.dpad_down) {
                //bring the lift 50 lower than it's current position & hold at that position
                lift.setTargetPosition(lift.getCurrentPosition() - 50);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setPower(1);
                sleep(20);
//                lift.setPower(0);
//                lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            if (gamepad1.dpad_right) {
                //reset the encoder of the lift
                lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
            if (gamepad1.a) {
                //raise the lift high enough to place a cone under
                lift.setTargetPosition(Pos1);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setPower(liftPower);
                sleep(1500);
                telemetry.addData("lift pos is : ", lift.getCurrentPosition());
                telemetry.update();
                //make a pause
                sleep(3000);
                //lower the lift into the cone
                lift.setTargetPosition(100);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setPower(liftPower);
                sleep(2500);
                // lift.setPower(0);
                telemetry.addData("Cam Position is: ", cam.getPosition());
                //close the Cam to pick up the cone
                cam.setPosition(0.00);
                //make a pause
                sleep(2500);
                //raise the lift back to the first spot
                lift.setTargetPosition(Pos1);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                sleep(500);
                lift.setPower(liftPower);
                //make a pause
                sleep(2500);
                //open the cam & drop the cone
                cam.setPosition(1.00);
                //make a pause
                sleep(2500);
                //return the lift to the starting position
                lift.setTargetPosition(0);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setPower(liftPower);
                sleep(3000);
//              lift.setPower(0);
//              lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//              cam.setPosition(0.50);

            }
            if (gamepad1.b) {
                //manually open the cam
                cam.setPosition(1.00);
            }
            if (gamepad1.x) {
                //manually close the cam
                cam.setPosition(0.00);
            }
            //update the lift and cam positions on the driver stations
            telemetry.addData("lift Pos: ", lift.getCurrentPosition());
            telemetry.addData("Cam Pos : ", cam.getPosition());
            telemetry.update();
        }

    }

}