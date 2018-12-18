package UI;

import Core.Constants;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GameOptionDialog {
    private JDialog dialog;

    private final String[] level = Constants.LEVEL_ARRAY;
    private final String[] layout = Constants.LAYOUT_ARRAY;

    GameOptionDialog(JFrame parent) {
        initComponents(parent);
    }

    private void initComponents(JFrame parent) {
        dialog = new JDialog(parent, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        JPanel gameOptionPanel = new JPanel(new GridLayout(3, 2));
        gameOptionPanel.setBorder(BorderFactory.createTitledBorder("Game Options"));
        gameOptionPanel.setPreferredSize(new Dimension(275, 125));

        JLabel computerAiLbl = new JLabel("Computer AI");
        gameOptionPanel.add(computerAiLbl);
        JComboBox<String> computerAi = new JComboBox<>(level);
        computerAi.setSelectedIndex(0);
        gameOptionPanel.add(computerAi);

        JLabel shipLayoutLbl = new JLabel("Ship Layout");
        gameOptionPanel.add(shipLayoutLbl);
        JComboBox<String> shipLayout = new JComboBox<>(layout);
        shipLayout.setSelectedIndex(0);
        gameOptionPanel.add(shipLayout);

        optionsPanel.add(gameOptionPanel);

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
            dialog.dispose();
        }
    }
}
