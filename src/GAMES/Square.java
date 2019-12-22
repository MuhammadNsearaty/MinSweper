package GAMES;

import java.io.Serializable;

public class Square implements Serializable {
    private int x,y;
    private Mine mine;
    private Color color;
    private SquareStatus squarestatus;
    private Shield shield = new Shield();
    public Square()
    {
        this.x= this.y = 0;
        this.mine = new Mine();
        this.squarestatus = new SquareStatus();
        color = new ConsoleColor();
    }
    public boolean IsShield()
    {
        return shield.isShield();
    }
    public void EnableShield()
    {
        shield.EnableShield();
    }
    public void DisableShield()
    {
        shield.DisableShield();
    }
    public Square(int i , int j)
    {
        this.x = i;
        this.y = j;
        this.mine = new Mine();
        this.squarestatus = new SquareStatus();
        color = new ConsoleColor();
    }
    public void SetColor(Object c)
    {
        color.SetColor(c);
    }
    public Object GetColor()
    {
        return color.GetColor();
    }
    public Boolean IsMine ()
    {
        if(mine.getmine() == "Mine")
            return true;
        return false;
    }
    public void SetMine()
    {
        mine.setmine();
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}
    public String  GetSquareStatus()
    {
        return squarestatus.GetSquareStatus();
    }

    public void SetSquareStatus(String str) {
        this.squarestatus.SetSquareStatus(str);
    }
}

