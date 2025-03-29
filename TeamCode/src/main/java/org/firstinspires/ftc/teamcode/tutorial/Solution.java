package org.firstinspires.ftc.teamcode.tutorial;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

//Give a description of what the program will do
/*
 *This program will use a Dc Motor as well as a 180 degree servo. The program will set the motor to move
 * for a short time, then move the servo through a few positions, then repeat.
 */

@TeleOp
public class Solution extends LinearOpMode {

    //Declare a Dc motor
    DcMotor motor1 = null;

    //Declare a 180 degree servo
    Servo   servo1 = null;




    @Override

    public void runOpMode() {
        //add the motor and servo to the hardware map
        //The device name you set in the hardware map will be what you have to type in the config
        motor1 = hardwareMap.get(DcMotor.class, "motor");
        servo1 = hardwareMap.get(Servo.class,"servo");

        //this brings the servo to it's leftmost position
        servo1.setPosition(1.00);

        waitForStart();

        while (opModeIsActive()){
            //give the motor power
            motor1.setPower(1);
            //add a sleep() method to allow the motor to move
            sleep(5000);
            //stop the motor
            motor1.setPower(0);
            //bring the servo to it's rightmost position
            servo1.setPosition(-1.00);
            //add a sleep() method to allow the servo time to reach the desired position
            sleep(2000);
            //bring the servo to it's middle position
            servo1.setPosition(0.00);
            sleep(1000);
            //bring the servo to it's leftmost position
            servo1.setPosition(1.00);
            sleep(1000);

        }

    }

}
