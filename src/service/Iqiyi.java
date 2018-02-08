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
	
	
	//����dao��
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
				cate = cate.replaceAll("��", "");
				System.out.println(cate);
				//getElementsByClass ��ȡclass  
				//getElementsByTag  ��ȡ��ǩ
				//���С��ǩ
				Elements aElements = element.getElementsByTag("a");
				
				for (Element element2 : aElements) {
					//a��ǩ����
					String aurl = element2.attr("href");
					//�ж��Ƿ�����ҳ��ַ
					if (aurl.endsWith(".html")) {
						//��װģ��
						CategoryMovieBean bean = new CategoryMovieBean(element2.text(), aurl, cate, iqiyiDefaultConfig.iqiyiSource, null);
						//�������ݿ�
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
			//������Ӱ
			Elements elements = document.getElementsByClass("site-piclist-auto").first().getElementsByTag("li");
			if (!string.equals(elements.toString())) {
				//��������ڣ���ֵ
				string = elements.toString();
			}else {
				//������ڣ�������ѭ��
				isOK=false;
				continue;
			}
			//����li
			for (Element element : elements) {
				//ÿ����Ӱ
				//��������
				Element aEle = element.getElementsByClass("site-piclist_pic").first().getElementsByTag("a").first();
				String playUrl = aEle.attr("href");
				//image��ǩ
				String imagePath = aEle.getElementsByTag("img").first().attr("src");
				//ʱ��
				String time = aEle.getElementsByClass("icon-vInfo").text();
				//�������� ��Ӱ���� ����
				Element element2 = element.getElementsByClass("site-piclist_info").first();
				//title
				String title = element2.getElementsByTag("p").first().getElementsByTag("a").first().text();
				//����
				String score= element2.getElementsByClass("score").text();
				System.out.println(playUrl+time+imagePath+title+score);
				if (score.isEmpty()) {
					score="0";
				}
				//����ģ��
				MovieTableBean bean = new MovieTableBean(title,playUrl,time,Float.parseFloat(score),imagePath,iqiyiDefaultConfig.iqiyiSource,0);
				inf1.insertMovieTable(bean);
			
			}
			
		
		
		}
	}

	@Override
	public void getMovieDetail() {
		// TODO Auto-generated method stub
		//�������ץȡ�ĵ�ӰĿ¼ÿһ����Ӱ
		//����ģ��
		MovieTableBean model = new MovieTableBean();
		model.setSource(iqiyiDefaultConfig.iqiyiSource);
		ArrayList<MovieTableBean>list= inf1.selectMovieTable(model);
		//��ʼ����
		for (MovieTableBean movieTableBean : list) {
			//��ȡ����û�д�����ĵ�Ӱ����ҳ  ��Ӱid ���� ���� ���  ����
			Document document;
			try {
				document =Jsoup.connect(movieTableBean.getUrl()).timeout(5000).get();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				continue;
			}
			//����ͼƬ
			HttpClientUtil.getInstance().downLoadPicture(iqiyiDefaultConfig.downloadPath+movieTableBean.getSaveImagePath(), "http:"+movieTableBean.getImagePath());
			//������ɺ���Ҫ���и���
//			inf1.upDateMovieTableById(movieTableBean.getId());
			//��õ�Ӱ����
			getIqiyiMovieDetail(document, movieTableBean.getId());
			//��Ӱ��Ա�� ��Ա ���ݽ�ɫ
			getMoviePerformer(document, movieTableBean.getId());
			
			
			
		}
	}
	/**
	 * �����Ա����
	 * 
	 * */
	public void getMoviePerformer(Document document,int uid){
		//ץȡ��Ա��
		Elements elements = document.getElementsByClass("type-con").select("a");
		//�����߱�hostǰ׺����Ա
		for (Element element : elements) {
			if (element.attr("rseat").startsWith("host")) {
				//�����Ա
				String performer= element.text();
				//��ý�ɫ
				String role = element.nextElementSibling().text().replaceAll("/", "");
				System.out.println(performer+"~~~"+role);
				MoviePerformerTableBean bean = new MoviePerformerTableBean(uid,performer,role);
				inf3.insertMoviePerformerTable(bean);
				//��һ�������Ա����
				//�����Ա��ַ
				String url = "http:"+element.attr("href");
				//�����������ݣ���������
				getPerformer(url);
			}
		}
		
	}
	
	/**
	 * �����Ա����ϸ��Ϣ
	 * */
	public void getPerformer(String url){
		//��������
		try {
			Document document = Jsoup.connect(url).timeout(5000).get();
			//�������
			Element element = document.getElementsByClass("result_detail").first().getElementsByClass("textOverflow").first();
			String name = element.text();
			//ʹ�����鱣����������
			String[] strings = new String[19];
			int index = 0;
			strings[index]=name;
			index++;
			//����б�
			Elements  elements=document.getElementsByClass("basicInfo-value");
			for (int i = 0; i < 7; i++) {
				strings[index]=elements.get(i).text();
				strings[index+1]=elements.get(i+7).text();
				index+=2;
			}
			//ְҵ
			Elements elements2 = document.getElementsByTag("li");
			for (Element element2 : elements2) {
				String att = element2.attr("itemprop");
				//ְҵ
				if (att.equals("jobTitle")) {
					strings[15]=element2.text();
				}
				//����
				if (att.equals("weight")) {
					strings[16]=element2.text();
				}
				String headerImage=document.getElementsByClass("result_pic").first().getElementsByTag("img").first().attr("src");
				strings[17]=headerImage;
				strings[18]=document.getElementsByClass("introduce-info").text();
				
				
			}
			PerformerDetailTableBean performerDetailTableBean = new PerformerDetailTableBean(strings);
			System.out.println(performerDetailTableBean);
			//����ģ��
			inf4.insertPerformerDetailTable(performerDetailTableBean);
			
		
			//����ͷ��
			System.out.println(Arrays.toString(strings));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
	/**
	 * ��õ�����Ӱ����
	 * */
	public void getIqiyiMovieDetail(Document document,int uid) {
		if (DEBUG) {
			return;
			
		}
		//��÷���
		Elements aElements = document.getElementById("datainfo-taglist").getElementsByTag("a");
		//find_in_set ���sql�ؼ��ʽ����ֶ��ھ�׼����
		String categroy="";
		for (Element element : aElements) {
			categroy=categroy+","+element.text();
		}
		//��ǰ��Ķ���ȥ��
		if (categroy.length()>0) {
			categroy=categroy.substring(1);
		}
		//��õ���
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
			//����ȴ�
			//���id
			String id = document.getElementById("data-videopoint").attr("data-qipuid");			
			//����http��������
			jsonStr = HttpClientUtil.getInstance().downLoadJson("http://qiqu.iqiyi.com/apis/video/tags/get?entity_id="+id+"&limit=10");
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (jsonStr!="") {
			//����json
			JSONObject jsonObject = JSON.parseObject(jsonStr);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			for (Object object : jsonArray) {
				//ǿ��ת��
				JSONObject object2 = (JSONObject)object;
				keyword = keyword+","+object2.getString("tag");
			}
			if (keyword.length()>0) {
				keyword = keyword.substring(1);
			}	
		}
		
		//���
		String des = document.getElementById("datainfo-tag-desc").text();
		
		System.out.println(keyword+"~~"+director+"~~"+categroy+des);
		//�������ݿ�
		//����ģ��
		MovieDetailTableBean bean = new MovieDetailTableBean(uid,director,des,categroy,keyword);
		System.out.println(bean);
		inf2.insertMovieDetailTable(bean);
		
	}
	
	
	
	
	
	@Override
	public void downloadPhoto() {
		// TODO Auto-generated method stub
		
	}

}
