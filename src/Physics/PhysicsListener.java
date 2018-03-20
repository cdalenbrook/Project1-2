//import java.util.Scanner;
import java.util.Timer;
//import javax.swing.event.*;
//import java.awt.*;
//import java.awt.event.*;
import java.util.TimerTask;
//import java.awt.Graphics;

public class PhysicsListener extends Physics{

    Timer timer = new Timer();
    TimerTask task = new TimerTask(){
    public void run(){
      if(xVelocity >= 0 && yVelocity >= 0){
      System.out.println("X Coordinate: " + xCoordinate);
      System.out.println("Y Coordinate: " + yCoordinate);
      System.out.println("X Velocity: " + xVelocity);
      System.out.println("Y Velocity: " + yVelocity);
      System.out.println("");
      xVelocity -= 0.1*(Math.abs(totalVelocity*Math.sin(radiansToDegrees(angle))));
      yVelocity -= 0.1*(Math.abs(totalVelocity*Math.cos(radiansToDegrees(angle))));
      xChange=((2*xVelocity)*delay)/2;                      // will change when we include friction because v wont be u
      xCoordinate=xCoordinate+xChange;
      yChange=((2*yVelocity)*delay)/2;                      // will change when we include friction because v wont be u
      yCoordinate=yCoordinate+yChange;
      }else{
        timer.cancel();
      }
    }
  };

  public void start(){
    timer.scheduleAtFixedRate(task, 1, 1);
  }
}
