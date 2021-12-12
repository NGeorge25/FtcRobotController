package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

public class TeleOpObjectiveLogic {


    RobotHardware robot;
    ObjectiveController objective;

    //Variables
        private int servoPosition= 1;

        private boolean aPrev = false;
        private boolean bPrev = false;
        private boolean xPrev = false;

        private boolean dUpPrev = false;
        private boolean dDownPrev = false;

        private boolean aMotor = false;
        private boolean bMotor = false;
        private boolean xMotor = false;

        private boolean ArmActive = false;

        private double rPosition = (RobotHardware.MAX_POS - RobotHardware.MIN_POS) * 0.6;
    //Variables




    public TeleOpObjectiveLogic(RobotHardware r, ObjectiveController o){robot = r; objective = o;robot.telemetry.addData("here", "Here2");robot.telemetry.update();}

    public void aButton(){
        if(!aPrev){
            aMotor = !aMotor;
        }
    }

    public void bButton(){
        if(!bPrev){
            bMotor = !bMotor;
        }
    }

    public void xButton(){
        if(!xPrev){
            xMotor = !xMotor;
        }
    }

    public void yButton(){

    }

    public void armUp(){
        ArmActive = true;
        robot.Arm.setPower(0.5);
    }

    public void armDown(){
        ArmActive = true;
        robot.Arm.setPower(0);
    }

    public void servoMid(){ rPosition = (RobotHardware.MAX_POS - RobotHardware.MIN_POS) * 0.6; }

    public void servoBottom(){
        rPosition = (RobotHardware.MAX_POS - RobotHardware.MIN_POS);
    }

    public void servoDump(){
        rPosition = 0;
    }

    public void incrementServo(){
        if(!dUpPrev) {
            if (servoPosition < 2) {
                servoPosition++;
            }
            assignServo(servoPosition);
        }
    }
    public void decrementServo(){
        if(!dDownPrev) {
            if (servoPosition > 0) {
                servoPosition--;
            }
            assignServo(servoPosition);
        }
    }

    private void assignServo(int pos){
        switch(pos){
            case 0:
                servoBottom();
                break;

            case 1:
                servoMid();
                break;

            case 2:
                servoDump();
                break;
        }
    }

    public void setStates(Gamepad g){
        aPrev = g.a;
        bPrev = g.b;
        xPrev = g.x;
        dUpPrev = g.dpad_up;
        dDownPrev = g.dpad_down;
        if(g.left_stick_y > -0.1 && g.left_stick_y < 0.1){ArmActive=false;}

    }

    public void updateMotors(){
        if(!ArmActive){
            if(robot.Arm != null) {
                robot.Arm.setPower(0.1);
            }
        } else {
            ArmActive = false;
        }

        if(bMotor){
            robot.Duck.setPower(0.5);
        }else if(xMotor){
            robot.Duck.setPower(-0.5);
        }else{
            robot.Duck.setPower(0);
        }

        if(aMotor){
            robot.IN.setPower(0.5);
        }else{
            robot.IN.setPower(0);
        }

        robot.rServo.setPosition(rPosition);
        robot.lServo.setPosition(1-rPosition);
        robot.telemetry.addData("Right Postion: ", rPosition);
        robot.telemetry.update();


    }

}
