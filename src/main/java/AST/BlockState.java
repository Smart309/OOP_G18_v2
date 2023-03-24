package AST;

import Error.*;
import GamePlay.Player;
import GamePlay.Region;

import java.util.LinkedList;
import java.util.Map;

public class BlockState implements Node{
    private LinkedList<Node> stetements;
    public BlockState(LinkedList<Node> stetements){
        this.stetements = stetements;
    }

    @Override
    public void evaluate( Map<String, Long> bindings, Player player ) throws EvalError{
        stetements.forEach( p -> {
            try{
                p.evaluate( bindings,player );
            }catch( EvalError e ){
                System.out.println("State Error");
            }
        } );
    }

    @Override
    public void prettyPrint( StringBuilder s ){
        if(stetements.isEmpty()){
            s.append( "{}" );
        }else{
            s.append( "{\n" );
            for( Node stetament : stetements){
                s.append( "\t" );
                stetament.prettyPrint( s );
                s.append( "\n" );
            }
            s.append( "}" );
        }
    }
}
