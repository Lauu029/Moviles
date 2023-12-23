package com.example.androidgame.GameLogic;

import android.util.Log;

import com.example.androidengine.Engine;
import com.example.androidgame.GameLogic.Scenes.Scene;
import com.example.androidgame.GameLogic.Scenes.SceneNames;

public class SceneManager {

    // Scene stack
    //private Stack<Scene> sceneStack = new Stack<>();
    private Scene[] sceneList_ = {null, null, null, null, null, null, null, null};
    // Unique instance of the class
    private static SceneManager instance;
    private Engine myEngine_;


    private SceneManager() {

    }


    public static void init() {
        instance = new SceneManager();
        MenuScene gm = new MenuScene();
        instance.addScene(gm, SceneNames.MENU.ordinal());

    }


    public static SceneManager getInstance() {

        return instance;
    }


    public void addScene(Scene scene, int idScene) {
        if (idScene == SceneNames.WORLD.ordinal())
            Log.d("MainActivity", "Nueva wordlScene");
        if (sceneList_[idScene] == null || idScene == SceneNames.GAME.ordinal() ||
                idScene == SceneNames.FINAL.ordinal()||idScene == SceneNames.WORLD_FINAL.ordinal()) {
            Log.d("MainActivity","Estoy creando la escena");
            sceneList_[idScene] = scene;
            sceneList_[idScene].init();
        }

        myEngine_ = GameManager.getInstance().getIEngine();
        myEngine_.setScene(sceneList_[idScene]);
    }


    // Method to switch to the previous scene
    public void setScene(int idScene) {
        myEngine_.setScene(sceneList_[idScene]);

    }
    public Scene getScene(int idScene){
        return sceneList_[idScene];
    }

    // Other methods and properties of the SceneManager class can go here


}
