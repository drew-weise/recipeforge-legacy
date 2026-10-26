package com.openrouter;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message{

	@JsonProperty("role")
	private String role;

	@JsonProperty("reasoning")
	private String reasoning;

	@JsonProperty("refusal")
	private Object refusal;

	@JsonProperty("reasoning_details")
	private List<ReasoningDetailsItem> reasoningDetails;

	@JsonProperty("content")
	private String content;

	public void setRole(String role){
		this.role = role;
	}

	public String getRole(){
		return role;
	}

	public void setReasoning(String reasoning){
		this.reasoning = reasoning;
	}

	public String getReasoning(){
		return reasoning;
	}

	public void setRefusal(Object refusal){
		this.refusal = refusal;
	}

	public Object getRefusal(){
		return refusal;
	}

	public void setReasoningDetails(List<ReasoningDetailsItem> reasoningDetails){
		this.reasoningDetails = reasoningDetails;
	}

	public List<ReasoningDetailsItem> getReasoningDetails(){
		return reasoningDetails;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}
}