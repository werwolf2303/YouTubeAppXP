package com.player.windows;

import com.libdialog.dialogs.EmptyDialog;
import com.libdialog.dummy.NullLogger;
import com.player.YouTubeAPI;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SearchMenu extends JFrame {
    private class SearchPanel extends JPanel {
        private JList jcomp1;
        private JTextField jcomp2;
        private JLabel jcomp3;
        private JButton click;

        public SearchPanel(JFrame frame) {
            ArrayList<String> ids = new ArrayList<String>();
            ArrayList<String> desc = new ArrayList<String>();
            DefaultListModel<String> model = new DefaultListModel<>();
            jcomp1 = new JList (model);
            jcomp2 = new JTextField ();
            jcomp3 = new JLabel ("Search YouTube");
            click = new JButton("Search");
            JPanel control = new JPanel();
            JScrollPane forlist = new JScrollPane(jcomp1);
            setPreferredSize (new Dimension (502, 320));
            setLayout (new BorderLayout());
            control.add (jcomp3);
            control.add (jcomp2, BorderLayout.CENTER);
            control.add (click);
            add(forlist, BorderLayout.CENTER);
            add(control, BorderLayout.NORTH);
            jcomp2.setPreferredSize(new Dimension(302, 30));
            jcomp1.setBounds (90, 115, 100, 75);
            jcomp2.setBounds (210, 30, 100, 25);
            jcomp3.setBounds (95, 30, 100, 25);

            click.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!jcomp2.getText().equals("")) {
                        if(model.getSize()>0) {
                            ids.clear();
                            desc.clear();
                            model.removeAllElements();
                        }
                        String[] res = new YouTubeAPI().search(jcomp2.getText(), false);
                        for(String s : res) {
                            model.addElement(s.split(":")[0]);
                            ids.add(s.split(":")[1]);
                        }
                    }
                }
            });
            jcomp1.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        JPopupMenu menu = new JPopupMenu();
                        JMenuItem description = new JMenuItem("Show Description");
                        JMenuItem thumbnail = new JMenuItem("Show Thumbnail");
                        MouseEvent r = e;
                        description.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                JList list = (JList)r.getSource();
                                int index = list.locationToIndex(r.getPoint());
                                jcomp1.setSelectedIndex(index);
                                EmptyDialog dialog = new EmptyDialog(new NullLogger());
                                dialog.registerEventListener(new DialogListener());
                                dialog.open(frame);
                                JTextArea field = new JTextArea();
                                field.setPreferredSize(new Dimension(dialog.getDialog().getWidth(), dialog.getDialog().getHeight()));
                                field.append(new YouTubeAPI().getDescription(ids.toArray(new String[0])[list.getSelectedIndex()]).replace("\n", ""));
                                field.setEditable(false);
                                field.setLineWrap(true);
                                field.setWrapStyleWord(true);
                                field.setBackground(Color.DARK_GRAY);
                                field.setForeground(Color.WHITE);
                                dialog.getDialog().add(field, BorderLayout.CENTER);
                                dialog.getDialog().setMinimumSize(new Dimension(600, 450));
                            }
                        });
                        thumbnail.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JList list = (JList)r.getSource();
                                if(list.isSelectionEmpty()) {

                                }else {
                                    EmptyDialog dialog = new EmptyDialog(new NullLogger());
                                    dialog.registerEventListener(new DialogListener());
                                    dialog.open(frame);
                                    String path = new YouTubeAPI().getThumbnailMedium(ids.toArray(new String[0])[list.getSelectedIndex()]);
                                    ImageIcon image = null;
                                    try {
                                        URL url = new URL(path);
                                        dialog.getDialog().setPreferredSize(new Dimension(400, 200));
                                        image = new SearchMenu().scaleImage(new ImageIcon(ImageIO.read(url), "Icon"), 400, 300);
                                    } catch (IOException ex) {

                                    }
                                    JLabel label = new JLabel(image);
                                    dialog.getDialog().add(label, BorderLayout.CENTER);
                                    dialog.getDialog().setMinimumSize(new Dimension(400, 200));
                                }
                            }
                        });
                        menu.add(description);
                        menu.add(thumbnail);
                        menu.show(SearchMenu.this, e.getX(),e.getY());

                    }else{
                        JList list = (JList)e.getSource();
                        if (e.getClickCount() == 2) {
                            int index = list.locationToIndex(e.getPoint());
                            String clickedid = ids.toArray(new String[0])[index];
                            MainMenu.url.setText(new YouTubeAPI().generateUrl(clickedid));
                        }
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
    }
    ImageIcon scaleImage(ImageIcon icon, int w, int h) {
        int nw = icon.getIconWidth();
        int nh = icon.getIconHeight();
        if (icon.getIconWidth() > w) {
            nw = w;
            nh = w * icon.getIconHeight() / icon.getIconWidth();
        }

        if (nh > h) {
            nh = h;
            nw = icon.getIconWidth() * h / icon.getIconHeight();
        }

        return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, 1));
    }
    public void init() {
        this.getContentPane().add(new SearchPanel(this));
        this.setTitle("Search");
        this.setVisible(true);
        this.pack();
    }
}
