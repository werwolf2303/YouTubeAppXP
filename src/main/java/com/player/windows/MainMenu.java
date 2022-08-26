package com.player.windows;

import com.github.kiulian.downloader.model.videos.quality.VideoQuality;
import com.player.Client;
import com.player.PublicThings;
import com.player.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class MainMenu extends JFrame {
    public static JTextField url;
    private JLabel jcomp2;
    private JButton play;
    private JComboBox jcomp4;
    private JLabel jcomp5;

    public class Content extends JPanel{
        boolean forceQuality = true;
        String quality = "";
        public Content() {
            //construct preComponents
            String[] jcomp4Items = {"480p", "720p", "1080p"};
            //construct components
            url = new JTextField(5);
            jcomp2 = new JLabel("YouTube URL");
            play = new JButton("Play Video");
            jcomp4 = new JComboBox(jcomp4Items);
            jcomp5 = new JLabel("Force Quality");
            jcomp4.setSelectedIndex(-1);
            //adjust size and set layout
            setPreferredSize(new Dimension(474, 209));
            setLayout(null);
            jcomp4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forceQuality = true;
                    quality = jcomp4.getSelectedItem().toString();
                }
            });
            //add components
            add(url);
            add(jcomp2);
            add(play);
            add(jcomp4);
            add(jcomp5);
            boolean simulate = false;
            play.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String open = null;
                        if(forceQuality) {
                            if(quality.equals("1080p")) {
                                open = new Client().get(new Utils().getId(url.getText()), VideoQuality.hd1080);
                            }else{
                                if(quality.equals("720p")) {
                                    open = new Client().get(new Utils().getId(url.getText()), VideoQuality.hd720);
                                }else{
                                    open = new Client().minimalGet(new Utils().getId(url.getText()));
                                }
                            }
                        }else {
                            open = new Client().get(new Utils().getId(url.getText()));
                        }
                        URL website = new URL(open);
                        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                        FileOutputStream fos = new FileOutputStream(PublicThings.CacheLocation + "\\" + "video.mp4");
                        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                        if(!simulate) {
                            ProcessBuilder pb = new ProcessBuilder("\"C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe\"", PublicThings.CacheLocation + "\\" + "video.mp4");
                            pb.start();
                        }else{
                            ProcessBuilder pb = new ProcessBuilder("\"C:\\Program Files\\VLC Plus Player\\vlc.exe\"", open);
                            pb.start();
                        }
                        } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            //set component bounds (only needed by Absolute Positioning)
            url.setBounds(35, 65, 405, 25);
            jcomp2.setBounds(35, 25, 100, 25);
            play.setBounds(35, 135, 210, 30);
            jcomp4.setBounds(340, 135, 100, 25);
            jcomp5.setBounds(270, 105, 100, 25);
        }
    }
    public void init() {
        JMenuBar bar = new JMenuBar();
        JMenu edit = new JMenu("Edit");
        JMenuItem settings = new JMenuItem("Settings");
        JMenu menu = new JMenu("YouTube Tools");
        JMenuItem search = new JMenuItem("Search");
        menu.add(search);
        edit.add(settings);
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsMenu().init();
            }
        });
        bar.add(menu);
        bar.add(edit);
        JFrame f = this;
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchMenu().init();
            }
        });
        this.setTitle("YouTube Player");
        this.setJMenuBar(bar);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                PublicThings.isOpened = false;
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        this.getContentPane().add(new Content());
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
    }
}
