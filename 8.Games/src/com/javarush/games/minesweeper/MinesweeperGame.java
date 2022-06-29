package com.javarush.games.minesweeper;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.objects.*;
import com.javarush.games.minesweeper.objects.immoveableObjects.*;


import java.util.ArrayList;
import java.util.Iterator;

public class MinesweeperGame extends Game {

    public static final int FPS = 10;
    public static final int SEC = 1000;

    private static final String[] currentLevel    = Levels.world1_1;

    private static final int MAX_SIDE           = 100;
    private static final int TILE_SIZE          = 8;
    private static final int SIDE_X             = MAX_SIDE;
    private static final int SIDE_Y             = SIDE_X * 3 / 4;
    //private static final int HALF_SIDE_X        = SIDE_X / 2;
    private static final int THIRD_SIDE_X       = SIDE_X / 3;
    private static final int HALF_SIDE_Y        = SIDE_Y / 2;
    private static final int HALF_TILE_SIZE     = TILE_SIZE / 2;
    //private static final int HALF_WINDOW_X      = HALF_SIDE_X - HALF_TILE_SIZE;
    private static final int THIRD_WINDOW_X     = THIRD_SIDE_X - HALF_TILE_SIZE;
    private static final int HALF_WINDOW_Y      = HALF_SIDE_Y - HALF_TILE_SIZE;
    //private static final int SIDE_X_IN_TILES    = SIDE_X / TILE_SIZE;
    //private static final int SIDE_Y_IN_TILES    = SIDE_Y / TILE_SIZE;
    //private static final int EXTRA_PIXELS_X     = SIDE_X % TILE_SIZE;
    //private static final int EXTRA_PIXELS_Y     = SIDE_Y % TILE_SIZE;

    //private static final int LEVEL_SIZE_X_IN_TILES      = currentLevel[0].length();
    private static final int LEVEL_SIZE_Y_IN_TILES      = currentLevel.length;
    //private static final int LEVEL_SIZE_X_IN_PIXELS     = LEVEL_SIZE_X_IN_TILES * TILE_SIZE;
    private static final int LEVEL_SIZE_Y_IN_PIXELS     = LEVEL_SIZE_Y_IN_TILES * TILE_SIZE;

    private static final int MAX_Y_OFFSET               = LEVEL_SIZE_Y_IN_PIXELS - SIDE_Y;

    private static final int MARIO_RAN_SPEED        = 3;
    private static final int MARIO_JUMP_SPEED       = 13;
    private static final int MAX_OBJECT_FALL_SPEED  = 7;
    private static final int FALL_BOOST             = 3;



    private static boolean isGameStopped    = false;
    private static boolean isInMenu         = false;
    private static boolean isGameWin        = false;
    private static boolean isMarioInAir     = false;

    private boolean isMarioDown = false; // для функции опускания флага

    private static int xOffset = 0;
    private static int yOffset = MAX_Y_OFFSET;

    //int marioSpeedX = 0;
    private static int marioSpeedY       = MAX_OBJECT_FALL_SPEED;
    private static final int marioStartX = findMarioPositionInLevel().x;
    private static final int marioStartY = findMarioPositionInLevel().y;

    private static int checkPoint = marioStartX;

    private static ArrayList<GameObject> allObjects     = new ArrayList<>();
    private static ArrayList<GameObject> newObjects     = new ArrayList<>();
    private static ArrayList<GameObject> deletedObjects = new ArrayList<>();

    private static ArrayList<GameString> allStrings     = new ArrayList<>();

    private Mario mario = new Mario(marioStartX, marioStartY);


    private static int score1 = 0;
    private static int coins = 0;
    private static int lives = 3;


    private static final int mSecPassLevelTime          = 400_000;
    private static final int mSecFireballCreationPeriod = 400;
    private static final int mSecShowLivesTime          = 2_500;
    private static int timeLeft                         = mSecPassLevelTime;

    private static long startLevelTime;
    private static long previousFireballTime = System.currentTimeMillis();
    private static long startShowLives;

    private static boolean wasSpaceReleased = true;
    private static boolean wasEnterReleased = true;
    private static boolean wasRightReleased = true;
    private static boolean wasLeftReleased = true;


    private static final Color BACKGROUND_COLOR = Color.CORNFLOWERBLUE;

    GameObject testObject = new GameObject(0,0);

    @Override
    public void initialize()
    {
        //  Тут выполняем все действия по инициализации игры и ее объектов
        setScreenSize(SIDE_X, SIDE_Y);
        showGrid(false);

        isInMenu = true;

        createGame();
        startShowLives = System.currentTimeMillis();
    }

    private static Point findMarioPositionInLevel()
    {
        Point marioStartPoint = new Point();
        for(int y = 0; y < currentLevel.length; y++)
        {
            for(int x = 0; x < currentLevel[y].length(); x++)
            {
                if(currentLevel[y].charAt(x) == 'M')
                {
                    marioStartPoint.setPosition(x * TILE_SIZE, y * TILE_SIZE - 1);
                }
            }
        }
        return marioStartPoint;
    }

    @Override
    public void onTurn(int step)
    {
        if(isInMenu){
            showLives();
        }
        else {
            clearWindowOfObjects(allObjects);
            clearWindowOfStrings(allStrings);
            clearTopWindow();
            clearTile((int)mario.x - xOffset, mario.y - yOffset, mario.tile);

            if (!isGameStopped) {
                mario.playAnimation(SEC / FPS);

                mario.setY(mario.y + marioSpeedY);

                // Марио упал
                if (mario.y > LEVEL_SIZE_Y_IN_PIXELS) {
                    goToCheckpoint();
                }

                // Это не помню зачем, видимо для того, чтоб марио за верхний край не выходил
                // Почему тогда просто с нулём не сравнивать?
                // А ясно, это просто проверка скопированная с икса
                if (mario.y < yOffset) {
                    mario.setY(yOffset);
                }

                checkMarioVerticalCollision();

                checkEnemyCollision();

                mario.setX(mario.x + mario.speedX);

                if (mario.x < xOffset) {
                    mario.setX(xOffset);
                }

                checkMarioHorizontalCollision();

                changeOffsets();


                if (mario.isGodMode && timeSince(mario.startGodModeTime) > mario.mSecGodModeTime) {
                    mario.stopBeingAGod();
                }

                if (!mario.isTouchable && timeSince(mario.startUntouchable) > mario.mSecUntouchable) {
                    mario.stopBeingUntouchable();
                }

                timeLeft -= timeSince(startLevelTime);
                if (timeLeft <= 0) {
                    goToCheckpoint();
                }
                startLevelTime = System.currentTimeMillis();
            }
            else {
                if (isGameWin) {

                    if (Flag.isFlagUp) {
                        flagMoveDown();
                    } else {
                        if (timeLeft > 0) {
                            score1 += 50;
                            timeLeft -= 1000;
                        }
                    }

                    if (mario.y - HALF_WINDOW_Y <= MAX_Y_OFFSET && mario.y - HALF_WINDOW_Y >= 0) {
                        yOffset = mario.y - HALF_WINDOW_Y;
                    }
                }
            }

            if (deletedObjects != null) {
                allObjects.removeAll(deletedObjects);
                deletedObjects.clear();
            }
            if (newObjects != null) {
                allObjects.addAll(newObjects);
                newObjects.clear();
            }



            drawMario();

            drawObjects(allObjects);

            drawTopLine();

            drawStrings();
        }
    }

    private void changeOffsets(){
        if (mario.x - xOffset > THIRD_WINDOW_X) {
            xOffset = (int)mario.x - THIRD_WINDOW_X;
        }

        if(mario.y - HALF_WINDOW_Y >= 0) {
            yOffset = Math.min(mario.y - HALF_WINDOW_Y, MAX_Y_OFFSET);
        }
    }


    private void addScore(int num, GameObject gameObject){
        score1 += num;
        allStrings.add(new GameString(String.valueOf(num), (int)gameObject.x, gameObject.y - 5));
    }

    private void destroyBrick(GameObject brick){
        deletedObjects.add(brick);

        DeadGameObject RU = new DeadGameObject(brick.x, brick.y, Tiles.BRICK_PIECE_RU, -2, -8);

        DeadGameObject LU = new DeadGameObject(brick.x + 4, brick.y, Tiles.BRICK_PIECE_LU, 2, -8);

        DeadGameObject RD = new DeadGameObject(brick.x, brick.y + 4, Tiles.BRICK_PIECE_RD, -2, -5);

        DeadGameObject LD = new DeadGameObject(brick.x + 4, brick.y + 4, Tiles.BRICK_PIECE_LD, 2, -5);

        newObjects.add(RU);
        newObjects.add(LU);
        newObjects.add(RD);
        newObjects.add(LD);
    }

    private void checkMarioVerticalCollision()
    {
        CheckedCollision checkedCollision = verticalCollision(mario);

        if(checkedCollision != null)
        {
            GameObject verticalObject = checkedCollision.gameObject;
            if(checkedCollision.side == CheckedCollision.Side.DOWN)
            {
                checkMarioDownCollision(verticalObject);
            }
            else
            if(checkedCollision.side == CheckedCollision.Side.UP)
            {
                checkMarioUpCollision(verticalObject);
            }
        }
        else
        if(marioSpeedY < MAX_OBJECT_FALL_SPEED)
        {
            marioSpeedY += FALL_BOOST;
            isMarioInAir = true;
        }
        else{
            marioSpeedY = MAX_OBJECT_FALL_SPEED;
            isMarioInAir = true;
        }
    }

    private void checkMarioDownCollision(GameObject verticalObject)
    {
        if(verticalObject instanceof Enemy)
        {
            addScore(100, verticalObject);
            if(mario.isGodMode){
                if(mario.direction == Mario.Direction.RIGHT) {
                    allObjects.add(new DeadGameObject(verticalObject, 2, -7));
                }
                else {
                    allObjects.add(new DeadGameObject(verticalObject, -2, -7));
                }
                addScore(100, verticalObject);
                deleteGameObject(verticalObject);
                return;
            }

            marioSpeedY = -7;

            if(verticalObject.getClass().equals(Koopa.class)){
                if(((Koopa) verticalObject).isSleeping){
                    if(mario.x <= verticalObject.x) {
                        verticalObject.speedX = 10;
                    }
                    else{
                        verticalObject.speedX = -10;
                    }
                }
                else {
                    ((Koopa) verticalObject).fallAsleep();
                    mario.setY(verticalObject.y - mario.tileSizeY);
                }
                return;
            }
            if(verticalObject.getClass().equals(Goomba.class)){
                mario.setY(verticalObject.y - mario.tileSizeY);
                addGameObject(new DeadGameObject(verticalObject, Tiles.GOOMBA_DEAD));
                deleteGameObject(verticalObject);
                return;
            }

            return;
        }

        if(verticalObject.getClass().equals(BonusObject.class))
        {
            marioMeetBonus(verticalObject);
            return;
        }

        if (isMarioInAir) {

            mario.setMarioTile(Tiles.MARIO);

            if(mario.speedX != 0){
                mario.isAnimationOn = true;
            }
        }

        if(verticalObject.getClass().equals(Flag.class) || verticalObject.getClass().equals(Flagpole.class)){
            win();
        }
        mario.setY(verticalObject.y - mario.tileSizeY);
        marioSpeedY = FALL_BOOST;
        isMarioInAir = false;
    }

    private void checkMarioUpCollision(GameObject verticalObject)
    {
        if(verticalObject instanceof Enemy)
        {
            marioMeetEnemy(verticalObject);
            return;
        }

        if (!verticalObject.isPostMarioUpCollision
                && verticalObject.tile != Tiles.EMPTY
                && verticalObject.tile != Tiles.FLAG) {
            verticalObject.postMarioUpCollisionOffset();
        }

        if(verticalObject instanceof Secret && verticalObject.tile != Tiles.EMPTY)
        {
            marioMeetSecret(verticalObject);
        }
        else
        if(mario.isSuperMario && verticalObject.getClass().equals(Brick.class))
        {
            destroyBrick(verticalObject);
        }
        else
        if(verticalObject.getClass().equals(ToughBrick.class))
        {
            if(((ToughBrick)verticalObject).coinsCount > 0)
            {
                ((ToughBrick)verticalObject).coinsCount--;
                addScore(200, verticalObject);
            }
            else
            {
                verticalObject.setTile(Tiles.EMPTY);
            }
        }
        else
        if(verticalObject.getClass().equals(BonusObject.class))
        {
            marioMeetBonus(verticalObject);
            return;
        }
        else if(verticalObject.getClass().equals(Flag.class) || verticalObject.getClass().equals(Flagpole.class)){
            win();
        }

        mario.setY(verticalObject.yEnd + 1);
        marioSpeedY = FALL_BOOST;
    }

    private void checkMarioHorizontalCollision()
    {
        CheckedCollision checkedCollision = sideCollision(mario);

        if(checkedCollision != null)
        {
            GameObject sideObject = checkedCollision.gameObject;

            if(sideObject instanceof Enemy)
            {
                marioMeetEnemy(sideObject);
                return;
            }

            if(sideObject.getClass().equals(BonusObject.class))
            {
                marioMeetBonus(sideObject);
            }
            else if(sideObject.getClass().equals(Flag.class) || sideObject.getClass().equals(Flagpole.class)){
                correctPostCollisionPosition(mario, checkedCollision);
                win();
            }
            else
            {
                correctPostCollisionPosition(mario, checkedCollision);
            }
        }
    }

    private void marioMeetEnemy(GameObject gameObject){
        if(!mario.isTouchable)
        {
            mario.setY(gameObject.y - (mario.tileSizeY - gameObject.tileSizeY));
        }
        else
        if(mario.isGodMode)
        {
            if(mario.direction == Mario.Direction.RIGHT) {
                allObjects.add(new DeadGameObject(gameObject, 2, -7));
            }
            else {
                allObjects.add(new DeadGameObject(gameObject, -2, -7));
            }
            addScore(100, gameObject);
            deleteGameObject(gameObject);
        }
        else
        if(gameObject.getClass().equals(Koopa.class) && ((Koopa) gameObject).isSleeping) {
            if (mario.direction == Mario.Direction.RIGHT) {
                gameObject.speedX = 10;
            } else {
                gameObject.speedX = -10;
            }
        }
        else
        if(mario.isSuperMario)
        {
            mario.becomingUntouchable();
        }
        else
        {
            goToCheckpoint();
        }
    }

    private void marioMeetSecret(GameObject gameObject){
        gameObject.tile = Tiles.EMPTY;
        GameObject bonusObject = new GameObject(gameObject.x, gameObject.y);

        if(gameObject.getClass().equals(CoinSecret.class))
        {
            coins++;
            addScore(100, gameObject);
            bonusObject = new BonusObject(gameObject.x, gameObject.y - TILE_SIZE, BonusObject.Bonus.COIN);
        }
        else
        if(gameObject.getClass().equals(MagicMushroomSecret.class))
        {
            bonusObject = new BonusObject(gameObject.x, gameObject.y, BonusObject.Bonus.SUPER_MARIO);
        }
        else
        if(gameObject.getClass().equals(One_UpMushroomSecret.class))
        {
            bonusObject = new BonusObject(gameObject.x, gameObject.y, BonusObject.Bonus.EXTRA_LIFE);
        }
        else
        if(gameObject.getClass().equals(SunflowerSecret.class))
        {
            if(mario.isSuperMario)
            {
                bonusObject = new BonusObject(gameObject.x, gameObject.y, BonusObject.Bonus.FIRE);
            }
            else
            {
                bonusObject = new BonusObject(gameObject.x, gameObject.y, BonusObject.Bonus.SUPER_MARIO);
            }
        }
        else
        if(gameObject.getClass().equals(StarSecret.class))
        {
            bonusObject = new BonusObject(gameObject.x, gameObject.y, BonusObject.Bonus.GOD_MODE);
        }
        allObjects.add(0, bonusObject);
    }

    private void marioMeetBonus(GameObject gameObject){
        switch (((BonusObject) gameObject).bonus) {
            case SUPER_MARIO:
                mario.becomingSuperMario();
                addScore(1000, gameObject);
                break;
            case EXTRA_LIFE:
                addScore(1000, gameObject);
                lives++;
                break;
            case FIRE:
                addScore(1000, gameObject);
                mario.becomingAFireman();
                break;
            case GOD_MODE:
                addScore(1000, gameObject);
                mario.becomingAGod();
                break;
            default:
                show("Неизвестный бонус");
                break;
        }
        deleteGameObject(gameObject);
    }

    private void correctPostCollisionPosition(GameObject activeObject, CheckedCollision checkedCollision)
    {
        if(checkedCollision.side == CheckedCollision.Side.RIGHT)
        {
            activeObject.setX(checkedCollision.gameObject.x - activeObject.tileSizeX);
        }
        else
        {
            activeObject.setX(checkedCollision.gameObject.x + checkedCollision.gameObject.tileSizeX);
        }
    }

    private void checkEnemyCollision()
    {
        for(GameObject gameObject : allObjects)
        {
            if(gameObject instanceof Movable)
            {
                if(mario.x + SIDE_X >= gameObject.x) {
                    if (gameObject.tile == Tiles.GOOMBA_DEAD) {
                        if (((DeadGameObject) gameObject).checkDeath()) {
                            deleteGameObject(gameObject);
                        }
                        continue;
                    }

                    if((gameObject.animation == Animations.COIN && gameObject.tileNumber == Animations.COIN.animationInTiles.length)
                    || (gameObject.animation == Animations.FIREBALL_BLAST && gameObject.tileNumber == Animations.FIREBALL_BLAST.animationInTiles.length)){
                        deleteGameObject(gameObject);
                        continue;
                    }

                    if (gameObject.getClass().equals(Koopa.class)) {
                        ((Koopa) gameObject).checkSleeping();
                    }

                    if(gameObject.getClass().equals(BonusObject.class) && ((BonusObject)gameObject).growth < gameObject.tileSizeY){
                        gameObject.setY(gameObject.y - BonusObject.growthSpeed);
                        ((BonusObject) gameObject).growth += BonusObject.growthSpeed;
                        continue;
                    }

                    gameObject.setY(gameObject.y + gameObject.speedY);

                    if (gameObject.y >= LEVEL_SIZE_Y_IN_PIXELS) {
                        deleteGameObject(gameObject);
                        continue;
                    }

                    if (gameObject.getClass().equals(DeadGameObject.class)) {
                        setEnemyFallSpeed(gameObject);
                    }
                    else {
                        CheckedCollision checkedVerticalCollision = verticalCollision(gameObject);


                        if (checkedVerticalCollision == null) {
                            setEnemyFallSpeed(gameObject);
                        } else {
                            GameObject verticalObject = checkedVerticalCollision.gameObject;

                            if (checkedVerticalCollision.side == CheckedCollision.Side.DOWN) {
                                checkEnemyDownCollision(gameObject, verticalObject);
                            }
                            else
                            if (checkedVerticalCollision.side == CheckedCollision.Side.UP) {
                                checkEnemyUpCollision(gameObject, verticalObject);
                            }
                        }
                    }

                    gameObject.setX(gameObject.x + gameObject.speedX);

                    if(gameObject.x < xOffset - gameObject.tileSizeX)
                    {
                        deleteGameObject(gameObject);
                        continue;
                    }

                    if (!gameObject.getClass().equals(DeadGameObject.class)) {
                        CheckedCollision checkedCollision = sideCollision(gameObject);
                        // Здесь был ебаный баг с CheckedCollision.gameObject != null AAA-AAA!!!
                        if (checkedCollision != null)// || isEdge(gameObject)){
                        {
                            if (gameObject.getClass().equals(Fireball.class)) {
                                if(checkedCollision.gameObject instanceof Enemy) {
                                    fireballEnemyCollision(gameObject, checkedCollision.gameObject);
                                }
                                else{
                                    newObjects.add(new DeadGameObject(gameObject));
                                    deleteGameObject(gameObject);
                                }
                                continue;
                            }

                            if (gameObject instanceof Enemy) {
                                if (gameObject.getClass().equals(Koopa.class)) {
                                    if (((Koopa) gameObject).isSleeping) {
                                        sleepingKoopaCollision(gameObject, checkedCollision.gameObject);
                                        continue;
                                    }

                                    if (gameObject.animation == Animations.KOOPA_MOVE_LEFT) {
                                        gameObject.setAnimation(Animations.KOOPA_MOVE_RIGHT);
                                    } else {
                                        gameObject.setAnimation(Animations.KOOPA_MOVE_LEFT);
                                    }
                                }

                                if (checkedCollision.gameObject.getClass().equals(Fireball.class)) {
                                    if (checkedCollision.gameObject.speedX > 0) {
                                        newObjects.add(new DeadGameObject(gameObject, 2, -7));
                                    } else {
                                        newObjects.add(new DeadGameObject(gameObject, -2, -7));
                                    }
                                    newObjects.add(new DeadGameObject(checkedCollision.gameObject));
                                    deleteGameObject(gameObject);
                                    deleteGameObject(checkedCollision.gameObject);
                                    continue;
                                }
                            }
                            correctPostCollisionPosition(gameObject, checkedCollision);
                            gameObject.speedX = -(gameObject.speedX);
                        }
                    }
                }
                else if (gameObject.getClass().equals(Fireball.class)) {
                    deleteGameObject(gameObject);
                }
            }

            if(gameObject.animation != null) {
                    gameObject.playAnimation();
            }

            if(gameObject.isPostMarioUpCollision
                    && timeSince(gameObject.startPostMarioUpCollisionTime) > GameObject.mSecPostMarioUpCollisionTime){
                gameObject.endPostMarioUpCollisionOffset();
            }
        }
    }

    private void fireballEnemyCollision(GameObject fireball, GameObject gameObject){

        if (fireball.speedX > 0) {
            newObjects.add(new DeadGameObject(gameObject, 2, -7));
        } else {
            newObjects.add(new DeadGameObject(gameObject, -2, -7));
        }
        newObjects.add(new DeadGameObject(fireball));
        deleteGameObject(fireball);
        deleteGameObject(gameObject);
        addScore(100, gameObject);
    }

    private void sleepingKoopaCollision(GameObject koopa, GameObject gameObject){
        if (gameObject instanceof Movable) {
            if (gameObject.speedX > 0) {
                newObjects.add(new DeadGameObject(gameObject, 2, -7));
            } else {
                newObjects.add(new DeadGameObject(gameObject, -2, -7));
            }
            addScore(100, gameObject);
            deleteGameObject(gameObject);

        } else {
            if (koopa.speedX > 0) {
                newObjects.add(new DeadGameObject(koopa, 2, -7));
            } else {
                newObjects.add(new DeadGameObject(koopa, -2, -7));
            }
            deleteGameObject(koopa);
        }
    }

    private void checkEnemyDownCollision(GameObject gameObject,
                                         GameObject verticalObject)
    {
        gameObject.speedY = FALL_BOOST;

        if(gameObject.getClass().equals(Fireball.class))
        {
            if(verticalObject instanceof Enemy)
            {
                fireballEnemyCollision(gameObject, verticalObject);
                return;
            }
            else
            {
                gameObject.speedY = -4;
            }
        }

        if(gameObject instanceof Enemy)
        {

            if(verticalObject.getClass().equals(Fireball.class))
            {
                if(verticalObject.speedX > 0) {
                    newObjects.add(new DeadGameObject(gameObject, 2, -7));
                }
                else {
                    newObjects.add(new DeadGameObject(gameObject, -2, -7));
                }
                newObjects.add(new DeadGameObject(verticalObject));
                deleteGameObject(gameObject);
                deleteGameObject(verticalObject);
                return;
            }

            if(verticalObject.getClass().equals(Koopa.class)
                    && ((Koopa)verticalObject).isSleeping){
                deleteGameObject(gameObject);
                return;
            }

            if(verticalObject.isPostMarioUpCollision){
                if(mario.x < gameObject.x) {
                    newObjects.add(new DeadGameObject(gameObject, 2, -7));
                }
                else {
                    newObjects.add(new DeadGameObject(gameObject, -2, -7));
                }
                deletedObjects.add(gameObject);
                return;
            }
        }
        else
        if(gameObject.getClass().equals(BonusObject.class))
        {
            if(((BonusObject)gameObject).bonus == BonusObject.Bonus.GOD_MODE)
            {
                gameObject.speedY = -7;
            }
        }

        gameObject.setY(verticalObject.y - gameObject.tileSizeY);
    }

    private void checkEnemyUpCollision(GameObject gameObject,
                                       GameObject verticalObject)
    {
        if(gameObject.getClass().equals(Fireball.class))
        {
            if(verticalObject instanceof Enemy)
            {
                fireballEnemyCollision(gameObject, verticalObject);
                return;
            }
        }

        if(gameObject instanceof Enemy)
        {
            if(verticalObject.getClass().equals(Fireball.class))
            {
                if(verticalObject.speedX > 0) {
                    newObjects.add(new DeadGameObject(gameObject, 2, -7));
                }
                else {
                    newObjects.add(new DeadGameObject(gameObject, -2, -7));
                }
                newObjects.add(new DeadGameObject(verticalObject));
                deleteGameObject(gameObject);
                deleteGameObject(verticalObject);
                return;
            }

            if(verticalObject.getClass().equals(Koopa.class)
                    && ((Koopa)verticalObject).isSleeping){
                if(verticalObject.speedX > 0) {
                    newObjects.add(new DeadGameObject(gameObject, 2, -7));
                }
                else {
                    newObjects.add(new DeadGameObject(gameObject, -2, -7));
                }
                deleteGameObject(gameObject);
                addScore(100, gameObject);
                return;
            }
        }

        gameObject.setY(verticalObject.yEnd + 1);
    }

    private void setEnemyFallSpeed(GameObject gameObject)
    {
        if(gameObject.getClass().equals(Fireball.class))
        {
            if(gameObject.speedY < MAX_OBJECT_FALL_SPEED/2)
            {
                gameObject.speedY += 1;
            }
        }
        else if(gameObject.getClass().equals(DeadGameObject.class) && gameObject.animation == Animations.FIREBALL_BLAST){
            return;
        }
        else if(gameObject.speedY < MAX_OBJECT_FALL_SPEED)
        {
            gameObject.speedY += FALL_BOOST;
        }
        else{
            gameObject.speedY = MAX_OBJECT_FALL_SPEED;
        }
    }
    private void deleteGameObject(GameObject gameObject)
    {
        deletedObjects.add(gameObject);
        gameObject.isTouchable = false;
    }

    private void addGameObject(GameObject gameObject){
        allObjects.add(gameObject);
    }

    /*private boolean isEdge(GameObject object)
    {

        if(object.x < 0)
        {
            object.setX(0);
            return true;
        }
        else
        if(object.x > LEVEL_SIZE_X_IN_PIXELS)
        {
            object.setX(LEVEL_SIZE_X_IN_PIXELS - TILE_SIZE);
            return true;
        }

        int edgeX = object.x / TILE_SIZE;
        int edgeY = object.y / TILE_SIZE;

        if(currentLevel[edgeY + 1].charAt(edgeX + 1) == ' ')
        {
            object.setX(edgeX * TILE_SIZE);
            return true;
        }
        if(currentLevel[edgeY + 1].charAt(edgeX) == ' ')
        {
            object.setX(edgeX * TILE_SIZE + TILE_SIZE);
            return true;
        }
        return false;
    }*/

    private CheckedCollision verticalCollision(GameObject currentObject)
    {
        GameObject anotherUp;
        for(int i = 0; i < allObjects.size(); i++)
        {
            GameObject gameObject = allObjects.get(i);
            if(currentObject.equals(gameObject))
            {
                continue;
            }
            if(gameObject.isTouchable) {
                int twoTileSizes = currentObject.tileSizeX + gameObject.tileSizeX;

                if (currentObject.xEnd - gameObject.x < twoTileSizes
                        && gameObject.xEnd - currentObject.x < twoTileSizes) {
                    if (currentObject.yEnd > gameObject.y
                            && currentObject.y < gameObject.y) {
                        return new CheckedCollision(allObjects.get(i), CheckedCollision.Side.DOWN);
                    }

                    if (currentObject.y < gameObject.yEnd
                            && currentObject.yEnd > gameObject.yEnd) {
                        anotherUp = checkBesideUpObjects(currentObject, i);
                        if(anotherUp != null) {
                            if (Math.abs(anotherUp.x - currentObject.x) >= Math.abs(gameObject.x - currentObject.x)) {
                                return new CheckedCollision(allObjects.get(i), CheckedCollision.Side.UP);
                            } else {
                                return new CheckedCollision(anotherUp, CheckedCollision.Side.UP);
                            }
                        }
                        return new CheckedCollision(allObjects.get(i), CheckedCollision.Side.UP);
                    }
                }
            }
        }
        return null;
    }


    private GameObject checkBesideUpObjects(GameObject currentObject, int i){
        if (i > 0
                && currentObject.x < allObjects.get(i - 1).xEnd
                && currentObject.y < allObjects.get(i - 1).yEnd
                && currentObject.yEnd > allObjects.get(i - 1).yEnd) {
            return allObjects.get(i - 1);
        }
        if (i < allObjects.size()
                && currentObject.xEnd > allObjects.get(i + 1).x
                && currentObject.y < allObjects.get(i + 1).yEnd
                && currentObject.yEnd > allObjects.get(i + 1).yEnd) {
            return allObjects.get(i + 1);
        }
        return null;
    }

    private CheckedCollision sideCollision(GameObject currentObject)
    {
        for(GameObject gameObject : allObjects)
        {
            if(currentObject.equals(gameObject))
            {
                continue;
            }
            if(gameObject.isTouchable) {
                int twoTileSizes = currentObject.tileSizeY + gameObject.tileSizeY;
                if (currentObject.yEnd - gameObject.y < twoTileSizes
                        && gameObject.yEnd - currentObject.y < twoTileSizes) //continue;
                {
                    if (currentObject.xEnd > gameObject.x
                            && currentObject.x < gameObject.x) {
                        return new CheckedCollision(gameObject, CheckedCollision.Side.RIGHT);
                    }
                    if (currentObject.xEnd > gameObject.xEnd
                            && currentObject.x < gameObject.xEnd) {
                        return new CheckedCollision(gameObject, CheckedCollision.Side.LEFT);
                    }
                }
            }
        }
        return null;
    }


    private void createFlag(){
        for (GameObject gameObject : allObjects) {
            if (gameObject.tile == Tiles.FLAG) {

                gameObject.setTile(Tiles.FLAGPOLE);
                // Я не знаю почему здесь x-1 и x + 5. Должно быть просто x и x + 6.
                // Когда я это писал так и было, но потом я сделал что-то другое,
                // и теперь нужно использовать x-1 и x + 5.
                newObjects.add(new Flag(gameObject.x - 1, gameObject.y));
                gameObject.setX(gameObject.x + 5);
                Flag.isFlagUp = true;
                Flag.flagDownSpeed = 1;
                isMarioDown = false;
                break;
            }
        }
    }


    private void flagMoveDown(){
        GameObject gameObject = allObjects.get(allObjects.size() - 1);

        CheckedCollision checkedCollision = verticalCollision(gameObject);
        if (checkedCollision == null) {
            gameObject.setY(gameObject.y + Flag.flagDownSpeed);
        } else if (checkedCollision.gameObject.getClass().equals(Flagpole.class)) {
            gameObject.setY(gameObject.y + Flag.flagDownSpeed);
        } else {
            mario.isAnimationOn = false;
            mario.setX(mario.x + mario.tileSizeX + 2);
            if(mario.isSuperMario){
                mario.setTile(Tiles.SUPER_MARIO_GET_FLAG_UP_REVERSE);
            }
            else {
                mario.setTile(Tiles.MARIO_GET_FLAG_UP_REVERSE);
            }
            Flag.isFlagUp = false;
        }

        if(!isMarioDown) {
            if (verticalCollision(mario) == null) {
                mario.setY(mario.y + Flag.flagDownSpeed);
            } else {
                mario.setY(mario.y - Flag.flagDownSpeed);
                isMarioDown = true;
            }
        }
        mario.playAnimation();
    }


    @Override
    public void onMouseLeftClick(int x, int y)
    {
        restart();
    }

    @Override
    public void onKeyPress(Key key)
    {
        if(key == Key.ENTER && wasEnterReleased){
            wasEnterReleased = false;
            if(isGameStopped){
                isGameStopped = false;
                clearString(allStrings.get(allStrings.size()-1));
                allStrings.remove(allStrings.size()-1);
                startLevelTime = System.currentTimeMillis();
            }
            else{
                isGameStopped = true;
                allStrings.add(new GameString("Pause", SIDE_X/2 - "pause".length()*2 + xOffset, SIDE_Y/2 - 3 + yOffset, false));
            }
        }

        if(!isGameStopped)
        {
            if(key == Key.RIGHT && wasRightReleased)
            {
                wasRightReleased = false;
                mario.direction = Mario.Direction.RIGHT;
                mario.setSpeedX(MARIO_RAN_SPEED);
                mario.setAnimation(Animations.MARIO_MOVE_RIGHT);
                if(!isMarioInAir) {

                    mario.isAnimationOn = true;
                }
                else{
                    mario.setMarioTile(Tiles.MARIO_MOVE_RIGHT_1);
                }
            }
            if(key == Key.LEFT && wasLeftReleased)
            {
                wasLeftReleased = false;
                mario.direction = Mario.Direction.LEFT;
                mario.setSpeedX(- MARIO_RAN_SPEED);
                mario.setAnimation(Animations.MARIO_MOVE_LEFT);
                if(!isMarioInAir) {

                    mario.isAnimationOn = true;
                }
                else{
                    mario.setMarioTile(Tiles.MARIO_MOVE_LEFT_1);
                }
            }

            if((key == Key.UP) && !isMarioInAir)
            {
                marioSpeedY = -(MARIO_JUMP_SPEED);

                isMarioInAir = true;
                mario.isAnimationOn = false;
                mario.setMarioTile(Tiles.MARIO_MOVE_RIGHT_1);
            }

            if(mario.isFirePower && key == Key.SPACE)
            {
                if(timeSince(previousFireballTime) > mSecFireballCreationPeriod && wasSpaceReleased)
                {
                    wasSpaceReleased = false;
                    GameObject fireball;
                    if (mario.direction == Mario.Direction.RIGHT) {
                        fireball = new Fireball(mario.xEnd, mario.y, mario.direction);
                    } else {
                        fireball = new Fireball(mario.x - Tiles.FIREBALL_CLOCKWISE.tile[0].length(), mario.y, mario.direction);
                    }
                    if (sideCollision(fireball) == null) {
                        addGameObject(fireball);
                    }
                    previousFireballTime = System.currentTimeMillis();
                }
            }
        }
    }

    @Override
    public void onKeyReleased(Key key)
    {
        if(key == Key.ENTER){
            wasEnterReleased = true;
        }

        if(!isGameStopped)
        {
            if(key == Key.RIGHT)
            {
                wasRightReleased = true;
                mario.setSpeedX(0);
                mario.isAnimationOn = false;
                if(!isMarioInAir) {
                    mario.setMarioTile(Tiles.MARIO);
                }
                mario.tileNumber = 0;
            }
            if(key == Key.LEFT)
            {
                wasLeftReleased = true;
                mario.setSpeedX(0);
                mario.isAnimationOn = false;
                if(!isMarioInAir) {
                    mario.setMarioTile(Tiles.MARIO);
                }
                mario.tileNumber = 0;
            }

            if(key == Key.SPACE)
            {
                wasSpaceReleased = true;
            }
        }
    }

    private void createGame()
    {
        createObjects();
        clearWindow(BACKGROUND_COLOR);

        startLevelTime = System.currentTimeMillis();
        setTurnTimer(SEC/FPS);
    }

    private void win()
    {
        isGameStopped = true;
        isGameWin = true;
        isMarioInAir = false;
        mario.setAnimation(Animations.MARIO_GET_FLAG);
        mario.isAnimationOn = true;
        score1 += 5000;
        createFlag();
    }

    private void goToCheckpoint(){
        isInMenu = true;

        timeLeft = 400_000;

        lives--;

        xOffset = 0;
        yOffset = MAX_Y_OFFSET;

        marioSpeedY = MAX_OBJECT_FALL_SPEED;

        if(mario.x >= checkPoint) {
            mario = new Mario(checkPoint, marioStartY);
        }
        else{
            mario = new Mario(marioStartX, marioStartY);
        }

        startShowLives = System.currentTimeMillis();
    }

    private void showLives(){
        if(timeSince(startShowLives) < mSecShowLivesTime){
            clearWindow(Color.BLACK);
            drawTopLine();
            if(lives > 0){
                drawTile(SIDE_X/2 - 8, SIDE_Y/2 - 5, Tiles.MARIO);
                drawTile(SIDE_X/2, SIDE_Y/2 - 2, Alphabet.MULTIPLE.value);
                drawString(String.valueOf(lives), SIDE_X/2+5, SIDE_Y/2 - 2);
            }
            else{
                drawString("Game Over", SIDE_X/2 - 18, SIDE_Y/2 - 2);
            }

            //showLives();
        }
        else if(lives > 0){
            createGame();
            isInMenu = false;
        }
        else{
            restart();
            isInMenu = false;
        }
    }
    private void restart()
    {
        clearWindowOfStrings(allStrings);
        allStrings.clear();

        score1 = 0;
        coins = 0;
        lives = 3;

        isGameWin = false;


        timeLeft = 400_000;

        xOffset = 0;
        yOffset = MAX_Y_OFFSET;
        mario = new Mario(marioStartX, marioStartY);
        marioSpeedY = MAX_OBJECT_FALL_SPEED;

        isGameStopped = false;
        createGame();
    }

    /*private void gameOver(String str)
    {
        isGameStopped = true;
        stopTurnTimer();
        show(str);
    }*/

    private void createObjects()
    {
        //allObjects.removeAll(allObjects);
        allObjects = new ArrayList<>();

        int xPosition;
        int yPosition;
        GameObject gameObject;

        for(int y = 0; y < currentLevel.length; y++)
        {
            char[] arrX = currentLevel[y].toCharArray();

            for(int x = 0; x < arrX.length; x++)
            {
                xPosition = x * TILE_SIZE;
                yPosition = y * TILE_SIZE;

                switch (arrX[x])
                {
                    // Здесь интересный баг с неисчезающей купой
                    case ' ':
                    case 'M':
                        continue;
                    case 'b':
                        gameObject = new Brick(xPosition, yPosition);
                        break;
                    case 't':
                        gameObject = new ToughBrick(xPosition, yPosition);
                        break;
                    case 'c':
                        gameObject = new CoinSecret(xPosition, yPosition);
                        break;
                    case 'S':
                        gameObject = new StarSecret(xPosition, yPosition);
                        break;
                    case 's':
                        gameObject = new SunflowerSecret(xPosition, yPosition);
                        break;
                    case 'o':
                        gameObject = new One_UpMushroomSecret(xPosition, yPosition);
                        break;
                    case 'm':
                        gameObject = new MagicMushroomSecret(xPosition, yPosition);
                        break;
                    case 'L':
                        gameObject = new TubeTopL(xPosition, yPosition);
                        break;
                    case 'R':
                        gameObject = new TubeTopR(xPosition, yPosition);
                        break;
                    case 'l':
                        gameObject = new GameObject(xPosition, yPosition, Tiles.TUBE_L);
                        break;
                    case 'r':
                        gameObject = new GameObject(xPosition, yPosition, Tiles.TUBE_R);
                        break;
                    case 'B':
                        gameObject = new GameObject(xPosition, yPosition, Tiles.BLOCK);
                        break;
                    case 'F':
                        gameObject = new Flagpole(xPosition - 3, yPosition, Tiles.FLAG);
                        break;
                    case 'f':
                        gameObject = new Flagpole(xPosition + 3, yPosition);
                        break;
                    case 'g':
                        gameObject = new GameObject(xPosition, yPosition, Tiles.GROUND);
                        break;
                    case 'G':
                        gameObject = new Goomba(xPosition, yPosition);
                        break;
                    case 'K':
                        gameObject = new Koopa(xPosition, yPosition);
                        break;
                    case 'C':
                        gameObject = new GameObject(xPosition, yPosition, Tiles.CASTLE_TOP);
                        break;
                    case 'D':
                        gameObject = new GameObject(xPosition, yPosition - TILE_SIZE, Tiles.DOORS);
                        break;
                    case 'p':
                        allObjects.add(new GameObject(xPosition, yPosition + 2, Tiles.POMMEL));
                        continue;
                    case 'e':
                        gameObject = new GameObject(xPosition + 3, yPosition, Tiles.FLAGPOLE);
                        break;
                    case 'P':
                        checkPoint = xPosition;
                        continue;
                    default:
                        gameObject = new UnknownObject(xPosition, yPosition);
                }

                if(touchable(x, y))
                {
                    gameObject.isTouchable = true;
                }
                allObjects.add(gameObject);
            }
        }
    }

    private boolean touchable(int xPosition, int yPosition)
    {
        if(currentLevel[yPosition].charAt(xPosition) == ' ')
        {
            return false;
        }

        if(yPosition > 0)
        {
            if(isUpEmpty(xPosition, yPosition))
                return true;
        }
        if(xPosition > 0)
        {
            if(isLeftEmpty(xPosition, yPosition))
                return true;
        }
        if(xPosition < currentLevel[0].length() - 1)
        {
            if(isRightEmpty(xPosition, yPosition))
                return true;
        }
        if(yPosition < currentLevel.length - 1)
        {
            if(isDownEmpty(xPosition, yPosition))
                return true;
        }

        return false;
    }

    private boolean isDownEmpty(int xPosition, int yPosition)
    {
        return currentLevel[yPosition + 1].charAt(xPosition) == ' ' ||
                currentLevel[yPosition + 1].charAt(xPosition) == 'G' ||
                currentLevel[yPosition + 1].charAt(xPosition) == 'K' ||
                currentLevel[yPosition + 1].charAt(xPosition) == 'M';
    }
    private boolean isUpEmpty(int xPosition, int yPosition)
    {
        return currentLevel[yPosition - 1].charAt(xPosition) == ' ' ||
                currentLevel[yPosition - 1].charAt(xPosition) == 'G' ||
                currentLevel[yPosition - 1].charAt(xPosition) == 'K' ||
                currentLevel[yPosition - 1].charAt(xPosition) == 'P' ||
                currentLevel[yPosition - 1].charAt(xPosition) == 'M';
    }
    private boolean isLeftEmpty(int xPosition, int yPosition)
    {
        return currentLevel[yPosition].charAt(xPosition - 1) == ' ' ||
                currentLevel[yPosition].charAt(xPosition - 1) == 'G' ||
                currentLevel[yPosition].charAt(xPosition - 1) == 'K' ||
                currentLevel[yPosition].charAt(xPosition - 1) == 'M';
    }
    private boolean isRightEmpty(int xPosition, int yPosition)
    {
        return currentLevel[yPosition].charAt(xPosition + 1) == ' ' ||
                currentLevel[yPosition].charAt(xPosition + 1) == 'G' ||
                currentLevel[yPosition].charAt(xPosition + 1) == 'K' ||
                currentLevel[yPosition].charAt(xPosition + 1) == 'M';
    }

    private void drawTile(int xPosition, int yPosition, Tiles tile)
    {
        String[] image = tile.tile;
        drawTile(xPosition, yPosition, image);
    }
    private void drawTile(int xPosition, int yPosition, String ... image)
    {
        for(int y = 0; y < image.length; y++)
        {
            char[] arr = image[y].toCharArray();
            for(int x = 0; x < arr.length; x++)
            {
                int xCoord = x + xPosition;
                int yCoord = y + yPosition;

                if(xCoord >= 0 && xCoord < SIDE_X && yCoord >=0 && yCoord < SIDE_Y)
                {
                    switch (arr[x]){
                        case ' ':
                            //setCellColor(x, y, Color.TRANSPARENT);
                            break;
                        case 'l':
                            setCellColor(xCoord, yCoord, Color.CORNFLOWERBLUE);
                            break;
                        case 'R':
                            setCellColor(xCoord, yCoord, Color.RED);
                            break;
                        case 'K':
                            setCellColor(xCoord, yCoord, Color.SANDYBROWN);
                            break;
                        case 'b':
                            setCellColor(xCoord, yCoord, Color.CHOCOLATE);
                            break;
                        case 'B':
                            setCellColor(xCoord, yCoord, Color.BLACK);
                            break;
                        case 'W':
                            setCellColor(xCoord, yCoord, Color.WHITE);
                            break;
                        case 'G':
                            setCellColor(xCoord, yCoord, Color.GREENYELLOW);
                            break;
                        case 'L':
                            setCellColor(xCoord, yCoord, Color.LIMEGREEN);
                            break;
                        case 'g':
                            setCellColor(xCoord, yCoord, Color.GRAY);
                            break;
                        case 't':
                            setCellColor(xCoord, yCoord, Color.TOMATO);
                            break;
                        case 'k':
                            setCellColor(xCoord, yCoord, Color.KHAKI);
                            break;
                        default:
                            setCellColor(xCoord, yCoord, Color.MAGENTA);
                    }
                }
            }
        }
    }

    private String[] blinking(GameObject gameObject)
    {
        String[] image = gameObject.tile.tile;
        String[] newImage = new String[image.length];
        for(int y = 0; y < image.length; y++)
        {
            switch(gameObject.blinkingNumber) {
                case 1:
                    newImage[y] = image[y].replace("K", "W").replace("b", "K");
                    break;
                case 2:
                    newImage[y] = image[y].replace("R", "B").replace("K", "W");
                    break;
                case 3:
                    newImage[y] = image[y].replace("R", "W").replace("b", "R");
                    break;
                case 4:
                    newImage[y] = image[y].replace("R", "L").replace("K", "W").replace("b", "K");
                    break;
            }
        }
        if(timeSince(gameObject.previousBlinkingTime) > gameObject.mSecBlinkingSpeed) {
            if(gameObject.blinkingNumber < 4) {
                gameObject.blinkingNumber++;

            }
            else {
                gameObject.blinkingNumber = 1;
            }
            gameObject.previousBlinkingTime = System.currentTimeMillis();
        }
        return newImage;
    }

    private String[] setSkin(GameObject gameObject, int skinNumber)
    {
        String[] image = gameObject.tile.tile;
        String[] newImage = new String[image.length];
        for(int y = 0; y < image.length; y++)
        {
            switch(skinNumber) {
                case 1:
                    newImage[y] = image[y].replace("K", "W").replace("b", "K");
                    break;
                case 2:
                    newImage[y] = image[y].replace("R", "B").replace("K", "W");
                    break;
                case 3:
                    newImage[y] = image[y].replace("R", "W").replace("b", "R");
                    break;
                case 4:
                    newImage[y] = image[y].replace("R", "L").replace("K", "W").replace("b", "K");
                    break;
                case 5:
                    newImage[y] = image[y].replace("R", "t").replace("K", "k").replace("b", "K").replace("B", "g");
                    break;
            }
        }
        return newImage;
    }

    private void drawMario(){
        int xPosition = (int)mario.x - xOffset;
        int yPosition = mario.y - yOffset;

        if (mario.isBlinking) {
            drawTile(xPosition, yPosition, blinking(mario));
            if (timeSince(mario.startBlinkingTime) > mario.mSecBlinkingTime) {
                mario.stopBlinking();
            }
        } else if (mario.isFirePower) {
            drawTile(xPosition, yPosition, setSkin(mario, 3));
        } else if (!mario.isTouchable) {
            drawTile(xPosition, yPosition, setSkin(mario, 5));
        } else {
            drawTile(xPosition, yPosition, mario.tile);
        }
    }

    private void drawObjects(ArrayList<GameObject> gameObjects)
    {
        for(GameObject gameObject : gameObjects)
        {
            if(gameObject.x <= xOffset + SIDE_X)
            {
                drawTile((int)gameObject.x - xOffset, gameObject.y - yOffset, gameObject.tile);
            }
        }
    }

    private void drawTopLine(){
        drawString("Mario", 2, 1);
        drawString(String.valueOf(score1), 2, 7);

        drawTile(29, 4, Tiles.COIN);
        drawString("*" + String.valueOf(coins), 37, 7);

        drawString("World", 53, 1);
        drawString("1-1", 58, 7);

        drawString("Time", SIDE_X - 19, 1);
        drawString(String.valueOf(timeLeft / SEC), SIDE_X - ((int)Math.log10(timeLeft / SEC) + 1) * 4 - 1, 7);
    }

    private void drawStrings(){
        Iterator<GameString> iter = allStrings.iterator();
        while(iter.hasNext()){
            GameString gameString = iter.next();
            if(gameString.isMove) {
                if (timeSince(gameString.creationTime) < gameString.mSecLifeTime) {
                    gameString.moveUp();
                    drawString(gameString);
                } else {
                    iter.remove();
                }
            }
            else {
                drawString(gameString);
            }
        }
    }
    private void drawString(String string, int x, int y){
        Alphabet[] aString = Alphabet.convertString(string);
        int xOff = 0;
        for (Alphabet letter : aString) {
            drawTile(x + xOff, y, letter.value);
            xOff += 1 + letter.value[0].length();
        }
    }

    private void drawString(GameString gameString){
        int xOff = 0;
        for (Alphabet letter : gameString.valueA) {
            drawTile(gameString.x + xOff - xOffset, gameString.y - yOffset, letter.value);
            xOff += 1 + letter.value[0].length();
        }
    }

    private void clearString(GameString gameString){
        int xOff = 0;
        for (Alphabet letter : gameString.valueA) {
            clearTile(gameString.x + xOff - xOffset, gameString.y - yOffset, letter.value);
            xOff += 1 + letter.value[0].length();
        }
    }

    private void clearWindowOfStrings(ArrayList<GameString> gameStrings)
    {
        for (GameString gameString : gameStrings) {
            clearString(gameString);
        }
    }


    /*
    private void drawLevel(String[] level)
    {
        String[] visibleLevelY = Arrays.copyOfRange(level , yOffset / TILE_SIZE, yOffset / TILE_SIZE + SIDE_Y_IN_TILES + 1);

        int xOffsetInTiles = xOffset / TILE_SIZE;

        int extraTile = 1;
        if(xOffset % TILE_SIZE + EXTRA_PIXELS_X < TILE_SIZE + HALF_TILE_SIZE)
            extraTile = 2;

        for(int y = 0; y < visibleLevelY.length; y++)
        {
            char[] visibleLevelX = visibleLevelY[y].
                substring(xOffsetInTiles , SIDE_X_IN_TILES + xOffsetInTiles + extraTile).
                toCharArray();

            for(int x = 0; x < visibleLevelX.length; x++)
            {
                int xPosition = x * TILE_SIZE - xOffset % TILE_SIZE;
                int yPosition = y * TILE_SIZE - yOffset % TILE_SIZE;

                switch (visibleLevelX[x])
                {
                    case ' ':
                    case 'M':
                    case 'G':
                    case 'K':
                        //draw(Sky);
                        break;
                    case 'g':
                        drawTile(Tiles.GROUND, xPosition, yPosition);
                        break;
                    case 'B':
                        drawTile(Tiles.BLOCK, xPosition, yPosition);
                        break;
                    case 'b':
                    case 's':
                        drawTile(Tiles.BRICK, xPosition, yPosition);
                        break;
                    case '?':
                        drawTile(Tiles.SECRET, xPosition, yPosition);
                        break;
                    case 'L':
                        drawTile(Tiles.TUBE_TOP_L, xPosition, yPosition);
                        break;
                    case 'R':
                        drawTile(Tiles.TUBE_TOP_R, xPosition, yPosition);
                        break;
                    case 'l':
                        drawTile(Tiles.TUBE_L, xPosition, yPosition);
                        break;
                    case 'r':
                        drawTile(Tiles.TUBE_R, xPosition, yPosition);
                        break;
                    case 'F':
                        //drawTile(Tiles.FLAG, xPosition - 3, yPosition);
                        //break;
                    case 'f':
                        drawTile(Tiles.FLAGPOLE, xPosition, yPosition);
                        break;
                    case 'C':
                        drawTile(Tiles.CASTLE_TOP, xPosition, yPosition);
                        break;
                    case 'D':
                        drawTile(Tiles.DOORS, xPosition, yPosition - TILE_SIZE);
                        break;
                    //case 'M':
                        //drawTile(Tiles.MARIO, xPosition, yPosition);
                        //break;
                    //case 'G':
                    //    drawTile(Tiles.GOOMBA, xPosition, yPosition);
                    //    break;
                    //case 'K':
                    //    drawTile(Tiles.KOOPA, xPosition, yPosition);
                    //    break;
                    default:
                        drawTile(Tiles.DEFAULT, xPosition, yPosition);
                }
            }
        }
    }
    */

    private void clearTile(int xPosition, int yPosition, String[] image){
        for(int y = 0; y < image.length; y++)
        {
            char[] arr = image[y].toCharArray();
            for(int x = 0; x < arr.length; x++)
            {
                int xCoord = x + xPosition;
                int yCoord = y + yPosition;

                if(xCoord >= 0 && xCoord < SIDE_X && yCoord >= 0 && yCoord < SIDE_Y)
                {
                    setCellColor(xCoord, yCoord, BACKGROUND_COLOR);
                }
            }
        }
    }
    private void clearTile(int xPosition, int yPosition, Tiles tile)
    {
        String[] image = tile.tile;
        clearTile(xPosition, yPosition, image);
    }

    private void clearWindowOfObjects(ArrayList<GameObject> gameObjects)
    {
        for(GameObject gameObject : gameObjects)
        {
            if(gameObject.x <= xOffset + SIDE_X)
            {
                clearTile((int)gameObject.x - xOffset, gameObject.y - yOffset, gameObject.tile);
            }
        }
    }

    private void clearWindow(Color color)
    {
        for(int y = 0; y < SIDE_Y; y++)
        {
            for(int x = 0; x < SIDE_X; x++)
            {
                setCellColor(x, y, color);
            }
        }
    }

    private void clearTopWindow()
    {
        for(int y = Alphabet.A.value.length + 1; y < Alphabet.A.value.length * 2 + 2; y++)
        {
            for(int x = 0; x < SIDE_X; x++)
            {
                setCellColor(x, y, BACKGROUND_COLOR);
            }
        }
    }

    void show(String str)
    {
        showMessageDialog(Color.WHITE, str, Color.BLACK, 20);
    }

    int timeSince(long start){

        return (int)(System.currentTimeMillis() - start);
    }
}