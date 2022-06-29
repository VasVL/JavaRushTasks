package com.javarush.games.minesweeper.objects;

public class CheckedCollision {

    public GameObject gameObject;
    public Side side;

    public CheckedCollision(GameObject gameObject, Side side)
    {
        this.gameObject = gameObject;
        this.side = side;
    }

    public enum Side
    {
        RIGHT,
        LEFT,
        UP,
        DOWN
    }
}