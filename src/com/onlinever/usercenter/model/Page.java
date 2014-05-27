package com.onlinever.usercenter.model;


import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 * @author Demon
 * 
 * @copyright (c) onlinever.com 2014
 */
public class Page<T> {

	private List<T> list;           // 当前页记录列表
	private int totalCount;         // 总记录数
	private int pageSize;           // 每页记录数

	private int startIndex;         // 翻页的offset
	
	private String method;
	private String queryString;


	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Page() {
	}

	public Page(List<T> list, int totalCount, int startIndex) {
		this.list = list;
		this.totalCount = totalCount;
		this.startIndex = startIndex;
		this.pageSize = 10;
	}

	public Page(List<T> list, int totalCount, int startIndex, int pageSize) {
		this.list = list;
		this.totalCount = totalCount;
		this.startIndex = startIndex;
		this.pageSize = pageSize;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	/**
	 * 获取总页数
	 * @return 总页数
	 */
	public int getTotalPage() {
		int remainRecord = getTotalCount() % pageSize;
		int totalPage = 0;
		// 计算总页数
		if (remainRecord == 0) {
			totalPage = getTotalCount() / pageSize;
		} else {
			totalPage = getTotalCount() / pageSize + 1;
		}
		return totalPage;
	}

	/**
	 * 根据页码计算startIndex
	 * @param currPage 当前页
	 * @return startIndex
	 */
	public int getStartIndex(int currPage) {
		return (currPage - 1) * pageSize;
	}

	/**
	 * 获取当前页
	 * @return 当前页
	 */
	public int getCurrPage() {
		return startIndex / pageSize + 1;
	}
	
	
	/**
	 * 获取下一页
	 * @return 下一页
	 */
	public int getNextPage() {
		int currPage = getCurrPage();
		int totalPage = getTotalPage();
		if (currPage == totalPage)
			return totalPage;
		else
			return currPage + 1;
	}

	/**
	 * 获取上一页
	 * @return 上一页
	 */
	public int getPrevPage() {
		int currPage = getCurrPage();
		if (currPage == 1)
			return 1;
		else
			return currPage - 1;
	}

	/**
	 * 计算翻页条上显示的页码列表,假定只显示5个页码
	 * @return 显示的页码列表
	 */
	public List<Integer> getPageNumList() {
		List<Integer> list = null;
		int totalPage = getTotalPage();
		int currPage = getCurrPage();
		if (totalPage <= 5) { // 小于翻页条显示总个数
			list = new ArrayList<Integer>(totalPage);
			for (int i = 1; i <= totalPage; i++)
				list.add(i);
		} else { // 大于翻页条显示总个数
			list = new ArrayList<Integer>(5);

			if (currPage <= 2) { // 当前页不大于翻页条一半时不移动页码
				list.add(1);
				list.add(2);
				list.add(3);
				list.add(4);
				list.add(5);
			} else if (currPage > 2) {
				list.add(currPage - 2);
				list.add(currPage - 1);
				list.add(currPage);
				if (currPage + 1 <= totalPage) list.add(currPage + 1);
				if (currPage + 2 <= totalPage) list.add(currPage + 2); 
				
				if(list.size() == 4) {
					list.clear();
					list.add(currPage-3);
					list.add(currPage-2);
					list.add(currPage-1);
					list.add(currPage);
					list.add(currPage + 1);
				}
				
				if(list.size() == 3) {
					list.clear();
					list.add(currPage-4);
					list.add(currPage-3);
					list.add(currPage-2);
					list.add(currPage-1);
					list.add(currPage);
				}
				
			}
		}
		return list;
	}

	/**
	 * 是否有上一页
	 * @return 
	 */
	public boolean hasPrev() {
		if (getCurrPage() == 1) return false;
		else
		return true;
	}

	/**
	 * 是否有下一页
	 * @return 
	 */
	public boolean hasNext() {
		
		if (getCurrPage() == getTotalPage() || getTotalPage() == 0) return false;
		else
		return true;
	}

	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	
	@Override
	public String toString() {
		return "总页数:" + getTotalPage()
				+", 当前页:" + getCurrPage()
				+", 是否有上一页:" + hasPrev()
				+", 是否有下一页:" + hasNext()
				+", 当前页记录条数:" + list.size();
	}
	
	
}

