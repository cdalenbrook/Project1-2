package Graphics;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import static java.lang.Math.tan;

/**
 * This class creates the main components of the game
 * @author Jordan, Charlotte
 * @version 1.0
 * @date 21.03
 */
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
    /**
     * Getter method for the ball in the game
     * @return ball
     */
    public Circle getBall(){
        return ball;
    }
    /**
     * Getter method for the finish area in the game
     * @return finish
     */
    public Circle getFinish(){
        return finish;
    }
    /**
     * Setter method for the helper line that shows the direction the ball would be shot in
     * @return line
     */
    public Line setLine(){
        lengthX= tan(Math.toRadians(0))*50;
        line.setStartX(ball.getCenterX());
        line.setStartY(ball.getCenterY());
        line.setEndX(ball.getCenterX()+lengthX);
        line.setEndY(ball.getCenterY() - 50);
        line.setFill(Color.BLACK);
        
        return line;
    }


    /**
     * Getter method for the amplification that is used in the game
     * @return amplification
     */
    public int getAmplification(){
        return amplification/2;
    }

    /**
     * Getter method for the amplification that is used in the game
     * @param angle the angle that has been chosen by the user
     */
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

    /**
     * Getter method for the helper line
     * @return line
     */
    public Line getLine(){
        return line;
    }
}
