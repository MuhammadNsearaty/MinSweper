package GAMES;

import java.io.Serializable;

public class MoveResult implements Serializable {
    private int newscore ;
    private  SquareStatus newstatus;
    private boolean FoundShield = false;
    private boolean LostShield = false;
    public MoveResult()
    {
        newscore = 0;
        newstatus = new SquareStatus();
        FoundShield = FoundShield;
        LostShield = LostShield;
    }

    public int getNewscore() {
        return newscore;
    }

    public void setNewscore(int newscore) {
        this.newscore = newscore;
    }

    public SquareStatus getNewstatus() {
        return newstatus;
    }

    public void setNewstatus(String st) {
        this.newstatus.SetSquareStatus(st);
    }
    public void isFoundShield() {
         FoundShield = true;
    }
    public void isLostShield()
    {
        LostShield = true;
    }
    public boolean getFoundShield()
    {
        return FoundShield;
    }
    public boolean getLostShield()
    {
        return LostShield;
    }

}
