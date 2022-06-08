package com.alexbondarenko.newsviewer.pojo.news;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Source implements Serializable {

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private Object id;

	public String getName(){
		return name;
	}

	public Object getId(){
		return id;
	}
}