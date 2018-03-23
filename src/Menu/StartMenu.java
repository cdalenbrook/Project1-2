package Menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import minigolf.Main;

public class StartMenu extends VBox{
    public StartMenu(){
        Button start = new Button("Play");
        start.setMinSize(150, 50);
        start.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        start.setFocusTraversable(false);

        Button exit = new Button("Exit");
        exit.setMinSize(150, 50);
        exit.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        exit.setFocusTraversable(false);

        setAlignment(Pos.CENTER);
        setSpacing(30);
        getChildren().addAll(start, exit);
        
        start.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                Stage primaryStage = Main.getStage();
                GameMenu gameMenu = new GameMenu();

                primaryStage.setScene(new Scene(gameMenu));
                primaryStage.setWidth(500);
                primaryStage.setHeight(500);
                
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
            }
        });
        exit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                Stage primaryStage = Main.getStage();
                primaryStage.close();
                System.exit(0);

             }
        });
        setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
