package AST;

import Error.EvalError;
import GamePlay.Player;
import GamePlay.Region;

import java.util.Map;

public class WhileState implements Node{
    protected Expr condition;
    protected Node stateInWhile;
    public WhileState(Expr condition, Node stateInWhile ){
        this.condition = condition;
        this.stateInWhile = stateInWhile;
    }

    @Override
    public void evaluate( Map<String, Long> bindings , Player player ) throws EvalError{
        for( int i = 0; i < 10000 && condition.eval( bindings ,player)>0; i++ ){
            stateInWhile.evaluate( bindings ,player);
        }
    }
    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( "while(" );
        condition.prettyPrint( s );
        s.append( ") " );
        stateInWhile.prettyPrint( s );
    }
}
