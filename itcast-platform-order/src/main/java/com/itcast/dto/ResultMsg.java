package com.itcast.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultMsg<T> implements Serializable{
	private static final long serialVersionUID = 1096311515906831278L;
	private boolean result;//处理状态
	private int resultcode;// 返回结果编码. 比如1成功,0失败,-1未确定. 根据业务确定.
	private Object data;//其他数据
	private long dataId;//业务参数id
	private String resultstrcode;//字符串的返回Code 返回结果编码.兼容ApiResultMsg.

	private String msg;//消息
    private List<T> rows = new ArrayList<>();//bootstarp分页设置每一行数据
	private T row;
	private Long total;//分页设置总页数
	private Integer records;//jqGrid总行数

	public String getResultstrcode() {
		return resultstrcode;
	}

	public void setResultstrcode(String resultstrcode) {
		this.resultstrcode = resultstrcode;
	}

	public int getResultcode() {
		return resultcode;
	}

	public void setResultcode(int resultcode) {
		this.resultcode = resultcode;
	}



	public boolean getResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}

	public void setResult(boolean result,String msg) {
		this.result = result;
		this.msg=msg;
	}

	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(long total2) {
		this.total = total2;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public long getDataId() {
		return dataId;
	}

	public void setDataId(long dataId) {
		this.dataId = dataId;
	}

	public T getRow() {
		return row;
	}

	public void setRow(T row) {
		this.row = row;
	}

	public ResultMsg(){

	}

	public ResultMsg(boolean result) {
		if(result){
			this.result = true;
			this.resultcode = 1001;
			this.resultstrcode = "1001";
			this.msg = "成功：操作成功！";
		}
		else{
			this.result = false;
			this.resultcode = 1002;
			this.resultstrcode = "1002";
			this.msg = "失败：操作失败，请重试！";
		}
	}

}
