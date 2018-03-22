import java.util.Scanner;
import javax.swing.Timer;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
import javax.swing.*;



public class Physics{

  final public static double delay = 1;
  public static double totalVelocity, angle;
  public static double xVelocity, yVelocity, zVelocity;
  public static double xCoordinate = 0, yCoordinate = 0, zCoordinate = 0;
  public static double xChange, yChange, zChange;
  public static JFrame frame;
  public static JLabel label, label2;
  public static JTextField text, text2;
  public static JPanel panel, panel2, panel3;
  public static JButton button;
  public static final double MASS_OF_BALL = 0.045;
  public static  double gravity = -9.81;
  public static double slopeAngleX = 0, slopeAngleY = 0;
  //public static double slope = 2;
  public static double legX, legY;
  public double zOld = 0, xOld = 0, yOld = 0;



  public static void main(String[] args){
    button = new JButton("shoot");
    frame = new JFrame("Test");
    frame.setSize(400,100);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    label = new JLabel("Edit Velocity");
    label2 = new JLabel("Edit Angle");
    text = new JTextField();
    text.setColumns(4);
    text2 = new JTextField();
    text2.setColumns(4);
    panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel2 = new JPanel();
    panel2.add(label);
    panel2.add(text);
    panel2.add(label2);
    panel2.add(text2);
    panel.add(panel2, BorderLayout.CENTER);
    panel3 = new JPanel();
    panel3.add(button);
    panel.add(panel3, BorderLayout.SOUTH);
    frame.add(panel);
    button.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        xCoordinate = 0;
        yCoordinate = 0;
        zCoordinate = 0;
        zVelocity = 0;
        totalVelocity = Integer.valueOf(text.getText());
        angle = Double.valueOf(text2.getText());
        System.out.println("TOTAL VELOCITY = " + totalVelocity + "m/s");
        System.out.println("ANGLE OF SHOT = " + angle + "°");
        PhysicsTimer timerlistener = new PhysicsTimer();
        legX = totalVelocity*Math.cos(timerlistener.getSlopeAngle(timerlistener.getXSlope(xCoordinate))); //0.995
        legY = totalVelocity*Math.cos(timerlistener.getSlopeAngle(timerlistener.getYSlope(yCoordinate))); //0.981
        xVelocity=legX*Math.sin(timerlistener.getXSlope(xCoordinate)) + legY*Math.sin(timerlistener.getXSlope(xCoordinate));
        yVelocity=legY*Math.cos(timerlistener.getYSlope(yCoordinate)) + legX*Math.cos(timerlistener.getYSlope(yCoordinate));
        zVelocity= (totalVelocity*Math.sin(timerlistener.getSlopeAngle(timerlistener.getXSlope(xCoordinate))) + totalVelocity*Math.sin(timerlistener.getSlopeAngle(timerlistener.getYSlope(yCoordinate))));
        timerlistener.start();
      }
    });
    frame.setVisible(true);
  }
  public static double radiansToDegrees(double degree){
    return ((degree*Math.PI)/180);
  }
}
//slopes function of y and x use calculus
//add exception for illegal angles
