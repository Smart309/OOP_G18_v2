package GamePlay;

import java.util.Map;

public class Territory {
    private int m ,n;
    private Region[][] territory;
    public Territory( int m , int n){
        this.m = m;
        this.n = n;
        this.territory = new Region[ m ][ n ];
        for(int i = 0 ; i < m ; i++){
            for(int j = 0 ; j < n ; j++){
                territory[i][j] = new Region(i,j);
            }
        }
    }
    public Region[][] getTerritory(){
        return territory;
    }
}