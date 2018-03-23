package Menu;

import Graphics.OptionsPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import minigolf.Main;

public class GameMenu extends VBox{
    public GameMenu(){
        Label exampleF = new Label("Example function:");
        exampleF.setFont(new Font("Arial", 22));
        
        Button graph2D = new Button("2D TOP VIEW");
        graph2D.setMinSize(150, 50);
        graph2D.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        graph2D.setFocusTraversable(false);

        Button graph3D = new Button("3D VIEW");
        graph3D.setMinSize(150, 50);
        graph3D.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        graph3D.setFocusTraversable(false);
        
        
        Label customF = new Label("Custom function:");
        customF.setFont(new Font("Arial", 22));
        
        Button Graph2D = new Button("2D TOP VIEW");
        Graph2D.setMinSize(150, 50);
        Graph2D.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        Graph2D.setFocusTraversable(false);

        Button Graph3D = new Button("3D VIEW");
        Graph3D.setMinSize(150, 50);
        Graph3D.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        Graph3D.setFocusTraversable(false);
        
        Button exit = new Button("Exit");
        exit.setMinSize(150, 50);
        exit.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        exit.setFocusTraversable(false);


        setAlignment(Pos.CENTER);
        setSpacing(10);
        getChildren().addAll(exampleF, graph2D, graph3D, customF, Graph2D, Graph3D, exit);
        
        graph2D.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                Stage primaryStage = Main.getStage();
                
                BorderPane pane = new BorderPane();
                Scene scene = new Scene(pane);
                OptionsPane options = new OptionsPane();
                DataMenu data = new DataMenu(pane, scene, options);
                pane.setRight(data);
                pane.setAlignment(pane, Pos.CENTER_LEFT);
                pane.setBottom(options);
                pane.setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
                
                primaryStage.setScene(scene);
                primaryStage.setWidth(1500);
                primaryStage.setHeight(900);
                
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
            }
        });
        graph3D.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                
            }
        });
        Graph2D.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {

            }
        });
        Graph3D.setOnAction(new EventHandler<ActionEvent>(){
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
