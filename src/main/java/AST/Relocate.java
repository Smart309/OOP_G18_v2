package AST;


public class Relocate implements Node{
    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( "relocate" );
    }
}
