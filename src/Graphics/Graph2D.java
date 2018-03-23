package Graphics;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * This class takes the input function and creates heat-map-like a graph of it
 * @author Jordan
 * @version 1.0
 * @date 20.03
 */
public class Graph2D {
    private WritableImage image;
    public boolean gameOver = false;
    
    public Graph2D(int xmin, int xmax, int zmin, int zmax, int amplification, Function function){
        
        int xMin = xmin*amplification;
        int xMax = xmax*amplification;
        int zMin = zmin*amplification;
        int zMax = zmax*amplification;
        
        int xRange = xMax - xMin;
        int zRange = zMax - zMin;
        
        image = new WritableImage(xRange, zRange);
        PixelWriter pw = image.getPixelWriter();
        for (int x = xMin; x < xMax; x++) {
            for (int z = zMin; z < zMax; z++) {

                float height = function.getHeight()[xRange + x - xMax][zRange + z - zMax];
                
                int min = function.getMin();
                int max = function.getMax();
                
                Color color;
                if(height < 0){
                    gameOver = true;
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
                pw.setColor(x + xRange - xMax, z + zRange - zMax, color);
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
