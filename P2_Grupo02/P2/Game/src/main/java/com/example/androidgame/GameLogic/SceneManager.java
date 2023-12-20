package com.example.androidgame.GameLogic;

import android.util.Log;

import com.example.androidengine.Engine;

public class SceneManager {

    // Scene stack
    //private Stack<Scene> sceneStack = new Stack<>();
    private Scene[] sceneList_ = {null, null, null, null, null, null, null, null};
    // Unique instance of the class
    private static SceneManager instance;
    private Engine myEngine_;

    // Private constructor to prevent instantiation from outside the class
    private SceneManager() {
        // Private constructor
    }

    // Initialize the unique instance of SceneManager
    public static void init() {
        instance = new SceneManager();
        MenuScene gm = new MenuScene();
        instance.addScene(gm, SceneNames.MENU.ordinal());
        //instance.worldScenes_ = new Scene[LevelManager.getInstance().getNumWorlds()][20];
    }

    // Method to get the unique instance of the class
    public static SceneManager getInstance() {
        // Returns the unique instance
        return instance;
    }

    // Method to add a scene to the stack
    public void addScene(Scene scene, int idScene) {
        if (idScene == SceneNames.WORLD.ordinal())
            Log.d("MainActivity", "Nueva wordlScene");
        if (sceneList_[idScene] == null || idScene == SceneNames.GAME.ordinal() || idScene == SceneNames.FINAL.ordinal()) {
            Log.d("MainActivity","Estoy creando la escena");
            sceneList_[idScene] = scene;
            sceneList_[idScene].init();
        }
        //sceneStack.push(scene);
        myEngine_ = GameManager.getInstance().getIEngine();
        myEngine_.setScene(sceneList_[idScene]);
    }

    // Method to get the current scene
    public Scene getCurrentScene() {
//        if (!sceneStack.isEmpty()) {
//            return sceneStack.peek();
//        } else {
//            return null; // The stack is empty
//        }
        return null;
    }

    // Method to switch to the previous scene
    public void setScene(int idScene) {
        myEngine_.setScene(sceneList_[idScene]);
//        if (sceneStack.size() > 1) {
//            sceneStack.pop(); // Removes the current scene
//            // The new current scene is now the one at the top of the stack
//            myEngine_=GameManager.getInstance().getIEngine();
//            myEngine_.setScene(getCurrentScene());
//        }
    }
    public Scene getScene(int idScene){
        return sceneList_[idScene];
    }

    // Other methods and properties of the SceneManager class can go here


}
