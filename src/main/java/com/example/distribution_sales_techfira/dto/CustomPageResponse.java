package com.example.distribution_sales_techfira.dto;

import java.util.List;

public class CustomPageResponse <T>{
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private boolean sorted;

    public List<T> getContent() {
        return content;
    }

    public CustomPageResponse(List<T> content, boolean unsorted, boolean sorted, long totalElements, int size, int page) {
        this.content = content;
        this.unsorted = unsorted;
        this.sorted = sorted;
        this.totalElements = totalElements;
        this.size = size;
        this.page = page;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isSorted() {
        return sorted;
    }

    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    public boolean isUnsorted() {
        return unsorted;
    }

    public void setUnsorted(boolean unsorted) {
        this.unsorted = unsorted;
    }

    private boolean unsorted;
}
