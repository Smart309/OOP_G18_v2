package AST;

import Error.*;

import java.util.Map;

public class InfoExpr implements Expr{
    private String expr;
    private Node dir;
    public InfoExpr(String expr,Node dir){
        this.expr =expr;
        this.dir =dir;
    }
    @Override
    public Long eval( Map<String, Long> bindings ) throws EvalError{
        return 0L;
    }
    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( expr );
        if(dir != null){
            dir.prettyPrint( s );
        }
    }
}
