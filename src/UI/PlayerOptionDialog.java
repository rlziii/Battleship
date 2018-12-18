package UI;

import Core.Constants;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.*;

public class PlayerOptionDialog {

    private JDialog dialog;

    private JPanel playerOptionPanel;
    private JPanel optionsPanel;
    private JPanel buttonPanel;

    private JLabel shipColorLbl;
    private JLabel firstPlayerLbl;

    private JComboBox shipColor;
    private JComboBox firstPlayer;

    private JButton saveBtn;
    private JButton canxBtn;

    private JRadioButton player1;
    private JRadioButton player2;
    private ButtonGroup playerOptions; // for JRadioButtons

    private int colorIndex;

    private static String[] colors = Constants.COLOR_NAME_ARRAY;
    private static String[] players = {Constants.PLAYER_ONE_NAME, Constants.PLAYER_TWO_NAME, "Random"};

    private Player currentPlayer;

    private Player[] playerArray;

    private static Color[] colorArray = Constants.COLOR_ARRAY;

    public PlayerOptionDialog(JFrame parent, Player[] inPlayers) {
        playerArray = inPlayers;
        initComponents(parent);
        restoreSettings();
    }

    private void restoreSettings() {
        if (playerArray[Constants.PLAYER_ONE].getIsFirst()) {
            firstPlayer.setSelectedIndex(Constants.PLAYER_ONE);
        } else {
            firstPlayer.setSelectedIndex(Constants.PLAYER_TWO);
        }

        player1.setEnabled(true);

        for (int index = 0; index < colorArray.length; index++) {
            if (colorArray[index] == playerArray[Constants.PLAYER_ONE].getShipColor()) {
                colorIndex = index;
            }
        }

        shipColor.setSelectedIndex(colorIndex);
    }

    private void initComponents(JFrame parent) {
        dialog = new JDialog(parent, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        optionsPanel = new JPanel();
        optionsPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        playerOptionPanel = new JPanel(new GridLayout(3, 2));
        playerOptionPanel.setBorder(BorderFactory.createTitledBorder("Player Options"));
        playerOptionPanel.setPreferredSize(new Dimension(275, 125));

        playerOptions = new ButtonGroup();
        player1 = new JRadioButton(Constants.PLAYER_ONE_NAME);
        player1.addActionListener(new PlayerListener());
        player2 = new JRadioButton(Constants.PLAYER_TWO_NAME);
        player2.addActionListener(new PlayerListener());
        playerOptions.add(player1);
        playerOptions.add(player2);
        playerOptionPanel.add(player1);
        playerOptionPanel.add(player2);

        shipColorLbl = new JLabel("Ship Color");
        playerOptionPanel.add(shipColorLbl);
        shipColor = new JComboBox(colors);
        shipColor.setSelectedIndex(0);
        playerOptionPanel.add(shipColor);

        firstPlayerLbl = new JLabel("Who Plays First?");
        playerOptionPanel.add(firstPlayerLbl);
        firstPlayer = new JComboBox(players);
        firstPlayer.setSelectedIndex(0);
        playerOptionPanel.add(firstPlayer);

        optionsPanel.add(playerOptionPanel);

        saveBtn = new JButton("Save");
        saveBtn.addActionListener(new SaveListener());
        canxBtn = new JButton("Cancel");
        canxBtn.addActionListener(new CancelListener());

        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        buttonPanel.add(saveBtn);
        buttonPanel.add(canxBtn);

        dialog.setTitle("Options");
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().add(optionsPanel, BorderLayout.CENTER);
        dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        dialog.setMinimumSize(new Dimension(300, 225));
        dialog.setLocation(100, 137);
        dialog.setVisible(true);
    }

    private class CancelListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            dialog.dispose();
        }
    }

    private class SaveListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Player selectedPlayer;

            if (player1.isSelected()) {
                selectedPlayer = playerArray[Constants.PLAYER_ONE];
            } else {
                selectedPlayer = playerArray[Constants.PLAYER_TWO];
            }

            // Need to update the color of the ships already placed
            selectedPlayer.updateShipColor(selectedPlayer.getShipColor(), colorArray[shipColor.getSelectedIndex()]);

            boolean isFirst = firstPlayer.getSelectedIndex() == 0 ? true : false;
            playerArray[Constants.PLAYER_ONE].setIsFirst(isFirst);
            playerArray[Constants.PLAYER_TWO].setIsFirst(!isFirst);

            dialog.dispose();
        }
    }

    private class PlayerListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int playerIndex = player1.isSelected() ? Constants.PLAYER_ONE : Constants.PLAYER_TWO;
            
            int colorIndex = Arrays.asList(colorArray).indexOf(playerArray[playerIndex].getShipColor());

            shipColor.setSelectedIndex(colorIndex);

            if (playerArray[Constants.PLAYER_ONE].getIsFirst()) {
                firstPlayer.setSelectedIndex(Constants.PLAYER_ONE);
            } else {
                firstPlayer.setSelectedIndex(Constants.PLAYER_TWO);
            }

            currentPlayer = playerArray[playerIndex];
        }

    }
}
