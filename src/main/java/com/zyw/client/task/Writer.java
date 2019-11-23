package com.zyw.client.task;

import com.zyw.client.queue.Holder;
import com.zyw.common.Message;
import lombok.extern.slf4j.Slf4j;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class Writer implements Runnable {
    private final ObjectOutputStream oos;
    private Integer userId;

    public Writer(ObjectOutputStream oos, int userId) {
        this.oos = oos;
        this.userId = userId;
    }

    @Override
    public void run() {
        try {
            LinkedBlockingQueue<Message> queue = Holder.toServerMessageMaps.get(userId);
            while (!Thread.currentThread().isInterrupted()) {
                Message message = queue.take();
                log.info("读用户 {} to队列, 消息 {}，发送到服务器", userId, message);
                oos.writeObject(message);
            }
        } catch (IOException e) {
            if (e instanceof EOFException) {
                log.info("服务器已停止");
                return;
            }
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
