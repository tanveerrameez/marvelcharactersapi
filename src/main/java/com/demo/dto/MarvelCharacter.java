package com.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarvelCharacter {
	
	@ApiModelProperty(notes = "ID of the Marvel character", name="id",example="1009718", position =1)
	private int id;
	
	@ApiModelProperty(notes = "Name of the Marvel character",name="name", example ="Wolverine", position =2)
	private String name;
	
	@ApiModelProperty(notes = "Description of the Marvel character",name="description", example ="Born with super-human senses and the power to heal from almost any" + 
			"wound, Wolverine was captured by a secret Canadian organization and given an" + 
			"unbreakable skeleton and claws. Treated like an animal, it took years for him to" + 
			"control himself. Now, he's a premiere member of both the X-Men and the Avengers.", position =3 )
	private String description;

	@ApiModelProperty(notes = "Thumbnail of the Marvel character", name="thumbnail", position = 4)
	private Thumbnail thumbnail;

	public MarvelCharacter() {}
	
	public MarvelCharacter(int id, String name, String description, Thumbnail thumbnail) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.thumbnail = thumbnail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Thumbnail getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Thumbnail thumbnail) {
		this.thumbnail = thumbnail;
	}

}
