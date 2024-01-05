package com.example.gamelogic.Managers;

import com.example.engine.IEngine;
import com.example.engine.IScene;
import com.example.gamelogic.Board;
import com.example.gamelogic.Circles.TryCircle;
import com.example.gamelogic.Difficulty;
import com.example.gamelogic.GameTry;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameManager {
    private static GameManager instance_;
    private IEngine myEngine_;
    private IScene actualScene_;
    private int width_, height_;
    private Difficulty levelDificulty_;
    private Board board_;
    private int[] levelSolution_;
    private int[] savedSolution_;

    private int[] finalSolution_;
    private boolean daltonics_;
    private Board savedBoard_;
    private int savedTries;
    private int [][] savedMatrix;
    private GameManager() {
        // Constructor privado
    }

    public static GameManager getInstance_() {
        return instance_;
    }


    public static int init(IEngine engine, int width, int height) {
        instance_ = new GameManager();
        instance_.myEngine_ = engine;
        instance_.width_ = width;
        instance_.height_ = height;
        instance_.daltonics_ = false;
        SceneManager.init();
        return 1;
    }

    public void changeScene(IScene scene) {
        this.actualScene_ = scene;
        myEngine_.setScene(scene);
    }

    public int getwidth() {
        return width_;
    }

    public int getHeight_() {
        return height_;
    }

    /*Pone el color que se le pase en el círculo correspondiente y su id en la
    * solución temporal para comprobarla más tarde */
    public void takeColor(int color, int id) {
        board_.putNewColor(id, color);
    }

    public IEngine getIEngine() {
        return myEngine_;
    }

    //Prepara un nivel nuevo con un array para la solucion, la dificultad necesaria y resetea la solución final
    public void setLevel(Difficulty dif) {
        this.levelDificulty_ = dif;
        this.levelSolution_ = new int[dif.getSolutionColors()];
        resetLevelSolution();
    }

    public Difficulty getLevel() {
        return this.levelDificulty_;
    }

    public int[] getLevelSolution() {
        return this.levelSolution_;
    }

    public void resetLevelSolution() {
        for (int i = 0; i < levelSolution_.length; i++) {
            this.levelSolution_[i] = -1;
        }
    }

    public void putColorSolution(int id, int color_id) {
        this.levelSolution_[id] = color_id;
    }

    public void changeDaltonicsMode() {
        this.daltonics_ = !this.daltonics_;
        this.board_.changeDaltonics(this.daltonics_);
    }

    public void setBoard_(Board b) {
        this.board_ = b;
    }
    public void setSavedBoard(Board b){savedBoard_=b;}
    public boolean getDaltonic(){
        return this.daltonics_;
    }
    public void setFinalSolution(int [] finalSol){
        finalSolution_=finalSol;
    }
    public int[] getFinalSolution(){
        return finalSolution_;
    }
    public void saveGameProgress() throws IOException {
        int [] savedSolution= finalSolution_;
        int numActualTry=savedBoard_.getLastUsedTry();
        System.out.print("Solucion guardada:[ ");
        for(int i=0; i<savedSolution.length; i++){
            System.out.print(savedSolution[i]);
        }
        System.out.println("] ");
        System.out.println("------------------------------");
        System.out.println("Num tries tot: "+numActualTry);
        GameTry sizeTry=savedBoard_.getTryByIndex(0);
        int circlesPerTry=sizeTry.getTries().length;
        System.out.println("CirclesPerTry: "+circlesPerTry);
        int [][] triesMatrix=new int[numActualTry][circlesPerTry];

        for(int i=0; i<numActualTry;i++)
        {
            GameTry currTry=savedBoard_.getTryByIndex(i);
            TryCircle[] triesInside=currTry.getTries();
            for(int j=0; j<circlesPerTry; j++)
            {
                triesMatrix[i][j]=triesInside[j].getId();
            }
        }
        System.out.println("------------------------------");
        System.out.print("Circulos ya puestos:[ ");
        for(int i=0; i<triesMatrix.length;i++)
        {
            System.out.print("[ ");

            for(int j=0; j<triesMatrix[0].length; j++)
            {
                System.out.print(triesMatrix[i][j]);
                System.out.print(" , ");

            }
            System.out.print("] ");
            System.out.println(" ");


        }
        System.out.print("] ");
        try {
            FileWriter fileWriter = new FileWriter("Assets/saved_game.txt");

            BufferedWriter buff = new BufferedWriter(fileWriter);

            buff.write("Intentos: " + numActualTry);
            buff.newLine();
            buff.write("Solucion: ");
            for (int i = 0; i < savedSolution.length; i++) {
                int s=savedSolution[i];
                buff.write(" "+s);
            }
            buff.newLine();
            buff.write("Matriz: ");
            buff.newLine();

            for (int i = 0; i < triesMatrix.length; i++) {
                for (int j = 0; j < triesMatrix[0].length; j++) {

                    int t=triesMatrix[i][j];
                    buff.write(" "+t);
                }
                buff.newLine();
            }
            buff.close();
            fileWriter.close();
        }catch(IOException e)
        {
            System.out.println("Excepcion al esccribir archivo");
        }
    }
    public void readSavedFile(){
        try
        {
            FileReader fileReader = new FileReader("Assets/saved_game.txt");
            BufferedReader buffReader = new BufferedReader(fileReader);

            String line=buffReader.readLine();
            savedTries=Integer.parseInt(line.split(":")[1].trim());

            line=buffReader.readLine();

            String [] solucion=line.split(":")[1].trim().split(" ");
            savedSolution_ = new int[solucion.length];
            for (int i=0; i<solucion.length;i++)
            {
                savedSolution_[i]=Integer.parseInt(solucion[i]);
            }
            line=buffReader.readLine(); //ignoramos lo de matriz
            savedMatrix= new int[savedTries][savedSolution_.length-1];
            for(int i=0; i<savedTries; i++)
            {
                line=buffReader.readLine();
                String [] fila=line.trim().split(" ");
                savedMatrix[i] = new int[fila.length];
                for (int j=0; j<fila.length; j++)
                {
                    savedMatrix[i][j]=Integer.parseInt(fila[j]);
                }
            }
            buffReader.close();
            fileReader.close();
        }
        catch (IOException e)
        {

        }

    }
}