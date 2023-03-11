package AST;

public class RegionCommand implements Node{
    protected String dep_with;
    protected Expr amount;
    public RegionCommand( String dep_with, Expr amount){
        this.dep_with = dep_with;
        this.amount = amount;
    }
    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( dep_with );
        amount.prettyPrint( s );
    }
}
