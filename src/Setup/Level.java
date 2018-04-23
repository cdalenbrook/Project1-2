package Setup;

import java.util.ArrayList;

/**
 * Class that represents each map and level
 * @author Jordan
 * @version 2.0
 * @date 19.04.2018
 */
public class Level {
    private int level;
    private ArrayList<char[]> function;
    private double[] rangeX = new double[2];
    private double[] rangeY = new double[2];
    private double[] ball = new double[2];
    private double[] goal = new double[2];
    private ArrayList<double[]> tree = new ArrayList<>();
    private ArrayList<double[]> sand = new ArrayList<>();
    
    /**
     * Method that clones the class, to avoid reference errors.
     * @return a cloned instance of this class
     */
    @Override
    public Level clone(){
        Level newLevel = new Level();
        newLevel.setLevel(level);
        newLevel.setFunction(cloneFunction(function));
        newLevel.setRangeX(rangeX);
        newLevel.setRangeY(rangeY);
        newLevel.setBall(ball);
        newLevel.setGoal(goal);
        newLevel.setTree(cloneArrayList(tree));
        newLevel.setSand(cloneArrayList(sand));
        
        return newLevel;
    }
    
    public void setLevel(int level){
        this.level = level;
    }

    public void setFunction(ArrayList<char[]> function) {
        this.function = function;
    }

    public void setRangeX(double[] rangeX) {
        this.rangeX = rangeX;
    }

    public void setRangeY(double[] rangeY) {
        this.rangeY = rangeY;
    }
    
    public void setBall(double[] ball) {
        this.ball = ball;
    }

    public void setGoal(double[] goal) {
        this.goal = goal;
    }

    public void setTree(ArrayList<double[]> tree) {
        this.tree = tree;
    }

    public void setSand(ArrayList<double[]> sand) {
        this.sand = sand;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<char[]> getFunction() {
        return function;
    }

    public double[] getRangeX() {
        return rangeX;
    }

    public double[] getRangeY() {
        return rangeY;
    }
    
    public double[] getBall() {
        return ball;
    }

    public double[] getGoal() {
        return goal;
    }

    public ArrayList<double[]> getTree() {
        return tree;
    }

    public ArrayList<double[]> getSand() {
        return sand;
    }
    public ArrayList<char[]> cloneFunction(ArrayList<char[]> arrayList){
        ArrayList<char[]> newArrayList = new ArrayList<>();
        for(char[] part: arrayList){
            char[] newPart = new char[part.length];
            for(int i = 0; i < part.length; i++){
                newPart[i] = part[i];
            }
            newArrayList.add(newPart);
        }
        return newArrayList;
    }
    public ArrayList<double[]> cloneArrayList(ArrayList<double[]> arrayList){
        ArrayList<double[]> newArrayList = new ArrayList<>();
        for(double[] part: arrayList){
            double[] newPart = new double[2];
            for(int i = 0; i < part.length; i++){
                newPart[i] = part[i];
            }
            newArrayList.add(newPart);
        }
        return newArrayList;
    }
}
