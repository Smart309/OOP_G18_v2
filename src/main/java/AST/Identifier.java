package AST;

import java.util.Map;

public class Identifier implements Expr{
    private String name;
    public Identifier( String name ){
        this.name = name;
    }

    @Override
    public Long eval( Map<String, Long> bindings ){
        if(name.equals( "random" )){
            bindings.replace( name,(long)(Math.random()*999) );
            return bindings.get( name );
        }else if(bindings.containsKey( name )){
            return bindings.get(name);
        }else{
            bindings.put( name,0L );
            return 0L;
        }
    }

    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( name );
    }
}
