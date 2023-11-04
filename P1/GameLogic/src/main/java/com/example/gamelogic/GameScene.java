package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IScene;
import com.example.engine.TouchEvent;

import java.util.ArrayList;

public class GameScene implements IScene {
    private Solution mySolution;
    private IEngine iEngine;
    private ArrayList<IGameObject> iGameObjects = new ArrayList<>();
    private int width, height;
    private ButtonDaltonics buttonDaltonics;
    private Board gameBoard;
    private IFont font;
    private Difficulty lev;
    private GameManager gm;

    public GameScene(IEngine IEngine, int w, int h) {
        this.iEngine = IEngine;
        this.width = w;
        this.height = h;
    }
    //Inicializa los botones, el tablero y la solución
    @Override
    public void init() {
        this.font = this.iEngine.getGraphics().newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        this.gm = GameManager.getInstance();
        this.lev = this.gm.getLevel();
        mySolution = new Solution();
        mySolution.createSolution(lev.isRepeat(), lev.getSolutionColors(), lev.getPosibleColors(), lev.getTries());
        this.gameBoard = new Board( lev.getSolutionColors(), lev.getTries(), lev.getPosibleColors(), lev.isRepeat(), width, height);
        addGameObject(gameBoard);
        gm.setBoard(this.gameBoard);
        this.buttonDaltonics =new ButtonDaltonics(70,50,this.width -70,1);
        addGameObject(buttonDaltonics);
        iEngine.getGraphics().setColor(0xFF000000);
        for (IGameObject g : iGameObjects) {
            g.init();
        }
        ButtonImage but2=new ButtonImage("cruz.png",40,40, 0,0,SceneNames.LEVEL);
        this.addGameObject(but2);
    }

    public void addGameObject(IGameObject gm) {
        iGameObjects.add(gm);
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        for (IGameObject g : iGameObjects) {
            for (TouchEvent event : events) {
                if (g.handleInput(event))
                    return;
            }
        }
    }

    @Override
    public void render() {
        IGraphics graph = iEngine.getGraphics();
        graph.clear(0xFFfff0f6);
        for (IGameObject g : iGameObjects) {
            g.render(graph);
        }
    }
    /*Comprueba si todas las casillas del intento actual se han llenado con algún color
     * -1 significa que no tienen color. Si está completa se comprueba si la solución es correcta,
     * si se ha ganado el juego o perdido por superar el numero de  intentos y si no se ha ganado
     * ni se ha acabado crea nuevas pistas en la clase tablero y avanza al siguiente intento*/
    @Override
    public void update(double time) {
        int[] tempSol = gm.getLevelSolution();
        int i = 0;
        boolean isComplete = true;
        while (i < tempSol.length && isComplete) {
            if (tempSol[i] == -1)
                isComplete = false;
            i++;
        }
        if (isComplete) {
            mySolution.check(tempSol);
            int try_ = this.gameBoard.getAcutalTry();
            if (mySolution.getCorrectPos(try_) == this.lev.getSolutionColors()) {
                EndScene end = new EndScene(this.iEngine, this.width, this.height, true, mySolution.getSol(), try_);
                this.gm.changeScene(end);
            } else if (try_ == lev.getTries() -1) {
                EndScene end = new EndScene(this.iEngine, this.width, this.height, false, mySolution.getSol(), try_);
                this.gm.changeScene(end);
            } else {
                gameBoard.setNewHints(mySolution.getCorrectPos(try_), mySolution.getCorrectColor(try_));
                gameBoard.nexTry();
            }
        }
        for (int j = 0; j < iGameObjects.size(); j++) {
            iGameObjects.get(j).update(time);
        }
    }
}
