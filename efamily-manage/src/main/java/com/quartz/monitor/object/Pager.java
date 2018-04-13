package com.quartz.monitor.object;


public class Pager {  
	public final static int DEFAULT_PAGE_SIZE = 10;
	public final static int DEFAULT_CURRENT_PAGE = 1;
	//总页数  
    private int totalPage;     
    //总记录数
    private int totalCount;  
    //当前页  
    private int currentPage ;  
    //每页的数量  
    private int pageSize = DEFAULT_PAGE_SIZE;      

    public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/** 
     * 分页处理 
     * @param current_page 当前页 
     * @param page_size 每页的数量 
     * @param total_count 总记录数 
     */  
    public void paging(int currentPage, int pageSize, int totalCount){  
        this.currentPage = currentPage;  
        this.pageSize = pageSize;  
        this.totalCount = totalCount;  
          
        if(currentPage < 1){  
            this.currentPage = 1;  
        }  
         
        this.totalPage = (this.totalCount + pageSize - 1)/pageSize;  
         
    }  
      
}  