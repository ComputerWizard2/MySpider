package dao;

import java.awt.image.DataBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


import bean.MovieTableBean;
import util.DBUtil;

public class MovieTableDaoImpl implements MovieTableDaoInf{

	@Override
	public ArrayList<MovieTableBean> selectMovieTable(MovieTableBean model) {
		// TODO Auto-generated method stub
		//����ʹ�õĲ�ѯ
		//��վʹ�õĲ�ѯ
		//�����ݿ�
		Connection connection = DBUtil.getConnection(null);
		Statement statement = null;
		ResultSet resultSet = null;
		//�������
		try {
			//ƴ�����
			String sql = "";
			if (model.getMovieName()==null) {
				//��ѯ���ǽ�Ҫ���ص�ͼƬ  ��Ҫע�⵱�������Ƚϴ��ʱ��ʹ��limit�����ݵ�ȡ��Ҫ�������ƣ������������
				sql = "select * from movietable where ISNULL(`status`)"+" and source='"+model.getSource()+"'";
			}else {
				sql= "select * form  movietable where moviename='"+model.getMovieName()+"'"+" and source='"+model.getSource()+"'";
			}
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			//�������飬��������
			ArrayList<MovieTableBean> list = new ArrayList<>();
			while(resultSet.next()){
				//����ģ��
				MovieTableBean model1 = new MovieTableBean();
				model1.setId(resultSet.getInt(1));
				model1.setMovieName(resultSet.getString(2));
				model1.setTime(resultSet.getString(3));
				model1.setUrl(resultSet.getString(4));
				model1.setImagePath(resultSet.getString(5));
				model1.setSaveImagePath(resultSet.getString(6));
				model1.setScore(resultSet.getFloat(7));
				model1.setSource(resultSet.getString(9));
				list.add(model1);
				
			}
			if (list.size()>0) {
				DBUtil.releaseDB(connection, statement, null);
				return list;
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		DBUtil.releaseDB(connection, statement, null);
		return null;
	}

	@Override
	public boolean insertMovieTable(MovieTableBean model) {
		//��������Ƿ��ظ�
		Connection connection = DBUtil.getConnection(null);
		PreparedStatement insert = null;
		try {
			ArrayList<MovieTableBean>list = selectMovieTable(model);
			if (list!=null) {
				System.out.println("�������ظ�");
				DBUtil.releaseDB(connection, null, null);
				return false;
			}
			//ִ�в������
			insert = connection.prepareStatement("insert into movietable(moviename,time,url,imagepath,saveimagepath,score,source) values(?,?,?,?,?,?,?)");
			insert.setString(1, model.getMovieName());
			insert.setString(2, model.getTime());
			insert.setString(3, model.getUrl());
			insert.setString(4, model.getImagePath());
			insert.setString(5, model.getSaveImagePath());
			insert.setFloat(6, model.getScore());
			insert.setString(7, model.getSource());
			boolean isSuccess = insert.execute();
			DBUtil.releaseDB(connection, insert, null);
			return isSuccess;
		} catch (Exception e) {
			// TODO: handle exception
		}
		DBUtil.releaseDB(connection, insert, null);
		
		return false;
	}

	@Override
	public boolean upDateMovieTableById(int id) {
		// TODO Auto-generated method stub
		Connection connection = DBUtil.getConnection(null);
		Statement stm = null;
		try {
			stm = connection.createStatement();
			boolean isSuccess = stm.execute("update movietable set status=1 where id ="+id);
			DBUtil.releaseDB(connection, stm, null);
			return isSuccess;
		} catch (Exception e) {
			// TODO: handle exception
		}
		DBUtil.releaseDB(connection, stm, null);
		return false;
	}

	@Override
	public boolean deleteMovieTableById(int id) {
		// TODO Auto-generated method stub
		Connection connection = DBUtil.getConnection(null);
		Statement stm = null;
		try {
			stm = connection.createStatement();
			boolean isSuccess = stm.execute("delete from movietable where id ="+id);
			DBUtil.releaseDB(connection, stm, null);
			return isSuccess;
		} catch (Exception e) {
			// TODO: handle exception
		}
		DBUtil.releaseDB(connection, stm, null);
		return false;
	}

}
