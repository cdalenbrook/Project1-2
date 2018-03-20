package Graphics;

import static java.lang.Math.sin;
import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Most of visualisation happens here. The map is drawn here.
 * @author Jordan
 * @version 2.6
 * @date 20.03
 */
public class MapDrawer extends Parent{
    //an arraylist containing the data needed to build a the map
    private ArrayList<Surface> mapData = new ArrayList<>();
    //rotation points for the 3D Camera
    private Rotate xAxis = new Rotate(330, Rotate.X_AXIS);
    private Rotate yAxis = new Rotate(0, Rotate.Y_AXIS);
    private Rotate zAxis = new Rotate(0, Rotate.Z_AXIS);
    
    //3D camera
    private PerspectiveCamera camera;
    
    //this is the height the ball should be at, could change depending on going on a hill.
    //The calculations and representation for this could be a lot better
    private double Y = 0;
    
    //a sphere representing the ball
    Ball ball;
    //a really sthreched box serving as a trajectory for the ball - just an example,
    //end version could be a lot better
    MeshView trajectory;
    
    /**
     * Constructor drawing everything
     */
    public MapDrawer(){
        //add example map
        addSomeData();
        //great a group where all game parts should be added
        Group root = new Group();
        root.setAutoSizeChildren(false);
        
        //create an instance of Ball
        ball = new Ball();
        //set ball position
        ball.setTranslateX(ball.getRadius() + 7.5);
        ball.setTranslateY(-ball.getRadius() - Y - 1.0);
        ball.setTranslateZ(ball.getRadius());
        //add it to group
        root.getChildren().add(ball);
        
        //create an example trajectory
        Box trajectoryMesh = new Box((float)ball.getRadius()/2, (float)ball.getRadius()/2, 5.0f);
        trajectory = new MeshView(trajectoryMesh);
        trajectory.setCullFace(CullFace.NONE);
        trajectory.setDrawMode(DrawMode.FILL);
        trajectory.setMaterial(new PhongMaterial(Color.RED));
        
        trajectory.setTranslateX(ball.getTranslateX());
        trajectory.setTranslateY(ball.getTranslateY());
        trajectory.setTranslateZ(trajectoryMesh.getDepth()/2 + ball.getRadius()/2);
        root.getChildren().add(trajectory);
        
        //draw the actual map from mapData
        for(Surface surface: mapData){
            switch(surface.getSurfaceType()){
                case EMPTY:
                case GRASS:
                case SAND:
                case WATER:
                    //if its one of the above it will create a box
                    Box boxMesh = new Box((float)surface.getWidth(), (float)2.0, (float)surface.getLength());
                    MeshView box = new MeshView(boxMesh);
                    box.setCullFace(CullFace.NONE);
                    box.setDrawMode(DrawMode.FILL);
                    box.setMaterial(new PhongMaterial(surface.getColor()));
                    box.setTranslateX(boxMesh.getWidth()/2 + surface.getX());
                    box.setTranslateZ(boxMesh.getDepth()/2 + surface.getZ());
                    box.setTranslateY(- Y);
                    surface.setY(Y);
                    root.getChildren().add(box);
                    break;
                case GRASS_UP:
                case GRASS_DOWN:
                case SAND_UP:
                case SAND_DOWN:
                    //create a prism
                    Prism triangleMesh = new Prism((float)surface.getWidth(), (float)surface.getLength(), (float)surface.getAngle());
                    MeshView triangle = new MeshView(triangleMesh);
                    triangle.setCullFace(CullFace.NONE);
                    triangle.setDrawMode(DrawMode.FILL);
                    triangle.setMaterial(new PhongMaterial(surface.getColor()));
                    triangle.setTranslateX(surface.getX());
                    triangle.setTranslateZ(surface.getZ());
                    triangle.setTranslateY(-1.0);
                    root.getChildren().add(triangle);
                    Y += triangleMesh.getHeight(); 
                    break;
            }
        }
        //instance of camera
        camera = new PerspectiveCamera(true);
        //add transformations to camera
        camera.getTransforms().addAll(
                xAxis, yAxis, zAxis, new Translate(7.5, -7.5, -35));
        
        root.getChildren().add(camera);
        
        //create a subscene for where the actual game will be represented
        SubScene subScene = new SubScene(root, 800, 800, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        getChildren().add(subScene);
    }
    /**
     * Example method with example data for map1
     */
    public void addSomeData(){
        mapData.add(new Surface(SurfaceType.GRASS, 10.0, 7.5, 0.0, 0.0, 0.0));
        mapData.add(new Surface(SurfaceType.WATER, 5.0, 7.5, 10.0, 0.0, 0.0));
        mapData.add(new Surface(SurfaceType.GRASS_UP, 15.0, 10.0, 0.0, 0.0, 7.5, 30.0));
        mapData.add(new Surface(SurfaceType.SAND, 15.0, 7.5, 0, 0.0, 16.19));
    }
    /**
     * Zoom in and out
     * @param value of camera Z coordinate
     */
    public void CameraZoom(double value){
        camera.setTranslateZ(-35 + value);
    }
    /**
     * Rotate camera around X axis
     * @param angle 
     */
    public void rotateX(double angle){
        xAxis.setAngle(angle);
    }
    /**
     * Rotate camera around Y axis
     * @param angle 
     */
    public void rotateY(double angle){
        yAxis.setAngle(angle);
    }
    /**
     * Rotate camera around Z axis
     * @param angle 
     */
    public void rotateZ(double angle){
        zAxis.setAngle(angle);
    }
    /**
     * @return instance of ball
     */
    public Sphere getBall(){
        return ball;
    }
    /**
     * @return array list, with coordinates, where ball will stop
     */
    public double[] getTrajectory(){
        return new double[]{ball.getTranslateX(), ball.getTranslateY(), ball.getTranslateZ() + 5.0};
    }
    //idea
    public void detectSurface(){
        for(Surface surface: mapData){
            switch(surface.getSurfaceType()){
                case EMPTY:
                case GRASS:
                case SAND:
                case WATER:
                    if(ball.getTranslateZ() >= surface.getZ() &&
                            ball.getTranslateZ() < surface.getZ() + surface.getLength() &&
                            ball.getTranslateX() >= surface.getX() &&
                            ball.getTranslateX() < surface.getX() + surface.getWidth()){
                        double YCoordinate = (surface.getLength()*sin(surface.getAngle()))/sin(90.0 - surface.getAngle());
                        ball.setTranslateY(-YCoordinate - ball.getRadius() - 1.0);
                        System.out.println(surface.getSurfaceType());
                    }
                    break;
                case GRASS_UP:
                case GRASS_DOWN:
                case SAND_UP:
                case SAND_DOWN:
                    Prism triangleMesh = new Prism((float)surface.getWidth(), (float)surface.getLength(), (float)surface.getAngle());
                    if(ball.getTranslateZ() >= surface.getZ() &&
                            ball.getTranslateZ() < surface.getZ() + triangleMesh.getDepth() &&
                            ball.getTranslateX() >= surface.getX() &&
                            ball.getTranslateX() < surface.getX() + surface.getWidth()){
                        double YCoordinate = (ball.getTranslateZ()*sin(surface.getAngle()))/(sin(90.0 - surface.getAngle()));
                        ball.setTranslateY(-YCoordinate - ball.getRadius() - 1.0);
                        System.out.println(ball.getTranslateY());
                        System.out.println(surface.getSurfaceType());
                    }
                    break;
            }
            
        }
    }
}
