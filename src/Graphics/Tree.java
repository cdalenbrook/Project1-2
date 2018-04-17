/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

/**
 *
 * @author danyp
 */
public class Tree{
    private float w;
    private float h;
    private float d;
    private MeshView TRUNK = new MeshView();
    private MeshView LEAVES = new MeshView();
    /**
     * Constructor will create a triangle mesh with points, textCoords, faces and faceSmoothingGroups
     * @param w the width of the wanted box
     * @param h the height of the wanted box
     * @param d the depth of the wanted box
     */
    public Tree(float w, float h, float d) {
        this.w = w;
        this.h = h;
        this.d = d;
        float hw = w / 2f;
        float hh = h / 4f;
        float hd = d / 2f;

        float pointsTrunk[] = {
            -hw, -hh, -hd,
             hw, -hh, -hd,
             hw,  0, -hd,
            -hw,  0, -hd,
            -hw, -hh,  hd,
             hw, -hh,  hd,
             hw,  0,  hd,
            -hw,  0,  hd};
        
        float pointsLeaves[] = {
            -w, -hh, -d, //front left
             w, -hh, -d, //front right
             -w, -hh, d, //left back
             w, -hh, d, //right back
             0, -h, 0}; //top
        
        float textCoordsTrunk[] = {0, 0, 1, 0, 1, 1, 0, 1};
        float textCoordsLeaves[] = {0, 0, 0, 0};
        
        int facesTrunk[] = {
            0, 0, 2, 2, 1, 1,
            2, 2, 0, 0, 3, 3,
            1, 0, 6, 2, 5, 1,
            6, 2, 1, 0, 2, 3,
            5, 0, 7, 2, 4, 1,
            7, 2, 5, 0, 6, 3,
            4, 0, 3, 2, 0, 1,
            3, 2, 4, 0, 7, 3,
            3, 0, 6, 2, 2, 1,
            6, 2, 3, 0, 7, 3,
            4, 0, 1, 2, 5, 1,
            1, 2, 4, 0, 0, 3};
        
        int facesLeaves[] = { 
            4, 0, 0, 0, 1, 0,
            4, 0, 2, 0, 0, 0,
            4, 0, 3, 0, 2, 0,
            4, 0, 1, 0, 3, 0,
            2, 0, 0, 0, 1, 0,
            2, 0, 1, 0, 3, 0};
        
        int faceSmoothingGroupsTrunk[] = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        
        int faceSmoothingGroupsLeaves[] = {
            0, 0, 0, 0, 0, 0};
        
        TriangleMesh trunk = new TriangleMesh();
        trunk.getFaceSmoothingGroups().setAll(faceSmoothingGroupsTrunk);
        trunk.getPoints().setAll(pointsTrunk);
        trunk.getTexCoords().setAll(textCoordsTrunk);
        trunk.getFaces().setAll(facesTrunk);
        
        TriangleMesh leaves = new TriangleMesh();
        leaves.getFaceSmoothingGroups().setAll(faceSmoothingGroupsLeaves);
        leaves.getPoints().setAll(pointsLeaves);
        leaves.getTexCoords().setAll(textCoordsLeaves);
        leaves.getFaces().setAll(facesLeaves);
        
        TRUNK.setMesh(trunk);
        TRUNK.setMaterial(new PhongMaterial(Color.BROWN));
        TRUNK.setCullFace(CullFace.NONE);
        
        LEAVES.setMesh(leaves);
        LEAVES.setMaterial(new PhongMaterial(Color.DARKGREEN));
        LEAVES.setCullFace(CullFace.NONE);
        
    }
    
    public MeshView getTrunk(){
        return TRUNK;
    }
    public MeshView getLeaves(){
        return LEAVES;
    }
    /**
     * Get the width of the box
     * @return the width of the box
     */
    public float getWidth(){
        return w;
    }
    /**
     * Get the height of the box
     * @return the height of the box
     */
    public float getHeight(){
        return h;
    }
    /**
     * Get the depth of the box
     * @return the depth of the box
     */
    public float getDepth(){
        return d;
    }
}
