package dao;

import java.util.ArrayList;

import bean.CategoryMovieBean;

public interface CateGroyMovieTableDaoInf {
	//��������
	public boolean insertIntoCateGroyMovie(CategoryMovieBean model);
	//��ѯ����
	public ArrayList<CategoryMovieBean> selectCateGroyMovieByModel(CategoryMovieBean model);
}
