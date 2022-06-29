package com.javarush.games.minesweeper;


public enum Alphabet {
    A(" WW",
            "W W",
            "W W",
            "WWW",
            "W W"),
    B("WW ",
            "W W",
            "WW ",
            "W W",
            "WW "),
    C(" WW",
            "W  ",
            "W  ",
            "W  ",
            " WW"),
    D("WW ",
            "W W",
            "W W",
            "W W",
            "WW "),
    E("WWW",
            "W  ",
            "WW ",
            "W  ",
            "WWW"),
    F("WWW",
            "W  ",
            "WW ",
            "W  ",
            "W  "),
    G(" WW",
            "W  ",
            "W  ",
            "W W",
            " WW"),
    H("W w",
            "W W",
            "WWW",
            "W W",
            "W W"),
    I("WWW",
            " W ",
            " W ",
            " W ",
            "WWW"),
    J("WWW",
            "  W",
            "  W",
            "W W",
            " W "),
    K("W W",
            "W W",
            "WW ",
            "W W",
            "W W"),
    L("W  ",
            "W  ",
            "W  ",
            "W  ",
            "WWW"),
    M("W   W",
            "WW WW",
            "W W W",
            "W   W",
            "W   W"),
    N("W  W",
            "W  W",
            "WW W",
            "W WW",
            "W  W"),
    O("WWW",
            "W W",
            "W W",
            "W W",
            "WWW"),
    P("WW ",
            "W W",
            "WWW",
            "W  ",
            "W  "),
    Q(" WW ",
            "W  W",
            "W  W",
            "W W ",
            " WWW"),
    R("WW ",
            "W W",
            "WW ",
            "W W",
            "W W"),
    S(" WW",
            "W  ",
            " W ",
            "  W",
            "WW "),
    T("WWW",
            " W ",
            " W ",
            " W ",
            " W "),
    U("W W",
            "W W",
            "W W",
            "W W",
            " W "),
    V("W  W",
            "W  W",
            "W  W",
            "W W ",
            " W  "),
    W("W   W",
            "W   W",
            "W   W",
            "W W W",
            " W W "),
    X("W W",
            "W W",
            " W ",
            "W W",
            "W W"),
    Y("W W",
            "W W",
            " W ",
            " W ",
            " W "),
    Z("WWW",
            "  W",
            " W ",
            "W  ",
            "WWW"),
    ZERO(" W ",
            "W W",
            "W W",
            "W W",
            " W "),
    ONE(" W ",
            "WW ",
            " W ",
            " W ",
            "WWW"),
    TWO("WW ",
            "  W",
            " W ",
            "W  ",
            "WWW"),
    THREE("WWW",
            "  W",
            " W ",
            "  W",
            "WWW"),
    FOUR("W W",
            "W W",
            "WWW",
            "  W",
            "  W"),
    FIVE("WWW",
            "W  ",
            "WWW",
            "  W",
            "WWW"),
    SIX("WWW",
            "W  ",
            "WWW",
            "W W",
            "WWW"),
    SEVEN("WWW",
            "  W",
            " W ",
            "W  ",
            "W  "),
    EIGHT("WWW",
            "W W",
            " W ",
            "W W",
            "WWW"),
    NINE("WWW",
            "W W",
            "WWW",
            "  W",
            "WWW"),
    DUSH("   ",
            "   ",
            "WWW",
            "   ",
            "   "),
    MULTIPLE("   ",
            "W W",
            " W ",
            "W W",
            "   "),
    SPACE(
            "   ",
            "   ",
            "   ",
            "   ",
            "   "
    );


    public final String[] value;

    Alphabet(String ... letter){
        this.value = letter;
    }

    public static Alphabet[] convertString(String string){
        Alphabet[] aSrting = new Alphabet[string.length()];
        string = string.toLowerCase();

        for(int i = 0; i < string.length(); i++){
            switch(string.charAt(i)){
                case 'a':
                    aSrting[i] = A;
                    break;
                case 'b':
                    aSrting[i] = B;
                    break;
                case 'c':
                    aSrting[i] = C;
                    break;
                case 'd':
                    aSrting[i] = D;
                    break;
                case 'e':
                    aSrting[i] = E;
                    break;
                case 'f':
                    aSrting[i] = F;
                    break;
                case 'g':
                    aSrting[i] = G;
                    break;
                case 'h':
                    aSrting[i] = H;
                    break;
                case 'i':
                    aSrting[i] = I;
                    break;
                case 'j':
                    aSrting[i] = J;
                    break;
                case 'k':
                    aSrting[i] = K;
                    break;
                case 'l':
                    aSrting[i] = L;
                    break;
                case 'm':
                    aSrting[i] = M;
                    break;
                case 'n':
                    aSrting[i] = N;
                    break;
                case 'o':
                    aSrting[i] = O;
                    break;
                case 'p':
                    aSrting[i] = P;
                    break;
                case 'q':
                    aSrting[i] = Q;
                    break;
                case 'r':
                    aSrting[i] = R;
                    break;
                case 's':
                    aSrting[i] = S;
                    break;
                case 't':
                    aSrting[i] = T;
                    break;
                case 'u':
                    aSrting[i] = U;
                    break;
                case 'v':
                    aSrting[i] = V;
                    break;
                case 'w':
                    aSrting[i] = W;
                    break;
                case 'x':
                    aSrting[i] = X;
                    break;
                case 'y':
                    aSrting[i] = Y;
                    break;
                case 'z':
                    aSrting[i] = Z;
                    break;
                case '0':
                    aSrting[i] = ZERO;
                    break;
                case '1':
                    aSrting[i] = ONE;
                    break;
                case '2':
                    aSrting[i] = TWO;
                    break;
                case '3':
                    aSrting[i] = THREE;
                    break;
                case '4':
                    aSrting[i] = FOUR;
                    break;
                case '5':
                    aSrting[i] = FIVE;
                    break;
                case '6':
                    aSrting[i] = SIX;
                    break;
                case '7':
                    aSrting[i] = SEVEN;
                    break;
                case '8':
                    aSrting[i] = EIGHT;
                    break;
                case '9':
                    aSrting[i] = NINE;
                    break;
                case '-':
                    aSrting[i] = DUSH;
                    break;
                case '*':
                    aSrting[i] = MULTIPLE;
                    break;
                case ' ':
                    aSrting[i] = SPACE;
                    break;
            }
        }

        return aSrting;
    }
}
