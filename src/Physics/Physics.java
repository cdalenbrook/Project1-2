package Physics;

import Graphics.Game2D;



public class Physics{

  final public static double delay = 10;
  public static double totalVelocity, angle;
  public static double xVelocity, yVelocity, zVelocity;
  public static double xCoordinate = 0, yCoordinate = 0, zCoordinate = 0;
  public static double xChange, yChange, zChange;
  public static final double MASS_OF_BALL = 0.045;
  public static  double gravity = -9.81;
  public static double slopeAngleX = 0, slopeAngleY = 0;
  //public static double slope = 2;
  public static double legX, legY;
  public double zOld = 0, xOld = 0, yOld = 0;
  public static Game2D game;

  public Physics(double totalVelocity, double angle, Game2D game){
      this.totalVelocity = totalVelocity/100;
      this.angle = angle;
      this.game = game;
      
  }
 public void startMoving(){
        xCoordinate = 0;
        yCoordinate = 0;
        zCoordinate = 0;
        zVelocity = 0;
        System.out.println("TOTAL VELOCITY = " + totalVelocity + "m/s");
        System.out.println("ANGLE OF SHOT = " + angle + "°");
        PhysicsTimer timerlistener = new PhysicsTimer(totalVelocity, angle, game);
        legX = totalVelocity*Math.cos(timerlistener.getSlopeAngle(timerlistener.getXSlope(xCoordinate))); //0.995
        legY = totalVelocity*Math.cos(timerlistener.getSlopeAngle(timerlistener.getYSlope(yCoordinate))); //0.981
        xVelocity=legX*Math.sin(timerlistener.getXSlope(xCoordinate)) + legY*Math.sin(timerlistener.getXSlope(xCoordinate));
        yVelocity=legY*Math.cos(timerlistener.getYSlope(yCoordinate)) + legX*Math.cos(timerlistener.getYSlope(yCoordinate));
        zVelocity= (totalVelocity*Math.sin(timerlistener.getSlopeAngle(timerlistener.getXSlope(xCoordinate))) + totalVelocity*Math.sin(timerlistener.getSlopeAngle(timerlistener.getYSlope(yCoordinate))));
        timerlistener.start();
 }
  public static double radiansToDegrees(double degree){
    return ((degree*Math.PI)/180);
  }
}
