package GAMES;

import Gui2.Gui2Game;
import Gui2.Gui2Player;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.Date;

public class Imp2Save implements Serializable {

    private int Rows,Columns,Sheilds,Mines;

    private Object PlayersList[][];
    private int CurrPlayerIndex;
    private int CurrCount;

    private String BeginTime,EndTime;

    private Object MovesSquares[][];
    private String MovesTypes[];
    private Object MovesResults[];
    private Object MovesPlayers[][];

    private int ShieldsPosition[][];
    private int MinesPosition[][];

    private long TimeLimit;


    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public int getRows() {
        return Rows;
    }

    public void setRows(int rows) {
        Rows = rows;
    }

    public int getColumns() {
        return Columns;
    }

    public void setColumns(int columns) {
        Columns = columns;
    }

    public int getSheilds() {
        return Sheilds;
    }

    public void setSheilds(int sheilds) {
        Sheilds = sheilds;
    }

    public int getMines() {
        return Mines;
    }

    public void setMines(int mines) {
        Mines = mines;
    }

    public Object[][] getPlayersList() {
        return PlayersList;
    }

    public void setPlayersList(Object[][] playersList) {
        PlayersList = playersList;
    }

    public int getCurrPlayerIndex() {
        return CurrPlayerIndex;
    }

    public void setCurrPlayerIndex(int currPlayerIndex) {
        CurrPlayerIndex = currPlayerIndex;
    }

    public int getCurrCount() {
        return CurrCount;
    }

    public void setCurrCount(int currCount) {
        CurrCount = currCount;
    }

    public Object[][] getMovesSquares() {
        return MovesSquares;
    }

    public void setMovesSquares(Object[][] movesSquares) {
        MovesSquares = movesSquares;
    }

    public String[] getMovesTypes() {
        return MovesTypes;
    }

    public void setMovesTypes(String[] movesTypes) {
        MovesTypes = movesTypes;
    }

    public Object[] getMovesResults() {
        return MovesResults;
    }

    public void setMovesResults(Object[] movesResults) {
        MovesResults = movesResults;
    }

    public Object[][] getMovesPlayers() {
        return MovesPlayers;
    }

    public void setMovesPlayers(Object[][] movesPlayers) {
        MovesPlayers = movesPlayers;
    }

    public int[][] getShieldsPosition() {
        return ShieldsPosition;
    }

    public void setShieldsPosition(int[][] shieldsPosition) {
        ShieldsPosition = shieldsPosition;
    }

    public int[][] getMinesPosition() {
        return MinesPosition;
    }

    public void setMinesPosition(int[][] minesPosition) {
        MinesPosition = minesPosition;
    }

    public long getTimeLimit() {
        return TimeLimit;
    }

    public void setTimeLimit(long timeLimit) {
        TimeLimit = timeLimit;
    }



    public Imp2Save()
    {

    }
    public Imp2Save makeOne(Grid grid , long timeLimit , int currCount, Player[] Playerlist,int currPlayerIndex,PlayerMove[] Movelist,String beginTime,String endTime)
    {
        Gui2Game game = new Gui2Game();
        game.setGrid(grid);
        game.setTimelimit(timeLimit);
        game.setCurrCount(currCount);
        game.setPlayersList(Playerlist);
        game.setCurrplayerindex(currPlayerIndex);
        game.setMoves(Movelist);
        game.setBeginTime(beginTime);
        game.setEndTime(endTime);
        Imp2Save imp2Save = new Imp2Save(game);
        return imp2Save;
    }

    public Imp2Save(Gui2Game game) {
        this.Rows = game.grid.getN();
        this.Columns = game.grid.getM();
        this.Sheilds = game.grid.getPermenShieldCount();
        this.Mines = game.grid.getMinCount();
        this.TimeLimit = game.getTimelimit();
        this.CurrCount = game.CurrCount;

        this.MinesPosition = new int[2][game.grid.getMinCount()];
        this.ShieldsPosition = new int[2][game.grid.getPermenShieldCount()];

        this.PlayersList = new Object[4][game.PlayersList.length];
        this.CurrPlayerIndex = ((Gui2Game) game).getCurrplayerindex();

        this.MovesSquares = new Object[2][game.CurrCount];
        this.MovesPlayers = new Object[4][game.CurrCount];
        this.MovesResults = new Object[game.CurrCount];
        this.MovesTypes = new String[game.CurrCount];

        this.BeginTime = game.getBeginTime();
        this.EndTime = game.getEndTime();

        for(int i = 0 ;i < game.PlayersList.length;i++)
        {
            this.PlayersList[0][i] = game.PlayersList[i].name;
            this.PlayersList[1][i] = game.PlayersList[i].currscore;
            this.PlayersList[2][i] = game.PlayersList[i].permenShieldCount;
            this.PlayersList[3][i] = game.PlayersList[i].color.colorchar.toString();
        }

        for(int i= 0 ; i< game.CurrCount ; i++)
        {
            this.MovesSquares[0][i] = game.MovesList[i].getSquare().getX();
            this.MovesSquares[1][i] = game.MovesList[i].getSquare().getY();
            this.MovesTypes[i] = game.MovesList[i].getType().GetType();
            this.MovesResults[i] = game.MovesList[i].GetMoveResult();

            this.MovesPlayers[0][i] = game.MovesList[i].getPlayer().name;
            this.MovesPlayers[1][i] = game.MovesList[i].getPlayer().currscore;
            this.MovesPlayers[2][i] = game.MovesList[i].getPlayer().permenShieldCount;
            this.MovesPlayers[3][i] = game.MovesList[i].getPlayer().color.colorchar.toString();

        }


        int mi =0 , sh =0;
        for(int i = 0 ; i<game.grid.getN();i++) {
            for (int j = 0; j < game.grid.getM(); j++) {
                if (game.grid.Sq[i][j].IsMine()) {
                    this.MinesPosition[0][mi] = i;
                    this.MinesPosition[1][mi] = j;
                    mi++;
                } else if (game.grid.Sq[i][j].IsShield()) {
                    this.ShieldsPosition[0][sh] = i;
                    this.ShieldsPosition[1][sh] = j;
                    sh++;
                }
            }
        }

    }
    public Gui2Game Load()
    {
        Gui2Game game = new Gui2Game();

        int X[]={-1,1,0,-1,1,0,-1,1};
        int Y[]={0,0,-1,-1,-1,1,1,1};

        game.grid.setSize(this.Rows,this.Columns);
        game.grid.setMinesCount(this.Mines);
        game.grid.setShieldsCount(this.Sheilds);
        game.grid.setPermenShieldCount(this.Sheilds);

        game.PlayersList = new Player[PlayersList[0].length];
        game.MovesList = new PlayerMove[10000];


        for(int i = 0; i < this.PlayersList[0].length ; i++)
        {
            if(this.PlayersList[0][i].equals("PC"))
                game.PlayersList[i] = new DumyPlayer();
            else
                game.PlayersList[i] = new Gui2Player();
            game.PlayersList[i].name = (String) this.PlayersList[0][i];
            game.PlayersList[i].currscore = (Score) this.PlayersList[1][i];
            game.PlayersList[i].SheildsCount = (int) this.PlayersList[2][i];
            game.PlayersList[i].permenShieldCount = (int) this.PlayersList[2][i];
            game.PlayersList[i].color.colorchar = Color.web((String) this.PlayersList[3][i]);
        }

        for(int i = 0 ;i < this.MovesTypes.length ; i++)
        {
            game.MovesList[i] = new PlayerMove();
            Gui2Player player = new Gui2Player();
            player.color.colorchar = Color.web((String)this.MovesPlayers[3][i]);
            player.name = (String)this.MovesPlayers[0][i];
            player.currscore = (Score)this.MovesPlayers[1][i];
            player.SheildsCount = (int)this.MovesPlayers[2][i];

            game.MovesList[i].setType(new MoveType(this.MovesTypes[i]));
            game.MovesList[i].setSquare((int)this.MovesSquares[0][i] , (int)this.MovesSquares[1][i]);
            game.MovesList[i].setPlayer(player);
            game.MovesList[i].SetMoveResult((MoveResult) this.MovesResults[i]);
        }

        game.grid.Sq = new Square[this.Rows][this.Columns];
        for(int i=0;i<this.Rows;i++)
            for(int j = 0 ;j<this.Columns;j++)
            {
                game.grid.Sq[i][j] = new Square();
                game.grid.Sq[i][j].setX(i);
                game.grid.Sq[i][j].setY(j);
            }

        for(int i = 0 ;i < this.Mines; i++)
        {
            int xi = this.MinesPosition[0][i];
            int yi = this.MinesPosition[1][i];
            game.grid.Sq[xi][yi].SetMine();
            game.grid.Sq[xi][yi].SetSquareStatus("Mine");
            for(int k=0;k<8;k++)
            {
                int u = X[k] + xi;
                int v = Y[k] + yi;
                if(u < 0 || u >= game.grid.getN() || v < 0 || v >= game.grid.getM())
                    continue;
                if(game.grid.Sq[u][v].GetSquareStatus() == "Closed")
                    game.grid.Sq[u][v].SetSquareStatus("1");
                else
                {
                    String str = game.grid.Sq[u][v].GetSquareStatus();
                    if(str == "Mine")
                        continue;
                    int ch = Integer.parseInt(str) + 1;
                    StringBuilder sr = new StringBuilder();
                    sr.append(ch);
                    game.grid.Sq[u][v].SetSquareStatus(sr.toString());
                }
            }

        }
        for(int j =0; j < this.Sheilds;j++)
        {
            game.grid.Sq[this.ShieldsPosition[0][j]][this.ShieldsPosition[1][j]].EnableShield();
        }



        game.CurrCount = this.CurrCount;
        game.setTimelimit(this.TimeLimit);
        game.setCurrplayerindex(this.CurrPlayerIndex);
        game.CurrPlayer = game.GetPlayerI(game.getCurrplayerindex());
        return game;
    }


}
