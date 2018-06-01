package am.ik.pks.gateway.loadbalancer;

import java.net.URI;
import java.util.List;
import java.util.Random;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequest;
import org.springframework.stereotype.Component;

import am.ik.pks.gateway.discovery.PksMasterDiscoveryClient;

@Component
public class PksMasterLoadBalancerClient implements LoadBalancerClient {
	private final PksMasterDiscoveryClient discoveryClient;
	private final Random random = new Random(System.nanoTime());

	public PksMasterLoadBalancerClient(PksMasterDiscoveryClient discoveryClient) {
		this.discoveryClient = discoveryClient;
	}

	@Override
	public <T> T execute(String serviceId, LoadBalancerRequest<T> request) {
		throw new UnsupportedOperationException("execute is not implemented.");
	}

	@Override
	public <T> T execute(String serviceId, ServiceInstance serviceInstance,
			LoadBalancerRequest<T> request) {
		throw new UnsupportedOperationException("execute is not implemented.");
	}

	@Override
	public URI reconstructURI(ServiceInstance instance, URI original) {
		return DefaultServiceInstance.getUri(instance);
	}

	@Override
	public ServiceInstance choose(String serviceId) {
		List<ServiceInstance> instances = this.discoveryClient.getInstances(serviceId);
		int index = random.nextInt(instances.size());
		return instances.get(index);
	}
}
