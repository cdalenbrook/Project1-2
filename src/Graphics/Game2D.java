package Graphics;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Game2D extends Parent{
    private Circle ball;
    private Circle finish;
    private int amplification;
    public Game2D(double radius, int amplification){
        this.amplification = amplification;
        ball = new Circle(radius);
        ball.setFill(Color.WHITE);
        finish = new Circle((0.02)*amplification);
        finish.setFill(Color.RED);
        getChildren().addAll(ball, finish);
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
}
