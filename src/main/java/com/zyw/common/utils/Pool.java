package com.zyw.common.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pool {
    public static final ExecutorService service = Executors.newCachedThreadPool();
}
