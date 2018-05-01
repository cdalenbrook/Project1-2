package Physics;

public class Physics {

  final public static int delay = 1;
  public static double totalVelocity, angle;
  public static double xVelocity, yVelocity, zVelocity;
  public static double xCoordinate = 0, yCoordinate = 0, zCoordinate = 0;
  public static double xChange, yChange, zChange;
  public static final double MASS_OF_BALL = 0.045;
  public static double gravity = -9.81;
  public static double slopeAngleX, slopeAngleY;
  
  public static PhysicsTimer timerlistener;

  public Physics(double totalVelocity, double angle){
      this.totalVelocity = totalVelocity;
      this.angle = angle;

  }
    public void startMoving(){
        xCoordinate = 0;
        yCoordinate = 0;
        zCoordinate = 0;
        timerlistener = new PhysicsTimer(totalVelocity, angle);
        double xPercentage = (10/9)*(Math.abs(angle));
        double yPercentage = 100-xPercentage;
        double angledSlope = ((xPercentage/100)*(getXSlope(xCoordinate, yCoordinate)))+((yPercentage/100)*(getYSlope(xCoordinate, yCoordinate)));
        double angledSlopeAngle = getSlopeAngle(angledSlope);
        double leg = totalVelocity*Math.cos(angledSlopeAngle);
        xVelocity = leg*Math.sin(angle)/1;
        yVelocity = leg*Math.cos(angle)/1;
        zVelocity = totalVelocity*Math.sin(angledSlopeAngle)/100;
        timerlistener.start();
    }

  public void stop()
  {
      timerlistener.stop();
  }

  public double getXSlope(double xCoordinate, double yCoordinate)
  {
    return (0.1 + (0.06*xCoordinate));
  }

  public double getYSlope(double xCoordinate, double yCoordinate)
  {
    return 0.2;
  }

  public double getSlopeAngle(double slope)
  {
    return Math.atan(slope);
  }

}
