package AST;

import Error.*;
import GamePlay.Player;

import java.util.Map;

public class Relocate implements Node{

    @Override
    public void evaluate( Map<String, Long> bindings, Player player ) throws EvalError{
        player.relocate();
    }

    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( "relocate" );
    }
}
