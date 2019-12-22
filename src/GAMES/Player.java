package GAMES;

import java.io.Serializable;

public abstract class Player implements Serializable {

    protected   String name;      ////////////here I put protected instead private
    protected Color color;
    protected Score currscore;
    protected int SheildsCount;

    public int getPermenShieldCount() {
        return permenShieldCount;
    }

    public void setPermenShieldCount(int permenShieldCount) {
        this.permenShieldCount = permenShieldCount;
    }

    protected int permenShieldCount;

    public Player()
    {
        permenShieldCount  = 0;
        SheildsCount = 0;
        name = "PC";
        currscore = new Score();// وضع السكور الابتدائي للاعب
    }
    public Player(Player p)
    {
        this.currscore = p.currscore;
        this.name = p.name;
        this.color = p.color;
    }

    public int getShieldsCount() {
        return SheildsCount;
    }
    public void setSheildsCount(int sheildsCount){this.SheildsCount = sheildsCount;}
    public void IncShieldCount()
    {
        this.SheildsCount++;
    }
    public void DecShieldCount()
    {
        this.SheildsCount--;
    }
    public abstract void GetPlayerInfo(Object obj);
    public Player getPlayer()
    {
        return this;
    }
    public void setPlayer(Player player)
    {
        this.SetName(player.getName());
        this.SetColor(player.GetColor());
        this.SetScore(player.GetScore());
    }
    public void SetScore(Score score)
    {
        this.currscore = score;
    }
    public void AddScore(int n)
    {
        int m = this.GetScore().GetScore()+ n ;
        this.currscore.SetScore(m);
    }
    public Score GetScore()
    {
        return currscore;
    }
    public void SetColor(Color color) {this.color = color; }
    public Color GetColor()
    {
        return color;
    }
    public void SetName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }
    public abstract PlayerMove GetPlayerMove(Object obj);
}

