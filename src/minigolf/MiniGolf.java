/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minigolf;

import Graphics.MapDrawer;
import Graphics.MovementTest;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author danyp
 */
public class MiniGolf extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        
        MapDrawer map = new MapDrawer();
        MovementTest controls = new MovementTest(map);
        root.getChildren().add(map);
        
        Scene scene = new Scene(root, 800, 600);
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED ,controls);
        
        primaryStage.setTitle("Crazy Putting!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
