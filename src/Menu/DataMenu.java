package Menu;

import Graphics.Function;
import Graphics.Game2D;
import Graphics.Graph2D;
import Graphics.OptionsPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
                int xMax = Integer.parseInt(EndX.getText())*Integer.parseInt(Amplification.getText());
                int yMax = Integer.parseInt(EndY.getText())*Integer.parseInt(Amplification.getText());
                
                
                Game2D ball = new Game2D(Integer.parseInt(Amplification.getText())/30, Integer.parseInt(Amplification.getText())*Integer.parseInt(Amplification.getText())/100);
                options.setGame(ball);
                ball.getBall().setCenterX((Integer.parseInt(StartX.getText()) - Integer.parseInt(InitialX.getText()))*Integer.parseInt(Amplification.getText()) + rangeX);
                ball.getBall().setCenterY((Integer.parseInt(StartY.getText()) - Integer.parseInt(InitialY.getText()))*Integer.parseInt(Amplification.getText()) + rangeY);
                ball.getFinish().setCenterX((Integer.parseInt(StartX.getText()) - Integer.parseInt(GoalX.getText()))*Integer.parseInt(Amplification.getText()) + rangeX);
                ball.getFinish().setCenterY((Integer.parseInt(StartY.getText()) - Integer.parseInt(GoalY.getText()))*Integer.parseInt(Amplification.getText()) + rangeY);
                
                options.setData(Integer.parseInt(Amplification.getText()), rangeX, rangeY, xMax, yMax);
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
        getChildren().addAll(rangeX, StartX, EndX, rangeY, StartY, EndY, amplification,
                Amplification, initial, InitialX, InitialY, goal, GoalX, GoalY, draw);
        setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, new Insets(15, 15, 15, 15))));
               
    }
}
