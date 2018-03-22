package Graphics;

import Old.Graphics.*;
import Dynamics.MovementCycle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * This class creates a GridPane with options for shooting the ball.
 * @author Jordan
 * @version 1.0
 * @date 20.03
 */
public class OptionsPane extends GridPane{
   
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
        }});
        
        add(shoot, 1, 0);
        add(force(), 0, 0);
        setHgap(20);
        setAlignment(Pos.CENTER);
    }
    /**
     * Create a BorderPane with options to control force
     * @return BorderPane
     */
    public BorderPane force(){
        BorderPane pane = new BorderPane();
        Label forceText = new Label("Specify force:");
        forceText.setFont(new Font("Arial", 20));
        
        pane.setTop(forceText);
        pane.setAlignment(forceText, Pos.CENTER);

        Slider force = new Slider(0,100,50);
        force.setShowTickLabels(true);
        force.setShowTickMarks(true);
        force.setMajorTickUnit(50);
        force.setMinorTickCount(5);
        force.setBlockIncrement(5);
        force.setFocusTraversable(false);

        Label forceValue = new Label("Force Selected: 50" );
        pane.setBottom(forceValue);
        pane.setAlignment(forceValue, Pos.CENTER);


        force.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                // set force of ball to the force it will be hit with
                forceValue.textProperty().setValue(
                        String.valueOf("Force Selected: " + (int) force.getValue()));
            }
        });

        pane.setCenter(force);
        pane.setMargin(force, new Insets(12,12,12,12));
        
        return pane;
    }
}
