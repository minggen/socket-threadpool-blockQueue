package com.zyw.client.queue;

import com.zyw.common.Message;

import java.util.ConcurrentModificationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author wangminggen
 */
public class Holder {
    public static ConcurrentHashMap<Integer, LinkedBlockingQueue<Message>> fromServerMessageMaps = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, LinkedBlockingQueue<Message>> toServerMessageMaps = new ConcurrentHashMap<>();
}
