package frc.robot.subsystems;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

//This is a special Accelerometer that should be created when starting to track a displacement and discarded when finished. 
//Since this is developed especially for commands during autonomous, most times this will automatically be discarded when 
//the command has exited. Make sure that you have a way to delete every new object each time you use it (setting it to null will work)
//otherwise it will fill up the memory. You can zero it, but it's not necessary when initializing the object. 

public class DisplacementTracker extends Accelerometer{

    double xDisplacement;
    double yDisplacement;

    double initVX, initVY; //not sure if these are neccesary, but are mentioned in vertical kinematics

    public DisplacementTracker(BuiltInAccelerometer bIA) {
        super(bIA);
        zeroVelocity();
        zeroDisplacement();
    }

    public void zeroDisplacement() {
        xDisplacement = 0;
        yDisplacement = 0;
    }



    @Override
    public void periodic() {
        //Since this overwrites the Accelerometer code we need to paste it back in here. 

        currentTimeMS = Constants.Time.nanoToMilli(System.nanoTime() - timerZero);
        filteredX = xAccel.calculate(myAccelerometer.getX());
        filteredY = yAccel.calculate(myAccelerometer.getX());

        if(System.nanoTime() - interval >= 10000000 && System.nanoTime() - interval <= 0) { //Every ten milliseconds (the zero is there in case the cpu overflows or something bugs out)
            interval = System.nanoTime();
            velocityX = velocityX + filteredX * 0.01; //vertical kinematics; calculating velocites from accelerations. 
            velocityY = velocityY + filteredY * 0.01; //The 0.001 is just multiplying the acceleration (m/s/s) by the elapsed seconds (0.01 for 10 milliseconds) to get m/s

            //new displacement code performed every 10 milliseconds
            xDisplacement = xDisplacement + ((initVX + velocityX)/2)*0.01;
            yDisplacement = yDisplacement + ((initVY + velocityY)/2)*0.01;
        }


        if(System.nanoTime() - interval <= 0) { //When this becomes negative, it's because of a bug. we just discard it and reset the clock. 
            interval = System.nanoTime();
        }

        SmartDashboard.putNumber("X Displacement (Interpolated)" , xDisplacement); //We should already get outputs of velocity and acceleration from Accelerometer so no need to do it here. 
        SmartDashboard.putNumber("Y Displacement (Interpolated)", yDisplacement);
        


    }

}
