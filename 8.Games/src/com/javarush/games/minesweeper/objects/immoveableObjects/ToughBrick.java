package com.javarush.games.minesweeper.objects.immoveableObjects;

import com.javarush.games.minesweeper.objects.*;
import com.javarush.games.minesweeper.Tiles;

public class ToughBrick extends GameObject{

    public int coinsCount = 10;

    public ToughBrick(int x, int y){
        super(x, y);
        tile = Tiles.BRICK;
    }
}