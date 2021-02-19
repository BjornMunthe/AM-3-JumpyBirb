package se.yrgo.am3.gameobjects;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HighscorePopup extends JPanel implements ActionListener {

    private JTextField jtf;
    private JButton jbtnSub;
    private JLabel jlabPrompt, jlabContents;
    private String name;

    HighscorePopup() {
        super();
        JFrame jfrm = new JFrame("New Highscore!");

        jfrm.setLayout(new FlowLayout());

        jfrm.setSize(240, 120);

        jfrm.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        jtf = new JTextField(10);

        jtf.setActionCommand("myTF");

        jbtnSub = new JButton("Submit");

        jtf.addActionListener(this);
        jbtnSub.addActionListener(this);

        jlabPrompt = new JLabel("Enter your name: ");
        jlabContents = new JLabel(" ");

        jfrm.add(jlabPrompt);
        jfrm.add(jtf);
        jfrm.add(jbtnSub);
        jfrm.add(jlabContents);

        jfrm.setVisible(true);
    }

    private void setName() {
        this.setVisible(false);
        if (jtf.getText().length()> 8) {
            name = jtf.getText().substring(0,8);
        } else {
            name = jtf.getText();
        }
    }

    public String getName() {
        return name;
    }

    public void actionPerformed(ActionEvent e) {

        if (jtf.getText().equals("")) {
            jlabContents.setText("Enter your name to set highscore");
        } else {
            setName();
        }
    }
}


