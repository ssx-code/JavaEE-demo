package com.cy.java.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestThreadPool01 {

	public static void main(String[] args) {
		BlockingQueue<Runnable> workQueue=new ArrayBlockingQueue<>(2);
		ThreadFactory threadFactory=new ThreadFactory() {
			AtomicInteger at=new AtomicInteger(1);
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r,"work-thead-"+at.getAndIncrement());
			}
		};
		//创建线程池
		ThreadPoolExecutor executor=
		new ThreadPoolExecutor(
				2,//corePoolSize 核心线程数(每次处理任务都会创建一个新的线程一直达到corePoolSize大小)
				3,//maximumPoolSize最大线程数(当任务队列满了，所有的线程又都在忙，此时再来新的任务创建新线程一直到maximumPoolSize)
				30,//keepAliveTime最大空闲时间
				TimeUnit.SECONDS,//unit时间单位
                workQueue, //workQueue工作队列/任务队列
                threadFactory,//threadFactory创建线程对象的工厂
                new ThreadPoolExecutor.CallerRunsPolicy());//当池已经达到最大吞吐量，再来新的任务该如何执行由拒绝策略决定
		
		//执行任务
		executor.execute(new Runnable() {
			@Override
			public void run() {
				String tName=Thread.currentThread().getName();
				System.out.println(tName+"->execute task 01");
				try{Thread.sleep(5000);}catch(Exception e) {}
			}
		});
		executor.execute(new Runnable() {
			@Override
			public void run() {
				String tName=Thread.currentThread().getName();
				System.out.println(tName+"->execute task 02");
				try{Thread.sleep(5000);}catch(Exception e) {}
			}
		});
		executor.execute(new Runnable() {
			@Override
			public void run() {
				String tName=Thread.currentThread().getName();
				System.out.println(tName+"->execute task 03");
				try{Thread.sleep(5000);}catch(Exception e) {}
			}
		});
		executor.execute(new Runnable() {
			@Override
			public void run() {
				String tName=Thread.currentThread().getName();
				System.out.println(tName+"->execute task 04");
				try{Thread.sleep(5000);}catch(Exception e) {}
			}
		});
		executor.execute(new Runnable() {
			@Override
			public void run() {
				String tName=Thread.currentThread().getName();
				System.out.println(tName+"->execute task 05");
				try{Thread.sleep(5000);}catch(Exception e) {}
			}
		});
		executor.execute(new Runnable() {
			@Override
			public void run() {
				String tName=Thread.currentThread().getName();
				System.out.println(tName+"->execute task 06");
				try{Thread.sleep(5000);}catch(Exception e) {}
			}
		});
	}
}



