package GamePlay;

import Error.*;

import java.util.Scanner;

public class GamePlay{
    private Player p1;
    private Player p2;
    private Region[][] territory;
    public GamePlay( Player p1 , Player p2 , Region[][] territory ) throws EvalError{
        this.p1 = p1;
        this.p2 = p2;
        this.territory = territory;
        start();
    }

    public void start() throws EvalError{
        p1.setCityCenter( territory );
//        p2.setCityCenter( territory );
        p1.showVar();
//        p2.showVar();
        Scanner scanner = new Scanner( System.in );
        while( p1.getBudget() > 0L && p2.getBudget() > 0L ){
            scanner.nextLine();
            p1.doPlan();
            p1.showVar();

//            p2.doPlan();
//            p2.updateDeposit( territory );
//            p2.showVar();
//            scanner.nextLine();
        }
    }
}
