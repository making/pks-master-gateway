package am.ik.pks.gateway.client;

import java.util.List;

import javax.net.ssl.SSLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;

import am.ik.pks.gateway.PksMasterGatewayProperties;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PksApiClient {
	private final Logger log = LoggerFactory.getLogger(PksApiClient.class);
	private final WebClient webClient;
	private final PksMasterGatewayProperties props;

	public PksApiClient(PksMasterGatewayProperties props,
			List<CodecCustomizer> codecCustomizers) {
		this.props = props;
		this.webClient = WebClient.builder()
				.clientConnector(new ReactorClientHttpConnector(options -> {
					try {
						options.sslContext(SslContextBuilder.forClient()
								.trustManager(InsecureTrustManagerFactory.INSTANCE)
								.build()).compression(true);
					}
					catch (SSLException e) {
						throw new RuntimeException(e);
					}
				}))
				.exchangeStrategies(ExchangeStrategies.builder()
						.codecs((codecs) -> codecCustomizers
								.forEach((customizer) -> customizer.customize(codecs)))
						.build())
				.build();
	}

	public Flux<PksCluster> getClusters() {
		log.info("Get clusters");
		return this.webClient.post().uri(this.props.getUaaUrl() + "/oauth/token")
				.body(Mono.just(new LinkedMultiValueMap<String, Object>() {
					{
						add("grant_type", "client_credentials");
					}
				}), LinkedMultiValueMap.class)
				.header(HttpHeaders.AUTHORIZATION, "Basic " + this.props.basic())
				.retrieve().bodyToMono(JsonNode.class)
				.map(n -> n.get("access_token").asText())
				.flatMapMany(token -> this.webClient.get()
						.uri(this.props.getApiUrl() + "/v1/clusters")
						.header(HttpHeaders.AUTHORIZATION, "bearer " + token).retrieve()
						.bodyToFlux(PksCluster.class)).log("cluster");
	}
}
