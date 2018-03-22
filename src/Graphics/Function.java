package Graphics;

public class Function{
    private float[][] height;
    private int maxValue;
    private int minValue;

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
                height[rangeX + x - EndX][rangeZ + z - EndZ] = calculate(x, z, amplification);
            }
        }
        /* for (int x = StartX; x < EndX; x++){
            for (int z = StartZ; z < EndZ; z++) {
                System.out.println(height[rangeX + x - EndX][rangeZ + z - EndZ]);
            }
        } */
        findMax();
        findMin();
    }
    private int calculate(float x, float z, float amplification){
        float y = (float)((0.1*((x)/amplification)) + (0.03*(Math.pow(((x)/amplification), 2.0))) + (0.2*((z)/amplification)));
        //System.out.println(y);
        y = y*1000;
        return (int)y;
    }
    public float[][] getHeight(){
        return height;
    }
    private void findMin(){
        int min = (int)height[0][0];
        for (int x = 0; x < height.length; x++){
            for (int z = 0; z < height[0].length; z++){
                if(height[x][z] < min){
                    min = (int)height[x][z];
                }
            }   
        }
        minValue = min;
    }
    public int getMin(){
        return minValue;
    }
    private void findMax(){
        int max = (int)height[0][0];
        for (int x = 0; x < height.length; x++){
            for (int z = 0; z < height[0].length; z++){
                if(height[x][z] > max){
                    max = (int)height[x][z];
                }
            }   
        }
        maxValue = max;
    }
    public int getMax(){
        return maxValue;
    }
}
