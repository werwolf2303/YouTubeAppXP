package com.player;


import com.libdialog.dialogs.SystemTrayDialog;
import com.libdialog.enums.LookAndFeel;
import com.libdialog.utils.GlobalLookAndFeel;
import com.player.windows.MainMenu;
import com.player.windows.SearchMenu;
import com.player.windows.SettingsMenu;
import jdk.nashorn.internal.runtime.regexp.joni.Config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Initiator {
    public static void main(String[] args) {
        if(!new File(PublicThings.CacheLocation).exists()) {
            if(!new File(PublicThings.CacheLocation).mkdir()) {
                System.out.println("Error in Main");
                System.exit(1);
            }
        }else{
            PublicThings.FirstRun = false;
        }
        if(!new File(PublicThings.ConfigLocation).exists()) {
            new ConfigHandler().create();
            new ConfigHandler().write("APIKey", "YOUR_OWN_API_KEY__OPTIONAL");
            new ConfigHandler().write("ThumbHeight", "400");
            new ConfigHandler().write("ThumbWidth", "200");
            new ConfigHandler().write("OpenInBrowser", "false");
            new ConfigHandler().write("SystemTray", "true");
        }
        if(!new ConfigHandler().read("CacheLocation").equals("")) {
            PublicThings.CacheLocation = new ConfigHandler().read("CacheLocation");
        }
        try {
            if(args[0].equals("--nogui")) {
                if(args[1].equals("--nodownload")) {
                    System.out.println("YouTube Video URL: " + new Client().get(new Utils().getId(args[2]), VideoQuality.hd1080));
                }
            }
        }catch(ArrayIndexOutOfBoundsException aioobe) {

        }
        new GlobalLookAndFeel().setLookAndFeel(LookAndFeel.WindowsClassic.getClassName());
        if(new ConfigHandler().read("SystemTray").equals("true")) {
            PublicThings.tray = new SystemTrayDialog();
            SystemTrayDialog dialog = PublicThings.tray;
            dialog.add(new ResourceReader().getImage("/logo.png"), "XPYouTubePlayer");
            dialog.addEntry("Open UI", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!PublicThings.isOpened) {
                        new MainMenu().init();
                        PublicThings.isOpened = true;
                    }
                }
            });
            dialog.addEntry("Settings", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new SettingsMenu().init();
                }
            });
            dialog.addEntry("Search", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(PublicThings.isOpened) {
                        new SearchMenu().init();
                    }else{
                        new MainMenu().init();
                        new SearchMenu().init();
                        PublicThings.isOpened = true;
                    }
                }
            });
            dialog.addEntry("Exit", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            dialog.open();
        }
        PublicThings.isOpened = true;
        new MainMenu().init();
    }
}
