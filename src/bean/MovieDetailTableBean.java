package bean;

public class MovieDetailTableBean {
	//id
	int id;
	//导演
	String director;
	//简介
	String des;
	//分类
	String categroy;
	//关键词
	String keyword;
	
	@Override
	public String toString() {
		return "MovieDetailTableBean [id=" + id + ", director=" + director + ", des=" + des + ", categroy=" + categroy
				+ ", keyword=" + keyword + "]";
	}
	public MovieDetailTableBean() {
		// TODO Auto-generated constructor stub
	}
	public MovieDetailTableBean(int id,String director,String des,String categroy,String keyword) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.director = director;
		this.des = des;
		this.categroy = categroy;
		this.keyword = keyword;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getCategroy() {
		return categroy;
	}
	public void setCategroy(String categroy) {
		this.categroy = categroy;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
