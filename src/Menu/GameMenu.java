package Menu;

import Graphics.Graph3D;
import Physics.Physics;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import minigolf.Main;

/**
 * This class creates a GridPane with options for shooting the ball.
 * @author Jordan, Charlotte
 * @version 1.0
 * @date 20.03
 */
public class GameMenu extends GridPane{
    private Slider velocity;
    private Slider angle;
    private Graph3D graph;
    
    /**
     * Constructor
     */
    public GameMenu(){
        Button shoot = new Button("Shoot");
        CheckBox withoutPhysics = new CheckBox("Without physics");
        shoot.setStyle("-fx-font: 22 arial; -fx-base: #6495ED");
        shoot.setMinSize(200, 50);
        shoot.setFocusTraversable(false);
        shoot.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                Timeline timeline = new Timeline();
                if(!withoutPhysics.isSelected()){
                    Physics physics = new Physics(velocity.getValue(), angle.getValue());
                    physics.startMoving();
                }
                else{
                    timeline.setCycleCount(1);  
                    KeyValue keyValueX = new KeyValue(Graph3D.ball.translateXProperty(), Graph3D.LineEndX);
                    KeyValue keyValueY = new KeyValue(Graph3D.ball.translateYProperty(), Graph3D.LineEndY);
                    KeyValue keyValueZ = new KeyValue(Graph3D.ball.translateZProperty(), Graph3D.LineEndZ);
                   
                    EventHandler onFinished = new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t){
                            graph.moveBallTest();
                            angle.setValue(0);
                        }
                    };
                    Duration duration = Duration.millis(2000);

                    KeyFrame keyFrame = new KeyFrame(duration, onFinished, keyValueX, keyValueY, keyValueZ);

                    timeline.getKeyFrames().add(keyFrame);

                    timeline.play(); 
                }
                
                /* AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long l){
                        double x = -((game.getBall().getCenterX() - rangeX + xMax)/300);
                        double y = -((game.getBall().getCenterY() - rangeY + yMax)/300);
                        double height = ((0.1*x) + (0.03*(Math.pow(x, 2.0))) + (0.2*y));
                        if(height < 0 || game.getBall().getCenterX() < 0 || game.getBall().getCenterY() < 0
                                || game.getBall().getCenterX() > rangeX || game.getBall().getCenterY() > rangeY){
                            
                            timeline.stop();
                            this.stop();
                            //physics.stop();
                            game.getBall().setCenterX(startX);
                            game.getBall().setCenterY(startY);
                            game.setLine();
                            
                        }
                    }
                };
                timer.start(); */
        }});
        
        Button quit = new Button("Quit");
        quit.setStyle("-fx-font: 22 arial; -fx-base: #6495ED");
        quit.setMinSize(200, 50);
        quit.setFocusTraversable(false);
        quit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                Stage primaryStage = Main.primaryStage;
                
                StackPane pane = new StackPane();
                pane.getChildren().add(new StartMenu());
                pane.setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, Insets.EMPTY)));

                Scene scene = new Scene(pane);

                primaryStage.setTitle("Crazy Putting!");
                primaryStage.setScene(scene);

                primaryStage.setWidth(325);
                primaryStage.setHeight(425);

                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
            }
        });
        
        GridPane gridPane = new GridPane();
        gridPane.add(shoot, 0, 0);
        gridPane.add(withoutPhysics, 0, 1);
        gridPane.add(quit, 1, 0);
        
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHalignment(withoutPhysics, HPos.CENTER);
        gridPane.setHgap(7.5);
        gridPane.setVgap(20);
        
        add(gridPane, 1, 0);
        add(force(), 0, 0);
        
        setHgap(20);
        setVgap(20);
        setAlignment(Pos.CENTER);
        setBackground(new Background(new BackgroundFill(Color.rgb(186, 216, 227), CornerRadii.EMPTY, new Insets(15, 15, 15, 15))));
        
    }
    /**
     * Create a GridPane with options to control velocity and angle
     * @return GridPane
     */
    public GridPane force(){
        GridPane pane = new GridPane();
        pane.setHgap(20); //horizontal gap in pixels => that's what you are asking for
        pane.setVgap(20); //vertical gap in pixels
        pane.setPadding(new Insets(10, 10, 10, 10));

        //Velocity text and slider:
        Label velocityText = new Label("Specify Velocity:");
        velocityText.setFont(new Font("Arial", 20));

        pane.add(velocityText, 1, 0);
        pane.setHalignment(velocityText, HPos.CENTER);

        velocity = new Slider(0,20,0);
        velocity.setShowTickLabels(true);
        velocity.setShowTickMarks(true);
        velocity.setMajorTickUnit(2);
        velocity.setMinorTickCount(1);
        velocity.setBlockIncrement(1);
        velocity.setFocusTraversable(false);

        pane.add(velocity, 1, 1);

        Label velocityValue = new Label("Velocity Selected: 5" );
        pane.setHalignment(velocityValue, HPos.CENTER);
        pane.add(velocityValue, 1, 2);


        velocity.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                velocityValue.textProperty().setValue(
                        String.valueOf("Velocity Selected: " + (int) velocity.getValue()));
            }
        });

       
        Label angleText = new Label("Specify Angle:");
        angleText.setFont(new Font("Arial", 20));

        pane.add(angleText, 2, 0);
        pane.setHalignment(angleText, HPos.CENTER);

        angle = new Slider(-180,180,0);
        angle.setShowTickLabels(true);
        angle.setShowTickMarks(true);
        angle.setMajorTickUnit(30);
        angle.setMinorTickCount(5);
        angle.setBlockIncrement(5);
        angle.setFocusTraversable(false);

        pane.add(angle, 2, 1);

        Label angleValue = new Label("Angle Selected: 0°" );
        pane.setHalignment(angleValue, HPos.CENTER);
        pane.add(angleValue, 2, 2);

        angle.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                angleValue.textProperty().setValue(
                        String.valueOf("Angle Selected: " + (int) angle.getValue() + "°"));
                double LineEndX = graph.LineRotateX*Math.cos(Math.toRadians(-angle.getValue())) - graph.LineRotateZ*Math.sin(Math.toRadians(-angle.getValue()));
                double LineEndZ = graph.LineRotateZ*Math.cos(Math.toRadians(-angle.getValue())) + graph.LineRotateX*Math.sin(Math.toRadians(-angle.getValue()));
                graph.setCoordinatesLine(LineEndX, LineEndZ);
            }
        });
        
        return pane;
    }
    /**
     * @param graph instance of Graph3D
     */
    public void setGraph(Graph3D graph){
        this.graph = graph;
    }
}
