package com.helen.dayjoke.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Helen on 2016/4/27.
 *
 */
public class ResponseBodyEn extends BaseEn{
    @SerializedName("allNum")
    private int totalCount;
    @SerializedName("allPages")
    private int pageCount;
    private int currentPage;
    @SerializedName("maxResult")
    private int pageSize;
    @SerializedName("contentlist")
    private List<JokeEn> jokeEnList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
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

    public List<JokeEn> getJokeEnList() {
        return jokeEnList;
    }

    public void setJokeEnList(List<JokeEn> jokeEnList) {
        this.jokeEnList = jokeEnList;
    }
}
