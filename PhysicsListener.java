import java.util.Scanner;
import javax.swing.Timer;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
public class PhysicsListener extends Physics implements ActionListener
{
  public void actionPerformed(ActionEvent e)
  {
    System.out.println("X Coordinate: "+xCoordinate);
    System.out.println("Y Coordinate: "+yCoordinate);
    System.out.println("X Velocity: "+xVelocity);
    System.out.println("Y Velocity: "+yVelocity);
    System.out.println("");

    xVelocity -= 0.1*(Math.abs(totalVelocity*Math.sin(radiansToDegrees(angle))));
    yVelocity-=0.1*(Math.abs(totalVelocity*Math.cos(radiansToDegrees(angle))));
    xChange=((2*xVelocity)*delay)/2;                      // will change when we include friction because v wont be u
    xCoordinate=xCoordinate+xChange;
    yChange=((2*yVelocity)*delay)/2;                      // will change when we include friction because v wont be u
    yCoordinate=yCoordinate+yChange;
  }
}
