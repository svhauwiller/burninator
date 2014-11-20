/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import org.lwjgl.LWJGLException;

/**
 *
 * @author Wesley
 */
public class Game {
    private final int WINDOW_WIDTH = 1280;
    private final int WINDOW_HEIGHT = 720;
    
    private Window mainWindow;
    
    public Game(){
        mainWindow = new Window(WINDOW_WIDTH, WINDOW_HEIGHT);
    }
    
    public void run(){
        try{
            mainWindow.init();
            mainWindow.display();
        } catch (LWJGLException ex) {
            mainWindow.destroy();
        }
    }
}
