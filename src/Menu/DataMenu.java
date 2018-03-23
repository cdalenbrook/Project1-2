package Menu;

import Graphics.Function;
import Graphics.Game2D;
import Graphics.Graph2D;
import Graphics.OptionsPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This is the class that takes care of the data and buttons on the right hand side of the game screen
 * @author Jordan, Charlotte
 * @version 1.1
 * @date 22.03
 */

public class DataMenu extends VBox{
    public TextField StartX;
    public TextField StartY;
    public TextField EndX;
    public TextField EndY;
    public TextField Amplification;
    public TextField InitialX;
    public TextField InitialY;
    public TextField GoalX;
    public TextField GoalY;
    private BorderPane pane;
    
    private DataMenu data;
    private Scene scene;
    private OptionsPane options;
    
    
    public DataMenu(BorderPane pane, Scene scene, OptionsPane options){
        data = this;
        this.options = options;
        this.scene = scene;
        this.pane = pane;
        Label rangeX = new Label("X range:");
        rangeX.setFont(new Font("Arial", 18));
        
        StartX = new TextField();
        StartX.setPrefColumnCount(10);
        //StartX.setFocusTraversable(false);
        
        EndX = new TextField();
        EndX.setPrefColumnCount(10);
        //EndX.setFocusTraversable(false);
        
        Label rangeY = new Label("Y range:");
        rangeY.setFont(new Font("Arial", 18));
        
        StartY = new TextField();
        StartY.setPrefColumnCount(10);
        //StartY.setFocusTraversable(false);
        
        EndY = new TextField();
        EndY.setPrefColumnCount(10);
        //EndY.setFocusTraversable(false);
        
        Label amplification = new Label("Amplification:");
        amplification.setFont(new Font("Arial", 18));
        
        Amplification = new TextField();
        Amplification.setPrefColumnCount(10);
        //Amplification.setFocusTraversable(false);
        
        Label initial = new Label("Starting coordinates");
        initial.setFont(new Font("Arial", 18));
        
        InitialX = new TextField();
        InitialX.setPrefColumnCount(10);
        //InitialX.setFocusTraversable(false);
        
        InitialY = new TextField();
        InitialY.setPrefColumnCount(10);
        //InitialY.setFocusTraversable(false);
        
        Label goal = new Label("Goal coordinates");
        goal.setFont(new Font("Arial", 18));
        
        GoalX = new TextField();
        GoalX.setPrefColumnCount(10);
        //GoalX.setFocusTraversable(false);
        
        GoalY = new TextField();
        GoalY.setPrefColumnCount(10);
        //GoalY.setFocusTraversable(false);
        
        Button draw = new Button("Draw");
        draw.setMinSize(150, 50);
        draw.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        draw.setFocusTraversable(false);
        draw.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                Function function = new Function(Integer.parseInt(StartX.getText()), Integer.parseInt(EndX.getText()),
                        Integer.parseInt(StartY.getText()), Integer.parseInt(EndY.getText()), Integer.parseInt(Amplification.getText()));
                Graph2D image = new Graph2D(Integer.parseInt(StartX.getText()), Integer.parseInt(EndX.getText()),
                        Integer.parseInt(StartY.getText()), Integer.parseInt(EndY.getText()), Integer.parseInt(Amplification.getText()), function);
        
                ImageView iv = new ImageView(image.getImage());
                iv.setRotate(180);
                StackPane stack = new StackPane();
                stack.setAlignment(Pos.CENTER);
                Group group = new Group();
                group.getChildren().add(iv);
                stack.getChildren().add(group);
                
                int rangeX = ((Integer.parseInt(EndX.getText()) - (Integer.parseInt(StartX.getText())))*(Integer.parseInt(Amplification.getText())));
                int rangeY = ((Integer.parseInt(EndY.getText()) - (Integer.parseInt(StartY.getText())))*(Integer.parseInt(Amplification.getText())));
                
                
                Game2D ball = new Game2D(Integer.parseInt(Amplification.getText())/30, Integer.parseInt(Amplification.getText())*Integer.parseInt(Amplification.getText())/100);
                options.setGame(ball);
                ball.getBall().setCenterX((Integer.parseInt(StartX.getText()) - Integer.parseInt(InitialX.getText()))*Integer.parseInt(Amplification.getText()) + rangeX);
                ball.getBall().setCenterY((Integer.parseInt(StartY.getText()) - Integer.parseInt(InitialY.getText()))*Integer.parseInt(Amplification.getText()) + rangeY);
                ball.getFinish().setCenterX((Integer.parseInt(StartX.getText()) - Integer.parseInt(GoalX.getText()))*Integer.parseInt(Amplification.getText()) + rangeX);
                ball.getFinish().setCenterY((Integer.parseInt(StartY.getText()) - Integer.parseInt(GoalY.getText()))*Integer.parseInt(Amplification.getText()) + rangeY);
                
                options.setData(Integer.parseInt(Amplification.getText()), rangeX, rangeY);
                group.getChildren().addAll(ball.getBall(), ball.getFinish(), ball.setLine());
                
                //PerspectiveCamera camera = new PerspectiveCamera(true);
                //add possible rotations and position of camera
                //camera.getTransforms().addAll(new Translate(CameraX, CameraY, CameraZ));

                //stack.getChildren().add(camera);

                //create a Scene from the group
                //SubScene subScene = new SubScene(stack, 1400, 700, true, SceneAntialiasing.BALANCED);
                //subScene.setCamera(camera);
                //scene.addEventFilter(KeyEvent.KEY_PRESSED, new CameraController(data));
                
                pane.setCenter(stack);
                pane.setAlignment(stack, Pos.CENTER);
            }

        });
        
        setSpacing(10);
        setAlignment(Pos.CENTER);

        Button help = new Button("Help");
        help.setMinSize(150, 50);
        help.setStyle("-fx-font: 22 arial; -fx-base: #8FBC8F;");
        setSpacing(10);
        setAlignment(Pos.CENTER);

        help.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e) {
                loadHelpPage();
            }
        });


        getChildren().addAll(rangeX, StartX, EndX, rangeY, StartY, EndY, amplification,
                Amplification, initial, InitialX, InitialY, goal, GoalX, GoalY, draw, help);
        setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, new Insets(15, 15, 15, 15))));


    }

    /**
     * Method that shows the helper window that is opened when the help button is pressed
     */
    public void loadHelpPage(){
        GridPane pane = new GridPane();
        pane.setHgap(20); //horizontal gap in pixels => that's what you are asking for
        pane.setVgap(20); //vertical gap in pixels
        pane.setPadding(new Insets(10, 10, 10, 10));

        Label xRange = new Label("X Range");
        xRange.setFont(new Font("Arial", 20));
        pane.add(xRange, 1, 0);
        pane.setHalignment(xRange, HPos.CENTER);

        Label xRangeDes = new Label("Height of the course (x1, x2)");
        xRangeDes.setFont(new Font("Arial", 20));
        pane.add(xRangeDes, 2, 0);
        pane.setHalignment(xRangeDes, HPos.CENTER);

        Label yRange = new Label("Y Range");
        yRange.setFont(new Font("Arial", 20));
        pane.add(yRange, 1, 1);
        pane.setHalignment(yRange, HPos.CENTER);

        Label yRangeDes = new Label("Width of the course (y1, y2)");
        yRangeDes.setFont(new Font("Arial", 20));
        pane.add(yRangeDes, 2, 1);
        pane.setHalignment(yRangeDes, HPos.CENTER);

        Label amp = new Label("Amplification");
        amp.setFont(new Font("Arial", 20));
        pane.add(amp, 1, 2);
        pane.setHalignment(amp, HPos.CENTER);

        Label ampDes = new Label("How zoomed in you want the image");
        ampDes.setFont(new Font("Arial", 20));
        pane.add(ampDes, 2, 2);
        pane.setHalignment(ampDes, HPos.CENTER);

        Label startCord = new Label("Starting coordinates");
        startCord.setFont(new Font("Arial", 20));
        pane.add(startCord, 1, 3);
        pane.setHalignment(startCord, HPos.CENTER);

        Label startCordDes = new Label("Where the ball starts from (x, y)");
        startCordDes.setFont(new Font("Arial", 20));
        pane.add(startCordDes, 2, 3);
        pane.setHalignment(startCordDes, HPos.CENTER);

        Label goalCord = new Label("Goal Coordinates");
        goalCord.setFont(new Font("Arial", 20));
        pane.add(goalCord, 1, 4);
        pane.setHalignment(goalCord, HPos.CENTER);

        Label goalCordDes = new Label("Where the ball should land to finish the course (x, y)");
        goalCordDes.setFont(new Font("Arial", 20));
        pane.add(goalCordDes, 2, 4);
        pane.setHalignment(goalCordDes, HPos.CENTER);

        Scene scene = new Scene(pane, 700, 250);
        Stage stage = new Stage();
        stage.setTitle("Help");
        stage.setScene(scene);
        stage.show();
    }
}
