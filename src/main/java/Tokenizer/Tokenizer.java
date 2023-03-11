package Tokenizer;

import Error.*;

public interface Tokenizer{
    /** Returns true if there is
     *  more token */
    boolean hasNextToken();

    /** Returns the next token
     *  in the input stream. */
    String peek();

    /** Returns true if
     * the next token is s. */
    boolean peek(String s);

    /** Consumes the next token
     *  from the input stream
     *  and returns it.
     *  effects: removes the next token
     *           from the input stream */
    String consume() throws LexicalError;

    /** Consumes the next token if it is s.
     * Throws Error.SyntaxError otherwise.
     * effects: removes the next token
     * from input stream if it is s
     */
    void consume(String s) throws SyntaxError, LexicalError;
}
