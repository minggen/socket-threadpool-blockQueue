package com.zyw.client.task;

import com.zyw.client.queue.Holder;
import com.zyw.common.Message;
import lombok.extern.slf4j.Slf4j;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class Reader implements Runnable {
    private final ObjectInputStream ois;
    private Integer userId;

    public Reader(ObjectInputStream ois, int userId) {
        this.ois = ois;
        this.userId = userId;
    }

    @Override
    public void run() {
        try {
            LinkedBlockingQueue<Message> queue = Holder.fromServerMessageMaps.get(userId);
            while (!Thread.currentThread().isInterrupted()) {
                Message ms = (Message) ois.readObject();
                log.info("写用户 {} from队列, 消息{} 写入", userId, ms);
                queue.put(ms);
            }
        } catch (IOException e) {
            if (e instanceof EOFException) {
                log.info("服务器已停止");
                return;
            }
            e.printStackTrace();
        } catch (ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
