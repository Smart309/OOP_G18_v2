package GamePlay;

import AST.Node;
import Error.*;

import java.util.*;

public class Player {
    private String name;
    private Map<String, Long> playerValue,gameValue;
    private Node plan;
    public int[] cityCenter = new int[2];
    private boolean endTurn = false;
    Region[][] territory;
    public Player(String name , Map<String,Long> gameValue , Node plan, Region[][] territory){
        this.name = name;
        this.gameValue = gameValue;
        this.playerValue = new HashMap<>();
        this.plan = plan;
        this.territory = territory;
        cityCenter[0] = (int)(1+Math.random()*(gameValue.get( "m" )));
        cityCenter[1] = (int)(1+Math.random()*(gameValue.get( "n" )));
        playerValue.put( "rows",gameValue.get( "m" ) );
        playerValue.put( "cols",gameValue.get( "n" ) );
        playerValue.put( "currow",(long)cityCenter[0] );
        playerValue.put( "curcol",(long)cityCenter[1] );
        playerValue.put( "budget",gameValue.get( "init_budget" ) );
        playerValue.put( "deposit",0L);
        playerValue.put( "int",0L );
        playerValue.put( "maxdeposit",gameValue.get( "max_dep" ) );
        playerValue.put("random",0L);
    }

    public void showVar(){
        System.out.println("|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|");
        System.out.println(">>>" + name + "<<<");
        Iterator<String> iterator = playerValue.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            System.out.println(key + ":" + playerValue.get(key));
        }
        System.out.println("|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|");
    }
    public Map<String,Long> getVar(){
        return playerValue;
    }

    public long getBudget(){
        return playerValue.get( "budget" );
    }
    public void doPlan() throws EvalError{
        endTurn = false;
        plan.doPlan(playerValue,this);
        System.out.println("End Of Turn");
    }
    
    public void done(){
        endTurn = true;
    }
    
    public void collect( long amount){
        if( !endTurn ){
            playerValue.replace( "budget", playerValue.get( "budget" ) - 1 );
            if( playerValue.get( "deposit" ) >= amount ){
                playerValue.replace( "budget", playerValue.get( "budget" ) + amount );
                playerValue.replace( "deposit", playerValue.get( "deposit" ) - amount );
            }
            System.out.println(">>>collect:[ " + amount +" unit ]<<<");
            this.done();
        }
    }
    public void invest( long amount){
        if( !endTurn ){
            playerValue.replace( "budget", playerValue.get( "budget" ) - 1 );
            if( playerValue.get( "budget" ) >= amount ){
                playerValue.replace( "budget", playerValue.get( "budget" ) - amount );
                playerValue.replace( "deposit", playerValue.get( "deposit" ) + amount );
            }
            System.out.println(">>>invest:[ " + amount + " unit ]<<<");
            this.done();
        }
    }

    public void move(long dir){
        if( !endTurn ){
            playerValue.replace( "budget", playerValue.get( "budget" ) - 1 );
            long currow = playerValue.get("currow");
            long curcol = playerValue.get("curcol");
            if( dir == 1L ){//Up
                if(currow > 1){
                    playerValue.replace( "currow",currow-1 );
                    System.out.println(">>>Move Up<<<");
                }else{
                    System.out.println(">>>Can't Move Up<<<");
                }
            }else if( dir == 2L ){//Up Right
                if((currow > 1 || curcol%2 !=0) && curcol < playerValue.get( "cols" )){
                    if(curcol % 2 == 0){
                        playerValue.replace( "currow",currow-1 );
                    }
                    playerValue.replace( "curcol",curcol+1 );
                    System.out.println(">>>Move Up Right<<<");
                }else{
                    System.out.println(">>>Can't Move Up Right<<<");
                }
            }else if( dir == 3L ){//Down Right
                if((currow < playerValue.get( "rows" ) || curcol%2 ==0) && curcol < playerValue.get( "cols" )){
                    if(curcol % 2 != 0){
                        playerValue.replace( "currow",currow+1 );
                    }
                    playerValue.replace( "curcol",curcol+1 );
                    System.out.println(">>>Move Down Right<<<");
                }else{
                    System.out.println(">>>Can't Move Down Right<<<");
                }
            }else if( dir == 4L ){//Down
                if(currow > playerValue.get("rows")){
                    playerValue.replace( "currow",currow+1 );
                    System.out.println(">>>Move Down<<<");
                }else{
                    System.out.println(">>>Can't Move Down<<<");
                }
            }else if( dir == 5L ){//Down Left
                if((currow < playerValue.get( "rows" ) || curcol%2 == 0) && curcol > 1){
                    if(curcol % 2 != 0){
                        playerValue.replace( "currow",currow+1 );
                    }
                    playerValue.replace( "curcol",curcol-1 );
                    System.out.println(">>>Move Down Left<<<");
                }else{
                    System.out.println(">>>Can't Move Down Left<<<");
                }
            }else if( dir == 6L ){//Up Left
                if((currow > 1 || curcol%2 !=0) && curcol > 1){
                    if(curcol % 2 == 0){
                        playerValue.replace( "currow",currow-1 );
                    }
                    playerValue.replace( "curcol",curcol-1 );
                    System.out.println(">>>Move Up Left<<<");
                }else{
                    System.out.println(">>>Can't Move Up Left<<<");
                }
            }
            this.done();
        }
    }
    public void setCityCenter(Region[][] territory){
        territory[cityCenter[0]-1][cityCenter[1]-1].setPlayerOwner( this );
    }
    public void updateDeposit(Region[][] territory){
        int currow = Math.toIntExact( playerValue.get( "currow" ) );
        int curcol = Math.toIntExact( playerValue.get( "curcol" ) );
        playerValue.replace( "deposit",territory[currow-1][curcol-1].getDeposit() );
    }
}