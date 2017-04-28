package com.eisenstudios.dungentales.weapon;

import java.util.ArrayList;

/**
 * Created by Connor on 24/04/2017.
 */
public class Weapon
{
    private String wepType;
    private String wepName;
    private String wepDesc;
    private int wepDmg;

    public Weapon(String wepType, String wepName, int wepDmg, String wepDesc)
    {
        this.wepType = wepType;
        this.wepName = wepName;
        this.wepDmg = wepDmg;
        this.wepDesc = wepDesc;
    }

    public String getWepType()
    {
        return wepType;
    }

    public String getWepName()
    {
        return wepName;
    }

    public int getWepDmg()
    {
        return wepDmg;
    }

    public String getWepDesc()
    {
        return wepDesc;
    }

    public String toString()
    {
        return "Wep type: " + wepType + "\nWep name: " + wepName + "\nWep dmg: " + wepDmg + "\nWep desc: " + wepDesc;
    }
}