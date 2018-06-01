package am.ik.pks.gateway.route;

import java.net.URI;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.stereotype.Component;

import am.ik.pks.gateway.synchronizer.PksClusterSynchronizer;
import reactor.core.publisher.Flux;

@Component
public class PksMasterRouteDefinitionLocator implements RouteDefinitionLocator {
	private final PksClusterSynchronizer pksClusterSynchronizer;

	public PksMasterRouteDefinitionLocator(
			PksClusterSynchronizer pksClusterSynchronizer) {
		this.pksClusterSynchronizer = pksClusterSynchronizer;
	}

	@Override
	public Flux<RouteDefinition> getRouteDefinitions() {
		return Flux.fromIterable(
				this.pksClusterSynchronizer.getClusters().stream().map(cluster -> {
					RouteDefinition routeDefinition = new RouteDefinition();
					routeDefinition.setId(cluster.getUuid());
					routeDefinition.setUri(URI.create("lb://" + cluster.getName()));
					routeDefinition.setPredicates(
							Collections.singletonList(new PredicateDefinition("Host="
									+ cluster.getParameters().getKubernetesMasterHost()
									+ "*")));
					routeDefinition.setFilters(Collections
							.singletonList(new FilterDefinition("PreserveHostHeader")));
					return routeDefinition;
				}).collect(Collectors.toList()));
	}
}
