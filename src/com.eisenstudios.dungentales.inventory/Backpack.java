package com.eisenstudios.dungentales.inventory;

 import com.eisenstudios.dungentales.entity.Space;
 import com.eisenstudios.dungentales.weapon.Weapon;

 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.KeyEvent;
 import java.util.ArrayList;

/**
 * Created by Connor on 24/04/2017.
 */
public class Backpack
{
    private JTabbedPane tab;
    private WeaponInventory weaponInventory;

    public Backpack(Space entPlayer)
    {
        tab = new JTabbedPane();
        ImageIcon icon = new ImageIcon("images/middle.gif");
        weaponInventory = new WeaponInventory(entPlayer);

        JPanel testItemsInv = new JPanel();
        JPanel testItemsInv2 = new JPanel();
        //ItemsInv itemsTab = new ItemsInv();
        //etc.

        tab.setBackground(Color.black);
        tab.setForeground(Color.white);
        tab.add(weaponInventory.getWeaponInventory(), "Weapons");
        tab.getComponentAt(0).setBackground(Color.DARK_GRAY);
        tab.add(testItemsInv2, "Stats");
        tab.add(testItemsInv, "Items");
        tab.setBorder(BorderFactory.createEmptyBorder());
    }

    public JTabbedPane getInventory()
    {
        return tab;
    }

    public void setPreferredInventorySize(int width, int height)
    {
        tab.setPreferredSize(new Dimension(width, height));
    }

    public void addWeaponToInventory(Weapon newWep)
    {
        weaponInventory.addWeapon(newWep);
    }

    public Weapon getWeaponFromInventory(String wepName)
    {
        return weaponInventory.getWeapon(wepName);
    }

}
