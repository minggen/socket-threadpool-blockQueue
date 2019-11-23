package com.zyw.server;


import com.zyw.common.Message;
import com.zyw.common.MsgType;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Server {
    public Server() {
        try {
            //在9999监听
            log.info("我是服务器主线程，在9999监听");
            ExecutorService executorService = Executors.newCachedThreadPool();
            ServerSocket ss = new ServerSocket(9999);
            //阻塞,等待连接
            do {
                Socket s = ss.accept();
                log.info("主线程得到新的连接，开启子线程处理");
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                Message m = (Message) ois.readObject();
                switch (m.getMsgType()) {
                    case LOGIN:
                        log.info("新客户端报道");
                        log.info("用户: {},Id: {} 请求登陆", m.getFrom(), m.getNickName());
                        Message replayMsg = Message.builder().conn("ok").msgType(MsgType.LOGIN_SUCCESS).build();
                        // 回复登陆成功
                        oos.writeObject(replayMsg);
                        // 开启子线程去接受消息
                        SerConClientThread thread = new SerConClientThread(s, oos, ois);
                        // 缓存子线程
                        Holder.cliThreads.put(m.getFrom(), thread);
                        // 开始接受消息
                        executorService.execute(thread);
                        break;
                }
            } while (!(executorService.isTerminated()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
