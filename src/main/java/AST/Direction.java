package AST;

import java.util.Map;

public enum Direction implements Expr{
    up,down,upleft,upright,downleft,downright;

    @Override
    public Long eval( Map<String, Long> bindings ) {
        switch (this)
        {
            case up -> {return 1L;}
            case upright -> {return 2L;}
            case downright -> {return 3L;}
            case down -> {return 4L;}
            case downleft -> {return 5L;}
            case upleft -> {return 6L;}
            default -> {return 0L;}
        }
    }

    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( " " ).append( Direction.this );
    }
}
