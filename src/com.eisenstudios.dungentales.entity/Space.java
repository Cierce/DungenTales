package com.eisenstudios.dungentales.entity;

import com.eisenstudios.dungentales.rndgen.WeaponGenerator;
import com.eisenstudios.dungentales.weapon.Weapon;

import java.util.Random;

/**
 * Created by Connor on 24/04/2017.
 */
public class Space
{
    private final String FLOOR;
    private final String ENEMY;
    private final String PLAYER;
    private final String WALL;
    private final String WEAPON;

    private String plrName;
    private String eneName;
    private String entityType;
    private String wepName;

    private int hitPoints;
    private int strPoints;
    private int defPoints;
    private int atkDmg;
    private int weapon;

    private boolean isPassable;
    private boolean dead;
    private WeaponGenerator weaponGenerator;
    private Weapon newWep;
    private Weapon equippedWeapon;

    public Space(String entityType)
    {
        FLOOR  = ",";
        ENEMY  = "E";
        PLAYER = "@";
        WALL   = "#";
        WEAPON = "W";

        this.entityType = entityType;

        if(entityType.equalsIgnoreCase("wall"))
        {
            isPassable = false;
        }
        else if(entityType.equalsIgnoreCase("enemy"))
        {
            genEnemy();
            dead       = false;
            isPassable = false;
        }
        else if(entityType.equalsIgnoreCase("player"))
        {
           createPlayer();
        }
        else if(entityType.equalsIgnoreCase("weapon"))
        {
            weaponGenerator = new WeaponGenerator();
            newWep = weaponGenerator.generateWeapon();
            isPassable = false;
        }
        else
        {
            isPassable = true;
        }
    }

    private void genEnemy()
    {
        Random rndEnm = new Random();
        int rndEnmType = rndEnm.nextInt(3)+1;

        switch (rndEnmType)
        {
            case 0:
                eneName   = "Lesser Ghoul";
                hitPoints = 30;
                strPoints = 7;
                defPoints = 4;
                break;
            case 1:
                eneName   = "Lesser Ghost";
                hitPoints = 20;
                strPoints = 6;
                defPoints = 3;
                break;
            default:
                eneName   = "Lesser Ghoul";
                hitPoints = 30;
                strPoints = 7;
                defPoints = 4;
        }
    }

    public String getEnemyName()
    {
        return eneName;
    }

    public String getPlayerName()
    {
        return plrName;
    }

    public String getEntity()
    {
        if(entityType.equalsIgnoreCase("floor"))
        {
            return FLOOR;
        }
        else if(entityType.equalsIgnoreCase("enemy"))
        {
            return ENEMY;
        }
        else if(entityType.equalsIgnoreCase("player"))
        {
            return PLAYER;
        }
        else if(entityType.equalsIgnoreCase("wall"))
        {
            return WALL;
        }
        else if(entityType.equalsIgnoreCase("weapon"))
        {
            return WEAPON;
        }
        else
        {
            return "n";
        }
    }

    public boolean isPassable()
    {
        return isPassable;
    }

    public void setHP(int hpDMG)
    {
        hitPoints -= (hpDMG - defPoints);
    }

    public int getHP()
    {
        return hitPoints;
    }

    public boolean isDead()
    {
        if(hitPoints <= 0)
        {
            return dead = true;
        }
        else
        {
            return dead = false;
        }
    }

    public String getWepName()
    {
        return wepName;
    }

    public int plrAttack()
    {
        Random rndDmg = new Random();
        atkDmg = rndDmg.nextInt(strPoints)+weapon;
        return atkDmg;
    }

    public int getDefPoints()
    {
        return defPoints;
    }

    public Weapon getNewWep()
    {
        return newWep;
    }

    public void createPlayer()
    {
        equippedWeapon = new Weapon("Unarmed", "Fists", 7, "Your weak bare hands.");
        plrName    = "Cierce";
        wepName    = equippedWeapon.getWepName();
        weapon     = equippedWeapon.getWepDmg();
        strPoints  = 5;
        defPoints  = 3;
        dead       = false;
        isPassable = false;
    }
    public void equipWeapon(Weapon equipWep)
    {
        equippedWeapon = equipWep;
        wepName = equippedWeapon.getWepName();
        weapon  = equippedWeapon.getWepDmg();
    }
}