package Menu;

import Graphics.CameraController;
import Graphics.Graph2D;
import Graphics.Graph3D;
import Setup.DataReader;
import Setup.Function;
import Setup.FunctionType;
import Setup.Level;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import minigolf.Main;

public class LevelMenu extends BorderPane{
    private int level = 0;
    public LevelMenu(){
        Label title = new Label("Select level:");
        title.setFont(new Font("Arial", 30));
        
        Button left = new Button("Left");
        left.setMinSize(200, 50);
        left.setStyle("-fx-font: 24 arial; -fx-base: #6495ED;");
        
        Button create = new Button("Create level");
        create.setMinSize(200, 50);
        create.setStyle("-fx-font: 24 arial; -fx-base: #6495ED;");
        
        Button right = new Button("Right");
        right.setMinSize(200, 50);
        right.setStyle("-fx-font: 24 arial; -fx-base: #6495ED;");
        
        setTop(title);
        setMargin(title, new Insets(20, 10, 10, 10));
        setAlignment(title, Pos.CENTER);
        try{
            Button first = getMap(level);
            setLeft(first);
            setAlignment(first, Pos.CENTER);
            setMargin(first, new Insets(10, 50, 10, 10));
            first.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e) {
                draw(level);
            }
        });
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        try{
            Button second = getMap(level + 1);
            setCenter(second);
            setAlignment(second, Pos.CENTER);
            setMargin(second, new Insets(10, 25, 10, 25));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        try{
            Button third = getMap(level + 2);
            setRight(third);
            setAlignment(third, Pos.CENTER);
            setMargin(third, new Insets(10, 25, 10, 50));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        HBox controlls = new HBox();
        controlls.setSpacing(50);
        setBottom(controlls);
        setMargin(controlls, new Insets(10, 10, 10, 10));
        controlls.getChildren().add(left);
        controlls.getChildren().add(create);
        controlls.getChildren().add(right);
        setAlignment(controlls, Pos.CENTER);
        
        setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
               
        
    }
    private Button getMap(int level){
        Button map = new Button();
        Function function = new Function(level, FunctionType.BUTTON);
        ImageView image = new ImageView(new Graph2D(level, FunctionType.BUTTON, function).getImage());
        image.setRotate(180);
        map.setGraphic(image);
        
        return map;
    }
    private void draw(int level){
        Stage primaryStage = Main.getStage();
        VBox pane = new VBox();
        pane.setPadding(new Insets(15, 15, 15, 15));
        
        Graph3D graph = new Graph3D(level, FunctionType.GRAPH);
        pane.getChildren().add(graph);
        

        GameMenu options = new GameMenu();
        options.setGraph(graph);
        pane.getChildren().add(options);
        pane.setSpacing(50);
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(pane);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new CameraController(graph));

        primaryStage.setScene(scene);
        primaryStage.setWidth(900);
        primaryStage.setHeight(850);

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }
}
