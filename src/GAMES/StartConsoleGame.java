package GAMES;

import javafx.scene.transform.Scale;

import java.util.Scanner;

public class StartConsoleGame implements Start {
     NormalGame game = new ConsoleGame();
    private Game.GameRules rules = game.new DefaultRules();

    @Override
    public void Play()
        {
            game.Rules =  game.new DefaultRules();
            Scanner scan  = new Scanner(System.in);
            boolean res1 = true;
            char ch1 =1;
            illegalChoiceException e = new illegalChoiceException("Wrong Choice \n Try Again..");
            while(res1) {
                res1 = false;
                try {
                    System.out.println("1 - New Game \n 2 - Option \n 3 - Exit\nChoose..... ");
                    ch1 = scan.next().charAt(0);
                    if(ch1 > '3' || ch1 < '1')
                        throw e;
                    switch (ch1) {
                        case '1': {
                            int[] vector =new int[3];
                            int arr[] = game.Rules.GetGameInfo();
                            vector[0] = arr[0];
                            vector[1] = arr[1];
                            vector[2] = game.Rules.GetGameLevel();
                            int tmp = 0;
                            game.SetGame(vector, rules);
                            boolean gameEnd = false;
                            int u =0;
                            for ( u = 0; u < vector[0]; u++)
                            {
                                game.PlayersList[u] = new ConsolePlayer();
                                Color c = new ConsoleColor();
                                c.SetColor((char)(42 + u));
                                game.PlayersList[u].SetColor(c);
                                System.out.println("Enter The "+ (u+1) + "Player Name:");
                                String name = scan.next();
                                game.PlayersList[u].SetName(name);
                            }
                            if(vector[1] != 0) {
                                game.PlayersList[u] = new DumyPlayer();
                                Color c = new ConsoleColor();
                                c.SetColor('^');
                                game.PlayersList[u].SetColor(c);
                            }
                            game.grid.PrintDemo();
                            while (!gameEnd) {
                                for (int p = 0; p < vector[0] + vector[1]; p++) {
                                    game.CurrPlayer = game.PlayersList[p].getPlayer();
                                    if (vector[0] + vector[1] > 1) // طباعة  اسماء اللاعبين في كل دور اذا كانوا اكثر من  لاعب واحد
                                        System.out.println("\n" + game.CurrPlayer.getName() + " Turn :");
                                    PlayerMove move = new PlayerMove();
                                    boolean res = false;
                                    Exception e2 = new illegalSquareName("Wrong Square Name\n Try Again..");
                                    game.grid.PrintGrid();
                                    System.out.println();
                                    while (!res)
                                    {
                                        try {
                                            res = true;
                                            move = game.CurrPlayer.GetPlayerMove(null);
                                            move.setPlayer( game.CurrPlayer);
                                            res = game.AcceptMove(move);
                                            if (!res)
                                                throw e2;
                                            else
                                            {
                                               if (move.getPlayer() instanceof DumyPlayer)
                                               {
                                                   char CH = (char)(move.getSquare().getY() +97);
                                                   StringBuilder str = new StringBuilder();
                                                   if(move.getType().GetType() == "Mark")
                                                       str.append('-');
                                                   str.append(CH);
                                                   str.append((move.getSquare().getX()+1) );
                                                   System.out.println( str.toString() );
                                               }
                                                tmp = game.CurrCount;
                                                game.DoTheMove(move);
                                            }
                                        } catch (Exception e1) {
                                            if(!(move.getPlayer() instanceof DumyPlayer))
                                            {
                                                System.out.println(e1.getMessage());
                                                game.grid.PrintGrid();
                                            }
                                            res = false;
                                        }
                                    }
                                    int result = game.MovesList[tmp].GetMoveResult().getNewscore();
                                    if (game.Rules.CheckPlayerLose(result))
                                    {
                                        if (vector[1] == 1 || vector[0] > 1)
                                        {
                                            game.PlayerLoses(game.PlayersList, p);
                                        }
                                        else
                                            System.out.println("You just pressed a Mine \n Good luck next time\n Your Result Is :" +game.PlayersList[0].GetScore().GetScore() );
                                        gameEnd = true;
                                        game.grid.PrintDemo();
                                        break;
                                    }
                                    else
                                        {
                                        game.PlayersList[p].AddScore(result);
                                        int Check = game.Rules.CheckPlayerWin();
                                        if (Check != -1) {
                                            game.PlayersList[p].AddScore(100 * Check);
                                            game.GameEnd();
                                            gameEnd = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            res1 = true;
                            break;
                        }
                        case '2': {
                            EditRuleGame ed = new EditRuleGame();
                            rules = ed.new EditRules();
                            ((EditRuleGame.EditRules) rules).SetScoreValues();
                            res1 = true;
                            break;
                        }
                        case '3': {
                            res1 = false;
                            break;
                        }
                    }
                }catch (Exception e1)
                {
                    System.out.println(e1.getMessage());
                    res1 = true;

                }
            }
        }
  /*  @Override
    public void Option() {



    }

    @Override
    public void SaveGame() {

    }

    @Override
    public Game Loadgame() {
        return null;
    }
*/

}
