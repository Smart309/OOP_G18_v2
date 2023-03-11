package AST;

import java.util.Map;

public class IntLit implements Expr{
    private Long val;
    public IntLit( long val ){
        this.val = val;
    }
    @Override
    public Long eval( Map<String, Long> bindings ) {
        return val;
    }
    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( val );
    }
}
