package se.yrgo.am3.gameobjects;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Popup implements ActionListener {

    JTextField jtf;
    JButton jbtnRev;
    JLabel jlabPrompt, jlabContents;
    String name;

    Popup() {
        JFrame jfrm = new JFrame("Use a Text Field");

        jfrm.setLayout(new FlowLayout());

        jfrm.setSize(240, 120);

        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jtf = new JTextField(10);

        jtf.setActionCommand("myTF");

        jbtnRev = new JButton("Reverse");

        jtf.addActionListener(this);
        jbtnRev.addActionListener(this);

        jlabPrompt = new JLabel("Enter Text: ");
        jlabContents = new JLabel(" ");

        jfrm.add(jlabPrompt);
        jfrm.add(jtf);
        jfrm.add(jbtnRev);
        jfrm.add(jlabContents);

        jfrm.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (jtf.getText().equals("")) {
            jlabContents.setText("Enter your name to submit higscore");

        } else {

            name = jtf.getText();
        }
    }
}


