package AST;

import GamePlay.Player;

import java.util.Map;

public class IntLit implements Expr{
    private Long val;
    public IntLit( long val ){
        this.val = val;
    }
    @Override
    public Long eval( Map<String, Long> bindings , Player player ) {
        return val;
    }
    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( val );
    }
}
