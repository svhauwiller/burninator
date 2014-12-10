/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package input;


import geo.Model3D;
import geo.RenderEngine;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

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
            renderer.setRotAngleX((-1*newRotX) + 30);
        }
        
        //UP PIVOT
        if(Keyboard.isKeyDown(Keyboard.KEY_S) && character.getModelRotX() < 90){
            double newRotX = character.getModelRotX() + 1.0;
            newRotX %= 360;
            character.setModelRotX(newRotX);
            renderer.setRotAngleX((-1*newRotX) + 30);
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

        renderer.setCameraX(character.getModelX() + (9 * Math.sin(playerRotY)));
        renderer.setCameraZ(character.getModelZ() + (9 * Math.cos(playerRotY)));
        
        double oldCamX = renderer.getCameraX();
        double oldCamY = renderer.getCameraY();
        double oldCamZ = renderer.getCameraZ();
        
        renderer.setCameraY(character.getModelY() - (9 * Math.sin(playerRotX - (Math.PI/6))));
        
        double distance = 9 * Math.cos(playerRotX - (Math.PI/6));
        double delta = 9 - distance;
        
        renderer.setCameraZ(oldCamZ - (delta * Math.cos(playerRotY)));
        renderer.setCameraX(oldCamX - (delta * Math.sin(playerRotY)));
        
    }

    public void recoilPlayer() {
        double oldModelX = character.getModelX();
        double oldModelY = character.getModelY();
        double oldModelZ = character.getModelZ();
        
        double playerRotX = Math.toRadians(character.getModelRotX());
        double playerRotY = Math.toRadians(character.getModelRotY());
        
        character.setModelX(oldModelX + Math.sin(playerRotY) * 0.2 * Math.cos(playerRotX));
        character.setModelY(oldModelY - Math.sin(playerRotX) * 0.2);
        character.setModelZ(oldModelZ + Math.cos(playerRotY) * 0.2 * Math.cos(playerRotX));

        renderer.setCameraX(character.getModelX() + (9 * Math.sin(playerRotY)));
        renderer.setCameraZ(character.getModelZ() + (9 * Math.cos(playerRotY)));
        
        double oldCamX = renderer.getCameraX();
        double oldCamY = renderer.getCameraY();
        double oldCamZ = renderer.getCameraZ();
        
        renderer.setCameraY(character.getModelY() - (9 * Math.sin(playerRotX - (Math.PI/6))));
        
        double distance = 9 * Math.cos(playerRotX - (Math.PI/6));
        double delta = 9 - distance;
        
        renderer.setCameraZ(oldCamZ - (delta * Math.cos(playerRotY)));
        renderer.setCameraX(oldCamX - (delta * Math.sin(playerRotY)));
    }
}
