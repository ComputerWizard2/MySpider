package bean;

public class MoviePerformerTableBean {
	int id = -1;
	String performer;
	String role;
	public MoviePerformerTableBean() {
		// TODO Auto-generated constructor stub
	}
	public MoviePerformerTableBean(int id,String performer,String role) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.performer = performer;
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPerformer() {
		return performer;
	}
	public void setPerformer(String performer) {
		this.performer = performer;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
