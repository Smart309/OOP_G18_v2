package AST;

import Error.*;
import GamePlay.Player;
import GamePlay.Region;

import java.util.LinkedList;
import java.util.Map;

public class State implements Node{
    private LinkedList<Node> nodePlan;
    public State(LinkedList<Node> nodePlan){
        this.nodePlan = nodePlan;
    }

    @Override
    public void evaluate( Map<String, Long> bindings, Player player ) throws EvalError{
        nodePlan.forEach( plan -> {
            try{
                plan.evaluate(bindings,player);
            }catch( EvalError e ){
                System.out.println( "State Error" );
            }
        });
    }
    @Override
    public void prettyPrint( StringBuilder s ){
        for( Node state : nodePlan ){
            state.prettyPrint( s );
            s.append( "\n" );
        }
    }
}