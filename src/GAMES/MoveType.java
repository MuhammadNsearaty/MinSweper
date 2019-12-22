package GAMES;
import java.io.Serializable;
import java.util.Scanner;
public class MoveType implements Serializable {
    private String type;
    public MoveType(String s)
    {
        this.type = s;
    }
    public MoveType()
    {
        this.type = "Reveal";
    }
      public  void SetNewType(String  t)
      {
          this.type = t;
      }
      public String GetType()
      {
          return type;
      }
}
