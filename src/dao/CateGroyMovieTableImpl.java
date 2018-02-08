package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


import bean.CategoryMovieBean;
import util.DBUtil;

public class CateGroyMovieTableImpl implements CateGroyMovieTableDaoInf{

	@Override
	public boolean insertIntoCateGroyMovie(CategoryMovieBean model) {
		// TODO Auto-generated method stub
		
		//��ѯ����
		ArrayList<CategoryMovieBean> list = selectCateGroyMovieByModel(model);
		if (list!=null) {
			return false;
		}
		//ִ�в������
		Connection connection = DBUtil.getConnection(null);
		PreparedStatement ps = null;
		try {
			String sql = "insert into categroyMovieTable"
					+ "(categroy,url,title,source) values(?,?,?,?)";
			ps = connection.prepareStatement(sql);
			ps.setString(1, model.getCategroy());
			ps.setString(2, model.getUrl());
			ps.setString(3, model.getTitle());
			ps.setString(4, model.getSource());
			boolean isSuccess = ps.execute();
			//�ͷ�
			DBUtil.releaseDB(connection, ps, null);
			return isSuccess;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		DBUtil.releaseDB(connection, ps, null);
		return false;
	}

	@Override
	public ArrayList<CategoryMovieBean> selectCateGroyMovieByModel(CategoryMovieBean model) {
		// TODO Auto-generated method stub
		
		Connection connection =DBUtil.getConnection(null);
		Statement stm = null;
		ResultSet resultSet = null;
		try {
			stm=connection.createStatement();
			String sqlString = "select * from categroyMovieTable where 1=1 ";
			String temp = "";
			if (model.getCategroy() !=null) {
				temp = temp+" and categroy='"+model.getCategroy()+"' ";
			}
			if (model.getTitle() !=null) {
				temp = temp+"and title='"+model.getTitle()+"' ";
			}
			if (model.getUrl() !=null) {
				temp = temp+"and url='"+model.getUrl()+"' ";
			}
			if (model.getSource() !=null) {
				temp = temp+"and source='"+model.getSource()+"' ";
			}
			if (model.getNumid() !=null) {
				temp = temp+"and numid='"+model.getNumid()+"' "; 
			}
			if (temp.length()>0) {
				sqlString=sqlString+temp;
			}
			System.out.println(sqlString);
			resultSet = stm.executeQuery(sqlString);
			//��������
			ArrayList<CategoryMovieBean> list = new ArrayList<>();
			while (resultSet.next()) {
				//����ģ��  43251 ע��1��int����
				CategoryMovieBean movie2=new CategoryMovieBean(resultSet.getString(4),resultSet.getString(3),resultSet.getString(2),resultSet.getString(5),resultSet.getInt(1)+"");
				list.add(movie2);
			}
			if (list.size()>0) {
				//�ر����ݿ�
				DBUtil.releaseDB(connection, stm, resultSet);
				return list;
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		//�ر����ݿ�
		DBUtil.releaseDB(connection, stm, resultSet);
		
		return null;
	}

}
