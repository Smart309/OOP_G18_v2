package AST;

import Error.*;

import java.util.LinkedList;
import java.util.Map;

public class State implements Node{
    private LinkedList<Node> nodePlan;
    public State(LinkedList<Node> nodePlan){
        this.nodePlan = nodePlan;
    }
    public void doPlan( Map<String,Long> bindings ){
        nodePlan.forEach( plan -> {
            try{
                plan.doPlan(bindings);
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