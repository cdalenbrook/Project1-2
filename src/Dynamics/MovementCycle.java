package Dynamics;

import Old.Graphics.MapDrawer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

/**
 * Class with movement cycle of the ball
 * @author Jordan
 * @version 1.2
 * @date 20.03
 */
public class MovementCycle {
    private Timeline movement;
    MapDrawer map;
    
    /**
     * Constructor will move the ball
     * @param map instance of MapDrawer
     */
    public MovementCycle(MapDrawer map){
        this.map = map;
        Sphere ball = map.getBall();
        double[] coordinates = map.getTrajectory();
        
        //instance of Timeline
        movement = new Timeline();
        //move the ball once
        movement.setCycleCount(1);
        
        //add keyvalues, changing the coordinates from initial ones to those in the end of trajectory
        KeyValue keyValueX = new KeyValue(ball.translateXProperty(), coordinates[0]);
        KeyValue keyValueY = new KeyValue(ball.translateYProperty(), coordinates[1]);
        KeyValue keyValueZ = new KeyValue(ball.translateZProperty(), coordinates[2]);
        
        //how fast the ball will move
        Duration duration = Duration.millis(2000);
        
        //create a keyframe
        KeyFrame keyFrame = new KeyFrame(duration, keyValueX, keyValueY, keyValueZ);
        
        //add it to the timeline
        movement.getKeyFrames().add(keyFrame);
        
        //play the timeline
        movement.play();
    }
}
