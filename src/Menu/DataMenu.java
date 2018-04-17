package Menu;

import Graphics.CameraController;
import Graphics.Graph3D;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * This is the class that takes care of the data and buttons on the right hand side of the game screen
 * @author Jordan, Charlotte
 * @version 1.1
 * @date 22.03
 */

public class DataMenu extends VBox{
    //private int minX, maxX, minY, maxY, amplification, heightPrecision, ballX, ballY, goalX, goalY;
    private boolean focus = true;
    
    public DataMenu(BorderPane pane, Scene scene, GameMenu options, boolean example){
        
        Label rangeX = new Label("Range of \"x\" [min, max]:");
        rangeX.setFont(new Font("Arial", 18));
        
        TextField RangeX = new TextField();
        RangeX.setPrefColumnCount(10);
        RangeX.setFocusTraversable(focus);
        
        Label rangeY = new Label("Range of \"y\" [min, max]:");
        rangeY.setFont(new Font("Arial", 18));
        
        TextField RangeY = new TextField();
        RangeY.setPrefColumnCount(10);
        RangeY.setFocusTraversable(focus);
        
        Label amplification1 = new Label("Set image and 3D graph");
        amplification1.setFont(new Font("Arial", 18));
        
        Label amplification2 = new Label("amplification [image, 3D]:");
        amplification2.setFont(new Font("Arial", 18));
        
        TextField Amplification = new TextField();
        Amplification.setPrefColumnCount(10);
        Amplification.setFocusTraversable(focus);
        
        Label height = new Label("Set height precision:");
        height.setFont(new Font("Arial", 18));
        
        TextField Height = new TextField();
        Height.setPrefColumnCount(10);
        Height.setFocusTraversable(focus);
        
        Label ball = new Label("Ball starting coordinates [x, y]:");
        ball.setFont(new Font("Arial", 18));
        
        TextField Ball = new TextField();
        Ball.setPrefColumnCount(10);
        Ball.setFocusTraversable(focus);
        
        Label goal = new Label("Goal coordinates [x, y]:");
        goal.setFont(new Font("Arial", 18));
        
        TextField Goal = new TextField();
        Goal.setPrefColumnCount(10);
        Goal.setFocusTraversable(focus);
        
        Button draw = new Button("Draw");
        draw.setMinSize(200, 50);
        draw.setStyle("-fx-font: 24 arial; -fx-base: #6495ED;");
        draw.setFocusTraversable(focus);

        Button help = new Button("Instructions");
        help.setMinSize(200, 50);
        help.setStyle("-fx-font: 24 arial; -fx-base: #6495ED;");
        help.setFocusTraversable(focus);
        
        if(example){
            RangeX.setText("-2, 2");
            RangeY.setText("-2, 2");
            Amplification.setText("50, 100");
            Height.setText("1");
            Ball.setText("0, 0");
            Goal.setText("1, 1");
        }
        draw.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                int[] dataX = calculate(RangeX.getText());
                int minX = dataX[0];
                int maxX = dataX[1];
                int[] dataY = calculate(RangeX.getText());
                int minY = dataY[0];
                int maxY = dataY[1];
                int[] dataAmplification = calculate(Amplification.getText());
                int ImageAmplification = dataAmplification[0];
                int GraphAmplification = dataAmplification[1];
                int heightPrecision = Integer.parseInt(Height.getText().trim());
                int[] dataBall = calculate(Ball.getText());
                int ballX = dataBall[0];
                int ballY = dataBall[1];
                int[] dataGoal = calculate(Goal.getText());
                int goalX = dataGoal[0];
                int goalY = dataGoal[1];
                
                
                Graph3D graph = new Graph3D(minX, maxX, minY, maxY, ImageAmplification, GraphAmplification, heightPrecision, ballX, ballY, goalX, goalY);
                options.setGraph(graph);
                scene.addEventFilter(KeyEvent.KEY_PRESSED, new CameraController(graph));
                
                scene.setOnMousePressed(event ->{
                    if(event.getSceneX() <= 860 && event.getSceneY() <= 560){
                        focus = false;
                    }
                    else{
                        focus = true;
                    }
                    RangeX.setFocusTraversable(focus);
                    RangeY.setFocusTraversable(focus);
                    Amplification.setFocusTraversable(focus);
                    Height.setFocusTraversable(focus);
                    Ball.setFocusTraversable(focus);
                    Goal.setFocusTraversable(focus);
                    draw.setFocusTraversable(focus);
                    help.setFocusTraversable(focus);
                    
                });
                
                pane.setCenter(graph);
                pane.setMargin(graph, new Insets(10, 10, 10, 10));
                pane.setAlignment(graph, Pos.CENTER);
            }

        });

        help.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e) {
                loadHelpPage();
            }
        });
        
        
        
        setSpacing(10);
        setAlignment(Pos.CENTER);
        getChildren().addAll(rangeX, RangeX, rangeY, RangeY, amplification1, amplification2, Amplification, height, Height, ball, Ball, goal, Goal, draw, help);
        setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, new Insets(15, 15, 15, 15))));

    }
    
    public int[] calculate(String s){
        char[] string = s.toCharArray();
        int[] data = new int[2];
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
}
