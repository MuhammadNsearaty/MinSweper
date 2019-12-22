package Gui2;
import GAMES.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSpinner;
import com.sun.media.jfxmedia.events.PlayerTimeListener;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.image.Image;
import javafx.util.Duration;


public class Gui2Game extends NormalGame {
    JFXButton bt[][] ;
    boolean pressed[] = new boolean[3];
    int currplayerindex = 0;

    Object lock = new Object();

    Timer timer = new Timer();

    long timelimit = 10;//متغير يدل على زمن الدور الخاص بالاعب

    RunThreads time = new RunThreads(lock);

    PlayersThreads playersThreads[] ;

    PCThread pcThread;

    Label name = new Label(),gameshieldsLabel = new Label();

    Timeline timeline;

    String name1;

    Stage currStage;

    public Gui2Game(Gui2Game game)
    {
        this.grid = game.grid;
        this.MovesList = game.MovesList;
        this.PlayersList = game.PlayersList;
        this.bt = game.bt;
        this.currplayerindex = game.currplayerindex;
        this.CurrPlayer = game.CurrPlayer;
    }

    public void unDoMove(PlayerMove list[],int index,int begin,int end)
    {
        for(int i = begin ; i<= end ; i++)
            MovesList[i] = null;
        CurrCount = CurrCount - (end - begin) - 1;

        PlayersList[index].setSheildsCount(list[0].getPlayer().getShieldsCount());
        int i=0;
        for(i = 0 ; list[i]!=null ;i++)
        {
            this.bt[list[i].getSquare().getX()][list[i].getSquare().getY()].setText("");
            String str = list[i].GetMoveResult().getNewstatus().GetSquareStatus();
            this.bt[list[i].getSquare().getX()][list[i].getSquare().getY()].setId("closed");

            this.grid.setSq(list[i].getSquare().getX() , list[i].getSquare().getY());

            if(str.equals("Revealed"))
            {
                grid.GetSquares()[list[i].getSquare().getX()][list[i].getSquare().getY()].SetSquareStatus("Closed");
                this.bt[list[i].getSquare().getX()][list[i].getSquare().getY()].setStyle("-fx-background-color :none");
            }
            else if(str.equals("Marked"))
            {
                if(list[i].GetMoveResult().getNewscore() < 0 )
                    grid.GetSquares()[list[i].getSquare().getX()][list[i].getSquare().getY()].SetSquareStatus("Closed");
                else
                {
                    grid.GetSquares()[list[i].getSquare().getX()][list[i].getSquare().getY()].SetSquareStatus("Mine");
                    grid.GetSquares()[list[i].getSquare().getX()][list[i].getSquare().getY()].SetMine();
                }
            }
            else if(str.equals("Shield"))
            {
                grid.GetSquares()[list[i].getSquare().getX()][list[i].getSquare().getY()].SetSquareStatus("Mine");
                grid.GetSquares()[list[i].getSquare().getX()][list[i].getSquare().getY()].SetMine();
                PlayersList[index].IncShieldCount();
            }
            else if(str.equals("Revealed Mine"))
            {
                grid.GetSquares()[list[i].getSquare().getX()][list[i].getSquare().getY()].SetSquareStatus("Mine");
            }
            else if(str.equals("UnMarked"))
            {
                grid.GetSquares()[list[i].getSquare().getX()][list[i].getSquare().getY()].SetSquareStatus("Marked");
                this.bt[list[i].getSquare().getX()][list[i].getSquare().getY()].setId("mark");
            }
            else
            {
                this.bt[list[i].getSquare().getX()][list[i].getSquare().getY()].setStyle("-fx-background-color :none");
                String value = list[i].GetMoveResult().getNewstatus().GetSquareStatus();
                value = value.substring(value.length()-1,value.length());
                grid.GetSquares()[list[i].getSquare().getX()][list[i].getSquare().getY()].SetSquareStatus(value);
            }
            if(list[i].GetMoveResult().getFoundShield())
            {
                PlayersList[index].DecShieldCount();
                grid.setShieldsCount(grid.getShieldsCount()+1);
                grid.GetSquares()[list[i].getSquare().getX()][list[i].getSquare().getY()].EnableShield();
            }

        }
        PlayersList[index].AddScore(-list[i-1].GetMoveResult().getNewscore());


        for(i = 0 ; i < grid.getN() ; i++)
            for(int j = 0 ; j < grid.getM() ;j++)
            {
                if(grid.GetSquares()[i][j].IsMine() && grid.GetSquares()[i][j].GetSquareStatus() != "Shield" )
                {
                    this.bt[i][j].setStyle("-fx-background-color :none");
                    this.bt[i][j].setId("closed");
                }
            }


    }
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

    String BeginTime;
    String EndTime;

    JFXSpinner timespinner= new JFXSpinner();
    public Gui2Game()
    {
        super();
        timespinner.setMaxSize(50,50);

        timeline = new Timeline(
                new KeyFrame(Duration.ZERO,new KeyValue(timespinner.progressProperty(),0)),
                new KeyFrame(Duration.seconds(timelimit),new KeyValue(timespinner.progressProperty(),1))
        );
        timeline.setCycleCount(1);

    }

    public void setCurrStage(Stage currStage) {
        this.currStage = currStage;
    }



    public Object getLock()
    {
        return lock;
    }

    public void setLock(Object lock) {
        this.lock = lock;
    }

    public void setBt()
    {
        bt = new JFXButton[grid.getN()][grid.getM()];
        for(int i = 0 ; i < grid.getN();i++)
            for(int j = 0 ;j  < grid.getM() ;j++) {
                bt[i][j] = new JFXButton();
                bt[i][j].setPrefSize(35, 35);
            }

    }
    public void setTimelimit(long timelimit)
    {
        this.timelimit =timelimit;
    }

    public void setPlayerIscore(int index)
    {
        PlayersList[index].SetScore(new Score());
    }
    public long getTimelimit() {
        return timelimit;
    }
    public void setCurrplayerindex(int currplayerindex)
    {
        this.currplayerindex = currplayerindex;
    }
    public int getMovelistlength()
    {
        return MovesList.length;
    }

    public void setCurrPlayer(Player player, int index)
    {
        CurrPlayer = player;
        currplayerindex = index;
    }
    public void swipnamelabel(Label label)
    {
        label.setText(name1);
    }

    public  void SwitchPlayer()
    {
        currplayerindex++;
        currplayerindex = (currplayerindex%PlayersList.length);

        CurrPlayer = PlayersList[currplayerindex];
    }
    public void prevPlayer()
    {
        currplayerindex--;
        if(currplayerindex < 0)
            currplayerindex = PlayersList.length-1;
    }
    public int getPlayersCount()
    {
        return PlayersList.length;
    }
    class RunThreads extends TimerTask
    {
        Object lock;
        public RunThreads(Object obj)
        {
            lock = obj;
        }
        @Override
        public void run() {
            synchronized (lock){
                lock.notify();
            }
        }
    }
    class PlayersThreads extends Thread implements EventHandler
    {
        Player p;
        int playerindex = 0;
        Object lock;
        public PlayersThreads(Player p ,int index,Object lock){
            this.p = p;
            playerindex = index;
            this.lock = lock;
        }
        public  void run() {
            while (true) {
                    while (true) {
                        synchronized (lock) {
                            if (CurrPlayer == p) {
                                break;
                        }
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                synchronized (lock) {
                    timeline.play();
                    pressed[1] = false;
                    for (int i = 0; i < grid.getN(); i++)
                        for (int j = 0; j < grid.getM(); j++) {
                            final int x = i, y = j;
                            bt[i][j].setOnMouseClicked(event ->
                            {
                                PlayerMove move = new PlayerMove();
                                MoveType type = new MoveType();
                                MouseButton click = event.getButton();
                                if (click == MouseButton.SECONDARY)
                                    type.SetNewType("Mark");
                                else
                                    type.SetNewType("Reveal");
                                move = CurrPlayer.GetPlayerMove(grid.GetSquares()[x][y]);
                                move.setType(type);
                                if (AcceptMove(move)) {
                                    int any = currplayerindex;
                                    SwitchPlayer();
                                    //timer.cancel();
                                    timeline.stop();

                                    int begin = CurrCount;

                                    DoTheMove(move);
                                    MoveResult res = move.GetMoveResult();

                                    System.out.println(move.getPlayer().getName());

                                    try {
                                        pressed[1] = UpdateScene(any, begin, res);
                                        if (pressed[1]) {
                                            System.out.println("Any thing");
                                            timespinner = null;
                                            timer.cancel();
                                            timeline.stop();
                                            Platform.runLater( () -> {
                                                if(currStage != null)
                                                   currStage.close();
                                                else
                                                {
                                                    currStage = new Stage();
                                                    currStage.close();
                                                }
                                            });
                                            try {
                                                SaveOnGameEnd();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            } catch (ClassNotFoundException e) {
                                                e.printStackTrace();
                                            }
                                            return;
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }

                                    gameshieldsLabel.setText("Game Remaining Shields" + grid.getShieldsCount());
                                    timer = new Timer();
                                    time = new RunThreads(lock);

                                    timeline = new Timeline(
                                            new KeyFrame(Duration.ZERO, new KeyValue(timespinner.progressProperty(), 0)),
                                            new KeyFrame(Duration.seconds(timelimit), new KeyValue(timespinner.progressProperty(), 1)));
                                    /*Platform.runLater(() -> {
                                        timespinner.setRadius(0.0);
                                    });*/
                                    timeline.setCycleCount(1);
                                    timeline.setOnFinished(this::handle);

                                    if(CurrPlayer.getName() == "PC")
                                        name1 = CurrPlayer.getName() + " Turn\nPc is Thinking";
                                    else
                                        name1 = CurrPlayer.getName() + " Turn\nPlayer Shields " + CurrPlayer.getShieldsCount();
                                    swipnamelabel(name);
                                    timeline.play();
                                    timer.schedule(time, 0, (timelimit * 1000));
                                }
                            });
                        }
                }

            }
        }
        @Override
        public void handle(Event event) {
            SwitchPlayer();
            timeline.stop();
            timer.cancel();
            time = new RunThreads(lock);
            timer = new Timer();
            timespinner.setRadius(0.0);

            timeline = new Timeline(
                    new KeyFrame(Duration.ZERO,new KeyValue(timespinner.progressProperty(),0)),
                    new KeyFrame(Duration.seconds(timelimit),new KeyValue(timespinner.progressProperty(),1)));
            timeline.setCycleCount(1);

            timeline.setOnFinished(this::handle);
            gameshieldsLabel.setText("Game Remaining Shields" +grid.getShieldsCount());
            if(CurrPlayer.getName() == "PC")
                name1 = CurrPlayer.getName() + " Turn\nPc is Thinking";
            else
                name1 = CurrPlayer.getName() + " Turn\nPlayer Shields " + CurrPlayer.getShieldsCount();
            swipnamelabel(name);
            timer.schedule(time, 0, (timelimit * 1000));
            timeline.play();


        }
    }
    class PCThread extends Thread implements EventHandler{
        ComPlayer PC;
        int playerindex;
        Object lock;
        public PCThread(ComPlayer player ,int index,Object lock)
        {
            PC = player;
            playerindex = index;
            this.lock = lock;
        }
        public void run() {
            while(true) {
                while (true) {
                    synchronized (lock) {
                        if (CurrPlayer == PC) {
                                SwitchPlayer();
                                break;
                        }
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                    }
                }
                boolean res[]= new boolean[1];
                synchronized (lock)
                {
                    for(int i = 0;i<grid.getN();i++)
                        for(int j = 0;j < grid.getM();j++)
                            bt[i][j].setDisable(true);
                    timeline.play();
                    timeline.setOnFinished(this::handle);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    timeline.stop();
                    timer.cancel();

                    PlayerMove move[] =new PlayerMove[1];
                    move[0] = PC.GetPlayerMove(null);
                    while(!AcceptMove(move[0]))
                        move[0]= PC.GetPlayerMove(null);
                    int begin = CurrCount;

                    DoTheMove(move[0]);
                    if(CurrPlayer.getName() == "PC")
                        name1 = CurrPlayer.getName() + " Turn\nPc is Thinking";
                    else
                        name1 = CurrPlayer.getName() + " Turn\nPlayer Shields " + CurrPlayer.getShieldsCount();
                    Platform.runLater( () ->{
                        try {
                            res[0] = UpdateScene(playerindex,begin,move[0].GetMoveResult());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        swipnamelabel(name);
                        if(res[0]) {
                            System.out.println("any thing");
                            timespinner = null;
                            timeline.stop();
                            timer.cancel();
                            currStage.close();
                            try {
                                SaveOnGameEnd();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    timer = new Timer();
                    time = new RunThreads(lock);
                    timeline = new Timeline(
                            new KeyFrame(Duration.ZERO,new KeyValue(timespinner.progressProperty(),0)),
                            new KeyFrame(Duration.seconds(timelimit),new KeyValue(timespinner.progressProperty(),1)));
                    timeline.setCycleCount(1);
                    timeline.play();
                    timeline.setOnFinished(this::handle);
                    for(int i = 0;i<grid.getN();i++)
                        for(int j = 0;j < grid.getM();j++)
                            bt[i][j].setDisable(false);
                    timer.schedule(time, 0, (timelimit*1000));
                }

            }
        }

        @Override
        public void handle(Event event) {
            timeline.stop();
            timeline = new Timeline(
                    new KeyFrame(Duration.ZERO,new KeyValue(timespinner.progressProperty(),0)),
                    new KeyFrame(Duration.seconds(timelimit),new KeyValue(timespinner.progressProperty(),1)));
            timeline.setCycleCount(1);
            gameshieldsLabel.setText("Game Remaining Shields" +grid.getShieldsCount());
            if(CurrPlayer.getName() == "PC")
                name1 = CurrPlayer.getName() + " Turn\nPc is Thinking";
            else
                name1 = CurrPlayer.getName() + " Turn\nPlayer Shields " + CurrPlayer.getShieldsCount();
            swipnamelabel(name);
            //timeline.setOnFinished(this::handle);
            timeline.play();

        }

    }

    /*class ReplayThread extends TimerTask {
        int currmove = 0;
        Object Relock;

        public ReplayThread(Object lock)
        {
            this.Relock = lock;
        }
        @Override
        public void run() {
            while(currmove != CurrCount) {
                PlayerMove move = MovesList[currmove];
                if(PlayersList.length == 1)
                {
                    DoTheMove(move);

                }
            }
        }
    }
    */
    public void SaveOnGameEnd() throws IOException, ClassNotFoundException {
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");
        Date date1 = new Date();
        EndTime = format1.format(date1);

        Imp2Save imp2Save = new Imp2Save().makeOne(this.grid,this.timelimit,this.CurrCount,this.PlayersList,this.currplayerindex,this.MovesList,BeginTime,EndTime);
        ArrayList<Imp2Save> arrayList = new ArrayList<>();
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("End Games"));
        arrayList = (ArrayList<Imp2Save>) objectInputStream.readObject();
        objectInputStream.close();

        arrayList.add(imp2Save);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("End Games"));
        objectOutputStream.writeObject(arrayList);
        objectOutputStream.flush();
        objectOutputStream.close();
    }
    public int getCurrCount()
    {
        return this.CurrCount;
    }
    public int getCurrplayerindex() {
        return currplayerindex;
    }
    public Player GetCurrPlayer()
    {
        return CurrPlayer;
    }
    public void SetGame(int[] n , GameRules rules)
    {
        super.SetGame(n,rules);
        bt = new JFXButton[grid.getN()][grid.getM()];
        for(int i =0 ;i<grid.getN();i++)
            for(int j =0 ;j<grid.getM();j++)
                bt[i][j] = new JFXButton();
    }
    public boolean CheckChoose(int index)
    {
        for(int i = 0; i<index ;i++)
            if(PlayersList[i].getName() == PlayersList[index].getName()|| PlayersList[i].GetColor().GetColor() == PlayersList[index].GetColor().GetColor())
                return true;
        return false;
    }
    public void SetPlayers(int n[])
    {
        Player list[] = new Player[n[0]+n[1]];
        int i ;
        for(i=0 ; i < n[0];i++)
        {
                try {
                    list[i] = new Gui2Player();
                    list[i].GetPlayerInfo(this.grid.getMinCount());

                }
                catch (Exception e)
                {
                System.out.println(e.getMessage());
                }
        }
        if(n[1]!= 0)
        {
            list[i] = new DumyPlayer();
            list[i].SetColor(new GuiColor(Color.GHOSTWHITE));
            pcThread = new PCThread((DumyPlayer)list[i],i,lock);
        }
        this.PlayersList = list;
        this.CurrPlayer = this.PlayersList[0];
        playersThreads = new PlayersThreads[PlayersList.length];
        for(int ii =0 ;ii<n[0];ii++)
            playersThreads[ii] = new PlayersThreads(PlayersList[ii],ii,lock);
    }

    public int getPlayerindex(String name)
    {
        int index = 0;
        for(int i = 0 ; i <PlayersList.length ; i++)
        {
            if(PlayersList[i].getName().equals(name))
            {
                index =  i;
                break;
            }
        }
        return index;
    }


    public Boolean UpdateScene(int index,int begin,MoveResult moveResult) throws IOException, ClassNotFoundException {
        PlayersList[index].AddScore(moveResult.getNewscore());
        for(int i = begin ; i<CurrCount ;i++) {
            if (MovesList[i].GetMoveResult().getFoundShield()) {
                if (!(PlayersList[index] instanceof DumyPlayer))
                    PlayersList[index].IncShieldCount();
            }
            else if (MovesList[i].GetMoveResult().getLostShield()) {
                if (!(PlayersList[index] instanceof DumyPlayer))
                    PlayersList[index].DecShieldCount();
            }
        }
        int result = this.Rules.CheckPlayerWin();
        int res = PlayersList[index].GetScore().GetScore();
        Stage stage = new Stage();
        Square sq[][] = grid.GetSquares();
        Boolean returnV;
        stage.initModality(Modality.APPLICATION_MODAL);
          if (res <= 0)
          {
              for(int i=0 ; i < grid.getN() ;i++)
                  for(int j=0 ; j < grid.getM() ;j++) {
                      if (sq[i][j].IsMine())
                      {
                          if(grid.GetSquares()[i][j].GetSquareStatus() != "Shield")
                             bt[i][j].setId("mine");
                      }
                  }
                  Label lose = new Label();
              lose.setText(PlayersList[index].getName() + " is The Loser");
              StackPane stackPane = new StackPane();
              int draw[] = new int[PlayersList.length];
              for(int i =0 ; i< draw.length;i++)
                  draw[i] = -2;
              if(PlayersList.length >1)
              {
                  int currWin = 0;
                  StringBuilder stringBuilder = new StringBuilder();
                  for(int i = 1 ; i< PlayersList.length ;i++) {
                      stringBuilder.append(PlayersList[i].getName() + " : "+PlayersList[i].GetScore().GetScore()+"\n");
                      if (PlayersList[i].GetScore().GetScore() > PlayersList[currWin].GetScore().GetScore())
                      {
                          for(int ii =0 ; ii< draw.length ;ii++)
                              draw[i]= -1;
                          currWin = i ;
                      }
                      else if (PlayersList[currWin].GetScore().GetScore() == PlayersList[i].GetScore().GetScore())
                      {
                          int j = 0 ;
                          while(draw[j]>-1)
                              j++;
                          draw[j] = i;
                      }
                  }
                  boolean iswin = false;
                  Label Win = new Label();
                  for(int i = 0; i<draw.length;i++)
                      if(draw[i] <= -1 )
                      {
                          iswin = true;
                          break;
                      }
                  if(iswin)
                     Win.setText("The Winner is " + PlayersList[currWin].getName() + "With Score" + PlayersList[currWin].GetScore().GetScore());
                  else
                  {
                      StringBuilder Winbuild = new StringBuilder();
                      Winbuild.append("There is a Draw Between\n");
                      for(int i = 0 ; i<draw.length;i++)
                      {
                          if(draw[i]> -1)
                                 Winbuild.append(PlayersList[i].getName()+ "\n");
                          else
                              break;
                      }
                      Win.setText(Winbuild.toString());
                  }
                  lose.setText("The Loser Is "+PlayersList[index].getName());
                  stackPane.getChildren().add(lose);
                  lose.setTranslateY(1);
                  stackPane.getChildren().add(Win);
                  Win.setStyle("-fx-text-fill: aliceblue");
                  Win.setTranslateY(-20);
              }
              else
              {
                  lose.setText(PlayersList[0].getName() + " Has Lost");
                  stackPane.getChildren().add(lose);
              }
              JFXButton Close = new JFXButton("OK");
              Close.setPrefSize(75,30);
              Close.setOnAction(Event -> stage.close());
              lose.setStyle("-fx-text-fill: aliceblue");
              stackPane.getChildren().addAll(Close);
              Close.setTranslateY(80);
             // Close.setTranslateX(150);//Later
              //Close.setTranslateY(-50);//Later
              Scene scene = new Scene(stackPane,300,200);
              scene.getStylesheets().add("GameOver.css");
              stage.setScene(scene);
              stage.setResizable(false);
              stage.showAndWait();
              returnV = true;
          }
        else
          {
            for(int k=begin;k<CurrCount;k++)
                {
                    PlayerMove move = MovesList[k];
                    int i = move.getSquare().getX();
                    int j = move.getSquare().getY();
                    String str = move.GetMoveResult().getNewstatus().GetSquareStatus();
                    Color PlayerColor;
                    if(PlayersList[index] instanceof DumyPlayer)
                    {
                        DumyPlayer  tmp = (DumyPlayer) PlayersList[index];
                        PlayerColor = tmp.GetGuiColor();
                    }
                    else {
                        Gui2Player tmp = (Gui2Player) PlayersList[index];
                        PlayerColor = tmp.GetGuiColor();
                    }
                    String strcolor = PlayerColor.toString();
                    strcolor = strcolor.substring(2,strcolor.length());
                    if(str == "Revealed")
                    {
                        bt[i][j].setStyle("-fx-background-image: none;-fx-background-color: #"+strcolor + ";"+"-fx-text-fill: black;-fx-border-color: black;-fx-font-weight: bold;-fx-font-size: 9");
                    }
                    else if(str == "Marked"){
                        bt[i][j].setId("mark");
                    }
                    else if(str == "Closed" || (str.charAt(0)>='1'&&str.charAt(0)<='8') || str == "Mine");
                    else if(str == "UnMarked")
                    {
                        bt[i][j].setId("unmark");
                    }
                    else if(str == "Shield")
                    {
                        bt[i][j].setId("shield");
                    }
                    else if(str == "Revealed Mine")
                    {
                        bt[i][j].setId("revealed-mine");
                    }
                    else
                    {
                        bt[i][j].setText(str.substring(str.length()-1,str.length()));
                        bt[i][j].setStyle("-fx-background-image: none;-fx-background-color: #"+strcolor+";"+"-fx-text-fill: black;-fx-border-color: black;-fx-font-weight: bold;-fx-font-size: 9");
                    }
                }
                returnV = false;
          }
          if(result != -1)
          {
              this.PlayersList[index].AddScore(100*result);
              this.PlayersList[index].AddScore(PlayersList[index].getShieldsCount()*50);
              Label WinLabel = new Label();
              StringBuilder st = new StringBuilder();
              st.append(PlayersList[0].getName() + " : "+PlayersList[0].GetScore().GetScore()+"\n");
              st.append(" \n");
              int currwin = 0;
              int draw[] = new int[PlayersList.length];
              for (int i = 0; i<draw.length ;i++)
                  draw[i]= -2;
              if(PlayersList.length > 1)
              {
                  for(int i=1;i<PlayersList.length;i++)
                  {
                      st.append(PlayersList[i].getName() + " : "+PlayersList[i].GetScore().GetScore()+"\n");
                      if(PlayersList[currwin].GetScore().GetScore() < PlayersList[i].GetScore().GetScore())
                      {
                          for(int ii = 0; ii<draw.length;ii++)
                              draw[ii] = -1;
                          currwin = i;
                      }
                      else if(PlayersList[currwin].GetScore().GetScore() == PlayersList[i].GetScore().GetScore())
                      {
                          int j = 0;
                          while(draw[j]> -1)
                              j++;
                          draw[j] = i;
                      }
                  }
              }
              if(draw[0] > -1)
              {
                  StringBuilder strwin = new StringBuilder();
                  strwin.append("There is a Draw Between" );
                  for(int i =0 ; i< draw.length ;i++)
                  {
                      if(draw[i] > -1)
                          strwin.append(PlayersList[draw[i]].getName()+"\n");
                  }
                  WinLabel.setText(strwin.toString());
              }
              else
              {
                  WinLabel.setText(PlayersList[currwin].getName() + " is The Winner \n The Scores Are :\n"+st.toString());
              }
              VBox WinPane = new VBox();
              JFXButton jfxButton = new JFXButton("OK");
              //jfxButton.setStyle("-fx-background-color: red");
              WinPane.getChildren().add(WinLabel);
              WinPane.getChildren().add(jfxButton);
              //WinLabel.setStyle("-fx-text-fill: blue");
              Scene scene = new Scene(WinPane,200,100);
              scene.getStylesheets().add("WinSheet.css");
              stage.setScene(scene);
              SaveOnGameEnd();
              stage.showAndWait();
          }

          return returnV;
    }



    @Override
    public void GameEnd() {

    }

    @Override
    public void PlayerLoses(Player[] p, int i) {

    }
}
/*       VBox box = new VBox(20);
            Label Warn = new Label("Your Score is Almost Zero Be CareFull");
            Button Close = new Button("Close");
            Close.setOnAction(E -> stage.close());
            box.getChildren().addAll(Warn,Close);
            Scene scene = new Scene(box,200,100);
            stage.setScene(scene);
            stage.showAndWait();*/