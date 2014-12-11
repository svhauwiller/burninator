/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geo;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHT1;
import static org.lwjgl.opengl.GL11.GL_LIGHT2;
import static org.lwjgl.opengl.GL11.GL_LIGHT3;
import static org.lwjgl.opengl.GL11.GL_LIGHT4;
import static org.lwjgl.opengl.GL11.GL_LIGHT5;
import static org.lwjgl.opengl.GL11.GL_LIGHT6;
import static org.lwjgl.opengl.GL11.GL_LIGHT7;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_LOCAL_VIEWER;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLightModeli;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glNormal3d;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glScaled;
import static org.lwjgl.opengl.GL11.glShadeModel;
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
    private double rotAngleX = 30;
    private double rotAngleY = 0;
    
    private boolean flameDisplay = false;
    
    private ArrayList<Model3D> models;
    private ArrayList<String> textureFilepaths;
    private ArrayList<Integer> modelTextureIndicies;
    private ArrayList<Texture> textures;
    
    public RenderEngine(){
        models = new ArrayList<>();
        textureFilepaths = new ArrayList<>();
        modelTextureIndicies = new ArrayList<>();
        textures = new ArrayList<>();
    }
    
    public void initView(){
        glEnable(GL_TEXTURE_2D);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 
//        glEnable(GL_LIGHTING);
//        glShadeModel(GL_SMOOTH);
//        glEnable(GL_LIGHT0);
//        
//        float[] posVert = {50.0f,60.0f,-150.0f,1.0f};
//        float[] ambColorVert = {1.0f, 1.0f, 1.0f, 1.0f};
//        float[] difColorVert = {1.0f, 1.0f, 1.0f, 1.0f};
//        float[] specColorVert = {1.0f, 1.0f, 1.0f, 1.0f};
//        FloatBuffer position = convertToFB(posVert);
//        FloatBuffer ambientColor = convertToFB(ambColorVert);
//        FloatBuffer diffuseColor = convertToFB(difColorVert);
//        FloatBuffer specularColor = convertToFB(specColorVert);
//        glLight(GL_LIGHT1, GL_POSITION, position);
//        glLight(GL_LIGHT1, GL_AMBIENT, ambientColor);
//        glLight(GL_LIGHT1, GL_DIFFUSE, diffuseColor);
//        glLight(GL_LIGHT1, GL_SPECULAR, specularColor);
//        glEnable(GL_LIGHT1);
//        glEnable(GL_LIGHT2);
//        glEnable(GL_LIGHT3);
//        glEnable(GL_LIGHT4);
//        glEnable(GL_LIGHT5);
//        glEnable(GL_LIGHT6);
//        glEnable(GL_LIGHT7);
//        glLightModeli(GL_LIGHT_MODEL_LOCAL_VIEWER, GL_TRUE);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_DEPTH_TEST);
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(FIELD_OF_VIEW, ASPECT_RATIO, NEAR_CLIP, FAR_CLIP);
        glMatrixMode(GL_MODELVIEW);
    }
    
    private FloatBuffer convertToFB(float[] vertices){
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4); 
        vbb.order(ByteOrder.nativeOrder());    // use the device hardware's native byte order
        FloatBuffer fb = vbb.asFloatBuffer();  // create a floating point buffer from the ByteBuffer
        fb.put(vertices);    // add the coordinates to the FloatBuffer
        fb.position(0);
        return fb;
    }
    
    public void addModel(Model3D model, String textureFilepath){
        models.add(model);
        if(textureFilepaths.contains(textureFilepath)){
            modelTextureIndicies.add(textureFilepaths.indexOf(textureFilepath));
        } else {
            textureFilepaths.add(textureFilepath);
            modelTextureIndicies.add(textureFilepaths.size() - 1);
        }
    }
    
    public Model3D getModelAt(int index){
        return models.get(index);
    }
    
    public int numOfModels(){
        return models.size();
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
        for(int i = 0; i < models.size(); i++){
            if(i == 1 && flameDisplay == false){
                continue;
            }
            Model3D model = models.get(i);
            bindTextures(i);
            applyTransformations(model);
            drawPolygons(model);
        }
    }
    
    private void bindTextures(int modelIndex){
        Color.white.bind();
        Texture modelTexture = textures.get(modelTextureIndicies.get(modelIndex));
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
            ArrayList<Integer> vertexNormIndicies = face.getVertexNormIndicies();

            glBegin(GL_POLYGON);
            for(int j = 0; j < vertexIndicies.size(); j++){
                Point2D uvCoord = model.getUvCoordAt(uvCoordIndicies.get(j));
                Point3D vertex = model.getVertexAt(vertexIndicies.get(j));
                Point3D vertexNorm = model.getVertNormAt(j);

                glTexCoord2d(uvCoord.getX(), uvCoord.getY());
                glVertex3d(vertex.getX(), vertex.getY(), vertex.getZ());
                glNormal3d(vertexNorm.getX(), vertexNorm.getY(), vertexNorm.getZ());
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

    public void setFlameDisplay(boolean flameDisplay) {
        this.flameDisplay = flameDisplay;
    }
}
