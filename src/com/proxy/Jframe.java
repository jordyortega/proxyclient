package com.proxy;

import com.proxy.sign.signlink;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class Jframe extends Client implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = -6978617783576386732L;
    private static JFrame frame;
    private static boolean musicToggled = true;

    public Jframe(String args[]) {
        super();
        try {
            signlink.startpriv(InetAddress.getByName(server));
            initUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void takeScreenshot() {
        try {
            Window window = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();
            Point point = window.getLocationOnScreen();
            int x = (int) point.getX();
            int y = (int) point.getY();
            int w = window.getWidth();
            int h = window.getHeight();
            Robot robot = new Robot(window.getGraphicsConfiguration().getDevice());
            Rectangle captureSize = new Rectangle(x, y, w, h);
            BufferedImage bufferedimage = robot.createScreenCapture(captureSize);
            String imageName = JOptionPane.showInputDialog(frame, "Image Name :", "Screenshot", JOptionPane.OK_CANCEL_OPTION);
            if (!imageName.equals("null"))
                ImageIO.write(bufferedimage, "png", new File(signlink.findcachedir() + "./Screenshots/" + imageName + ".png"));
        } catch (Exception e) {
        }
    }

    public void initUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JPopupMenu.setDefaultLightWeightPopupEnabled(false);
            frame = new JFrame("Web3Scape - Bringing You Back What You Loved");
            frame.setLayout(new BorderLayout());
            setFocusTraversalKeysEnabled(false);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JMenuBar menubar = new JMenuBar();

            JLabel title = new JLabel("Crisis");
            title.setFont(new Font("Verdana", Font.BOLD + Font.ITALIC, 12));
            Border border = title.getBorder();
            Border margin = new EmptyBorder(0, 10, 0, 0);
            title.setBorder(new CompoundBorder(border, margin));

            JLabel title2 = new JLabel("X");
            title2.setFont(new Font("Verdana", Font.BOLD + Font.ITALIC, 12));
            Border border3 = title2.getBorder();
            Border margin3 = new EmptyBorder(0, 0, 0, 10);
            title2.setBorder(new CompoundBorder(border3, margin3));
            title2.setForeground(Color.RED);

            JLabel sepText = new JLabel("|");
            sepText.setFont(new Font("Verdana", Font.PLAIN, 12));
            Border border2 = sepText.getBorder();
            Border margin2 = new EmptyBorder(0, 5, 0, 5);
            sepText.setBorder(new CompoundBorder(border2, margin2));

            JButton website = new JButton("Website");
            website.setFocusPainted(false);

            JButton forums = new JButton("Forums");
            forums.setFocusPainted(false);

            JButton vote = new JButton("Vote");
            vote.setFocusPainted(false);

            JButton highscores = new JButton("Highscores");
            highscores.setFocusPainted(false);

            JButton music = new JButton("Toggle music off");
            music.setFocusPainted(false);

            JButton hp = new JButton("Toggle HP on");
            hp.setFocusPainted(false);

            JButton world = new JButton("World Map");
            world.setFocusPainted(false);

            menubar.add(title);
            menubar.add(title2);
            menubar.add(website);
            menubar.add(forums);
            menubar.add(vote);
            menubar.add(highscores);
            menubar.add(sepText);
            menubar.add(music);
            menubar.add(hp);
            menubar.add(world);

            website.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI("http://facebook.com"));
                    } catch (IOException | URISyntaxException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });
            forums.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI("http://facebook.com"));
                    } catch (IOException | URISyntaxException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });
            vote.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI("http://facebook.com"));
                    } catch (IOException | URISyntaxException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });
            highscores.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI("http://facebook.com"));
                    } catch (IOException | URISyntaxException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });

            music.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (musicToggled) {
                        setMidiVolume(0);
                        music.setText("Toggle music on");
                        musicToggled = false;
                    } else {
                        setMidiVolume(256);
                        music.setText("Toggle music off");
                        musicToggled = true;
                    }
                }
            });
            hp.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!hpToggled) {
                        hp.setText("Toggle HP off");
                        hpToggled = true;
                    } else {
                        hp.setText("Toggle HP on");
                        hpToggled = false;
                    }
                }
            });
            world.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI("http://facebook.com"));
                    } catch (IOException | URISyntaxException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });

            JPanel gamePanel = new JPanel();
            gamePanel.setLayout(new BorderLayout());
            gamePanel.add(this);
            gamePanel.setPreferredSize(new Dimension(765, 503));
            frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true); // can see the client
            frame.setResizable(false); // resizeable frame
            //frame.setJMenuBar(menubar);
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public URL getCodeBase() {
        try {
            return new URL("http://" + server + "/cache");
        } catch (Exception e) {
            return super.getCodeBase();
        }
    }

    public URL getDocumentBase() {
        return getCodeBase();
    }

    public void loadError(String s) {
        System.out.println("loadError: " + s);
    }

    public String getParameter(String key) {
        return "";
    }

    public void actionPerformed(ActionEvent evt) {
    }

}