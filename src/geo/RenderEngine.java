/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geo;

import java.util.ArrayList;
import java.util.Iterator;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glScaled;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex3d;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 *
 * @author Wesley
 */
public class RenderEngine {
    private final float FIELD_OF_VIEW = (float) 90.0;
    private final float ASPECT_RATIO = (float) ((float) 4.0/3.0);
    private final float NEAR_CLIP = (float) 1.0;
    private final float FAR_CLIP = (float) 1000.0;
    
    private double cameraX = 0;
    private double cameraY = 0;
    private double cameraZ = -10;
    private double rotAngleX = 0;
    private double rotAngleY = 0;
    
    private ArrayList<Model3D> models;
    
    public RenderEngine(){
        models = new ArrayList<>();
    }
    
    public void initView(){
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(FIELD_OF_VIEW, ASPECT_RATIO, NEAR_CLIP, FAR_CLIP);
        glMatrixMode(GL_MODELVIEW);
    }
    
    public void addModel(Model3D model){
        models.add(model);
    }
    
    public void run(){
        glClear(GL_COLOR_BUFFER_BIT);
        
        
//        Iterator<Line3D> lineIter = model.getLines();
//        while(lineIter.hasNext()){
//            Line3D line = lineIter.next();
//            glBegin(GL_LINES);
//                glVertex3d(line.start.getX(), line.start.getY(), line.start.getZ());
//                glVertex3d(line.end.getX(), line.end.getY(), line.end.getZ());
//            glEnd();
//        }
//        
//        glLoadIdentity();
//        glTranslated(cameraX, cameraY, cameraZ);
        
        for(int i = 0; i < models.size(); i++){
            glLoadIdentity();
            glRotated(rotAngleX, 1.0, 0.0, 0.0);
            glRotated(rotAngleY, 0.0, 1.0, 0.0);
            glTranslated(cameraX, cameraY, cameraZ);
            
            Model3D model = models.get(i);
            ArrayList<Poly3D> faces = model.getFaces();
            
            glTranslated(model.getModelX(), model.getModelY(), model.getModelZ());
            glRotated(model.getModelRotY(), 0.0, 1.0, 0.0);
            glRotated(model.getModelRotX(), 1.0, 0.0, 0.0);
            glRotated(model.getModelRotZ(), 0.0, 0.0, 1.0);
            glScaled(model.getModelScaleX(), model.getModelScaleY(), model.getModelScaleZ());

            for(Poly3D face : faces){
                ArrayList<Integer> vertexIndicies = face.getVertexIndicies();
                glBegin(GL_POLYGON);
                for(int j = 0; j < vertexIndicies.size(); j++){
                    Point3D vertex = model.getVertexAt(vertexIndicies.get(j));
                    glVertex3d(vertex.getX(), vertex.getY(), vertex.getZ());
                }
                glEnd();
            }
        }
    }

    public double getCameraX() {
        return cameraX;
    }

    public void setCameraX(double cameraX) {
        this.cameraX = cameraX;
    }

    public double getCameraY() {
        return cameraY;
    }

    public void setCameraY(double cameraY) {
        this.cameraY = cameraY;
    }

    public double getCameraZ() {
        return cameraZ;
    }

    public void setCameraZ(double cameraZ) {
        this.cameraZ = cameraZ;
    }

    public double getRotAngleX() {
        return rotAngleX;
    }

    public void setRotAngleX(double rotAngleX) {
        this.rotAngleX = rotAngleX;
    }

    public double getRotAngleY() {
        return rotAngleY;
    }

    public void setRotAngleY(double rotAngleY) {
        this.rotAngleY = rotAngleY;
    }
}
