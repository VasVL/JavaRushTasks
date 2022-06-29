package com.javarush.games.minesweeper.objects;

import com.javarush.games.minesweeper.Tiles;


public class Koopa extends GameObject implements Enemy{

    public long koopaSpeepStartTime;

    private static final int mSecKoopaSleepPeriod = 4000;
    public boolean isSleeping = false;
    private double speedBeforeSleeping;
    public Koopa(double x, int y){
        super(x, y);
        setTile(Tiles.KOOPA);
        setAnimation(Animations.KOOPA_MOVE_LEFT);
        speedX = -1.5;
        speedY = 1;
    }

    public void fallAsleep()
    {
        isSleeping = true;
        isAnimationOn = false;
        speedBeforeSleeping = speedX;
        speedX = 0;
        setTile(Tiles.KOOPA_SLEEP);
        koopaSpeepStartTime = System.currentTimeMillis();
    }

    public boolean checkSleeping()
    {
        if(isSleeping
                && System.currentTimeMillis() - koopaSpeepStartTime > mSecKoopaSleepPeriod)
        {
            isSleeping = false;
            isAnimationOn = true;
            speedX = speedBeforeSleeping;
        }
        return  isSleeping;
    }
}
