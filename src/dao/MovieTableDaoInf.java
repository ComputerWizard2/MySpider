package dao;

import java.util.ArrayList;

import bean.MovieTableBean;

public interface MovieTableDaoInf {
	//��������
	public ArrayList<MovieTableBean> selectMovieTable(MovieTableBean model);
	//��������
	public boolean insertMovieTable(MovieTableBean model);
	//����
	public boolean upDateMovieTableById(int id);
	//ɾ��
	public boolean deleteMovieTableById(int id);
}
