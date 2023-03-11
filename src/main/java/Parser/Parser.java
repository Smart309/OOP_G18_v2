package Parser;

import AST.Node;
import Error.*;

public interface Parser{
    /** Attempts to parse the token stream
     * given to this parser.
     * throws: Error.SyntaxError if the token
     * stream cannot be parsed */
    Node parse() throws SyntaxError, LexicalError, EvalError;
}
