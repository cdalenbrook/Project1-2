package Graphics;

public class FindMaxandMin{
    public static double currentMax;
    public static double max;
    public static double currentMin;
    public static double min;
    public static double h;

    public static double findMin(int x1, int x2, int y1, int y2){
        currentMin = 999;
        for (int i = x1; i <= x2; i++){
            for(int j = y1; j <= y2; j++){
                h = (0.1*i + 0.03*(i*i) + 0.2*j);
                if(h < currentMin){
                    currentMin = h;
                }
            }
        }
        return min = currentMin;
    }

    public static double findMax(int x1, int x2, int y1, int y2){
        currentMax = -999;
        for (int i = x1; i <= x2; i++){
            for(int j = y1; j <= y2; j++){
                h = (0.1*i + 0.03*(i*i) + 0.2*j);
                if(h > currentMax){
                    currentMax = h;
                }
            }
        }
        return max = currentMax;
    }

    public static void main(String[] args) {

        System.out.println(findMin(-10, 10, -10, 10));
        System.out.println(findMax(-10, 10, -10, 10));

    }


}
