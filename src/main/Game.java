/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.util.ArrayList;
import java.util.List;

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
    
    private final String GROUND_MODEL_FILE = "square.obj";
    private final String PAVEMENT_TEXTURE_FILE = "pavement.jpg";
    
    private final String BUILDING_MODEL_FILE = "building1.obj";
    private final String BUILDING_TEXTURE_FILE = "mirror_building.jpg";

    private int numOfBuildings = 5; // currently we square this number, so there are numOfBuildings x numOfBuildings
    
    private List<Model3D> listOfOthers = new ArrayList<Model3D>();
    
    private Window mainWindow;
    
    public Game(){
        mainWindow = new Window(WINDOW_WIDTH, WINDOW_HEIGHT);
    }
    
    public void initPlayer() throws LWJGLException{
        Model3D playerModel = new Model3D();
        playerModel.loadDataFromFile(PLAYER_MODEL_FILE);
        playerModel.createCollider();
        mainWindow.addModel(playerModel, PLAYER_TEXTURE_FILE);
        
        mainWindow.initPlayerController(playerModel);
    }
    
    private void generateEnv() {
    	Model3D groundModel = new Model3D();
    	groundModel.loadDataFromFile(GROUND_MODEL_FILE);
    	mainWindow.addModel(groundModel, PAVEMENT_TEXTURE_FILE);
    	listOfOthers.add(groundModel);
    	
    	for(int i = 1; i < numOfBuildings + 1; i++)
    	{
    		for(int j = 1; j < numOfBuildings + 1; j++)
    		{
		    	Model3D buildingModel = new Model3D();
		    	buildingModel.loadDataFromFile(BUILDING_MODEL_FILE);
		    	buildingModel.setModelX(20*i);
		    	buildingModel.setModelY(0); // 50 is the height of building1.obj
		    	buildingModel.setModelZ(20*j);
		    	buildingModel.setModelScaleX(.5);
		    	buildingModel.setModelScaleY(1);
		    	buildingModel.setModelScaleZ(.5);
                        buildingModel.createCollider();
		    	mainWindow.addModel(buildingModel, BUILDING_TEXTURE_FILE);
		    	listOfOthers.add(buildingModel);
    		}
    	}
    }
    
    public void run(){
        try{
            mainWindow.init();
            initPlayer();
            generateEnv();
            mainWindow.display();
        } catch (LWJGLException ex) {
            mainWindow.destroy();
        }
    }
}
