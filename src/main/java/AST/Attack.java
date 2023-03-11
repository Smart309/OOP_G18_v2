package AST;


public class Attack implements Node{
    protected Node dir;
    private Expr amount;
    Node expression;
    public Attack( Node dir , Expr amount){
        this.dir = dir;
        this.amount = amount;
    }
    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( "shoot");
        amount.prettyPrint( s );
        dir.prettyPrint( s );
    }
}
