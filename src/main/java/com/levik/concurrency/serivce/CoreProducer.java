package com.levik.concurrency.serivce;

import java.util.Queue;
import java.util.Random;

public class CoreProducer<E> implements Runnable{

    private Queue<E> queue;
    private int maxSize;
    private E element;

    public CoreProducer(int maxSize, Queue<E> queue, E element) {
        this.maxSize = maxSize;
        this.queue = queue;
        this.element = element;
    }

    @Override
    public void run() {

        while (true) {
            synchronized (queue) {
                while (queue.size() == maxSize) {
                    System.out.println("Queue is full. Producer wait until Consumer take something from queue");
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("Producing value : " + element);
                queue.add(element);
                queue.notifyAll();
            }
        }


    }
}
