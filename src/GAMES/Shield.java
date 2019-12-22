package GAMES;

import java.io.Serializable;

public class Shield implements Serializable {

   private boolean shield;


   public Shield()
   {
       shield = false;
   }
    public boolean isShield() {
        return shield;
    }
    public void DisableShield()
    {
        this.shield = false;
    }
    public void EnableShield()
    {
        this.shield = true;
    }
}
