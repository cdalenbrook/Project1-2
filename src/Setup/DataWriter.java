package Setup;

import java.io.PrintWriter;
import java.util.ArrayList;

public class DataWriter {
    public DataWriter(ArrayList<Level> data){
        try{
            PrintWriter out = new PrintWriter("Data.txt");
            for(Level level: data){
                out.println("Level: " + level.getLevel());
                out.print("Function: ");
                for(char[] function: level.getFunction()){
                    out.print(new String(function) + " ");
                }
                out.println();
                out.println("X: " + level.getRangeX()[0] + " " + level.getRangeX()[1]);
                out.println("Y: " + level.getRangeY()[0] + " " + level.getRangeY()[1]);
                out.println("Ball: " + level.getBall()[0] + " " + level.getBall()[1]);
                out.println("Goal: " + level.getGoal()[0] + " " + level.getGoal()[1]);
                out.print("Tree: ");
                for(double[] tree: level.getTree()){
                    out.print(tree[0] + " " + tree[1] + " ");
                }
                out.println();
                out.print("Sand: ");
                for(double[] sand: level.getSand()){
                    out.print(sand[0] + " " + sand[1] + " ");
                }
                out.println();
            }
            out.close();
        }
        catch(Exception e){
        }
    }
}
