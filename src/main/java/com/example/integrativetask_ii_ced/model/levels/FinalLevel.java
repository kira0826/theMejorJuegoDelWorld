package com.example.integrativetask_ii_ced.model.levels;

import com.example.integrativetask_ii_ced.model.entities.mob.Boss;
import com.example.integrativetask_ii_ced.model.entities.objects.functional.MobilePump;
import com.example.integrativetask_ii_ced.model.map.GameMap;

import java.util.ArrayList;

public class FinalLevel {

    public static ArrayList<MobilePump> mobilePumps = new ArrayList<>();
    public static GameMap gameMap = new GameMap(1200,720, 80,3);

    public static Boss finalBoss = new Boss(0,0,0,0,0);


}
