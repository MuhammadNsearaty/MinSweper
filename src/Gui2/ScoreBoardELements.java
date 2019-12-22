package Gui2;

import GAMES.*;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Currency;
import java.util.Date;

public class ScoreBoardELements implements EventHandler {
    Imp2Save game;
    int Id;
    String playerNames;
    int highestScore;
    String highestPlayerScoreAndName;
    String time;
    JFXButton replay;
    static int temp = 0;
    public ScoreBoardELements(Imp2Save game)
    {
        this.game = game;
        replay = new JFXButton("Replay");
        if(temp%2 == 0)
            replay.setStyle("-fx-background-color :#6d6d6d;-fx-text-fill :aliceblue");
        else
            replay.setStyle("-fx-background-color :#2FAB13;-fx-text-fill :aliceblue");

        StringBuilder Names = new StringBuilder();
        Score score = (Score) game.getPlayersList()[1][0];
        int currscore = score.GetScore();

        temp++;
        this.Id = temp;
        for(int i = 0 ;i < game.getPlayersList()[0].length ; i++)
        {
            Names.append("/"+(String) game.getPlayersList()[0][i]);
            Score score1 = (Score) game.getPlayersList()[1][i];
            highestPlayerScoreAndName = (String)( game.getPlayersList()[0][0])
                    + " : " + new Score((Score) game.getPlayersList()[1][0]).GetScore();
            if(score1.GetScore() > currscore)
            {
                highestPlayerScoreAndName = (String)( game.getPlayersList()[0][i])
                        + " : " + new Score((Score) game.getPlayersList()[1][i]).GetScore();
                currscore = new Score((Score)game.getPlayersList()[1][i]).GetScore();
            }
        }
        time = game.getBeginTime().toString() + "  "+game.getEndTime().toString();
        playerNames = Names.toString();
        highestScore = currscore;
        replay.setOnMouseClicked(this::handle);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(String playerNames) {
        this.playerNames = playerNames;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public String getHighestPlayerScoreAndName() {
        return highestPlayerScoreAndName;
    }

    public void setHighestPlayerScoreAndName(String highestPlayerScoreAndName) {
        this.highestPlayerScoreAndName = highestPlayerScoreAndName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public JFXButton getReplay() {
        return replay;
    }

    public void setReplay(JFXButton replay) {
        this.replay = replay;
    }

    public static int getTemp() {
        return temp;
    }

    public static void setTemp(int temp) {
        ScoreBoardELements.temp = temp;
    }

    @Override
    public void handle(Event event) {
        Gui2Game gui2Game[] = new Gui2Game[1];
        gui2Game[0] = game.Load();

        PlayerMove movelist[] = gui2Game[0].getMovesList();
        gui2Game[0].setMovess();

        Stage stage = new Stage();
        stage.setTitle("Replay");
        stage.initModality(Modality.APPLICATION_MODAL);
        StackPane stackPane = new StackPane();
        Label GameShields = new Label();
        Label PlayersInfo = new Label();

        HBox hBox = new HBox(180);

        int currmove[] = new int[1];
        currmove[0] = 0;
        int movelistsize = gui2Game[0].getCurrCount();

        gui2Game[0].setCurrCount(0);
        gui2Game[0].setCurrplayerindex(0);
        gui2Game[0].setCurrPlayer(gui2Game[0].GetPlayerI(0) , 0);
        JFXButton nextmove = new JFXButton("Next");
        JFXButton prevmove = new JFXButton("Prev");
        JFXButton autoplay = new JFXButton("Auto Play");
        JFXButton pause = new JFXButton("Pause");

        gui2Game[0].setBt();
        GridPane gridPane = new GridPane();
        for(int i =0 ; i< gui2Game[0].getGrid().getN() ; i++)
            for(int j= 0 ;j < gui2Game[0].getGrid().getM();j++)
            {
                gridPane.add(gui2Game[0].bt[i][j],j,i);
            }
        for(int i = 0 ; i< gui2Game[0].getPlayersCount() ;i++)
            gui2Game[0].setPlayerIscore(i);

        int lastPlayer[] = new int[1];
        lastPlayer[0] = 0;
        final Gui2Game tempgame[] = new Gui2Game[1];
        boolean auto[] = new boolean[1];
        auto[0] = true;

        nextmove.setOnMouseClicked(event1 -> {
           prevmove.setDisable(false);

            boolean res = false;
            PlayerMove move = new PlayerMove();

               move = movelist[currmove[0]];
               move.setPlayer(gui2Game[0].getCurrPlayer());
               currmove[0]++;

           if (currmove[0] < movelistsize - 1){
               if(movelist[currmove[0]].getType().GetType().equals("Flood Reveal"))
               {
                   currmove[0]++;
                   while(movelist[currmove[0]].getType().GetType().equals("Flood Reveal"))
                       currmove[0]++;
               }
           }
            int begin = gui2Game[0].getCurrCount();
            lastPlayer[0] = gui2Game[0].getCurrplayerindex();
            PlayerMove newmove =new PlayerMove(move);
            gui2Game[0].DoTheMove(newmove);

            try {
                res = gui2Game[0].UpdateScene(gui2Game[0].getCurrplayerindex(), begin , move.GetMoveResult());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            //String nn = movelist[currmove[0]].getPlayer().getName();
            //gui2Game[0].setCurrPlayer(gui2Game[0].GetPlayerI(gui2Game[0].getPlayerindex(nn)),gui2Game[0].getPlayerindex(nn));

            gui2Game[0].SwitchPlayer();

            if(res)
            {
                gui2Game[0].setCurrPlayer(gui2Game[0].GetPlayerI(lastPlayer[0]) , lastPlayer[0]);
                nextmove.setDisable(true);
            }
            StringBuilder string = new StringBuilder();
            string.append("Game Shields :");
            string.append(gui2Game[0].getGrid().getShieldsCount());
            GameShields.setText(string.toString());

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(gui2Game[0].GetPlayerI(gui2Game[0].getCurrplayerindex()).getName());
            stringBuilder.append(" : ");
            stringBuilder.append(gui2Game[0].GetPlayerI(gui2Game[0].getCurrplayerindex()).getPermenShieldCount());
            stringBuilder.append("\n"+gui2Game[0].GetPlayerI(gui2Game[0].getCurrplayerindex()).GetScore().GetScore());
            PlayersInfo.setText(stringBuilder.toString());

            String stri = gui2Game[0].GetPlayerI(gui2Game[0].getCurrplayerindex()).GetColor().GetColor().toString();
            System.out.println(stri);
            stri = stri.substring(2,stri.length());
            PlayersInfo.setStyle("-fx-text-fill :#" + stri);


        });

        prevmove.setOnMouseClicked(event1 -> {
            nextmove.setDisable(false);
            currmove[0]--;
            int end = currmove[0];
            PlayerMove list[] = new PlayerMove[1000];
            int i = -1 ;
            do{
                i++;
                list[i] = movelist[currmove[0]];
                currmove[0]--;
            }while(list[i].getType().GetType().equals("Flood Reveal"));

            currmove[0]++;
            int begin = currmove[0];

            gui2Game[0].unDoMove(list,lastPlayer[0],begin,end);

            //String nn = movelist[currmove[0]].getPlayer().getName();
            //gui2Game[0].setCurrPlayer(gui2Game[0].GetPlayerI(gui2Game[0].getPlayerindex(nn)),gui2Game[0].getPlayerindex(nn));

            gui2Game[0].SwitchPlayer();

            if(currmove[0] <= 0)
            {
                currmove[0] = 0;
                gui2Game[0].setCurrPlayer(gui2Game[0].GetPlayerI(0) , 0);
                prevmove.setDisable(true);
            }
            StringBuilder string = new StringBuilder();
            string.append("Game Shields :");
            string.append(gui2Game[0].getGrid().getShieldsCount());
            GameShields.setText(string.toString());

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(gui2Game[0].GetPlayerI(gui2Game[0].getCurrplayerindex()).getName());
            stringBuilder.append(" : ");
            stringBuilder.append(gui2Game[0].GetPlayerI(gui2Game[0].getCurrplayerindex()).getPermenShieldCount());
            stringBuilder.append("\n"+gui2Game[0].GetPlayerI(gui2Game[0].getCurrplayerindex()).GetScore().GetScore());
            PlayersInfo.setText(stringBuilder.toString());

            String stri = gui2Game[0].GetPlayerI(gui2Game[0].getCurrplayerindex()).GetColor().GetColor().toString();
            System.out.println(stri);
            stri = stri.substring(2,stri.length());
            PlayersInfo.setStyle("-fx-text-fill :#" + stri);
        });
         StringBuilder string = new StringBuilder();
         string.append("Game Shields :");
         string.append(gui2Game[0].getGrid().getPermenShieldCount());
         GameShields.setText(string.toString());



        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(gui2Game[0].GetPlayerI(0).getName());
        stringBuilder.append(" : ");
        stringBuilder.append(gui2Game[0].GetPlayerI(0).getPermenShieldCount());
        stringBuilder.append("\n"+gui2Game[0].GetPlayerI(0).GetScore().GetScore());
        PlayersInfo.setText(stringBuilder.toString());

        String stri = gui2Game[0].GetPlayerI(gui2Game[0].getCurrplayerindex()).GetColor().GetColor().toString();
        System.out.println(stri);
        stri = stri.substring(2,stri.length());
        PlayersInfo.setStyle("-fx-text-fill :#" + stri);

        hBox.getChildren().add(PlayersInfo);
        hBox.getChildren().add(GameShields);


        nextmove.setPrefSize(60,35);
        prevmove.setPrefSize(60,35);

        nextmove.setId("replay");
        prevmove.setId("replay");
        autoplay.setId("replay");
        pause.setId("replay");



         stackPane.getChildren().addAll(gridPane,hBox,nextmove,prevmove);
         gridPane.setAlignment(Pos.CENTER);
         hBox.setAlignment(Pos.CENTER);
         hBox.setTranslateY(-210);
         nextmove.setTranslateY(-210);
         nextmove.setTranslateX(30);
         prevmove.setTranslateY(-210);
         prevmove.setTranslateX(-30);
         autoplay.setTranslateY(180);
         autoplay.setTranslateX(-30);
         pause.setTranslateY(180);
         pause.setTranslateX(30);


        Scene ReplayScene = new Scene(stackPane,1180,580);
        ReplayScene.getStylesheets().add("Replay.css");
        stage.setScene(ReplayScene);
        stage.showAndWait();


    }
}
