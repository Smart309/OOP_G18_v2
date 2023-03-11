package AST;

import Error.*;

import java.util.Map;

public class Move implements Node{
    private Expr dir;
    public Move( Expr dir ){
        this.dir = dir;
    }

    @Override
    public void doPlan( Map<String, Long> bindings ) throws EvalError{
        if(dir.eval( bindings ) == 1L){
            if( bindings.get( "curcol" ) != 1L){
                bindings.replace( "curcol",bindings.get( "curcol" )-1);
            }
            bindings.replace( "budget",bindings.get( "budget" )-1);
        }else if(dir.eval( bindings ) == 2L){
            if( bindings.get( "currow" ) != bindings.get( "rows" )){
                bindings.replace( "currow",bindings.get( "currow" )+1);
            }
            bindings.replace( "budget",bindings.get( "budget" )-1);
        }else if( dir.eval( bindings ) == 3L ){
            if( bindings.get( "currow" ) != bindings.get( "rows" )){
                bindings.replace( "currow",bindings.get( "currow" )+1);
            }
            if( bindings.get( "curcol" ) != bindings.get( "cols" )){
                bindings.replace( "curcol",bindings.get( "curcol" )+1);
            }
            bindings.replace( "budget",bindings.get( "budget" )-1);
        }else if( dir.eval( bindings ) == 4L ){
            if( bindings.get( "curcol" ) != bindings.get( "cols" )){
                bindings.replace( "curcol",bindings.get( "curcol" )+1);
            }
            bindings.replace( "budget",bindings.get( "budget" )-1);
        }else if( dir.eval( bindings ) == 5L ){
            if( bindings.get( "currow" ) != 1L){
                bindings.replace( "currow",bindings.get( "currow" )-1);
            }
            if( bindings.get( "curcol" ) != 1L){
                bindings.replace( "curcol",bindings.get( "curcol" )-1);
            }
            bindings.replace( "budget",bindings.get( "budget" )-1);
        }else if( dir.eval( bindings ) == 6L ){
            if( bindings.get( "currow" ) != 1L){
                bindings.replace( "currow",bindings.get( "currow" )-1);
            }
            bindings.replace( "budget",bindings.get( "budget" )-1);
        }
    }

    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( "move" );
        dir.prettyPrint( s );
    }
}
