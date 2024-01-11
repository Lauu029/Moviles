package com.example.androidgame.GameLogic.Managers;

import android.util.Log;

import com.example.androidengine.Engine;
import com.example.androidgame.GameLogic.Difficulty;
import com.example.androidgame.GameLogic.Scenes.DifficultyScene;
import com.example.androidgame.GameLogic.Scenes.EndScene;
import com.example.androidgame.GameLogic.Scenes.GameScene;
import com.example.androidgame.GameLogic.Scenes.MenuScene;
import com.example.androidgame.GameLogic.Scenes.Scene;
import com.example.androidgame.GameLogic.Scenes.SceneNames;
import com.example.androidgame.GameLogic.Scenes.WorldGameScene;
import com.example.androidgame.GameLogic.Scenes.WorldScene;

import java.util.ArrayList;

public class SceneManager {
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

    //Añade una nueva escena, si la escena no es de tipo final, juego o mundo y ya estaba creada simplemente la carga
    public void addScene(Scene scene, int idScene) {
        if (sceneList_[idScene] == null || idScene == SceneNames.GAME.ordinal() ||idScene == SceneNames.WORLD_SCENE.ordinal() ||
                idScene == SceneNames.FINAL.ordinal() || idScene == SceneNames.WORLD_FINAL.ordinal() || idScene == SceneNames.WORLD.ordinal()) {
            sceneList_[idScene] = scene;
            sceneList_[idScene].init();
        }
        myEngine_ = GameManager.getInstance().getIEngine();
        myEngine_.setScene(sceneList_[idScene]);
    }

    //Activa la escena que está en la posicion del array solicitada
    public void setScene(int idScene) {
        if(sceneList_[idScene]!=null)
        myEngine_.setScene(sceneList_[idScene]);
        else {
            Scene scene=null;
            switch (idScene) {
                case 0:
                    scene= new MenuScene();
                    break;
                case 1:
                     scene= new DifficultyScene();
                    break;

                case 2:
                    scene= new GameScene();
                    break;

                case 3:
                    scene= new WorldScene();
                    break;

                case 7:
                    scene= new WorldGameScene();
                    break;
                default:

            }
        }
    }

    // Devuelve la escena en la posicion del array solicitada
    public Scene getScene(int idScene) {
        return sceneList_[idScene];
    }

}
