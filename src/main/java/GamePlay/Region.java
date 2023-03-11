package Gameplay;


import java.util.Map;

public class Region {
    private int[] position;
    private Long maxDeposit;
    private Long r;

    public Region( Map<String,Long> var ){
        maxDeposit = var.get("max_dep");
        r = var.get("interest_pct");
    }
}