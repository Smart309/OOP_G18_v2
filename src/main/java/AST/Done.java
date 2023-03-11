package AST;

public class Done implements Node{
    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( "done" );
    }
}
