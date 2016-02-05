package com.levik.concurrency.serivce;

import java.util.concurrent.BlockingQueue;

public class Consumer<E> implements Runnable{

    private BlockingQueue<E> queue;

    public Consumer(BlockingQueue<E> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for(;;){
            try {
                Thread.sleep(100l);
                final E element = queue.take();
                System.out.println("get element " + element);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
