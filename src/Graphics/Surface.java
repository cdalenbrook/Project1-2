package Graphics;

import javafx.scene.paint.Color;

/**
 * A class representing a surface on which the ball will be. Most of the data here is just an example on
 * how we could/should do it, after completing the physics engine.
 * @author Jordan
 * @version 1.1
 * @date 20.03
 */
public class Surface {
    private SurfaceType surfaceType;
    private Color color;
    private double friction;
    private boolean gameOver;
    private double width;
    private double length;
    private double angle;
    private double XCoordinate;
    private double YCoordinate;
    private double ZCoordinate;
    /**
     * Constructor for boxes -  will create an instance of a surface with different data.
     * @param surfaceType - enum with type of surface
     * @param width - width of this piece of surface
     * @param length - length of this piece of surface
     * @param X - starting coordinate x
     * @param Z  - starting coordinate z
     */
    public Surface(SurfaceType surfaceType, double width, double length, double X, double Y, double Z){
        this.surfaceType = surfaceType;
        this.width = width;
        this.length = length;
        angle = 0;
        XCoordinate = X;
        YCoordinate = Y;
        ZCoordinate = Z;
        //add some more data
        switch(surfaceType){
            case EMPTY:
                friction = 0.0; gameOver = true; color = Color.BLACK; break;
            case GRASS:
                friction = 1.0; gameOver = false; color = Color.GREEN; break;
            case SAND:
                friction = 2.0; gameOver = false; color = Color.SANDYBROWN; break;
            case WATER:
                friction = 1.5; gameOver = true; color = Color.BLUE; break;
        }
            
    }
    /**
     * Constructor for prism - it is used for hills, etc.
     * @param surfaceType - enum with type of surface
     * @param width - width of this piece of surface
     * @param length - length of this piece of surface
     * @param X - starting coordinate x
     * @param Z  - starting coordinate z
     * @param angle - tilted angle of surface of prism
     */
    public Surface(SurfaceType surfaceType, double width, double length, double X, double Y, double Z, double angle){
        this.surfaceType = surfaceType;
        this.width = width;
        this.length = length;
        this.angle = angle;
        XCoordinate = X;
        YCoordinate = Y;
        ZCoordinate = Z;
        switch(surfaceType){
            case GRASS_UP:
                friction = 1.0; gameOver = true; color = Color.DARKGREEN; break;
            case GRASS_DOWN:
                friction = 1.0; gameOver = false; color = Color.DARKGREEN; break;
            case SAND_UP:
                friction = 2.0; gameOver = false; color = Color.ORANGE; break;
            case SAND_DOWN:
                friction = 2.0; gameOver = true; color = Color.ORANGE; break;
        }
    }
    /**
     * @return X coordinate
     */
    public double getX(){
        return XCoordinate;
    }
    /**
     * @return Y coordinate
     */
    public double getY(){
        return YCoordinate;
    }
    //testing
    public void setY(double Y){
        YCoordinate = Y;
    }
    /**
     * 
     * @return Z coordinate 
     */
    public double getZ(){
        return ZCoordinate;
    }
    /**
     * @return colour
     */
    public Color getColor(){
        return color;
    }
    /**
     * @param width - set width
     */
    public void setWidth(double width){
        this.width = width;
    }
    /**
     * @param length - set length
     */
    public void setLength(double length){
        this.length = length;
    }
    /**
     * @param angle - set angle
     */
    public void setAngle(double angle){
        this.angle = angle;
    }
    /**
     * @return width
     */
    public double getWidth(){
        return width;
    }
    /**
     * @return length
     */
    public double getLength(){
        return length;
    }
    /**
     * @return angle
     */
    public double getAngle(){
        return angle;
    }
    /**
     * @return friction
     */
    public double getFriction(){
        return friction;
    }
    /**
     * @return boolean stating if Game is Over
     */
    public boolean getGameOver(){
        return gameOver;
    }
    /**
     * @return enum surfaceType
     */
    public SurfaceType getSurfaceType(){
        return surfaceType;
    }
}
