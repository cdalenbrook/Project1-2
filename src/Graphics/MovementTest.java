package Graphics;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import static javafx.scene.input.MouseEvent.MOUSE_DRAGGED;

public class MovementTest implements EventHandler<MouseEvent>{
    MapDrawer map;
    public MovementTest(MapDrawer map){
        this.map = map;
    }
    @Override
    public void handle(MouseEvent event){
        if(event.getEventType().equals(MOUSE_DRAGGED)){
            if(event.getX() <= 375){
                if(map.getBall1()[0] + 5.0 >= event.getX() && map.getBall1()[0] - 5.0 <= event.getX()
                    && map.getBall1()[1] + 5.0 >= event.getY() && map.getBall1()[1] - 5.0 <= event.getY()){
                    map.setBall1(new double[]{event.getX(), event.getY()});
                }
            }
            else{
                if(map.getBall2()[0] + 5.0 >= event.getX() && map.getBall2()[0] - 5.0 <= event.getX()
                    && map.getBall2()[1] + 5.0 >= event.getY() && map.getBall2()[1] - 5.0 <= event.getY()){
                    map.setBall2(new double[]{event.getX(), event.getY()});
                }
            }
        }
    }
}
