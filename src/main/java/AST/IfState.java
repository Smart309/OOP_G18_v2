package AST;

import Error.*;
import GamePlay.Player;
import GamePlay.Region;

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
    public void evaluate( Map<String, Long> bindings , Player player ) throws EvalError{
        if(condition.eval( bindings ,player) > 0){
            thenStatement.evaluate( bindings,player );
        }else {
            elseStatement.evaluate( bindings,player );
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
