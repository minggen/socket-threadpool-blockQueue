package com.zyw.client;

import com.zyw.client.queue.Holder;
import com.zyw.client.task.Reader;
import com.zyw.client.task.Writer;
import com.zyw.client.ui.ChatRoom;
import com.zyw.common.Consts;
import com.zyw.common.Message;
import com.zyw.common.MsgType;
import com.zyw.common.utils.Pool;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class Client {
    ObjectOutputStream oos;
    ObjectInputStream ois;
    int userId;
    String nickName;

    public Client(int userId, String nickName, String pic) {
        this.userId = userId;
        this.nickName = nickName;
        if (!initSocket()) {
            log.error("初始化失败");
        }
        initBlockQueue();
        // 初始化 两个 队列
        initReaderAndWriter();
        new ChatRoom(userId, nickName, pic);
    }

    private void initReaderAndWriter() {
        // 从服务器读取消息 写入同步队列
        Pool.service.execute(new Reader(ois, userId));
        //从同步队列消费，发送到服务器
        Pool.service.execute(new Writer(oos, userId));
    }

    /**
     * 初始化同步队列
     */
    private void initBlockQueue() {
        LinkedBlockingQueue<Message> from = new LinkedBlockingQueue<>();
        Holder.fromServerMessageMaps.put(userId, from);
        LinkedBlockingQueue<Message> to = new LinkedBlockingQueue<>();
        Holder.toServerMessageMaps.put(userId, to);
    }

    /**
     * 链接服务器，初始化socket链接
     * @return
     */
    private boolean initSocket() {
        Socket socket = null;
        try {
            socket = new Socket(Consts.ip, Consts.port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            doLogin();
            ois = new ObjectInputStream(socket.getInputStream());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void doLogin() throws IOException {
        Message message = Message.builder().msgType(MsgType.LOGIN).from(userId).nickName(nickName).build();
        this.oos.writeObject(message);
    }
}
