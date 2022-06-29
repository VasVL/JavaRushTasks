package com.javarush.games.minesweeper.objects;

import com.javarush.games.minesweeper.Tiles;

import java.util.HashMap;

public class Mario extends GameObject {

    public static HashMap<String, Animations[]> marioAnimations = new HashMap<>(2);
    public static HashMap<String, Tiles[]> marioTiles           = new HashMap<>(2);


    public Direction direction = Direction.RIGHT;

    public boolean isSuperMario = false;
    public boolean isFirePower  = false;
    public boolean isGodMode    = false;
    public int mSecGodModeTime  = 15000;
    public int mSecUntouchable  = 4000;

    public long startGodModeTime;
    public long startUntouchable;

    public Mario(double x, int y){
        super(x, y);
        marioAnimations.put("Normal",
                new Animations[]{
                        Animations.MARIO_MOVE_RIGHT, // 0
                        Animations.MARIO_MOVE_LEFT,  // 1
                        Animations.MARIO_GET_FLAG}); // 2
        marioAnimations.put("Super",
                new Animations[]{
                        Animations.SUPER_MARIO_MOVE_RIGHT,
                        Animations.SUPER_MARIO_MOVE_LEFT,
                        Animations.SUPER_MARIO_GET_FLAG});
        marioTiles.put("Normal",
                new Tiles[]{
                       Tiles.MARIO,
                        Tiles.MARIO_REVERSE,
                       Tiles.MARIO_MOVE_RIGHT_1,
                       Tiles.MARIO_MOVE_LEFT_1
                });
        marioTiles.put("Super",
                new Tiles[]{
                        Tiles.SUPER_MARIO,
                        Tiles.SUPER_MARIO_REVERSE,
                        Tiles.SUPER_MARIO_MOVE_RIGHT_1,
                        Tiles.SUPER_MARIO_MOVE_LEFT_1
                });
        setTile(Tiles.MARIO);
        setAnimation(Animations.MARIO_MOVE_RIGHT);
        isAnimationOn = false;
        isTouchable = true;
    }

    public void setSpeedX(double speed){
        speedX = speed;

        if(speed > 0)
        {
            direction = Direction.RIGHT;
        }
        else if(speed < 0)
        {
            direction = Direction.LEFT;
        }
    }


    public void setMarioTile(Tiles tile){
        Tiles[] tiles;
        if (isSuperMario) {
            tiles = marioTiles.get("Super");
        }
        else {
            tiles = marioTiles.get("Normal");
        }
        switch(tile) {
            case MARIO:
            case SUPER_MARIO:
            case MARIO_REVERSE:
            case SUPER_MARIO_REVERSE:
                if(direction == Direction.RIGHT) {
                    this.tile = tiles[0];
                }
                else{
                    this.tile = tiles[1];
                }
                break;
            case MARIO_MOVE_RIGHT_1:
            case SUPER_MARIO_MOVE_RIGHT_1:
            case MARIO_MOVE_LEFT_1:
            case SUPER_MARIO_MOVE_LEFT_1:
                if(direction == Direction.RIGHT) {
                    this.tile = tiles[2];
                }
                else{
                    this.tile = tiles[3];
                }
                break;
            default:
                this.tile = Tiles.DEFAULT;
        }
        if(this.tile != null) {
            x += (tileSizeX - this.tile.tile[0].length());
            y += (tileSizeY - this.tile.tile.length);
        }
        tileSizeY = this.tile.tile.length;
        tileSizeX = this.tile.tile[0].length();
        xEnd = x + tileSizeX;
        yEnd = y + tileSizeY;
    }

    @Override
    public void setAnimation(Animations animation){
        Animations[] animations;
        if (isSuperMario) {
            animations = marioAnimations.get("Super");
        }
        else {
            animations = marioAnimations.get("Normal");
        }
        switch(animation) {
            case MARIO_MOVE_RIGHT:
            case SUPER_MARIO_MOVE_RIGHT:
            case MARIO_MOVE_LEFT:
            case SUPER_MARIO_MOVE_LEFT:
                if(direction == Direction.RIGHT) {
                    this.animation = animations[0];
                }
                else {
                    this.animation = animations[1];
                }
                break;
            case MARIO_GET_FLAG:
            case SUPER_MARIO_GET_FLAG:
                this.animation = animations[2];
                break;
        }
    }

    public void becomingSuperMario(){
        isSuperMario = true;
        setMarioTile(Tiles.SUPER_MARIO);

        setAnimation(Animations.SUPER_MARIO_MOVE_RIGHT);
    }

    public void becomingAGod(){
        isGodMode = true;
        startGodModeTime = System.currentTimeMillis();
        startBlinking(mSecGodModeTime);
    }

    public void becomingAFireman(){
        isFirePower = true;
        startBlinking();
    }

    public void stopBeingAGod(){
        isGodMode = false;
        stopBlinking();
    }

    public void becomingUntouchable(){
        isSuperMario = false;
        isFirePower = false;
        isTouchable = false;
        startUntouchable = System.currentTimeMillis();
        setMarioTile(Tiles.MARIO);
        setAnimation(Animations.MARIO_MOVE_RIGHT);
    }

    public void stopBeingUntouchable(){
        isTouchable = true;
    }

    public enum Direction
    {
        LEFT,
        RIGHT
    }

}