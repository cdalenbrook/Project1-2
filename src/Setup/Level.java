package Setup;

import java.util.ArrayList;

public class Level {
    private int level;
    private ArrayList<char[]> function;
    private double[] rangeX = new double[2];
    private double[] rangeY = new double[2];
    private int[] amplification = new int[2];
    private double precision;
    private double[] ball = new double[2];
    private double[] goal = new double[2];
    private ArrayList<double[]> tree;
    private ArrayList<double[]> sand;
    
    public Level clone(){
        Level level = new Level();
        level.setLevel(this.getLevel());
        level.setFunction(this.getFunction());
        level.setRangeX(this.getRangeX());
        level.setRangeY(this.getRangeY());
        level.setAmplification(this.getAmplification());
        level.setPrecision(this.getPrecision());
        level.setBall(this.getBall());
        level.setGoal(this.getGoal());
        level.setTree(this.getTree());
        level.setSand(this.getSand());
        
        return level;
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

    public void setAmplification(int[] amplification) {
        this.amplification = amplification;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
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

    public int[] getAmplification() {
        return amplification;
    }

    public double getPrecision() {
        return precision;
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
}
