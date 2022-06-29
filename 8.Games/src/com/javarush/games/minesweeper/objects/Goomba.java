package com.javarush.games.minesweeper.objects;


public class Goomba extends GameObject implements Enemy{

    public Goomba(double x, int y){
        super(x, y);
        setAnimation(Animations.GOOMBA_MOVE);
        speedX = -1.5;
        speedY = 1;
    }
}
