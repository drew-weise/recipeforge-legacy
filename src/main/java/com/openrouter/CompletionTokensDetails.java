package com.openrouter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompletionTokensDetails{

	@JsonProperty("audio_tokens")
	private int audioTokens;

	@JsonProperty("reasoning_tokens")
	private int reasoningTokens;

	@JsonProperty("image_tokens")
	private int imageTokens;

	public void setAudioTokens(int audioTokens){
		this.audioTokens = audioTokens;
	}

	public int getAudioTokens(){
		return audioTokens;
	}

	public void setReasoningTokens(int reasoningTokens){
		this.reasoningTokens = reasoningTokens;
	}

	public int getReasoningTokens(){
		return reasoningTokens;
	}

	public void setImageTokens(int imageTokens){
		this.imageTokens = imageTokens;
	}

	public int getImageTokens(){
		return imageTokens;
	}
}