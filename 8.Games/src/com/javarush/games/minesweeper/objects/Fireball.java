package com.javarush.games.minesweeper.objects;

import com.javarush.games.minesweeper.Tiles;

public class Fireball extends GameObject implements Movable{

    public Fireball(double x, int y){
        super(x, y);
        setTile(Tiles.FIREBALL_CLOCKWISE);
        speedX = 3;
        speedY = 3;
    }

    public Fireball(double x, int y, Mario.Direction direction){
        super(x, y);
        speedY = 3;
        if(direction == Mario.Direction.RIGHT)
        {
            setTile(Tiles.FIREBALL_CLOCKWISE);
            setAnimation(Animations.FIREBALL_CLOCKWISE);
            speedX = 3;
        }
        else
        {
            setTile(Tiles.FIREBALL_COUNTER_CLOCKWISE);
            setAnimation(Animations.FIREBALL_COUNTER_CLOCKWISE);
            speedX = -3;
        }
    }
}
