package com.zyw.main;

import com.zyw.client.Client;
import com.zyw.common.utils.NameRandom;

/**
 * @author wangminggen
 */
public class RunClient {
    public static void main(String[] args) {
        int clientCount = 2;
        String[] tx = {"tx.jpg", "tx.png"};
        for (int i = 0; i < clientCount; i++) {
            new Client(i, NameRandom.getChineseName(), i < tx.length ? tx[i] : null);
        }
    }
}
