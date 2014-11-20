/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geo;

import java.util.ArrayList;

/**
 *
 * @author Wesley
 */
public class Poly3D {
    
    private ArrayList<Integer> vertexIndicies;
    private ArrayList<Integer> uvCoordIndicies;
    private ArrayList<Integer> vertexNormIndicies;
    
    public Poly3D(){
        vertexIndicies = new ArrayList<>();
        uvCoordIndicies = new ArrayList<>();
        vertexNormIndicies = new ArrayList<>();
    }
    
    public void addPoint(Integer vertex){
        addPoint(vertex, -1, -1);
    }
    
    public void addPoint(Integer vertex, Integer vertexNorm){
        addPoint(vertex, -1, vertexNorm);
    }
    
    public void addPoint(Integer vertex, Integer uvCoord, Integer vertexNorm){
        getVertexIndicies().add(vertex);
        getUvCoordIndicies().add(uvCoord);
        getVertexNormIndicies().add(vertexNorm);
    }

    public ArrayList<Integer> getVertexIndicies() {
        return vertexIndicies;
    }

    public ArrayList<Integer> getUvCoordIndicies() {
        return uvCoordIndicies;
    }

    public ArrayList<Integer> getVertexNormIndicies() {
        return vertexNormIndicies;
    }
    
}
