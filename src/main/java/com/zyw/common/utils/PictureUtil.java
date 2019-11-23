package com.zyw.common.utils;


import javax.swing.*;

public class PictureUtil {
    private static String basePath = PictureUtil.class.getResource("/imgs/").getPath();

    public static ImageIcon getPicture(String name) {
        ImageIcon icon = new ImageIcon(basePath + name);
        return icon;
    }
}
