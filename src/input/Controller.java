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
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            System.out.println("DOWN");
            
            double deltaX = (Math.cos(Math.toRadians(character.getModelRotY())));
            double deltaZ = (Math.sin(Math.toRadians(character.getModelRotY())));
            character.setModelRotX(character.getModelRotX() - deltaX);
            character.setModelRotZ(character.getModelRotZ() + deltaZ);
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            System.out.println("UP");
            double deltaX = (Math.cos(Math.toRadians(character.getModelRotY())));
            double deltaZ = (Math.sin(Math.toRadians(character.getModelRotY())));
            character.setModelRotX(character.getModelRotX() + deltaX);
            character.setModelRotZ(character.getModelRotZ() - deltaZ);
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            System.out.println("LEFT");
            double newRotY = character.getModelRotY() + 1.0;
            newRotY %= 360;
            character.setModelRotY(newRotY);
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            System.out.println("RIGHT");
            double newRotY = character.getModelRotY() - 1.0;
            newRotY %= 360;
            character.setModelRotY(newRotY);
        }
    }
}
