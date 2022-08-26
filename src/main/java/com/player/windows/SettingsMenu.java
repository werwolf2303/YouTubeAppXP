package com.player.windows;

import com.player.ConfigHandler;
import com.player.PublicThings;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsMenu extends JFrame {
    private class Content extends JPanel {
        private JTextField jcomp1;
        private JLabel jcomp2;
        private JLabel jcomp3;
        private JLabel jcomp4;
        private JTextField jcomp5;
        private JRadioButton jcomp6;
        private JButton jcomp7;
        private JLabel jcomp8;
        private JTextField jcomp9;
        private JButton jcomp10;
        private JCheckBox jcomp11;
        private JTextField jcomp12;
        private JLabel jcomp13;
        private JButton jcomp14;
        private JCheckBox jcomp15;

        public Content() {
            jcomp1 = new JTextField ("400", 5);
            jcomp2 = new JLabel ("Set thumbnail size");
            jcomp3 = new JLabel ("width:");
            jcomp4 = new JLabel ("height:");
            jcomp5 = new JTextField ("200", 5);
            jcomp6 = new JRadioButton ("AutoScale"); //Disabled
            jcomp7 = new JButton ("Set");
            jcomp8 = new JLabel ("YouTube APIKEY");
            jcomp9 = new JTextField (5);
            jcomp10 = new JButton ("Set");
            jcomp11 = new JCheckBox ("Enable system tray icon", Boolean.parseBoolean(new ConfigHandler().read("SystemTray")));
            jcomp12 = new JTextField (5);
            jcomp13 = new JLabel ("Custom cache location");
            jcomp14 = new JButton ("Set");
            jcomp15 = new JCheckBox ("Open video in browser", Boolean.parseBoolean(new ConfigHandler().read("OpenInBrowser")));
            jcomp5.setText(new ConfigHandler().read("ThumbHeight"));
            jcomp1.setText(new ConfigHandler().read("ThumbWidth"));
            if(!new ConfigHandler().read("APIKey").equals("YOUR_OWN_API_KEY__OPTIONAL")) {
                jcomp9.setText(new ConfigHandler().read("APIKey"));
            }
            if(new ConfigHandler().read("CacheLocation").equals("C:\\Users\\gianl\\AppData\\Roaming\\YTXP")) {
            }else{
                if(!new ConfigHandler().read("CacheLocation").equals("")) {
                    jcomp12.setText(new ConfigHandler().read("CacheLocation"));
                }
            }
            jcomp7.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!jcomp1.getText().equals("")&&!jcomp5.getText().equals("")) {
                        new ConfigHandler().write("ThumbWidth", jcomp1.getText());
                        new ConfigHandler().write("ThumbHeight", jcomp5.getText());
                    }
                }
            });
            jcomp10.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!jcomp9.getText().equals("")) {
                        new ConfigHandler().write("APIKey", jcomp9.getText());
                    }
                }
            });
            jcomp11.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(jcomp11.isSelected()) {
                        new ConfigHandler().write("SystemTray", "true");
                    }else{
                        new ConfigHandler().write("SystemTray", "false");
                    }
                }
            });
            jcomp14.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!jcomp12.getText().equals("")) {
                        new ConfigHandler().write("CacheLocation", jcomp12.getText());
                    }
                }
            });
            jcomp15.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if(jcomp15.isSelected()) {
                        new ConfigHandler().write("OpenInBrowser", "true");
                    }else{
                        new ConfigHandler().write("OpenInBrowser", "false");
                    }
                }
            });
            jcomp6.setEnabled (false);
            setPreferredSize (new Dimension(478, 341));
            setLayout (null);
            add (jcomp1);
            add (jcomp2);
            add (jcomp3);
            add (jcomp4);
            add (jcomp5);
            add (jcomp6);
            add (jcomp7);
            add (jcomp8);
            add (jcomp9);
            add (jcomp10);
            add (jcomp11);
            add (jcomp12);
            add (jcomp13);
            add (jcomp14);
            add (jcomp15);
            jcomp1.setBounds (55, 55, 100, 25);
            jcomp2.setBounds (5, 15, 140, 25);
            jcomp3.setBounds (10, 55, 45, 25);
            jcomp4.setBounds (170, 55, 45, 25);
            jcomp5.setBounds (215, 55, 100, 25);
            jcomp6.setBounds (325, 55, 85, 25);
            jcomp7.setBounds (410, 55, 55, 25);
            jcomp8.setBounds (5, 100, 100, 25);
            jcomp9.setBounds (5, 135, 465, 25);
            jcomp10.setBounds (5, 170, 465, 25);
            jcomp11.setBounds (10, 220, 165, 25);
            jcomp12.setBounds (5, 300, 365, 25);
            jcomp13.setBounds (5, 265, 145, 25);
            jcomp14.setBounds (375, 300, 100, 25);
            jcomp15.setBounds (175, 220, 160, 25);
        }
    }
    public void init() {
        this.setTitle("Settings");
        this.setResizable(false);
        this.getContentPane().add(new Content());
        this.setVisible(true);
        this.pack();
    }
}
