//package com.zyw.client;
//
//import com.zyw.client.queue.Holder;
//import com.zyw.client.ui.ChatRoom;
//import com.zyw.common.Consts;
//import com.zyw.common.Message;
//import com.zyw.common.MsgType;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.EOFException;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.Socket;
//import java.util.concurrent.LinkedBlockingQueue;
//
///**
// * @author wangminggen
// */
//@Slf4j
//public class ChatWindow implements Runnable {
//    private int userId;
//
//    ObjectOutputStream oos;
//    ObjectInputStream ois;
//
//    public ChatWindow(int userId, String nickName) throws IOException {
//        this.userId = userId;
//        initBlockQueue();
//        initSocket(nickName);
//        new ChatRoom(userId, nickName);
//    }
//
//    private void initSocket(String nickName) throws IOException {
//        Socket main = new Socket(Consts.ip, Consts.port);
//        oos = new ObjectOutputStream(main.getOutputStream());
//        Message message = Message.builder().msgType(MsgType.LOGIN).from(userId).nickName(nickName).build();
//        oos.writeObject(message);
//        ois = new ObjectInputStream(main.getInputStream());
//    }
//
//
//    private void initBlockQueue() {
//
//    }
//
//    public void main() {
//        try {
//            LinkedBlockingQueue<Message> queue = Holder.fromServerMessageMaps.get(userId);
//            while (!Thread.currentThread().isInterrupted()) {
//                Message ms = (Message) ois.readObject();
//                log.info("收到服务器发过来的消息，写入队列");
//                queue.put(ms);
//            }
//        } catch (IOException e) {
//            if (e instanceof EOFException) {
//                log.info("服务器已停止");
//                return;
//            }
//            e.printStackTrace();
//        } catch (ClassNotFoundException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}