// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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

    public static class FieldConstants extends SubsystemBase { //theoretically this could be moved to subsystems, but I like it here
      //Declare Gyro in here
      public static double GYRO_ZERO = 0; //For zeroing the gyro
      public static double TEMP_GYRO_VALUES = 0; //Replace with gyro object
      public static boolean USING_FIELD = false; //on startup the robot will be robot-oriented until turned on

      public static double vxr; //X and Y velocities relative to the robot. 
      public static double vyr;

      public static boolean ZERO_ON_CHANGE = true; //This zeroes the field-orientation to the forward direction of the robot every time it's turned on

      public static double GYRO_READ; 

      

      public static void changeOrientation(boolean value) {
        USING_FIELD = value; //input specifies directly whether it should be on or off
        if(ZERO_ON_CHANGE) {
          zero();
        }
      }

      public static void changeOrientation() {
        USING_FIELD = !USING_FIELD; //When there is no input it just reverses
        if(ZERO_ON_CHANGE) {
          zero();
        }
      }

      public static void zero() {
        GYRO_ZERO = TEMP_GYRO_VALUES;
      }

      //Converting two drive velocities from feild-oriented to robot-oriented
      //Rotation is not included in here as rotation will always be robot-oriented when changed

      public static void getFieldVelocities(double vxf, double vyf) {
        if(USING_FIELD) {
            double theta = TEMP_GYRO_VALUES + Math.atan(vxf/vyf);
            double vf = Math.sqrt(Math.pow(vxf,2)+Math.pow(vyf,2));
            vxr = vf * Math.sin(theta);
            vyr = vf * Math.cos(theta);

        } else {
          vxr = vxf;
          vyr = vyf;
        }
      }

      @Override //Here the gyro readout is periodically refreshed
      public void periodic() {
        GYRO_READ = TEMP_GYRO_VALUES - GYRO_ZERO; 
      }

    }

  /************************* DRIVE *************************/
}