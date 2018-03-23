package Physics;
//import java.util.Scanner;
import Graphics.Game2D;
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
  public double oldX, oldY, oldZ;

  Timer timer = new Timer();
  TimerTask task = new TimerTask(){ 
    public void run(){

        slopeAngleX = getSlopeAngle(getXSlope(xCoordinate));
        slopeAngleY = getSlopeAngle(getYSlope(yCoordinate));

       game.getBall().setCenterX(game.getBall().getCenterX() - xCoordinate*game.getAmplification());
       game.getBall().setCenterY(game.getBall().getCenterY() - yCoordinate*game.getAmplification());

      System.out.println("X Coordinate = " + xCoordinate);
      System.out.println("Y Coordinate: " + yCoordinate);
      System.out.println("Z Coordinate: " + zCoordinate);
      System.out.println("X VELOCITY = " + xVelocity);
      System.out.println("Y VELOCITY = " + yVelocity);
      System.out.println("Z VELOCITY = " + zVelocity);

      System.out.println("");
      gravityForceOnBallX = (gravity*MASS_OF_BALL)*Math.sin(slopeAngleX);
      gravityForceOnBallY = (gravity*MASS_OF_BALL)*Math.sin(slopeAngleY);
      //gravityForceOnX = gravityForceOnBallX;
      //gravityForceOnY = gravityForceOnBallY;
      gravityForceOnX = (gravityForceOnBallX)*Math.sin(slopeAngleX);
      gravityForceOnY = (gravityForceOnBallY)*Math.cos(slopeAngleY);

      gravityForceOnZ_X = (gravityForceOnBallX)*Math.cos(slopeAngleX);
      gravityForceOnZ_Y = (gravityForceOnBallY)*Math.cos(slopeAngleY);

      gravityForceOnZ = gravityForceOnZ_X + gravityForceOnZ_Y;

      oldZ = zVelocity;
      zVelocity = ((zVelocity + getAcceleration(gravityForceOnZ)*delay) - 0.1*(zVelocity + getAcceleration(gravityForceOnZ)*delay));
      oldY = yVelocity;
      yVelocity = ((yVelocity + getAcceleration(gravityForceOnY)*delay) - 0.1*(yVelocity + getAcceleration(gravityForceOnY)*delay));
      oldX = xVelocity;
      xVelocity = ((xVelocity + getAcceleration(gravityForceOnX)*delay) - 0.1*(xVelocity + getAcceleration(gravityForceOnX)*delay));

      xChange=((xVelocity+oldX)*delay)/2;
      xCoordinate += xChange/5000;
      yChange=((yVelocity+oldY)*delay)/2;
      yCoordinate += yChange/5000;
      zCoordinate = 0.1*(xCoordinate)+0.03*(xCoordinate*xCoordinate)+0.2*(yCoordinate);
    }
  };

   public PhysicsTimer(double totalVelocity, double angle, Game2D game) {
        super(totalVelocity, angle, game);
   }


  public void start(){
    timer.scheduleAtFixedRate(task, 1, delay);
  }

  public double getSlopeAngle(double slope){
    return radiansToDegrees(Math.abs(Math.atan(slope)));
  }

  public double getAcceleration(double force){
    return force/MASS_OF_BALL; //in m*s^2
  }

  public double getXSlope(double xCoordinate){
    return (0.1 + (0.06*xCoordinate));
  }

  public double getYSlope(double yCoordinate){
    return 0.2;
  }
  public void stop(){
      timer.cancel();
  }
  
}
