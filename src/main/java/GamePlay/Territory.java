package Gameplay;

import java.util.Map;

public class Territory {
    private long m ,n;
    private Gameplay.Region[][] territory;
    public Territory( Map<String, Long> var){
        m = var.get("m");
        n = var.get("n");
        territory = new Gameplay.Region[Math.toIntExact( m )][Math.toIntExact( n )];
        for(int i = 0 ; i < m ; i++){
            for(int j = 0 ; j < n ; j++){
                territory[i][j] = new Gameplay.Region(var);
            }
        }
    }
    public Long rows(){
        return m;
    }
    public Long cols(){
        return n;
    }
}