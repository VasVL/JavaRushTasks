package com.javarush.games.minesweeper.objects;

import com.javarush.games.minesweeper.Tiles;

public class Flag extends GameObject implements Movable{

    public static int flagDownSpeed = 1;
    public static boolean isFlagUp = true;
    public Flag(double x, int y){
        super(x, y);
        setTile(Tiles.FLAG);
    }
}