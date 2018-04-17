package Menu;

import Setup.DataReader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import minigolf.Main;

public class StartMenu extends VBox{
    public StartMenu(){
        Button example = new Button("NEW GAME");
        example.setMinSize(200, 50);
        example.setStyle("-fx-font: 24 arial; -fx-base: #6495ED;");
        example.setFocusTraversable(false);

        Button custom = new Button("CONTINUE GAME");
        custom.setMinSize(200, 50);
        custom.setStyle("-fx-font: 24 arial; -fx-base: #6495ED;");
        custom.setFocusTraversable(false);
        
        Button settings = new Button("SETTINGS");
        settings.setMinSize(200, 50);
        settings.setStyle("-fx-font: 24 arial; -fx-base: #6495ED;");
        settings.setFocusTraversable(false);

        Button exit = new Button("EXIT");
        exit.setMinSize(200, 50);
        exit.setStyle("-fx-font: 24 arial; -fx-base: #6495ED;");
        exit.setFocusTraversable(false);

        setAlignment(Pos.CENTER);
        setSpacing(15);
        getChildren().addAll(example, custom, settings, exit);
        
        example.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                Stage primaryStage = Main.getStage();
                
                BorderPane pane = new BorderPane();
                Scene scene = new Scene(pane);
                GameMenu options = new GameMenu();
                DataMenu data = new DataMenu(pane, scene, options, true);
                pane.setRight(data);
                pane.setAlignment(pane, Pos.CENTER);
                pane.setMargin(data, new Insets(10, 10, 10, 10));
                pane.setBottom(options);
                pane.setMargin(options, new Insets(10, 10, 10, 10));
                pane.setPadding(new Insets(10, 10, 10, 10));
                pane.setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
                
                primaryStage.setScene(scene);
                primaryStage.setWidth(1200);
                primaryStage.setHeight(850);
                
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
            }
        });
        custom.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                Stage primaryStage = Main.getStage();
                
                DataReader data = new DataReader();
                
                Scene scene = new Scene(new LevelMenu());
                
                primaryStage.setScene(scene);
                primaryStage.setWidth(750);
                primaryStage.setHeight(450);
                
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
            }
        });
        settings.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                
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
