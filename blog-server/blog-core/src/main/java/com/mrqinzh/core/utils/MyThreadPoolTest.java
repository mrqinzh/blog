package com.mrqinzh.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class MyThreadPoolTest {

    public static void main(String[] args) {
        MyThreadPool pool = new MyThreadPool(2);
        for (int i = 0; i < 10; i++) {
            pool.execute(() -> {
                log.debug("test");
            });
        }
    }

}

@Slf4j(topic = "MyThreadPool")
class MyThreadPool {

    private int coreSize;

    private Set<Worker> workers = new HashSet<>();

    private MyBlockingQueue<Runnable> queue;

    public MyThreadPool(int coreSize) {
        this.coreSize = coreSize;
        queue = new MyBlockingQueue<>(coreSize);
    }

    public void execute(Runnable runnable) {
        synchronized (workers) {
            if (workers.size() < coreSize) {
                Worker worker = new Worker(runnable);
                workers.add(worker);
                worker.start();
            } else {
                queue.addTask(runnable);
            }
        }
    }

    class Worker extends Thread {

        private Runnable runnable;

        public Worker(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            while (runnable != null || (runnable = queue.getTask()) != null) {
                try {
                    log.debug("执行run + {}", Thread.currentThread().getName());
                    runnable.run();
                } finally {
                    log.debug("释放 + {}", Thread.currentThread().getName());
                    runnable = null;
                }
            }
        }

    }

}



class MyBlockingQueue<T> {

    private int size;

    private ArrayBlockingQueue<T> queue;

    private ReentrantLock lock = new ReentrantLock();

    private Condition emptyWait = lock.newCondition();
    private Condition fullWait = lock.newCondition();

    public MyBlockingQueue(int size) {
        this.size = size;
        queue = new ArrayBlockingQueue<>(size);
    }

    public T getTask() {
        while (queue.size() == 0) {
            try {
                emptyWait.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        T poll = queue.poll();
        fullWait.notifyAll();
        return poll;
    }

    public void addTask(T task) {
        while (queue.size() == size) {
            try {
                fullWait.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.add(task);
        emptyWait.notifyAll();
    }

}