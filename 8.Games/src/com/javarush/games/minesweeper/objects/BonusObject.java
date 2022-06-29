package com.javarush.games.minesweeper.objects;

import com.javarush.games.minesweeper.Tiles;

public class BonusObject extends GameObject implements Movable{

    public Bonus bonus;
    public static int growthSpeed = 2;
    public int growth = 0;

    public BonusObject(double x, int y, Bonus bonus){
        super(x, y);
        this.bonus = bonus;

        switch(bonus){
            case COIN:
                setTile(Tiles.COIN);
                setAnimation(Animations.COIN, 100);
                growth = 8;
                speedY = -7;
                break;
            case SUPER_MARIO:
                setTile(Tiles.MAGIC_MUSHROOM);
                speedX = 2;
                isTouchable = true;
                break;
            case EXTRA_LIFE:
                setTile(Tiles.ONE_UP_MUSHROOM);
                speedX = 2;
                isTouchable = true;
                break;
            case FIRE:
                setTile(Tiles.SUNFLOWER);
                setAnimation(Animations.SUNFLOWER);
                isTouchable = true;
                break;
            case GOD_MODE:
                setTile(Tiles.STAR);
                setAnimation(Animations.STAR);
                speedX = 2;
                speedY = -7;
                isTouchable = true;
                break;
            default:
                setTile(Tiles.DEFAULT);
        }
    }

    public enum Bonus{
        COIN,
        SUPER_MARIO,
        EXTRA_LIFE,
        FIRE,
        GOD_MODE
    }
}