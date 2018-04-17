package Setup;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class DataReader {
    private static ArrayList<Level> data = new ArrayList<>();
    public DataReader(){
        try{
            FileReader LevelFile = new FileReader("Data.txt");
            Scanner in = new Scanner(LevelFile);
            Level level = new Level();
            while(in.hasNext()) {
                String info = in.next();
                if(info.equals("Level:")){
                    level.setLevel(Integer.parseInt(in.next()));
                    in.next();
                    ArrayList<char[]> function = new ArrayList<>();
                    while(in.hasNext()){
                        String next = in.next();
                        if(next.equals("X:")){
                            level.setFunction(function);
                            break;
                        }
                        else{
                            function.add(new String(next).toCharArray());
                        }    
                    }
                    double[] rangeX = new double[2];
                    rangeX[0] = Double.parseDouble(in.next());
                    rangeX[1] = Double.parseDouble(in.next());
                    level.setRangeX(rangeX);
                    in.next();
                    double[] rangeY = new double[2];
                    rangeY[0] = Double.parseDouble(in.next());
                    rangeY[1] = Double.parseDouble(in.next());
                    level.setRangeY(rangeY);
                    in.next();
                    int[] amplification = new int[2];
                    amplification[0] = Integer.parseInt(in.next());
                    amplification[1] = Integer.parseInt(in.next());
                    level.setAmplification(amplification);
                    in.next();
                    level.setPrecision(Integer.parseInt(in.next()));
                    in.next();
                    double[] ball = new double[2];
                    ball[0] = Double.parseDouble(in.next());
                    ball[1] = Double.parseDouble(in.next());
                    level.setBall(ball);
                    in.next();
                    double[] goal = new double[2];
                    goal[0] = Double.parseDouble(in.next());
                    goal[1] = Double.parseDouble(in.next());
                    level.setGoal(goal);
                    in.next();
                    ArrayList<double[]> tree = new ArrayList<>();
                    while(in.hasNext()){
                        String next = in.next();
                        if(next.equals("Sand:")){
                            level.setTree(tree);
                            break;
                        }
                        else{
                            double[] array = new double[2];
                            array[0] = Double.parseDouble(next);
                            array[1] = Double.parseDouble(in.next());
                            tree.add(array);
                        }    
                    }
                    ArrayList<double[]> sand = new ArrayList<>();
                    while(in.hasNext()){
                        String next = in.next();
                        if(!in.hasNext() || next.equals("Level:")){
                            level.setSand(sand);
                            break;
                        }
                        else{
                            double[] array = new double[2];
                            array[0] = Double.parseDouble(next);
                            array[1] = Double.parseDouble(in.next());
                            sand.add(array);
                        }    
                    }
                    data.add(level.clone());  
                }
            }
            LevelFile.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static ArrayList<Level> getData(){
        return data;
    }
}
