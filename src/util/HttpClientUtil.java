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
	//创建一个单例
	private static HttpClientUtil instance = null;
	//私有化构造方法
	private HttpClientUtil() {
		// TODO Auto-generated constructor stub
	}
	//创建静态方法
	public static HttpClientUtil getInstance() {
		if (instance == null) {
			instance = new HttpClientUtil();
		}
		return instance;
	}
	/**
	 * 下载图片，先判断路径是否存在
	 * 
	 * */
	public boolean downLoadPicture(String fileStr,String url) {
		File file = new File(fileStr);
		//判断文件是否存在
		if (file.exists()) {
			//如果存在
			return false;
		}else {
			if (file.isDirectory()) {
				return false;
			}
			//获得上级文件夹路径
			File file2 = file.getParentFile();
			//执行创建
			if (!file2.exists()) {
				file2.mkdirs();
			}
		}
		//执行下载
		return downLoadPicture(url, file);
	}
	private boolean downLoadPicture(String urlStr,File file) {
		//使用httpClient进行下载
		HttpGet httpGet = new HttpGet(urlStr);
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		boolean isSuccess;
		try {
			httpClient=HttpClients.createDefault();
			//执行请求
			response = httpClient.execute(httpGet);
			//获得内容
			entity = response.getEntity();
			//创建缓冲流
			//输入流
			BufferedInputStream bitStream = new BufferedInputStream(entity.getContent());
			//输出流
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
			//释放资源
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
	
	
	//下载json数据
	public String downLoadJson(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient =HttpClients.createDefault();
		//创建GET实例
		HttpGet httpGet = new HttpGet(url);
		//获得结果
		CloseableHttpResponse response = httpClient.execute(httpGet);
		//执行
		HttpEntity entity = response.getEntity();
		//使用工具类进行转换
		String string = EntityUtils.toString(entity,"utf-8");
		response.close();
		httpClient.close();
		return string;
		
	}
	
	
}












