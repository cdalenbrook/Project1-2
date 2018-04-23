package Menu;

import Graphics.CameraController;
import Graphics.Graph2D;
import Graphics.Graph3D;
import Setup.DataReader;
import Setup.Function;
import Setup.ImageType;
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

/**
 * Class building stage with level display
 * @author Jordan
 */
public class LevelMenu extends BorderPane{
    private int level = 0;
    private int availableLevel = DataReader.getData().size() -1;
    
    /**
     * Constructor
     */
    public LevelMenu(){
        Label title = new Label("Select level:");
        title.setFont(new Font("Arial", 30));
        
        Button left = new Button("Left");
        left.setMinSize(200, 50);
        left.setStyle("-fx-font: 24 arial; -fx-base: #6495ED;");
        left.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e) {
                if(level == 0){
                    if((availableLevel + 1)%3 == 0 && availableLevel - 2 != level){
                        level = availableLevel - 2;
                        drawDisplayedMaps();
                    }
                    else if((availableLevel + 1)%3 == 1 && availableLevel != level){
                        level = availableLevel;
                        drawDisplayedMaps();
                    }
                    else if((availableLevel + 1)%3 == 2 && availableLevel - 1 != level){
                        level = availableLevel - 1;
                        drawDisplayedMaps();
                    }
                }
                else{
                    level-=3;
                    drawDisplayedMaps();
                }
            }
        });
        
        Button create = new Button("Create level");
        create.setMinSize(200, 50);
        create.setStyle("-fx-font: 24 arial; -fx-base: #6495ED;");
        create.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e) {
                Stage primaryStage = Main.primaryStage;
                
                BorderPane pane = new BorderPane();
                Scene scene = new Scene(pane);
                DataMenu data = new DataMenu(pane, scene);
                
                data.setOnMouseMoved(event -> {
                    data.requestFocus();
                });
                
                pane.setRight(data);
                pane.setAlignment(pane, Pos.CENTER);
                pane.setMargin(data, new Insets(10, 10, 10, 10));
                pane.setPadding(new Insets(10, 10, 10, 10));
                pane.setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
                
                primaryStage.setScene(scene);
                primaryStage.setWidth(1200);
                primaryStage.setHeight(750);
                
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
            }
        });
        
        Button right = new Button("Right");
        right.setMinSize(200, 50);
        right.setStyle("-fx-font: 24 arial; -fx-base: #6495ED;");
        right.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e) {
                if(availableLevel <= level + 2){
                    level = 0;
                    drawDisplayedMaps();
                }
                else{
                    if(level + 3 <= availableLevel){
                        level = level + 3;
                        drawDisplayedMaps();
                    }
                }
            }
        });
        
        setTop(title);
        setMargin(title, new Insets(20, 10, 10, 10));
        setAlignment(title, Pos.CENTER);
        
        drawDisplayedMaps();
        
        HBox controlls = new HBox();
        controlls.setSpacing(50);
        controlls.setAlignment(Pos.CENTER);
        setBottom(controlls);
        setMargin(controlls, new Insets(10, 10, 10, 10));
        controlls.getChildren().add(left);
        controlls.getChildren().add(create);
        controlls.getChildren().add(right);
        setAlignment(controlls, Pos.CENTER);
        
        setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));
         
    }
    /**
     * Draws the map on the button representing a level
     * @param level level to be displayed
     * @return button with image
     */
    private Button getMap(int level){
        Button map = new Button();
        Function function = new Function(DataReader.getData().get(level), ImageType.BUTTON);
        ImageView image = new ImageView(new Graph2D(DataReader.getData().get(level), ImageType.BUTTON, function).getImage());
        image.setRotate(180);
        map.setGraphic(image);
        
        return map;
    }
   
    /**
     * Method that draws the game after the user has selected a level
     * @param level level selected
     */
    private void drawGame(int level){
        Stage primaryStage = Main.primaryStage;
        VBox pane = new VBox();
        pane.setPadding(new Insets(15, 15, 15, 15));
        
        Graph3D graph = new Graph3D(DataReader.getData().get(level), ImageType.GRAPH);
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
    
    /**
     * Method that draws the available levels
     */
    private void drawDisplayedMaps(){
        setLeft(null);
        setCenter(null);
        setRight(null);
        try{
            VBox box1 = new VBox();
            box1.setAlignment(Pos.CENTER);
            box1.setSpacing(15);
            
            Label leftLevel = new Label("Level: " + level);
            leftLevel.setFont(new Font("Arial", 24));
            box1.getChildren().add(leftLevel);
            
            Button first = getMap(level);
            box1.getChildren().add(first);
            setLeft(box1);
            setAlignment(box1, Pos.CENTER);
            setMargin(box1, new Insets(10, 50, 10, 10));
            first.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent e) {
                    drawGame(level);
                }
            });
        }
        catch(Exception e){
            //System.out.println(e.getMessage());
        }
        try{
            VBox box2 = new VBox();
            box2.setAlignment(Pos.CENTER);
            box2.setSpacing(15);
            
            Label centerLevel = new Label("Level: " + (level + 1));
            centerLevel.setFont(new Font("Arial", 24));
            box2.getChildren().add(centerLevel);
            
            Button second = getMap(level + 1);
            box2.getChildren().add(second);
            setCenter(box2);
            setAlignment(box2, Pos.CENTER);
            setMargin(box2, new Insets(10, 25, 10, 25));
            second.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent e) {
                    drawGame(level + 1);
                }
            });
        }
        catch(Exception e){
            //System.out.println(e.getMessage());
        }
        try{
            VBox box3 = new VBox();
            box3.setAlignment(Pos.CENTER);
            box3.setSpacing(15);
            
            Label rightLevel = new Label("Level: " + (level + 2));
            rightLevel.setFont(new Font("Arial", 24));
            box3.getChildren().add(rightLevel);
            
            Button third = getMap(level + 2);
            box3.getChildren().add(third);
            setRight(box3);
            setAlignment(box3, Pos.CENTER);
            setMargin(box3, new Insets(10, 25, 10, 50));
            third.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent e) {
                    drawGame(level + 2);
                }
            });
        }
        catch(Exception e){
            //System.out.println(e.getMessage());
        }
    }
}
