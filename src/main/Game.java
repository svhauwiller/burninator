/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import geo.Model3D;
import org.lwjgl.LWJGLException;

/**
 *
 * @author Wesley
 */
public class Game {
    private final int WINDOW_WIDTH = 1280;
    private final int WINDOW_HEIGHT = 720;
    
    private final String PLAYER_MODEL_FILE = "car.obj";
    private final String PLAYER_TEXTURE_FILE = "car_j.jpg";
    
    private Window mainWindow;
    
    public Game(){
        mainWindow = new Window(WINDOW_WIDTH, WINDOW_HEIGHT);
    }
    
    public void initPlayer() throws LWJGLException{
        Model3D playerModel = new Model3D();
        playerModel.loadDataFromFile(PLAYER_MODEL_FILE);
        mainWindow.addModel(playerModel, PLAYER_TEXTURE_FILE);
        mainWindow.initPlayerController(playerModel);
    }
    
    public void run(){
        try{
            mainWindow.init();
            initPlayer();
            mainWindow.display();
        } catch (LWJGLException ex) {
            mainWindow.destroy();
        }
    }
}
