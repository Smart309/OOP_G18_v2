package AST;

import Error.*;
import GamePlay.Player;

import java.util.Map;

public class Attack implements Node{
    protected Expr dir;
    private Expr amount;
    public Attack( Expr dir , Expr amount){
        this.dir = dir;
        this.amount = amount;
    }

    @Override
    public void evaluate( Map<String, Long> bindings, Player player ) throws EvalError{
        player.shoot( dir.eval( null,player ),amount.eval( null,player ) );
    }

    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( "shoot");
        amount.prettyPrint( s );
        dir.prettyPrint( s );
    }
}
