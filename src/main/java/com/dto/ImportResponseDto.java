package com.dto;

public class ImportResponseDto {

	private boolean status;
	private String title;
	private String body;

	public ImportResponseDto(boolean status, String title, String body) {
		this.status = status;
		this.title = title;
		this.body = body;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
