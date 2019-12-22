package GAMES;

import java.io.Serializable;
import java.util.Scanner;

public class ConsoleGame extends  NormalGame implements Serializable {
    //private final int MoveMatrixX[] = {0,-1,1,1,-1,0,1,-1};
    //private final int MoveMatrixY[] = {-1,-1,-1,0,0,1,1,1};
    public ConsoleGame()
    {
        super();
    }

    public void PlayerLoses(Player[] p,int i)
    {
        System.out.println( p[i].getName() + " pressed a Mine\n Good Luck Next Time");
        System.out.println("The Finale Results :\n");
        for(int j = 0 ; j<p.length;j++)
            System.out.println(p[j].getName()+ " : "+p[j].GetScore().GetScore());
    }
    public void GameEnd()
    {
        if(PlayersList.length ==1) {
            System.out.println("You  Win :)\nYour Finale Score :" + PlayersList[0].GetScore().GetScore());
            for (int i = 0; i < grid.getN(); i++) {
                for (int j = 0; j < grid.getM(); j++) {
                    System.out.print(" | ");
                    String str = grid.Sq[i][j].GetSquareStatus();
                    if (str == "Mine")
                        System.out.print("M");
                    else if (str.charAt(str.length() - 1) >= '1' && str.charAt(str.length() - 1) <= '8')
                        System.out.print(str.charAt(str.length() - 1));
                    else if (str == "Marked")
                        System.out.print("P");
                    else
                        System.out.print(" ");
                }
                System.out.println(" |");
            }
        }
        else
        {
            if(PlayersList[0].GetScore().GetScore() > PlayersList[1].GetScore().GetScore())
                System.out.println(PlayersList[0].getName() + " Is The Winner!!\n");
            else
                System.out.println(PlayersList[1].getName() + " Is The Winner!!\n");
            System.out.println("The Scores Are : \n");
            System.out.println(PlayersList[0].getName() + " : " + PlayersList[0].GetScore().GetScore());
            System.out.println(PlayersList[1].getName() + " : " + PlayersList[1].GetScore().GetScore());
        }
    }

}
