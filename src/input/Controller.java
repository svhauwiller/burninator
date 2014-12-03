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
import org.lwjgl.input.Mouse;

/**
 *
 * @author Wesley
 */
public class Controller {
    private Model3D character;
    private RenderEngine renderer;
    
    public Controller(Model3D character) throws LWJGLException{
        this.character = character;
        
        Keyboard.create();
    }
    
    public void update(){
        
        //DOWN PIVOT
        if(Keyboard.isKeyDown(Keyboard.KEY_W) && character.getModelRotX() > -90){
            double newRotX = character.getModelRotX() - 1.0;
            newRotX %= 360;
            character.setModelRotX(newRotX);
        }
        
        //UP PIVOT
        if(Keyboard.isKeyDown(Keyboard.KEY_S) && character.getModelRotX() < 90){
            double newRotX = character.getModelRotX() + 1.0;
            newRotX %= 360;
            character.setModelRotX(newRotX);
        }
        
        //LEFT PIVOT
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            double newRotY = character.getModelRotY() + 1.0;
            newRotY %= 360;
            character.setModelRotY(newRotY);
        }
        
        //RIGHT PIVOT
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            double newRotY = character.getModelRotY() - 1.0;
            newRotY %= 360;
            character.setModelRotY(newRotY);
        }
        
        movePlayerForward();
    }
    
    private void movePlayerForward(){
        double oldModelX = character.getModelX();
        double oldModelY = character.getModelY();
        double oldModelZ = character.getModelZ();
        
        character.setModelX(oldModelX - Math.sin(Math.toRadians(character.getModelRotY())) * 0.05);
        character.setModelY(oldModelY + Math.sin(Math.toRadians(character.getModelRotX())) * 0.05);
        character.setModelZ(oldModelZ - Math.cos(Math.toRadians(character.getModelRotY())) * 0.05);
    }
}
