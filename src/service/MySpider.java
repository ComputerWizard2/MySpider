package service;

import java.util.Timer;
import java.util.TimerTask;

public abstract class MySpider extends Thread{
	//设置全局变量
	boolean isSuccess= true;
	//抓取间隔
	long time=setSpaceTime();
	//抓取次数
	long endNum=setEndNum();
	//抓取间隔
	public abstract long setSpaceTime();
	//抓取次数
	public abstract int setEndNum();
	
	//获得分类
	public abstract void getCategroyMovieList();
	//获得电影列表
	public abstract void getMovieList();
	//获得电影详情
	public abstract void getMovieDetail();
	//下载图片
	public abstract void downloadPhoto();
	
	//设置抓取每一次的抓取任务，也就是执行一次的任务
	public void mySpiderRun(){
		isSuccess = false;
		try {
			getCategroyMovieList();
			System.out.println(this.getClass().getName()+"分类加载完成");
			getMovieList();
			System.out.println(this.getClass().getName()+"电影列表加载完成");
			getMovieDetail();
			System.out.println(this.getClass().getName()+"电影详情加载完成");
			downloadPhoto();
			System.out.println(this.getClass().getName()+"电影封面图片加载完成");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		isSuccess = true;

		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
//		//执行一次任务
//		mySpiderRun();
		//启动定时器   第一个是任务 第二个是延迟多久启动 第三是间隔多久调用一次
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (isSuccess) {
					//执行任务
					mySpiderRun();
					endNum--;
					if (endNum==0) {
						System.out.println("抓取完成");
						//停止计时器
						cancel();
						//强制清扫
						System.gc();
					}
					
				}else {
					System.out.println("当前任务尚未执行完成，跳过本次任务");
				}
				
			}
		},0,time);
	}
	
	
	
	
	
	
	
	
	
	
	
}
