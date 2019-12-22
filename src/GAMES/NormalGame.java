package GAMES;

import java.io.Serializable;
import java.util.Scanner;

public abstract class NormalGame extends Game implements Serializable {

    private final int MoveMatrixX[] = {0,-1,1,1,-1,0,1,-1};
    private final int MoveMatrixY[] = {-1,-1,-1,0,0,1,1,1};

    public NormalGame()
    {
        super();
        super.Rules = new DefaultRules();
    }
    public abstract void GameEnd();
    public abstract void PlayerLoses(Player[] p,int i);



    class DefaultRules extends GameRules implements Serializable
    {
        public int[] GetGameInfo() {
            Scanner scan = new Scanner(System.in);
            int vector[] = new int[2];
            boolean res = true;
            illegalChoiceException e = new illegalChoiceException("Wrong Choice .....\n Try again");
            while (res) {
                res = false;
                try {
                    System.out.println("1 - Singele Player \n 2 - Human VS Human \n 3 - Human VS PC \n  Enter Your Choice :");
                    int ch = scan.nextInt();
                    switch (ch) {
                        case 1: {
                            vector[0] = 1;
                            vector[1] = 0;
                            break;
                        }
                        case 2: {
                            vector[0] = 2;
                            vector[1] = 0;
                            break;
                        }
                        case 3: {
                            vector[0] = 1;
                            vector[1] = 1;
                            break;
                        }
                        default: {
                            throw e;
                        }
                    }
                } catch (Exception e1) {
                    System.out.println(e1.getMessage());
                    res = true;
                }
            }

            return vector;
        }

        public int GetGameLevel()
        {
            Scanner scan = new Scanner(System.in);
            boolean res = true;
            illegalChoiceException e = new illegalChoiceException("Wrong Choice .....\n Try again");
            while (res) {
                res = false;
                try {
                    System.out.println("1 - Easy \n2 - Medium \n3 - Hard \nChoice the level of the Game");
                    char c = scan.next().charAt(0);
                    switch (c) {
                        case '1': {
                            return 1;
                        }
                        case '2': {
                            return 2;
                        }
                        case '3': {
                            return 3;
                        }
                        default: {
                            throw e;
                        }
                    }
                } catch (Exception e1) {
                    System.out.println(e1.getMessage());
                    res = true;
                }
            }
            return 1;
        }
        public int CheckPlayerWin()
        {
            int ClosedSquaresCount = 0;
            int MarkedMinesCount = 0;
            int SheildedCount = 0;
            String str ;
            for(int i = 0 ; i < grid.getN() ; i++)
                for(int j = 0 ; j < grid.getM() ; j++)
                {
                    str = grid.Sq[i][j].GetSquareStatus();
                    if(str == "Shield"){
                        ClosedSquaresCount++;
                        SheildedCount++;
                    }
                    if(str == "Revealed Mine")
                        ClosedSquaresCount++;
                    if(str == "Closed" || str == "Marked" || (str.charAt(0) >= '1'&& str.charAt(0) <='8') || str == "Mine")
                        ClosedSquaresCount++;
                    if(str == "Marked" && grid.Sq[i][j].IsMine())
                        MarkedMinesCount++;
                }

            if( ClosedSquaresCount == grid.getMinCount())
                return grid.getMinCount() - (MarkedMinesCount+SheildedCount);
            return -1;
        }
        public  boolean CheckPlayerLose (int result)
        {
            if(result == 0)
                return true;
            return false;
        }
        public int SetNewScore(int state){

            switch (state)
            {
                case 1://وضع علم على لغم مخفي
                {
                    return 5;
                }
                case 2 ://وضع علم على مربع مغلق لا يحوي لغم
                {
                    return -1;
                }
                case 3://فتح مربع فارغ
                {
                    return 10;
                }
                case 4://ازالة علم عن مربع مغلق و لا يحوي لغم
                {
                    return 1;
                }
                case 5://ازالة علم عن مربع يحوي لغم مخفي
                {
                    return -5;
                }
                case 6://الكبس على لغم
                {
                    return -250;
                }
                case 7://فتح مربع بال  Flood Fill
                {
                    return 1;
                }
                default://عند فتح مربع يحوي رقم نعيد قيمةالرقم
                {
                    return state;
                }
            }
        }
    }




    public  void DoTheMove(PlayerMove move) {


            if(move.getType().GetType().equals("Flood Reveal"))
            {
                String str = grid.Sq[move.getSquare().getX()][move.getSquare().getY()].GetSquareStatus();
                if( str.charAt(0) >= '1' && str.charAt(0) <= '8')
                 {
                     MoveResult res = new MoveResult();
                     if(move.getSquare().IsShield())
                     {
                         res.isFoundShield();
                         this.grid.DecShieldsCount();
                     }
                     grid.Sq[move.getSquare().getX()][move.getSquare().getY()].SetSquareStatus("Revealed "+ str.charAt(0));
                     Object c = move.getPlayer().GetColor();
                     grid.Sq[move.getSquare().getX()][move.getSquare().getY()].SetColor(c);
                     res.setNewscore(Rules.SetNewScore(7));
                     res.setNewstatus("Revealed "+ str.charAt(0));
                     move.SetMoveResult(res);
                     AddMove(move);
                     return;
                 }
                 else
                     {
                         grid.Sq[move.getSquare().getX()][move.getSquare().getY()].SetSquareStatus("Revealed");
                         MoveResult res = new MoveResult();
                         res.setNewscore(Rules.SetNewScore(7));
                         res.setNewstatus("Revealed");
                         if(move.getSquare().IsShield())
                         {
                             res.isFoundShield();
                             this.grid.DecShieldsCount();
                         }
                         move.SetMoveResult(res);
                         AddMove(move);
                         Object c = move.getPlayer().GetColor();
                         grid.Sq[move.getSquare().getX()][move.getSquare().getY()].SetColor(c);
                         for (int i = 0; i < 8; i++)
                         {
                            int X = move.getSquare().getX() + MoveMatrixX[i];
                            int Y = move.getSquare().getY() + MoveMatrixY[i];
                            if (X >= grid.getN() || X < 0 || Y >= grid.getM() || Y < 0)
                                continue;
                            String str1 = grid.Sq[X][Y].GetSquareStatus();
                            if( ! ( str1 == "Closed" || (str1.charAt(0) >= '0'&& str1.charAt(0) <='8') ))
                                continue;
                            PlayerMove FloodMove1 = new PlayerMove();
                            MoveType mv = new MoveType();
                            mv.SetNewType("Flood Reveal");
                            FloodMove1.setType(mv);
                            FloodMove1.setPlayer(move.getPlayer());
                            FloodMove1.setSquare(grid.Sq[X][Y].getX(),grid.Sq[X][Y].getY());
                            DoTheMove(FloodMove1);
                         }
                     }
            }
            else if (move.getType().GetType().equals("Reveal"))
            {
                String str = grid.Sq[move.getSquare().getX()][move.getSquare().getY()].GetSquareStatus();
                //اذا كان المربع يحوي لغم
                if (str == "Mine" || grid.Sq[move.getSquare().getX()][move.getSquare().getY()].IsMine()) {
                    MoveResult res = new MoveResult();
                    if(move.getPlayer().getShieldsCount() != 0)
                    {
                        res.setNewstatus("Shield");
                        res.setNewscore(0);
                        res.isLostShield();
                        grid.Sq[move.getSquare().getX()][move.getSquare().getY()].SetSquareStatus("Shield");
                    }
                    else
                        {
                            res.setNewstatus("Revealed Mine");
                            res.setNewscore(Rules.SetNewScore(6));
                        }
                    move.SetMoveResult(res);
                    AddMove(move);
                    Object c = move.getPlayer().GetColor();
                    grid.Sq[move.getSquare().getX()][move.getSquare().getY()].SetColor(c);
                    return;
                }
                //اذا كان المربع يحوي رقم
                else if( str.charAt(0) >= '1' && str.charAt(0) <= '8')
                {
                    MoveResult res = new MoveResult();
                    res.setNewstatus("Revealed " +str.charAt(0));
                    res.setNewscore(Integer.parseInt(str));
                    if(move.getSquare().IsShield())
                    {
                        res.isFoundShield();
                        this.grid.DecShieldsCount();

                    }
                    move.SetMoveResult(res);
                    AddMove(move);
                    grid.Sq[move.getSquare().getX()][move.getSquare().getY()].SetSquareStatus("Revealed "+ str.charAt(0));
                    Object c = move.getPlayer().GetColor();
                    grid.Sq[move.getSquare().getX()][move.getSquare().getY()].SetColor(c);
                    return;
                }
                //اذا كان المربع فارغ
                else
                {
                    grid.Sq[move.getSquare().getX()][move.getSquare().getY()].SetSquareStatus("Revealed");
                    Object c = move.getPlayer().GetColor();
                    grid.Sq[move.getSquare().getX()][move.getSquare().getY()].SetColor(c);
                    int tmp = CurrCount;
                    CurrCount++;
                    int result = Rules.SetNewScore(3);
                    for(int i = 0; i< 8 ; i++) {
                      //  MoveResult res = new MoveResult();
                            int X = move.getSquare().getX() + MoveMatrixX[i];
                            int Y = move.getSquare().getY() + MoveMatrixY[i];
                            if(X >= grid.getN() || X < 0 || Y >= grid.getM() || Y < 0)
                                continue;
                            str =  grid.Sq[X][Y].GetSquareStatus();
                            if( !(str == "Closed" || (str.charAt(0) >= '1'&& str.charAt(0)<='8' )))
                                continue;
                                PlayerMove FloodMove = new PlayerMove();
                                MoveType mv = new MoveType();
                                mv.SetNewType("Flood Reveal");
                                FloodMove.setType(mv);
                                FloodMove.setPlayer(move.getPlayer());
                                FloodMove.setSquare(grid.Sq[X][Y].getX(),grid.Sq[X][Y].getY());
                                DoTheMove(FloodMove);
                        }
                     for(int i = tmp+1 ; i < CurrCount ; i++ )
                         result += MovesList[i].GetMoveResult().getNewscore();
                    MoveResult res = new MoveResult();
                    if(move.getSquare().IsShield())
                    {
                         res.isFoundShield();
                        this.grid.DecShieldsCount();
                    }
                    res.setNewstatus("Revealed");
                    res.setNewscore(result);
                    move.SetMoveResult(res);
                    super.MovesList[tmp] = new PlayerMove(move) ;
                }
            }
            //اذا كانت الحركة Mark
            else
                {
                    MoveResult res = new MoveResult();
                    String str = grid.Sq[move.getSquare().getX()][move.getSquare().getY()].GetSquareStatus();
                    //اذا كان المربع عليه علم ونريد ازالته
                    if(str == "Marked")
                    {
                        //اذا ازلنا علم عن  لغم
                          if( grid.Sq[move.getSquare().getX()][move.getSquare().getY()].IsMine())
                              res.setNewscore(Rules.SetNewScore(5));
                          // اذا ازلنا علم عن مربع فارغ
                          else
                              res.setNewscore(Rules.SetNewScore(4));
                          char cnt = '0';
                          for(int z =0 ;z < 8 ;z++)
                          {
                              int X = move.getSquare().getX() + MoveMatrixX[z];
                              int Y = move.getSquare().getY() + MoveMatrixY[z];
                              if(X < 0 || Y < 0 || X >= grid.getN() ||Y >= grid.getM() )
                                  continue;
                              if(grid.Sq[X][Y].IsMine())
                                  cnt++;
                          }
                          if(cnt == '0')
                              str = "Closed";
                          else {
                              StringBuilder st = new StringBuilder();
                              st.append(cnt);
                              str = st.toString();
                          }
                         grid.Sq[move.getSquare().getX()][move.getSquare().getY()].SetSquareStatus(str);
                         // Object c = new ConsoleColor();
                         // grid.Sq[move.getSquare().getX()][move.getSquare().getY()].SetColor(c);
                          res.setNewstatus("UnMarked");
                          move.SetMoveResult(res);
                          AddMove(move);
                          return;
                    }
                    else {

                        //اذا كان المربع يحوي لغم نزيد بمقدار 5
                        if (grid.Sq[move.getSquare().getX()][move.getSquare().getY()].IsMine())
                            res.setNewscore(Rules.SetNewScore(1));
                        // اذا كان المربع لا يحوي لغم ننقص بمقدار 1
                        else
                            res.setNewscore(Rules.SetNewScore(2));
                        grid.Sq[move.getSquare().getX()][move.getSquare().getY()].SetSquareStatus("Marked");
                        //Color c = move.getPlayer().GetColor();
                        //grid.Sq[move.getSquare().getX()][move.getSquare().getY()].SetColor(c.GetColor());
                        res.setNewstatus("Marked");
                        move.SetMoveResult(res);
                        AddMove(move);
                        return;
                    }
                }
    }
    public boolean AcceptMove(PlayerMove move){

        int x= move.getSquare().getX();
        int y = move.getSquare().getY();
        int n = grid.getN();
        int  m = grid.getM();
        if ( (x >= n ||  y >= m)|| (x < 0 || y < 0) )
            return false;
        String st = move.getType().GetType();
        String str = grid.Sq[x][y].GetSquareStatus();
        if( str == "Marked" && move.getType().GetType() == "Mark")
            return true;
        if( str == "Closed"  || (str.charAt(0) >= '1' && str.charAt(0) <= '8') || str == "Mine")
            return true;
        return false;
    }


}

