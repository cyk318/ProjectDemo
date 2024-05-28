package org.cyk.base.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

//任务
class MyTask implements Comparable<MyTask>{
    //将要执行的任务
    private Runnable runnable;
    //多久后执行任务
    private long time;
    public MyTask(Runnable runnable, long time){
        this.runnable = runnable;
        this.time = time + System.currentTimeMillis();
    }
    public Runnable getRunnable() {
        return runnable;
    }
    public long getTime() {
        return time;
    }
    public int compareTo(MyTask o) {
        return (int)(this.time - o.time);
    }
}
//计时器
public class MyTimer {
    //优先级阻塞队列来存放任务，保证时间最小的先出队
    private BlockingQueue<MyTask> queue = new PriorityBlockingQueue<>();
    private Object locker = new Object();
    public MyTimer() {
        //创建一个线程，不断来扫描下一个任务
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    synchronized(locker) {
                        try {
                            //取出队首元素
                            MyTask task = queue.take();
                            //获取当前时间
                            long nowTime = System.currentTimeMillis();
                            //若到时了就执行任务，若没到时就阻塞等待
                            if(nowTime >= task.getTime()){
                                task.getRunnable().run();
                            }else{
                                //先将取出的元素放回
                                queue.put(task);
                                locker.wait(task.getTime() - nowTime);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        t1.start();
    }
    //安排
    public void schedule(Runnable runnable, long time) throws InterruptedException {
        queue.put(new MyTask(runnable, time));
        synchronized(locker) {
            locker.notify();
        }
    }
}
