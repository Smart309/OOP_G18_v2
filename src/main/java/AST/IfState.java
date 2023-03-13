package AST;

import Error.*;
import GamePlay.Player;

import java.util.Map;

public class IfState implements Node{
    private Expr condition;
    private Node thenStatement,elseStatement;
    public IfState( Expr condition, Node thenStatement, Node elseStatement ){
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public void doPlan( Map<String, Long> bindings , Player player ) throws EvalError{
        if(condition.eval( bindings ) > 0){
            thenStatement.doPlan( bindings,player );
        }else {
            elseStatement.doPlan( bindings,player );
        }
    }

    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( "if(" );
        condition.prettyPrint( s );
        s.append( ") then " );
        thenStatement.prettyPrint( s );
        if( elseStatement != null ){
            s.append( "else " );
            elseStatement.prettyPrint( s );
        }
    }
}
