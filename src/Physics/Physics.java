package Physics;





public class Physics{

  final public static int delay = 1;
  public static double totalVelocity, angle;
  public static double xVelocity, yVelocity, zVelocity;
  public static double xCoordinate = 0, yCoordinate = 0, zCoordinate = 0;
  public static double xChange, yChange, zChange;
  public static final double MASS_OF_BALL = 0.45;
  public static double gravity = -9.81;
  public static double slopeAngleX = 0, slopeAngleY = 0;
  public static double leg;
  public static PhysicsTimer timerlistener;

  public Physics(double totalVelocity, double angle){
      this.totalVelocity = totalVelocity;
      this.angle = -angle;

  }
    public void startMoving(){
        xCoordinate = 0;
        yCoordinate = 0;
        zCoordinate = 0;
        System.out.println("TOTAL VELOCITY = " + totalVelocity + "m/s");
        System.out.println("ANGLE OF SHOT = " + angle + "Â°");
        timerlistener = new PhysicsTimer(totalVelocity, angle);
        zVelocity= (totalVelocity*Math.sin(timerlistener.getSlopeAngle(timerlistener.getXSlope(xCoordinate))) + totalVelocity*Math.sin(timerlistener.getSlopeAngle(timerlistener.getYSlope(yCoordinate))));
        leg=Math.sqrt((totalVelocity*totalVelocity)-(zVelocity*zVelocity));
        xVelocity=leg*Math.sin(angle);
        yVelocity=leg*Math.cos(angle);
        timerlistener.start();
    }
  public static double radiansToDegrees(double degree){
    return ((degree*Math.PI)/180);
  }
  public void stop(){
      timerlistener.stop();
  }

}
