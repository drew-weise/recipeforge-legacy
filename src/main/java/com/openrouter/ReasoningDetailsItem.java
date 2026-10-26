package com.openrouter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReasoningDetailsItem{

	@JsonProperty("format")
	private String format;

	@JsonProperty("index")
	private int index;

	@JsonProperty("text")
	private String text;

	@JsonProperty("type")
	private String type;

	public void setFormat(String format){
		this.format = format;
	}

	public String getFormat(){
		return format;
	}

	public void setIndex(int index){
		this.index = index;
	}

	public int getIndex(){
		return index;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}
}