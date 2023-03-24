package AST;

import Error.*;
import GamePlay.Player;
import GamePlay.Region;

import java.util.Map;

public class Move implements Node{
    private Expr dir;
    public Move( Expr dir ){
        this.dir = dir;
    }

    @Override
    public void evaluate( Map<String, Long> bindings , Player player ) throws EvalError{
        player.move( dir.eval( bindings ,player) );
    }

    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( "move" );
        dir.prettyPrint( s );
    }
}
