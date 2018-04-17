package Setup;

import java.util.ArrayList;

public class Function{
    private double X;
    private double Y;
    
    private float[][] height;
    private float maxValue;
    private float minValue;
    
    public Function(int level, FunctionType functionType){
        Level data = DataReader.getData().get(level);
        
        double startX = data.getRangeX()[0];
        double endX = data.getRangeX()[1];
        double startZ = data.getRangeY()[0];
        double endZ = data.getRangeY()[1];
        
        int amplification = 0;
        switch(functionType){
            case BUTTON: amplification = (int)(200/(endX - startX)); break;
            case GRAPH: amplification = (int)(400/(endX - startX)); break;
            case IMAGE: amplification = (int)(600/(endX - startX)); break;
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
                height[rangeX + x - EndX][rangeZ + z - EndZ] = (float)calculate(data.getFunction(), x/(double)amplification, z/(double)amplification);
            }
        }
        findMax();
        findMin();
    }
    
    public Function(int startX, int endX, int startZ, int endZ, int amplification){
        int rangeX = (endX - startX)*amplification;
        int rangeZ = (endZ - startZ)*amplification;
        
        height = new float[rangeX][rangeZ];
        
        int StartX = startX*amplification;
        int EndX = endX*amplification;
        int StartZ = startZ*amplification;
        int EndZ = endZ*amplification;
        
        for (int x = StartX; x < EndX; x++){
            for (int z = StartZ; z < EndZ; z++) {
                height[rangeX + x - EndX][rangeZ + z - EndZ] = calculateExample(x, z, amplification);
            }
        }
        findMax();
        findMin();
    }
    private float calculateExample(float x, float z, float amplification){
        float y = (float)((0.1*((x)/amplification)) + (0.03*(Math.pow(((x)/amplification), 2.0))) + (0.2*((z)/amplification)));
        
        return y;
    }
    public float[][] getHeight(){
        return height;
    }
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
    public float getMin(){
        return minValue;
    }
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
    public float getMax(){
        return maxValue;
    }
    
    private double calculate(ArrayList<char[]> function, double X, double Y){
        this.X = X;
        this.Y = Y;
        double height = 0;
        for(char[] part: function){
            height = height + getMultiplayer(part)*getX(part);
        }
        return height;
    }
    private double getX(char[] data){
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
        return sth(newData);
    }
    private double sth(char[] data){
        switch(new String(data).trim()){
            case "x": return X;
            case "y": return Y;
            case "x+y": return X + Y;
            case "x-y": return X - Y;
            case "xy" : return X*Y;
            case "x/y" : return X/Y;
        }
        if(data[0] == 'c' && data[1] == '0' && data[2] == 's'){
            char[] newData = new char[data.length - 4];
            for(int i = 0; i < newData.length; i++){
                newData[i] = data[i + 3];
            }
            return Math.cos(sth(newData));
        }
        else if(data[0] == 's' && data[1] == 'i' && data[2] == 'n'){
            char[] newData = new char[data.length - 4];
            for(int i = 0; i < newData.length; i++){
                newData[i] = data[i + 3];
            }
            return Math.sin(sth(newData));
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
                        power[i - counter] = data[i];
                    }
                }
            }
            return Math.pow(sth(number), Double.parseDouble(new String(power).trim()));
        }
    }
    private double getMultiplayer(char[] data){
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
    }
}
