package com.javarush.games.minesweeper.objects.immoveableObjects;

import com.javarush.games.minesweeper.objects.*;
import com.javarush.games.minesweeper.Tiles;

public class StarSecret extends GameObject implements Secret{

    public StarSecret(int x, int y){
        super(x, y);
        tile = Tiles.BRICK;
    }
}
