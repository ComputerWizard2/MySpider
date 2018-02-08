package service;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.client.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import bean.CategoryMovieBean;
import bean.MovieDetailTableBean;
import bean.MoviePerformerTableBean;
import bean.MovieTableBean;
import bean.PerformerDetailTableBean;
import config.iqiyiDefaultConfig;
import dao.CateGroyMovieTableDaoInf;
import dao.CateGroyMovieTableImpl;
import dao.MovieDetailTableDaoImp;
import dao.MovieDetailTableDaoInf;
import dao.MoviePerformerTableDaoImp;
import dao.MoviePerformerTableDaoInf;
import dao.MovieTableDaoImpl;
import dao.MovieTableDaoInf;
import dao.PerformerDetailTableInf;
import dao.PerformerDetailtableImpl;
import util.HttpClientUtil;

public class Iqiyi extends MySpider{
	boolean DEBUG=true;
	
	
	//建立dao层
	CateGroyMovieTableDaoInf inf= new CateGroyMovieTableImpl();
	MovieTableDaoInf inf1 = new MovieTableDaoImpl();
	MovieDetailTableDaoInf inf2 = new MovieDetailTableDaoImp(); 
	MoviePerformerTableDaoInf inf3 = new MoviePerformerTableDaoImp();
	PerformerDetailTableInf inf4 = new PerformerDetailtableImpl();
	@Override
	public long setSpaceTime() {
		// TODO Auto-generated method stub
		return 100000;
	}

	@Override
	public int setEndNum() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void getCategroyMovieList() {
		if (DEBUG) {
			return;
		}
			String url = iqiyiDefaultConfig.categroyURL;
			Document document=null;
			try {
				document= Jsoup.connect(url).timeout(2000).get();
			} catch (Exception e) {
				// TODO: handle exception
			}
			Elements elements = document.getElementsByClass("mod_sear_list");
			for (int i = 2; i < elements.size()-1; i++) {
				Element element = elements.get(i);
				String cate =element.getElementsByTag("h3").text();
				cate = cate.replaceAll("：", "");
				System.out.println(cate);
				//getElementsByClass 读取class  
				//getElementsByTag  读取标签
				//获得小标签
				Elements aElements = element.getElementsByTag("a");
				
				for (Element element2 : aElements) {
					//a标签链接
					String aurl = element2.attr("href");
					//判断是否是网页地址
					if (aurl.endsWith(".html")) {
						//组装模型
						CategoryMovieBean bean = new CategoryMovieBean(element2.text(), aurl, cate, iqiyiDefaultConfig.iqiyiSource, null);
						//存入数据库
						inf.insertIntoCateGroyMovie(bean);
					}
				}
			
			}
		
	}

	@Override
	public void getMovieList() {
		

		//http://list.iqiyi.com/www/1/-------------11-101-1---.html
		boolean isOK= true;
		String string = "";
		int page =0;
		while (isOK) {
			page++;
			String url = "http://list.iqiyi.com/www/1/-------------11-"+page+"-1---.html";
			Document document;
			try {
				document=Jsoup.connect(url).timeout(5000).get();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return;
			}
			//解析电影
			Elements elements = document.getElementsByClass("site-piclist-auto").first().getElementsByTag("li");
			if (!string.equals(elements.toString())) {
				//如果不等于，则赋值
				string = elements.toString();
			}else {
				//如果等于，则跳出循环
				isOK=false;
				continue;
			}
			//遍历li
			for (Element element : elements) {
				//每个电影
				//播放链接
				Element aEle = element.getElementsByClass("site-piclist_pic").first().getElementsByTag("a").first();
				String playUrl = aEle.attr("href");
				//image标签
				String imagePath = aEle.getElementsByTag("img").first().attr("src");
				//时间
				String time = aEle.getElementsByClass("icon-vInfo").text();
				//文字内容 电影名称 评分
				Element element2 = element.getElementsByClass("site-piclist_info").first();
				//title
				String title = element2.getElementsByTag("p").first().getElementsByTag("a").first().text();
				//评分
				String score= element2.getElementsByClass("score").text();
				System.out.println(playUrl+time+imagePath+title+score);
				if (score.isEmpty()) {
					score="0";
				}
				//创建模型
				MovieTableBean bean = new MovieTableBean(title,playUrl,time,Float.parseFloat(score),imagePath,iqiyiDefaultConfig.iqiyiSource,0);
				inf1.insertMovieTable(bean);
			
			}
			
		
		
		}
	}

	@Override
	public void getMovieDetail() {
		// TODO Auto-generated method stub
		//获得我们抓取的电影目录每一个电影
		//创建模型
		MovieTableBean model = new MovieTableBean();
		model.setSource(iqiyiDefaultConfig.iqiyiSource);
		ArrayList<MovieTableBean>list= inf1.selectMovieTable(model);
		//开始遍历
		for (MovieTableBean movieTableBean : list) {
			//读取所有没有处理完的电影详情页  电影id 导演 看点 简介  分类
			Document document;
			try {
				document =Jsoup.connect(movieTableBean.getUrl()).timeout(5000).get();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				continue;
			}
			//下载图片
			HttpClientUtil.getInstance().downLoadPicture(iqiyiDefaultConfig.downloadPath+movieTableBean.getSaveImagePath(), "http:"+movieTableBean.getImagePath());
			//下载完成后需要进行更新
//			inf1.upDateMovieTableById(movieTableBean.getId());
			//获得电影详情
			getIqiyiMovieDetail(document, movieTableBean.getId());
			//电影演员表 演员 饰演角色
			getMoviePerformer(document, movieTableBean.getId());
			
			
			
		}
	}
	/**
	 * 获得演员详情
	 * 
	 * */
	public void getMoviePerformer(Document document,int uid){
		//抓取演员表
		Elements elements = document.getElementsByClass("type-con").select("a");
		//遍历具备host前缀是演员
		for (Element element : elements) {
			if (element.attr("rseat").startsWith("host")) {
				//获得演员
				String performer= element.text();
				//获得角色
				String role = element.nextElementSibling().text().replaceAll("/", "");
				System.out.println(performer+"~~~"+role);
				MoviePerformerTableBean bean = new MoviePerformerTableBean(uid,performer,role);
				inf3.insertMoviePerformerTable(bean);
				//进一步获得演员详情
				//获得演员地址
				String url = "http:"+element.attr("href");
				//进行请求数据，解析数据
				getPerformer(url);
			}
		}
		
	}
	
	/**
	 * 获得演员的详细信息
	 * */
	public void getPerformer(String url){
		//解析数据
		try {
			Document document = Jsoup.connect(url).timeout(5000).get();
			//获得名称
			Element element = document.getElementsByClass("result_detail").first().getElementsByClass("textOverflow").first();
			String name = element.text();
			//使用数组保存所有数据
			String[] strings = new String[19];
			int index = 0;
			strings[index]=name;
			index++;
			//获得列表
			Elements  elements=document.getElementsByClass("basicInfo-value");
			for (int i = 0; i < 7; i++) {
				strings[index]=elements.get(i).text();
				strings[index+1]=elements.get(i+7).text();
				index+=2;
			}
			//职业
			Elements elements2 = document.getElementsByTag("li");
			for (Element element2 : elements2) {
				String att = element2.attr("itemprop");
				//职业
				if (att.equals("jobTitle")) {
					strings[15]=element2.text();
				}
				//体重
				if (att.equals("weight")) {
					strings[16]=element2.text();
				}
				String headerImage=document.getElementsByClass("result_pic").first().getElementsByTag("img").first().attr("src");
				strings[17]=headerImage;
				strings[18]=document.getElementsByClass("introduce-info").text();
				
				
			}
			PerformerDetailTableBean performerDetailTableBean = new PerformerDetailTableBean(strings);
			System.out.println(performerDetailTableBean);
			//创建模型
			inf4.insertPerformerDetailTable(performerDetailTableBean);
			
		
			//下载头像
			System.out.println(Arrays.toString(strings));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
	/**
	 * 获得单个电影详情
	 * */
	public void getIqiyiMovieDetail(Document document,int uid) {
		if (DEBUG) {
			return;
			
		}
		//获得分类
		Elements aElements = document.getElementById("datainfo-taglist").getElementsByTag("a");
		//find_in_set 这个sql关键词进行字段内精准查找
		String categroy="";
		for (Element element : aElements) {
			categroy=categroy+","+element.text();
		}
		//最前面的逗号去掉
		if (categroy.length()>0) {
			categroy=categroy.substring(1);
		}
		//获得导演
		Elements elements = document.getElementsByClass("type-con").select("a");
		String director ="";
		for (Element element : elements) {
			if (element.attr("rseat").startsWith("director")) {
				director=director+","+element.text();
			}
		}
		if (director.length()>0) {
			director=director.substring(1);
		}
		
		String jsonStr = "";
		String keyword="";
		try {
			//获得热词
			//获得id
			String id = document.getElementById("data-videopoint").attr("data-qipuid");			
			//发起http网络请求
			jsonStr = HttpClientUtil.getInstance().downLoadJson("http://qiqu.iqiyi.com/apis/video/tags/get?entity_id="+id+"&limit=10");
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (jsonStr!="") {
			//解析json
			JSONObject jsonObject = JSON.parseObject(jsonStr);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			for (Object object : jsonArray) {
				//强制转换
				JSONObject object2 = (JSONObject)object;
				keyword = keyword+","+object2.getString("tag");
			}
			if (keyword.length()>0) {
				keyword = keyword.substring(1);
			}	
		}
		
		//简介
		String des = document.getElementById("datainfo-tag-desc").text();
		
		System.out.println(keyword+"~~"+director+"~~"+categroy+des);
		//存入数据库
		//创建模型
		MovieDetailTableBean bean = new MovieDetailTableBean(uid,director,des,categroy,keyword);
		System.out.println(bean);
		inf2.insertMovieDetailTable(bean);
		
	}
	
	
	
	
	
	@Override
	public void downloadPhoto() {
		// TODO Auto-generated method stub
		
	}

}
