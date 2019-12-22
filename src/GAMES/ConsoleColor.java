package GAMES;

import java.io.Serializable;

public class ConsoleColor extends Color implements Serializable {

    @Override
    public void SetColor(Object c) {
        this.colorchar = c;
    }

    @Override
    public Object GetColor() {
        return (char)colorchar;
    }
}
