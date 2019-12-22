package GAMES;

import java.io.*;
import java.util.ArrayList;

public abstract class Game implements Serializable{
    protected Grid grid;
    protected Player PlayersList[] = new ConsolePlayer[3];
    protected Player CurrPlayer;
    public GameRules Rules;
    protected PlayerMove MovesList[];
    protected int CurrCount = 0;


    public void SetRules(GameRules rule)
    {
        this.Rules = rule;
    }
    public void SetplayerI(String Str,int i)
    {
        PlayersList[i] = new ConsolePlayer();
        PlayersList[i].SetName(Str);
    }
    public PlayerMove getMoveI(int index)
    {
        return MovesList[index];
    }

    public void setCurrCount(int currCount) {
        CurrCount = currCount;
    }

    public Grid getGrid() {
        return grid;
    }
    public Player getCurrPlayer()
    {
        return CurrPlayer;
    }
    public Player GetPlayerI(int i)
    {
        return PlayersList[i];
    }
    public PlayerMove[] getMovesList()
    {
        return MovesList;
    }
    public void setMovesList()
    {
        MovesList = new PlayerMove[10000];
    }
    public void setGrid(Grid grid)
    {
        this.grid = grid;
    }
    public void setMoves(PlayerMove[] movesList)
    {
        this.MovesList = movesList;
    }
    public void setPlayersList(Player[] playersList)
    {
        this.PlayersList = playersList;
    }
    public Game()
    {
    grid = new Grid();
    }
    public Grid GetGrid()
    {return grid;}
    public void SetGrid(Grid g)
    {
        this.grid = g;
    }
    public void AddMove(PlayerMove move)
    {
        this.MovesList[CurrCount] = move;
        CurrCount = CurrCount + 1 ;
    }
    public abstract class  GameRules implements Serializable
    {
        public abstract boolean CheckPlayerLose(int result);
        public abstract int CheckPlayerWin();
        public abstract int SetNewScore(int state);
        public abstract int GetGameLevel();
        public abstract int[] GetGameInfo();
    }
    public void SetGame(int[] n , GameRules rules)
    {
        this.grid  = new Grid(n);
        this.PlayersList = new Player[n[0]+n[1]];
        this.CurrPlayer = PlayersList[0];
        this.MovesList = new PlayerMove[10000];
        this.Rules = rules;
        this.CurrCount = 0;
    }
    public void setPlayersShields(Object[] obj)
    {
        for(int i = 0 ; i< PlayersList.length ; i++)
        {
            PlayersList[i].setSheildsCount((int)obj[i]);
        }
    }
    public void setRules(GameRules rules)
    {
        this.Rules = rules;
    }
    public abstract boolean AcceptMove(PlayerMove move);
    public  abstract void DoTheMove(PlayerMove move);
    public void Write2File(Object game , String FilePath) throws IOException, ClassNotFoundException {

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FilePath));
        objectOutputStream.writeObject(game);
        objectOutputStream.flush();
        objectOutputStream.close();

        ArrayList<String> arrayList = new ArrayList<String>();


        File file = new File("Gui2/Paths");

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
        if(file.canRead())
           arrayList = (ArrayList<String>) objectInputStream.readObject();
        objectInputStream.close();


        for(String str : arrayList)
            System.out.println(str);
        System.out.println("|||||||");
        arrayList.add(FilePath);

        for(String str : arrayList)
            System.out.println(str);
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Paths"));
        outputStream.writeObject(arrayList);
        outputStream.flush();
        outputStream.close();
    }
    public Imp2Save LoadGame(String FilePath) throws IOException, ClassNotFoundException {

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FilePath));
        Imp2Save game = (Imp2Save) objectInputStream.readObject();
        objectInputStream.close();
        return game;
    }
    public ArrayList<Imp2Save> LoadReplayList(String FilePath) throws IOException, ClassNotFoundException {

     ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FilePath));
     ArrayList<Imp2Save> arrayList = (ArrayList<Imp2Save>) objectInputStream.readObject();
     return arrayList;
    }
public void setMovess()
{
    MovesList = new PlayerMove[10000];
}


}
