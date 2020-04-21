package com.demo.dto;

import io.swagger.annotations.ApiModelProperty;

public class Thumbnail {
	
	@ApiModelProperty(notes = "path of the Marvel character's thumbnail",name="path", example="http://i.annihil.us/u/prod/marvel/i/mg/2/60/537bcaef0f6cf", position=1)
	private String path;
	
	@ApiModelProperty(notes = "image file extension of the Marvel character's thumbnail",name="extension", example="jpg", position=2)
	private String extension;
	
	public Thumbnail() {}
	public Thumbnail(String path, String extension) {
		super();
		this.path = path;
		this.extension = extension;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
}
