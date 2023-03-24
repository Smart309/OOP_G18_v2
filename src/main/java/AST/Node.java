package AST;

import GamePlay.Player;
import Error.*;

import java.util.Map;

public interface Node{
    void prettyPrint(StringBuilder s);

    default void evaluate( Map<String, Long> bindings, Player player) throws EvalError{};
}
