package UI;

import Core.BattleshipClient;
import Core.Constants;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class BattleshipUI extends JFrame {

    private static JButton deploy;

    private static JPanel playerOnePanel;
    private static JPanel playerTwoPanel;
    private static JPanel gameStatusPanel;

    public static JTextArea gameStatusTextArea;

    private JComboBox<String> shipCb;
    private JComboBox<String> directionCb;

    private static String[] rowLetters;
    private static String[] columnNumbers;
    private static String[] ships;
    private static String[] direction;

    private Player playerOne;
    private Player playerTwo;
    private Player[] players;

    private BattleshipClient client;

    public void run() {
        initObjects();
        initArrays();
        initComponents();
    }
    
    private void initArrays() {
        rowLetters = Constants.ROW_LETTERS_ARRAY;
        columnNumbers = Constants.COL_NUMBERS_ARRAY;
        ships = Constants.SHIP_NAMES_ARRAY;
        direction = Constants.DIRECTION_NAMES_ARRAY;

        players = new Player[]{playerOne, playerTwo};
    }
    
    private void initObjects() {
        playerOne = new Player(getThisParent(), Constants.PLAYER_ONE_NAME);
        playerTwo = new Player(getThisParent(), Constants.PLAYER_TWO_NAME);
    }

    private void initComponents() {
        // Setup for the main Battleship window
        this.setTitle("Battleship");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 500));
        this.setMinimumSize(new Dimension(500, 500));

        // This forces the application's GUI to use the "Java Look & Feel" (aka "Metal")
        // This means that the application should look the same on macOS, Linux, UNIX, and Windows
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Setup for JMenuBar items
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic('G');
        JMenu optionMenu = new JMenu("Options");
        optionMenu.setMnemonic('O');
        menuBar.add(gameMenu);
        menuBar.add(optionMenu);
        this.setJMenuBar(menuBar);

        // Setup for gameMenu items
        GameListener gameListener = new GameListener();

        JMenuItem playerPlayer = new JMenuItem("Player vs. Player");
        playerPlayer.addActionListener(gameListener);
        playerPlayer.setEnabled(false);
        gameMenu.add(playerPlayer);

        JMenuItem playerComputer = new JMenuItem("Player vs. Computer");
        playerComputer.addActionListener(gameListener);
        playerComputer.setSelected(true);
        gameMenu.add(playerComputer);

        JMenuItem computerComputer = new JMenuItem("Computer vs. Computer");
        computerComputer.addActionListener(gameListener);
        computerComputer.setEnabled(false);
        gameMenu.add(computerComputer);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ExitListener());
        gameMenu.add(exit);

        // Setup for optionMenu items
        OptionsListener optionListener = new OptionsListener();

        JMenuItem game = new JMenuItem("Game Options");
        game.addActionListener(optionListener);
        optionMenu.add(game);

        JMenuItem player = new JMenuItem("Player Options");
        player.addActionListener(optionListener);
        optionMenu.add(player);

        // Setup for Buttons
        deploy = new JButton("Deploy Ships");
        deploy.addActionListener(new DeployListener());
        deploy.setEnabled(false);

        // Setup for Combo Boxes
        shipCb = new JComboBox<>(ships);
        shipCb.addActionListener(new ShipListener());
        shipCb.setSelectedIndex(0);

        directionCb = new JComboBox<>(direction);
        directionCb.addActionListener(new DirectionListener());
        directionCb.setSelectedIndex(0);

        // Setup for top Panel
        JPanel shipLayoutPanel = new JPanel(new FlowLayout());
        shipLayoutPanel.setBorder(BorderFactory.createTitledBorder("Select Ship and Direction"));
        shipLayoutPanel.add(shipCb);
        shipLayoutPanel.add(directionCb);
        shipLayoutPanel.add(deploy);

        gameStatusPanel = new JPanel();
        gameStatusPanel.setBorder(BorderFactory.createTitledBorder("Game Status"));
        gameStatusTextArea = new JTextArea();
        gameStatusTextArea.setLineWrap(true);
        gameStatusTextArea.setEditable(false);
        JScrollPane gameStatusScrollPane = new JScrollPane(gameStatusTextArea);
        gameStatusScrollPane.setPreferredSize(new Dimension(200, 350));
        gameStatusScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gameStatusScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        gameStatusPanel.add(gameStatusScrollPane);

        setupPlayerPanels();

        this.add(shipLayoutPanel, BorderLayout.NORTH);
        this.add(playerOnePanel, BorderLayout.WEST);
        this.setVisible(true);
    }

    void setDeployEnabled() {
        deploy.setEnabled(true);
    }

    private void disableShipLayoutPanel() {
        shipCb.setEnabled(false);
        directionCb.setEnabled(false);
        deploy.setEnabled(false);
    }

    private class DeployListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // Disables the UI components on the JPanel with ship selection options
            disableShipLayoutPanel();

            // Add a JPanel to the right of the Player One's button board with a
            // JTextArea for displaying the game status to the user
            BattleshipUI.this.setMinimumSize(new Dimension(1000, 500));
            BattleshipUI.this.add(gameStatusPanel, BorderLayout.CENTER);
            BattleshipUI.this.add(playerTwoPanel, BorderLayout.EAST);
            BattleshipUI.this.setVisible(true);

            // Call the play() method in class BattleshipClient
            client = new BattleshipClient(players);
        }
    }

    private class ShipListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int index = shipCb.getSelectedIndex();
            int shipLength = playerOne.getShips().get(index).getShipLength();
            
            playerOne.setCurrentShip(index);
            playerOne.setCurrentShipLength(shipLength);
        }
    }

    private class DirectionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (Objects.requireNonNull(directionCb.getSelectedItem()).equals("Horizontal")) {
                playerOne.setCurrentDirection(Constants.HORIZONTAL);
            } else if (directionCb.getSelectedItem().equals("Vertical")) {
                playerOne.setCurrentDirection(Constants.VERTICAL);
            }
        }
    }

    private void setupPlayerBoard(Player player, JPanel playerPanel) {
        JButton[][] playerArray = player.getBoard();

        for (int row = 0; row <= player.getBoardLength(); row++) {
            for (int col = 0; col <= player.getBoardLength(); col++) {
                if (row == 0) {
                    JLabel colLabel = new JLabel(columnNumbers[col], SwingConstants.CENTER);
                    colLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    playerPanel.add(colLabel);
                } else if (row > 0 && col == 0) {
                    JLabel rowLabel = new JLabel(rowLetters[row], SwingConstants.CENTER);
                    rowLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    playerPanel.add(rowLabel);
                } else {
                    playerPanel.add(playerArray[row - 1][col - 1]);
                }
            }
        }
    }

    private void setupPlayerPanels() {
        playerOnePanel = new JPanel(new GridLayout(11, 11));
        playerOnePanel.setMinimumSize(new Dimension(400, 400));
        playerOnePanel.setPreferredSize(new Dimension(400, 400));
        playerOnePanel.setBorder(BorderFactory.createTitledBorder("Player One"));

        playerTwoPanel = new JPanel(new GridLayout(11, 11));
        playerTwoPanel.setMinimumSize(new Dimension(400, 400));
        playerTwoPanel.setPreferredSize(new Dimension(400, 400));
        playerTwoPanel.setBorder(BorderFactory.createTitledBorder("Player Two"));

        setupPlayerBoard(playerOne, playerOnePanel);
        setupPlayerBoard(playerTwo, playerTwoPanel);
    }

    private class ExitListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int confirmed = JOptionPane.showConfirmDialog(null,
                    "Really exit Battleship?", "Exit?",
                    JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private class GameListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
//            switch (e.getActionCommand()) {
//                case "Player vs. Player":
//                    players[Constants.PLAYER_ONE].setPlayMode(Constants.HUMAN);
//                    players[Constants.PLAYER_TWO].setPlayMode(Constants.HUMAN);
//                    break;
//                case "Player vs. Computer":
//                    players[Constants.PLAYER_ONE].setPlayMode(Constants.HUMAN);
//                    players[Constants.PLAYER_TWO].setPlayMode(Constants.COMPUTER);
//                    break;
//                case "Computer vs. Computer":
//                    players[Constants.PLAYER_ONE].setPlayMode(Constants.COMPUTER);
//                    players[Constants.PLAYER_TWO].setPlayMode(Constants.COMPUTER);
//                    break;
//            }
        }
    }

    private class OptionsListener implements ActionListener {

        GameOptionDialog gameOptions;
        PlayerOptionDialog playerOptions;

        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Game Options")) {
                gameOptions = new GameOptionDialog(getThisParent());
            } else if (e.getActionCommand().equals("Player Options")) {
                playerOptions = new PlayerOptionDialog(getThisParent(), players);
            }
        }
    }
    
    private BattleshipUI getThisParent() {
        return this;
    }

    BattleshipClient getClient() {
        return client;
    }
}
