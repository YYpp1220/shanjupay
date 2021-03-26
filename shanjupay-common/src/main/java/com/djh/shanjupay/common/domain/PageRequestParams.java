


package com.djh.shanjupay.common.domain;


/**
 * 页面请求参数
 *
 * @author MrMyHui
 * @date 2021/03/26
 */
public class PageRequestParams {
	
	private long startRow;
	
	private long limit;
	
	private PageRequestParams(Long startRow, Long limit){
		this.startRow = startRow;
		this.limit = limit;
	}
	
	public static PageRequestParams of(Integer pageNo, Integer pageSize){
		Long startRow = (long) ((pageNo - 1) * pageSize);
		Long limit = Long.valueOf((pageSize));
		return new PageRequestParams(startRow , limit);
	}

	public long getStartRow() {
		return startRow;
	}

	public void setStartRow(long startRow) {
		this.startRow = startRow;
	}

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
		this.limit = limit;
	}
	
	
	
}
