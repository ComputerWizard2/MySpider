package dao;

import java.util.ArrayList;

import bean.PerformerDetailTableBean;

public interface PerformerDetailTableInf {
	//≤È—Ø
	public ArrayList<PerformerDetailTableBean> selectPerformerDetailTable(PerformerDetailTableBean bean);
	
	//≤Â»Î
	public boolean insertPerformerDetailTable(PerformerDetailTableBean bean);
}
