package Gui2;

import GAMES.*;
import com.jfoenix.controls.*;
import com.sun.javaws.util.JfxHelper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.w3c.dom.events.EventException;
import sun.security.provider.ConfigFile;

import java.awt.*;
import java.awt.TextArea;
import java.io.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class Gui2Main extends Application  {
    public static void main(String[] args) {
          launch(args);
    }
    Gui2Game game = new Gui2Game();
    GridPane Pane = new GridPane();
    int n[] = new int[7];
    TableView<ScoreBoardELements> scoreboard = new TableView();
    @Override
    public void start(Stage primaryStage) throws Exception {

        //primaryStage.initModality(Modality.APPLICATION_MODAL);
        n[0]=1;n[1]=0;//Default Values



       /* ArrayList<Imp2Save> arrayList1 = new ArrayList<>();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("End Games"));
        objectOutputStream.writeObject(arrayList1);
        objectOutputStream.flush();
        objectOutputStream.close();*/


        /*ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("End Games"));
        objectOutputStream.writeObject(new ArrayList<Imp2Save>());
        objectOutputStream.flush();
        objectOutputStream.close();*/


        //واجهة اللعبة الجديدة
        JFXButton NewGame = new JFXButton("New Game"), Option = new JFXButton("Option"),Exit = new JFXButton("Exit");
        // واجهة اختيار عدد الاعبين
        JFXButton SinglePlayer = new JFXButton("Single Player") , MultiPlayer = new JFXButton("MultiPlayer") ,HumanvsPC = new JFXButton("Human VS Pc"), Back1 = new JFXButton("Back");
        //واجهة اختيار الصعوبة او ابعاد الرقعة
        JFXButton Easy = new JFXButton("Easy"),Medium = new JFXButton("Medium") , Hard = new JFXButton("Hard"),Custom = new JFXButton("Custom") , Back2 = new JFXButton("Back");

        JFXButton LoadGame = new JFXButton("Load Game");
        JFXButton ScoreBoard = new JFXButton("Score Board");

        StackPane StPane1 = new StackPane(),StPane2 = new StackPane(),StPane3 = new StackPane();
        VBox ButtonsBox1 = new VBox(20) ,ButtonsBox2 = new VBox(20),ButtonsBox3 = new VBox(20);

        ButtonsBox1.getChildren().addAll(NewGame,LoadGame,ScoreBoard,Option,Exit);
        ButtonsBox1.setAlignment(Pos.CENTER);
        StPane1.getChildren().add(ButtonsBox1);
        primaryStage.setResizable(false);
        Scene BeginScene = new Scene(StPane1,1180,580);
        BeginScene.getStylesheets().add("ButtonsHover.css");
        

        ButtonsBox2.getChildren().addAll(Easy,Medium,Hard,Custom,Back1);
        ButtonsBox2.setAlignment(Pos.CENTER);
        StPane2.getChildren().add(ButtonsBox2);
        Scene NewGameScene = new Scene(StPane2,1180,580);
        NewGameScene.getStylesheets().add("ButtonsHover.css");

        ButtonsBox3.getChildren().addAll(SinglePlayer,MultiPlayer,HumanvsPC,Back2);
        ButtonsBox3.setAlignment(Pos.CENTER);
        StPane3.getChildren().add(ButtonsBox3);
        Scene playerCountScene = new Scene(StPane3,1180,580);
        playerCountScene.getStylesheets().add("ButtonsHover.css");

        Pane.setAlignment(Pos.CENTER);
        Pane.setVgap(1);
        Pane.setHgap(1);



        NewGame.setOnMouseClicked(EVENT -> {
            primaryStage.setScene(NewGameScene);
        });
        LoadGame.setOnMouseClicked(EVENT -> {
            StackPane stackPane = new StackPane();
            JFXListView<Label> listView = new JFXListView<>();

            ObjectInputStream objectintputstream = null;

            try {
                objectintputstream = new ObjectInputStream(new FileInputStream("Paths"));
                final ArrayList<String> arrayList = (ArrayList<String>) objectintputstream.readObject();
                objectintputstream.close();
                for(String str : arrayList)
                    System.out.println(str);
                int i = 1;
                for(String path : arrayList) {
                Label label = new Label();
                label.setText(i+" - "+path);
                listView.getItems().add(label);
                i++;
            }

            JFXButton click = new JFXButton("Load");
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            VBox vBox = new VBox(20);
            vBox.getChildren().add(listView);
            vBox.getChildren().add(click);
            vBox.setAlignment(Pos.TOP_CENTER);
            stackPane.getChildren().add(vBox);

            Scene scene = new Scene(stackPane,700,400);
            stage.setScene(scene);

            click.setOnMouseClicked(event ->
            {
                stage.close();
                String ChosenGame = null;
                Label label = listView.getSelectionModel().getSelectedItem();
                    ChosenGame = label.getText();
                    StringBuilder stringBuilder = new StringBuilder();
                    int ch = 0;
                    while (true) {
                        stringBuilder.append(ChosenGame.charAt(ch));
                        ch++;
                        if (ChosenGame.charAt(ch) == ' ')
                            break;
                    }
                    int gameid = Integer.parseInt(stringBuilder.toString()) - 1;
                    try {
                        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(arrayList.get(gameid)));
                        Imp2Save imp2Save = (Imp2Save) objectInputStream.readObject();
                        Gui2Game gui2Game = imp2Save.Load();
                        PlayerMove[] tmplist = gui2Game.getMovesList();
                        int tmpcount = gui2Game.getCurrCount();
                        for(int j = 0 ; j <  tmpcount; j++)
                            tmplist[j] = gui2Game.getMoveI(j);
                        gui2Game.setMovesList();
                        gui2Game.setBt();
                        objectInputStream.close();
                        for(int pl = 0 ; pl < gui2Game.getPlayersCount();pl++)
                        {
                            gui2Game.setPlayerIscore(pl);
                        }
                        gui2Game.setCurrCount(0);
                        int index = 0 ;
                        for(int k = 0 ; k < tmpcount ;k ++)
                            {
                                if(tmplist[k].getType().GetType().equals("Flood Reveal"))
                                    continue;
                                else {
                                    int begin = gui2Game.getCurrCount();
                                    gui2Game.setCurrPlayer(gui2Game.GetPlayerI(index), index);
                                    gui2Game.DoTheMove(tmplist[k]);
                                    gui2Game.UpdateScene(index, begin, tmplist[k].GetMoveResult());
                                    index++;
                                    index = (index % gui2Game.getPlayersCount());
                                }
                            }
                        index = (index % gui2Game.getPlayersCount());
                        gui2Game.setCurrPlayer(gui2Game.GetPlayerI(index), index);


                        GridPane gridPane = new GridPane();
                        for(int l = 0 ; l < gui2Game.getGrid().getN();l++)
                            for(int k = 0 ;k < gui2Game.getGrid().getM();k++)
                            {
                                gridPane.add(gui2Game.bt[l][k],k,l);
                            }
                        if(gui2Game.getPlayersCount() == 1)
                            doSingle(primaryStage,gui2Game,true);
                        else
                        {
                            if(gui2Game.GetPlayerI(1) instanceof DumyPlayer)
                            {
                                gui2Game.lock = new Object();
                                gui2Game.timeline = new Timeline(
                                        new KeyFrame(Duration.ZERO,new KeyValue( gui2Game.timespinner.progressProperty(),0)),
                                        new KeyFrame(Duration.seconds( gui2Game.timelimit),new KeyValue( gui2Game.timespinner.progressProperty(),1))
                                );
                                gui2Game.timeline.setCycleCount(1);
                                gui2Game.playersThreads = new Gui2Game.PlayersThreads[gui2Game.getPlayersCount()];
                                gui2Game.playersThreads[0] =  gui2Game.new PlayersThreads(gui2Game.GetPlayerI(0),0,gui2Game.getLock());
                                gui2Game.pcThread = gui2Game.new PCThread((ComPlayer) gui2Game.GetPlayerI(1),1,gui2Game.getLock());
                                doPC(primaryStage,gui2Game,true);

                            }
                            else
                            {
                                gui2Game.lock = new Object();
                                gui2Game.timeline = new Timeline(
                                        new KeyFrame(Duration.ZERO,new KeyValue( gui2Game.timespinner.progressProperty(),0)),
                                        new KeyFrame(Duration.seconds( gui2Game.timelimit),new KeyValue( gui2Game.timespinner.progressProperty(),1))
                                );
                                gui2Game.timeline.setCycleCount(1);
                                gui2Game.playersThreads = new Gui2Game.PlayersThreads[gui2Game.getPlayersCount()];
                                for(int ii =0 ;ii<gui2Game.getPlayersCount() ;ii++)
                                    gui2Game.playersThreads[ii] =  gui2Game.new PlayersThreads(gui2Game.GetPlayerI(ii),ii,gui2Game.getLock());
                                doMulti(primaryStage,gui2Game,true);
                            }
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
            });
            stage.showAndWait();


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ScoreBoard.setOnMouseClicked(EVENT -> {
            try {
                Stage stage = new Stage();
                stage.setTitle("Score Board");
                stage.initModality(Modality.APPLICATION_MODAL);
                //Id Column
                TableColumn<ScoreBoardELements,Integer> Idcolumn = new TableColumn<>("ID");
                Idcolumn.setMinWidth(100);
                Idcolumn.setCellValueFactory(new PropertyValueFactory<ScoreBoardELements,Integer>("Id"));
                //The highest Score Column
                TableColumn<ScoreBoardELements , Integer> HighestScoreColumn = new TableColumn<>("Highest Scores");
                HighestScoreColumn.setMinWidth(150);
                HighestScoreColumn.setCellValueFactory(new PropertyValueFactory<ScoreBoardELements, Integer>("highestScore"));
                //Player names Column
                TableColumn<ScoreBoardELements,String> PlayersNamesColumn = new TableColumn<>("Players names");
                PlayersNamesColumn.setMinWidth(200);
                PlayersNamesColumn.setCellValueFactory(new PropertyValueFactory<ScoreBoardELements,String>("playerNames"));
                //Highest Player Score And Name
                TableColumn<ScoreBoardELements , String> HighestPlayerColumn = new TableColumn<>("The Winner");
                HighestPlayerColumn.setMinWidth(200);
                HighestPlayerColumn.setCellValueFactory(new PropertyValueFactory<ScoreBoardELements,String>("highestPlayerScoreAndName"));
                //Time Column
                TableColumn<ScoreBoardELements ,String> TimeColumn = new TableColumn<>("Game Time");
                TimeColumn.setMinWidth(150);
                TimeColumn.setCellValueFactory(new PropertyValueFactory<ScoreBoardELements,String>("time"));
                //buttons column
                TableColumn<ScoreBoardELements,JFXButton> ButtonColumn  = new TableColumn<>("Replay");
                ButtonColumn.setMinWidth(65);
                ButtonColumn.setCellValueFactory(new PropertyValueFactory<ScoreBoardELements,JFXButton>("replay"));

                scoreboard = new TableView<>();
                scoreboard.setItems(getGames());
                scoreboard.getColumns().addAll(Idcolumn,HighestScoreColumn,PlayersNamesColumn,HighestPlayerColumn,TimeColumn,ButtonColumn);

                VBox vBox = new VBox();
                vBox.getChildren().add(scoreboard);

                Scene scoreboardscene = new Scene(vBox,860,375);
                stage.setScene(scoreboardscene);

                scoreboardscene.getStylesheets().add("ScoreBoard.css");

                stage.setResizable(false);
                stage.showAndWait();


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        Option.setOnMouseClicked(EVENT ->{
            Stage stage = new Stage();
            stage.setTitle("Option");
            stage.initModality(Modality.APPLICATION_MODAL);
            Spinner<Integer> FlagOnMine = new Spinner<Integer>(1,100,1,1),FlagNoMine = new Spinner<Integer>(-100,-1,-1,-1),EmptySquare= new Spinner<Integer>(1,100,1,1),FloodFillOpen = new Spinner<Integer>(1,100,1,1);
            JFXButton Done = new JFXButton("Done");
            Label FlagOnMinelabel = new Label("Put a Flag On a Mine");
            Label FlagNoMinelabel = new Label("Put a Flag On Empty Square");
            Label EmptySquarelabel = new Label("Open an Empty Square");
            Label FloodFillOpenlabel = new Label("Open With FloodFill");
            Label Timelimitlabel = new Label("Choose The Turn period");
            Spinner<Integer> Timelimit = new Spinner<>(7,100,7,1);
            EditRuleGame edit = new EditRuleGame();
            int Values[] = new int[10];

            GridPane gridPane = new GridPane();
            gridPane.setVgap(20);
            gridPane.setHgap(20);
            stage.setOnCloseRequest(e ->{
                e.consume();
                boolean res = popUpClose();
                if(res)
                    stage.close();
            });

            Done.setOnAction(event ->{
                EditRuleGame.EditRules rules = edit.new EditRules();
                Values[0] = FlagOnMine.getValue();
                Values[1] = FlagNoMine.getValue();
                Values[2] = EmptySquare.getValue();
                Values[3] = FloodFillOpen.getValue();
                game.setTimelimit(Timelimit.getValue());
                rules.SetValues(Values);
                game.setRules(rules);
               stage.close();
            });
            gridPane.add(FlagOnMine,1,0);
            gridPane.add(FlagNoMine,1,1);
            gridPane.add(EmptySquare,1,2);
            gridPane.add(FloodFillOpen,1,3);
            gridPane.add(Timelimit,1,4);
            gridPane.add(Done,1,5);

            gridPane.add(FlagOnMinelabel,0,0);
            gridPane.add(FlagNoMinelabel,0,1);
            gridPane.add(EmptySquarelabel,0,2);
            gridPane.add(FloodFillOpenlabel,0,3);
            gridPane.add(Timelimitlabel,0,4);

            Scene scene = new Scene(gridPane,450,350);
            gridPane.setAlignment(Pos.CENTER);
            scene.getStylesheets().add("SpinnerSheet.css");
            stage.setScene(scene);
            stage.showAndWait();


        });
        Exit.setOnMouseClicked(EVENT ->
        {
           Boolean res = popUpClose();
           if(res)
               primaryStage.close();
        });
        Easy.setOnMouseClicked(EVENT ->
        {
          n[2] = 1;
          primaryStage.setScene(playerCountScene);
       });
        Medium.setOnMouseClicked(EVENT ->
        {
           n[2] = 2;
           primaryStage.setScene(playerCountScene);
       });
        Hard.setOnMouseClicked(EVENT ->
        {
            n[2] = 3;
            primaryStage.setScene(playerCountScene);
        });
        Custom.setOnMouseClicked(EVENT ->
        {
           int CustomSizes[] = CustomSize();
            n[2] = 4;
            n[3] = CustomSizes[0];
            n[4] = CustomSizes[1];
            n[5] = CustomSizes[2];
            n[6] = 2;
            primaryStage.setScene(playerCountScene);
        });
        Back1.setOnMouseClicked(EVENT -> primaryStage.setScene(BeginScene));

        SinglePlayer.setOnMouseClicked(EVENT ->
        {
            doSingle(primaryStage,game,false);
        });
        MultiPlayer.setOnMouseClicked(EVENT ->
        {
            game.setCurrStage(primaryStage);
            doMulti(primaryStage,game,false);
        });
        HumanvsPC.setOnMouseClicked(EVENT ->
        {
            game.setCurrStage(primaryStage);
            doPC(primaryStage,game,false);
        });
        Back2.setOnMouseClicked(EVENT -> primaryStage.setScene(NewGameScene));
        primaryStage.setScene(BeginScene);
        primaryStage.show();

    }



public void doSingle(Stage primaryStage , Gui2Game game,boolean loaded)
{

    if(!loaded) {
        n[0] =1;
        n[1] =0;
        game.SetGame(n,game.Rules);
        game.SetPlayers(n);
    }

    System.out.println(game.getGrid().getPermenShieldCount());
    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    Date date = new Date();
    game.BeginTime = format.format(date);

    System.out.println(game.BeginTime);

    JFXButton SaveExit = new JFXButton("Save And Exit");
    Label sheildscount = new Label();
    Label PlayerShields = new Label();
    boolean res[] = new boolean[1];
    int x2 = game.getGrid().getShieldsCount();
    StringBuilder s1 = new StringBuilder();
    s1.append(x2);
    sheildscount.setText("Shields Count :" + s1.toString());
    PlayerShields.setText(game.GetPlayerI(0).getName() + " Shields "+ game.GetPlayerI(0).getShieldsCount());
    for(int i=0;i<game.getGrid().getN();i++)
        for(int j= 0; j< game.getGrid().getM();j++)
        {
            game.bt[i][j].setPrefSize(35,35);
            final int x = i , y = j;
            game.bt[i][j].setOnMouseClicked(event ->
            {
                MouseButton click = event.getButton();
                MoveType type = new MoveType();
                if(click == MouseButton.SECONDARY)
                    type.SetNewType("Mark");
                else
                    type.SetNewType("Reveal");
                PlayerMove move = game.getCurrPlayer().GetPlayerMove(game.getGrid().GetSquares()[x][y]);
                move.setType(type);
                if(game.AcceptMove(move))
                {
                    int begin = game.getCurrCount();
                    game.DoTheMove(move);
                    MoveResult moveResult = move.GetMoveResult();

                    try {
                        res[0] = game.UpdateScene(0,begin,moveResult);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    int x1 = game.getGrid().getShieldsCount();
                    StringBuilder s12 = new StringBuilder();
                    s12.append(x1);
                    sheildscount.setText("Shields Count :" + s12.toString());
                    int x3 = game.GetPlayerI(0).getShieldsCount();
                    StringBuilder s123 = new StringBuilder();
                    s123.append(x3);
                    PlayerShields.setText(game.GetPlayerI(0).getName() + " Shields "+ s123.toString());
                    if(res[0])
                    {
                        SimpleDateFormat endformat = new SimpleDateFormat("HH:mm:ss");
                        Date enddate = new Date();
                        game.EndTime = endformat.format(enddate).toString();
                        try {
                            SaveOnGameEnd(game);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        primaryStage.close();
                    }
                }
            });
            Pane.add(game.bt[i][j],j,i);
        }


    SaveExit.setStyle("-fx-background-color :aliceblue;-fx-background-image :none");

    SaveExit.setOnMouseClicked(event ->
    {
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");
        Date date1 = new Date();
        game.EndTime = format1.format(date1);

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(primaryStage);
        Imp2Save imp2Save = new Imp2Save(game);
        try {
            game.Write2File(imp2Save,file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        primaryStage.close();

    });
    StackPane stackPane = new StackPane();
    //Pane.setAlignment(Pos.CENTER);
    Pane.setTranslateY(50);
    stackPane.getChildren().add(sheildscount);
    stackPane.getChildren().add(Pane);
    sheildscount.setTranslateY(-215);
    sheildscount.setTranslateX(235);

    stackPane.getChildren().add(PlayerShields);
    PlayerShields.setTranslateY(-215);
    PlayerShields.setTranslateX(-190);

    stackPane.getChildren().add(SaveExit);
    SaveExit.setTranslateX(-400);
    SaveExit.setTranslateY(-170);


    Scene PlayScene = new Scene(stackPane,1180,580);
    PlayScene.getStylesheets().add("PlaySene.css");


    primaryStage.setScene(PlayScene);
}



public void doMulti(Stage primaryStage , Gui2Game game ,boolean loaded)
{
    JFXButton SaveExit = new JFXButton("Save And Exit");
    if(!loaded){
    n[0] = ChosePlayersCount(game.getGrid().getN() * game.getGrid().getM());
    n[1] = 0;
    game.SetGame(n,game.Rules);
    game.SetPlayers(n);
    }

    for(int i = 0 ;i< game.getGrid().getN() ;i++)
        for(int j =0; j<game.getGrid().getM();j++)
        {
            game.bt[i][j].setPrefSize(35,35);
            Pane.add(game.bt[i][j],j,i);
        }
    StackPane stackPane = new StackPane();
    SaveExit.setOnMouseClicked(event ->
    {
        game.timer.cancel();
        game.timeline.stop();

        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");
        Date date1 = new Date();
        game.EndTime = format1.format(date1);

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(primaryStage);
        Imp2Save imp2Save = new Imp2Save(game);
        try {
            game.Write2File(imp2Save,file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        primaryStage.close();

    });

    stackPane.getChildren().add(Pane);
    game.name.setText(game.GetPlayerI(game.getCurrplayerindex()).getName() + " Turn\nPlayer Shields: " + game.GetPlayerI(game.getCurrplayerindex()).getShieldsCount());
    game.name.setStyle("-fx-text-fill: aliceblue;-fx-font-weight: bold;-fx-font-size: 20px;-fx-font-family: Eras ITC");
    stackPane.getChildren().add(game.name);
    game.name.setTranslateY(-215);
    game.name.setTranslateX(-190);

    game.gameshieldsLabel.setText("Game Remaining Shields" +game.getGrid().getShieldsCount());
    game.gameshieldsLabel.setStyle("-fx-text-fill: aliceblue;-fx-font-weight: bold;-fx-font-size: 20px;-fx-font-family: Eras ITC");
    stackPane.getChildren().add(game.gameshieldsLabel);
    game.gameshieldsLabel.setTranslateX(190);
    game.gameshieldsLabel.setTranslateY(-215);

    stackPane.getChildren().add(game.timespinner);
    game.timespinner.setTranslateY(-220);
    game.timeline.setOnFinished(e -> game.swipnamelabel(game.name));
    Pane.setTranslateY(50);

    SaveExit.setStyle("-fx-background-color :aliceblue;-fx-background-image :none");
    stackPane.getChildren().add(SaveExit);
    SaveExit.setTranslateX(-400);
    SaveExit.setTranslateY(-170);


    Scene PlayScene = new Scene(stackPane,1180,580);
    PlayScene.getStylesheets().add("PlaySene.css");
    primaryStage.setScene(PlayScene);

    game.setCurrPlayer(game.GetPlayerI(0),0);
    System.out.println(game.GetCurrPlayer().getName());

    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    Date date = new Date();
    game.BeginTime = format.format(date);

    for (int z = 0; z < game.getPlayersCount(); z++) {
        game.playersThreads[z].start();
    }
    game.timeline.setOnFinished(this::handle);
    game.timeline.setCycleCount(1);
    game.timer.schedule(game.time,0, (game.timelimit*1000));

}


public void doPC(Stage primaryStage , Gui2Game game ,boolean loaded)
{
    JFXButton SaveExit = new JFXButton("Save And Exit");
    if(!loaded) {
        n[0] = 1;
        n[1] = 1;
        game.SetGame(n, game.Rules);
        game.SetPlayers(n);
    }
    for(int i = 0 ;i< game.getGrid().getN() ;i++)
        for(int j =0; j<game.getGrid().getM();j++)
        {
            game.bt[i][j].setPrefSize(35,35);
            Pane.add(game.bt[i][j],j,i);
        }
    StackPane stackPane = new StackPane();
    stackPane.getChildren().add(Pane);
    game.name1 = new String(game.GetPlayerI(0).getName() +" Turn\nPlayer Shields: "+game.GetPlayerI(0).getShieldsCount() );
    game.name.setText(game.name1);
    game.name.setStyle("-fx-text-fill: aliceblue;-fx-font-weight: bold;-fx-font-size: 20px;-fx-font-family: Eras ITC");
    stackPane.getChildren().add(game.name);
    game.name.setTranslateY(-210);
    game.name.setTranslateX(-200);

    SaveExit.setStyle("-fx-background-color :aliceblue;-fx-background-image :none");
    stackPane.getChildren().add(SaveExit);
    SaveExit.setTranslateX(-400);
    SaveExit.setTranslateY(-170);

    stackPane.getChildren().add(game.timespinner);
    game.timespinner.setTranslateY(-220);
    Scene PlayScene = new Scene(stackPane,1180,580);
    PlayScene.getStylesheets().add("PlaySene.css");
    primaryStage.setScene(PlayScene);

    SaveExit.setOnMouseClicked(event -> {
        if(game.getCurrplayerindex() == 0) {
            game.timer.cancel();
            game.timeline.stop();

            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(primaryStage);
            SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");
            Date date1 = new Date();
            game.EndTime = format1.format(date1);

            Imp2Save imp2Save = new Imp2Save(game);
            try {
                game.Write2File(imp2Save, file.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            primaryStage.close();
        }
    });
    game.setCurrPlayer(game.GetPlayerI(game.getCurrplayerindex()),game.getCurrplayerindex());

    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    Date date = new Date();
    game.BeginTime = format.format(date);

    game.playersThreads[0].start();
    game.pcThread.start();

    game.timeline.setOnFinished(this::handle);
    game.timeline.setCycleCount(1);
    game.timer.schedule(game.time,0, (game.timelimit*1000));


}

    public void SaveOnGameEnd(Gui2Game endgame) throws IOException, ClassNotFoundException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        game.EndTime = format.format(date);

        Imp2Save imp2Save = new Imp2Save(endgame);
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
    public void handle(Event event) {
        System.out.println(game.currplayerindex);
        game.SwitchPlayer();
        System.out.println(game.currplayerindex);
        game.timer.cancel();
        game.timeline.stop();
        game.timespinner.setRadius(0.0);


        game.time = game.new RunThreads(game.lock);
        game.timer = new Timer();
        game.timeline = new Timeline(
                new KeyFrame(Duration.ZERO,new KeyValue(game.timespinner.progressProperty(),0)),
                new KeyFrame(Duration.seconds(game.timelimit),new KeyValue(game.timespinner.progressProperty(),1)));
        game.timeline.setCycleCount(1);
        game.timeline.setOnFinished(this::handle);
        game.gameshieldsLabel.setText("Game Remaining Shields" +game.getGrid().getShieldsCount());
        if(game.getCurrPlayer().getName() == "PC")
            game.name1 = game.getCurrPlayer().getName() + " Turn\nPc is Thinking";
        else
            game.name1 = game.getCurrPlayer().getName() + " Turn\nPlayer Shields " + game.getCurrPlayer().getShieldsCount();
        game.swipnamelabel(game.name);
        game.timer.schedule(game.time,0, (game.timelimit*1000));
        game.timeline.play();

    }

    public ObservableList<ScoreBoardELements> getGames() throws IOException, ClassNotFoundException {

        ArrayList<Imp2Save> imp2SaveArrayList = new ArrayList<>();
        ObservableList<ScoreBoardELements> observableList = FXCollections.observableArrayList();


        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("End Games"));
        imp2SaveArrayList = (ArrayList<Imp2Save>) inputStream.readObject();
        inputStream.close();
        for(Imp2Save game: imp2SaveArrayList)
        {
            observableList.add(new ScoreBoardELements(game));
        }

        return observableList;
    }





   public Boolean popUpClose()
   {
       final Boolean[] res = new Boolean[1];
       res[0] = false;
       Stage stage = new Stage();
       stage.initModality(Modality.APPLICATION_MODAL);
       HBox gp = new HBox(40);
       StackPane stackPane = new StackPane();
       Label label = new Label("Are You Sure You Want To Exit ?");
       JFXButton yes = new JFXButton("Yes"), no = new JFXButton("No");
       gp.getChildren().add(yes);
       gp.getChildren().add(no );
       yes.setOnMouseClicked(EVENT -> {
           res[0] = true;
           stage.close();
       });
       no.setOnMouseClicked(Event -> {
           res[0] = false;
           stage.close();
       });
       yes.setId("button-yes");
       no.setId("button-no");
       stackPane.getChildren().add(gp);
       gp.setAlignment(Pos.CENTER);
       stackPane.getChildren().add(label);
       label.setTranslateY(-40);
       Scene scene = new Scene(stackPane,200,150);
       scene.getStylesheets().add("CloseRequste.css");
       stage.setScene(scene);

       stage.showAndWait();
       return res[0];
   }
   public int[] CustomSize()
   {
       Stage stage = new Stage();
       stage.initModality(Modality.APPLICATION_MODAL);

       JFXButton Donesize  = new JFXButton("Set Sizes"),Donemines = new JFXButton("Set Mines Count"),Doneshields = new JFXButton("Set Shields Count");
       //Done.setStyle("-fx-background-color: #aaaaaa");
       GridPane gp = new GridPane();
       gp.setPadding(new Insets(10,10,10,10));
       gp.setVgap(20);
       int SizeValue[] = new int[4];
       //SizeValue[0] = SizeValue[1] = SizeValue[2] =5;//Default Values

       Spinner<Integer> Nsize = new Spinner<>(5,26,5,1);
       Spinner<Integer> Msize = new Spinner<>(5,26,5,1);
       Spinner<Integer> MinesCount[] = new Spinner[1];
       Spinner<Integer> ShieldsCount[] = new Spinner[1];

       Label N = new Label("Choose The Row Count ");
       Label M = new Label("Choose The Columns Count");
       Label Mines = new Label("Choose The Mines Average");
       Label Shields = new Label("Choose The Shields Count");

       gp.add(N,0,0); gp.add(M,0,1);
       gp.add(Nsize,1,0);gp.add(Msize,1,1);
       gp.add(Donesize,2,0);

       Donesize.setOnAction(EVENT->
       {
           SizeValue[0] = Nsize.getValue();
           SizeValue[1] = Msize.getValue();
           Nsize.setDisable(true);
           Msize.setDisable(true);
           Donesize.setDisable(true);
           MinesCount[0] = new Spinner<Integer>(1,(SizeValue[0]*SizeValue[1])-1,1,1);
           gp.add(Mines,0,2);
           gp.add(MinesCount[0],1,2);
           gp.add(Donemines,2,2);

       });
       Donemines.setOnAction(EVENT ->
       {
           SizeValue[2] = MinesCount[0].getValue();
           MinesCount[0].setDisable(true);
           Donemines.setDisable(true);
           ShieldsCount[0] = new Spinner<>(0,SizeValue[2]-1,0,1);
           gp.add(Shields,0,3);
           gp.add(ShieldsCount[0],1,3);
           gp.add(Doneshields,2,3);
       });
       Doneshields.setOnAction(EVENT ->
       {
           SizeValue[3] = ShieldsCount[0].getValue();
           stage.close();
       });

       Scene scene = new Scene(gp,400,400);
       scene.getStylesheets().add("SpinnerSheet.css");
       stage.setScene(scene);
       stage.showAndWait();

       return SizeValue;
   }
   public int ChosePlayersCount(int SquaresCount)
   {
       int res[] = new int[1];
       res[0] = 1;
       Stage stage = new Stage();
       stage.initModality(Modality.APPLICATION_MODAL);
       JFXButton Done = new JFXButton("Done");
       Spinner<Integer> spinner = new Spinner<>(1,SquaresCount-1,1,1);
       Label label = new Label("Chose Player Count");
       Done.setOnAction(EVENT ->
       {
           res[0] = spinner.getValue();
           stage.close();
       });
       stage.setOnCloseRequest(e ->
       {
           e.consume();
       });
       HBox box  = new HBox(20);
       box.getChildren().addAll(label,spinner,Done);
       Scene scene = new Scene(box,500,120);
       scene.getStylesheets().add("SpinnerSheet.css");
       stage.setScene(scene);
       stage.setResizable(false);
       stage.showAndWait();
       return res[0];
   }

}











/* for(int i = 0 ; i < game.getGrid().getN();i++)
            for(int j= 0 ; j < game.getGrid().getM();j++)
            {
                StGame.add(game.bt[i][j],j,i);
                game.bt[i][j].setOnMouseClicked(event->
                {
                    MouseButton click = event.getButton();
                    if(click == MouseButton.PRIMARY)
                    else if (click == MouseButton.SECONDARY)



                });
            }
*/


/*
       JFXProgressBar bar = new JFXProgressBar();
       bar.setPrefWidth(500);
       bar.setProgress(1.0f);
       JFXProgressBar bar2 = new JFXProgressBar();
       bar2.setPrefWidth(100);
       bar2.setPrefHeight(30);
       Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO,new KeyValue(bar2.progressProperty(),0),new KeyValue(bar.progressProperty(),0)),
                                        new KeyFrame(Duration.seconds(10),new KeyValue(bar2.progressProperty(),1),new KeyValue(bar.progressProperty(),1)));
       timeline.setCycleCount(Timeline.INDEFINITE);
       timeline.play();*/