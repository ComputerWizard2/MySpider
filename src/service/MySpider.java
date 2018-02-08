package service;

import java.util.Timer;
import java.util.TimerTask;

public abstract class MySpider extends Thread{
	//����ȫ�ֱ���
	boolean isSuccess= true;
	//ץȡ���
	long time=setSpaceTime();
	//ץȡ����
	long endNum=setEndNum();
	//ץȡ���
	public abstract long setSpaceTime();
	//ץȡ����
	public abstract int setEndNum();
	
	//��÷���
	public abstract void getCategroyMovieList();
	//��õ�Ӱ�б�
	public abstract void getMovieList();
	//��õ�Ӱ����
	public abstract void getMovieDetail();
	//����ͼƬ
	public abstract void downloadPhoto();
	
	//����ץȡÿһ�ε�ץȡ����Ҳ����ִ��һ�ε�����
	public void mySpiderRun(){
		isSuccess = false;
		try {
			getCategroyMovieList();
			System.out.println(this.getClass().getName()+"����������");
			getMovieList();
			System.out.println(this.getClass().getName()+"��Ӱ�б�������");
			getMovieDetail();
			System.out.println(this.getClass().getName()+"��Ӱ����������");
			downloadPhoto();
			System.out.println(this.getClass().getName()+"��Ӱ����ͼƬ�������");

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
//		//ִ��һ������
//		mySpiderRun();
		//������ʱ��   ��һ�������� �ڶ������ӳٶ������ �����Ǽ����õ���һ��
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (isSuccess) {
					//ִ������
					mySpiderRun();
					endNum--;
					if (endNum==0) {
						System.out.println("ץȡ���");
						//ֹͣ��ʱ��
						cancel();
						//ǿ����ɨ
						System.gc();
					}
					
				}else {
					System.out.println("��ǰ������δִ����ɣ�������������");
				}
				
			}
		},0,time);
	}
	
	
	
	
	
	
	
	
	
	
	
}
