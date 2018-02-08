package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	//����һ������
	private static HttpClientUtil instance = null;
	//˽�л����췽��
	private HttpClientUtil() {
		// TODO Auto-generated constructor stub
	}
	//������̬����
	public static HttpClientUtil getInstance() {
		if (instance == null) {
			instance = new HttpClientUtil();
		}
		return instance;
	}
	/**
	 * ����ͼƬ�����ж�·���Ƿ����
	 * 
	 * */
	public boolean downLoadPicture(String fileStr,String url) {
		File file = new File(fileStr);
		//�ж��ļ��Ƿ����
		if (file.exists()) {
			//�������
			return false;
		}else {
			if (file.isDirectory()) {
				return false;
			}
			//����ϼ��ļ���·��
			File file2 = file.getParentFile();
			//ִ�д���
			if (!file2.exists()) {
				file2.mkdirs();
			}
		}
		//ִ������
		return downLoadPicture(url, file);
	}
	private boolean downLoadPicture(String urlStr,File file) {
		//ʹ��httpClient��������
		HttpGet httpGet = new HttpGet(urlStr);
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		boolean isSuccess;
		try {
			httpClient=HttpClients.createDefault();
			//ִ������
			response = httpClient.execute(httpGet);
			//�������
			entity = response.getEntity();
			//����������
			//������
			BufferedInputStream bitStream = new BufferedInputStream(entity.getContent());
			//�����
			BufferedOutputStream boStream = new BufferedOutputStream(new FileOutputStream(file));
			byte[] buf = new byte[1024];
			int len;
			while ((len=bitStream.read(buf))!=-1) {
				boStream.write(buf,0,len);
			}
			boStream.close();
			bitStream.close();
			isSuccess = true;
			
		} catch (Exception e) {
			// TODO: handle exception
			file.delete();
			isSuccess =false;
		}finally {
			//�ͷ���Դ
			if (response!=null) {
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (httpClient!=null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return isSuccess;
		
	}
	
	
	//����json����
	public String downLoadJson(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient =HttpClients.createDefault();
		//����GETʵ��
		HttpGet httpGet = new HttpGet(url);
		//��ý��
		CloseableHttpResponse response = httpClient.execute(httpGet);
		//ִ��
		HttpEntity entity = response.getEntity();
		//ʹ�ù��������ת��
		String string = EntityUtils.toString(entity,"utf-8");
		response.close();
		httpClient.close();
		return string;
		
	}
	
	
}












