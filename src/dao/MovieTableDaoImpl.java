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
		//爬虫使用的查询
		//网站使用的查询
		//打开数据库
		Connection connection = DBUtil.getConnection(null);
		Statement statement = null;
		ResultSet resultSet = null;
		//创建语句
		try {
			//拼接语句
			String sql = "";
			if (model.getMovieName()==null) {
				//查询的是将要下载的图片  需要注意当数据量比较大的时候，使用limit对数据的取出要进行限制，否则程序会崩溃
				sql = "select * from movietable where ISNULL(`status`)"+" and source='"+model.getSource()+"'";
			}else {
				sql= "select * form  movietable where moviename='"+model.getMovieName()+"'"+" and source='"+model.getSource()+"'";
			}
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			//创建数组，保存数据
			ArrayList<MovieTableBean> list = new ArrayList<>();
			while(resultSet.next()){
				//创建模型
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
		//检查内容是否重复
		Connection connection = DBUtil.getConnection(null);
		PreparedStatement insert = null;
		try {
			ArrayList<MovieTableBean>list = selectMovieTable(model);
			if (list!=null) {
				System.out.println("内容有重复");
				DBUtil.releaseDB(connection, null, null);
				return false;
			}
			//执行插入操作
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
