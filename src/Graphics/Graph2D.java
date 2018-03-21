package Graphics;

import static Test.Chart3dDemo.normalizeValue;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Graph2D {
    private WritableImage image;
    
    public Graph2D(int xMin, int xMax, int yMin, int yMax, int resolution){
        //resolution should be added to make picture clearer
        
        int width = (xMax - xMin);
        int length = (yMax - yMin);
        
        image = new WritableImage(width, length);
        PixelWriter pw = image.getPixelWriter();
        for (int x = xMin; x < xMax; x++) {
            for (int y = yMin; y < yMax; y++) {

                double heigth = ((0.1*(x)) + (0.03*(Math.pow(x, 2.0))) + (0.2*(y)));
                //calculations
                int min = 0;
                int max = 1000;
                
                Color color;
                if(heigth <= 0){
                    color = Color.BLUE;
                }
                else{
                    double newHeight = normalizeValue(heigth, min, max, 0., 1.);

                    color = Color.GREEN.interpolate(Color.YELLOW, newHeight);

                    
                }
                if(xMin < 0 && yMin >= 0){
                    pw.setColor(x + width + xMin, y, color);
                }
                else if(yMin < 0 && xMin >= 0){
                    pw.setColor(x, y + length + yMin, color);
                }
                else if(xMin < 0 && yMin <0){
                    pw.setColor(x + width + xMin, y + length + yMin, color);
                }
                else{
                    pw.setColor(x, y, color);
                }

            }
        }
    }
    public WritableImage getImage(){
        return image;
    }
    
    public double normalizeValue(double value, double min, double max, double newMin, double newMax) {

        return (value - min) * (newMax - newMin) / (max - min) + newMin;
    }
}
