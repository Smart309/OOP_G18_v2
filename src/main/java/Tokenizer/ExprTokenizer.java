package Tokenizer;

import Error.*;

import java.util.NoSuchElementException;

public class ExprTokenizer implements Tokenizer{
    private  String src;
    private String next; private int pos;
    public ExprTokenizer(String src) throws LexicalError{
        this.src = src; pos = 0;
        computeNext();
    }
    public boolean hasNextToken(){
        return next != null;
    }
    public String peek(){
        if(!hasNextToken()) throw new NoSuchElementException("no more token");
        return next;
    }
    public boolean peek(String s) {
        if( !hasNextToken() ){
            return false;
        }
        return peek().equals(s);
    }
    public String consume() throws LexicalError{
        if(!hasNextToken()) throw new NoSuchElementException("no more tokens");
        String result = next;
        computeNext();
        return result;
    }
    public void consume(String s) throws SyntaxError, LexicalError{
        if (peek(s))
            consume();
        else
            throw new SyntaxError(s + " expected");
    }
    private void computeNext() throws LexicalError{
        StringBuilder s = new StringBuilder();
        while( pos < src.length() && Character.isWhitespace( src.charAt( pos ) ) ){
            pos++;
        }
        if(pos < src.length() && src.charAt( pos ) =='#'){
            while( pos<src.length() && src.charAt( pos ) != '\n' ){
                pos++;
            }
            if(pos<src.length() && src.charAt( pos )=='\n'){
                pos++;
            }
            computeNext();
        }else if(pos < src.length()){
            char c = src.charAt( pos );
            if('\n'==c){
                pos++;
            }else if(Character.isLetter( c )){
                s.append( c );
                for( pos++;pos<src.length()&&Character.isLetter( src.charAt( pos ) );pos++ ){
                    s.append( src.charAt( pos ) );
                }
            }else if(Character.isDigit( c )){
                s.append( c );
                for( pos++;pos<src.length()&&Character.isDigit( src.charAt( pos ) );pos++ ){
                    s.append( src.charAt( pos ) );
                }
            }else if(c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^' || c == '=' || c =='(' || c == ')' || c == '{' || c == '}'){
                s.append( c );
                pos++;
            }else{
                throw new LexicalError( "unknown character :" + c );
            }
            next = s.toString();
        }else{
            next = null;
        }
    }
}
