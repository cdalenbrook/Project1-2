package Physics;

import Graphics.Graph3D;
import java.util.Timer;
import java.util.TimerTask;

public class PhysicsTimer extends Physics{
  public double newVelocity;
  public double gravityForceOnY = 0, gravityForceOnX = 0, gravityForceOnZ = 0;
  public double gravityForceOnZ_X = 0, gravityForceOnZ_Y = 0;
  public double gravityForceOnBallX = 0, gravityForceOnBallY = 0;
  public double oldX, oldY, oldZ;
  public double calculatedAngleX;
  public double calculatedAngleY;

  Timer timer = new Timer();
  TimerTask task = new TimerTask(){
    public void run(){

        slopeAngleX = getSlopeAngle(getXSlope(xCoordinate, yCoordinate));
        slopeAngleY = getSlopeAngle(getYSlope(xCoordinate, yCoordinate));   
        
        Graph3D.moveBall(xCoordinate, zCoordinate, yCoordinate);

      System.out.println("X Coordinate = " + xCoordinate);
      System.out.println("Y Coordinate: " + yCoordinate);
      System.out.println("Z Coordinate: " + zCoordinate);
      System.out.println("X VELOCITY = " + xVelocity);
      System.out.println("Y VELOCITY = " + yVelocity);
      System.out.println("");

      //oldZ = zVelocity;
      oldY = yVelocity;
      oldX = xVelocity;

      if (slopeAngleX >= 0)
      {
        calculatedAngleX = slopeAngleX;
      }
      else
      {
        calculatedAngleX = 90-slopeAngleX;
      }

      if (slopeAngleY >= 0)
      {
        calculatedAngleY = slopeAngleY;
      }
      else
      {
        calculatedAngleY = 90-slopeAngleY;
      }

      gravityForceOnBallX = (gravity*MASS_OF_BALL)*Math.sin(calculatedAngleX);
      gravityForceOnBallY = (gravity*MASS_OF_BALL)*Math.sin(calculatedAngleY);
      gravityForceOnX = (gravityForceOnBallX)*Math.cos(slopeAngleX);
      gravityForceOnY = (gravityForceOnBallY)*Math.cos(slopeAngleY);

      //gravityForceOnZ_X = (gravityForceOnBallX)*Math.cos(slopeAngleX);
      //gravityForceOnZ_Y = (gravityForceOnBallY)*Math.cos(slopeAngleY);

      //gravityForceOnZ = gravityForceOnZ_X + gravityForceOnZ_Y;

      //zVelocity = ((zVelocity + getAcceleration(gravityForceOnZ)*delay) - 0.1*(zVelocity + getAcceleration(gravityForceOnZ)*delay));
      yVelocity = (((yVelocity + getAcceleration(gravityForceOnY)*delay) - 0*(yVelocity + getAcceleration(gravityForceOnY)*delay))/1);
      xVelocity = (((xVelocity + getAcceleration(gravityForceOnX)*delay) - 0*(xVelocity + getAcceleration(gravityForceOnX)*delay))/1);

      xChange=((xVelocity+oldX)*1)/2;
      xCoordinate += xChange/999999;
      yChange=((yVelocity+oldY)*1)/2;
      yCoordinate += yChange/999999;
      zCoordinate = 0.1*((xCoordinate)+0.03*(Math.pow(xCoordinate, 2)) + 0.2*(yCoordinate));
    }
  };

   public PhysicsTimer(double totalVelocity, double angle) {
        super(totalVelocity, angle);
   }

  public void start(){
    timer.scheduleAtFixedRate(task, 1, delay);
  }

  public double getAcceleration(double force){
    return force/MASS_OF_BALL;
  }

  public void stop(){
      timer.cancel();
  }

}
