package AST;

import Error.*;

import java.util.Map;

public class BinaryArithExpr implements Expr{
    private Expr left,right;
    private String op;
    public BinaryArithExpr( Expr left, String op, Expr right ){
        this.left = left;
        this.op = op;
        this.right = right;
    }

    @Override
    public Long eval( Map<String, Long> bindings ) throws EvalError{
        double lv = left.eval(bindings);
        double rv = right.eval(bindings);
        if(op.equals( "+" ))return (long) (lv + rv);
        if( op.equals( "-" ) )return (long) (lv - rv);
        if(op.equals( "*" ))return (long) (lv * rv);
        if( op.equals( "/" ) ) return (long) (lv / rv);
        if( op.equals( "%" ) )return (long) (lv % rv);
        if( op.equals( "^" ) )return (long) Math.pow( lv,rv );
        throw new ArithmeticException( "Unknown op " + op  );
    }

    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( "(" );
        left.prettyPrint( s );
        s.append( op );
        right.prettyPrint( s );
        s.append( ")" );
    }
}
