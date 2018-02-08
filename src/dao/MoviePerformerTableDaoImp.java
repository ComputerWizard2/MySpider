package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import bean.MoviePerformerTableBean;
import util.DBUtil;

public class MoviePerformerTableDaoImp implements MoviePerformerTableDaoInf{

	@Override
	public boolean insertMoviePerformerTable(MoviePerformerTableBean bean) {
		// TODO Auto-generated method stub
		Connection connection = DBUtil.getConnection(null);
		PreparedStatement ps = null;
		try {
			ps=connection.prepareStatement("insert into movieperformertable() values(?,?,?)");
			ps.setInt(1, bean.getId());
			ps.setString(2, bean.getPerformer());
			ps.setString(3, bean.getRole());
			ps.execute();
			DBUtil.releaseDB(connection, ps, null);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		DBUtil.releaseDB(connection, ps, null);
		return false;
	}
}
