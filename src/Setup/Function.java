package Setup;

import java.util.ArrayList;

/**
 * To avoid excessive calculation time, this class will calculate all the points 
 * of a function needed in a given range and store them in a 2D array.
 * @author Jordan
 */
public class Function{
    private float[][] height;
    private float maxValue;
    private float minValue;
    
    /**
     * Constructor
     * @param level instance of Level
     * @param imageType to calculate amplification, we need to know for what we are going to use the points
     */
    public Function(Level level, ImageType imageType){
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
        
        int rangeX = (int)(endX - startX)*amplification;
        int rangeZ = (int)(endZ - startZ)*amplification;
        
        height = new float[rangeX][rangeZ];
        
        int StartX = (int)startX*amplification;
        int EndX = (int)endX*amplification;
        int StartZ = (int)startZ*amplification;
        int EndZ = (int)endZ*amplification;
        for (int x = StartX; x < EndX; x++){
            for (int z = StartZ; z < EndZ; z++) {
                height[rangeX + x - EndX][rangeZ + z - EndZ] = (float)Calculator.findZ(data.getFunction(), x/(double)amplification, z/(double)amplification);
            }
        }
        findMax();
        findMin();
    }
    //test
    public Function(ArrayList<char[]> functionData, int startX, int endX, int startZ, int endZ, int amplification){
        int rangeX = (endX - startX)*amplification;
        int rangeZ = (endZ - startZ)*amplification;
        
        height = new float[rangeX][rangeZ];
        
        int StartX = startX*amplification;
        int EndX = endX*amplification;
        int StartZ = startZ*amplification;
        int EndZ = endZ*amplification;
        
        for (int x = StartX; x < EndX; x++){
            for (int z = StartZ; z < EndZ; z++) {
                height[rangeX + x - EndX][rangeZ + z - EndZ] = (float)Calculator.findZ(functionData, x/(double)amplification, z/(double)amplification);
             }
        }
        findMax();
        findMin();
    }
    /**
     * @return 2D array representing all height points of the function
     */
    public float[][] getHeight(){
        return height;
    }
    /**
     * Finds the lowest value of the function in a given range
     */
    private void findMin(){
        float min = (int)height[0][0];
        for (int x = 0; x < height.length; x++){
            for (int z = 0; z < height[0].length; z++){
                if(height[x][z] < min){
                    min = height[x][z];
                }
            }   
        }
        minValue = min;
    }
    /**
     * @return the lowest value of a function in a set range
     */
    public float getMin(){
        return minValue;
    }
    /**
     * Finds the highest value of a function in a set range
     */
    private void findMax(){
        float max = height[0][0];
        for (int x = 0; x < height.length; x++){
            for (int z = 0; z < height[0].length; z++){
                if(height[x][z] > max){
                    max = height[x][z];
                }
            }   
        }
        maxValue = max;
    }
    
    /**
     * @return highest value of a function in a set range
     */
    public float getMax(){
        return maxValue;
    }
    
    /**
     * Calculates the height of a function, that is read from a file
     * @param function ArrayList containing the function
     * @param X value of x
     * @param Y value of x
     * @return height/z
     */
    /* private double calculate(ArrayList<char[]> function, double X, double Z){
        this.X = X;
        this.Z = Z;
        double height = 0;
        for(char[] part: function){
            height = height + getMultiplier(part)*calculate1(part);
        }
        return height;
    }
    
    /**
     * Used to distinguish the variable part of a function(x or y)
     * @param data char array representing part of a function
     * @return char array with variable parts
     */
    /* private double calculate1(char[] data){
        int counter = 0;
        for(int i = 0; i < data.length; i++){
            char b = data[i];
            if(!Character.isDigit(b) && b != '/' && b != '.' && b != '-'){
                counter = i;
                break;
            }
        }
        char[] newData = new char[data.length - counter];
        for(int i = 0; i < newData.length; i++){
            newData[i] = data[i + counter];
        }
        return calculate2(newData);
    }
    
    /**
     * Calculates the variable part of a function
     * @param data char array representing part of a function
     * @return double with value
     */
   /* private double calculate2(char[] data){
        switch(new String(data).trim()){
            case "x": return X;
            case "y": return Z;
            case "x+y": return X + Z;
            case "x-y": return X - Z;
            case "xy" : return X*Z;
            case "x/y" : return X/Z;
        }
        if(data[0] == 'c' && data[1] == '0' && data[2] == 's'){
            char[] newData = new char[data.length - 3];
            for(int i = 0; i < newData.length; i++){
                newData[i] = data[i + 3];
            }
            return Math.cos(calculate2(newData));
        }
        else if(data[0] == 's' && data[1] == 'i' && data[2] == 'n'){
            char[] newData = new char[data.length - 3];
            for(int i = 0; i < newData.length; i++){
                newData[i] = data[i + 3];
            }
            return Math.sin(calculate2(newData));
        }
        else{
            char[] number = new char[data.length - 7];
            char[] power = new char[data.length - 7];
            boolean Power = false;
            int counter = 0;
            for(int i = 4; i < data.length - 1; i++){
                if(data[i] == ','){
                    Power = true;
                    counter = i + 1;
                }
                else{
                    if(!Power){
                        number[i - 4] = data[i];
                    }
                    else{
                        if(Character.isDigit(data[i]) || data[i] == ',' || data[i] == '-' || data[i] == '/'){
                            power[i - counter] = data[i];
                        }    
                    }
                }
            }
            return Math.pow(calculate2(number), Double.parseDouble(new String(power).trim()));
        }
    }
    
    /**
     * Reads the multiplier (number in front of x or y) from a function.
     * @param data char array to read from
     * @return double as value
     */
   /* private double getMultiplier(char[] data){
        ArrayList<Character> list = new ArrayList<>();
        for(char b: data){
           if(Character.isDigit(b) || b == '/' || b == '.' || b == '-'){
               list.add(b);
           }
           else{
              break;
           }
        }
        if(list.size() == 0){
            return 1.0;
        }
        else{
            char[] a = new char[list.size()];
            char[] b = new char[list.size()];
            b[0] = '1';
            boolean division = false;
            int count = 0;
            for(int i = 0; i < list.size(); i++){
                if(list.get(i) == '/'){
                    division = true;
                    count = i + 1;
                }
                else if(!division){
                    a[i] = list.get(i);
                }
                else{
                    b[i - count] = list.get(i);
                }
            }
            String A = new String(a);
            String B = new String(b);
            double AA = Double.parseDouble(A.trim());
            double BB = Double.parseDouble(B.trim());
            return AA/BB;
        }
    }*/
}
