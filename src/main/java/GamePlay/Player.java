package Gameplay;

import AST.Node;
import Error.*;

import java.util.*;

public class Player {
    private String name;
    private Map<String, Long> playerValue,gameValue;
    //    private Configuration configuration;
    private Node plan;
    private Long cityCenterX;
    private Long cityCenterY;
    public Player(String name , Map<String,Long> gameValue , Node plan){
        this.name = name;
        this.gameValue = gameValue;
        this.playerValue = new HashMap<>();
        this.plan = plan;
        cityCenterX = (long)(1+Math.random()*(gameValue.get( "m" )-1));
        cityCenterY = (long)(1+Math.random()*(gameValue.get( "n" )-1));
        playerValue.put( "rows",gameValue.get( "m" ) );
        playerValue.put( "cols",gameValue.get( "n" ) );
        playerValue.put( "currow",cityCenterX );
        playerValue.put( "curcol",cityCenterY );
        playerValue.put( "budget",gameValue.get( "init_budget" ) );
        playerValue.put( "deposit",1L);
        playerValue.put( "int",0L );
        playerValue.put( "maxdeposit",gameValue.get( "max_dep" ) );
        playerValue.put("random",0L);
    }

    public Map<String,Long> gatVar(){
        if(playerValue != null){
            playerValue.forEach( (variable,value) -> System.out.println(variable + " = " + value) );
        }
        return playerValue;
    }
    public void doPlan() throws EvalError{
        plan.doPlan(playerValue);

    }
}