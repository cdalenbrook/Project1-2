import java.util.Scanner;
import javax.swing.Timer;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
import javax.swing.*;



public class Physics{

  final public static double delay = 0.001;
  public static double totalVelocity, angle;
  public static double xVelocity, yVelocity;
  public static double xCoordinate = 0, yCoordinate = 0;
  public static double xChange, yChange;
  public static double oldXVelocity, oldYVelocity;
  final public static double LIMIT = 0.0001;
  public static JFrame frame;
  public static JLabel label, label2;
  public static JTextField text, text2;
  public static JPanel panel, panel2, panel3;
  public static JButton button;



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
        totalVelocity = Integer.valueOf(text.getText());
        angle = Integer.valueOf(text2.getText());
        xVelocity=(Math.abs(totalVelocity*Math.sin(radiansToDegrees(angle))));
        yVelocity=(Math.abs(totalVelocity*Math.cos(radiansToDegrees(angle))));
        System.out.println("TOTAL VELOCITY = " + totalVelocity + "m/s");
        System.out.println("ANGLE OF SHOT = " + angle + "°");
        PhysicsListener timerlistener = new PhysicsListener();
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
