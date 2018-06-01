package am.ik.pks.gateway;

import javax.net.ssl.SSLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.ipc.netty.http.client.HttpClient;

@SpringBootApplication
@EnableScheduling
public class PksMasterGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(PksMasterGatewayApplication.class, args);
	}

	@Bean
	public HttpClient httpClient() {
		return HttpClient.create(options -> {
			try {
				options.sslContext(SslContextBuilder.forClient()
						.trustManager(InsecureTrustManagerFactory.INSTANCE).build())
						.compression(true);
			}
			catch (SSLException e) {
				throw new RuntimeException(e);
			}
		});
	}
}
