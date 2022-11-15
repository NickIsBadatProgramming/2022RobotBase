package frc.robot.subsystems;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Accelerometer extends SubsystemBase {

    BuiltInAccelerometer myAccelerometer;
    double filteredX;
    double filteredY;
    double velocityX;
    double velocityY;
    long timerZero;
    long currentTimeMS;
    long interval = System.nanoTime();
    
    public Accelerometer(BuiltInAccelerometer myAccelerometer) {
        this.myAccelerometer = myAccelerometer;
    }
    
    //Get the moving average of the filters so that the acceleration isn't so noisy
    LinearFilter xAccel = LinearFilter.movingAverage(10);
    LinearFilter yAccel = LinearFilter.movingAverage(10);


    //Zero the current robot velocity. 

    public void zeroVelocity() { //Needed before we can do calculations for travel distance. 
        velocityX = 0;
        velocityY = 0;
    }

    public void resetTimer() { //adds a zero to an internal timer
        timerZero = System.nanoTime();
    }

    @Override 
    public void periodic() {
        currentTimeMS = Constants.Time.nanoToMilli(System.nanoTime() - timerZero);
        filteredX = xAccel.calculate(myAccelerometer.getX());
        filteredY = yAccel.calculate(myAccelerometer.getX());

        if(System.nanoTime() - interval >= 10000000 && System.nanoTime() - interval <= 0) { //Every ten milliseconds (the zero is there in case the cpu overflows or something bugs out)
            interval = System.nanoTime();
            velocityX = velocityX + filteredX * 0.01; //vertical kinematics; calculating velocites from accelerations. 
            velocityY = velocityY + filteredY * 0.01; //The 0.001 is just multiplying the acceleration (m/s/s) by the elapsed seconds (0.01 for 10 milliseconds) to get m/s
        }

        if(System.nanoTime() - interval <= 0) { //When this becomes negative, it's because of a bug. we just discard it and reset the clock. 
            interval = System.nanoTime();
        }
    }
}
