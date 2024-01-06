package com.example.androidgame.GameLogic.Managers;

import android.util.Log;

import com.example.androidengine.Engine;
import com.example.androidgame.GameLogic.Scenes.DifficultyScene;
import com.example.androidgame.GameLogic.Scenes.EndScene;
import com.example.androidgame.GameLogic.Scenes.GameScene;
import com.example.androidgame.GameLogic.Scenes.MenuScene;
import com.example.androidgame.GameLogic.Scenes.SaveDataScene;
import com.example.androidgame.GameLogic.Scenes.Scene;
import com.example.androidgame.GameLogic.Scenes.SceneNames;
import com.example.androidgame.GameLogic.Scenes.ShopScene;
import com.example.androidgame.GameLogic.Scenes.WorldScene;

public class SceneManager {
    private Scene[] sceneList_ = {null, null, null, null, null, null, null, null, null};
    // Unique instance of the class
    private static SceneManager instance;
    private Engine myEngine_;
    private int actualScene_;

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
        if (sceneList_[idScene] == null || idScene == SceneNames.GAME.ordinal() || idScene == SceneNames.WORLD_SCENE.ordinal() ||
                idScene == SceneNames.FINAL.ordinal() || idScene == SceneNames.WORLD_FINAL.ordinal() || idScene == SceneNames.WORLD.ordinal()) {
            sceneList_[idScene] = scene;
            sceneList_[idScene].init();
        }
        myEngine_ = GameManager.getInstance().getIEngine();
        myEngine_.setScene(sceneList_[idScene]);
        actualScene_ = idScene;
    }

    //Activa la escena que está en la posicion del array solicitada
    public void setScene(int idScene) {
        if (sceneList_[idScene] == null||idScene==SceneNames.WORLD.ordinal()) {
            Log.d("MainActivity", "ID scene loaded: " + idScene);
            Scene s =getTypeSceneByIndex(idScene);
            if(s!=null)
            addScene(getTypeSceneByIndex(idScene), idScene);
        } else {
            myEngine_.setScene(sceneList_[idScene]);
        }
            actualScene_ = idScene;
    }

    Scene getTypeSceneByIndex(int idScene) {
        switch (idScene) {
            case 0:
                return new MenuScene();
            case 1:
                return new DifficultyScene();
            case 2:
                return new GameScene();
            case 3:
                return new WorldScene();
            case 4:
                break;
            case 5:
                break;
            case 6:
                return new ShopScene();
            case 7:
                return new WorldScene();
            default:
                break;
        }
        return null;
    }

    // Devuelve la escena en la posicion del array solicitada
    public Scene getScene(int idScene) {
        return sceneList_[idScene];
    }

    //Devuelve el id de la escena actual
    public int getIdActualScene() {
        return actualScene_;
    }

}
