package GAMES;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerMove implements Serializable {
   private Player player;
   private Square square;
   private MoveType type;
   private MoveResult result;


   public PlayerMove(PlayerMove move) {
      player = move.player ;
      square = move.square;
      type = move.type;
      result = move.result;

   }
   public PlayerMove()
   {
       square = new Square();
       type = new MoveType();
       result = new MoveResult();
       player = new ConsolePlayer();
   }
   /*public void SetDumyPlayer(DumyPlayer p)
   {
       this.player = new DumyPlayer(p);
   }*/

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
       this.player = player;
    }

    /*public void setGPlayer(GuiPlayer player) {
       this.player = player;
    }*/
    public Square getSquare() {
        return square;
    }

    public void setSquare(int x,int y) {
        this.square.setX(x);
        this.square.setY(y);
    }
    public void setSquare(Square square)
    {
        this.square = square;
    }
    public MoveType getType() {
        return type;
    }

    public void setType(MoveType type) {
        this.type = type;
    }
    public MoveResult GetMoveResult()
    {
        return this.result;
    }
    public void SetMoveResult(MoveResult m)
    {
        this.result = m;
    }


}



