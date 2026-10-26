package com.openrouter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChoicesItem{

	@JsonProperty("finish_reason")
	private String finishReason;

	@JsonProperty("native_finish_reason")
	private String nativeFinishReason;

	@JsonProperty("index")
	private int index;

	@JsonProperty("message")
	private Message message;

	@JsonProperty("logprobs")
	private Object logprobs;

	public void setFinishReason(String finishReason){
		this.finishReason = finishReason;
	}

	public String getFinishReason(){
		return finishReason;
	}

	public void setNativeFinishReason(String nativeFinishReason){
		this.nativeFinishReason = nativeFinishReason;
	}

	public String getNativeFinishReason(){
		return nativeFinishReason;
	}

	public void setIndex(int index){
		this.index = index;
	}

	public int getIndex(){
		return index;
	}

	public void setMessage(Message message){
		this.message = message;
	}

	public Message getMessage(){
		return message;
	}

	public void setLogprobs(Object logprobs){
		this.logprobs = logprobs;
	}

	public Object getLogprobs(){
		return logprobs;
	}
}