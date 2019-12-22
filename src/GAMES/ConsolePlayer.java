package GAMES;

import java.io.Serializable;
import java.util.Scanner;

public class ConsolePlayer extends Player implements Serializable {

    Scanner scn = new Scanner(System.in);

    public ConsolePlayer()
    {
        super();
        color = new ConsoleColor();
    }

    @Override
    public void GetPlayerInfo(Object obj) {

    }

    public void SetPlayerName() {
        System.out.println("please enter your name (console player) :");
        String n = scn.next();
        super.SetName(n);
    }

    public void SetPlayer1Color() {
        char x = '*';
        Color c = new ConsoleColor();
        c.SetColor(x);
    }

    /*public Object GetPlayerScore() {
        Object score = GetScore();
        return score;
    }*/

    @Override
    public PlayerMove GetPlayerMove(Object obj) {
        PlayerMove move = new PlayerMove();

        illegalSquareName e = new illegalSquareName("Wrong Square Name ");        // أوبجكت من الاكسبشن تبع اللعبة
        boolean res = true;
        while(res) {
            try {
                System.out.println("please enter your guest of row(int) and the column(char) :");
                String temp = scn.next();
                temp = temp.toLowerCase();
                res = false;
                if (temp.length() == 0)
                    throw e;
                else if ((temp.charAt(0) >= 'a' && temp.charAt(0) <= 'z') || ( (temp.charAt(0) == '-') && (temp.charAt(1) >= 'a' && temp.charAt(1) <= 'z'))) {  // الإم هي عدد الاعمدة يلي عندك ياها بحيث بدك تأخذ الاحرف من اول شي و لبعد عدد الاهمدة
                    String str;
                    if(temp.charAt(0) == '-')
                        str = temp.substring(2, temp.length());
                    else
                        str = temp.substring(1, temp.length());
                    int i;
                    for (i = 0; i < str.length(); i++) {
                        char ch = str.charAt(i);
                        if (!(ch >= '0' && ch <= '9')) {
                            i = -1;
                            break;
                        }
                    }
                    if (i == -1)
                        throw e;
                    int x = Integer.parseInt(str)-1;
                    int y;
                    if (temp.charAt(0) == '-') {
                        move.setType(new MoveType("Mark"));
                        y = temp.charAt(1) - 97;
                    } else {
                        move.setType(new MoveType("Reveal"));
                        y = temp.charAt(0) - 97;
                    }
                    move.setSquare(x, y);
                }
                else if ((temp.charAt(0) >= '0' && temp.charAt(0) <= '9') ||( (temp.charAt(0) == '-') && (temp.charAt(1) >= '0' && temp.charAt(1) <= '9'))) {
                    char z1 = temp.charAt(temp.length()-1);
                    char ch;
                    if (!(z1 >= 'a' && z1 <= 'z') ){
                        throw e;
                    }
                    int x, y;
                    String str;
                    if (temp.charAt(0) == '-')
                        str = temp.substring(1, temp.length() - 1);
                    else
                        str = temp.substring(0, temp.length() - 1);
                    int i;
                    for (i = 0; i < str.length(); i++) {
                         ch = (char)str.charAt(i);
                        if (!(ch >= '0' && ch <= '9')) {
                            i = -1;
                            break;
                        }
                    }
                    if (i == -1)
                        throw e;
                    y = z1 - 97;
                    x = Integer.parseInt(str)-1;
                    if (temp.charAt(0) == '-')
                        move.setType(new MoveType("Mark"));
                    else
                        move.setType(new MoveType("Reveal"));
                    move.setSquare(x, y);
                } else
                    throw e;
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
                res = true;
            }
        }
            return move;
    }
}

















/*

                if (temp.charAt(0) >= 'a' && temp.charAt(0) <= 'a' + m && temp.charAt(1) >= 'a' && temp.charAt(1) <= 'a' + m)   // في حال السترنغ المدخل هو حرفين
                        {
                        throw (e);
                        } else if (temp.charAt(0) >= '1' && temp.charAt(0) <= '9' && temp.charAt(1) >= '1' && temp.charAt(1) <= m)  // في حال الحرفين المدخلين هو عبارة عن رقمين
                        {
                        throw (e);
                        } else if (temp.length() >= 2)  // في حال طول السترنغ أكبر من  الواحد // هون حطها اول شي  ظظظظظظظظظظظظظظظظظظظظظظظظظظظظظظظظظظظظظ
                        {
                        try {

                        if (temp.charAt(0) == '-' && temp.charAt(1) >= 0 && temp.charAt(1) <= n && temp.charAt(2) >= 'a' && temp.charAt(2) <= 'a' + m) //  في حال التأشيرة بشكل صحيح //////////////////////////////////// حط المجال الرقمي فقط أكبر او يساويالصفر  و حط

                        {
                        move.square.SetX(temp.charAt(1));
                        move.square.SetY(temp.charAt(2));
                        move.type.SetMoveType("Mark");
                        } else if (temp.charAt(0) == '-' && temp.charAt(1) >= 'a' && temp.charAt(1) < 'a' + m && temp.charAt(2) >= 0 && temp.charAt(2) <= m) //في حال كان لدي تاشيرة بس مدخل حرف بعدين رقم
        {
        move.square.SetX(temp.charAt(2));
        move.square.SetY(temp.charAt(1));
        move.type.SetMoveType("Mark");
        } else if (temp.charAt(0) == '-' && temp.charAt(1) >= 0 && temp.charAt(1) <= n && temp.charAt(2) >= 0 && temp.charAt(2) <= n) // هون في حال كان عندي تاشيرة بس رقمين السترنغ
        throw (e);
        else if (temp.charAt(0) == '-' && temp.charAt(1) >= 'a' && temp.charAt(1) <= 'a' + m && temp.charAt(2) >= 'a' && temp.charAt(2) <= 'a' + m)// هون في حال كان عندي تاشيرة بس عندي جرفين السترنغ
        throw (e);
        else
        throw (e);

        } catch (Exception e) {
        System.out.println(e.getMessage());
        }
        }    //               أنا هون حطيتها ب تراي لحالها لانو طريقة تعبياية الاحداثيات ترتيبها بيختلف
        else {
        move.square.SetX(temp.charAt(0));
        move.square.SetY(temp.charAt(1));
        move.type.SetMoveType("Reveal");
        }
        } catch (Exception e1) {
        System.out.println(e1.getMessage());
        }
        return move;*/