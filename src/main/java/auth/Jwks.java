package auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Jwks {

	@JsonProperty("keys")
	private List<Key> keys;

	public List<Key> getKeys(){
		return keys;
	}
}