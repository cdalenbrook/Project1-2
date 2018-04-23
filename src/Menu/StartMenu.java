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

/**
 * Class that builds the start menu screen
 * @author Jordan
 */
public class StartMenu extends VBox{
    
    /**
     * Constructor
     */
    public StartMenu(){
        Button newGame = new Button("NEW GAME");
        newGame.setMinSize(200, 50);
        newGame.setStyle("-fx-font: 24 arial; -fx-base: #6495ED;");
        newGame.setFocusTraversable(false);

        Button continueGame = new Button("CONTINUE GAME");
        continueGame.setMinSize(200, 50);
        continueGame.setStyle("-fx-font: 24 arial; -fx-base: #6495ED;");
        continueGame.setFocusTraversable(false);
        
        Button test = new Button("TEST");
        test.setMinSize(200, 50);
        test.setStyle("-fx-font: 24 arial; -fx-base: #6495ED;");
        test.setFocusTraversable(false);
        
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
        getChildren().addAll(newGame, continueGame, test, settings, exit);
        
        newGame.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                Stage primaryStage = Main.primaryStage;
                
                try{
                    DataReader.getData().clear();
                }
                catch(Exception exception){
                
                }
                DataReader data = new DataReader();
                
                Scene scene = new Scene(new LevelMenu());
                
                primaryStage.setScene(scene);
                primaryStage.setWidth(850);
                primaryStage.setHeight(500);
                
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
            }
        });
        continueGame.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                System.out.println("This feature is not implemented yet");
            }
        });
        test.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                Stage primaryStage = Main.primaryStage;
                
                BorderPane pane = new BorderPane();
                Scene scene = new Scene(pane);
                GameMenu options = new GameMenu();
                DataMenu data = new DataMenu(pane, scene, options);
                
                pane.setRight(data);
                pane.setAlignment(pane, Pos.CENTER);
                pane.setMargin(data, new Insets(10, 10, 10, 10));
                pane.setBottom(options);
                pane.setMargin(options, new Insets(10, 10, 10, 10));
                pane.setPadding(new Insets(10, 10, 10, 10));
                pane.setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
                
                data.setOnMouseMoved(event -> {
                    data.requestFocus();
                });
                options.setOnMouseMoved(event -> {
                    options.requestFocus();
                });
                
                primaryStage.setScene(scene);
                primaryStage.setWidth(1200);
                primaryStage.setHeight(950);
                
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
            }
        });
        settings.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
               System.out.println("This feature is not implemented yet");
            }
        });
        exit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                Stage primaryStage = Main.primaryStage;
                primaryStage.close();
                System.exit(0);

             }
        });
        setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
