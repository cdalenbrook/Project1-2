package minigolf;

import Graphics.MapDrawer;
import Graphics.CameraController;
import Graphics.OptionsPane;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 * This is the main class, with which we start the Application.
 * @author Jordan
 * @version 1.1
 * @date 20.03
 */
public class MiniGolf extends Application {
    /**
     * Creates an instance of MapDrawer and OptionsPane, then they are being added to
     * the scene, used by the primaryStage.
     * @param primaryStage primaryStage being visualised
     */
    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();
        //an instance of MapDrawer
        MapDrawer map = new MapDrawer();
        //add map to the pane
        pane.add(map, 0, 0);
        //add OptionsPane instance to pane
        pane.add(new OptionsPane(map), 0, 1);
        //allgin pane
        pane.setAlignment(Pos.TOP_CENTER);
        
        //Create a scene from pane, with dimensions, 1000 and 900. The rest is
        //needed for the 3D Graphics and rotations, because otherwise, the way things are seen changes.
        Scene scene = new Scene(pane, 1000, 900, true, SceneAntialiasing.BALANCED);
        //Add an EventFilter to the Scene with options for moving the camera
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new CameraController(map));
        
        //Create a primary stage - basically the window.
        primaryStage.setTitle("Crazy Putting!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * Main static method
     * @param args 
     */
    public static void main(String[] args) {
        launch(args);
    }
}
