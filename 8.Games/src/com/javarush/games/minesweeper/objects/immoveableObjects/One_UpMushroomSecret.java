package com.javarush.games.minesweeper.objects.immoveableObjects;

import com.javarush.games.minesweeper.objects.*;
import com.javarush.games.minesweeper.Tiles;

public class One_UpMushroomSecret extends GameObject implements Secret{

    public One_UpMushroomSecret(int x, int y){
        super(x, y);
        tile = Tiles.INVISIBLE;
    }
}
