package com.javarush.games.minesweeper.objects.immoveableObjects;

import com.javarush.games.minesweeper.objects.*;
import com.javarush.games.minesweeper.Tiles;

public class Flagpole extends GameObject{

    public Flagpole(int x, int y){
        super(x, y);
        tile = Tiles.FLAGPOLE;
    }

    public Flagpole(int x, int y, Tiles tile){
        super(x ,y, tile);
    }
}
