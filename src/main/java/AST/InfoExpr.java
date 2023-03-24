package AST;

import Error.*;
import GamePlay.Player;

import java.util.Map;

public class InfoExpr implements Expr{
    private String expr;
    private Expr dir;
    public InfoExpr(String expr,Expr dir){
        this.expr =expr;
        this.dir =dir;
    }

    @Override
    public Long eval( Map<String, Long> bindings ,Player player) throws EvalError{
        if(expr.equals("opponent"))
            return player.opponent();
        else
            return player.nearby(dir.eval(bindings,player));
    }
    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( expr );
        if(dir != null){
            dir.prettyPrint( s );
        }
    }
}
