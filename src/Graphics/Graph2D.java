package Graphics;

import Setup.Function;
import Setup.ImageType;
import Setup.Level;
import javafx.geometry.Point2D;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * This class takes the input function and creates an image representing it
 * @author Jordan
 * @version 1.0
 * @date 20.03
 */
public class Graph2D {
    private WritableImage image;
    
    /**
     *  Constructor
     * @param level instance of Level
     * @param imageType the way we want to draw the image
     * @param function instance of function with data for the height
     */
    public Graph2D(Level level, ImageType imageType, Function function){
        Level data = level;
        double startX = data.getRangeX()[0];
        double endX = data.getRangeX()[1];
        double startZ = data.getRangeY()[0];
        double endZ = data.getRangeY()[1];
        
        int amplification = 0;
        switch(imageType){
            case BUTTON: amplification = (int)(200/(endX - startX)); break;
            case GRAPH: amplification = (int)(400/(endX - startX)); break;
            case IMAGE: amplification = (int)(600/(endX - startX)); break;
            case LEVELCREATIONMAP: amplification = (int)(600/(endX - startX)); break;
        }
        int StartX = (int)startX*amplification;
        int EndX = (int)endX*amplification;
        int StartZ = (int)startZ*amplification;
        int EndZ = (int)endZ*amplification;
        
        int RangeX = EndX - StartX;
        int RangeZ = EndZ - StartZ;
        
        Point2D goal = new Point2D(data.getGoal()[0]*amplification, data.getGoal()[1]*amplification);
        
        int goalRadius = amplification/15;
        int sandRadius = amplification/8;
        int treeRadius = amplification/32;
        
        image = new WritableImage(RangeX, RangeZ);
        PixelWriter pw = image.getPixelWriter();
        for (int x = StartX; x < EndX; x++) {
            for (int z = StartZ; z < EndZ; z++) {

                float height = function.getHeight()[RangeX + x - EndX][RangeZ + z - EndZ];
                
                float min = function.getMin();
                float max = function.getMax();
                
                Point2D point = new Point2D(x, z);
                
                Color color;
                if(point.distance(goal) <= goalRadius){
                    color = Color.RED;
                }
                else if(height < 0){
                    double newHeight;
                    if(min < -1){
                        newHeight = normalizeValue(height, min, 0, 0., 1.);
                    }
                    else{
                        newHeight = normalizeValue(height, -1, 0, 0., 1.);
                    }
                    color = Color.DARKBLUE.interpolate(Color.BLUE, newHeight);
                    //color = Color.color(0, 0, 0).interpolate(Color.color(0, 178, 255), newHeight);
                }
                else{
                    double newHeight = normalizeValue(height, 0, max, 0., 1.);
                    color = Color.DARKGREEN.interpolate(Color.CHARTREUSE, newHeight);
                    //color = Color.color(0, 102, 51).interpolate(Color.color(178, 255, 102), newHeight);  
                }
                
                for(double[] sand: level.getSand()){
                    Point2D Sand = new Point2D(sand[0]*amplification, sand[1]*amplification);
                    if(point.distance(Sand) <= sandRadius && !color.equals(Color.RED)){
                        color = Color.SANDYBROWN;
                    }
                }
                if(imageType == ImageType.BUTTON){
                    for(double[] tree: level.getTree()){
                    Point2D Tree = new Point2D(tree[0]*amplification, tree[1]*amplification);
                    if(point.distance(Tree) <= treeRadius && !color.equals(Color.RED)){
                        color = Color.BROWN;
                    }
                }
                }
                
                pw.setColor(x + RangeX - EndX, z + RangeZ - EndZ, color);
            }
        }
    }    
    
    //test
    public Graph2D(int startX, int endX, int startZ, int endZ, int amplification, Function function){
        
        int StartX = startX*amplification;
        int EndX = endX*amplification;
        int StartZ = startZ*amplification;
        int EndZ = endZ*amplification;
        
        int RangeX = EndX - StartX;
        int RangeZ = EndZ - StartZ;
        
        image = new WritableImage(RangeX, RangeZ);
        PixelWriter pw = image.getPixelWriter();
        for (int x = StartX; x < EndX; x++) {
            for (int z = StartZ; z < EndZ; z++) {

                float height = function.getHeight()[RangeX + x - EndX][RangeZ + z - EndZ];
                
                float min = function.getMin();
                float max = function.getMax();
                
                Color color;
                if(height < 0){
                    double newHeight;
                    if(min < -1){
                        newHeight = normalizeValue(height, min, 0, 0., 1.);
                    }
                    else{
                        newHeight = normalizeValue(height, -1, 0, 0., 1.);
                    }
                    color = Color.DARKBLUE.interpolate(Color.BLUE, newHeight);
                    //color = Color.color(0, 0, 0).interpolate(Color.color(0, 178, 255), newHeight);
                }
                else{
                    double newHeight = normalizeValue(height, 0, max, 0., 1.);
                    color = Color.DARKGREEN.interpolate(Color.CHARTREUSE, newHeight);
                    //color = Color.color(0, 102, 51).interpolate(Color.color(178, 255, 102), newHeight);

                    
                }
                pw.setColor(x + RangeX - EndX, z + RangeZ - EndZ, color);
            }
        }
    }

    /**
     * Getter method for the image of the game
     * @return image
     */
    public WritableImage getImage(){ return image; }

    /**
     * Normalization method for the value of the co-ordinates of the course
     * @param value value in the range of co-ordinates to be normalized
     * @param min current minimum value
     * @param max current maximum value
     * @param newMin normalized minimum value
     * @param newMax normalized maximum value
     */
    
    public double normalizeValue(double value, double min, double max, double newMin, double newMax) {

        return (value - min) * (newMax - newMin) / (max - min) + newMin;
    }
}
