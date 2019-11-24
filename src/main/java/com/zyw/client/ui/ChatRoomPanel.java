package com.zyw.client.ui;

import com.zyw.client.queue.Holder;
import com.zyw.common.Consts;
import com.zyw.common.Message;
import com.zyw.common.MsgType;
import com.zyw.common.utils.PictureUtil;
import com.zyw.common.utils.StringUtil;
import com.zyw.common.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class ChatRoomPanel extends JPanel {

    /**
     * 历史消息区域
     */
    public JTextPane historyTextPane;

    /**
     * 输入消息区域
     */
    private JTextPane inputTextPane;
    /**
     * 发送按钮
     */
    private JButton sendButton;

    private String msg;

    int userId;
    String nickName;
    String pic;

    LinkedBlockingQueue<Message> from;
    LinkedBlockingQueue<Message> to;

    public ChatRoomPanel(int userId, String nickName, String pic) {
        super();
        this.userId = userId;
        this.pic = pic;
        this.nickName = nickName;
        initGUI();
        initListener();
        initReceiveThread();
        from = Holder.fromServerMessageMaps.get(userId);
        to = Holder.toServerMessageMaps.get(userId);
    }

    private void initReceiveThread() {
        new Thread(() -> {
            while (true) {
                try {
                    Message m = from.take();
                    log.info("读用户 {} from队列, 消息 {}，渲染到界面", userId, m);
                    insertText(m);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void insertText(Message m) {
        if (m.getFrom() != userId && MsgType.TEXT.equals(m.getMsgType())) {
            insertText(TimeUtil.getTime(m.getSendTime()) + " " + m.getNickName() + "说:\n    " + m.getConn() + "\n", false);
        }
    }

    private void initGUI() {
        try {
            setLayout(null);
            setOpaque(false);
            setSize(500, 456);

            //信息面板
            JPanel infoPanel = new JPanel();
            add(infoPanel);
            infoPanel.setBounds(0, 0, 635, 70);
            infoPanel.setLayout(null);
            infoPanel.setOpaque(false);

            JLabel picture = new JLabel();
            infoPanel.add(picture);
            picture.setBounds(3, 10, 55, 55);
            picture.setBorder(Consts.LIGHT_GRAY_BORDER);

            ImageIcon picture1 = PictureUtil.getPicture("tx/00" + new Random().nextInt(10) + ".jpg");
            if (pic != null) {
                picture1 = PictureUtil.getPicture(pic);
            }
            picture.setIcon(new ImageIcon(picture1.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));

            JLabel chatName = new JLabel();
            infoPanel.add(chatName);
            chatName.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            chatName.setText(nickName);
            chatName.setBounds(70, 10, 402, 25);

            JLabel desc = new JLabel();
            infoPanel.add(desc);
            desc.setFont(Consts.BASIC_FONT2);
            desc.setBounds(70, 35, 402, 25);
            desc.setText("群描述");

            // 显示消息
            JPanel history = new JPanel();
            add(history);
            history.setBounds(3, 70, 650, 270);
            history.setOpaque(false);
            history.setLayout(new BorderLayout());

            JScrollPane historyScroll = new JScrollPane();
            history.add(historyScroll, BorderLayout.CENTER);
            historyTextPane = new JTextPane();
            historyTextPane.setEditable(false);//不允许编辑

            historyScroll.setViewportView(historyTextPane);
            historyScroll.getVerticalScrollBar().setUI(new MyScrollBarUI());
            historyScroll.setBorder(Consts.LIGHT_GRAY_BORDER);

            // 输入框
            JPanel input = new JPanel();
            add(input);
            input.setBounds(3, 370, 650, 120);
            input.setLayout(new BorderLayout());

            JScrollPane inputScroll = new JScrollPane();
            input.add(inputScroll);
            inputTextPane = new JTextPane();
            inputScroll.setViewportView(inputTextPane);
            inputScroll.getVerticalScrollBar().setUI(new MyScrollBarUI());
            inputScroll.setBorder(Consts.LIGHT_GRAY_BORDER);
            inputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            // 取消按钮（关闭）
            JButton quitButton = new JButton();
            add(quitButton);
            quitButton.setBounds(450, 492, 93, 28);
            quitButton.setBorder(Consts.LIGHT_GRAY_BORDER);
            //quitButton.setText("关闭");

            quitButton.setIcon(PictureUtil.getPicture("close_btn.png"));
            // 发送按钮
            sendButton = new JButton();
            add(sendButton);
            sendButton.setBounds(550, 492, 93, 28);
            sendButton.setBorder(Consts.LIGHT_GRAY_BORDER);
            //sendButton.setText("发送");
            sendButton.setIcon(PictureUtil.getPicture("send_btn.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        // 回车发送
        inputTextPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                msg = inputTextPane.getText();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // 发送消息
                    if (StringUtil.isEmpty(msg)) {
                        log.info("输入为空");
                        inputTextPane.setText("");
                    } else {
                        sendMsg(msg);
                    }
                }
            }


        });

        // 发送按钮事件
        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // 发送消息
                msg = inputTextPane.getText();
                if (StringUtil.isEmpty(inputTextPane.getText())) {
                    //alertWindow.showMessageDialog(null, "发送内容不能为空，请重新输入！", "友情提示");
                    inputTextPane.setText("");
                } else {
                    sendMsg(msg);
                }

            }
        });

    }

    private void sendMsg(String msg) {
        insertText(TimeUtil.getCurrentTime() + "  " + "你说" + "：\n    " + msg + "\n", true);
        Message m = Message.builder().msgType(MsgType.TEXT).from(userId).nickName(nickName).conn(msg).sendTime(System.currentTimeMillis()).build();
        inputTextPane.setText("");
        //发送给服务器.
        try {
            to.put(m);
            log.info("写用户 {} to队列, 消息 {}，write线程发送到服务器", userId, m);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void insertText(String str, boolean self) {
        SimpleAttributeSet attrset = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrset, 24);
        if (self) {
            StyleConstants.setForeground(attrset, Color.RED);
        } else {
            StyleConstants.setForeground(attrset, Color.BLUE);
        }
        Document docs = historyTextPane.getDocument();//获得文本对象
        try {
            docs.insertString(docs.getLength(), str, attrset);//对文本进行追加
            historyTextPane.setCaretPosition(historyTextPane.getStyledDocument().getLength());
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }
}
