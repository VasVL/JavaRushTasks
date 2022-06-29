package com.javarush.games.minesweeper.objects.immoveableObjects;

import com.javarush.games.minesweeper.objects.*;
import com.javarush.games.minesweeper.Tiles;

public class TubeTopL extends GameObject{

    public TubeTopL(int x, int y){
        super(x, y);
        tile = Tiles.TUBE_TOP_L;
    }
}