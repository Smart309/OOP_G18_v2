package AST;

import Error.EvalError;
import GamePlay.Player;

import java.util.Map;
public interface Expr extends Node{
    Long eval( Map<String,Long> bindings, Player player ) throws EvalError;
}
