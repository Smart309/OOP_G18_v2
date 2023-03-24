package GamePlay;

import Error.*;

import java.util.Scanner;


public class Main{
    public static void main(String[] args){
        Player p1 = new Player(1 );
        Player p2 = new Player(2 );
        GamePlay play = new GamePlay( p1,p2 );
        play.showStatus();
        Scanner nextPlayerTurn = new Scanner( System.in );
        try{
            while( play.isWinner() == null){
                System.out.print("Press Enter To Continue.");
                nextPlayerTurn.nextLine();
                play.doPlan();
            }
            System.out.println("___________________________________________________________");
            if( play.isWinner().equals( p1 ) ){
                System.out.println(">>>>>>"+p1.getName() + " Is The Winner<<<<<<");
            }else{
                System.out.println(">>>>>>"+p2.getName() + " Is The Winner<<<<<<");
            }
            System.out.println("___________________________________________________________");
        }catch( EvalError e ){
            System.out.println(e.getMessage());
        }
    }
}