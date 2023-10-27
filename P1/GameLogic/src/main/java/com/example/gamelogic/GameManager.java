package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IScene;

public class GameManager {
    private static GameManager instance;
    private IEngine myEngine_;
    private int width_;
    private int height_;
    private int selectedColor;
    private boolean hasSelectedColor;

    // Paso 2: Hacer el constructor privado para evitar la creación de instancias adicionales.
    private GameManager() {
        // Constructor privado
    }

    // Paso 3: Crear un método público para obtener la única instancia de la clase.
    public static GameManager getInstance() {
        // Si la instancia no ha sido creada, la creamos.

        return instance;
    }


    public static int init(IEngine engine,int width,int height){
        instance = new GameManager();
        instance.myEngine_=engine;
        instance.width_=width;
        instance.height_=height;
        return 1;
    }

    // Ahora, puedes agregar métodos y propiedades a tu clase Singleton.
    // Por ejemplo:
    public void changeScene(IScene scene) {
        myEngine_.setScene(scene);
    }
    public int getwidth() {
       return width_;
    }
    public int getHeight() {
        return height_;
    }

    //para almacenar el color que ha cogido y quiere colocar en la matriz
    public void takeColor(int color) {
        this.hasSelectedColor = true;
        this.selectedColor = color;
        System.out.print("Han seleccionado un color: "+ color+"\n");
    }

    public boolean colorSelected() {
        return this.hasSelectedColor;
    }
    public IEngine getIEngine(){
        return  myEngine_;
    }
    public int getSelectedColor() {
        this.hasSelectedColor = false;
        return this.selectedColor;
    }
}