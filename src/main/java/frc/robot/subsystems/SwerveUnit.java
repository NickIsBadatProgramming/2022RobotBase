package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SwerveConstants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

public class SwerveUnit extends SubsystemBase {


  TalonFX driveMotor, steerMotor;
  CANCoder magEncoder;


  private double circumferenceM;
  private boolean EMERGENCY_STOP = false;
  private boolean invertedDrive;
  private boolean invertedSteer;
  private String moduleName;
  double driveMotorSpeed = 0, steerMotorSpeed = 0;
  double rawAngle;


  /** Creates a new SwerveUnit. */

  public SwerveUnit(double circumferenceM, boolean invertedDrive, boolean invertedSteer, TalonFX driveMotor, TalonFX steerMotor, CANCoder magEncoder, String moduleName) {
   this.circumferenceM = circumferenceM;
   this.invertedDrive = invertedDrive;
   this.invertedSteer = invertedSteer;
   this.driveMotor = driveMotor; //Get the motor configs from a superceding class
   this.steerMotor = steerMotor;
   this.magEncoder = magEncoder;
   this.moduleName = moduleName;
  }




  public double GetClosestAngle (double desiredAngle, double currentAngle) { //See if it's better to turn forwards and keep positive direction or turn backwards and reverse direction
    double difference;
    if(!invertedSteer) {
      difference = desiredAngle - currentAngle;
      if(Math.abs(difference) >= 180) {
        difference = 360 + difference;
        this.invertedDrive = !this.invertedDrive;
      }
    } else {
      difference = desiredAngle - currentAngle;
      if(Math.abs(difference) >= 180) {
        difference = difference -360;
        this.invertedDrive = !this.invertedDrive;
      }
    }
    return difference;
  }


  public void GetMotorValues(double speedM_S, double desiredAngle) {
    if(EMERGENCY_STOP) {
      this.driveMotorSpeed = 0;
      this.steerMotorSpeed = 0;
    }
    else {
      double rotationDegrees = GetClosestAngle(desiredAngle, this.rawAngle);

      this.driveMotorSpeed = ((60 * speedM_S)/this.circumferenceM) * SwerveConstants.SWERVE_GEAR_RATIO_DRIVE;
      if(invertedDrive) {
        this.driveMotorSpeed = this.driveMotorSpeed * -1;
      }
      this.driveMotorSpeed = this.driveMotorSpeed * SwerveConstants.CalculateFalloffMultiplier(GetClosestAngle(desiredAngle, this.rawAngle));
      this.steerMotorSpeed = ((60 * rotationDegrees)/(360 * SwerveConstants.MODULE_TURN_TIME_SECONDS)) * SwerveConstants.SWERVE_GEAR_RATIO_STEER;
    }
  }

  //Just some emergency stop stuff

  public void Halt() {
    EMERGENCY_STOP = true;
  }

  public void ResetHalt() {
    EMERGENCY_STOP = false;
  }


  @Override

  public void periodic() {
    rawAngle = magEncoder.getPosition();
    if(rawAngle < 0) {
      rawAngle = 360 + rawAngle;
    }
    driveMotor.set(ControlMode.Velocity, SwerveConstants.RPMToFalconVelocity(this.driveMotorSpeed));
    steerMotor.set(ControlMode.Velocity, SwerveConstants.RPMToFalconVelocity(this.steerMotorSpeed));
    SmartDashboard.putNumber("Module " + moduleName + "Value:", this.rawAngle);
  }


  @Override

  public void simulationPeriodic() {

    // This method will be called once per scheduler run during simulation

  }

}
