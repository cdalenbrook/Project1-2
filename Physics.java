import java.util.Scanner;
import javax.swing.Timer;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
public class Physics
{
  final public static int delay=1000;
  public static double totalVelocity;
  public static double angle;
  public static double xVelocity;
  public static double yVelocity;
  public static double xCoordinate=0;
  public static double yCoordinate=0;
  public static double xChange;
  public static double yChange;
  public static Timer timer;
  public static double oldXVelocity;
  public static double oldYVelocity;
  final public static double limit=0.0001;
  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
    System.out.println("Enter initial velocity");
    totalVelocity=in.nextDouble();
    System.out.println("Enter angle");
    angle=in.nextInt();
    xVelocity=(Math.abs(totalVelocity*Math.sin(radiansToDegrees(angle))));
    yVelocity=(Math.abs(totalVelocity*Math.cos(radiansToDegrees(angle))));
    PhysicsListener myListener=new PhysicsListener();
    timer=new Timer(delay, myListener);
    while(true)
    {
      System.out.println("running");
      timer.start();
    }
  }
  public static double radiansToDegrees(double degree)
  {
    return ((degree*Math.PI)/180);
  }
}


//slopes function of y and x use calculus
//add exception for illegal angles
