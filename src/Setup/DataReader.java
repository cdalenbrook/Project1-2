package Setup;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that reads from a file that stores data as levels
 * @author Jordan
 */
public class DataReader {
    private static ArrayList<Level> data = new ArrayList<>();
    
    /**
     * Constructor will read all the data and store it in a static ArrayList
     */
    public DataReader(){
        try{
            FileReader LevelFile = new FileReader("Data.txt");
            Scanner in = new Scanner(LevelFile);
            Level level = new Level();
            while(in.hasNext()) {
                String info = in.next();
                if(info.equals("Level:")){
                    gatherData(in, level);
                }
            }        
            LevelFile.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        catch(IOException e){
            System.out.println("IO exception");
        }
    }
    
    /**
     * @return ArrayList with levels
     */
    public static ArrayList<Level> getData(){
        return data;
    }
    
    /**
     * Method used to read from the file
     * @param in Scanner that reads from the file
     * @param level instance of class level to store information into
     */
    private void gatherData(Scanner in, Level level){
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
                function.add((next.trim()).toCharArray());
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
            if(next.equals("Level:")){
                gatherData(in, new Level());
            }
            else{
                double[] array = new double[2];
                array[0] = Double.parseDouble(next);
                array[1] = Double.parseDouble(in.next());
                sand.add(array);
            }    
        }
        level.setSand(sand);
        data.add(0, level.clone());  
    }
}
