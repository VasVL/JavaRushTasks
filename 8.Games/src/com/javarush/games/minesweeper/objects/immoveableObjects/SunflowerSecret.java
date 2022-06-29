package com.javarush.games.minesweeper.objects.immoveableObjects;

import com.javarush.games.minesweeper.objects.*;
import com.javarush.games.minesweeper.Tiles;

public class SunflowerSecret extends GameObject implements Secret{

    public SunflowerSecret(int x, int y){
        super(x, y);
        tile = Tiles.SECRET;
    }
}
