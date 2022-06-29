package com.javarush.games.minesweeper.objects.immoveableObjects;

import com.javarush.games.minesweeper.objects.*;
import com.javarush.games.minesweeper.Tiles;

public class TubeTopR extends GameObject{

    public TubeTopR(int x, int y){
        super(x, y);
        tile = Tiles.TUBE_TOP_R;
    }
}
