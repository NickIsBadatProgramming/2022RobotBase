// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    
  /************************* DRIVE *************************/
    public static final class SwerveConstants {
        public static final double SWERVE_GEAR_RATIO_DRIVE = 1; // Gear ratio of swerve drive
        public static final double SWERVE_GEAR_RATIO_STEER = 1;
        public static final float MODULE_TURN_TIME_SECONDS = 1;
        public static final int MOTOR_MAX_RPM = 5000;

        public static final double WheelbaseM = 1; //Dimensions for swerve calculation - in meters
        public static final double TrackwidthM = 1;
        public static final double WheelCircumferenceM = 1;

        public static double RPMToFalconVelocity (double inputRPM) {
          return (2048 * inputRPM)/60000;
        }
        public static double CalculateFalloffMultiplier(double inputAngle) {
          inputAngle = Math.abs(inputAngle);
          if(Math.pow(0.95,inputAngle) < 0.1) {
            return 0;
          } else {
            return Math.pow(0.95,inputAngle);
          }
        }
      }

  /************************* DRIVE *************************/
}