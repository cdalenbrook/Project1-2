package Graphics;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import static java.lang.Math.tan;

public class Game2D{
    private Circle ball;
    private Circle finish;
    private Line line;
    private int amplification;
    public double lengthX;

    public Game2D(double radius, int amplification){
        this.amplification = amplification;
        ball = new Circle(radius);
        ball.setFill(Color.WHITE);

        line = new Line();
        finish = new Circle((0.02)*amplification);
        finish.setFill(Color.RED);
    }
    public Circle getBall(){
        return ball;
    }
    public Circle getFinish(){
        return finish;
    }
    public Line setLine(){
        lengthX= tan(Math.toRadians(0))*50;
        line.setStartX(ball.getCenterX());
        line.setStartY(ball.getCenterY());
        line.setEndX(ball.getCenterX()+lengthX);
        line.setEndY(ball.getCenterY() - 50);
        line.setFill(Color.BLACK);
        
        return line;
    }
    public Line setLineTest(){
        lengthX= tan(Math.toRadians(0))*50;
        line.setStartX(ball.getCenterX());
        line.setStartY(ball.getCenterY());
        line.setEndX(ball.getCenterX()+lengthX);
        line.setEndY(ball.getCenterY() - 50);
        line.setFill(Color.BLACK);
        
        return line;
    }
    public int getAmplification(){
        return amplification/2;
    }
    public void setAngle(double angle){
        if(angle <= 80 && angle >= - 80){
            lengthX= tan(Math.toRadians(angle))*50;
            line.setEndX(ball.getCenterX()+lengthX);
            line.setEndY(ball.getCenterY() - 50);
            line.setFill(Color.BLACK);
        }
        if(angle >= 100 || angle <= -100){
            lengthX= tan(Math.toRadians(angle))*50;
            line.setEndX(ball.getCenterX() - lengthX);
            line.setEndY(ball.getCenterY() + 50);
            line.setFill(Color.BLACK);
        }
    }
    public Line getLine(){
        return line;
    }
}
