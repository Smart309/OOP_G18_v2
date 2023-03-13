package AST;

import java.util.Map;

import Error.*;
import GamePlay.Player;

public class Done implements Node{
    @Override
    public void doPlan( Map<String, Long> bindings , Player player ) throws EvalError{
        player.done();
    }

    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( "done" );
    }
}