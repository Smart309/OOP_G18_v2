package GamePlay;

import Error.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class GamePlay{
    private Player p1;
    private Player p2;
    private static Region[][] territory;
    private Player currentTurn;
    private Player winner;
    Path path = Paths.get("src/configuration file.txt");
    public GamePlay( Player p1 , Player p2){
        Configuration.setConfig(path);
        int m = (int) Configuration.getM();
        int n = (int) Configuration.getN();
        Territory createMap = new Territory( m,n );
        territory = createMap.getTerritory();
        this.p1 = p1;
        this.p2 = p2;
        int cityCenterRowP1 = (int)(Math.random()*(Configuration.getM()));
        int cityCenterColP1 = (int)(Math.random()*(Configuration.getN()));
        int cityCenterRowP2 = (int)(Math.random()*(Configuration.getM()));
        int cityCenterColP2 = (int)(Math.random()*(Configuration.getN()));

        while( cityCenterRowP2 == cityCenterRowP1 && cityCenterColP2 == cityCenterColP1 ){
            cityCenterRowP2 = (int)(Math.random()*(Configuration.getM()));
            cityCenterColP2 = (int)(Math.random()*(Configuration.getN()));
        }

        p1.setCityCenter( cityCenterRowP1,cityCenterColP1 );
        p2.setCityCenter( cityCenterRowP2,cityCenterColP2 );
        int rowP1 = p1.getCityCenterRow();
        int colP1 = p1.getCityCenterCol();
        int rowP2 = p2.getCityCenterRow();
        int colP2 = p2.getCityCenterCol();
        territory[rowP1][colP1].setCityCenter( p1 );
        territory[rowP1][colP1].updateDeposit( Configuration.getInit_center_dep() );
        territory[rowP2][colP2].setCityCenter( p2 );
        territory[rowP2][colP2].updateDeposit( Configuration.getInit_center_dep() );

        this.currentTurn = p1;
    }
    public static Region getRegion(int m ,int n ){
        return territory[m][n];
    }
    public void doPlan() throws EvalError{
//        if( currentTurn.equals( p1 ) ){
//            System.out.print("Action "+ p1.getName() +" :");
//        }else{
//            System.out.print("Action "+ p2.getName() +" :");
//        }
        if( currentTurn.equals( p1 ) ){
            p1.doPlan();
        }else{
            p2.doPlan();
        }

        if( currentTurn.equals( p1 ) ){
            currentTurn = p2;
        }else{
            currentTurn = p1;
        }

        if( p1.checkLose() ){
            winner = p2;
        }else if( p2.checkLose() ){
            winner = p1;
        }

        for (int i = 0; i < Configuration.getM(); i++)
        {
            for (int j = 0; j < Configuration.getN(); j++)
            {
                Region region = getRegion(i, j);
                if(region.getDeposit() == 0 || region.getDeposit() == Configuration.getMax_dep() || region.getPlayerOwner() == null)
                {
                    continue;
                }
                region.updateInterestPct();
                region.updateInterest();
                region.updateDeposit(region.getInterest());
            }
        }
        showStatus();
    }
    public void showStatus(){
        System.out.println("Name: " + p1.getName() + "           ||     " + "Name: " + p2.getName());
        System.out.println("Turn: " + p1.getTurn() + "           ||     " + "Turn: " + p2.getTurn());
        System.out.println("City Center: (" + (p1.getCityCenterRow()+1) + "," + (p1.getCityCenterCol()+1) + ")"+ "    ||     " + "City Center: (" + (p2.getCityCenterRow()+1) + "," + (p2.getCityCenterCol()+1) + ")");
        System.out.println("City Crew: (" + (p1.getCityCrewRow()+1) + "," + (p1.getCityCrewCol()+1) + ")"+ "    ||     " + "City Crew: (" + (p2.getCityCrewRow()+1) + "," + (p2.getCityCrewCol()+1) + ")");
        System.out.println("Budget: " + p1.getBudget() + "           ||     " + "Budget: " + p2.getBudget());
        System.out.println("Deposit: " + territory[p1.getCityCrewRow()][p1.getCityCrewCol()].getDeposit() +"/"+Configuration.getMax_dep()+ "   ||   " + "Deposit: " + territory[p2.getCityCrewRow()][p2.getCityCrewCol()].getDeposit() +"/"+Configuration.getMax_dep());
        System.out.println("Interest: " + territory[p1.getCityCrewRow()][p1.getCityCrewCol()].getInterest() + "           ||     " + "Interest: " + territory[p2.getCityCrewRow()][p2.getCityCrewCol()].getInterest());
        System.out.println("My Region: " + p1.showMyRegion() + "           ||     " + "My Region: " + p2.showMyRegion());
    }

    public Player isWinner(){
        if( p1.checkLose() ){
            winner = p2;
        }else if( p2.checkLose() ){
            winner = p1;
        }else {
            winner = null;
        }
        return winner;
    }

}
