package am.ik.pks.gateway.synchronizer;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import am.ik.pks.gateway.client.PksApiClient;
import am.ik.pks.gateway.client.PksCluster;

@Component
public class PksClusterSynchronizer {
	private final PksApiClient pksApiClient;
	private final AtomicReference<List<PksCluster>> clusters = new AtomicReference<>();

	public PksClusterSynchronizer(PksApiClient pksApiClient) {
		this.pksApiClient = pksApiClient;
	}

	@Scheduled(fixedDelayString = "${pks.cluster.synchronize.interval:60000}", initialDelay = 0)
	public void sync() {
		this.pksApiClient.getClusters().collectList().doOnNext(this.clusters::set)
				.subscribe();
	}

	public List<PksCluster> getClusters() {
		return clusters.get();
	}
}
