package com.eisenstudios.dungentales.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Connor on 24/04/2017.
 */
public class Menu
{
    private JFrame frmMenu;
    private Thread gameThread;     //JFrame frmMenu provides us with a JFrame to use for the Main Menu
    private JPanel pnlMenu;                //JPanel pnlMenu provides us with a JPanel to use with the Main Menu JFrame
    private JDialog dlgGame;               //JDialog dlgGame declares an instance of the object JDialog
    private DungenTales dungenTales;        //GamePanel brickyBreaky provides us with the core game to load and display
    private ImageIcon gameLogo;            //ImageIcon gameLogo stores an image of the Bricky Breaky game logo
    private Color bgColour;                //Color bgColour stores the colour of the background
    private JButton btnPlay;               //JButton btnPlay we launch the game from this
    private JButton btnHowToPlay;          //JButton btnHowToPlay we launch the How to Play dialog from this
    private JButton btnExit;               //JButton btnExit we exit the Main Menu from this

    /**
     * This member function is the starting point of the game, as it loads the Main Menu
     * <br>which provides us with the ability to launch the game etc.
     * <br>We also set the look and feel throughout the GUI from this member function.
     *
     * @param args String[] Required paramater to use the main() member function
     */
    public static void main(String[] args)
    {
        new Menu();
    }

    /**
     * This constructor initalise's our necessary default values and loads our Main Menu panel, buttons and frame.
     */
    Menu()
    {
        initaliseValues();
        loadPanel();
        loadButtons();
        loadFrame();
    }

    /**
     * This member function initalise's our Main Menu JFrame.
     * <br>It also adds the Main Menu JPanel to it, then displays the JFrame with the attached JPanel.
     */
    private void loadFrame()
    {
        frmMenu = new JFrame("Main Menu");
        frmMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmMenu.setSize(250, 200);
        frmMenu.setResizable(false);
        frmMenu.setLocationRelativeTo(null);
        frmMenu.setIconImage(gameLogo.getImage());
        frmMenu.add(pnlMenu);
        frmMenu.setVisible(true);
    }

    /**
     * This member function initalise's our Main Menu JButtons and displays them.
     */
    private void loadButtons()
    {
        pnlMenu.add(Box.createRigidArea(new Dimension(0, 30))); //adds a hidden 'box' that creates spacing between components
        btnPlay = new JButton("Play");
        btnPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPlay.addActionListener(new GameHandler());
        btnPlay.setBackground(bgColour);
        pnlMenu.add(btnPlay);

        pnlMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        btnHowToPlay = new JButton("How to Play");
        btnHowToPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnHowToPlay.addActionListener(new GuideHandler());
        btnHowToPlay.setBackground(bgColour);
        pnlMenu.add(btnHowToPlay);

        pnlMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        btnExit = new JButton("Exit");
        btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExit.addActionListener(new ExitHandler());
        btnExit.setBackground(bgColour);
        pnlMenu.add(btnExit);
    }

    /**
     * This member function initalise's our Main Menu JPanel to be displayed by our Main Menu JFrame.
     */
    private void loadPanel()
    {
        pnlMenu = new JPanel();
        pnlMenu.setLayout(new BoxLayout(pnlMenu, javax.swing.BoxLayout.Y_AXIS));
        pnlMenu.setBackground(bgColour);
    }

    /**
     * This member function initalise's values that are used throughout the Main Menu
     * <br>and sets the default s.com.eisenstudios.dungentalestate of isSetTheme and isSetHardMode.
     */
    private void initaliseValues()
    {
        gameLogo = new ImageIcon("resources\\logo.png");
        bgColour = new Color(0, 0, 0);
    }

    /**
     * This member function will load the GamePanel and display it.
     */
    private void loadGame()
    {
        dlgGame = new JDialog(dlgGame, "Dungen Tales v0.1");
        dungenTales = new DungenTales();
        dlgGame.add(dungenTales);
        dlgGame.setModal(true);
        dlgGame.setIconImage(gameLogo.getImage());
        dlgGame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dlgGame.setSize(1280, 720);
        dlgGame.setLocationRelativeTo(null);
        dlgGame.setVisible(false);
        dlgGame.setResizable(false);
        dlgGame.setVisible(true);
    }

    /**
     * This handler will call the loadGame() method in the MainMenu object when added to a Component as a new actionListener.
     *
     * @author Connor Phillips
     * @version 1.0
     * @since 1.0
     */
    private class GameHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            loadGame();
        }
    }

    /**
     * This handler will construct a new JOptionPane and call JOptionPane member function showMessageDialog(Component, String, int)
     * <br>and display a 'How to Play' message.
     *
     * @author Connor Phillips
     * @version 1.0
     * @since 1.0
     */
    private class GuideHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            JOptionPane.showMessageDialog(
                    frmMenu,
                    "<html><center>Help</center></html>",
                    "Instructions", -1);
        }
    }

    /**
     * This handler will exit any application when added as a new actionListener.
     *
     * @author Connor Phillips
     * @version 1.0
     * @since 1.0
     */
    private class ExitHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            System.exit(0);
        }
    }
}