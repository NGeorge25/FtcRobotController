package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Systems.RobotHardware;

@Autonomous(name = "Blue Warehouse Auton")
public class Warehouse_Blue_Auton extends LinearOpMode implements Auton_Values{

    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware robot = new RobotHardware();

    private int elementPosition = 1; //1 is at the far left

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, telemetry);
        waitForStart();
        while (opModeIsActive()) {

            //SetArm(2);
            //SetServo(2); //1 Bottom 2 Mid 3 Dump
            //checking position and go to set position by first square
            Drive(7.5);
            if (checkPos()) {
                elementPosition = 3;
                Drive(3.5); //this value is not confirmed
            }
            else {
                Drive(3.5);
                Turn(90, "Left"); //might be something screwy here
                if (checkPos()){
                    elementPosition = 2;
                }
                Turn(90, "Right");
            }
            robot.telemetry.addData("Barcode: ", elementPosition);
            robot.telemetry.update();

            //drop block in tower
            Drive(8);
            Turn(90, "Right");
            Drive(3.5);

            robot.IN.setPower(0.4);
            robot.Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            SetArm(0);
            robot.rServo.setPosition(servoM);

            robot.IN.setPower(0);
            sleep(100);
            SetArm(elementPosition);
            robot.rServo.setPosition(servoBM);

            sleep(100);
            robot.rServo.setPosition(servoD);

            sleep(2000);

            //reset arm
            robot.rServo.setPosition(servoB);

            SetArm(0);
            sleep(1000);

            //go over to duck wheel and spin it
            Drive(-3.1);
            Drive(25.5, "Right");

            Drive(-4);
            Turn(50, "Right");
            Drive(5.2, "Right", 0.4);

            robot.Duck.setPower(-duckMotorPowerA);
            sleep(3000);
            robot.Duck.setPower(-duckMotorPowerB);
            sleep(1000);
            robot.Duck.setPower(0);

            Drive(2, "Left");

            //park
            Turn(50, "Left");
            Drive(11);
            Drive(3, "Right");

            stop();
        }
    }

    private void ExecuteEncoders() {
        robot.SpeedSet(0.7);
        while (robot.MotorsBusy() && opModeIsActive()) {
            robot.telemetry.addData("Encoder RF: ", robot.RF.getCurrentPosition());
            robot.telemetry.addData("Encoder LF: ", robot.LF.getCurrentPosition());
            robot.telemetry.addData("Encoder RB: ", robot.RB.getCurrentPosition());
            robot.telemetry.addData("Encoder LB: ", robot.LB.getCurrentPosition());

            // in theory these should be the same while it's going forward

            robot.telemetry.update();
        }
        robot.SpeedSet(0.2);
        sleep(100);
        robot.SpeedSet(0);
        sleep(200);
    }
    private void ExecuteEncoders(double Speed) {
        robot.SpeedSet(Speed);
        while (robot.MotorsBusy() && opModeIsActive()) {
            idle();
        }
        robot.SpeedSet(0.2);
        sleep(100);
        robot.SpeedSet(0);
        sleep(200);
    }

    private boolean checkPos(){
        if(robot.getDistInch() < 4){
            return true;
        }
        return false;
    }

    private void Drive(double Dist){
        robot.DriveDistance(-Dist);
        ExecuteEncoders();
    }

    private void Drive(double Dist, String Direction){
        robot.DriveDistance(Dist, Direction);
        ExecuteEncoders();
    }
    private void Drive(double Dist, String Direction, double Speed){
        robot.DriveDistance(Dist, Direction);
        ExecuteEncoders(Speed);
    }

    private void Turn(int Degrees, String Direction){
        if(Direction.equals("Right")){
            robot.turnEncoderDegree(Degrees);
        }else if(Direction.equals("Left")){
            robot.turnEncoderDegree(-Degrees);
        }
        ExecuteEncoders();
    }
    private void SetArm(int pos){

        switch(pos){
            case 1:

                robot.Arm.setTargetPosition(low_goal);
                break;
            case 2:

                robot.Arm.setTargetPosition(mid_goal);
                break;
            case 3:

                robot.Arm.setTargetPosition(high_goal);
                break;
            case 0:
                robot.Arm.setTargetPosition(reset_arm);
                break;
        }
        robot.Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.Arm.setPower(armSpeed);
        while(robot.Arm.isBusy() && opModeIsActive()){
            idle();
        }
        robot.Arm.setPower(0.1);

    }
    private void SetServo(int position){
        double pos = servoB;
        switch(position) {
            case 1:
                pos = servoB;
            case 2:
                pos = servoM;
            case 3:
                pos = servoD;
        }

        while(Math.abs(pos-robot.rServo.getPosition()) > 0.01 && opModeIsActive()){
            if (robot.rServo.getPosition() > pos) {
                robot.rServo.setPosition(robot.rServo.getPosition()-0.01);
            } else {
                robot.rServo.setPosition(robot.rServo.getPosition()+0.01);
            }
        }

    }
}