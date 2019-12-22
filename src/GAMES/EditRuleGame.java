package GAMES;

import GAMES.NormalGame.DefaultRules;

import java.io.Serializable;
import java.util.Scanner;

public class EditRuleGame extends NormalGame implements Serializable {

    @Override
    public void GameEnd() {

    }

    @Override
    public void PlayerLoses(Player[] p, int i) {

    }

    public class EditRules extends DefaultRules {
        Scanner scan = new Scanner(System.in);
        int value[] = new int[10];
        public int CheckPlayerWin() {
            int ClosedSquaresCount = 0;
            int MarkedMinesCount = 0;
            int SheildedCount = 0;
            String str;
            for (int i = 0; i < grid.getN(); i++)
                for (int j = 0; j < grid.getM(); j++) {
                    str = grid.Sq[i][j].GetSquareStatus();
                    if (str == "Shield") {
                        ClosedSquaresCount++;
                        SheildedCount++;
                    }
                    if (str == "Revealed Mine")
                        ClosedSquaresCount++;
                    if (str == "Closed" || str == "Marked" || (str.charAt(0) >= '1' && str.charAt(0) <= '8') || str == "Mine")
                        ClosedSquaresCount++;
                    if (str == "Marked" && grid.Sq[i][j].IsMine())
                        MarkedMinesCount++;
                }
            if( ClosedSquaresCount == grid.getMinCount())
                return grid.getMinCount() - (MarkedMinesCount+SheildedCount);
            return -1;
        }
            public void SetValues(int v[])
        {
            value = v;
        }
        public void SetScoreValues() {
            System.out.println("Option :\n");
            illegalChoiceException e = new illegalChoiceException("Wrong Choice ...\n Try Again With Normal Numbers ");
            boolean res = true;
            while (res) {
                res = false;
                try {
                    System.out.println("Put A Flag on A Mine ( >= 0 ) >> ");
                    value[0] = scan.nextInt();
                    if (value[0] < 0)
                        throw e;
                } catch (Exception e1) {
                    System.out.println(e1.getMessage());
                    res = true;
                }
            }
            res = true;
            while (res) {
                res = false;
                try {
                    System.out.println("Put A Flag on An Empty Closed Square( most be <= 0 )  >> ");
                    value[1] = scan.nextInt();
                    if (value[1] > 0)
                        throw e;
                } catch (Exception e1) {
                    System.out.println(e1.getMessage());
                    res = true;
                }
            }
            res = true;

            while (res) {
                res = false;
                try {
                    System.out.println("Open An Empty Square with Click ( > 0 )>>");
                    value[2] = scan.nextInt();
                    if (value[2] <= 0)
                        throw e;
                } catch (Exception e1) {
                    System.out.println(e1.getMessage());
                    res = true;
                }
            }
            res = true;

            while (res) {
                res = false;
                try {
                    System.out.println("Open A Sqaure With Flood Fill ( >= 0 )>>");
                    value[3] = scan.nextInt();
                    if (value[3] <= 0)
                        throw e;
                    } catch (Exception e1) {
                    System.out.println(e1.getMessage());
                    res = true;
                    }
                }

        }
        public int SetNewScore(int state) {
            switch (state) {
                case 1://وضع علم على لغم مخفي
                {
                    return value[0];
                }
                case 2://وضع علم على مربع مغلق لا يحوي لغم
                {
                    return value[1];
                }
                case 3://فتح مربع فارغ
                {
                    return value[2];
                }
                case 4://  ازالة علم عن مربع مغلق و لا يحوي لغم
                {
                    return -value[1];
                }
                case 5://ازالة علم عن مربع يحوي لغم مخفي
                {
                    return -value[0];
                }
                case 7://فتح مربع بال  Flood Fill
                {
                  return value[3];
                }
                default://عند فتح مربع يحوي رقم نعيد قيمةالرقم
                {
                    return state;
                }
            }

        }

    }
}