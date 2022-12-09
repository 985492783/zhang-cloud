package com.zhang.cloud.entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author 98549
 * @date 2021/12/23 16:31
 */
public class MyQueue {
    private static Map<String,MyQueue> instances=new HashMap<>();
    private Queue<Long> queue;
    private MyQueue() {
        this.queue = new LinkedList<>();
    }
    public static synchronized MyQueue create(String path){
        MyQueue instance=instances.get(path);
        if(instance==null){
            System.out.println(path);
            instance=new MyQueue();
            instances.put(path,instance);
        }
        return instance;
    }
    public static MyQueue getInstance(String path){
        MyQueue instance=instances.get(path);
        if(instance==null){
            instance=create(path);
        }
        return instance;
    }
    public static Map<String, MyQueue> getAllInstance(){
        return instances;
    }
    public void setQueue(Queue queue){
        this.queue=queue;
    }

    public Queue<Long> getQueue() {
        return this.queue;
    }
}
