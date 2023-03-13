package GamePlay;

import AST.Node;
import Parser.ExprParser;
import Tokenizer.*;
import Error.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Main{
    public static void readConfiguration( Map<String, Long> var, Path configFile, Charset charset)
    {
        try (BufferedReader reader = Files.newBufferedReader(configFile, charset))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String key = line.substring(0, line.indexOf('='));
                Long val = Long.valueOf(line.substring(line.indexOf('=') + 1));
                var.put(key, val);
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage() + " : file not found");
        }
    }

    public static void main(String[] args) throws LexicalError, SyntaxError, EvalError{
        Charset charset = StandardCharsets.UTF_8;
        Path configFile = Paths.get("src/configuration file.txt");
        Map<String, Long> var = new HashMap<>();
        readConfiguration(var, configFile, charset);

        Tokenizer tkz = null;
        Path file = Paths.get("src/construction plan.txt");
//        Path file = Paths.get("src/construction plan.txt");
        try
        {
            String content = Files.readString(file, charset);
            tkz = new ExprTokenizer(content);
        }
        catch (NoSuchElementException | LexicalError e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage() + " : file not found");
        }
        ExprParser plan = new ExprParser(tkz);
        Node p = plan.parse();
//        StringBuilder s =new StringBuilder();
//        p.prettyPrint( s );
//        System.out.println(s);
        return;
//        try
//        {

//            StringBuilder s =new StringBuilder();
//            p.prettyPrint( s );
//            System.out.println(s);


//            Territory territory = new Territory( var );
//            Player p1 = new Player("player1", var, p,territory.getTerritory());
//            Player p2 = new Player("player2", var, p,territory.getTerritory());
//            GamePlay gamePlay = new GamePlay( p1,p2,territory.getTerritory() );
//            gamePlay.start();
//        }catch (LexicalError | SyntaxError | EvalError e){
//            System.out.println(e.getMessage());
//        }


    }
}