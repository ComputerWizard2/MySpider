package bean;

public class CategoryMovieBean {
	//分类名称
	String title;
	//地址
	String url;
	//大分类
	String categroy;
	//来源
	String source;
	//numid
	String numid;
	
	public CategoryMovieBean() {
		// TODO Auto-generated constructor stub
	}
	
	public CategoryMovieBean(String title,String url,String categroy,String source,String numid) {
		// TODO Auto-generated constructor stub
		this.numid = numid;
		this.title = title;
		this.url = url;
		this.source = source;
		this.categroy = categroy;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCategroy() {
		return categroy;
	}

	public void setCategroy(String categroy) {
		this.categroy = categroy;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getNumid() {
		return numid;
	}

	public void setNumid(String numid) {
		this.numid = numid;
	}
	
}
