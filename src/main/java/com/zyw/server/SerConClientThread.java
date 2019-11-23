package com.zyw.server;

import com.zyw.common.Message;
import lombok.extern.slf4j.Slf4j;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SerConClientThread implements Runnable {

    Socket s;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public SerConClientThread(Socket s, ObjectOutputStream oos, ObjectInputStream ois) throws IOException {
        this.s = s;
        this.ois = ois;
        this.oos = oos;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message m = (Message) ois.readObject();
                log.info("收到id {},用户{}消息 {}进行转发", m.getFrom(), m.getNickName(), m.getConn());
                // 通知 所有线程 转发消息
                HashMap<Integer, SerConClientThread> cliThreads = Holder.cliThreads;
                for (Map.Entry<Integer, SerConClientThread> integerRunnableEntry : cliThreads.entrySet()) {
                    SerConClientThread other = integerRunnableEntry.getValue();
                    other.oos.writeObject(m);
                }
            } catch (IOException e) {
                if (e instanceof EOFException) {
                    log.info("the client is stop");
                    break;
                }
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }
        }

    }
    //
    //private void sendMsg(Message m) throws IOException {
    //    log.info("向用户转发消息");
    //    oos.writeObject(m);
    //}
    //
    //private void notifyAllThreads(Message m) throws IOException {
    //    HashMap<Integer, SerConClientThread> cliThreads = Holder.cliThreads;
    //    for (Map.Entry<Integer, SerConClientThread> integerRunnableEntry : cliThreads.entrySet()) {
    //        SerConClientThread other = integerRunnableEntry.getValue();
    //        other.sendMsg(m);
    //    }
    //}
}
