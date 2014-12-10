/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glScaled;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex3d;
import static org.lwjgl.util.glu.GLU.gluPerspective;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 *
 * @author Wesley
 */
public class RenderEngine {
    private final float FIELD_OF_VIEW = (float) 90.0;
    private final float ASPECT_RATIO = (float) ((float) 16.0/9.0);
    private final float NEAR_CLIP = (float) 1.0;
    private final float FAR_CLIP = (float) 1000.0;
    
    private double cameraX = 0;
    private double cameraY = 3;
    private double cameraZ = 2;
    private double rotAngleX = 0;
    private double rotAngleY = 0;
    
    private int numCol = 0;
    
    private ArrayList<Model3D> models;
    private ArrayList<String> textureFilepaths;
    private ArrayList<Texture> textures;
    
    public RenderEngine(){
        models = new ArrayList<>();
        textureFilepaths = new ArrayList<>();
        textures = new ArrayList<>();
    }
    
    public void initView(){
        glEnable(GL_TEXTURE_2D);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);  
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_DEPTH_TEST);
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(FIELD_OF_VIEW, ASPECT_RATIO, NEAR_CLIP, FAR_CLIP);
        glMatrixMode(GL_MODELVIEW);
    }
    
    public void addModel(Model3D model, String textureFilepath){
        models.add(model);
        textureFilepaths.add(textureFilepath);
    }
    
    public void loadTextures(){
        for(String filepath : textureFilepaths){
            Texture texture;
            String fileExt = filepath.split("[.]")[1].toUpperCase();
            try {
                texture = TextureLoader.getTexture(fileExt, new FileInputStream(filepath), true);

                System.out.println("Texture loaded: "+texture);
                System.out.println(">> Image width: "+texture.getImageWidth());
                System.out.println(">> Image height: "+texture.getImageWidth());
                System.out.println(">> Texture width: "+texture.getTextureWidth());
                System.out.println(">> Texture height: "+texture.getTextureHeight());
                System.out.println(">> Texture ID: "+texture.getTextureID());
                
                textures.add(texture);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    
    public void run(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        checkCollisions();
        redrawAll();
        
    }
    
    private void checkCollisions(){
        Model3D player = models.get(0);
        for(int i = 1; i < models.size(); i++){
            if(models.get(i).hasCollision(player)){
                System.out.println("COLLIDE " + i);
                numCol++;
            }
        }
    }
    
    private void redrawAll(){
        for(int i = 0; i < models.size(); i++){
            Model3D model = models.get(i);
            bindTextures(i);
            applyTransformations(model);
            drawPolygons(model);
        }
    }
    
    private void bindTextures(int modelIndex){
        Color.white.bind();
        Texture modelTexture = textures.get(modelIndex);
        modelTexture.bind();
    }
    
    private void applyTransformations(Model3D model){
        //APPLY GLOBAL TRANSFORMATUIONS (CAMERA)
        glLoadIdentity();
        glRotated(rotAngleX, 1.0, 0.0, 0.0);
        glRotated(rotAngleY, 0.0, 1.0, 0.0);
        glTranslated(-1*cameraX, -1*cameraY, -1*cameraZ);

        //APPLY LOCAL TRANSFORMATIONS
        glTranslated(model.getModelX(), model.getModelY(), model.getModelZ());
        glRotated(model.getModelRotY(), 0.0, 1.0, 0.0);
        glRotated(model.getModelRotX(), 1.0, 0.0, 0.0);
        glRotated(model.getModelRotZ(), 0.0, 0.0, 1.0);
        glScaled(model.getModelScaleX(), model.getModelScaleY(), model.getModelScaleZ());
    }
   
    private void drawPolygons(Model3D model){
        ArrayList<Poly3D> faces = model.getFaces();
        for(Poly3D face : faces){
            ArrayList<Integer> uvCoordIndicies = face.getUvCoordIndicies();
            ArrayList<Integer> vertexIndicies = face.getVertexIndicies();

            glBegin(GL_POLYGON);
            for(int j = 0; j < vertexIndicies.size(); j++){
                Point2D uvCoord = model.getUvCoordAt(uvCoordIndicies.get(j));
                Point3D vertex = model.getVertexAt(vertexIndicies.get(j));

                glTexCoord2d(uvCoord.getX(), uvCoord.getY());
                glVertex3d(vertex.getX(), vertex.getY(), vertex.getZ());
            }
            glEnd();
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
