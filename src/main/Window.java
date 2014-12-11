/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import geo.Model3D;
import geo.RenderEngine;
import input.Controller;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 *
 * @author Wesley
 */
public class Window {
    private int width;
    private int height;
    private Controller input;
    private RenderEngine renderer;
    
    public Window(int width, int height){
        this.width = width;
        this.height = height;
    }
    
    public void init() throws LWJGLException{
        Display.setDisplayMode(new DisplayMode(width,height));
        Display.create();
        Display.setVSyncEnabled(true);
        
        this.renderer = new RenderEngine();
        this.renderer.initView();
    }
    
    public void addModel(Model3D model, String textureFilepath){
        renderer.addModel(model, textureFilepath);
    }
    
    public void initPlayerController(Model3D playerModel, Model3D flameModel) throws LWJGLException{
        input = new Controller(playerModel, flameModel, this.renderer);
    }
    
    public void addBurnTexture(String textureFilepath){
        renderer.addBurningBuildingTexture(textureFilepath);
    }
    
    private boolean checkCollisions(){
        Model3D player = renderer.getModelAt(0);
        for(int i = 2; i < renderer.numOfModels(); i++){
            if(renderer.getModelAt(i).hasCollision(player)){
                System.out.println("COLLIDE " + i);
                return true;
            }
        }
        return false;
    }
    
    public void display(){
        renderer.loadTextures();
        
        while(!Display.isCloseRequested()){
            input.update();
            if(checkCollisions()){
                input.recoilPlayer();
            }
            if(renderer.getFlameDisplay()){
                int burningBuildingIndex = checkBurningBuilding();
                if(burningBuildingIndex > 0){
                    System.out.println("BURNINATE!");
                    renderer.burnBuilding(burningBuildingIndex);
                }   
            }
            renderer.run();
            
            Display.update();
            Display.sync(60);
        }
    }
    
    public void destroy(){
        Display.destroy();
    }

    private int checkBurningBuilding() {
        Model3D flame = renderer.getModelAt(1);
        for(int i = 2; i < renderer.numOfModels(); i++){
            if(renderer.getModelAt(i).hasCollision(flame)){
                return i;
            }
        }
        return -1;
    }
}
