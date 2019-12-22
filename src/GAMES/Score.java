package GAMES;

import java.io.Serializable;

public class Score implements Serializable {

    private int score;
    public Score(Score s)
    {
        this.score = s.GetScore();
    }
    public Score()
    {
        score = 100;
    }
    public void SetScore(int  obj)
    {
        this.score = obj;
    }
    public int GetScore()
    {
        return score;
    }


}
