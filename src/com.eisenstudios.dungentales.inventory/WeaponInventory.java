
package com.eisenstudios.dungentales.inventory;

import com.eisenstudios.dungentales.entity.Space;
import com.eisenstudios.dungentales.weapon.Weapon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Connor on 24/04/2017.
 */
public class WeaponInventory
{
    private JTable tblInventory;
    private JScrollPane scrlAvailableWeps;
    private DefaultTableModel tblWepInvModel;
    private ArrayList<Weapon> weaponInventory;
    private Space entPlayer;
    private int wepRow;

    public WeaponInventory(Space entPlayer)
    {
        this.entPlayer = entPlayer;
        weaponsTab();
    }

    public void weaponsTab()
    {
        weaponInventory = new ArrayList<>();
        Weapon fist = new Weapon("Unarmed", "Fist", 2, "Your weak bare hands.");
        weaponInventory.add(fist);
        wepRow = 0;

        tblWepInvModel = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return column == 4 ? true : false;
            }
        };

        tblWepInvModel.setDataVector(new Object[][]{{fist.getWepType(), fist.getWepName(), fist.getWepDmg(), fist.getWepDesc(), "Equip"}}, new Object[]{"TYPE", "NAME", "DAMAGE", "DESCRIPTION", "EQUIPPED"});
        tblInventory = new JTable(tblWepInvModel);

        tblInventory.getColumn("EQUIPPED").setCellRenderer(new ButtonRenderer());
        tblInventory.getColumn("EQUIPPED").setCellEditor(new ButtonEditor(new JCheckBox()));

        tblWepInvModel.isCellEditable(0, 4);
        tblInventory.setFocusable(false);
        tblInventory.setRowSelectionAllowed(false);
        tblInventory.getTableHeader().setReorderingAllowed(false);
        tblInventory.getTableHeader().setResizingAllowed(false);
        scrlAvailableWeps = new JScrollPane(tblInventory);
        scrlAvailableWeps.getViewport().setBackground(Color.black);
    }

    public JScrollPane getWeaponInventory()
    {
        return scrlAvailableWeps;
    }

    public Weapon getWeapon(String wepName)
    {
        for (int i = 0; i < weaponInventory.size(); i++)
        {
            if (weaponInventory.get(i).getWepName().equalsIgnoreCase(wepName))
            {
                return weaponInventory.get(i);
            }
        }
        return null;
    }

    public void addWeapon(Weapon newWep)
    {
        weaponInventory.add(newWep);
        tblWepInvModel.insertRow(weaponInventory.size() - 1, new Object[]{newWep.getWepType(), newWep.getWepName(), newWep.getWepDmg(), newWep.getWepDesc(), "Equip"});
    }

    class ButtonEditor extends DefaultCellEditor
    {
        protected JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox)
        {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
        {
            if (isSelected)
            {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            }
            wepRow = row;
            isPushed = true;
            button.setText("equip");
            return button;
        }

        @Override
        public Object getCellEditorValue()
        {
        /*
        So your renderer needs to set the text of the button based on the data in the TableModel. If you want to change the text that is displayed on the button then you
        need to update the TableModel to the new text value. Then you tell the table to repaint that cell and your renderer will automatically pick up the new text from the model.
        */
            if (isPushed)
            {

            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing()
        {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer
    {
        public ButtonRenderer()
        {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            if (isSelected)
            {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            }
            else if (isSelected)
            {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            return this;
        }
    }
}

