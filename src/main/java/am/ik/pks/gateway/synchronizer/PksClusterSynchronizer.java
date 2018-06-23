package am.ik.pks.gateway.synchronizer;

import am.ik.pks.gateway.client.PksApiClient;
import am.ik.pks.gateway.client.PksCluster;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class PksClusterSynchronizer {
    private final PksApiClient pksApiClient;
    private final AtomicReference<List<PksCluster>> clusters = new AtomicReference<>();
    private final ApplicationEventPublisher publisher;


    public PksClusterSynchronizer(PksApiClient pksApiClient, ApplicationEventPublisher publisher) {
        this.pksApiClient = pksApiClient;
        this.publisher = publisher;
    }

    @Scheduled(fixedDelayString = "${pks.cluster.synchronize.interval:30000}", initialDelay = 0)
    public void sync() {
        this.pksApiClient.getClusters().collectList()
                .doOnNext(this.clusters::set)
                .doFinally(x -> this.publisher.publishEvent(new RefreshRoutesEvent(this)))
                .subscribe();
    }

    public List<PksCluster> getClusters() {
        return clusters.get();
    }
}
