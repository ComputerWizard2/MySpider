package dao;

import bean.MovieDetailTableBean;

public interface MovieDetailTableDaoInf {
	public boolean insertMovieDetailTable(MovieDetailTableBean bean);
	//插入详情，不在做检查重复操作，在获得电影前，就做了查重操作，电影没有重复，则电影详情就没重复
	
}
