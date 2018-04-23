package Menu;

import Graphics.CameraController;
import Graphics.Graph2D;
import Graphics.Graph3D;
import Setup.DataReader;
import Setup.DataWriter;
import Setup.Function;
import Setup.ImageType;
import Setup.Level;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import minigolf.Main;

/**
 * This is the class that takes care of the data and buttons on the right hand side of the game screen
 * @author Jordan, Charlotte
 * @version 1.1
 * @date 22.03
 */
public class DataMenu extends VBox{
    private Label function, rangeX, rangeY, amplification1, amplification2, height, ball, goal;
    private TextField Function, RangeX, RangeY, Amplification, Height, Ball, Goal;
    private Button draw, help, play, undo1, undo2;
    private HBox trees_Pane, sand_Pane;
    private RadioButton button_Trees, button_Sand;
    
    public DataMenu(BorderPane pane, Scene scene){
        ArrayList<double[]> tree = new ArrayList<>();
        ArrayList<double[]> sand = new ArrayList<>();
        Group group = new Group();
        Level level = new Level();
        draw();
        
        Function.setText("0.1x 0.03pow(x,2) 0.2y");
        RangeX.setText("-2, 2");
        RangeY.setText("-2, 2");
        Ball.setText("0, 0");
        Goal.setText("1, 1");
        
        getChildren().addAll(function, Function, rangeX, RangeX, rangeY, RangeY, ball, Ball, goal, Goal, draw, help);
    
        draw.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                getChildren().removeAll(draw, help, trees_Pane, sand_Pane, play);
                getChildren().addAll(trees_Pane, sand_Pane, draw, play, help);
                    
                ArrayList<char[]> function = separate(Function.getText());
                level.setFunction(function);
                double[] dataX = calculate(RangeX.getText());
                level.setRangeX(dataX);
                double startX = dataX[0];
                double endX = dataX[1];
                double[] dataY = calculate(RangeX.getText());
                level.setRangeY(dataY);
                double startZ = dataY[0];
                double endZ = dataY[1];
                double[] dataBall = calculate(Ball.getText());
                level.setBall(dataBall);
                double ballX = dataBall[0];
                double ballZ = dataBall[1];
                double[] dataGoal = calculate(Goal.getText());
                level.setGoal(dataGoal);
                double goalX = dataGoal[0];
                double goalZ = dataGoal[1];
                
                int ImageAmplification = (int)(600/(endX - startX));
                
                int StartX = (int)startX*ImageAmplification;
                int EndX = (int)endX*ImageAmplification;
                int StartZ = (int)startZ*ImageAmplification;
                int EndZ = (int)endZ*ImageAmplification;

                int RangeX = EndX - StartX;
                int RangeZ = EndZ - StartZ;
                
                
                Function graph2D = new Function(level, ImageType.LEVELCREATIONMAP);
                Graph2D image = new Graph2D(level, ImageType.LEVELCREATIONMAP, graph2D);
                ImageView Image = new ImageView(image.getImage());
                //Image.setRotate(180);
                
                group.getChildren().add(Image);
                Circle ball = new Circle(((ballX*ImageAmplification) + RangeX - EndX), ((ballZ*ImageAmplification) + RangeZ - EndZ), 5);
                ball.setFill(Color.WHITE);
                group.getChildren().addAll(ball);
                
                pane.setCenter(group);
                pane.getCenter().setOnMouseClicked(event -> {
                    if(button_Trees.isSelected()){
                        double[] coordinate = new double[2];
                        coordinate[0] = (event.getX()/ImageAmplification - endX);
                        coordinate[1] = (event.getY()/ImageAmplification - endZ);
                        tree.add(coordinate);
                        Circle circle = new Circle(event.getX(), event.getY(), 5);
                        circle.setFill(Color.BROWN);
                        group.getChildren().add(circle);
                    }
                    else if(button_Sand.isSelected()){
                        double[] coordinate = new double[2];
                        coordinate[0] = (event.getX()/ImageAmplification - endX);
                        coordinate[1] = (event.getY()/ImageAmplification - endZ);
                        sand.add(coordinate);
                        Circle circle = new Circle(event.getX(), event.getY(), 20);
                        circle.setFill(Color.SANDYBROWN);
                        group.getChildren().add(circle);
                    }
                });
                pane.getCenter().setOnMouseMoved(event -> {
                    pane.getCenter().requestFocus();
                });
                pane.getCenter().requestFocus();
                pane.setMargin(Image, new Insets(10, 10, 10, 10));
                pane.setAlignment(Image, Pos.CENTER);
            }
        });
        undo1.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                if(group.getChildren().size() > 2){
                    try{
                        tree.remove(tree.size() - 1);
                        group.getChildren().remove(group.getChildren().size() - 1);
                    }
                    catch(Exception exception){  
                    }
                }
            }
        });
        undo2.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                if(group.getChildren().size() > 2){
                    try{
                        sand.remove(sand.size() - 1);
                        group.getChildren().remove(group.getChildren().size() - 1);
                    }
                    catch(Exception exception){  
                    }
                }
            }
        });
        button_Trees.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                if(button_Sand.isSelected()){
                    button_Sand.setSelected(false);
                }
            }
        });
        button_Sand.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                if(button_Trees.isSelected()){
                    button_Trees.setSelected(false);
                }
            }
        });
        play.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                level.setTree(tree);
                level.setSand(sand);
                level.setLevel(DataReader.getData().size());
                DataReader.getData().add(level);
                
                DataWriter write = new DataWriter(DataReader.getData());
                
                Stage primaryStage = Main.primaryStage;
                VBox pane = new VBox();
                pane.setPadding(new Insets(15, 15, 15, 15));

                Graph3D graph = new Graph3D(level, ImageType.GRAPH);
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
        });
    }
    
    public DataMenu(BorderPane pane, Scene scene, GameMenu options){
        draw();
        
        Function.setText("0.1x 0.03pow(x,2) 0.2y");
        RangeX.setText("-2, 2");
        RangeY.setText("-2, 2");
        Amplification.setText("50, 100");
        Height.setText("1");
        Ball.setText("0, 0");
        Goal.setText("1, 1");
            
        getChildren().addAll(function, Function, rangeX, RangeX, rangeY, RangeY, amplification1, amplification2, Amplification, height, Height, ball, Ball, goal, Goal, draw, help);
    
        draw.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                ArrayList<char[]> function = separate(Function.getText());
                double[] dataX = calculate(RangeX.getText());
                int startX = (int)dataX[0];
                int endX = (int)dataX[1];
                double[] dataY = calculate(RangeX.getText());
                int startZ = (int)dataY[0];
                int endZ = (int)dataY[1];
                double[] dataAmplification = calculate(Amplification.getText());
                int ImageAmplification = (int)dataAmplification[0];
                int GraphAmplification = (int)dataAmplification[1];
                int heightPrecision = Integer.parseInt(Height.getText().trim());
                double[] dataBall = calculate(Ball.getText());
                int ballX = (int)dataBall[0];
                int ballZ = (int)dataBall[1];
                double[] dataGoal = calculate(Goal.getText());
                int goalX = (int)dataGoal[0];
                int goalZ = (int)dataGoal[1];
                
                
                Graph3D graph = new Graph3D(function, startX, endX, startZ, endZ, ImageAmplification, GraphAmplification, heightPrecision, ballX, ballZ, goalX, goalZ);
                options.setGraph(graph);
                scene.addEventFilter(KeyEvent.KEY_PRESSED, new CameraController(graph));
                
                pane.setCenter(graph);
                pane.getCenter().setOnMouseMoved(event -> {
                    pane.getCenter().requestFocus();
                });
                pane.getCenter().requestFocus();
                pane.setMargin(graph, new Insets(10, 10, 10, 10));
                pane.setAlignment(graph, Pos.CENTER);
            }
        });
    }
    
    private void draw(){
        function = new Label("Function:");
        function.setFont(new Font("Arial", 18));
        
        Function = new TextField();
        Function.setPrefColumnCount(10);
        
        rangeX = new Label("Range of \"x\" [min, max]:");
        rangeX.setFont(new Font("Arial", 18));
        
        RangeX = new TextField();
        RangeX.setPrefColumnCount(10);
        
        rangeY = new Label("Range of \"y\" [min, max]:");
        rangeY.setFont(new Font("Arial", 18));
        
        RangeY = new TextField();
        RangeY.setPrefColumnCount(10);
        
        amplification1 = new Label("Set image and 3D graph");
        amplification1.setFont(new Font("Arial", 18));
        
        amplification2 = new Label("amplification [image, 3D]:");
        amplification2.setFont(new Font("Arial", 18));
        
        Amplification = new TextField();
        Amplification.setPrefColumnCount(10);
        
        height = new Label("Set height precision:");
        height.setFont(new Font("Arial", 18));
        
        Height = new TextField();
        Height.setPrefColumnCount(10);
        
        ball = new Label("Ball starting coordinates [x, y]:");
        ball.setFont(new Font("Arial", 18));
        
        Ball = new TextField();
        Ball.setPrefColumnCount(10);
        
        goal = new Label("Goal coordinates [x, y]:");
        goal.setFont(new Font("Arial", 18));
        
        Goal = new TextField();
        Goal.setPrefColumnCount(10);
        
        trees_Pane = new HBox();
        trees_Pane.setSpacing(15);
        trees_Pane.setAlignment(Pos.CENTER);
        sand_Pane = new HBox();
        sand_Pane.setSpacing(15);
        sand_Pane.setAlignment(Pos.CENTER);
                
        //final ToggleGroup group = new ToggleGroup();
        
        button_Trees = new RadioButton("Add trees");
        //button_Trees.setToggleGroup(group);
        trees_Pane.getChildren().add(button_Trees);
        
        button_Sand = new RadioButton("Add sand");
        //button_Sand.setToggleGroup(group);
        sand_Pane.getChildren().add(button_Sand);
        
        undo1 = new Button("Undo");
        //undo1.setMinSize(200, 50);
        undo1.setStyle("-fx-font: 18 arial; -fx-base: #6495ED;");
        trees_Pane.getChildren().add(undo1);
        
        undo2 = new Button("Undo");
        //undo2.setMinSize(200, 50);
        undo2.setStyle("-fx-font: 18 arial; -fx-base: #6495ED;");
        sand_Pane.getChildren().add(undo2);
        
        draw = new Button("Draw");
        draw.setMinSize(200, 50);
        draw.setStyle("-fx-font: 24 arial; -fx-base: #6495ED;");
        
        play = new Button("Save and Play");
        play.setMinSize(200, 50);
        play.setStyle("-fx-font: 24 arial; -fx-base: #6495ED;");

        help = new Button("Instructions");
        help.setMinSize(200, 50);
        help.setStyle("-fx-font: 24 arial; -fx-base: #6495ED;");
        
        help.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e) {
                loadHelpPage();
            }
        });
        
        setSpacing(10);
        setAlignment(Pos.CENTER);
        setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, new Insets(15, 15, 15, 15))));
    }
    
    /**
     * Method used to extract integers from a String
     * @param s String
     * @return int array with data
     */
    public double[] calculate(String s){
        char[] string = s.toCharArray();
        double[] data = new double[2];
        int comma = 0;
        for(int v = 0; v < string.length; v++){
            if(string[v] == ','){
                comma = v;
                v = string.length - 1;
            }
        }
        char[] first = new char[comma];
        char[] second = new char[string.length - comma];
        for(int v = 0; v < comma; v++){
            first[v] = string[v];
        }
        for(int v = 0; v + 1 < string.length - comma; v++){
            second[v] = string[comma + v + 1];
        }
        data[0] = Integer.parseInt(new String(first).trim());
        data[1] = Integer.parseInt(new String(second).trim());
        
        return data;
    }
    
    /**
     * Method that shows the helper window that is opened when the help button is pressed
     */
    public void loadHelpPage(){
        VBox pane = new VBox();
        pane.setSpacing(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        Label range1 = new Label("To display the function in a given range:");
        range1.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label range2 = new Label("Input \"X\" and \"Y\" range as integers, starting with the minimum and separating them with a comma.");
        range2.setFont(new Font("Arial", 20));
        
        HBox range = new HBox();
        Label range3 = new Label("\"X\" is width and \"Y\" is depth.");
        range3.setFont(new Font("Arial", 20));
        range.getChildren().add(range3);

        Label range4 = new Label("Example: \"-1, 1\"");
        range4.setFont(new Font("Arial", 20));
        range.getChildren().add(range4);
        range.setAlignment(Pos.CENTER);
        range.setSpacing(10);

        Label amplification1 = new Label("To set 3D graph precision and image precision:");
        amplification1.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label amplification2 = new Label("Input integers, separated with a comma in amplification texfield.");
        amplification2.setFont(new Font("Arial", 20));
        
        Label amplification3 = new Label("Amplification means number of points taken between two integer points.");
        amplification3.setFont(new Font("Arial", 20));
        
        Label amplification4 = new Label("Note: range of x multiplied by amplification should not be more than 600, because execution time grows exponentially!");
        amplification4.setFont(new Font("Arial", 20));

        Label amplification5 = new Label("Example input for range from -1 to 1: \"50, 100\"");
        amplification5.setFont(new Font("Arial", 20));
        
        Label height1 = new Label("Height precision is used for debugging, dividing the height by an integer");
        height1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        Label ball = new Label("Ball starting coordinates are set as integer points in the graph. Example: \"0, 0\"");
        ball.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label goal = new Label("Goal coordinates are set as integer points in the graph. Example: \"1, 1\"");
        goal.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, new Insets(0, 0, 0, 0))));

        pane.getChildren().addAll(range1, range2, range, amplification1, amplification2, amplification3, amplification4, height1, ball, goal);
        pane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(pane, 1100, 350);
        Stage stage = new Stage();
        stage.setTitle("Instructions");
        stage.setScene(scene);
        stage.show();
    }
    private ArrayList<char[]> separate(String function){
        ArrayList<char[]> data = new ArrayList<>();
        char[] f = function.toCharArray();
        for(int i = 0; i < f.length; i++){
            if(f[i] == ' '){
                char[] part = new char[i];
                for(int x = 0; x < part.length; x++){
                    part[x] = f[x];
                }
                data.add(part);
                char[] newF = new char[f.length - i];
                for(int x = i + 1; x < f.length; x++){
                    newF[x - i - 1] = f[x];
                }
                f = new char[newF.length];
                for(int x = 0; x < newF.length; x++){
                    f[x] = newF[x];
                }
                i = 0;
            }
        }
        data.add(f);
        return data;
    }
}
