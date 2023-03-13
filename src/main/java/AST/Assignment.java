package AST;

import Error.*;
import GamePlay.Player;

import java.util.Map;

public class Assignment implements Node{
    private String variable,op;
    private Expr number;
    public Assignment( String variable,String op ,Expr number ){
        this.variable = variable;
        this.number = number;
        this.op = op;
    }

    @Override
    public void doPlan( Map<String, Long> bindings , Player player ) throws EvalError{
        if( bindings.containsKey( variable ) ){
            bindings.replace( variable,number.eval( bindings ) );
        }else{
            bindings.put(variable, number.eval(bindings) );
        }
    }

    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( variable );
        s.append( op );
        number.prettyPrint( s );
    }
}
