package minigolf;

import Graphics.Function;
import Graphics.Game2D;
import Graphics.Graph2D;
import Graphics.OptionsPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
        int startX = -200;
        int endX = 200;
        int startZ = -200;
        int endZ = 200;
        int amplification = 1;
        
        int rangeX = (endX - startX)*amplification;
        int rangeZ = (endZ - startZ)*amplification;
        
        Function function = new Function(startX, endX, startZ, endZ, amplification);
        Pane map = new Pane();
        Graph2D image = new Graph2D(startX, endX, startZ, endZ, amplification, function);
        
        ImageView iv = new ImageView(image.getImage());
        iv.setRotate(180);
        
        map.getChildren().add(iv);
        Game2D game = new Game2D(5, amplification*2);
        map.getChildren().add(game);
        int CoordinateX = 0;
        int CoordinateZ = 0;
        game.getBall().setCenterX((startX - CoordinateX)*amplification + rangeX);
        game.getBall().setCenterY((startZ - CoordinateZ)*amplification + rangeZ);
        
        int coordinateX  = 0;
        int coordinateZ = 1;
        game.getFinish().setCenterX((startX - coordinateX)*amplification + rangeX);
        game.getFinish().setCenterY((startZ - coordinateZ)*amplification + rangeZ);
        
        pane.add(map, 0, 0);
        pane.add(new OptionsPane(game), 0, 1);
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setVgap(20);
        pane.setHgap(20);
        pane.setPadding(new Insets(20, 20, 20, 20));
        
        int width = rangeX + 100;
        int height = rangeZ + 300;
        
        Scene scene = new Scene(pane, width, height);
       
        
        primaryStage.setTitle("Crazy Putting!");
        primaryStage.setScene(scene);
        
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        
        /* Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
        */
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
