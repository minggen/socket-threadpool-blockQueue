package com.zyw.common.utils;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class PictureUtil {
    public static ImageIcon getPicture(String name) {
        ImageIcon imageIcon = new ImageIcon();
        try {
            InputStream resourceAsStream = PictureUtil.class.getClassLoader().getResourceAsStream("imgs/" + name);
            if (resourceAsStream != null) {
                imageIcon = new ImageIcon(ImageIO.read(resourceAsStream));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageIcon;
    }
}
