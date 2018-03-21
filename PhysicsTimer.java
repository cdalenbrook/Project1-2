//import java.util.Scanner;
import java.util.Timer;
//import javax.swing.event.*;
//import java.awt.*;
//import java.awt.event.*;
import java.util.TimerTask;
//import java.awt.Graphics;

public class PhysicsTimer extends Physics{
  public double newVelocity;
  public double gravityForceOnY = 0, gravityForceOnX = 0, gravityForceOnZ = 0;
  public double gravityForceOnZ_X = 0, gravityForceOnZ_Y = 0;
  public double gravityForceOnBallX = 0, gravityForceOnBallY = 0;

  Timer timer = new Timer();
  TimerTask task = new TimerTask(){
    public void run(){

        slopeAngleX = getSlopeAngle(getXSlope(xCoordinate));
        if(slopeAngleX < 0){
          gravity = 9.81;
        }else{
          gravity = -9.81;
        }

      slopeAngleY = getSlopeAngle(getYSlope(yCoordinate));
      System.out.println("X Coordinate = " + xCoordinate);
      System.out.println("Y Coordinate: " + yCoordinate);
      System.out.println("Z Coordinate: " + zCoordinate);

      System.out.println("X Velocity: " + xVelocity);
      System.out.println("Y Velocity: " + yVelocity);
      System.out.println("Z Velocity: " + zVelocity);

      System.out.println("");
      gravityForceOnBallX = (gravity)*Math.sin(slopeAngleX);
      gravityForceOnBallY = (gravity)*Math.sin(slopeAngleY);
      gravityForceOnX = (gravityForceOnBallX)*Math.sin(slopeAngleX);
      gravityForceOnY = (gravityForceOnBallY)*Math.cos(slopeAngleY);

      gravityForceOnZ_X = (gravityForceOnBallX)*Math.cos(slopeAngleX);
      gravityForceOnZ_Y = (gravityForceOnBallY)*Math.cos(slopeAngleY);

      gravityForceOnZ = gravityForceOnZ_X + gravityForceOnZ_Y;

      zOld = zVelocity;
      zVelocity = zVelocity + getAcceleration(gravityForceOnZ)*delay;
      yOld = yVelocity;
      yVelocity = yVelocity + getAcceleration(gravityForceOnY)*delay;
      xOld = xVelocity;
      xVelocity = xVelocity + getAcceleration(gravityForceOnX)*delay;

        //xVelocity= legX*Math.sin(angle);
        xChange=((xOld + xVelocity)*delay)/2;                 // will change when we include friction because v wont be u
        xCoordinate += xChange;
        yChange=((yOld + yVelocity)*delay)/2;                 // will change when we include friction because v wont be u
        yCoordinate += yChange;
        zChange=((zOld + zVelocity)*delay)/2;
        zCoordinate += zChange;
      /*  if(zCoordinate < 0){ //no more z component, only y and x
          zCoordinate = 0;
          zVelocity = 0;
        }*/
    }
  };

  public void start(){
    timer.scheduleAtFixedRate(task, 1, 1);
  }

  public double getSlopeAngle(double slope){
    return radiansToDegrees(Math.atan(slope));
  }

  public double getAcceleration(double force){
    return force/MASS_OF_BALL; //in m*s^2
  }

  public double getXSlope(double xCoordinate){
    return (0.1 + 0.06*(xCoordinate));
  }

  public double getYSlope(double yCoordinate){
    return 0.2;
  }
}
