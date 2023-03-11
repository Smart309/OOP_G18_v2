package AST;

import Error.*;

import java.util.Map;

public interface Node{
    void prettyPrint(StringBuilder s);

    default void doPlan( Map<String, Long> bindings ) throws EvalError{};
}
