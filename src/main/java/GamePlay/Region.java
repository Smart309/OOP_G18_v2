package GamePlay;


import java.nio.file.Path;
import java.nio.file.Paths;

public class Region {
    private final int[] position = new int[2];
    private double deposit = 0.0;
    private double interest ;
    private double r ;
    private Player playerOwner ;
    private boolean isCityCenter;
    Path path = Paths.get("src/configuration file.txt");
    public Region( int posRow , int posCol){
        Configuration.setConfig(path);
        r = (double) Configuration.getInterest_pct();
        this.position[0] = posRow;
        this.position[1] = posCol;
    }
    public int getRowOfRegion(){return position[0];}
    public int getColOfRegion(){return position[1];}
    public void setPlayerOwner(Player owner){
        playerOwner = owner;
    }
    public void removePlayerOwner(){
        playerOwner = null;
    }
    public Player getPlayerOwner(){
        return playerOwner;
    }
    public long getDeposit(){
        if( playerOwner == null ){
            return (long) -deposit;
        }
        return (long) deposit;
    }
    public void updateDeposit(Long amount){
        if( deposit + amount < Configuration.getMax_dep() ){
            deposit += amount;
        }else{
            deposit = Configuration.getMax_dep();
        }

    }
    public void updateInterest(){
        interest = (deposit*r)/100.0;
    }
    public long getInterest(){
        return (long) interest;
    }
    public boolean isCityCenter(){
        return this.isCityCenter;
    }
    public void setCityCenter(Player player){
        playerOwner = player;
        isCityCenter = true;
    }

    public void changeCityCenter(){
        isCityCenter = false;
    }
    public void updateInterestPct()
    {
        r = Configuration.getInterest_pct()*Math.log10(deposit)*Math.log(playerOwner.getTurn());
    }
    public void centerCityLose(){
        if( playerOwner != null ){
            playerOwner.isLoser();
        }
    }

}