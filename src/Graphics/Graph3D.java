package Graphics;

import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Graph3D extends Parent{
    private int amplification;
    
    private MeshView meshView;
    private PerspectiveCamera camera;
    
    private Rotate xAxis = new Rotate(-40, Rotate.X_AXIS);
    private Rotate yAxis = new Rotate(10, Rotate.Y_AXIS);
    private Rotate zAxis = new Rotate(0, Rotate.Z_AXIS);
    
    public Graph3D(int xmin, int xmax, int zmin, int zmax, int amplification){
        
        Group root = new Group();
        root.setAutoSizeChildren(false);
        
        this.amplification = amplification;
        int xMin = xmin*amplification;
        int xMax = xmax*amplification;
        int zMin = zmin*amplification;
        int zMax = zmax*amplification;
        
        int xRange = xMax - xMin;
        int zRange = zMax - zMin;
        
        TriangleMesh mesh = new TriangleMesh();

        for (int x = xMin; x < xMax; x++) {
            for (int z = zMin; z < zMax; z++) {
                mesh.getPoints().addAll(x, calculate(x, z), z);
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
        
        Graph2D image = new Graph2D(xmin, xmax, zmin, zmax, 1);
        Image diffuseMap = image.getImage();

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(diffuseMap);
        //material.setSpecularColor(Color.WHITE);
        
        meshView = new MeshView(mesh);
        //meshView.setTranslateX(-0.5 * range);
        //meshView.setTranslateZ(-0.5 * range);
        //meshView.setMaterial(new PhongMaterial(Color.BLACK));
        meshView.setMaterial(material);
        meshView.setCullFace(CullFace.NONE);
        meshView.setDrawMode(DrawMode.LINE);
        meshView.setDepthTest(DepthTest.ENABLE);
        root.getChildren().add(meshView);
        camera = new PerspectiveCamera(true);
        //add transformations to camera
        camera.getTransforms().addAll(
                xAxis, yAxis, zAxis, new Translate(0, 5, -55));
        
        root.getChildren().add(camera);
        
        //create a subscene for where the actual game will be represented
        SubScene subScene = new SubScene(root, 1600, 900, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        getChildren().add(subScene);
        
    
    }
    public MeshView getMesh(){
        return meshView;
    }
    
    private int calculate(int x, int z){
        double y = ((0.1*(x/amplification)) + (0.03*(Math.pow((x/amplification), 2.0))) + (0.2*(z/amplification)));
        Long L = Math.round(y);
        int i = Integer.valueOf(L.intValue());
        return i;
    }
    public void CameraZoom(double value){
        camera.setTranslateZ(camera.getTranslateZ() + value);
    }
    public void print(){
        System.out.println(camera.getTranslateZ() + " " + xAxis.getAngle() + " " + yAxis.getAngle() + " " + zAxis.getAngle());
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
}
