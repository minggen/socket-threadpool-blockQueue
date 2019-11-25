package com.zyw.common.utils;


import javax.swing.*;

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
