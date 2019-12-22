package GAMES;

import java.io.Serializable;

public abstract class Color implements Serializable {

    protected Object colorchar;

    public Color()
    {
        colorchar = ' ';
    }

    public abstract void SetColor(Object c);

    public abstract Object GetColor();
}
