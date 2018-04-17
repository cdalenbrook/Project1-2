package Graphics;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
/**
 * This class controls the camera with keys
 * @author Jordan
 * @version 1.5
 * @date 20.03
 */
public class CameraController implements EventHandler<KeyEvent>{
    private Graph3D map;
    
    private double zoom = 0;
    /**
     * Constructor takes as a parameter the MapDrawer so it can call methods in it
     * @param map an instance of MapDrawer
     */
    public CameraController(Graph3D map){
        this.map = map;
    }
    /**
     * Method handle handles the events. If a button is pressed, angle is changed.
     * @param event a KeyEvent
     */
    @Override
    public void handle(KeyEvent event){
        if(event.getCode() == KeyCode.RIGHT){
            map.rotateY(10);
        }
        else if(event.getCode() == KeyCode.LEFT){
            map.rotateY(-10);
        }
        else if(event.getCode() == KeyCode.UP){
            map.rotateX(10);
        }
        else if(event.getCode() == KeyCode.DOWN){
            map.rotateX(-10);
        }
        else if(event.getCode() == KeyCode.Z){
            map.rotateZ(10);
        }
        else if(event.getCode() == KeyCode.X){
            map.rotateZ(-10);
        }
        else if(event.getCode() == KeyCode.N){
            zoom+= 1;
            map.CameraZoom(+1);
        }
        else if(event.getCode() == KeyCode.M){
            zoom-= 1;
            map.CameraZoom(-1);
        }
        else if(event.getCode() == KeyCode.P){
            map.print();
        }
        else if(event.getCode() == KeyCode.C){
            map.CameraY(1);
        }
        else if(event.getCode() == KeyCode.V){
            map.CameraY(-1);
        }
        else if(event.getCode() == KeyCode.H){
            map.CameraX(1);
        }
        else if(event.getCode() == KeyCode.J){
            map.CameraX(-1);
        }
    }    
}
