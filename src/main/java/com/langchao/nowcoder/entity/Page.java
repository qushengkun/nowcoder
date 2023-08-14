package com.langchao.nowcoder.entity;

/**
 * Description: Page
 * 封装分页相关的信息
 * @Author: qsk
 * @Create: 2023/8/13 - 20:43
 * @version: v1.0
 */

public class Page {

    // 当前页码
    private int current = 1;
    // 每个页面中显示的上限
    private int limit = 10;
    // 总的记录数
    private int rows;

    // 查询路径（用于复用分页链接）
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if(rows >= 1){
            this.rows = rows;
        }
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if(current >= 1) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }


    public void setLimit(int limit) {
        if(limit >= 1 && limit <= 100) {
            this.limit = limit;
        }
    }

    /**
     * 该页的起始行数
     * @return
     */
    public int getOff(){
        return (current - 1) * limit;
    }

    /**
     * 获取总页码
     * @return
     */
    public int getTotal(){
        if(rows % limit == 0){
            return rows / limit;
        }else{
            return rows / limit + 1;
        }
    }

    /**
     * 获取起始页码（首页）
     * @return
     */
    public int getStart(){
        int start = current - 1;
        return start < 1 ? 1 : start;
    }

    /**
     * 获取末尾页码 （末页）
     * @return
     */
    public int getEnd(){
        return getTotal();
    }


}
