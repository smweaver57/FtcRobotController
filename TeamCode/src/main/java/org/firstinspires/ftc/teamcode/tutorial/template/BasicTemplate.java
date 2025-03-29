package org.firstinspires.ftc.teamcode.tutorial.template;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;



/**
 * Give a description of what the program will do
 */
@TeleOp
public class BasicTemplate extends LinearOpMode {

    //Declare a Dc motor variable


    //Declare a 180 degree servo variable





    @Override

    public void runOpMode() {
        //This is the "init" section

        //look up motor and servo from the hardware map
        //The device name you set in the hardware map will be what you have to type in the config


        //this brings the servo to it's leftmost position


        //This waits for the user to press the play button
        waitForStart();

        while (opModeIsActive()){
            //This is the "run" section

            //give the motor power

            //add a sleep() method to allow the motor to move

            //stop the motor

            //bring the servo to its rightmost position

            //add a sleep() method to allow the servo time to reach the desired position

            //bring the servo to it's middle position

            //bring the servo to its leftmost position


        }

    }

}
