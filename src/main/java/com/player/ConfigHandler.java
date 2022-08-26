package com.player;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class ConfigHandler {
    File config = new File(PublicThings.ConfigLocation);
    public void create() {
        try {
            if(!config.createNewFile()) {
                System.out.println("Error in ConfigHandler");
                System.exit(1);
            }
        } catch (IOException e) {
            System.out.println("Error in ConfigHandler");
            System.exit(1);
        }
    }
    public void write(String name, String value) {
        try {
            FileInputStream propsInput = new FileInputStream(PublicThings.ConfigLocation);
            Properties prop = new Properties();
            prop.load(propsInput);
            prop.setProperty(name, value);
            prop.store(new FileOutputStream(PublicThings.ConfigLocation), null);
        } catch (IOException e) {
            System.out.println("Error in ConfigHandler");
            System.exit(1);
        }
    }
    public String read(String name) {
        String toret = "";
        try {
            FileInputStream propsInput = new FileInputStream(PublicThings.ConfigLocation);
            Properties prop = new Properties();
            prop.load(propsInput);
            toret = prop.getProperty(name);
        } catch (IOException ex) {
            System.out.println("Error in ConfigHandler");
            System.exit(1);
        }catch (NullPointerException ex2) {
            toret = "";
        }
        try {
            if (toret.equals("")) {
                return name;
            } else {
                return toret;
            }
        }catch (NullPointerException ex) {
            return "";
        }
    }
}
