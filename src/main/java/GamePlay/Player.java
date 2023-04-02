package GamePlay;

import AST.Node;
import Error.*;
import Parser.ExprParser;
import Tokenizer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Player {
    private String name;
    private long budget;
    private Map<String, Long> playerValue;
    private int[] cityCenter = new int[2];
    private int[] cityCrew = new int[2];
    private boolean endTurn ;
    private boolean loser;
    private int turn = 1;
    private String plan,p ;
    private Path path = Paths.get("src/configuration file.txt");
    private Path pathPlan ;
    private Charset charset = StandardCharsets.UTF_8;
    private Scanner s = new Scanner( System.in );
    private int numberPlayer ;
    private ArrayList<Region> myRegion = new ArrayList<Region>();
    public Player( int numberPlayer){
        Configuration.setConfig(path);
        this.numberPlayer = numberPlayer;
        if( numberPlayer == 1 ){
            System.out.print("Name Of Player 1 : ");
        }else{
            System.out.print("Name Of Player 2 : ");
        }
        this.name = s.nextLine();
        this.playerValue = new HashMap<>();
        budget = Configuration.getInit_budget();
        System.out.print("Path Construction Plan Of " + this.name + " : ");
        p = s.nextLine();
        pathPlan = Paths.get(p);//"src/construction plan.txt"
        try{
            this.plan = Files.readString( pathPlan,charset );
        }catch( IOException e ){
            throw new RuntimeException( e );
        }
    }
    public Map<String,Long> getVar(){
        return playerValue;
    }

    public long getBudget(){
        return budget;
    }
    public void doPlan() throws EvalError{
        endTurn = false;
        Node p = null;
        try{
//            System.out.print("Action Of " + this.name + " : " );
//            plan = s.nextLine();
            Tokenizer tkz = new ExprTokenizer( plan );
            ExprParser parser = new ExprParser( tkz );
            p = parser.parse();
        }catch( LexicalError e ){
            System.out.println(e.getMessage());
        }catch( SyntaxError e ){
            System.out.println(e.getMessage());
        }
        p.evaluate( playerValue,this );
        turn++;
    }
    public void done(){
        endTurn = true;
        cityCrew[0] = cityCenter[0];
        cityCrew[1] = cityCenter[1];
    }
    public void collect( long amount){
        if(!endTurn){
            if( budget > 0 ){
                budget--;
                Region curRegion = GamePlay.getRegion( cityCrew[0],cityCrew[1] );
                if( amount <= curRegion.getDeposit() && curRegion.getPlayerOwner().equals( this ) ){
                    curRegion.updateDeposit( -amount );
                    budget += amount;
                    System.out.println("___________________________________________________________");
                    System.out.println(name + " :" + " Collect " + amount + " Unit.");
                    System.out.println("___________________________________________________________");
                }else {
                    System.out.println("___________________________________________________________");
                    System.out.println(name + " :" + " Not Enough Budget.");
                    System.out.println("___________________________________________________________");
                }
                if( curRegion.getDeposit() == 0 ){
                    System.out.println("___________________________________________________________");
                    System.out.println(name + " :" + " Lose Region (" + (curRegion.getRowOfRegion()+1) + "," + (curRegion.getColOfRegion()+1) + ")");
                    System.out.println("___________________________________________________________");
                    myRegion.remove( curRegion );
                    curRegion.removePlayerOwner();
                }
                endTurn = true;
            }else {
                done();
            }
        }
    }
    public void invest( long amount){
        if( !endTurn ){
            Region curRegion = GamePlay.getRegion( cityCrew[0],cityCrew[1] );
            if(budget > amount){
                budget--;
                if( curRegion.getDeposit() + amount <= Configuration.getMax_dep() ){
                    budget -= amount;
                    curRegion.updateDeposit( amount );
                    System.out.println("___________________________________________________________");
                    System.out.println(name + " :" + " Invest " + amount + " Unit.");
                    System.out.println("___________________________________________________________");
                }else{
                    budget -= Configuration.getMax_dep() - curRegion.getDeposit();
                    System.out.println("___________________________________________________________");
                    System.out.println(name + " :" + " Invest " + (Configuration.getMax_dep() - curRegion.getDeposit()) + " Unit.");
                    System.out.println("___________________________________________________________");
                }
                if(curRegion.getPlayerOwner() == null)
                {
                    System.out.println("___________________________________________________________");
                    System.out.println(name + " :" + " Get Region (" + (curRegion.getRowOfRegion()+1) + "," + (curRegion.getColOfRegion()+1) + ")");
                    System.out.println("___________________________________________________________");
                    myRegion.add( curRegion );
                    curRegion.setPlayerOwner(this);
                }
                endTurn = true;
            }else{
                done();
            }
        }
    }
    public long opponent(){
        if( !endTurn ){
            int[][] opPosition = new int[6][2];
            for (int i = 0; i < 6; i++){
                opPosition[i] = new int[]{cityCrew[0], cityCrew[1]};
            }
            long dist = 1;
            while (opPosition[0] != null || opPosition[1] != null || opPosition[2] != null || opPosition[3] != null || opPosition[4] != null || opPosition[5] != null){
                for (int i = 0; i < 6; i++){
                    if(opPosition[i] == null) continue;
                    int[] next = position(i+1, opPosition[i][0], opPosition[i][1]);
                    Region opponent = GamePlay.getRegion(next[0], next[1]);
                    if(opponent.getPlayerOwner() != null && !opponent.getPlayerOwner().equals(this)){
                        System.out.println("___________________________________________________________");
                        System.out.println(name + " :" + " Opponent Is " + ((dist*10) + i + 1));
                        System.out.println("___________________________________________________________");
                        return (dist*10) + i + 1;
                    }
                    if(opPosition[i][0] == next[0] && opPosition[i][1] == next[1]){
                        opPosition[i] = null;
                    }else{
                        opPosition[i] = next;
                    }
                }
                dist++;
            }
            System.out.println("___________________________________________________________");
            System.out.println(name + " :" + " Opponent Is " + 0L);
            System.out.println("___________________________________________________________");
            return 0L;
        }
        return 0L;
    }
    public void relocate(){
        if(!endTurn){
            if(budget > 0){
                budget--;
                Region cityCenterOld = GamePlay.getRegion( cityCenter[0], cityCenter[1] );
                Region cur = GamePlay.getRegion( cityCrew[0], cityCrew[1] );
                int[] next;
                if( cur.getPlayerOwner() != null ){
                    if( cur.getPlayerOwner().equals( this ) ){
                        int dist = 0;
                        while( ( cur.getRowOfRegion() != cityCenterOld.getRowOfRegion() && cur.getColOfRegion() != cityCenterOld.getColOfRegion() ) || cur.getRowOfRegion() != cityCenterOld.getRowOfRegion() || cur.getColOfRegion() != cityCenterOld.getColOfRegion() ){
                            if(cityCenterOld.getColOfRegion() < cur.getColOfRegion()){
                                if(cityCenterOld.getRowOfRegion() == cur.getRowOfRegion()){
                                    cityCenterOld = GamePlay.getRegion(cur.getRowOfRegion(), cur.getColOfRegion() );
                                    dist += cur.getColOfRegion() - cityCenterOld.getColOfRegion();
                                }else if(cityCenterOld.getRowOfRegion() < cur.getRowOfRegion()){
                                    next = position(3, cityCenterOld.getRowOfRegion(), cityCenterOld.getColOfRegion()); //downright
                                    cityCenterOld = GamePlay.getRegion(next[0], next[1]);
                                }else{
                                    next = position(2, cityCenterOld.getRowOfRegion(), cityCenterOld.getColOfRegion()); //upright
                                    cityCenterOld = GamePlay.getRegion(next[0], next[1]);
                                }
                            }else if(cityCenterOld.getColOfRegion() > cur.getColOfRegion()){
                                if(cityCenterOld.getRowOfRegion() == cur.getRowOfRegion()){
                                    cityCenterOld = GamePlay.getRegion(cur.getRowOfRegion(), cur.getColOfRegion());
                                    dist += cityCenterOld.getColOfRegion() - cur.getColOfRegion();
                                }else if(cityCenterOld.getRowOfRegion() < cur.getRowOfRegion()){
                                    next = position(5, cityCenterOld.getRowOfRegion(), cityCenterOld.getColOfRegion()); //downleft
                                    cityCenterOld = GamePlay.getRegion(next[0], next[1]);
                                }else{
                                    next = position(6, cityCenterOld.getRowOfRegion(), cityCenterOld.getColOfRegion()); //upleft
                                    cityCenterOld = GamePlay.getRegion(next[0], next[1]);
                                }
                            }else{
                                if(cityCenterOld.getRowOfRegion() < cur.getRowOfRegion()){
                                    cityCenterOld = GamePlay.getRegion(cur.getRowOfRegion(), cur.getColOfRegion());
                                    dist += cur.getRowOfRegion() - cityCenterOld.getRowOfRegion();
                                }else{
                                    cityCenterOld = GamePlay.getRegion(cur.getRowOfRegion(), cur.getColOfRegion());
                                    dist += cityCenterOld.getRowOfRegion() - cur.getRowOfRegion();
                                }
                            }
                            dist++;
                        }
                        int cost = 5*dist + 10;
                        System.out.println("___________________________________________________________");
                        System.out.print(name + " :" + " Relocate City Center From (" +(cityCenter[0]+1)+","+(cityCenter[1]+1)+") To ("+(cityCrew[0]+1)+","+(cityCrew[1]+1)+")");
                        System.out.print(" Pay " + cost +" Unit. -->");
                        if(budget >= cost)
                        {
                            budget -= cost;
                            GamePlay.getRegion(cityCenter[0], cityCenter[1]).changeCityCenter();
                            cur.setCityCenter(this);
                            cityCenter[0] = cityCrew[0];
                            cityCenter[1] = cityCrew[1];
                            System.out.println("Successfully Relocate.");
                        }else{
                            System.out.println("Failed To Relocate.");
                        }
                        System.out.println("___________________________________________________________");
                    }
                }else{
                    System.out.println("___________________________________________________________");
                    System.out.println(name + " :" + " You don't own region (" + (cur.getRowOfRegion()+1) + "," + (cur.getColOfRegion()+1) + ") yet.");
                    System.out.println("___________________________________________________________");
                }
                endTurn = true;
            }else{
                done();
            }
        }
    }
    public long nearby(long dir){
        long dist = 0;
        int[] opPosition = position(dir, cityCrew[0], cityCrew[1]);
        while (opPosition[0] > 0 && opPosition[0] < Configuration.getM()-1 && opPosition[1] > 0 && opPosition[1] < Configuration.getN()-1){
            dist++;
            Region opponent = GamePlay.getRegion(opPosition[0], opPosition[1]);
            int digitsDeposit = Long.toString(opponent.getDeposit()).length();
            if(opponent.getPlayerOwner() != null && !opponent.getPlayerOwner().equals(this)){
                System.out.println("___________________________________________________________");
                System.out.println(name + " :" + " NearBy Is " + (100*dist + digitsDeposit));
                System.out.println("___________________________________________________________");
                return 100*dist + digitsDeposit;
            }
            if(opPosition[0] > 0 && opPosition[0] < Configuration.getM()-1 && opPosition[1] > 0 && opPosition[1] < Configuration.getN()-1){
                opPosition = position(dir, opPosition[0], opPosition[1]);
            }
        }
        System.out.println("___________________________________________________________");
        System.out.println(name + " :" + " NearBy Is " + 0);
        System.out.println("___________________________________________________________");
        return 0L;
    }
    public void shoot(long dir, long cost){
        if( !endTurn ){
            String ShowDir = "";
            if(dir == 1){
                ShowDir = " Up";
            }else if( dir == 2 ){
                ShowDir = " Up Right";
            }else if( dir == 3 ){
                ShowDir = " Down Right";
            }else if( dir == 4 ){
                ShowDir = " Down";
            }else if( dir == 5 ){
                ShowDir = " Down Left";
            }else if( dir == 6 ){
                ShowDir = " Up Left";
            }
            if(budget - cost > 0){
                budget -= cost+1;
                int[] shootPosition = position(dir, cityCrew[0], cityCrew[1]);
                int opRow = shootPosition[0];
                int opCol = shootPosition[1];
                Region opponent = GamePlay.getRegion(opRow, opCol);
                if(opponent.getPlayerOwner() != null){
                    if(cost < opponent.getDeposit()){
                        opponent.updateDeposit(-cost);
                    }else{
                        opponent.updateDeposit(-opponent.getDeposit());
                        if(opponent.isCityCenter()){
                            opponent.getPlayerOwner().isLoser();
                        }
                        opponent.getPlayerOwner().myRegion.remove( opponent );
                        opponent.setPlayerOwner(null);
                    }
                }
                System.out.println("___________________________________________________________");
                System.out.println(name + " : Shoot To" + ShowDir + " With Budget " + cost + " Unit.");
                System.out.println("___________________________________________________________");
                endTurn = true;
            }else if(budget > 0){
                --budget;
                System.out.println("___________________________________________________________");
                System.out.println(name + " : Not Enough Budget.");
                System.out.println("___________________________________________________________");
                endTurn = true;
            }else{
                done();
            }
        }
    }
    public void move(long dir){
        if(!endTurn){
            String ShowDir = "";
            if( budget > 0 ){
                budget--;
                long rows = Configuration.getM()-1;
                long cols = Configuration.getN()-1;
                if(dir == 1){
                    if(cityCrew[0] > 0){
                        ShowDir = " Move Up";
                    }else{
                        ShowDir = " Can't Move Up";
                    }
                }else if( dir == 2 ){
                    if(cityCrew[1] < cols && (cityCrew[0] > 0 || cityCrew[1] % 2 == 0)){
                        ShowDir = " Move Up Right";
                    }else{
                        ShowDir = " Can't Move Up Right";
                    }
                }else if( dir == 3 ){
                    if(cityCrew[1] < cols && (cityCrew[0] < rows || cityCrew[1] % 2 == 0)){
                        ShowDir = " Move Down Right";
                    }else{
                        ShowDir = " Can't Move Down Right";
                    }
                }else if( dir == 4 ){
                    if(cityCrew[0] < rows){
                        ShowDir = " Move Down";
                    }else{
                        ShowDir = " Can't Move Down";
                    }
                }else if( dir == 5 ){
                    if(cityCrew[1] > 0 && (cityCrew[0] < rows || cityCrew[1] % 2 != 0)){
                        ShowDir = " Move Down Left";
                    }else{
                        ShowDir = " Can't Move Down Left";
                    }
                }else if( dir == 6 ){
                    if( cityCrew[1] > 0 && ( cityCrew[0] > 0 || cityCrew[1] % 2 == 0 ) ){
                        ShowDir = " Move Up Left";
                    }else{
                        ShowDir = " Can't Move Up Left";
                    }
                }
                System.out.println("___________________________________________________________");
                System.out.println(name + " :" + ShowDir);
                System.out.println("___________________________________________________________");
                int[] moveTo = position( dir,cityCrew[0],cityCrew[1] );
                Player owner = GamePlay.getRegion( moveTo[0],moveTo[1] ).getPlayerOwner();
                if(owner == null || owner.equals( this )){
                    cityCrew[0] = moveTo[0];
                    cityCrew[1] = moveTo[1];
                }else{
                    System.out.println("___________________________________________________________");
                    System.out.println("You Do Not Have Access To The Region.");
                    System.out.println("___________________________________________________________");
                }
                endTurn = true;
            }else{
                done();
            }
        }
    }
    private int[] position(long dir , int currow,int curcol){
        long rows = Configuration.getM()-1;
        long cols = Configuration.getN()-1;
        if(dir == 1){
            if(currow > 0){
                currow--;
            }
        }else if( dir == 2 ){
            if(curcol < cols && (currow > 0 || curcol % 2 == 0)){
                if( curcol % 2 != 0 && currow > 0 ){
                    currow--;
                }
                curcol++;
            }
        }else if( dir == 3 ){
            if(curcol < cols && (currow < rows || curcol % 2 != 0)){
                if( curcol % 2 == 0 && currow < rows ){
                    currow++;
                }
                curcol++;
            }
        }else if( dir == 4 ){
            if(currow < rows){
                currow++;
            }
        }else if( dir == 5 ){
            if(curcol > 0 && (currow < rows || curcol % 2 != 0)){
                if( curcol % 2 == 0 && currow < rows ){
                    currow++;
                }
                curcol--;
            }
        }else if( dir == 6 ){
            if(curcol > 0 && (currow > 0 || curcol % 2 == 0)){
                if( curcol % 2 != 0 && currow > 0 ){
                    currow--;
                }
                curcol--;
            }
        }
        return new int[] {currow, curcol};
    }
    public void setCityCenter(int m , int n){
        cityCenter[0] = m;
        cityCenter[1] = n;
        cityCrew[0] = cityCenter[0];
        cityCrew[1] = cityCenter[1];
    }
    public String getName(){
        return name;
    }
    public boolean checkLose(){
        return loser;
    }
    public void isLoser(){
        loser = true;
    }
    public int getTurn(){
        return turn;
    }
    public int getCityCenterRow(){
        return cityCenter[0];
    }
    public int getCityCenterCol(){
        return cityCenter[1];
    }
    public int getCityCrewRow(){
        return cityCrew[0];
    }
    public int getCityCrewCol(){
        return cityCrew[1];
    }
    public String showMyRegion(){
        int n = 0;
        String s = "";
        for(Region i : myRegion){
            if( n != 0){
                s += "," ;
            }
            s += "("+ (i.getRowOfRegion()+1) + "," + (i.getColOfRegion()+1) + ")";
        }
        return s;
    }
    public void addMyRegin(Region x){
        this.myRegion.add( x );
    }
    public void removeMyRegin(Region x){
        this.myRegion.remove( x );
    }
    public boolean checkEndTurn(){
        return endTurn;
    }
}