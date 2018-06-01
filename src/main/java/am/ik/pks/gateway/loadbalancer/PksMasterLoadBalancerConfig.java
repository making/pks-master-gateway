package am.ik.pks.gateway.loadbalancer;

import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PksMasterLoadBalancerConfig {
	@Bean
	public LoadBalancerClientFilter loadBalancerClientFilter(
			PksMasterLoadBalancerClient loadBalancerClient) {
		return new LoadBalancerClientFilter(loadBalancerClient);
	}
}
