package Graphics;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
/**
 * This class controls the camera with keys
 * @author Jordan
 * @version 1.5
 * @date 20.03
 */
public class CameraController implements EventHandler<KeyEvent>{
    private Graph3D map;
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
        switch(event.getCode()){
            case RIGHT: map.CameraX(0.5); break;
            case LEFT: map.CameraX(-0.5); break;
            case UP: map.CameraY(-0.5); break;
            case DOWN: map.CameraY(0.5); break;
            case Z: map.CameraZ(0.5); break;
            case X: map.CameraZ(-0.5); break;
            case P: map.print(); break;
            case W: map.rotateX(5); break;
            case S: map.rotateX(-5); break;
            case A: map.rotateY(-5); break;
            case D: map.rotateY(5); break;
            case Q: map.rotateZ(5); break;
            case E: map.rotateZ(-5); break;
        }
    }    
}
