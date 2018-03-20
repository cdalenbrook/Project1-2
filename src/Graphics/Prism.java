package Graphics;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import javafx.scene.shape.TriangleMesh;

/**
 * This class creates a Prism from TriangleMesh for representing hills.
 * @author Jordan
 * @version 1.2
 * @date 20.03
 */
public class Prism extends TriangleMesh {
    //variables for the prism
    //width
    private float w;
    //depth
    private float d;
    //height
    private float h;
    //length - this will be the top part of the prism, on what the boll will be rolling
    private float l;
    //angle of how much the prism is tilted
    private float angle;
    
    /**
     * Constructor
     * @param w prism width
     * @param l prism length - top surface
     * @param angle how much the top surface is tilted
     */
    public Prism(float w, float l, float angle) {
        this.w = w;
        this.l = l;
        this.angle = angle;
        //some basic math calculations to find depth and height
        d = l*(float)cos(Math.toRadians(angle));
        h = l*(float)sin(Math.toRadians(angle));
        
        //6 vertices of a prism
        float points[] = {
            0, 0, 0, //0
            w, 0, 0, //1
            0, 0, d, //2
            w, 0, d, //3
            0, -h, d, //4
            w, -h, d  //5
        };
        //used for images
        //could be implemented later on
        float texCoords[] = {0, 0};
        
        //faces are three connected vertices, with which we create triangles, with which we create 3D shapes   
        int faces[] = {
            4, 0, 1, 0, 5, 0,
            1, 0, 4, 0, 0, 0,
            4, 0, 3, 0, 2, 0,
            3, 0, 4, 0, 5, 0,
            1, 0, 3, 0, 5, 0,
            4, 0, 2, 0, 0, 0,
            2, 0, 1, 0, 0, 0,
            1, 0, 2, 0, 3, 0
            
        };
        
        //no clue what this is, not sure if we even need it, but it doesn't do bad so...
        int faceSmoothingGroups[] = {
            0, 0, 0, 0, 0, 0, 0, 0
        };
        
        //add all data to TriangleMesh
        this.getFaceSmoothingGroups().setAll(faceSmoothingGroups);
        this.getPoints().setAll(points);
        this.getTexCoords().setAll(texCoords);
        this.getFaces().setAll(faces);
    }
    /**
     * @return width
     */
    public float getWidth(){
        return w;
    }
    /**
     * @return height
     */
    public float getHeight(){
        return h;
    }
    /**
     * @return depth
     */
    public float getDepth(){
        return d;
    }
    /**
     * @return length
     */
    public float getLength(){
        return l;
    }
    /**
     * @return angle
     */
    public float getAngle(){
        return angle;
    }
}
