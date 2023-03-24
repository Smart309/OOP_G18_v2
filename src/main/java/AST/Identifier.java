package AST;

import GamePlay.*;

import java.util.Map;

public class Identifier implements Expr{
    private String name;
    public Identifier( String name ){
        this.name = name;
    }

    @Override
    public Long eval( Map<String, Long> bindings , Player player ){
        if(name.equals("rows")){
            return Configuration.getM();
        }else if(name.equals("cols")){
            return Configuration.getN();
        }else if(name.equals("currow")){
            return (long) player.getCityCrewRow();
        }else if(name.equals("curcol")){
            return (long) player.getCityCrewCol();
        }else if(name.equals("budget")){
            return player.getBudget();
        }else if(name.equals("deposit")){
            return GamePlay.getRegion(player.getCityCrewRow(), player.getCityCrewCol()).getDeposit();
        }else if(name.equals("int")){
            return GamePlay.getRegion(player.getCityCrewRow(), player.getCityCrewCol()).getInterest();
        }else if(name.equals("maxdeposit")){
            return Configuration.getMax_dep();
        }else if(name.equals("random")){
            return (long) (Math.random() * 1000);
        }else if (bindings.containsKey(name)){
            return bindings.get(name);
        }else{
            bindings.put(name, 0L);
            return 0L;
        }
    }

    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( name );
    }
}
