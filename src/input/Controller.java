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
    private Model3D flame;
    private Model3D player1Model;
    private Model3D player2Model;
    private RenderEngine renderer;
    private int currentPlayer = 1;
    private boolean enterDown = false;
   
    public Controller(Model3D player1Model, Model3D player2Model, Model3D flame, RenderEngine renderer) throws LWJGLException{
        this.character = player1Model;
        this.flame = flame;
        this.player1Model = player1Model;
        this.player2Model = player2Model;
        this.renderer = renderer;
        
        Keyboard.create();
    }
    
    public void update(){
        
        //DOWN PIVOT
        if(Keyboard.isKeyDown(Keyboard.KEY_W) && character.getModelRotX() > -90){
            double newRotX = character.getModelRotX() - 1.0;
            newRotX %= 360;
            character.setModelRotX(newRotX);
            flame.setModelRotX(newRotX);
            renderer.setRotAngleX((-1*newRotX) + 30);
        }
        
        //UP PIVOT
        if(Keyboard.isKeyDown(Keyboard.KEY_S) && character.getModelRotX() < 90){
            double newRotX = character.getModelRotX() + 1.0;
            newRotX %= 360;
            character.setModelRotX(newRotX);
            flame.setModelRotX(newRotX);
            renderer.setRotAngleX((-1*newRotX) + 30);
        }
        
        //LEFT PIVOT
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            double newRotY = character.getModelRotY() + 1.0;
            newRotY %= 360;
            character.setModelRotY(newRotY);
            flame.setModelRotY(newRotY);
            renderer.setRotAngleY(-1*newRotY);
        }
        
        //RIGHT PIVOT
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            double newRotY = character.getModelRotY() - 1.0;
            newRotY %= 360;
            character.setModelRotY(newRotY);
            flame.setModelRotY(newRotY);
            renderer.setRotAngleY(-1*newRotY);
        }
        
        //FLAMETHROWER
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            renderer.setFlameDisplay(true);
        } else {
            renderer.setFlameDisplay(false);
        }
        
        //SWAP CHARACTERS
        if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
            if(!enterDown){
                if(currentPlayer == 1){
                    currentPlayer = 2;
                    player2Model.setModelX(player1Model.getModelX());
                    player2Model.setModelY(player1Model.getModelY());
                    player2Model.setModelZ(player1Model.getModelZ());
                    player2Model.setModelRotX(player1Model.getModelRotX());
                    player2Model.setModelRotY(player1Model.getModelRotY());
                    player2Model.setModelRotZ(player1Model.getModelRotZ());
                    character = player2Model;
                    renderer.setPlayer(2);
                } else {
                    currentPlayer = 1;
                    player1Model.setModelX(player2Model.getModelX());
                    player1Model.setModelY(player2Model.getModelY());
                    player1Model.setModelZ(player2Model.getModelZ());
                    player1Model.setModelRotX(player2Model.getModelRotX());
                    player1Model.setModelRotY(player2Model.getModelRotY());
                    player1Model.setModelRotZ(player2Model.getModelRotZ());
                    character = player1Model;
                    renderer.setPlayer(1);
                }
                enterDown = true;
            }
        } else {
            enterDown = false;
        }
        
        movePlayerForward();
    }
    
    private void movePlayerForward(){
        double oldModelX = character.getModelX();
        double oldModelY = character.getModelY();
        double oldModelZ = character.getModelZ();
        
        double playerRotX = Math.toRadians(character.getModelRotX());
        double playerRotY = Math.toRadians(character.getModelRotY());
        
        character.setModelX(oldModelX - Math.sin(playerRotY) * 1 * Math.cos(playerRotX));
        character.setModelY(oldModelY + Math.sin(playerRotX) * 1);
        character.setModelZ(oldModelZ - Math.cos(playerRotY) * 1 * Math.cos(playerRotX));

        flame.setModelX(character.getModelX() - (36 * Math.sin(playerRotY)));
        flame.setModelZ(character.getModelZ() - (36 * Math.cos(playerRotY)));
        renderer.setCameraX(character.getModelX() + (9 * Math.sin(playerRotY)));
        renderer.setCameraZ(character.getModelZ() + (9 * Math.cos(playerRotY)));
        
        double oldFlameX = flame.getModelX();
        double oldFlameZ = flame.getModelZ();
        double oldCamX = renderer.getCameraX();
        double oldCamZ = renderer.getCameraZ();
        
        flame.setModelY(character.getModelY() + (36 * Math.sin(playerRotX)));
        renderer.setCameraY(character.getModelY() - (9 * Math.sin(playerRotX - (Math.PI/6))));
        
        double flameDelta = 36 - (36 * Math.cos(playerRotX));
        double camDelta = 9 - (9 * Math.cos(playerRotX - (Math.PI/6)));
        
        flame.setModelZ(oldFlameZ + (flameDelta * Math.cos(playerRotY)));
        flame.setModelX(oldFlameX + (flameDelta * Math.sin(playerRotY)));
        renderer.setCameraZ(oldCamZ - (camDelta * Math.cos(playerRotY)));
        renderer.setCameraX(oldCamX - (camDelta * Math.sin(playerRotY)));
        
    }

    public void recoilPlayer() {
        double oldModelX = character.getModelX();
        double oldModelY = character.getModelY();
        double oldModelZ = character.getModelZ();
        
        double playerRotX = Math.toRadians(character.getModelRotX());
        double playerRotY = Math.toRadians(character.getModelRotY());
        
        character.setModelX(oldModelX + Math.sin(playerRotY) * 1 * Math.cos(playerRotX));
        character.setModelY(oldModelY - Math.sin(playerRotX) * 1);
        character.setModelZ(oldModelZ + Math.cos(playerRotY) * 1 * Math.cos(playerRotX));

        flame.setModelX(character.getModelX() - (36 * Math.sin(playerRotY)));
        flame.setModelZ(character.getModelZ() - (36 * Math.cos(playerRotY)));
        renderer.setCameraX(character.getModelX() + (9 * Math.sin(playerRotY)));
        renderer.setCameraZ(character.getModelZ() + (9 * Math.cos(playerRotY)));
        
        double oldFlameX = flame.getModelX();
        double oldFlameZ = flame.getModelZ();
        double oldCamX = renderer.getCameraX();
        double oldCamZ = renderer.getCameraZ();
        
        flame.setModelY(character.getModelY() + (36 * Math.sin(playerRotX)));
        renderer.setCameraY(character.getModelY() - (9 * Math.sin(playerRotX - (Math.PI/6))));
        
        double flameDelta = 36 - (36 * Math.cos(playerRotX));
        double camDelta = 9 - (9 * Math.cos(playerRotX - (Math.PI/6)));
        
        flame.setModelZ(oldFlameZ + (flameDelta * Math.cos(playerRotY)));
        flame.setModelX(oldFlameX + (flameDelta * Math.sin(playerRotY)));
        renderer.setCameraZ(oldCamZ - (camDelta * Math.cos(playerRotY)));
        renderer.setCameraX(oldCamX - (camDelta * Math.sin(playerRotY)));
    }
}
