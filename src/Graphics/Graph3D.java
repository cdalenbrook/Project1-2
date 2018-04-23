package Graphics;

import Setup.Function;
import Setup.ImageType;
import static Setup.ImageType.BUTTON;
import static Setup.ImageType.GRAPH;
import static Setup.ImageType.IMAGE;
import Setup.Level;
import java.util.ArrayList;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Create the visual representation of the map
 * @author Jordan
 */
public class Graph3D extends Parent{
    public static Sphere ball;
    private MeshView meshView;
    private static PerspectiveCamera camera;
    private Cylinder line;
    private Function graph3D;
    private static double ballSize;
    private int GraphAmplification;
    
    public static double LineStartX, LineStartY, LineStartZ, LineEndX, LineEndY, LineEndZ;
    public double LineRotateX, LineRotateZ;
    private int RangeX, RangeZ, EndX, EndZ;
    
    private Group root;
    
    private double mousePosX, mousePosY;
    private double mouseOldX, mouseOldY;
    
    private Rotate xAxis = new Rotate(0, Rotate.X_AXIS);
    private Rotate yAxis = new Rotate(0, Rotate.Y_AXIS);
    private Rotate zAxis = new Rotate(0, Rotate.Z_AXIS);
    
    /**
     * Constructor
     * @param level instance of Level
     * @param imageType the way we want to draw the image
     */
    public Graph3D(Level level, ImageType imageType){
        root = new Group();
        root.setAutoSizeChildren(false);
        
        root.getChildren().add(drawMesh(level, imageType));
        
        createBall(level);
        root.getChildren().add(ball);
        
        createLine();
        root.getChildren().add(line);
        
        drawTrees(level);
        
        camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll(
                xAxis, yAxis, zAxis);
        
        //camera.setTranslateX(0);
        //camera.setTranslateY(-startY - 1);
        
        camera.setTranslateZ(-ballSize*100 + ball.getTranslateZ());
        camera.setFarClip(1000);
        //camera.setNearClip(0);
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(Color.WHITE);
        root.getChildren().add(camera);
        root.getChildren().add(ambient);
        
        SubScene subScene = new SubScene(root, 850, 550, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        subScene.setOnMousePressed(me -> {
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        subScene.setOnMouseDragged(me -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            xAxis.setAngle(xAxis.getAngle() - (mousePosY - mouseOldY));
            yAxis.setAngle(yAxis.getAngle() + (mousePosX - mouseOldX));
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;

        });
        getChildren().add(subScene);   
    }
    
    
    //test
    public Graph3D(ArrayList<char[]> functionData, int startX, int endX, int startZ, int endZ, int ImageAmplification, int GraphAmplification, int HeightPrecision, int BallX, int BallZ, int GoalX, int GoalZ){
        this.GraphAmplification = GraphAmplification;
        Group root = new Group();
        root.setAutoSizeChildren(false);
       
        graph3D = new Function(functionData, startX, endX, startZ, endZ, GraphAmplification);
        
        int StartX = startX*GraphAmplification;
        EndX = endX*GraphAmplification;
        int StartZ = startZ*GraphAmplification;
        EndZ = endZ*GraphAmplification;
        
        RangeX = EndX - StartX;
        RangeZ = EndZ - StartZ;
        
        TriangleMesh mesh = new TriangleMesh();
        
        float startY = graph3D.getHeight()[BallX + RangeX - EndX][BallZ + RangeZ - EndZ]/HeightPrecision;
        for (float x = StartX; x < EndX; x++) {
            for (float z = StartZ; z < EndZ; z++) {
                float y = graph3D.getHeight()[(int)x + RangeX - EndX][(int)z + RangeZ - EndZ]/HeightPrecision;
                mesh.getPoints().addAll(x/GraphAmplification, -y, z/GraphAmplification);
            }
        }

        for (float x = 0; x < RangeX - 1; x++) {
            for (float z = 0; z < RangeZ - 1; z++) {

                float x0 = x / RangeX;
                float z0 = z / RangeZ;
                float x1 = (x + 1) / RangeX;
                float z1 = (z + 1) / RangeZ;

                mesh.getTexCoords().addAll( //
                    x0, z0, // 0, top-left
                    x0, z1, // 1, bottom-left
                    x1, z1, // 2, top-right
                    x1, z1 // 3, bottom-right
                );
            }
        }
        
        for (int x = 0; x < RangeX - 1; x++) {
            for (int z = 0; z < RangeZ - 1; z++) {
                
                int tl = x * RangeX + z; // top-left
                int bl = x * RangeZ + z + 1; // bottom-left
                int tr = (x + 1) * RangeX + z; // top-right
                int br = (x + 1) * RangeZ + z + 1; // bottom-right

                int offset = (x * (RangeX - 1) + z ) * 8/2;
                
                // working
                mesh.getFaces().addAll(bl, offset + 1, tl, offset + 0, tr, offset + 2);
                mesh.getFaces().addAll(tr, offset + 2, br, offset + 3, bl, offset + 1);
                
                
                //mesh.getFaces().addAll(bl, tl, tr);
                //mesh.getFaces().addAll(tr, br, bl);

            }
        }
        
        Function graph2D = new Function(functionData, startX, endX, startZ, endZ, ImageAmplification);
        Graph2D image = new Graph2D(startX, endX, startZ, endZ, ImageAmplification, graph2D);
        Image diffuseMap = image.getImage();
        
        ballSize = 0.01;
        
        ball = drawBall(ballSize, BallX, -startY - ballSize, BallZ);
        LineStartX = BallX;
        LineStartZ = BallZ;
        LineEndX = LineStartX;
        LineEndZ = LineStartZ + ballSize*10;
        LineRotateX = LineEndX;
        LineRotateZ = LineEndZ;
        
        root.getChildren().add(ball);
        
        line = new Cylinder(ballSize/10, ballSize*10);
        line.setMaterial(new PhongMaterial(Color.BLACK));
        LineStartY =  -ballSize -graph3D.getHeight()[(int)(LineStartX*GraphAmplification) + RangeX - EndX][(int)(LineStartZ*GraphAmplification) + RangeZ - EndZ];
        LineEndY = -ballSize -graph3D.getHeight()[(int)(LineEndX*GraphAmplification) + RangeX - EndX][(int)(LineEndZ*GraphAmplification) + RangeZ - EndZ];
        double distance = Math.sqrt(Math.pow(LineEndX - LineStartX, 2) + Math.pow(LineEndY - LineStartY, 2) + Math.pow(LineEndZ - LineStartZ, 2));
        
        rotateLine(new Point3D(LineStartX, LineStartY ,LineStartZ),
                new Point3D(LineEndX, LineEndY ,LineEndZ), distance);
       
        root.getChildren().add(line);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(diffuseMap);
        //material.setSpecularColor(Color.WHITE);
        
        meshView = new MeshView(mesh);
        //meshView.setMaterial(new PhongMaterial(Color.BLACK));
        meshView.setMaterial(material);
        meshView.setCullFace(CullFace.NONE);
        meshView.setDrawMode(DrawMode.FILL);
        //meshView.setDepthTest(DepthTest.ENABLE);
        root.getChildren().add(meshView);
        camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll(
                xAxis, yAxis, zAxis);
        
        
        camera.setTranslateZ(-ballSize*100 + ball.getTranslateZ());
        camera.setFarClip(1000);
        
        
        //camera.setNearClip(0);
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(Color.WHITE);
        root.getChildren().add(camera);
        root.getChildren().add(ambient);
        
        //create a subscene for where the actual game will be represented
        SubScene subScene = new SubScene(root, 850, 550, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        subScene.setOnMousePressed(me -> {
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        subScene.setOnMouseDragged(me -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            xAxis.setAngle(xAxis.getAngle() - (mousePosY - mouseOldY));
            yAxis.setAngle(yAxis.getAngle() + (mousePosX - mouseOldX));
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;

        });
        getChildren().add(subScene);
        
    }
    
    /**
     * Method that will move the ball on the map
     * @param x new xCoordinate
     * @param y new yCoordinate
     * @param z new zCoordinate
     */
    public static void moveBall(double x, double y, double z){
        ball.setTranslateX(x);
        ball.setTranslateY(-y - ballSize);
        ball.setTranslateZ(z);
        camera.setTranslateX(ball.getTranslateX());
        camera.setTranslateY(-y);
        camera.setTranslateZ(-ballSize*100 + ball.getTranslateZ());
    }
    //test
    public void moveBallTest(){
        LineEndY = -ballSize -graph3D.getHeight()[(int)(LineEndX*GraphAmplification) + RangeX - EndX][(int)(LineEndZ*GraphAmplification) + RangeZ - EndZ];
        ball.setTranslateX(LineEndX);
        ball.setTranslateY(LineEndY);
        ball.setTranslateZ(LineEndZ);
        LineStartX = LineEndX;
        LineStartZ = LineEndZ;
        LineEndZ = LineStartZ + ballSize*10;
        LineRotateX = LineEndX;
        LineRotateZ = LineEndZ;
        setCoordinatesLine(LineEndX, LineEndZ);
        camera.setTranslateX(ball.getTranslateX());
        camera.setTranslateY(LineEndY + ballSize);
        camera.setTranslateZ(-ballSize*100 + ball.getTranslateZ());
    }
    /**
     * Draws a ball
     * @param BallSize desired ball size
     * @param BallX starting coordinate x
     * @param BallY starting coordinate y
     * @param BallZ starting coordinate z
     * @return Sphere representation of the ball
     */
    private Sphere drawBall(double BallSize, double BallX, double BallY, double BallZ){
        Sphere ball = new Sphere(BallSize);
        ball.setMaterial(new PhongMaterial(Color.WHITE));
        ball.setTranslateX(BallX);
        ball.setTranslateY(BallY);
        ball.setTranslateZ(BallZ);
        return ball;
    }
    
    private void createBall(Level level){
        double BallX = level.getBall()[0];
        double BallZ = level.getBall()[1];
        ballSize = 1.0/(double)GraphAmplification;
        
        float startY = graph3D.getHeight()[(int)BallX*GraphAmplification + RangeX - EndX][(int)BallZ*GraphAmplification + RangeZ - EndZ];
        
        ball = drawBall(ballSize, BallX, -startY - ballSize, BallZ);
        LineStartX = BallX;
        LineStartZ = BallZ;
        LineEndX = LineStartX;
        LineEndZ = LineStartZ + ballSize*10;
        LineRotateX = LineEndX;
        LineRotateZ = LineEndZ;
    }
    
    /**
     * State the new end coordinates of the line
     * @param LineEndX
     * @param LineEndZ 
     */
    public void setCoordinatesLine(double LineEndX, double LineEndZ){
        this.LineEndX = LineEndX;
        this.LineEndZ = LineEndZ;
        LineStartY =  -ballSize -graph3D.getHeight()[(int)(LineStartX*GraphAmplification) + RangeX - EndX][(int)(LineStartZ*GraphAmplification) + RangeZ - EndZ];
        LineEndY = -ballSize -graph3D.getHeight()[(int)(LineEndX*GraphAmplification) + RangeX - EndX][(int)(LineEndZ*GraphAmplification) + RangeZ - EndZ];
        double distance = Math.sqrt(Math.pow(LineEndX - LineStartX, 2) + Math.pow(LineEndY - LineStartY, 2) + Math.pow(LineEndZ - LineStartZ, 2));
        rotateLine(new Point3D(LineStartX, LineStartY ,LineStartZ),
                new Point3D(LineEndX, LineEndY ,LineEndZ), distance);
       }
    
    /**
     * Rotates the line based on:
     * @param origin Starting point
     * @param target End Point
     * @param distance Distance between the two points
     */
    private void rotateLine(Point3D origin, Point3D target, double distance) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);
        line.setHeight(distance);
        line.getTransforms().clear();
        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);
    }
    
    private void createLine(){
        line = new Cylinder(ballSize/10, ballSize*10);
        line.setMaterial(new PhongMaterial(Color.BLACK));
        LineStartY =  -ballSize -graph3D.getHeight()[(int)(LineStartX*GraphAmplification) + RangeX - EndX][(int)(LineStartZ*GraphAmplification) + RangeZ - EndZ];
        LineEndY = -ballSize -graph3D.getHeight()[(int)(LineEndX*GraphAmplification) + RangeX - EndX][(int)(LineEndZ*GraphAmplification) + RangeZ - EndZ];
        double distance = Math.sqrt(Math.pow(LineEndX - LineStartX, 2) + Math.pow(LineEndY - LineStartY, 2) + Math.pow(LineEndZ - LineStartZ, 2));
        
        rotateLine(new Point3D(LineStartX, LineStartY ,LineStartZ),
                new Point3D(LineEndX, LineEndY ,LineEndZ), distance);
    }
    
    /**
     * @return the map representation as a MeshView
     */
    public MeshView getMesh(){
        return meshView;
    }
     /**
      * move camera on z axis
     * @param value
     */
    public void CameraZ(double value){
        camera.setTranslateZ(camera.getTranslateZ() + value);
    }
    /**
     * move camera on y axis
     * @param value 
     */
    public void CameraY(double value){
        camera.setTranslateY(camera.getTranslateY() + value);
    }
    /**
     * move camera on x axis
     * @param value 
     */
    public void CameraX(double value){
        camera.setTranslateX(camera.getTranslateX() + value);
    }
    /**
     * prints data
     */
    public void print(){
        System.out.println(camera.getFarClip() + " " + camera.getNearClip() + " " +  camera.getTranslateZ() + " " + camera.getTranslateY() + " " + camera.getTranslateX() + " " + xAxis.getAngle() + " " + yAxis.getAngle() + " " + zAxis.getAngle());
    }
    /**
     * Rotate camera around X axis
     * @param angle 
     */
    public void rotateX(double angle){
        xAxis.setAngle(xAxis.getAngle() + angle);
    }
    /**
     * Rotate camera around Y axis
     * @param angle 
     */
    public void rotateY(double angle){
        yAxis.setAngle(yAxis.getAngle() + angle);
    }
    /**
     * Rotate camera around Z axis
     * @param angle 
     */
    public void rotateZ(double angle){
        zAxis.setAngle(zAxis.getAngle() + angle);
    }
    private MeshView drawMesh(Level level, ImageType imageType){
        Level data = level;
        double startX = data.getRangeX()[0];
        double endX = data.getRangeX()[1];
        double startZ = data.getRangeY()[0];
        double endZ = data.getRangeY()[1];
        double BallX = data.getBall()[0];
        double BallZ = data.getBall()[1];
        
        GraphAmplification = 0;
        switch(imageType){
            case BUTTON: GraphAmplification = (int)(200/(endX - startX)); break;
            case GRAPH: GraphAmplification = (int)(400/(endX - startX)); break;
            case IMAGE: GraphAmplification = (int)(600/(endX - startX)); break;
            case LEVELCREATIONMAP: GraphAmplification = (int)(600/(endX - startX)); break;
        }
       
        graph3D = new Function(level, imageType);
        
        int StartX = (int)startX*GraphAmplification;
        EndX = (int)endX*GraphAmplification;
        int StartZ = (int)startZ*GraphAmplification;
        EndZ = (int)endZ*GraphAmplification;
        
        RangeX = EndX - StartX;
        RangeZ = EndZ - StartZ;
        
        TriangleMesh mesh = new TriangleMesh();
        
        float startY = graph3D.getHeight()[(int)BallX*GraphAmplification + RangeX - EndX][(int)BallZ*GraphAmplification + RangeZ - EndZ];
        for (float x = StartX; x < EndX; x++) {
            for (float z = StartZ; z < EndZ; z++) {
                float y = graph3D.getHeight()[(int)x + RangeX - EndX][(int)z + RangeZ - EndZ];
                mesh.getPoints().addAll(x/GraphAmplification, -y, z/GraphAmplification);
            }
        }

        for (float x = 0; x < RangeX - 1; x++) {
            for (float z = 0; z < RangeZ - 1; z++) {

                float x0 = x / RangeX;
                float z0 = z / RangeZ;
                float x1 = (x + 1) / RangeX;
                float z1 = (z + 1) / RangeZ;

                mesh.getTexCoords().addAll( //
                    x0, z0, // 0, top-left
                    x0, z1, // 1, bottom-left
                    x1, z1, // 2, top-right
                    x1, z1 // 3, bottom-right
                );
            }
        }
        
        for (int x = 0; x < RangeX - 1; x++) {
            for (int z = 0; z < RangeZ - 1; z++) {
                
                int tl = x * RangeX + z; // top-left
                int bl = x * RangeZ + z + 1; // bottom-left
                int tr = (x + 1) * RangeX + z; // top-right
                int br = (x + 1) * RangeZ + z + 1; // bottom-right

                int offset = (x * (RangeX - 1) + z ) * 8/2;
                
                // working
                mesh.getFaces().addAll(bl, offset + 1, tl, offset + 0, tr, offset + 2);
                mesh.getFaces().addAll(tr, offset + 2, br, offset + 3, bl, offset + 1);

            }
        }
        
        
        
        Function graph2D = new Function(level, ImageType.IMAGE);
        Graph2D image = new Graph2D(level, ImageType.IMAGE, graph2D);
        Image diffuseMap = image.getImage();
        
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(diffuseMap);
        
        MeshView meshView = new MeshView(mesh);
        meshView.setMaterial(material);
        meshView.setCullFace(CullFace.NONE);
        meshView.setDrawMode(DrawMode.FILL);
        
        return meshView;
    }
    
    private void drawTrees(Level data){
        ArrayList<double[]> trees = data.getTree();
        for(double[] coordinate: trees){
            double coordinateY = -graph3D.getHeight()[(int)(coordinate[0]*GraphAmplification) + RangeX - EndX][(int)(coordinate[1]*GraphAmplification) + RangeZ - EndZ];
            Tree tree = new Tree(0.01f, 0.1f, 0.03f);
            MeshView trunk = tree.getTrunk();
            trunk.setTranslateX(coordinate[0]);
            trunk.setTranslateY(coordinateY);
            trunk.setTranslateZ(coordinate[1]);
            root.getChildren().add(trunk);
            MeshView leaves = tree.getLeaves();
            leaves.setTranslateX(coordinate[0]);
            leaves.setTranslateY(coordinateY);
            leaves.setTranslateZ(coordinate[1]);
            root.getChildren().add(leaves);
        }
    }
}
