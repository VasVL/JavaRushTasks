package com.javarush.games.minesweeper.objects;

import com.javarush.games.minesweeper.Tiles;


public class DeadGameObject extends GameObject implements Movable{

    public long creationTime;
    public static final int mSecDeadObjectsLifeTime = 1000;

    public DeadGameObject(double x, int y, Tiles tile, int startSpeedX, int startSpeedY){
        super(x, y, tile);
        speedX = startSpeedX;
        speedY = startSpeedY;
    }
    public DeadGameObject(GameObject gameObject, double startSpeedX, int startSpeedY){
        this(gameObject, startSpeedX);
        speedY = startSpeedY;
    }
    public DeadGameObject(GameObject gameObject, double startSpeedX){
        this(gameObject);
        speedX = startSpeedX;
    }

    public DeadGameObject(GameObject gameObject){
        super(gameObject.x, gameObject.y);
        switch(gameObject.animation){
            case GOOMBA_MOVE:
                setTile(Tiles.GOOMBA_DOWN);
                break;
            case KOOPA_MOVE_LEFT:
            case KOOPA_MOVE_RIGHT:
                setTile(Tiles.KOOPA_SLEEP);
                break;
            case FIREBALL_CLOCKWISE:
            case FIREBALL_COUNTER_CLOCKWISE:
                setTile(Tiles.FIREBALL_CLOCKWISE);
                setAnimation(Animations.FIREBALL_BLAST, 100);
                isAnimationOn = true;
                break;
            default:
                setTile(Tiles.DEFAULT);
        }
        setX(gameObject.x + (gameObject.tileSizeX - tileSizeX));
        setY(gameObject.y + (gameObject.tileSizeY - tileSizeY));
        creationTime = System.currentTimeMillis();
    }

    public DeadGameObject(GameObject gameObject, Tiles tile) {
        super(gameObject, tile);
        creationTime = System.currentTimeMillis();
    }

    public boolean checkDeath(){
        return System.currentTimeMillis() - creationTime > mSecDeadObjectsLifeTime;
    }
}
