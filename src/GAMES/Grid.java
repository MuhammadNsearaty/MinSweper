package GAMES;

import java.io.Serializable;
import java.util.Random;

public class Grid implements Serializable {
   // private Mine mines ;
    private int m,n,min;
    private Shield[] gridSheild;
    private int ShieldsCount = 2;
    private int permenShieldCount = 2;
     Square Sq[][];
    int X[]={-1,1,0,-1,1,0,-1,1};
    int Y[]={0,0,-1,-1,-1,1,1,1};
    public Grid()
    {
        m=n=5;
        min = 5;
        Sq = new Square[n][m];
        ShieldsCount =2;
        permenShieldCount = 2;
        gridSheild = new Shield[ShieldsCount];
        gridSheild[0] = new Shield();
        gridSheild[1] = new Shield();
    }
    public void setPermenShieldCount(int permenShieldCount)
    {
        this.permenShieldCount = permenShieldCount;
    }
    public int getPermenShieldCount()
    {
        return permenShieldCount;
    }
    public void setSize(int n,int m)
    {
        this.n = n;
        this.m = m;
    }
    public void setSq(int i , int j)
    {
        Sq[i][j] = new Square();
        Sq[i][j].setX(i);
        Sq[i][j].setY(j);
    }

    public void setShieldsCount(int shieldsCount) {
        ShieldsCount = shieldsCount;
    }
    public void setMinesCount(int minesCount)
    {
        this.min = minesCount;
    }
    public Square[][] GetSquares()
    {
        return Sq;
    }
    public void PrintGrid() {
        System.out.print("      ");
        for(int k =0 ;k < n ;k++)
        {
            char ch = (char) (k + 65);
            System.out.print(ch + "    ");
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            if(i+1 < 10)
                System.out.print((i+1) + " ");
            else
                System.out.print(i+1);
            for (int j = 0; j < m; j++) {
                System.out.print(" | ");
                //char c = Sq[i][j].GetColor();
                //System.out.print(c);
                String str = Sq[i][j].GetSquareStatus();
                if (str == "Marked")
                    System.out.print("P");
                else if (str == "Closed" || (str.charAt(0) >= '1' && str.charAt(0) <= '8') || str == "Mine")
                    System.out.print("O");
                else if(str == "Revealed")
                    System.out.print(" ");
                else
                    System.out.print(str.charAt(str.length() - 1));

            }
            System.out.println(" |");
        }
    }
    public void PrintDemo()
    {
        for(int i =0 ; i < n ; i++) {
                for (int j = 0; j < m; j++) {
                    System.out.print(" | ");
                    String str = Sq[i][j].GetSquareStatus();
                    if(str == "Closed")
                            System.out.print("  ");
                    else if(str == "Mine")
                        System.out.print(" M");
                    else if(str == "Marked")
                        System.out.print(" P");
                    else
                        System.out.print(" "+str.charAt(0));
                }
            System.out.println(" |");
            }

    }
    public int getM()
    {
        return m;
    }
    public int getN()
    {
        return n;
    }
    public int getMinCount()
    {
        return min;
    }

    public int getShieldsCount() {
        return ShieldsCount;
    }
    public void DecShieldsCount()
    {
        this.ShieldsCount--;
    }
    public Grid(int z[]){
    {
        switch(z[2]) {
            case 1: {
                m = n = 5;
                min = 5;
                ShieldsCount = 2;
                permenShieldCount = 2;
                break;
            }
            case 2: {
                n = 9;
                m = 10;
                min = 20;
                ShieldsCount = 5;
                permenShieldCount = 5;
                break;
            }
            case 3: {
                n = 11;
                m = 21;
                min = 40;
                ShieldsCount = 8;
                permenShieldCount = 8;
                break;
            }
            case 4:
            {
                n = z[3];
                m = z[4];
                min = z[5];
                ShieldsCount = z[6];
                permenShieldCount = z[6];
            }
        }
        gridSheild = new Shield[ShieldsCount];

        for(Shield x : gridSheild)
            x = new Shield();

        Sq = new Square[n][m];
        for(int i =0 ;i < n;i++)
            for(int j = 0 ; j< m ;j++)
                Sq[i][j] = new Square(i,j);
        //توزيع ألغام
        int l = 0;
        Random rand =new Random();
        int xi ;
        int yi ;
        while ( min > l )
        {
            xi =rand.nextInt(n-1) + 1;
            yi =rand.nextInt(m-1) + 1;
            if(xi >= n || xi < 0 || yi >= m || yi < 0)
             continue;
            if(!Sq[xi][yi].IsMine())
             {
                    Sq[xi][yi].SetMine();
                    Sq[xi][yi].SetSquareStatus("Mine");
                    l++;
                    for(int k=0;k<8;k++)
                    {
                        int u = X[k] + xi;
                        int v = Y[k] + yi;
                        if(u < 0 || u >= n || v < 0 || v >= m)
                            continue;
                        if(Sq[u][v].GetSquareStatus() == "Closed")
                            Sq[u][v].SetSquareStatus("1");
                        else
                        {
                            String str = Sq[u][v].GetSquareStatus();
                            if(str == "Mine")
                                continue;
                            int ch = Integer.parseInt(str) + 1;
                            StringBuilder sr = new StringBuilder();
                            sr.append(ch);
                            Sq[u][v].SetSquareStatus(sr.toString());
                        }
                    }

             }
        }

        Random random = new Random();
        l = 0;
        while(l < ShieldsCount)
        {
            xi =rand.nextInt(n-1) + 1;
            yi =rand.nextInt(m-1) + 1;
            if(xi >= n || xi < 0 || yi >= m || yi < 0)
                continue;
            if(Sq[xi][yi].IsMine() || Sq[xi][yi].IsShield())
                continue;
            Sq[xi][yi].EnableShield();
            l++;
        }
    }
    }





}
