package dao;

import java.util.ArrayList;

import bean.CategoryMovieBean;

public interface CateGroyMovieTableDaoInf {
	//插入数据
	public boolean insertIntoCateGroyMovie(CategoryMovieBean model);
	//查询数据
	public ArrayList<CategoryMovieBean> selectCateGroyMovieByModel(CategoryMovieBean model);
}
