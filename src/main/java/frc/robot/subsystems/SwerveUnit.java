package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

public class SwerveUnit extends SubsystemBase {


  TalonFX driveMotor, steerMotor;
  CANCoder magEncoder;


  private double circumferenceM;
  private boolean EMERGENCY_STOP = false;
  private boolean reversedDrive = false;
  private boolean invertedSteer = false;

  double driveMotorSpeed = 0, steerMotorSpeed = 0;
  double rawAngle, angleOffset;


  /** Creates a new SwerveUnit. */

  public SwerveUnit(double circumferenceM, boolean invertedDrive, boolean invertedSteer, double angleOffset) {
   this.circumferenceM = circumferenceM;
   this.reversedDrive = !invertedDrive;
   this.invertedSteer = invertedSteer;
   this.angleOffset = angleOffset;
  }

  public double GetClosestAngle (double desiredAngle, double currentAngle) {
    double difference;
    if(!invertedSteer) {
      difference = desiredAngle - currentAngle;
      if(Math.abs(difference) >= 180) {
        difference = 360 + difference;
        this.reversedDrive = !this.reversedDrive;
      }
    } else {
      difference = desiredAngle - currentAngle;
      if(Math.abs(difference) >= 180) {
        difference = difference -360;
        this.reversedDrive = !this.reversedDrive;
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

      this.driveMotorSpeed = ((60 * speedM_S)/this.circumferenceM) * Constants.SwerveConstants.SWERVE_GEAR_RATIO_DRIVE;
      if(invertedSteer) {
        this.driveMotorSpeed = this.driveMotorSpeed * -1;
      }
      this.steerMotorSpeed = ((60 * rotationDegrees)/(360 * Constants.SwerveConstants.MODULE_TURN_TIME_SECONDS)) * Constants.SwerveConstants.SWERVE_GEAR_RATIO_STEER;
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
    rawAngle = magEncoder.getPosition() - this.angleOffset;
    if(rawAngle < 0) {
      rawAngle = 360 + rawAngle;
    }
    driveMotor.set(ControlMode.Velocity, Constants.SwerveConstants.RPMToFalconVelocity(this.driveMotorSpeed));
    steerMotor.set(ControlMode.Velocity, Constants.SwerveConstants.RPMToFalconVelocity(this.steerMotorSpeed));
  }


  @Override

  public void simulationPeriodic() {

    // This method will be called once per scheduler run during simulation

  }

}
