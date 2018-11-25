package com.e3mall.easyui;

import java.io.Serializable;
import java.util.List;

public class DataGridResult implements Serializable{
	private Long total;//总条数
	private List rows;//数据
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}	
}
