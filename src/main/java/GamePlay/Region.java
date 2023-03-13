package GamePlay;


import java.util.Map;

public class Region {
    private Long maxDeposit;
    private double deposit;
    private double r;
    private Player playerOwner = null;

    public Region( Map<String,Long> var ){
        maxDeposit = var.get("max_dep");
        r = var.get("interest_pct");
        this.deposit = 0L;
    }
    public long getDeposit(){
        return (long) deposit;
    }

    public void setPlayerOwner(Player owner){
        playerOwner = owner;
    }

}