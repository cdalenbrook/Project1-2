package Graphics;

import Setup.DataReader;
import Setup.Function;
import Setup.FunctionType;
import static Setup.FunctionType.BUTTON;
import static Setup.FunctionType.GRAPH;
import static Setup.FunctionType.IMAGE;
import Setup.Level;
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

public class Graph3D extends Parent{
    private MeshView meshView;
    private PerspectiveCamera camera;
    private Cylinder line;
    private Function graph3D;
    private double ballSize;
    private int GraphAmplification;
    
    public double LineStartX, LineStartZ, LineEndX, LineEndZ;
    public double LineRotateX, LineRotateZ;
    private int xRange, zRange, xMax, zMax;
    
    private Rotate xAxis = new Rotate(0, Rotate.X_AXIS);
    private Rotate yAxis = new Rotate(0, Rotate.Y_AXIS);
    private Rotate zAxis = new Rotate(0, Rotate.Z_AXIS);
    
    public Graph3D(int level, FunctionType functionType){
        Level data = DataReader.getData().get(level);
        double xmin = data.getRangeX()[0];
        double xmax = data.getRangeX()[1];
        double zmin = data.getRangeY()[0];
        double zmax = data.getRangeY()[1];
        double BallX = data.getBall()[0];
        double BallZ = data.getBall()[1];
        double GoalX = data.getGoal()[0];
        double GoalZ = data.getGoal()[1];
        float HeightPrecision = (float)data.getPrecision();
        
        GraphAmplification = 0;
        switch(functionType){
            case BUTTON: GraphAmplification = (int)(200/(xmax - xmin)); break;
            case GRAPH: GraphAmplification = (int)(400/(xmax - xmin)); break;
            case IMAGE: GraphAmplification = (int)(600/(xmax - xmin)); break;
        }
        
        Group root = new Group();
        root.setAutoSizeChildren(false);
       
        graph3D = new Function(level, functionType);
        
        int xMin = (int)xmin*GraphAmplification;
        xMax = (int)xmax*GraphAmplification;
        int zMin = (int)zmin*GraphAmplification;
        zMax = (int)zmax*GraphAmplification;
        
        xRange = xMax - xMin;
        zRange = zMax - zMin;
        
        TriangleMesh mesh = new TriangleMesh();
        
        float startY = graph3D.getHeight()[(int)BallX*GraphAmplification + xRange - xMax][(int)BallZ*GraphAmplification + zRange - zMax]/HeightPrecision;
        for (float x = xMin; x < xMax; x++) {
            for (float z = zMin; z < zMax; z++) {
                float y = graph3D.getHeight()[(int)x + xRange - xMax][(int)z + zRange - zMax]/HeightPrecision;
                mesh.getPoints().addAll(x/GraphAmplification, -y, z/GraphAmplification);
            }
        }

        for (float x = 0; x < xRange - 1; x++) {
            for (float z = 0; z < zRange - 1; z++) {

                float x0 = x / xRange;
                float z0 = z / zRange;
                float x1 = (x + 1) / xRange;
                float z1 = (z + 1) / zRange;

                mesh.getTexCoords().addAll( //
                    x0, z0, // 0, top-left
                    x0, z1, // 1, bottom-left
                    x1, z1, // 2, top-right
                    x1, z1 // 3, bottom-right
                );
            }
        }
        
        for (int x = 0; x < xRange - 1; x++) {
            for (int z = 0; z < zRange - 1; z++) {
                
                int tl = x * xRange + z; // top-left
                int bl = x * zRange + z + 1; // bottom-left
                int tr = (x + 1) * xRange + z; // top-right
                int br = (x + 1) * zRange + z + 1; // bottom-right

                int offset = (x * (xRange - 1) + z ) * 8/2;
                
                // working
                mesh.getFaces().addAll(bl, offset + 1, tl, offset + 0, tr, offset + 2);
                mesh.getFaces().addAll(tr, offset + 2, br, offset + 3, bl, offset + 1);
                
                
                //mesh.getFaces().addAll(bl, tl, tr);
                //mesh.getFaces().addAll(tr, br, bl);

            }
        }
        
        Function graph2D = new Function(level, FunctionType.IMAGE);
        Graph2D image = new Graph2D(level, FunctionType.IMAGE, graph2D);
        Image diffuseMap = image.getImage();
        
        ballSize = 0.01;
        
        Sphere ball = drawBall(ballSize, BallX, -startY - ballSize, BallZ);
        LineStartX = BallX;
        LineStartZ = BallZ;
        LineEndX = LineStartX;
        LineEndZ = LineStartZ + ballSize*10;
        LineRotateX = LineEndX;
        LineRotateZ = LineEndZ;
        
        root.getChildren().add(ball);
        
        line = new Cylinder(ballSize/10, ballSize*10);
        line.setMaterial(new PhongMaterial(Color.BLACK));
        double LineStartY =  -ballSize -graph3D.getHeight()[(int)(LineStartX*GraphAmplification) + xRange - xMax][(int)(LineStartZ*GraphAmplification) + zRange - zMax];
        double LineEndY = -ballSize -graph3D.getHeight()[(int)(LineEndX*GraphAmplification) + xRange - xMax][(int)(LineEndZ*GraphAmplification) + zRange - zMax];
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
        
        //camera.setTranslateX(0);
        //camera.setTranslateY(-startY - 1);
        camera.setTranslateZ(-ballSize*100);
        camera.setFarClip(1000);
        //camera.setNearClip(0);
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(Color.WHITE);
        root.getChildren().add(camera);
        root.getChildren().add(ambient);
        
        //test
        /* Tree tree = new Tree(0.01f, 0.1f, 0.03f);
        MeshView trunk = tree.getTrunk();
        trunk.setTranslateX(BallX);
        trunk.setTranslateY(-startY);
        trunk.setTranslateZ(BallZ);
        root.getChildren().add(trunk);
        MeshView leaves = tree.getLeaves();
        leaves.setTranslateX(BallX);
        leaves.setTranslateY(-startY);
        leaves.setTranslateZ(BallZ);
        root.getChildren().add(leaves); */
        
        //create a subscene for where the actual game will be represented
        SubScene subScene = new SubScene(root, 850, 550, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        getChildren().add(subScene);
        
    }
    
    public Graph3D(int xmin, int xmax, int zmin, int zmax, int ImageAmplification, int GraphAmplification, int HeightPrecision, int BallX, int BallZ, int GoalX, int GoalZ){
        this.GraphAmplification = GraphAmplification;
        Group root = new Group();
        root.setAutoSizeChildren(false);
       
        graph3D = new Function(xmin, xmax, zmin, zmax, GraphAmplification);
        
        int xMin = xmin*GraphAmplification;
        xMax = xmax*GraphAmplification;
        int zMin = zmin*GraphAmplification;
        zMax = zmax*GraphAmplification;
        
        xRange = xMax - xMin;
        zRange = zMax - zMin;
        
        TriangleMesh mesh = new TriangleMesh();
        
        float startY = graph3D.getHeight()[BallX + xRange - xMax][BallZ + zRange - zMax]/HeightPrecision;
        for (float x = xMin; x < xMax; x++) {
            for (float z = zMin; z < zMax; z++) {
                float y = graph3D.getHeight()[(int)x + xRange - xMax][(int)z + zRange - zMax]/HeightPrecision;
                mesh.getPoints().addAll(x/GraphAmplification, -y, z/GraphAmplification);
            }
        }

        for (float x = 0; x < xRange - 1; x++) {
            for (float z = 0; z < zRange - 1; z++) {

                float x0 = x / xRange;
                float z0 = z / zRange;
                float x1 = (x + 1) / xRange;
                float z1 = (z + 1) / zRange;

                mesh.getTexCoords().addAll( //
                    x0, z0, // 0, top-left
                    x0, z1, // 1, bottom-left
                    x1, z1, // 2, top-right
                    x1, z1 // 3, bottom-right
                );
            }
        }
        
        for (int x = 0; x < xRange - 1; x++) {
            for (int z = 0; z < zRange - 1; z++) {
                
                int tl = x * xRange + z; // top-left
                int bl = x * zRange + z + 1; // bottom-left
                int tr = (x + 1) * xRange + z; // top-right
                int br = (x + 1) * zRange + z + 1; // bottom-right

                int offset = (x * (xRange - 1) + z ) * 8/2;
                
                // working
                mesh.getFaces().addAll(bl, offset + 1, tl, offset + 0, tr, offset + 2);
                mesh.getFaces().addAll(tr, offset + 2, br, offset + 3, bl, offset + 1);
                
                
                //mesh.getFaces().addAll(bl, tl, tr);
                //mesh.getFaces().addAll(tr, br, bl);

            }
        }
        
        Function graph2D = new Function(xmin, xmax, zmin, zmax, ImageAmplification);
        Graph2D image = new Graph2D(xmin, xmax, zmin, zmax, ImageAmplification, graph2D);
        Image diffuseMap = image.getImage();
        
        ballSize = 0.01;
        
        Sphere ball = drawBall(ballSize, BallX, -startY - ballSize, BallZ);
        LineStartX = BallX;
        LineStartZ = BallZ;
        LineEndX = LineStartX;
        LineEndZ = LineStartZ + ballSize*10;
        LineRotateX = LineEndX;
        LineRotateZ = LineEndZ;
        
        root.getChildren().add(ball);
        
        line = new Cylinder(ballSize/10, ballSize*10);
        line.setMaterial(new PhongMaterial(Color.BLACK));
        double LineStartY =  -ballSize -graph3D.getHeight()[(int)(LineStartX*GraphAmplification) + xRange - xMax][(int)(LineStartZ*GraphAmplification) + zRange - zMax];
        double LineEndY = -ballSize -graph3D.getHeight()[(int)(LineEndX*GraphAmplification) + xRange - xMax][(int)(LineEndZ*GraphAmplification) + zRange - zMax];
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
        
        //camera.setTranslateX(0);
        //camera.setTranslateY(-startY - 1);
        camera.setTranslateZ(-ballSize*100);
        camera.setFarClip(1000);
        //camera.setNearClip(0);
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(Color.WHITE);
        root.getChildren().add(camera);
        root.getChildren().add(ambient);
        
        //test
        /* Tree tree = new Tree(0.01f, 0.1f, 0.03f);
        MeshView trunk = tree.getTrunk();
        trunk.setTranslateX(BallX);
        trunk.setTranslateY(-startY);
        trunk.setTranslateZ(BallZ);
        root.getChildren().add(trunk);
        MeshView leaves = tree.getLeaves();
        leaves.setTranslateX(BallX);
        leaves.setTranslateY(-startY);
        leaves.setTranslateZ(BallZ);
        root.getChildren().add(leaves); */
        
        //create a subscene for where the actual game will be represented
        SubScene subScene = new SubScene(root, 850, 550, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        getChildren().add(subScene);
        
    }
    private Sphere drawBall(double BallSize, double BallX, double BallY, double BallZ){
        Sphere ball = new Sphere(BallSize);
        ball.setMaterial(new PhongMaterial(Color.WHITE));
        ball.setTranslateX(BallX);
        ball.setTranslateY(BallY);
        ball.setTranslateZ(BallZ);
        return ball;
    }
    public void setCoordinates(double LineEndX, double LineEndZ){
        this.LineEndX = LineEndX;
        this.LineEndZ = LineEndZ;
        double LineStartY =  -ballSize -graph3D.getHeight()[(int)(LineStartX*GraphAmplification) + xRange - xMax][(int)(LineStartZ*GraphAmplification) + zRange - zMax];
        double LineEndY = -ballSize -graph3D.getHeight()[(int)(LineEndX*GraphAmplification) + xRange - xMax][(int)(LineEndZ*GraphAmplification) + zRange - zMax];
        double distance = Math.sqrt(Math.pow(LineEndX - LineStartX, 2) + Math.pow(LineEndY - LineStartY, 2) + Math.pow(LineEndZ - LineStartZ, 2));
        rotateLine(new Point3D(LineStartX, LineStartY ,LineStartZ),
                new Point3D(LineEndX, LineEndY ,LineEndZ), distance);
       }
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
    public MeshView getMesh(){
        return meshView;
    }
    public void CameraZoom(double value){
        camera.setTranslateZ(camera.getTranslateZ() + value);
    }
    public void CameraY(double value){
        camera.setTranslateY(camera.getTranslateY() + value);
    }
    public void CameraX(double value){
        camera.setTranslateX(camera.getTranslateX() + value);
    }
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
}
