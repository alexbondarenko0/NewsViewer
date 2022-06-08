package com.alexbondarenko.newsviewer.pojo.news;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArticlesItem implements Serializable {
	public ArticlesItem(String publishedAt, String author, String urlToImage, String description, Source source, String title, String url, String content) {
		this.publishedAt = publishedAt;
		this.author = author;
		this.urlToImage = urlToImage;
		this.description = description;
		this.source = source;
		this.title = title;
		this.url = url;
		this.content = content;
	}

	@SerializedName("publishedAt")
	private String publishedAt;

	@SerializedName("author")
	private String author;

	@SerializedName("urlToImage")
	private String urlToImage;

	@SerializedName("description")
	private String description;

	@SerializedName("source")
	private Source source;

	@SerializedName("title")
	private String title;

	@SerializedName("url")
	private String url;

	@SerializedName("content")
	private String content;

	public String getPublishedAt(){
		SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		SimpleDateFormat output = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		try {
			Date d = input.parse(publishedAt);
			return output.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return publishedAt;
	}

	public String getAuthor(){
		return author;
	}

	public String getUrlToImage(){
		return urlToImage;
	}

	public String getDescription(){
		return description;
	}

	public Source getSource(){
		return source;
	}

	public String getTitle(){
		return title;
	}

	public String getUrl(){
		return url;
	}

	public String getContent(){
		return content;
	}
}