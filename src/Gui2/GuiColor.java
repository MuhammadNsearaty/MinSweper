package Gui2;

import GAMES.Color;

public class GuiColor extends Color {
    public GuiColor()
    {
        super();
        colorchar = javafx.scene.paint.Color.GHOSTWHITE;
    }
    public GuiColor(javafx.scene.paint.Color color)
    {
        colorchar = color;
    }
    @Override
    public void SetColor(Object c) {
        this.colorchar = (javafx.scene.paint.Color)c;
    }
    @Override
    public Object GetColor() {
        return (javafx.scene.paint.Color)colorchar;
    }
}
