package com.javarush.games.minesweeper.objects;

import com.javarush.games.minesweeper.Alphabet;



public class GameString{

    public int x;
    public int y;
    public String value;
    public Alphabet[] valueA;
    public boolean isMove = true;
    public int mSecLifeTime = 500;
    public long creationTime;

    public GameString(String str, int x, int y){
        this.x = x;
        this.y = y;
        value = str;
        valueA = Alphabet.convertString(str);
        creationTime = System.currentTimeMillis();
    }

    public GameString(String str, int x, int y, boolean isMove){
        this(str, x, y);
        this.isMove = isMove;
    }

    public void moveUp(){
        y -=3;
    }

    public void setValue(String str){
        value = str;
        valueA = Alphabet.convertString(str);
    }
}
