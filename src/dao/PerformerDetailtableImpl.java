package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import bean.MovieTableBean;
import bean.PerformerDetailTableBean;
import util.DBUtil;

public class PerformerDetailtableImpl implements PerformerDetailTableInf{

	@Override
	public ArrayList<PerformerDetailTableBean> selectPerformerDetailTable(PerformerDetailTableBean bean) {
		
		return null;
	}

	@Override
	public boolean insertPerformerDetailTable(PerformerDetailTableBean bean) {
		Connection connection = DBUtil.getConnection(null);
		PreparedStatement insert = null;
		try {
			
			//Ö´ÐÐ²åÈë²Ù×÷
			insert = connection.prepareStatement("insert into performerdetailta"
					+ "ble (name,e_name,alias,sex,bloodtype,height,address,birthday,"
					+ "constellation,location,residentialaddress,"
					+ "school,brokerageagency,fameyear,hobby"
					+ ",occupation,weight,image,des) VALUE "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			insert.setString(1, bean.getName());
			insert.setString(2, bean.getE_name());
			insert.setString(3, bean.getAlias());
			insert.setString(4, bean.getSex());
			insert.setString(5, bean.getBloodtype());
			insert.setString(6, bean.getHeight());
			insert.setString(7, bean.getAddress());
			insert.setString(8, bean.getBirthday());
			insert.setString(9, bean.getConstellation());
			insert.setString(10, bean.getLocation());
			insert.setString(11, bean.getResidentialAddress());
			insert.setString(12, bean.getSchool());
			insert.setString(13, bean.getBrokerageAgency());
			insert.setString(14, bean.getFameyear());
			insert.setString(15, bean.getHobby());
			insert.setString(16, bean.getOccupation());
			insert.setString(17, bean.getWeight());
			insert.setString(18, bean.getImage());
			insert.setString(19, bean.getDes());
			boolean isSuccess = insert.execute();
			DBUtil.releaseDB(connection, insert, null);
			return isSuccess;
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBUtil.releaseDB(connection, insert, null);
		
		return false;
	}

}
