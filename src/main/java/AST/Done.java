package AST;

import java.util.Map;

import Error.*;
import GamePlay.Player;
import GamePlay.Region;

public class Done implements Node{
    @Override
    public void evaluate( Map<String, Long> bindings , Player player) throws EvalError{
        player.done();
    }

    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( "done" );
    }
}