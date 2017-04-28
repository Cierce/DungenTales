
import com.eisenstudios.dungentales.weapon.Weapon;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class JButtonTableExample
{

    public JButtonTableExample()
    {

        /*
        So your renderer needs to set the text of the button based on the data in the TableModel. If you want to change the text that is displayed on the button then you
        need to update the TableModel to the new text value. Then you tell the table to repaint that cell and your renderer will automatically pick up the new text from the model.
        */

        JFrame frame = new JFrame("JButtonTable Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Weapon wep = new Weapon("Sword", "Slayer", 10, "A sword");

        DefaultTableModel dm = new DefaultTableModel();
        dm.setDataVector(new Object[][]{{wep.getWepType(), wep.getWepName(), wep.getWepDmg(), wep.getWepDesc(), "Equip"}}, new Object[]{"Type", "Name", "Damage", "Description", "Equip"});
        Weapon fist = new Weapon("Unarmed", "Fist", 2, "Your weak bare hands.");

        dm.insertRow(1,new Object[]{fist.getWepDesc(), fist.getWepDesc(), fist.getWepDesc(), fist.getWepDesc(), "Equip"});
        JTable table = new JTable(dm);
        table.getColumn("Equip").setCellRenderer(new ButtonRenderer());
        table.getColumn("Equip").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scroll = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());//thanks mKorbel +1 http://stackoverflow.com/questions/10551995/how-to-set-jscrollpane-layout-to-be-the-same-as-jtable
        table.getColumnModel().getColumn(0).setPreferredWidth(100);//so buttons will fit and not be shown butto..

        frame.add(scroll);
        frame.pack();
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new JButtonTableExample();
            }
        });

    }
}

class ButtonRenderer extends JButton implements TableCellRenderer
{
    public static boolean equipped = false;

    public ButtonRenderer()
    {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column)
    {
        if (isSelected)
        {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        }
        else
        {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }

        if(equipped != true)
        {
            setText("Equip");
        }
        else if(equipped == true)
        {
            setText("Equipped");
        }
        else
        {
            setText("Equip");
        }
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor
{
    protected JButton button;
    private String label;
    private boolean isPushed;
    private boolean equipped;

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
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column)
    {
        if (isSelected)
        {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        }
        else
        {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }

        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue()
    {
        if (isPushed)
        {
            if(ButtonRenderer.equipped)
            {
                ButtonRenderer.equipped = false;
            }
            else if(ButtonRenderer.equipped != true)
            {
                ButtonRenderer.equipped = true;
            }
            else
            {
                button.setText("Equip");
            }
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