package com.levik.concurrency.serivce;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Producer<E> implements Runnable{

    private BlockingQueue<E> queue;
    private List<E> elements;

    public Producer(BlockingQueue<E> queue, List<E> elements) {
        this.queue = queue;

        this.elements = new ArrayList<E>(elements);
    }

    @Override
    public void run() {
        for(;;) {
            for(E element : elements) {
                try {
                    Thread.sleep(100l);
                    queue.put(element);
                    System.out.println("Put element " + element);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
