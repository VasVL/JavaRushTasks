package com.javarush.games.minesweeper;

public enum Tiles {
    BACKGROUND(
            "llllllll",
            "llllllll",
            "llllllll",
            "llllllll",
            "llllllll",
            "llllllll",
            "llllllll",
            "llllllll",
            "llllllll"
    ),
    BLOCK(
            "bWWWWWWb",
            "WbWWWWbB",
            "WWbbbbBB",
            "WWbbbbBB",
            "WWbbbbBB",
            "WWbbbbBB",
            "WbBBBBbB",
            "bBBBBBBb"
    ),
    BRICK(
            "bbbBbbbB",
            "bbbBbbbB",
            "BBBBBBBB",
            "bBbbbBbb",
            "bBbbbBbb",
            "BBBBBBBB",
            "bbbBbbbB",
            "bbbBbbbB"
    ),
    BRICK_PIECE_RU(
            "bbbB",
            "bbbB",
            "BBBB",
            "bBbb"),
    BRICK_PIECE_LU(
            "bbbB",
            "bbbB",
            "BBBB",
            "bBbb"),
    BRICK_PIECE_RD(
            "bBbb",
            "BBBB",
            "bbbB",
            "bbbB"),
    BRICK_PIECE_LD(
            "bBbb",
            "BBBB",
            "bbbB",
            "bbbB"),
    CASTLE_TOP(
            "bbW  WbB",
            "bbW  WbB",
            "BBBWWBBB",
            "bBbbbBbb",
            "bBbbbBbb",
            "BBBBBBBB",
            "bbbBbbbB",
            "bbbBbbbB"
    ),
    COIN(
            "   KK   ",
            "  KWRK  ",
            " KWKKRK ",
            " KWKKRK ",
            " KWKKRK ",
            " KWKKRK ",
            "  KWRK  ",
            "   KK   "
    ),
    COIN_1(
            "   K   ",
            "  KWK  ",
            " KWKRK ",
            " KWKRK ",
            " KWKRK ",
            " KWKRK ",
            "  KRK  ",
            "   K   "
    ),
    COIN_2(
            "   K  ",
            "  KKK ",
            "  KKK ",
            "  KKK ",
            "  KKK ",
            "  KKK ",
            "  KKK ",
            "   K  "
    ),
    DEFAULT(
            "RRRRRRRR",
            "RRRRRRRR",
            "RRRRRRRR",
            "RRRRRRRR",
            "RRRRRRRR",
            "RRRRRRRR",
            "RRRRRRRR",
            "RRRRRRRR"
    ),
    DOORS(
            "   BB   ",
            "  BBBB  ",
            "  BBBB  ",
            " BBBBBB ",
            " BBBBBB ",
            " BBBBBB ",
            "BBBBBBBB",
            "BBBBBBBB",
            "BBBBBBBB",
            "BBBBBBBB",
            "BBBBBBBB",
            "BBBBBBBB",
            "BBBBBBBB",
            "BBBBBBBB",
            "BBBBBBBB",
            "BBBBBBBB"
    ),
    EMPTY(
            "bbbbbbb ",
            "bBbbbBbB",
            "bbbbbbbB",
            "bbbbbbbB",
            "bbbbbbbB",
            "bBbbbBbB",
            "bbbbbbbB",
            " BBBBBBB"
    ),
    FIREBALL_BLAST_1(
            "R RR R",
            " RbbR ",
            "RbKKbR",
            "RbKKbR",
            " RbbR ",
            "R RR R"
    ),
    FIREBALL_BLAST_2(
            "R RRRR R",
            " RbbbbR ",
            "RbKbbKbR",
            "RbbKKbbR",
            "RbbKKbbR",
            "RbKbbKbR",
            " RbbbbR ",
            "R RRRR R"
    ),
    FIREBALL_CLOCKWISE(
            " RR ",
            "R RR",
            " RKR",
            " RR "
    ),
    FIREBALL_CLOCKWISE_90(turn90(FIREBALL_CLOCKWISE)),
    FIREBALL_COUNTER_CLOCKWISE(reverse(FIREBALL_CLOCKWISE)),
    FIREBALL_COUNTER_CLOCKWISE_90(turn90(FIREBALL_COUNTER_CLOCKWISE)),
    //FIREBALL_CLOCKWISE_180(rotate(FIREBALL_COUNTER_CLOCKWISE)),
    FIREBALL_CLOCKWISE_180(turn90(FIREBALL_CLOCKWISE_90)),
    FIREBALL_COUNTER_CLOCKWISE_180(turn90(FIREBALL_COUNTER_CLOCKWISE_90)),
    //FIREBALL_COUNTER_CLOCKWISE_180(rotate(FIREBALL_CLOCKWISE)),
    FIREBALL_CLOCKWISE_270(turn90(FIREBALL_CLOCKWISE_180)),
    FIREBALL_COUNTER_CLOCKWISE_270(turn90(FIREBALL_COUNTER_CLOCKWISE_180)),
    FLAG(
            "WWWWWWWW",
            "WWWWLLLW",
            " WWLWLWL",
            "  WLLWLL",
            "   WLLLW",
            "    WWWW",
            "     WWW",
            "      WW"
    ),
    FLAGPOLE(
            "GG   ",
            "GG   ",
            "GG   ",
            "GG   ",
            "GG   ",
            "GG   ",
            "GG   ",
            "GG   "
    ),
    GOOMBA(
            "  bbbb  ",
            " BBbbBB ",
            "bbBBBBbb",
            "bbWbbWbb",
            " bbbbbb ",
            "BBKKKKBB",
            "BBBKKBBB",
            " BB  BB "
    ),
    GOOMBA_DOWN(rotate(GOOMBA)),
    GOOMBA_DEAD(
            "   bb   ",
            " bbbbbb ",
            "bBBbbBBb",
            "bKbBBbKb",
            "  KKKK  "

    ),
    GOOMBA_LEFT(
            "  bbbb  ",
            " BBbbBB ",
            "bbBBBBbb",
            "bbWbbWbb",
            " bbbbbb ",
            "BBKKKK  ",
            "BBBKKBB ",
            " BB  BB "
    ),
    GOOMBA_RIGHT(
            "  bbbb  ",
            " BBbbBB ",
            "bbBBBBbb",
            "bbWbbWbb",
            " bbbbbb ",
            "  KKKKBB",
            " BBKKBBB",
            " BB  BB "
    ),
    GRAVE(
            "  ggB  ",
            " ggggB ",
            "ggBBggB",
            "gBggBgB",
            "gBggggB",
            "gBggBgB",
            "ggBBBgB",
            "ggggggB"
    ),
    GROUND(
            //"bbbbbBWb",
            "bbbbBWbB",
            "bbbbBbbB",
            "bbbbBBBB",
            "BbbBBWWB",
            "bBBBWWbB",
            "bWWBWbbB",
            "bbbBWbbB",
            "BBBWbBBb"
    ),
    INVISIBLE(
            "        ",
            "        ",
            "        ",
            "        ",
            "        ",
            "        ",
            "        ",
            "        "
    ),
    KOOPA(
            " WK     ",
            "KLKK    ",
            "KKKK LL ",
            "K K LLGL",
            " KKWLGLG",
            "  KWGLGL",
            "   WWWWW",
            " KKK  KK"
    ),
    KOOPA_LEFT_1(
            " WK     ",
            "KLKK    ",
            "KKKK LL ",
            "K K LLGL",
            " KKWLGLG",
            "  KWGLGL",
            "   WWWWW",
            " KKK KK "
    ),
    KOOPA_LEFT_2(
            " KW     ",
            "KKLK    ",
            "K KK LL ",
            "  K LLGL",
            " KKWLGLG",
            "  KWGLGL",
            "   WWWWW",
            "  KKK KK"
    ),
    KOOPA_RIGHT_1(reverse(KOOPA_LEFT_1)),
    KOOPA_RIGHT_2(reverse(KOOPA_LEFT_2)),
    KOOPA_SLEEP(
            "  WWWW  ",
            "WWLLLLWW",
            "GLGLLGLG",
            "LLLGGLLL",
            " LGLLGL ",
            "  LGGL  "
    ),
    MAGIC_MUSHROOM(
            "   KK   ",
            "  KKRK  ",
            " KKKRRK ",
            "KRRKKKKK",
            "KRRKKKRK",
            "KKWWWWKK",
            " WWWWKW ",
            "  WWWW  "
    ),
    MARIO(
            " RRR  ",
            " RRRR ",
            "bbBKKK",
            "KbKbb ",
            " KKK  ",
            "bbRRbb",
            "KRRRRK",
            " R  R ",
            "bb  bb"
    ),
    MARIO_REVERSE(reverse(MARIO)),
    MARIO_MOVE_RIGHT_1(
            " RRR  ",
            " RRRR ",
            "bbBKKK",
            "KbKbb ",
            " KKK K",
            "bbRRbb",
            "K RRR ",
            " RR Rb",
            " b    "
    ),
    MARIO_MOVE_RIGHT_2(
            " RRR  ",
            " RRRR ",
            "bbBKKK",
            "KbKbb ",
            " KKK  ",
            "bRbR  ",
            "KRbbK ",
            "bR  R ",
            "b   bb"
    ),
    MARIO_MOVE_RIGHT_3(
            " RRR  ",
            " RRRR ",
            "bbBKKK",
            "KbKbb ",
            " KKK  ",
            " bRRbK",
            " bbK  ",
            "bRRR  ",
            "b  bb "
    ),
    MARIO_GET_FLAG_UP(
            " RRR  ",
            " RRRR ",
            "bbBKKK",
            "KbKbb ",
            " KKK  ",
            " RRbbK",
            " RRR  ",
            " RRR b",
            " RRRRb"
    ),
    MARIO_GET_FLAG_DOWN(
            " RRR  ",
            " RRRR ",
            "bbBKKK",
            "KbKbb ",
            " KKK  ",
            " RRb  ",
            " RRbbK",
            " RRR b",
            " RRRRb"
    ),
    MARIO_GET_FLAG_UP_REVERSE(reverse(MARIO_GET_FLAG_UP)),
    MARIO_MOVE_LEFT_1(reverse(MARIO_MOVE_RIGHT_1)),
    MARIO_MOVE_LEFT_2(reverse(MARIO_MOVE_RIGHT_2)),
    MARIO_MOVE_LEFT_3(reverse(MARIO_MOVE_RIGHT_3)),
    POMMEL(
            "   BB  ",
            "  BLLB ",
            " BLWLLB",
            " BLLLLB",
            "  BLLB ",
            "   BB  "
    ),
    ONE_UP_MUSHROOM(
            "   KK   ",
            "  KKLK  ",
            " KKKLLK ",
            "KLLKKKKK",
            "KLLKKKLK",
            "KKWWWWKK",
            " WWWWKW ",
            "  WWWW  "
    ),
    SECRET(
            //"bKKbbKK ",
            "bKbbbbK ",
            "KKbBBbKK",
            "KKBKKbKK",
            "KKKKbBKK",
            "KKKbBKKK",
            "KKKBKKKK",
            "KKKbKKKK",
            " KKBKKKB"
    ),
    STAR(
            "   KK   ",
            "   KK   ",
            "  KKKK  ",
            "KKRKKRKK",
            " KRKKRK ",
            "  KKKK  ",
            " KKKKKK ",
            "KKK  KKK"
    ),
    STAR_1(
            "   bb   ",
            "   bb   ",
            "  bbbb  ",
            "bbBbbBbb",
            " bBbbBb ",
            "  bbbb  ",
            " bbbbbb ",
            "bbb  bbb"
    ),
    STAR_2(
            "   RR   ",
            "   RR   ",
            "  RRRR  ",
            "RRWRRWRR",
            " RWRRWR ",
            "  RRRR  ",
            " RRRRRR ",
            "RRR  RRR"
    ),
    STAR_3(
            "   KK   ",
            "   KK   ",
            "  KKKK  ",
            "KKLKKLKK",
            " KLKKLK ",
            "  KKKK  ",
            " KKKKKK ",
            "KKK  KKK"
    ),
    SUNFLOWER(
            " WWWWWW ",
            "WWKKKKWW",
            "WKRRRRKW",
            "WWKKKKWW",
            " WWWWWW ",
            "L  LL  L",
            "LL LL LL",
            " LLLLLL "
    ),
    SUNFLOWER_1(
            " WWWWWW ",
            "WWbbbbWW",
            "WbBBBBbW",
            "WWbbbbWW",
            " WWWWWW ",
            "L  LL  L",
            "LL LL LL",
            " LLLLLL "
    ),
    SUNFLOWER_2(
            " bbbbbb ",
            "bbLLLLbb",
            "bLRRRRLb",
            "bbLLLLbb",
            " bbbbbb ",
            "L  LL  L",
            "LL LL LL",
            " LLLLLL "
    ),
    SUNFLOWER_3(
            " WWWWWW ",
            "WWbbbbWW",
            "WbLLLLbW",
            "WWbbbbWW",
            " WWWWWW ",
            "L  LL  L",
            "LL LL LL",
            " LLLLLL "
    ),
    SUPER_MARIO(
            " RRRR   ",
            "RRRRRRR ",
            "bbbKbK  ",
            "bKbKbKKK",
            "bKKKKbKK",
            "bbKKbbb ",
            " KKKKK  ",
            " bRbbb  ",
            "bbRbbRb ",
            "bbRRRRbb",
            "KRKRRKRK",
            "KKRRRRKK",
            "KRRRRRRK",
            " RR  RR ",
            " bb  bb ",
            "bbb  bbb"
    ),
    SUPER_MARIO_REVERSE(reverse(SUPER_MARIO)),
    SUPER_MARIO_MOVE_RIGHT_1(
            " RRRR   ",
            "RRRRRRR ",
            "bbbKbK  ",
            "bKbKbKKK",
            "bKKKKbKK",
            "bbKKbbb ",
            "  KKKK K",
            "  Rbb KK",
            " bbRbRbK",
            "bbRRRR  ",
            "K KRRK  ",
            "KKRRRR b",
            "KRRRRRbb",
            " RR  Rbb",
            " bb     ",
            " bbb    "
    ),
    SUPER_MARIO_MOVE_RIGHT_2(
            " RRRR   ",
            "RRRRRRR ",
            "bbbKbK  ",
            "bKbKbKKK",
            "bKKKKbKK",
            "bbKKbbb ",
            "  KKKK  ",
            "  bbRR  ",
            " KbbbR  ",
            "KKRbbbKK",
            " RRRbbKK",
            " RRRRR  ",
            "  RRRRR ",
            "bbRR RR ",
            "bbRR bb ",
            "b    bbb"
    ),
    SUPER_MARIO_MOVE_RIGHT_3(
            " RRRR   ",
            "RRRRRRR ",
            "bbbKbK  ",
            "bKbKbKKK",
            "bKKKKbKK",
            "bbKKbbb ",
            "  KKKK  ",
            "  bRRR  ",
            "  bbRR  ",
            " RbbKK  ",
            " RRbKK  ",
            "  RRRRR ",
            " RRRRRR ",
            " RR bb  ",
            " bb bbb ",
            " bbb    "
    ),
    SUPER_MARIO_GET_FLAG_UP(
            " RRRR   ",
            "RRRRRRR ",
            "bbbKbK  ",
            "bKbKbKKK",
            "bKKKKbKK",
            "bbKKbbb ",
            " KKKKK  ",
            " RRRR   ",
            " RRbbbKK",
            " RRbbbKK",
            " RRRR   ",
            " RRKR  b",
            " RRRRRbb",
            "  RRRRbb"
    ),
    SUPER_MARIO_GET_FLAG_DOWN(
            " RRRR   ",
            "RRRRRRR ",
            "bbbKbK  ",
            "bKbKbKKK",
            "bKKKKbKK",
            "bbKKbbb ",
            " KKKKK  ",
            " RRRR   ",
            " RRbb   ",
            " RRbbbKK",
            " RRRbbKK",
            " RRKR  b",
            " RRRRRbb",
            "  RRRRbb"
    ),
    SUPER_MARIO_GET_FLAG_UP_REVERSE(reverse(SUPER_MARIO_GET_FLAG_UP)),
    SUPER_MARIO_MOVE_LEFT_1(reverse(SUPER_MARIO_MOVE_RIGHT_1)),
    SUPER_MARIO_MOVE_LEFT_2(reverse(SUPER_MARIO_MOVE_RIGHT_2)),
    SUPER_MARIO_MOVE_LEFT_3(reverse(SUPER_MARIO_MOVE_RIGHT_3)),
    TUBE_L(
            " GGLGGGL",
            " GGLGGGL",
            " GGLGGGL",
            " GGLGGGL",
            " GGLGGGL",
            " GGLGGGL",
            " GGLGGGL",
            " GGLGGGL"
    ),
    TUBE_R(
            "GLLLGLG ",
            "GLLGLGG ",
            "GLLLGLG ",
            "GLLGLGG ",
            "GLLLGLG ",
            "GLLGLGG ",
            "GLLLGLG ",
            "GLLGLGG "
    ),
    TUBE_TOP_L(
            "BGGGGGGG",
            "BLLGGGLL",
            "BGLGGGLG",
            "BGLGGGLG",
            "BGLGGGLG",
            "BGLGGGLG",
            "BGLGGGLG",
            "BBBBBBBB"
    ),
    TUBE_TOP_R(
            "GGGGGGGB",
            "LLLLLLLB",
            "LLLLLGLB",
            "LLLLGLGB",
            "LLLLLGLB",
            "LLLLGLGB",
            "LLLLLGLB",
            "BBBBBBBB"
    );



    public final String[] tile;

    Tiles(String ... tile){
        this.tile = tile;
    }

    private static String[] reverse(Tiles tile){
        return reverse(tile.tile);//strArr;
    }
    private static String[] reverse(String ... strings){
        String[] strArr = new String[strings.length];
        for(int i = 0; i < strArr.length; i++){
            strArr[i] = new StringBuffer(strings[i]).reverse().toString();
        }
        return strArr;
    }

    private static String[] rotate(Tiles tile){

        return rotate(tile.tile);
    }
    private static String[] rotate(String ... strings){
        String[] strArr = new String[strings.length];
        String strBuff;
        for(int i = 0; i < strArr.length / 2; i++){
            strBuff = strings[i];
            strArr[i] = strings[strArr.length - i - 1];
            strArr[strArr.length - i - 1] = strBuff;
        }
        return strArr;
    }

    private static String[] turn90(Tiles tile) {

        return turn90(tile.tile);
    }

    private static String[] turn90(String ... strings) {
        String[] strArr = new String[strings.length];

        for(int y = 0; y < strings.length; y++){
            char[] chArrBuff = new char[strings[y].length()];
            for(int x = 0; x < strings[y].length(); x++){
                chArrBuff[x] = strings[strings.length - 1 - x].charAt(y);
            }
            strArr[y] = new String(chArrBuff);
        }

        return strArr;
    }
}