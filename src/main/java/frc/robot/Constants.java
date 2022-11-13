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


    public static class CommonMethods {
      public static double getSign(double value) {
        return (Math.abs(value)/value);
      }
    }

    
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
    public static class JoystickLimits {
      public static final double X_AXIS_MAX_SPEED_MS = 5; //speeds in meters/second - X and Y should usually be the same
      public static final double Y_AXIS_MAX_SPEED_MS = 5;
      public static final double Z_AXIS_MAX_ROTATION_RS = 0.5; //rotations per second
      public static final double CONTROLLER_DEADZONE = 0.2;

      public static double getXFromJoystickPosition(double value) { //since values of the joystick are from -1 to 1, you need to convert them to their respected extremes
        return value * X_AXIS_MAX_SPEED_MS; //linear return, could always be exponential or quadratic
      }

      public static double getYFromJoystickPosition(double value) {
        return value * Y_AXIS_MAX_SPEED_MS;
      }

      public static double getZFromJoystickPosition(double value) {
        return value * Z_AXIS_MAX_ROTATION_RS;
      }

      //Controller deadzones

      public static double adjustForDeadzone(double value) {
        if(Math.abs(value) > CONTROLLER_DEADZONE) {
          return value;
        } else {
          return 0;
        }

      }


    }

  /************************* DRIVE *************************/
}