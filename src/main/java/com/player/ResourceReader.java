package com.player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ResourceReader {
    public ImageIcon getImage(String path) {
        try {
            return new ImageIcon(ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream(path))), "Icon");
        } catch (IOException e) {
            return null;
        }
    }
}
