package auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Jwks {

	@JsonProperty("keys")
	private List<Jwk> keys;

	public List<Jwk> getKeys(){
		return keys;
	}
}