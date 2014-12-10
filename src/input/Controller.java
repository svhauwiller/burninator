/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package input;

import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glScaled;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.util.ArrayList;

import geo.Model3D;
import geo.Point2D;
import geo.Point3D;
import geo.Poly3D;
import geo.RenderEngine;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

/**
 *
 * @author Wesley
 */
public class Controller {
    private Model3D character;
    private RenderEngine renderer;
    
    public Controller(Model3D character, RenderEngine renderer) throws LWJGLException{
        this.character = character;
        this.renderer = renderer;
        
        Keyboard.create();
    }
    
    public void update(){
        
        //DOWN PIVOT
        if(Keyboard.isKeyDown(Keyboard.KEY_W) && character.getModelRotX() > -90){
            double newRotX = character.getModelRotX() - 1.0;
            newRotX %= 360;
            character.setModelRotX(newRotX);
            renderer.setRotAngleX(-1*newRotX);
        }
        
        //UP PIVOT
        if(Keyboard.isKeyDown(Keyboard.KEY_S) && character.getModelRotX() < 90){
            double newRotX = character.getModelRotX() + 1.0;
            newRotX %= 360;
            character.setModelRotX(newRotX);
            renderer.setRotAngleX(-1*newRotX);
        }
        
        //LEFT PIVOT
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            double newRotY = character.getModelRotY() + 1.0;
            newRotY %= 360;
            character.setModelRotY(newRotY);
            renderer.setRotAngleY(-1*newRotY);
        }
        
        //RIGHT PIVOT
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            double newRotY = character.getModelRotY() - 1.0;
            newRotY %= 360;
            character.setModelRotY(newRotY);
            renderer.setRotAngleY(-1*newRotY);
        }
        
        movePlayerForward();
    }
    
    private void movePlayerForward(){
        double oldModelX = character.getModelX();
        double oldModelY = character.getModelY();
        double oldModelZ = character.getModelZ();
        
        double playerRotX = Math.toRadians(character.getModelRotX());
        double playerRotY = Math.toRadians(character.getModelRotY());
        
        character.setModelX(oldModelX - Math.sin(playerRotY) * 0.2 * Math.cos(playerRotX));
        character.setModelY(oldModelY + Math.sin(playerRotX) * 0.2);
        character.setModelZ(oldModelZ - Math.cos(playerRotY) * 0.2 * Math.cos(playerRotX));

        renderer.setCameraX(character.getModelX() + (4 * Math.sin(playerRotY)));
        renderer.setCameraZ(character.getModelZ() + (4 * Math.cos(playerRotY)));
        
        double oldCamX = renderer.getCameraX();
        double oldCamY = renderer.getCameraY();
        double oldCamZ = renderer.getCameraZ();
        
        renderer.setCameraY(character.getModelY() - (4 * Math.sin(playerRotX)));
        
        double distance = 4 * Math.cos(playerRotX);
        double delta = 4 - distance;
        
        renderer.setCameraZ(oldCamZ - (delta * Math.cos(playerRotY)));
        renderer.setCameraX(oldCamX - (delta * Math.sin(playerRotY)));
        
    }
}
