package GAMES;

import java.io.Serializable;

public class SquareStatus implements Serializable {
    private String Status ;
    public SquareStatus()
    {
        Status = "Closed";
    }
    public String GetSquareStatus() {
        return Status;
    }

    public void SetSquareStatus(String status) {
        Status = status;
    }
}
