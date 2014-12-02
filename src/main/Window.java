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
        
        Model3D character = new Model3D();
        character.generateDefault();
        
        this.renderer = new RenderEngine();
        this.renderer.initView();
        this.renderer.addModel(character);
        
        this.input = new Controller(character);
    }
    
    public void display(){
        while(!Display.isCloseRequested()){
            input.update();
            renderer.run();
            
            Display.update();
            Display.sync(60);
        }
    }
    
    public void destroy(){
        Display.destroy();
    }
}
