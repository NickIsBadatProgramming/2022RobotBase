// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveGroup extends SubsystemBase{
    //get all swerve modules
    SwerveUnit moduleFR1;
    SwerveUnit moduleFL2;
    SwerveUnit moduleBL3;
    SwerveUnit moduleBR4;

    public SwerveGroup(SwerveUnit moduleFR1, SwerveUnit moduleFL2, SwerveUnit moduleBL3, SwerveUnit moduleBR4) {
        this.moduleFR1 = moduleFR1;
        this.moduleFL2 = moduleFL2;
        this.moduleBL3 = moduleBL3;
        this.moduleBR4 = moduleBR4;
    }

    




  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
