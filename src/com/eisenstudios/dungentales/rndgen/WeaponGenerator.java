package com.eisenstudios.dungentales.rndgen;

import com.eisenstudios.dungentales.weapon.Weapon;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Connor on 24/04/2017.
 */
public class WeaponGenerator
{
    private String wepType;
    private String wepName;
    private String wepName2;
    private String wepDesc;
    private String descTier;
    private String wepTier;
    private int wepDmg;

    private ArrayList<String> wepTypes;
    private ArrayList<String> wepNamesAdjective;
    private ArrayList<String> wepNamesConnective;
    private ArrayList<String> wepNames;

 /*   public static void main(String[] args)
    {
        WeaponGenerator wepGen = new WeaponGenerator();

        /*
        int cntWeak       = 0;
        int cntGood       = 0;
        int cntStrong     = 0;
        int cntVeryStrong = 0;

        for(int i = 0; i < 1000; i++)
        {

            String wep = wepGen.generateWeapon();
            if(wep.equalsIgnoreCase("weak"))
            {
                cntWeak++;
            }
            else  if(wep.equalsIgnoreCase("good"))
            {
                cntGood++;
            }
            else  if(wep.equalsIgnoreCase("strong"))
            {
                cntStrong++;
            }
            else  if(wep.equalsIgnoreCase("very strong"))
            {
                cntVeryStrong++;
            }
        }
        System.out.println("\n\n\nChance of generating a weak weapon: " + cntWeak/10);
        System.out.println("Chance of generating a good weapon: " + cntGood/10);
        System.out.println("Chance of generating a strong weapon: " + cntStrong/10);
        System.out.println("Chance of generating a very strong weapon: " + cntVeryStrong/10);

        System.out.println("\nWep type: " + wep.getWepType());
        System.out.println("Wep name: " + wep.getWepName());
        System.out.println("Wep dmg: "  + wep.getWepDmg());
        System.out.println("Wep desc: " + wep.getWepDesc());
    }*/

    public WeaponGenerator()
    {
        wepTypes = new ArrayList<>();
        wepTypes.add(0, "Claw");
        wepTypes.add(1, "Sword");
        wepTypes.add(2, "Ghost");
        wepTypes.add(3, "Spear");
        wepTypes.add(4, "Greatsword");

        wepNames = new ArrayList<>();
        wepNames.add(0, "Destroyer");
        wepNames.add(1, "King");
        wepNames.add(2, "Blade");
        wepNames.add(3, "Zweihander");
        wepNames.add(4, "Plasma");

        wepNamesConnective = new ArrayList<>();
        wepNamesConnective.add(0, "of");

        wepNamesAdjective = new ArrayList<>();
        wepNamesAdjective.add(0, "Power");
        wepNamesAdjective.add(1, "Kings");
        wepNamesAdjective.add(2, "Evil");
        wepNamesAdjective.add(3, "Holy");
    }

    public Weapon generateWeapon()
    {
        Random rndChance = new Random();

        int tierListChance = rndChance.nextInt(20);
        int rndWepType = rndChance.nextInt(wepTypes.size());
        int rndWepNames = rndChance.nextInt(wepNames.size());
        int rndWepNamesConnective = rndChance.nextInt(wepNamesConnective.size());
        int rndWepNamesAdjective = rndChance.nextInt(wepNamesAdjective.size());

        if (tierListChance <= 9) //tier 1 // 0 - 20
        {
            wepTier  = "weak";
        }
        else if (tierListChance >= 10 && tierListChance <= 15) //tier 2
        {
            wepTier  = "good";
        }
        else if (tierListChance >= 16 && tierListChance <= 18) //tier 3
        {
            wepTier = "strong";
        }
        else if (tierListChance >= 19) //tier 4
        {
            wepTier = "very strong";
        }

        if(wepTier.equalsIgnoreCase("weak"))
        {
            descTier = "ewak";
            wepDmg = rndChance.nextInt(4) + 1; //dmg chance between 1 and 4
        }
        else if(wepTier.equalsIgnoreCase("good"))
        {
            descTier = "good";
            wepDmg = rndChance.nextInt(9) + 5; //dmg chance between 5 and 9
        }
        else if(wepTier.equalsIgnoreCase("strong"))
        {
            descTier = "strong";
            wepDmg = rndChance.nextInt(13) + 10; //dmg chance between 10 and 13
        }
        else if(wepTier.equalsIgnoreCase("very strong"))
        {
            descTier = "very strong";
            wepDmg = rndChance.nextInt(17) + 14; //dmg chance between 14 and 17
        }

        wepType  = wepTypes.get(rndWepType);
        wepName2 = wepNamesConnective.get(rndWepNamesConnective) + " " + wepNamesAdjective.get(rndWepNamesAdjective);

        if (wepType == "Sword")
        {
            wepName = wepType + " " + wepName2;
            wepDesc = "A " + descTier + " " + wepType.toLowerCase();
        }
        else if (wepType == "Greatsword")
        {
            wepName = wepType + " " + wepName2;
            wepDesc = "A " + descTier + " " + wepType.toLowerCase();
        }
        else if( wepType == "Spear")
        {
            wepName = wepType + " " + wepName2;
            wepDesc = "A " + descTier + " " + wepType.toLowerCase();
        }
        else if (wepType == "Ghost")
        {
            wepName = wepType + " " + wepName2;
            wepDesc = "A " + descTier + " " + wepType.toLowerCase() + " like weapon.";
        }
        else if (wepType == "Claw")
        {
            wepName = wepType + " " + wepName2;
            wepDesc = "A " + descTier  + " set of " + wepType.toLowerCase() + "s.";
        }

        return new Weapon(wepType, wepName, wepDmg, wepDesc);
    }
}