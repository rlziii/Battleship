package userInterface;

import core.Constants;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class GameOptionDialog {

    private JDialog dialog;
    private JLabel computerAiLbl;
    private JLabel shipLayoutLbl;
    private JComboBox computerAi;
    private JComboBox shipLayout;
    private JButton saveBtn;
    private JButton canxBtn;
    private JPanel buttonPanel;
    private JPanel optionsPanel;
    private JPanel gameOptionPanel;

    String[] level = Constants.LEVEL_ARRAY;
    String[] layout = Constants.LAYOUT_ARRAY;

    Player[] playerArray;

    public GameOptionDialog(JFrame parent, Player[] inPlayers) {
        playerArray = inPlayers;
        initComponents(parent);
    }

    private void initComponents(JFrame parent) {
        dialog = new JDialog(parent, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        optionsPanel = new JPanel();
        optionsPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        gameOptionPanel = new JPanel(new GridLayout(3, 2));
        gameOptionPanel.setBorder(BorderFactory.createTitledBorder("Game Options"));
        gameOptionPanel.setPreferredSize(new Dimension(275, 125));

        computerAiLbl = new JLabel("Computer AI");
        gameOptionPanel.add(computerAiLbl);
        computerAi = new JComboBox(level);
        computerAi.setSelectedIndex(0);
        gameOptionPanel.add(computerAi);

        shipLayoutLbl = new JLabel("Ship Layout");
        gameOptionPanel.add(shipLayoutLbl);
        shipLayout = new JComboBox(layout);
        shipLayout.setSelectedIndex(0);
        gameOptionPanel.add(shipLayout);

        optionsPanel.add(gameOptionPanel);

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
            dialog.dispose();
        }
    }
}
