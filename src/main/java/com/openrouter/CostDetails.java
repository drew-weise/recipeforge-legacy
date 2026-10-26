package com.openrouter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CostDetails{

	@JsonProperty("upstream_inference_cost")
	private int upstreamInferenceCost;

	@JsonProperty("upstream_inference_completions_cost")
	private int upstreamInferenceCompletionsCost;

	@JsonProperty("upstream_inference_prompt_cost")
	private int upstreamInferencePromptCost;

	public void setUpstreamInferenceCost(int upstreamInferenceCost){
		this.upstreamInferenceCost = upstreamInferenceCost;
	}

	public int getUpstreamInferenceCost(){
		return upstreamInferenceCost;
	}

	public void setUpstreamInferenceCompletionsCost(int upstreamInferenceCompletionsCost){
		this.upstreamInferenceCompletionsCost = upstreamInferenceCompletionsCost;
	}

	public int getUpstreamInferenceCompletionsCost(){
		return upstreamInferenceCompletionsCost;
	}

	public void setUpstreamInferencePromptCost(int upstreamInferencePromptCost){
		this.upstreamInferencePromptCost = upstreamInferencePromptCost;
	}

	public int getUpstreamInferencePromptCost(){
		return upstreamInferencePromptCost;
	}
}