package com.example.gamelogic.Managers;

import com.example.engine.IEngine;
import com.example.gamelogic.Scenes.MenuScene;
import com.example.gamelogic.Scenes.Scene;
import com.example.gamelogic.Scenes.SceneNames;

public class SceneManager {
    private Scene[] sceneList_ = {null, null, null, null, null, null, null, null,null};
    // Unique instance of the class
    private static SceneManager instance;
    private IEngine myEngine_;


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

    //Añade una nueva escena, si la escena no es de tipo final, juego o mundo y ya estaba creada simplemente la carga
    public void addScene(Scene scene, int idScene) {
        if (sceneList_[idScene] == null || idScene == SceneNames.GAME.ordinal() ||idScene == SceneNames.CONTINUE.ordinal() ||
                idScene == SceneNames.FINAL.ordinal()) {
            sceneList_[idScene] = scene;
            sceneList_[idScene].init();
            if (idScene == SceneNames.GAME.ordinal())
                sceneList_[idScene].setGameScene();
        }
        myEngine_ = GameManager.getInstance_().getIEngine();
        myEngine_.setScene(sceneList_[idScene]);
    }

    //Activa la escena que está en la posicion del array solicitada
    public void setScene(int idScene) {
        if(sceneList_[idScene]!=null)
        myEngine_.setScene(sceneList_[idScene]);

    }

    // Devuelve la escena en la posicion del array solicitada
    public Scene getScene(int idScene) {
        return sceneList_[idScene];
    }

}
