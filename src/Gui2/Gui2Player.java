package Gui2;
import GAMES.MoveType;
import GAMES.Square;
import Gui2.GuiColor;
import com.jfoenix.controls.JFXColorPicker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import GAMES.Player;
import GAMES.PlayerMove;

import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.jfoenix.controls.*;

import java.util.concurrent.SynchronousQueue;

import static javafx.scene.paint.Color.*;

public class Gui2Player extends Player {
    static int num =1;
    public void setGuiColor(Color guiColor) {
        color.SetColor(guiColor);
    }
    public Color GetGuiColor()
    {
        return (Color)color.GetColor();
    }

    public Gui2Player()
    {
        super();
        color = new GuiColor();
    }


    @Override
    public PlayerMove GetPlayerMove(Object obj) {
        Square obj1 = (Square)obj;
        PlayerMove  move = new PlayerMove();
        move.setSquare(obj1);
        move.setPlayer(this);
        return move;
    }
    public void GetPlayerInfo(Object obj)
    {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Getting Players Info ");
        GridPane gp = new GridPane();
        gp.setVgap(10);
        JFXButton Done = new JFXButton("Done");
        Spinner<Integer> spinner = new Spinner<Integer>(1,(int)obj-1,1,1);
        gp.setPadding(new Insets(10,10,10,10));
        Label label = new Label("Choose Player Color:");
        Label shieldlabel = new Label("Choose Shields Count: ");
        JFXTextField field = new JFXTextField();
        field.setPromptText("Enter Name here");

        final JFXColorPicker pick = new JFXColorPicker();
        pick.setValue(Color.WHITE);
        pick.setPrefHeight(10);
        gp.add(field,0,0);
        gp.add(label,0,2);
        gp.add(pick,0,4);
        gp.add(Done,0,10);
        gp.add(spinner,0,8);
        gp.add(shieldlabel,0,6);
        Done.setOnAction(event -> {
            this.SheildsCount = spinner.getValue();
            this.permenShieldCount = spinner.getValue();
            stage.close();
        });
        Done.wrapTextProperty();
        pick.setOnAction(Event -> {
            this.setGuiColor(pick.getValue());
            String str1 = pick.getValue().toString();
            str1 = str1.substring(2,str1.length());
            Done.setStyle("-fx-background-color: #"+ str1+";-fx-font-weight: bold");
        });
        stage.setOnCloseRequest(e->{
            e.consume();
        });
        Scene scene = new Scene(gp,400,300);
        scene.getStylesheets().add("SpinnerSheet.css");
        stage.setScene(scene);
        stage.showAndWait();
        String str = new String();
        str = field.getText();

        if ( !(str.isEmpty()))
            this.SetName(str);
        else
        {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Player ");
            stringBuilder.append(num);
            this.SetName(stringBuilder.toString());
            num++;
        }


    }


}




