package dao;

import java.util.ArrayList;

import bean.MovieTableBean;

public interface MovieTableDaoInf {
	//查找内容
	public ArrayList<MovieTableBean> selectMovieTable(MovieTableBean model);
	//插入数据
	public boolean insertMovieTable(MovieTableBean model);
	//更新
	public boolean upDateMovieTableById(int id);
	//删除
	public boolean deleteMovieTableById(int id);
}
