package am.ik.pks.gateway;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

@Component
@ConfigurationProperties(prefix = "pks.master.gateway")
public class PksMasterGatewayProperties {
	private String apiUrl;
	private String uaaUrl;
	private String uaaClientId;
	private String uaaClientSecret;

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getUaaUrl() {
		return uaaUrl;
	}

	public void setUaaUrl(String uaaUrl) {
		this.uaaUrl = uaaUrl;
	}

	public String getUaaClientId() {
		return uaaClientId;
	}

	public void setUaaClientId(String uaaClientId) {
		this.uaaClientId = uaaClientId;
	}

	public String getUaaClientSecret() {
		return uaaClientSecret;
	}

	public void setUaaClientSecret(String uaaClientSecret) {
		this.uaaClientSecret = uaaClientSecret;
	}

	public String basic() {
		return Base64Utils.encodeToString(
				(this.getUaaClientId() + ":" + this.getUaaClientSecret()).getBytes());
	}
}
