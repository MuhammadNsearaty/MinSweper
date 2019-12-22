package GAMES;

import java.io.Serializable;

public class Mine implements Serializable {
    private String id;
    public Mine()
    {
        this.id = "Null";
    }
    public void setmine()
    {
        id="Mine";
    }
    public String getmine()
    {
        return id;
    }
}

