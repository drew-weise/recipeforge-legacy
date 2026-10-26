package com.openrouter;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatCompletionResponse{

	@JsonProperty("provider")
	private String provider;

	@JsonProperty("created")
	private int created;

	@JsonProperty("usage")
	private Usage usage;

	@JsonProperty("model")
	private String model;

	@JsonProperty("service_tier")
	private Object serviceTier;

	@JsonProperty("id")
	private String id;

	@JsonProperty("system_fingerprint")
	private Object systemFingerprint;

	@JsonProperty("choices")
	private List<ChoicesItem> choices;

	@JsonProperty("object")
	private String object;

	public void setProvider(String provider){
		this.provider = provider;
	}

	public String getProvider(){
		return provider;
	}

	public void setCreated(int created){
		this.created = created;
	}

	public int getCreated(){
		return created;
	}

	public void setUsage(Usage usage){
		this.usage = usage;
	}

	public Usage getUsage(){
		return usage;
	}

	public void setModel(String model){
		this.model = model;
	}

	public String getModel(){
		return model;
	}

	public void setServiceTier(Object serviceTier){
		this.serviceTier = serviceTier;
	}

	public Object getServiceTier(){
		return serviceTier;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setSystemFingerprint(Object systemFingerprint){
		this.systemFingerprint = systemFingerprint;
	}

	public Object getSystemFingerprint(){
		return systemFingerprint;
	}

	public void setChoices(List<ChoicesItem> choices){
		this.choices = choices;
	}

	public List<ChoicesItem> getChoices(){
		return choices;
	}

	public void setObject(String object){
		this.object = object;
	}

	public String getObject(){
		return object;
	}
}