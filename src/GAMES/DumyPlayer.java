package GAMES;

import Gui2.GuiColor;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.Random;


public class DumyPlayer extends ComPlayer implements Serializable {

    private Random rand = new Random();

    /*public Object GetPlayerScore()
    {
        return super.GetScore().GetScore();
    }*/
    public DumyPlayer()
    {
        super();
        color = new GuiColor();
    }
    public DumyPlayer(Player p)
    {
        this.currscore = p.currscore;
        this.color = p.color;
        this.name = p.name;
    }
    public Color GetGuiColor()
    {
     return (Color)color.GetColor();
    }

    @Override
    public void GetPlayerInfo(Object obj) {

    }

    @Override
    public PlayerMove GetPlayerMove(Object obj) {
        PlayerMove move = new PlayerMove();
        int rownum[] = new int [26];  // n is the number of the row grid
        char colnum[] = new char [26];  //  m is the number of the col grid
        for(int i=0; i<26;i++)
            rownum[i]=i;
        for(char i='a';i<='z';i++)
            colnum[(int)i-97]=i;
        int c = rand.nextInt(25)  ;
        int r = rand.nextInt(25) ;
        int x = rownum[c];
        int y =  ((int)colnum[r] - 97);
        move.setSquare(x,y);
        MoveType mv = new MoveType();
        int Type[] = new int[5];
        Type[0] = 0;
        Type[1] = 1;
        Type[2] = 1;
        Type[3] = 1;
        Type[4] = 1;
        int type = Type[rand.nextInt(4)];
        if(type == 1)
           mv.SetNewType("Reveal");
        else
            mv.SetNewType("Mark");
        move.setType(mv);
        move.setPlayer(this);
        return move;
    }

}