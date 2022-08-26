package com.player;

import com.libdialog.dialogs.SystemTrayDialog;

public class PublicThings {
    public static SystemTrayDialog tray = null;
    public static boolean isOpened = false;
    public static boolean FirstRun = true;
    public static String AppData = System.getenv("appdata");
    public static String CacheLocation = PublicThings.AppData + "\\" + "YTXP";
    public static String ConfigLocation = PublicThings.CacheLocation + "\\config.lcfg";
}
