package com.example.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainTests {
    private static final ReadWriteLock rtLock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0 ;i<2;i++){
            Task task = new Task();
            executorService.submit(task);
        }

        executorService.shutdown();

    }

   static class Task implements Runnable{

        @Override
        public void run() {
            rtLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + "-writeLock");
            rtLock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + "-readLock");
        }
    }
}
