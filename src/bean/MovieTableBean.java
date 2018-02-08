package bean;

import java.security.MessageDigest;

import org.jsoup.select.Evaluator.Id;

import config.iqiyiDefaultConfig;

public class MovieTableBean {
	//电影名称
	String movieName;
	//电影地址
	String url;
	//电影时长
	String time;
	//评分
	float score;
	//图片地址
	String imagePath;
	//保存地址
	String saveImagePath;
	//来源
	String source;
	//id
	int id;
	
	//创建构造方法
	public MovieTableBean() {
		// TODO Auto-generated constructor stub
	}
	public MovieTableBean(String movieName,String url,String time,float score,String imagePath,String source,int id) {
		// TODO Auto-generated constructor stub
		this.movieName = movieName;
		this.url = url;
		this.time = time;
		this.score = score;
		this.imagePath = imagePath;
		this.source = source;
		this.id = id;
		
		//单独处理保存路径
		if (imagePath!=null) {
			this.saveImagePath = iqiyiDefaultConfig.photoPicture+makeMD5(imagePath)+".jpg";
		}
		
	}
	
	
	
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getSaveImagePath() {
		return saveImagePath;
	}
	public void setSaveImagePath(String saveImagePath) {
		this.saveImagePath = saveImagePath;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public static String makeMD5(String url) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(url.getBytes());
			byte b[]= md.digest();
			int i ;
			StringBuffer buf = new StringBuffer();
			for (int j = 0; j < b.length; j++) {
				i=b[j];
				if (i<0) {
					i+=256;
				}
				if (i<16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			//32位加密
			return buf.toString();
			//16位加密
//			return buf.toString().substring(8,24);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	
	
	

	
}
