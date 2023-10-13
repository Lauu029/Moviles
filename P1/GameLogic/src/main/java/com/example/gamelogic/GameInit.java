package com.example.gamelogic;
enum LevelDifficulty {
    FACIL,
    MEDIO,
    DIFICIL,
    IMPOSIBLE
}
public class GameInit {
    private LevelDifficulty level_;

    GameInit(int difficulty){
        switch(difficulty){
            case 0:
                level_=LevelDifficulty.FACIL;
                break;
            case 1:
                level_=LevelDifficulty.MEDIO;
                break;
            case 2:
                level_=LevelDifficulty.DIFICIL;
                break;
            case 3:
                level_=LevelDifficulty.IMPOSIBLE;
                break;
        }
    }
}
