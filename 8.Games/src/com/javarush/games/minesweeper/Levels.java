package com.javarush.games.minesweeper;


public class Levels {

    public static String[] world1_1 = {
            "                                                                               P                                                                                                                           ",
            "                                                                                                                                                                                                           ",
            "                                                                                                                                                                                                  p        ",
            "                                                                                                                                                                                                  F        ",
            "                                                                           G G                                                                                                                    f        ",
            "                 c                                                         bbbbbbbb   bbbc              s           bbb    bccb                                                         BB        f        ",
            "                                                                                                                                                                                       BBB        f        ",
            "                                                                                                                                                                                      BBBB        f        ",
            "                                                           o                                                                                                                         BBBBB        f    CCC ",
            "           c   bmbcb                     LR         LR                  bsb              t     bS    c  c  c     b          bb      B  B          BB  B            bbcb             BBBBBB        f    b b ",
            "                                 LR      lr         lr                                                                             BB  BB        BBB  BB                           BBBBBBB        f   CCCCC",
            "                       LR        lr      lr         lr                                                                            BBB  BBB      BBBB  BBB     LR               LR BBBBBBBB        e   bbbbb",
            "  M             G      lr        lr G    lr     GG  lr                                    G G      K                  G G G G    BBBB  BBBB    BBBBB  BBBB    lr         G G   lrBBBBBBBBB        B   bbDbb",
            "gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg  ggggggggggggggg   gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg  ggggggggggggggggggggggggggggggggggggggggggggggggggggg"
    };

    public static String[] testCollision = {
            "                      ",
            "                      ",
            "                      ",
            "         bbb          ",
            "                      ",
            "                      ",
            "                      ",
            "      bbb             ",
            " LR         LR        ",
            " lr         lr        ",
            " lr  M      lr        ",
            "gggggggggggggggggggggg"
    };

    public static String[] testEnemy = {
            "                      ",
            "                      ",
            "                      ",
            "                      ",
            "                      ",
            "    KKK               ",
            "    bbb               ",
            "     bGGKb            ",
            "  bb bbbbb            ",
            "                      ",
            "  K   M     K  K   G  ",
            "gggg ggggg ggggggggggg"
    };

    public static String[] testBonus = {
            "                                ",
            "                                ",
            "                                ",
            "                                ",
            "                                ",
            "                                ",
            "                                ",
            "                                ",
            "   cm  sbs  St  o               ",
            "                    LR          ",
            "                    lr       LR ",
            "    M               lr K G   lr ",
            "gg ggggggggggggggggggggggggggggg"
    };

    public static String[] testKoopa = {
            "                                ",
            "                                ",
            "                                ",
            "                                ",
            "                                ",
            "                                ",
            "                                ",
            "                                ",
            "    b  bbb  Sbb                 ",
            "                             LR ",
            "                     M       lr ",
            "              K     BB   K   lr ",
            "gggggggggggggggggggggggggggggggg"
    };
    public static String[] testAnimations = {
            "                                ",
            "                                ",
            "                                ",
            "                                ",
            "                                ",
            "                                ",
            "                                ",
            "                                ",
            "                                ",
            "                                ",
            "         M                      ",
            "    BB K BB G BB                ",
            "gggggggggggggggggggggggggggggggg"
    };

    public static String[] testWin = {
            "                                ",
            "                                ",
            "                                ",
            "                                ",
            "      p                         ",
            "      F                         ",
            "      f                         ",
            "      f    CCC                  ",
            "  mb  f    b b                  ",
            "      f   CCCCC                 ",
            "      e   bbbbb                 ",
            "  M   B   bbDbb                 ",
            "gggggggggggggggggggggggggggggggg"
    };

    //char[][] window = new char[12][11];
}
