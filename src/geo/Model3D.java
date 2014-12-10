/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import physics.Collider;

/**
 *
 * @author Wesley
 */
public class Model3D {
    private ArrayList<Point3D> vertices;
    private ArrayList<Point2D> uvCoords;
    private ArrayList<Point3D> vertNorms;
    private ArrayList<Poly3D> faces;
    
    private Model3D parent;
    private Collider collider;
    
    private double modelX;
    private double modelY;
    private double modelZ;
    private double modelRotX;
    private double modelRotY;
    private double modelRotZ;
    private double modelScaleX;
    private double modelScaleY;
    private double modelScaleZ;
    
    private double maxX;
    private double minX;
    private double maxY;
    private double minY;
    private double maxZ;
    private double minZ;
    
    public Model3D(){
        parent = null;
        
        modelScaleX = 1.0;
        modelScaleY = 1.0;
        modelScaleZ = 1.0;
        
        maxX = Double.NaN;
        minX = Double.NaN;
        maxY = Double.NaN;
        minY = Double.NaN;
        maxZ = Double.NaN;
        minZ = Double.NaN;
        
        clearAllData();
    }
    
    public void generateDefault(){
        Point3D vertex1 = new Point3D(1.0, 1.0, 0.0);
        vertices.add(vertex1);
        Point3D vertex2 = new Point3D(1.0, -1.0, 0.0);
        vertices.add(vertex2);
        Point3D vertex3 = new Point3D(-1.0, -1.0, 0.0);
        vertices.add(vertex3);
        Point3D vertex4 = new Point3D(-1.0, 1.0, 0.0);
        vertices.add(vertex4);
        Poly3D face = new Poly3D();
        face.addPoint(0);
        face.addPoint(1);
        face.addPoint(2);
        face.addPoint(3);
        faces.add(face);
    }
        
    public void loadDataFromFile(String filename)
    {
        clearAllData();
        try{
            List<String> fileData = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
            
            for(String line : fileData){
                
                if(line.startsWith("v ")){
                    parseVertex(line);
                } else if (line.startsWith("vt ")){
                    parseUVCoord(line);
                } else if (line.startsWith("vn ")){
                    parseVertexNormal(line);
                } else if (line.startsWith("f ")){
                    parsePolygon(line);
                }
            }
        } catch(IOException ex) {
            System.out.println(ex.toString());
        }
    }
    
    private void parseVertex(String data)
    {
        String[] parts = data.split(" ");
        Point3D vertex = new Point3D(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
        vertices.add(vertex);
        evaluateMinMax(vertex);
    }
    
    private void evaluateMinMax(Point3D vertex){
        
        if(Double.isNaN(maxX)){
            maxX = vertex.getX();
            minX = vertex.getX();
            maxY = vertex.getY();
            minY = vertex.getY();
            maxZ = vertex.getZ();
            minZ = vertex.getZ();
        }
        
        if(maxX < vertex.getX()){
            maxX = vertex.getX();
        } else if (minX > vertex.getX()){
            minX = vertex.getX();
        }
        
        if(maxY < vertex.getY()){
            maxY = vertex.getY();
        } else if (minY > vertex.getY()){
            minY = vertex.getY();
        }
        
        if(maxZ < vertex.getZ()){
            maxZ = vertex.getZ();
        } else if (minZ > vertex.getZ()){
            minZ = vertex.getZ();
        }
        
    }
    
    private void parseUVCoord(String data)
    {
        String[] parts = data.split(" ");
        Point2D coord = new Point2D(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
        uvCoords.add(coord);
    }
    
    private void parseVertexNormal(String data)
    {
        String[] parts = data.split(" ");
        Point3D vertexNorm = new Point3D(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
        vertNorms.add(vertexNorm);
    }
    
    private void parsePolygon(String data)
    {
        String[] parts = data.split(" ");
        Poly3D face = new Poly3D();
        for(int i = 1; i < parts.length; i++)
        {
            String[] indicies = parts[i].split("/");
            face.addPoint(Integer.parseInt(indicies[0]) - 1, Integer.parseInt(indicies[1]) - 1, Integer.parseInt(indicies[2]) - 1); //Indexed by 1 in the OBJ file. Subtract by 1 to get 0 indexing.
        }
        getFaces().add(face);
    }
    
    public void createCollider(){
        double width = maxX - minX;
        double height = maxY - minY;
        double depth = maxZ - minZ;
        double offsetX = (maxX + minX)/2;
        double offsetY = (maxY + minY)/2;
        double offsetZ = (maxZ + minZ)/2;
        createCollider(width, height, depth, offsetX, offsetY, offsetZ);
    }
    
    public void createCollider(double width, double height, double depth, double offsetX, double offsetY, double offsetZ){
        collider = new Collider(this, width, height, depth, offsetX, offsetY, offsetZ);
    }
    
    public boolean hasCollision(Model3D testModel){
        Collider testCollider = testModel.getCollider();
        if(collider != null && testCollider != null){
            return collider.hasCollision(testModel.getCollider());
        } else {
            return false;
        }
    }

    public Point3D getVertexAt(int i) {
        return vertices.get(i);
    }

    public Point2D getUvCoordAt(int i) {
        return uvCoords.get(i);
    }

    public Point3D getVertNormAt(int i) {
        return vertNorms.get(i);
    }

    public ArrayList<Poly3D> getFaces() {
        return faces;
    }

    public Model3D getParent() {
        return parent;
    }

    public void setParent(Model3D parent) {
        this.parent = parent;
    }
    
    public Collider getCollider() {
        return collider;
    }

    public double getModelX() {
        return modelX;
    }

    public void setModelX(double modelX) {
        this.modelX = modelX;
    }

    public double getModelY() {
        return modelY;
    }

    public void setModelY(double modelY) {
        this.modelY = modelY;
    }

    public double getModelZ() {
        return modelZ;
    }

    public void setModelZ(double modelZ) {
        this.modelZ = modelZ;
    }

    public double getModelRotX() {
        return modelRotX;
    }

    public void setModelRotX(double modelRotX) {
        this.modelRotX = modelRotX;
    }

    public double getModelRotY() {
        return modelRotY;
    }

    public void setModelRotY(double modelRotY) {
        this.modelRotY = modelRotY;
    }

    public double getModelRotZ() {
        return modelRotZ;
    }

    public void setModelRotZ(double modelRotZ) {
        this.modelRotZ = modelRotZ;
    }

    public double getModelScaleX() {
        return modelScaleX;
    }

    public void setModelScaleX(double modelScaleX) {
        this.modelScaleX = modelScaleX;
    }

    public double getModelScaleY() {
        return modelScaleY;
    }

    public void setModelScaleY(double modelScaleY) {
        this.modelScaleY = modelScaleY;
    }

    public double getModelScaleZ() {
        return modelScaleZ;
    }

    public void setModelScaleZ(double modelScaleZ) {
        this.modelScaleZ = modelScaleZ;
    }
    
    public void clearAllData()
    {
        vertices = new ArrayList<>();
        uvCoords = new ArrayList<>();
        vertNorms = new ArrayList<>();
        faces = new ArrayList<>();
    }

}
