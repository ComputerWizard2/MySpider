package dao;

import java.util.ArrayList;

import bean.PerformerDetailTableBean;

public interface PerformerDetailTableInf {
	//��ѯ
	public ArrayList<PerformerDetailTableBean> selectPerformerDetailTable(PerformerDetailTableBean bean);
	
	//����
	public boolean insertPerformerDetailTable(PerformerDetailTableBean bean);
}
