package com.javarush.games.minesweeper.objects;

import com.javarush.games.minesweeper.Tiles;
import com.javarush.games.minesweeper.MinesweeperGame;


public class GameObject {

    public boolean isTouchable = false;
    public boolean isPostMarioUpCollision = false;
    public boolean isAnimationOn = true;
    public boolean isBlinking = false;

    public double x;
    public int y;
    public double xEnd;
    public int yEnd;
    public double speedX = 0;
    public int speedY = 0;
    public Tiles tile;
    public int tileSizeX;
    public int tileSizeY;
    public Animations animation;

    public int mSecAnimationSpeed = MinesweeperGame.SEC/MinesweeperGame.FPS * 2;
    public int mSecBlinkingSpeed = 200;

    public int tileNumber = 0;
    public int blinkingNumber = 1;

    private final int mSecDefaultBlinkingTime = 2000;
    public static final int mSecPostMarioUpCollisionTime = 300;
    public int mSecBlinkingTime;
    public long startAnimationTime;
    public long startBlinkingTime;
    public long previousBlinkingTime;
    public long startPostMarioUpCollisionTime;





    public GameObject(double x, int y){
        this.x = x;
        this.y = y;
        setTile(Tiles.DEFAULT);
        startAnimationTime = System.currentTimeMillis();
    }

    public GameObject(double x, int y, Tiles tile){
        this.x = x;
        this.y = y;
        setTile(tile);
        startAnimationTime = System.currentTimeMillis();
    }

    public GameObject(GameObject gameObject, Tiles tile){
        setTile(tile);
        setX(gameObject.x + (gameObject.tileSizeX - tileSizeX));
        setY(gameObject.y + (gameObject.tileSizeY - tileSizeY));
        startAnimationTime = System.currentTimeMillis();
    }

    public void setPosition(double x, int y){
        this.x = x;
        this.y = y;
        xEnd = x + tileSizeX;
        yEnd = y + tileSizeY;
    }

    public void setX(double x){
        this.x = x;
        xEnd = x + tileSizeX;
    }

    public void setY(int y){
        this.y = y;
        yEnd = y + tileSizeY;
    }

    public void setTile(Tiles tile){
        if(this.tile != null) {
            x += (tileSizeX - tile.tile[0].length()) / 2;
            y += (tileSizeY - tile.tile.length) / 2;
        }
        this.tile = tile;
        tileSizeY = tile.tile.length;
        tileSizeX = tile.tile[0].length();
        xEnd = x + tileSizeX;
        yEnd = y + tileSizeY;
    }

    public void setTile(Tiles tile, double x, int y){
        this.x = x;
        this.y = y;
        setTile(tile);
    }

    public void postMarioUpCollisionOffset(){
        setY(y - 1);
        isPostMarioUpCollision = true;
        startPostMarioUpCollisionTime = System.currentTimeMillis();
    }
    public void endPostMarioUpCollisionOffset(){
        setY(y + 1);
        isPostMarioUpCollision = false;
    }

    public void setAnimation(Animations animation){
        this.animation = animation;
    }
    public void setAnimation(Animations animation, int mSecAnimationSpeed){
        this.animation = animation;
        this.mSecAnimationSpeed = mSecAnimationSpeed;
    }

    public void playAnimation(){
        if(isAnimationOn
                && System.currentTimeMillis() - startAnimationTime > mSecAnimationSpeed) {
            if (tileNumber >= animation.animationInTiles.length) {
                tileNumber = 0;
            }
            setTile(animation.animationInTiles[tileNumber]);
            tileNumber++;
            startAnimationTime = System.currentTimeMillis();
        }
    }

    public void playAnimation(int mSec){
        if(isAnimationOn
                && System.currentTimeMillis() - startAnimationTime > mSec) {
            if (tileNumber >= animation.animationInTiles.length) {
                tileNumber = 0;
            }
            setTile(animation.animationInTiles[tileNumber]);
            tileNumber++;
            startAnimationTime = System.currentTimeMillis();
        }
    }

    public void startBlinking(){
        isBlinking = true;
        startBlinkingTime = System.currentTimeMillis();
        previousBlinkingTime = System.currentTimeMillis();
        mSecBlinkingTime = mSecDefaultBlinkingTime;
    }

    public void startBlinking(int time){
        isBlinking = true;
        startBlinkingTime = System.currentTimeMillis();
        previousBlinkingTime = System.currentTimeMillis();
        mSecBlinkingTime = time;
    }

    public void stopBlinking(){
        isBlinking = false;
        blinkingNumber = 1;
    }
}
