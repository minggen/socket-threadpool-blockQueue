package com.zyw.client.ui;

import com.sun.corba.se.impl.interceptors.PICurrent;
import com.zyw.common.Consts;
import com.zyw.common.utils.PictureUtil;
import com.zyw.common.utils.Pool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ChatRoom extends JFrame {
    private JLabel minButton;
    private JLabel exitButton;
    public ChatRoomPanel sp;
    private Point point = new Point();

    public ChatRoom(int userId, String nickName, String pic) {
        super();
        sp = new ChatRoomPanel(userId, nickName, pic);
        initGUI();
        initListener();
        setLocation(800, 400);
        setVisible(true);
    }

    private void initGUI() {
        try {
            setSize(660, 550);
            setUndecorated(true);

            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setIconImage(PictureUtil.getPicture("qq_icon.png").getImage());

            JPanel contentPane = new JPanel() {
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(PictureUtil.getPicture("chat_bg.png").getImage(), 0, 0, null);
                    this.setOpaque(false);
                }
            };
            contentPane.setLayout(null);
            contentPane.setBorder(Consts.LIGHT_GRAY_BORDER);
            setContentPane(contentPane);

            JPanel downPanel = new JPanel();
            contentPane.add(downPanel);
            downPanel.setOpaque(false);
            downPanel.setBounds(1, 2, 658, 519);
            downPanel.setLayout(new BorderLayout());

            minButton = new JLabel();
            contentPane.add(minButton);
            minButton.setBounds(593, 0, 31, 20);
            minButton.setIcon(PictureUtil.getPicture("minimize.png"));

            exitButton = new JLabel();
            contentPane.add(exitButton);
            exitButton.setBounds(621, 0, 39, 20);
            exitButton.setIcon(PictureUtil.getPicture("close.png"));
            downPanel.add(sp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        // 主窗体事件
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                point.x = e.getX();
                point.y = e.getY();
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = getLocation();
                setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
            }
        });
        // 任务栏右键关闭
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();

            }
        });
        // 最小化按钮事件
        minButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                minButton.setIcon(PictureUtil.getPicture("minimize.png"));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                minButton.setIcon(PictureUtil.getPicture("minimize_active.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }
        });
        // 退出按钮事件
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setIcon(PictureUtil.getPicture("close.png"));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setIcon(PictureUtil.getPicture("close_active.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dispose();
            }
        });
    }
}
