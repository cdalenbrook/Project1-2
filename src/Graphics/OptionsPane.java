package Graphics;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * This class creates a GridPane with options for shooting the ball.
 * @author Jordan, Charlotte
 * @version 1.0
 * @date 20.03
 */
public class OptionsPane extends GridPane{
    private Slider velocity;
    private Slider angle;
    private Game2D game;
    
    private int amplification;
    private int rangeX;
    private int rangeY;
    
    public OptionsPane(){
        //create a button
        Button shoot = new Button("Shoot");
        //some css style thins, font is 22 Arial, base is button color
        shoot.setStyle("-fx-font: 22 arial; -fx-base: #6495ED");
        //setsize
        shoot.setMinSize(225, 50);
        //using keys like up down etc., won't trigger the button
        shoot.setFocusTraversable(false);
        //add action to the button
        shoot.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                double startX = game.getBall().getCenterX();
                double startY = game.getBall().getCenterY();
                
                Timeline timeline = new Timeline();
                timeline.setCycleCount(1);
                
                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long l){
                        double x = game.getBall().getCenterX() - amplification;
                        double y = game.getBall().getCenterY() - amplification;
                        double height = ((0.1*x) + (0.03*(Math.pow(x, 2.0))) + (0.2*y));
                        System.out.println(height);
                        if(height < 0){
                            
                            timeline.stop();
                            game.getBall().setCenterX(startX);
                            game.getBall().setCenterY(startY);
                            game.setLine();
                            
                        }
                    }
                };
                
                KeyValue keyValueX = new KeyValue(game.getBall().centerXProperty(), game.getLine().getEndX());
                KeyValue keyValueY = new KeyValue(game.getBall().centerYProperty(), game.getLine().getEndY());
                
                EventHandler onFinished = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t){
                        game.setLine();
                    }
                };
                Duration duration = Duration.millis(2000);
                
                KeyFrame keyFrame = new KeyFrame(duration, onFinished, keyValueX, keyValueY);
 
                timeline.getKeyFrames().add(keyFrame);
 
                timeline.play();
                timer.start();
        }});
        
        add(shoot, 1, 0);
        add(force(), 0, 0);
        setHgap(20);
        setAlignment(Pos.CENTER);
        setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, new Insets(15, 15, 15, 15))));
        
    }
    public void setData(int amplification, int rangeX, int rangeY){
        this.amplification = amplification;
        this.rangeX = rangeX;
        this.rangeY = rangeY;
    }
    /**
     * Create a GridPane with options to control velocity and angle
     * @return GridPane
     */
    public GridPane force(){
        GridPane pane = new GridPane();
        pane.setHgap(20); //horizontal gap in pixels => that's what you are asking for
        pane.setVgap(20); //vertical gap in pixels
        pane.setPadding(new Insets(10, 10, 10, 10));

        //Velocity text and slider:
        Label velocityText = new Label("Specify Velocity:");
        velocityText.setFont(new Font("Arial", 20));

        pane.add(velocityText, 1, 0);
        pane.setHalignment(velocityText, HPos.CENTER);

        velocity = new Slider(0,10,5);
        velocity.setShowTickLabels(true);
        velocity.setShowTickMarks(true);
        velocity.setMajorTickUnit(2);
        velocity.setMinorTickCount(1);
        velocity.setBlockIncrement(1);
        velocity.setFocusTraversable(false);

        pane.add(velocity, 1, 1);

        Label velocityValue = new Label("Velocity Selected: 5" );
        pane.setHalignment(velocityValue, HPos.CENTER);
        pane.add(velocityValue, 1, 2);


        velocity.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                // set force of ball to the force it will be hit with
                velocityValue.textProperty().setValue(
                        String.valueOf("Velocity Selected: " + (int) velocity.getValue()));
            }
        });


        //Angle text and slider:
        Label angleText = new Label("Specify Angle:");
        angleText.setFont(new Font("Arial", 20));

        pane.add(angleText, 2, 0);
        pane.setHalignment(angleText, HPos.CENTER);

        angle = new Slider(-180,180,0);
        angle.setShowTickLabels(true);
        angle.setShowTickMarks(true);
        angle.setMajorTickUnit(30);
        angle.setMinorTickCount(5);
        angle.setBlockIncrement(5);
        angle.setFocusTraversable(false);

        pane.add(angle, 2, 1);

        Label angleValue = new Label("Angle Selected: 0°" );
        pane.setHalignment(angleValue, HPos.CENTER);
        pane.add(angleValue, 2, 2);

        angle.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                // set angle of ball to the angle it will be hit with
                angleValue.textProperty().setValue(
                        String.valueOf("Angle Selected: " + (int) angle.getValue() + "°"));
                game.setAngle(angle.getValue());
            }
        });
        
        return pane;
    }
    public void setGame(Game2D game){
        this.game = game;
    }
}
