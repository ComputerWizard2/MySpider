package bean;

public class PerformerDetailTableBean {
	String name;
	String e_name;
	String alias;
	String sex;
	String bloodtype;
	String height;
	String address;
	String birthday;
	String constellation;
	String location;
	String ResidentialAddress;
	String school;
	String BrokerageAgency;
	String fameyear;
	String hobby;
	String Occupation;
	String weight;
	String image;
	String des;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getE_name() {
		return e_name;
	}
	public void setE_name(String e_name) {
		this.e_name = e_name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBloodtype() {
		return bloodtype;
	}
	public void setBloodtype(String bloodtype) {
		this.bloodtype = bloodtype;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getConstellation() {
		return constellation;
	}
	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getResidentialAddress() {
		return ResidentialAddress;
	}
	public void setResidentialAddress(String residentialAddress) {
		ResidentialAddress = residentialAddress;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getBrokerageAgency() {
		return BrokerageAgency;
	}
	public void setBrokerageAgency(String brokerageAgency) {
		BrokerageAgency = brokerageAgency;
	}
	public String getFameyear() {
		return fameyear;
	}
	public void setFameyear(String fameyear) {
		this.fameyear = fameyear;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getOccupation() {
		return Occupation;
	}
	public void setOccupation(String occupation) {
		Occupation = occupation;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	@Override
	public String toString() {
		return "PerformerDetailTableBean [name=" + name + ", e_name=" + e_name + ", alias=" + alias + ", sex=" + sex
				+ ", bloodtype=" + bloodtype + ", height=" + height + ", address=" + address + ", birthday=" + birthday
				+ ", constellation=" + constellation + ", location=" + location + ", ResidentialAddress="
				+ ResidentialAddress + ", school=" + school + ", BrokerageAgency=" + BrokerageAgency + ", fameyear="
				+ fameyear + ", hobby=" + hobby + ", Occupation=" + Occupation + ", weight=" + weight + ", image="
				+ image + ", des=" + des + "]";
	}
	public PerformerDetailTableBean() {
		// TODO Auto-generated constructor stub
	}
	public PerformerDetailTableBean(String[] arr) {
		// TODO Auto-generated constructor stub
		if (arr.length==19) {
			name = arr[0];
			e_name = arr[2];
			sex = arr[3];
			height = arr[5];
			birthday = arr[4];
			location = arr[9];
			school = arr[6];
			fameyear =arr[7];
			alias = arr[8];
			bloodtype = arr[4];
			address = arr[10];
			constellation = arr[11];
			ResidentialAddress = arr[12];
			BrokerageAgency = arr[13];
			hobby = arr[14];
			Occupation = arr[15];
			weight =arr[16];
			image = arr[17];
			des = arr[18];
		}
	}
}
