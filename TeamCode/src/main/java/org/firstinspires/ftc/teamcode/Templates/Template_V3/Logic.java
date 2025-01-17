package org.firstinspires.ftc.teamcode.Templates.Template_V3;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Logic extends Controllers {

    public Logic(Robot r) {
        super(r);
    }

    public double distance_between_squares = 5.5;
    public double DuckWheelPowerA = 0.3;
    public double DuckWheelPowerB = 0.55;

    public String elementPosition;

    //Autonomous Functions

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

        /* EXAMPLE - we want 2 servos, but only one can be connected to a controller
        robot.servo_list[servo_names.indexOf("left")].setPosition(1-robot.servo_list[servo_names.indexOf("right")].getPosition());
         */

        /* EXAMPLE - we want 2 motors, but only one can be connected to a controller; we want motor 2 to be at 2x the position motor 1 is at
        robot.dc_motor_list[dc_motor_names.indexOf("arm2")].setPower(Math.max(-0.6, Math.min(0.6,
            2*robot.dc_motor_list[dc_motor_names.indexOf("arm")].getCurrentPosition() - robot.dc_motor_list[dc_motor_names.indexOf("arm2")].getCurrentPosition()
        )));
        */

    }

    //Initialization

    public void init() {
        driver_servo_target_positions[servo_names.indexOf("right")] = 0.6;
    }




































    //DO NOT CHANGE

    //PID Stuff

    public int[] operator_values = new int[16];
    public int[] driver_values = new int[16];

    public int speedFactor = 1;

    public double heading = 0; // Current angle of robot
    public double desiredHeading = 0; // Angle that the robot wants to be at

    public double correction = 0; // Calculated amount that the robot has to correct

    public double current_error; // The difference between the heading and the desired heading
    public double previous_error;

    public long current_time;
    public long previous_time;

    public double p_weight = 0.025;
    public double d_weight = 0.85;

    //Basic Autonomous Functions

    public void pause(double milliseconds) {
        long t = System.nanoTime();
        while (System.nanoTime() - t <= milliseconds * 1000000) {

        }
    }

    public void ExecuteEncoders(double Speed) {
        robot.SpeedSet(Speed);
        while (robot.MotorsBusy()) {
        }
        robot.SpeedSet(0.2);
        pause(100);
        robot.SpeedSet(0);
        pause(200);
    }

    public void ExecuteEncoders() {
        ExecuteEncoders(0.7);
    }

    public void Drive(double Dist){
        robot.DriveDistance(-Dist);
        ExecuteEncoders();
    }

    public void Drive(double Dist, String Direction){
        robot.DriveDistance(Dist, Direction);
        ExecuteEncoders();
    }

    public void Drive(double Dist, String Direction, double Speed){
        robot.DriveDistance(Dist, Direction);
        ExecuteEncoders(Speed);
    }

    public void Turn(int Degrees, String Direction){
        if(Direction.equals("Right")) {
            robot.turnWithEncoders(Degrees);
        } else if(Direction.equals("Left")) {
            robot.turnWithEncoders(-Degrees);
        }
        ExecuteEncoders();
    }

    public void SetPower(String name, double power) {
        robot.dc_motor_list[dc_motor_names.indexOf(name)].setPower(power);
    }

    public void ResetEncoder(String name) {
        robot.dc_motor_list[dc_motor_names.indexOf(name)].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
