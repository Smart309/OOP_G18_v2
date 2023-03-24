package AST;

import Error.*;
import GamePlay.Player;
import GamePlay.Region;

import java.util.Map;

public class RegionCommand implements Node{
    protected String dep_with;
    protected Expr amount;
    public RegionCommand( String dep_with, Expr amount){
        this.dep_with = dep_with;
        this.amount = amount;
    }

    @Override
    public void evaluate( Map<String, Long> bindings, Player player ) throws EvalError{
        if( dep_with.equals( "collect" ) ){
            player.collect( amount.eval( bindings,player ) );
        }else if( dep_with.equals( "invest" ) ){
            player.invest( amount.eval( bindings,player ) );
        }
    }

    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( dep_with );
        s.append( " " );
        amount.prettyPrint( s );
        s.append( " " );
    }
}
