package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.SwerveGroup;
import frc.robot.subsystems.SwerveUnit;

public class RobotContainer {
    //This is kind of the brain of the robot, it controls all subsystems and commands in one hub that is scheduled by the Robot class. 


    
    //Setting up objects

    /* ---- Controllers ---- */
    public static Joystick logitech3d;



    /* ---- Drive ---- */
    public static TalonFX driveMotorFR, steerMotorFR; //Motors for each module
    public static TalonFX driveMotorFL, steerMotorFL;
    public static TalonFX driveMotorBL, steerMotorBL;
    public static TalonFX driveMotorBR, steerMotorBR;


    public static CANCoder cFR, cFL, cBL, cBR;

    double xAxis, yAxis, zAxis;

    /* ---- Individual Swerve Modules ---- */
    SwerveUnit frontRight = new SwerveUnit(Constants.SwerveConstants.WheelCircumferenceM, false, false, driveMotorFR, steerMotorFR, cFR, "Front Right");
    SwerveUnit frontLeft = new SwerveUnit(Constants.SwerveConstants.WheelCircumferenceM, false, false, driveMotorFL, steerMotorFL, cFL, "Front Left");
    SwerveUnit backLeft = new SwerveUnit(Constants.SwerveConstants.WheelCircumferenceM, false, false, driveMotorBL, steerMotorBL, cBL, "Back Left");
    SwerveUnit backRight = new SwerveUnit(Constants.SwerveConstants.WheelCircumferenceM, false, false, driveMotorBR, steerMotorBR, cBR, "Back Right");

    SwerveGroup swerve = new SwerveGroup(frontRight, frontLeft, backRight, backLeft);

    public RobotContainer() {
        logitech3d = new Joystick(0);

    }

    public void refreshPeriodic() {
        this.xAxis = Constants.JoystickLimits.adjustForDeadzone(logitech3d.getRawAxis(1));
        this.yAxis = Constants.JoystickLimits.adjustForDeadzone(-logitech3d.getRawAxis(2));
        this.zAxis = Constants.JoystickLimits.adjustForDeadzone(logitech3d.getRawAxis(3));
    }

    public void haltAllModules() {
        frontRight.Halt();
        frontLeft.Halt();
        backLeft.Halt();
        backRight.Halt();
    }

    public void resetAllModules() {
        frontRight.ResetHalt();
        frontLeft.ResetHalt();
        backLeft.ResetHalt();
        backRight.ResetHalt();
    }

    public void Drive() {
        swerve.Drive(Constants.JoystickLimits.getXFromJoystickPosition(this.xAxis), Constants.JoystickLimits.getYFromJoystickPosition(this.yAxis), Constants.JoystickLimits.getZFromJoystickPosition(this.zAxis));
    }

    



}
