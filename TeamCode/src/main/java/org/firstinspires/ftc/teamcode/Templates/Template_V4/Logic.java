package org.firstinspires.ftc.teamcode.Templates.Template_V4;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Templates.Template_V4.DoNotChange.Logic_Base;
import org.firstinspires.ftc.teamcode.Templates.Template_V4.DoNotChange.Robot;

public class Logic extends Logic_Base {

    public double distance_between_squares = 5.5;
    public double DuckWheelPowerA = 0.3;
    public double DuckWheelPowerB = 0.55;

    public String elementPosition;

    //Autonomous Functions
    //Default Autonomous Functions (contained in superclass):
        //pause(time) - in milliseconds
        //Drive(distance, speed, direction) - not all args necessary, but need left args before right ones
        //Turn(degrees, direction) - "Right" or "Left"
        //SetPower(dc_motor_name, power)
        //ResetEncoder(dc_motor_name)

    public void SetArm(String position){

        robot.dc_motor_list[dc_motor_names.indexOf("arm")].setTargetPosition(armPositions[armPositionNames.indexOf(position)]);
        robot.dc_motor_list[dc_motor_names.indexOf("arm")].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.dc_motor_list[dc_motor_names.indexOf("arm")].setPower(0.4);
        while(robot.dc_motor_list[dc_motor_names.indexOf("arm")].isBusy()) {
        }
        robot.dc_motor_list[dc_motor_names.indexOf("arm")].setPower(0);

    }

    public void SetServo(String position) {
        robot.servo_list[servo_names.indexOf("right")].setPosition(servoPositions[servoPositionNames.indexOf(position)]);
        pause(100);
        /* This *could* be a solution to the issue - let's see
        for (int i = 0; i < 100; i++) {
            r.servo_list[servo_names.indexOf("BucketServo")].setPosition(servoPositions[servoPositionNames.indexOf(position)]);
            r.servo_list[servo_names.indexOf("BucketServo")].setPosition(servoPositions[servoPositionNames.indexOf(position)]+0.001);
        }
        */
    }

    public boolean checkPos() {
        return (robot.getDistInch("sensor_distance") < 4);
    }

    public void execute_non_driver_controlled() {
        //this will have the telemetry, LEDs, etc.

        //Telemetry
        robot.telemetry.addData("Arm Position: ", robot.dc_motor_list[dc_motor_names.indexOf("arm")].getCurrentPosition());
        robot.telemetry.update();

        /* EXAMPLE - set LED color if distance sensor detects something
        if (r.getDistInch("dSensor") < 4) r.set_led_color("led", "Blue");
        else r.set_led_color("led", "Green");
        */

    }

    //Initialization

    public void init() {
        target_positions[dc_motor_names.size() + servo_names.indexOf("right")] = 0.6;
    }

    public Logic(Robot r) {
        super(r);
    }

}
