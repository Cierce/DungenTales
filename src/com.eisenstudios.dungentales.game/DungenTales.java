package com.eisenstudios.dungentales.game;

import com.eisenstudios.dungentales.entity.Space;
import com.eisenstudios.dungentales.inventory.Backpack;
import com.eisenstudios.dungentales.weapon.Weapon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.ArrayList;
import java.util.Random;

/**
 * Instead of a char two-dimensional array, I would make a custom object, such as Space,
 * and define a two-dimensional array of Spaces (eg, Space[][]).
 * <p>
 * There are a few reasons for this:
 * You can define a space in a variety of ways (rather than just 1 character).
 * For example, Space[i][j].hasTreasure() can return a boolean to let you know whether or not you found the treasure.
 * More to your question of movement, I would also recommend a few things.
 * Similar to redneckjedi's suggestion of a CheckIfMoveIsValid() method,
 * I would pass the grid and move direction as parameters and return a boolean.
 */

/**
 * Created by Connor on 24/04/2017.
 */
public class DungenTales extends JPanel implements KeyListener
{
    private static final long serialVersionUID = 1L;

    private String strMap;
    private JDialog showInventory;
    int directionCounter;
    String old_msg = "";
    String new_msg = "";
    int southCounter = 0;
    int eastCounter = 0;
    int northCounter = 0;
    int westCounter = 0;

    /**
     * MOVEMENT LOGIC FOR COLLISION, ITEMS, ENEMIES AND PLAYER
     */
    int plrPos_x = 0;
    int plrPos_y = 0;
    int walkNorth = 0;
    int walkEast = 0;
    int walkSouth = 0;
    int walkWest = 0;

    /**
     * Game Fields
     */
    private JTextArea gameField;
    private JTextPane gameDialogue;
    private JScrollPane dlgScroll;

    /**
     * Stat Related
     */
    private JLabel titStats;
    private JLabel hpStat;
    private JLabel strStat;

    /**
     * Entities
     */
    private Space[][] map;
    private Space entPlayer;
    private Space entFloor;
    private Space entWall;
    private Space entEnemy;
    private Space entWeapon;
    private ArrayList<Space> entEnemies;
    private ArrayList<Space> entWeapons;

    /**
     * Map stuff
     */
    private int mapColumnMax;
    private int mapRowMax;
    private int plrColPos = 0;
    private int plrRowPos = 0;
    private String msgDirection;

    /**
     * Backpack
     */
    private Backpack backpack;

    /**
     * Other
     */
    private Color bgColour;
    private Color fgColour;
    private GridBagConstraints gridBagCons;

    long[] recordTimes = new long[10];

    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }
        new DungenTales();
    }

    public DungenTales()
    {
        initaliseValues();
        loadInitalMap();
        loadGameFields();
        updateMap();

        JFrame frame = new JFrame();
        frame.add(this);
        frame.setSize(new Dimension(1280, 720));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void initaliseValues()
    {
        mapRowMax = 25;
        mapColumnMax = 78;
        entPlayer = new Space("player");
        entFloor  = new Space("floor");
        entWall   = new Space("wall");
        strMap = "";
        showInventory = new JDialog();
        showInventory.setBackground(Color.black);
        showInventory.setForeground(Color.white);
        bgColour = new Color(0, 0, 0);
        fgColour = new Color(255, 255, 255);
        msgDirection = "";
        gridBagCons = new GridBagConstraints();
        directionCounter = 0;
        addKeyListener(this);
        setFocusable(true);
        this.setBackground(bgColour);
        this.setLayout(new GridBagLayout());
    }

    public void loadGameFields()
    {
        /**MAP SCEEEN*/
        gameField = new JTextArea();
        gameField.setEnabled(false);
        gameField.setBackground(bgColour);
        gameField.setForeground(fgColour);
        gameField.setPreferredSize(new Dimension(780, 540));
        gameField.setFont(new Font("monospaced", Font.PLAIN, 14));
        gridBagCons.fill = GridBagConstraints.BOTH;
        gridBagCons.weightx = 1;
        gridBagCons.weighty = 1;
        gridBagCons.gridx = 0;
        gridBagCons.gridy = 0;
        this.add(gameField, gridBagCons);

        /**BACKPACK SCREEN*/
        backpack = new Backpack(entPlayer);
        backpack.setPreferredInventorySize(500, 580);
        showInventory.add(backpack.getInventory());
        showInventory.setSize(800, 500);
        showInventory.setLocationRelativeTo(null);
        showInventory.setModal(true);
        showInventory.setBackground(Color.BLACK);
        showInventory.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {

            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_I)
                {
                    showInventory.setVisible(false);
                }
            }

            @Override
            public void keyReleased(KeyEvent e)
            {

            }
        });

        /**MAP INFORMATION*/
        gameDialogue = new JTextPane();
        gameDialogue.setEnabled(false);
        gameDialogue.setBackground(bgColour);
        gameDialogue.setForeground(fgColour);
        dlgScroll = new JScrollPane(gameDialogue);
        dlgScroll.setFont(new Font("Helvetica", Font.PLAIN, 22));
        dlgScroll.setPreferredSize(new Dimension(780, 180));
        gridBagCons.ipady = 110;
        gridBagCons.gridx = 0;
        gridBagCons.gridy = 1;
        this.add(dlgScroll, gridBagCons);
    }

    public void loadInitalMap()
    {
        map = new Space[mapRowMax][mapColumnMax];

        for (int row = 0; row < mapRowMax; row++)
        {
            for (int column = 0; column < mapColumnMax; column++) //[0][0]
            {
                if (row == 2 && column == 2)
                {
                    map[row][column] = entPlayer;
                }
                else
                {
                    map[row][column] = entFloor;
                }
            }
        }

        /**GENERATES MAP PERIMETER*/
        for (int i = 0; i < mapRowMax; i++)
        {
            for (int j = 0; j < mapColumnMax; j++)
            {
                /**NORTH WALL*/
                map[0][j] = entWall;

                /**EAST WALL*/
                map[i][mapColumnMax - 1] = entWall;

                /**SOUTH WALL*/
                map[mapRowMax - 1][j] = entWall;

                /**WEST WALL*/
                map[i][0] = entWall;
            }
        }
        genEnemies(); //add the objects for gen enemies and weps here and call their respective methods
        genRndWeps();
    }

    public void genEnemies()
    {
        /**Random enemy generation*/
        Random rndEnm = new Random();
        int enmAmt = rndEnm.nextInt(3) + 1;

        int[] enmCords = new int[enmAmt * 2];
        int counter = 0;
        int arrayCounter = 0;

        entEnemies = new ArrayList<>();

        for (int i = 0; i < enmAmt * 2; i++)
        {
            enmCords[i] = rndEnm.nextInt(23) + 1;

            if (i % 2 == 1)
            {
                entEnemies.add(new Space("enemy"));
                map[enmCords[counter]][enmCords[counter + 1]] = entEnemies.get(arrayCounter);
                arrayCounter++;
                counter += 2;
            }
        }
    }

    public void genRndWeps()
    {
        /**Random wep generation*/
        Random rndEnm = new Random();
        int enmAmt = rndEnm.nextInt(2) + 1;

        int[] enmCords = new int[enmAmt * 2];
        int counter = 0;
        int arrayCounter = 0;

        entWeapons = new ArrayList<>();

        for (int i = 0; i < enmAmt * 2; i++)
        {
            enmCords[i] = rndEnm.nextInt(23) + 1;

            if (i % 2 == 1)
            {
                entWeapons.add(new Space("weapon"));
                map[enmCords[counter]][enmCords[counter + 1]] = entWeapons.get(arrayCounter);
                arrayCounter++;
                counter += 2;
            }
        }
    }

    public void updateMap()
    {
        for (Space[] column : map)
        {
            if (!(strMap.trim().length() == 0))
            {
                strMap += "\n";
            }
            for (Space ent : column)
            {
                strMap += " " + ent.getEntity();
            }
        }
        gameField.setText("");
        gameField.setText(strMap);
        strMap = "";
    }

    @Override
    public void keyPressed(KeyEvent keyPress)
    {
        if (keyPress.getKeyCode() == KeyEvent.VK_UP || keyPress.getKeyCode() == KeyEvent.VK_RIGHT || keyPress.getKeyCode() == KeyEvent.VK_DOWN || keyPress.getKeyCode() == KeyEvent.VK_LEFT)
        {
            movePlayer(keyPress);
        }
        else if(keyPress.getKeyCode() == KeyEvent.VK_P)
        {
            pickUpItem();
        }

        if (keyPress.getKeyCode() == KeyEvent.VK_I)
        {
            if (!(showInventory.isVisible()))
            {
                showInventory.setFocusable(true);
                showInventory.setVisible(true);
            }
        }

        if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            SwingUtilities.getWindowAncestor(this).dispose();
        }
    }

    public void movePlayer(KeyEvent keyPress)
    {
        for (plrPos_x = 0; plrPos_x < mapRowMax; plrPos_x++)
        {
            for (plrPos_y = 0; plrPos_y < mapColumnMax; plrPos_y++)
            {
                if (map[plrPos_x][plrPos_y] == entPlayer)
                {
                    walkNorth = plrPos_x - 1;
                    walkEast = plrPos_y + 1;
                    walkSouth = plrPos_x + 1;
                    walkWest = plrPos_y - 1;

                    map[plrPos_x][plrPos_y] = entFloor;

                    if (keyPress.getKeyCode() == KeyEvent.VK_UP)
                    {
                        if (map[walkNorth][plrPos_y].isPassable())
                        {
                            new_msg = "you walk north";
                            plrPos_x = walkNorth;
                        }
                        else if (!(map[walkNorth][plrPos_y].isPassable()))
                        {
                            doCollision(walkNorth, plrPos_y);
                        }
                    }
                    else if (keyPress.getKeyCode() == KeyEvent.VK_RIGHT)
                    {
                        if (map[plrPos_x][walkEast].isPassable())
                        {
                            new_msg = "you walk east";
                            plrPos_y = walkEast;
                        }
                        else if (!(map[plrPos_x][walkEast].isPassable()))
                        {
                            doCollision(plrPos_x, walkEast);
                        }
                    }
                    else if (keyPress.getKeyCode() == KeyEvent.VK_DOWN)
                    {
                        if (map[walkSouth][plrPos_y].isPassable())
                        {
                            new_msg = "you walk south";
                            plrPos_x = walkSouth;
                        }
                        else if (!(map[walkSouth][plrPos_y].isPassable()))
                        {
                            doCollision(walkSouth, plrPos_y);
                        }
                    }
                    else if (keyPress.getKeyCode() == KeyEvent.VK_LEFT)
                    {
                        if (map[plrPos_x][walkWest].isPassable())
                        {
                            new_msg = "you walk west";
                            plrPos_y = walkWest;
                        }
                        else if (!(map[plrPos_x][walkWest].isPassable()))
                        {
                            doCollision(plrPos_x, walkWest);
                        }
                    }
                    map[plrPos_x][plrPos_y] = entPlayer;
                    moveEnemies(plrPos_x, plrPos_y);
                    writeToGameDialogue(new_msg);
                    updateMap();
                }
            }
        }
    }

    public void pickUpItem()
    {
        for (plrPos_x = 0; plrPos_x < mapRowMax; plrPos_x++)
        {
            for (plrPos_y = 0; plrPos_y < mapColumnMax; plrPos_y++)
            {
                if (map[plrPos_x][plrPos_y] == entPlayer)
                {
                    walkNorth = plrPos_x - 1;
                    walkEast  = plrPos_y + 1;
                    walkSouth = plrPos_x + 1;
                    walkWest  = plrPos_y - 1;

                    if (!(map[walkNorth][plrPos_y].isPassable()))
                    {
                        doCollision(walkNorth, plrPos_y);
                    }
                    else if (!(map[plrPos_x][walkEast].isPassable()))
                    {
                        doCollision(plrPos_x, walkEast);
                    }
                    else if (!(map[walkSouth][plrPos_y].isPassable()))
                    {
                        doCollision(walkSouth, plrPos_y);
                    }
                    else if (!(map[plrPos_x][walkWest].isPassable()))
                    {
                        doCollision(plrPos_x, walkWest);
                    }
                    updateMap();
                }
            }
        }
    }

    public void doCollision(int plrPos_x, int plrPox_y)
    {
        /**Battle logic for player engaging enemies*/
        for (int i = 0; i < entEnemies.size(); i++)
        {
            if (map[plrPos_x][plrPox_y] == entEnemies.get(i))
            {
                if (entEnemies.get(i).isDead())
                {
                    map[plrPos_x][plrPox_y] = entFloor;
                    writeToGameDialogue("You killed " + entEnemies.get(i).getEnemyName() + " HP: "
                            + entEnemies.get(i).getHP() + ". "
                            + entPlayer.getPlayerName() + " attacked with " + entPlayer.getWepName());
                }
                else
                {
                    writeToGameDialogue(entEnemies.get(i).getEnemyName() + " HP: " + entEnemies.get(i).getHP() + ". "
                            + entPlayer.getPlayerName() + " attacked with " + entPlayer.getWepName());
                    entEnemies.get(i).setHP(entPlayer.plrAttack());
                }
            }
        }
        //for loop for enemies engaging player here
        /**Logic for picking up a weapon when walking into it*/
        for (int i = 0; i < entWeapons.size(); i++)
        {
            if (map[plrPos_x][plrPox_y] == entWeapons.get(i))
            {
                updateWeaponTab(entWeapons.get(i).getNewWep());
                map[plrPos_x][plrPox_y] = entFloor;
            }
        }
    }

    public void updateWeaponTab(Weapon newWep)
    {
        writeToGameDialogue("you pick up " + newWep.getWepName());
        backpack.addWeaponToInventory(newWep);
    }

    public void writeToGameDialogue(String msg)
    {
        gameDialogue.setText(gameDialogue.getText() + "\n" + msg);
    }

    public void moveEnemies(int plrPos_x, int plrPos_y)
    {
        int walkNorth = 0;
        int walkEast = 0;
        int walkSouth = 0;
        int walkWest = 0;

        /**ENEMY MOVEMENTS AND COLLISIONS*/
        for (int enemyPos_x = 0; enemyPos_x < mapRowMax; enemyPos_x++)
        {
            for (int enemyPos_y = 0; enemyPos_y < mapColumnMax; enemyPos_y++)
            {
                for (int i = 0; i < entEnemies.size(); i++)
                {
                    if (map[enemyPos_x][enemyPos_y] == entEnemies.get(i))
                    {
                        walkNorth = enemyPos_x - 1;
                        walkEast = enemyPos_y + 1;
                        walkSouth = enemyPos_x + 1;
                        walkWest = enemyPos_y - 1;

                        map[enemyPos_x][enemyPos_y] = entFloor;

                        if (enemyPos_x > plrPos_x)
                        {
                            if (map[walkNorth][enemyPos_y].isPassable())
                            {
                                enemyPos_x = walkNorth;
                            }
                            else
                            {

                            }
                        }
                        else if (enemyPos_y < plrPos_y)
                        {
                            if (map[enemyPos_x][walkEast].isPassable())
                            {
                                enemyPos_y = walkEast;
                            }
                            else
                            {

                            }
                        }
                        else if (enemyPos_x < plrPos_x)
                        {
                            if (map[walkSouth][enemyPos_y].isPassable())
                            {
                                enemyPos_x = walkSouth;
                            }
                            else
                            {

                            }
                        }
                        else if (enemyPos_y > plrPos_y)
                        {
                            if (map[enemyPos_x][walkWest].isPassable())
                            {
                                enemyPos_y = walkWest;
                            }
                            else
                            {

                            }
                        }
                        map[enemyPos_x][enemyPos_y] = entEnemies.get(i);
                    }
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        // TODO Auto-generated method stub

    }
}