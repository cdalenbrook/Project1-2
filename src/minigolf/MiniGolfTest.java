package minigolf;

import Graphics.CameraController;
import Graphics.Function;
import Graphics.Graph3D;
import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This is the main class, with which we start the Application.
 * @author Jordan
 * @version 1.1
 * @date 20.03
 */
public class MiniGolfTest extends Application {
    
    private double mousePosX, mousePosY;
    private double mouseOldX, mouseOldY;
    //private final Rotate rotateX = new Rotate(20, Rotate.X_AXIS);
    //private final Rotate rotateY = new Rotate(-45, Rotate.Y_AXIS);
    /**
     * Creates an instance of MapDrawer and OptionsPane, then they are being added to
     * the scene, used by the primaryStage.
     * @param primaryStage primaryStage being visualised
     */
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        int startX = -1;
        int endX = 1;
        int startZ = -1;
        int endZ = 1;
        int amplification = 10;
        Function function = new Function(startX, endX, startZ, endZ, amplification);
        Graph3D graph = new Graph3D(startX, endX, startZ, endZ, amplification, function);
        root.getChildren().add(graph);
        
        Scene scene = new Scene(root, 1600, 900, true, SceneAntialiasing.BALANCED);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new CameraController(graph));
        scene.setCamera(new PerspectiveCamera());

        /* scene.setOnMousePressed(me -> {
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged(me -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            rotateX.setAngle(rotateX.getAngle() - (mousePosY - mouseOldY));
            rotateY.setAngle(rotateY.getAngle() + (mousePosX - mouseOldX));
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;

        }); */
        
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
