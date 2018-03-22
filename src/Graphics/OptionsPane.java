package Graphics;

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
import Physics.Physics;

/**
 * This class creates a GridPane with options for shooting the ball.
 * @author Jordan, Charlotte
 * @version 1.0
 * @date 20.03
 */
public class OptionsPane extends GridPane{
    private Slider velocity;
    private Slider angle;
    
    public OptionsPane(Game2D game){
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
                Physics physics = new Physics(velocity.getValue()*1000, angle.getValue(), game);
                physics.startMoving();
        }});
        
        add(shoot, 1, 0);
        add(force(), 0, 0);
        setHgap(20);
        setAlignment(Pos.CENTER);
    }
    /**
     * Create a BorderPane with options to control velocity and angle
     * @return BorderPane
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

        angle = new Slider(-90,90,0);
        angle.setShowTickLabels(true);
        angle.setShowTickMarks(true);
        angle.setMajorTickUnit(30);
        angle.setMinorTickCount(5);
        angle.setBlockIncrement(5);
        angle.setFocusTraversable(false);

        pane.add(angle, 2, 1);

        Label angleValue = new Label("Angle Selected: 0" );
        pane.setHalignment(angleValue, HPos.CENTER);
        pane.add(angleValue, 2, 2);

        angle.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                // set angle of ball to the angle it will be hit with
                angleValue.textProperty().setValue(
                        String.valueOf("Angle Selected: " + (int) angle.getValue()));
            }
        });
        
        return pane;
    }
}
