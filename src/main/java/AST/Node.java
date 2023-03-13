package AST;

import Error.*;
import GamePlay.Player;

import java.util.Map;

public interface Node{
    void prettyPrint(StringBuilder s);

    default void doPlan( Map<String, Long> bindings, Player player ) throws EvalError{};
}
