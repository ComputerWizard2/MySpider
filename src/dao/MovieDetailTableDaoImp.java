package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import bean.MovieDetailTableBean;
import util.DBUtil;

public class MovieDetailTableDaoImp implements MovieDetailTableDaoInf{

	@Override
	public boolean insertMovieDetailTable(MovieDetailTableBean bean) {
		// TODO Auto-generated method stub
		Connection connection = DBUtil.getConnection(null);
		//����sql���
		String sql = "insert into moviedetailtable() values(?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, bean.getId());
			ps.setString(2, bean.getDirector());
			ps.setString(3, bean.getKeyword());
			ps.setString(4, bean.getCategroy());
			ps.setString(5, bean.getDes());
			ps.execute();
			//�ر����ݿ�
			DBUtil.releaseDB(connection, ps, null);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		//�ر����ݿ�
		DBUtil.releaseDB(connection, ps, null);
		return false;
	}

}
