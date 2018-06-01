package am.ik.pks.gateway.discovery;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import am.ik.pks.gateway.client.PksCluster;
import am.ik.pks.gateway.synchronizer.PksClusterSynchronizer;

@Component
public class PksMasterDiscoveryClient implements DiscoveryClient {
	private final PksClusterSynchronizer pksClusterSynchronizer;

	public PksMasterDiscoveryClient(PksClusterSynchronizer pksClusterSynchronizer) {
		this.pksClusterSynchronizer = pksClusterSynchronizer;
	}

	@Override
	public String description() {
		return "PKS Master Discovery Client";
	}

	@Override
	public List<ServiceInstance> getInstances(String serviceId) {
		return this.pksClusterSynchronizer.getClusters().stream()
				.filter(c -> Objects.equals(serviceId, c.getName()))
				.flatMap(c -> c.getKubernetesMasterIps().stream()
						.map(ip -> new DefaultServiceInstance(c.getName(), ip,
								c.getParameters().getKubernetesMasterPort(), true,
								new HashMap<String, String>() {
									{
										put("uuid", c.getUuid());
										put("plan_name", c.getPlanName());
										put("kubernetes_master_host", c.getParameters()
												.getKubernetesMasterHost());
									}
								})))
				.collect(Collectors.toList());
	}

	@Override
	public List<String> getServices() {
		return this.pksClusterSynchronizer.getClusters().stream().map(PksCluster::getName)
				.collect(Collectors.toList());
	}
}
