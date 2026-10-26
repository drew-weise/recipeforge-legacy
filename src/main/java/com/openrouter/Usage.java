package com.openrouter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Usage{

	@JsonProperty("completion_tokens")
	private int completionTokens;

	@JsonProperty("prompt_tokens")
	private int promptTokens;

	@JsonProperty("cost")
	private int cost;

	@JsonProperty("cost_details")
	private CostDetails costDetails;

	@JsonProperty("completion_tokens_details")
	private CompletionTokensDetails completionTokensDetails;

	@JsonProperty("prompt_tokens_details")
	private PromptTokensDetails promptTokensDetails;

	@JsonProperty("total_tokens")
	private int totalTokens;

	@JsonProperty("is_byok")
	private boolean isByok;

	public void setCompletionTokens(int completionTokens){
		this.completionTokens = completionTokens;
	}

	public int getCompletionTokens(){
		return completionTokens;
	}

	public void setPromptTokens(int promptTokens){
		this.promptTokens = promptTokens;
	}

	public int getPromptTokens(){
		return promptTokens;
	}

	public void setCost(int cost){
		this.cost = cost;
	}

	public int getCost(){
		return cost;
	}

	public void setCostDetails(CostDetails costDetails){
		this.costDetails = costDetails;
	}

	public CostDetails getCostDetails(){
		return costDetails;
	}

	public void setCompletionTokensDetails(CompletionTokensDetails completionTokensDetails){
		this.completionTokensDetails = completionTokensDetails;
	}

	public CompletionTokensDetails getCompletionTokensDetails(){
		return completionTokensDetails;
	}

	public void setPromptTokensDetails(PromptTokensDetails promptTokensDetails){
		this.promptTokensDetails = promptTokensDetails;
	}

	public PromptTokensDetails getPromptTokensDetails(){
		return promptTokensDetails;
	}

	public void setTotalTokens(int totalTokens){
		this.totalTokens = totalTokens;
	}

	public int getTotalTokens(){
		return totalTokens;
	}

	public void setIsByok(boolean isByok){
		this.isByok = isByok;
	}

	public boolean isIsByok(){
		return isByok;
	}
}