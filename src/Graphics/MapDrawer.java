package Graphics;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class MapDrawer extends Pane{
    Circle ball1;
    Circle ball2;
    
    public MapDrawer(){
        getChildren().add(drawSideView());
        getChildren().add(drawTopView());
    }
    private Parent drawSideView(){
        Pane SideView = new Pane();
        Rectangle sideView = new Rectangle(375, 375);
        sideView.setFill(Color.SKYBLUE);
        sideView.setStroke(Color.BLACK);
        sideView.setTranslateX(0);
        sideView.setTranslateY(0);
        SideView.getChildren().add(sideView);
        
        Polygon p1 = new Polygon();
        Polygon p2 = new Polygon();
        Polygon p3 = new Polygon();
        Polygon p4 = new Polygon();
        p1.getPoints().addAll(new Double[]{
         0.0, 225.0,
         100.0, 225.0,
         100.0, 375.0,
         0.0, 375.0,
        });
        p1.setFill(Color.GREEN);
        p2.getPoints().addAll(new Double[]{ 
         100.0, 225.0,
         250.0, 175.0,
         250.0, 375.0,
         100.0, 375.0,
        }); 
        p2.setFill(Color.GREEN);
        p3.getPoints().addAll(new Double[]{ 
         250.0, 175.0,
         375.0, 175.0,
         375.0, 375.0,
         250.0, 375.0,
        });
        p3.setFill(Color.YELLOW);
        p4.getPoints().addAll(new Double[]{ 
         50.0, 225.0,
         100.0, 225.0,
         100.0, 275.0,
         50.0, 275.0,
        });
        p4.setFill(Color.BLUE);
        SideView.getChildren().add(p1);
        SideView.getChildren().add(p2);
        SideView.getChildren().add(p3);
        SideView.getChildren().add(p4);
        
        ball1 = new Circle();
        ball1.setFill(Color.BLACK);
        ball1.setCenterX(5.0f); 
        ball1.setCenterY(220.0f); 
        ball1.setRadius(5.0f);
        SideView.getChildren().add(ball1);
        
        return SideView;
    }
    
    private Parent drawTopView(){
        Pane TopView = new Pane();
        Rectangle topView = new Rectangle(375, 375);
        topView.setFill(Color.SKYBLUE);
        topView.setStroke(Color.BLACK);
        topView.setTranslateX(425);
        topView.setTranslateY(0);
        
        TopView.getChildren().add(topView);
        Polygon p1 = new Polygon();
        Polygon p2 = new Polygon();
        Polygon p3 = new Polygon();
        Polygon p4 = new Polygon();
        p1.getPoints().addAll(new Double[]{ 
         425.0, 0.0,
         800.0, 0.0,
         800.0, 100.0,
         425.0, 100.0
        });
        p1.setFill(Color.GREEN);
        p2.getPoints().addAll(new Double[]{ 
         425.0, 100.0, 
         800.0, 100.0,
         800.0, 250.0,
         425.0, 250.0
        }); 
        p2.setFill(Color.DARKGREEN);
        p3.getPoints().addAll(new Double[]{ 
          425.0, 250.0,
          800.0, 250.0,
          800.0, 375.0,
          425.0, 375.0,
        });
        p3.setFill(Color.YELLOW);
        p4.getPoints().addAll(new Double[]{ 
         575.0, 50.0,
         650.0, 50.0,
         650.0, 100.0,
         575.0, 100.0
             
        });
        p4.setFill(Color.BLUE);
        TopView.getChildren().add(p1);
        TopView.getChildren().add(p2);
        TopView.getChildren().add(p3);
        TopView.getChildren().add(p4);
        
        ball2 = new Circle();
        ball2.setFill(Color.BLACK);
        ball2.setCenterX(612.5f); 
        ball2.setCenterY(5.0f); 
        ball2.setRadius(5.0f);
        TopView.getChildren().add(ball2);
        
        return TopView;
    }
    public double[] getBall1(){
        return new double[]{ball1.getCenterX(), ball1.getCenterY()};
    }
    public double[] getBall2(){
        return new double[]{ball2.getCenterX(), ball2.getCenterY()};
    }
    public void setBall1(double[] coordinate){
        ball1.setCenterX(coordinate[0]);
        ball1.setCenterY(coordinate[1]);
        ball2.setCenterY(coordinate[0]);
    }
    public void setBall2(double[] coordinate){
        ball2.setCenterX(coordinate[0]);
        ball2.setCenterY(coordinate[1]);
        ball1.setCenterX(coordinate[1]);
    }
    /*public boolean doesCollide1(double[] coordinate){
        if(coordinate[0] < 0 || coordinate[0] > 375){
        
        }
        else if(coordinate[1] ){
        
        }
        else{
        
        }
    }
    public boolean doesCollide2(){
        
        return false;
    }*/
}
