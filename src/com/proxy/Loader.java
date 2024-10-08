package com.proxy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Loader extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -5473115987583012536L;
    protected static Loader frame;
    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public Loader() {
        setTitle("Enable Music?");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 320, 126);
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        final JRadioButton revision474 = new JRadioButton("474 characters");
        revision474.setBounds(47, 7, 100, 23);
        contentPane.add(revision474);

        final JRadioButton revision667 = new JRadioButton("634 characters");
        revision667.setBounds(160, 7, 100, 23);
        contentPane.add(revision667);

        JButton btnStart = new JButton("Start");
        btnStart.setBounds(110, 37, 89, 23);
        contentPane.add(btnStart);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(revision474);
        buttonGroup.add(revision667);

        btnStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (revision474.isSelected()) {
                    Client.main(new String[]{"474"});
                    frame.dispose();
                }
                if (revision667.isSelected()) {
                    Client.main(new String[]{"667"});
                    frame.dispose();
                }

            }
        });

    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    frame = new Loader();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
