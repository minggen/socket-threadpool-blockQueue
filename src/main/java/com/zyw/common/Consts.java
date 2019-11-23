package com.zyw.common;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * @author wangminggen
 */
public class Consts {
    public static final int port = 9999;
    public static final String ip = "127.0.0.1";
    // 微软雅黑
    public static Font BASIC_FONT  = new Font("微软雅黑", Font.BOLD, 12);
    public static Font BASIC_FONT2 = new Font("微软雅黑", Font.TYPE1_FONT, 12);
    // 楷体
    public static Font DIALOG_FONT = new Font("楷体", Font.PLAIN, 16);

    public static Border GRAY_BORDER = BorderFactory.createLineBorder(Color.GRAY);
    public static Border ORANGE_BORDER = BorderFactory.createLineBorder(Color.ORANGE);
    public static Border LIGHT_GRAY_BORDER = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

}
