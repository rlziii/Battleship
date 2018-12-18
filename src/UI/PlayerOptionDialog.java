package UI;

import Core.Constants;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

class PlayerOptionDialog {

    private JDialog dialog;

    private JComboBox<String> shipColor;
    private JComboBox<String> firstPlayer;

    private JRadioButton player1;

    private int colorIndex;

    private static final String[] colors = Constants.COLOR_NAME_ARRAY;
    private static final String[] players = {Constants.PLAYER_ONE_NAME, Constants.PLAYER_TWO_NAME, "Random"};

    private final Player[] playerArray;

    private static final Color[] colorArray = Constants.COLOR_ARRAY;

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

        JPanel optionsPanel = new JPanel();
        optionsPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        JPanel playerOptionPanel = new JPanel(new GridLayout(3, 2));
        playerOptionPanel.setBorder(BorderFactory.createTitledBorder("Player Options"));
        playerOptionPanel.setPreferredSize(new Dimension(275, 125));

        // for JRadioButtons
        ButtonGroup playerOptions = new ButtonGroup();
        player1 = new JRadioButton(Constants.PLAYER_ONE_NAME);
        player1.addActionListener(new PlayerListener());
        JRadioButton player2 = new JRadioButton(Constants.PLAYER_TWO_NAME);
        player2.addActionListener(new PlayerListener());
        playerOptions.add(player1);
        playerOptions.add(player2);
        playerOptionPanel.add(player1);
        playerOptionPanel.add(player2);

        JLabel shipColorLbl = new JLabel("Ship Color");
        playerOptionPanel.add(shipColorLbl);
        shipColor = new JComboBox<>(colors);
        shipColor.setSelectedIndex(0);
        playerOptionPanel.add(shipColor);

        JLabel firstPlayerLbl = new JLabel("Who Plays First?");
        playerOptionPanel.add(firstPlayerLbl);
        firstPlayer = new JComboBox<>(players);
        firstPlayer.setSelectedIndex(0);
        playerOptionPanel.add(firstPlayer);

        optionsPanel.add(playerOptionPanel);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(new SaveListener());
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(new CancelListener());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

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

            boolean isFirst = firstPlayer.getSelectedIndex() == 0;
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
        }

    }
}
