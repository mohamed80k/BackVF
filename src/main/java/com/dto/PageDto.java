package com.dto;

import java.util.List;

public class PageDto<T> {

	private List<T> content;
	private int totalPages;
	private long totalElements;
	private int size;
	private int number;
	private int numberOfElements;
	private boolean first;
	private boolean last;

	public PageDto(List<T> content, int totalPages, long totalElements, int size, int number, int numberOfElements,
			boolean first, boolean last) {
		super();
		this.content = content;
		this.totalPages = totalPages;
		this.totalElements = totalElements;
		this.size = size;
		this.number = number;
		this.numberOfElements = numberOfElements;
		this.first = first;
		this.last = last;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

}
