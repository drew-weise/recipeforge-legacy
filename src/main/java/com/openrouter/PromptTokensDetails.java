package com.openrouter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PromptTokensDetails{

	@JsonProperty("cache_write_tokens")
	private int cacheWriteTokens;

	@JsonProperty("audio_tokens")
	private int audioTokens;

	@JsonProperty("video_tokens")
	private int videoTokens;

	@JsonProperty("cached_tokens")
	private int cachedTokens;

	public void setCacheWriteTokens(int cacheWriteTokens){
		this.cacheWriteTokens = cacheWriteTokens;
	}

	public int getCacheWriteTokens(){
		return cacheWriteTokens;
	}

	public void setAudioTokens(int audioTokens){
		this.audioTokens = audioTokens;
	}

	public int getAudioTokens(){
		return audioTokens;
	}

	public void setVideoTokens(int videoTokens){
		this.videoTokens = videoTokens;
	}

	public int getVideoTokens(){
		return videoTokens;
	}

	public void setCachedTokens(int cachedTokens){
		this.cachedTokens = cachedTokens;
	}

	public int getCachedTokens(){
		return cachedTokens;
	}
}