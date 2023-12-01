package com.example.androidgame.GameLogic;

import com.example.androidengine.Engine;

import java.util.Stack;

public class SceneManager {

    // Scene stack
    private Stack<Scene> sceneStack = new Stack<>();

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
        instance.addScene(gm);
    }

    // Method to get the unique instance of the class
    public static SceneManager getInstance() {
        // Returns the unique instance

        return instance;
    }

    // Method to add a scene to the stack
    public void addScene(Scene scene) {
        sceneStack.push(scene);
        myEngine_=GameManager.getInstance().getIEngine();
        myEngine_.setScene(scene);

    }

    // Method to get the current scene
    public Scene getCurrentScene() {
        if (!sceneStack.isEmpty()) {
            return sceneStack.peek();
        } else {
            return null; // The stack is empty
        }
    }

    // Method to switch to the previous scene
    public void switchToPreviousScene() {
        if (sceneStack.size() > 1) {
            sceneStack.pop(); // Removes the current scene
            // The new current scene is now the one at the top of the stack
            myEngine_=GameManager.getInstance().getIEngine();
            myEngine_.setScene(getCurrentScene());
        }
    }

    // Other methods and properties of the SceneManager class can go here

    public void doSomething() {
        System.out.println("Doing something in SceneManager");
    }
}
