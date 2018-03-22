package Graphics;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.lang.Math;

import static java.lang.Math.tan;

public class Game2D extends Parent{
    private Circle ball;
    private Circle finish;
    private Line line;
    private int amplification;
    private double angleVal;
    public double lengthX;

    public Game2D(double radius, int amplification){
        this.amplification = amplification;
        ball = new Circle(radius);
        ball.setFill(Color.WHITE);

        // pos_x at angle, pos_y at angle
        lengthX= tan(angleVal)*50;
        line = new Line(ball.getCenterX(), ball.getCenterY(), ball.getCenterX()+lengthX, ball.getCenterY()+50);
        line.setFill(Color.BLACK);

        finish = new Circle((0.02)*amplification);
        finish.setFill(Color.RED);
        getChildren().addAll(ball, line, finish);
    }
    public Circle getBall(){
        return ball;
    }
    public Circle getFinish(){
        return finish;
    }
    public int getAmplification(){
        return amplification/2;
    }

    public void setAngle(double angle){
        angleVal = angle;
    }
}
