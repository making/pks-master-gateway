package am.ik.pks.gateway.client;

import java.io.Serializable;
import java.util.List;

public class PksCluster implements Serializable {
	private String name;
	private String planName;
	private String lastAction;
	private String lastActionState;
	private String lastActionDescription;
	private String uuid;
	private List<String> kubernetesMasterIps;
	private Parameters parameters;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getLastAction() {
		return lastAction;
	}

	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}

	public String getLastActionState() {
		return lastActionState;
	}

	public void setLastActionState(String lastActionState) {
		this.lastActionState = lastActionState;
	}

	public String getLastActionDescription() {
		return lastActionDescription;
	}

	public void setLastActionDescription(String lastActionDescription) {
		this.lastActionDescription = lastActionDescription;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<String> getKubernetesMasterIps() {
		return kubernetesMasterIps;
	}

	public void setKubernetesMasterIps(List<String> kubernetesMasterIps) {
		this.kubernetesMasterIps = kubernetesMasterIps;
	}

	public Parameters getParameters() {
		return parameters;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "Cluster{" + "name='" + name + '\'' + ", planName='" + planName + '\''
				+ ", lastAction='" + lastAction + '\'' + ", lastActionState='"
				+ lastActionState + '\'' + ", lastActionDescription='"
				+ lastActionDescription + '\'' + ", uuid='" + uuid + '\''
				+ ", kubernetesMasterIps=" + kubernetesMasterIps + ", parameters="
				+ parameters + '}';
	}

	public static class Parameters {
		private String kubernetesMasterHost;
		private Integer kubernetesMasterPort;
		private String workerHaproxyIpAddresses;
		private Integer kubernetesWorkerInstances;
		private String authorizationMode;

		public String getKubernetesMasterHost() {
			return kubernetesMasterHost;
		}

		public void setKubernetesMasterHost(String kubernetesMasterHost) {
			this.kubernetesMasterHost = kubernetesMasterHost;
		}

		public Integer getKubernetesMasterPort() {
			return kubernetesMasterPort;
		}

		public void setKubernetesMasterPort(Integer kubernetesMasterPort) {
			this.kubernetesMasterPort = kubernetesMasterPort;
		}

		public String getWorkerHaproxyIpAddresses() {
			return workerHaproxyIpAddresses;
		}

		public void setWorkerHaproxyIpAddresses(String workerHaproxyIpAddresses) {
			this.workerHaproxyIpAddresses = workerHaproxyIpAddresses;
		}

		public Integer getKubernetesWorkerInstances() {
			return kubernetesWorkerInstances;
		}

		public void setKubernetesWorkerInstances(Integer kubernetesWorkerInstances) {
			this.kubernetesWorkerInstances = kubernetesWorkerInstances;
		}

		public String getAuthorizationMode() {
			return authorizationMode;
		}

		public void setAuthorizationMode(String authorizationMode) {
			this.authorizationMode = authorizationMode;
		}

		@Override
		public String toString() {
			return "Parameters{" + "kubernetesMasterHost='" + kubernetesMasterHost + '\''
					+ ", kubernetesMasterPort=" + kubernetesMasterPort
					+ ", workerHaproxyIpAddresses='" + workerHaproxyIpAddresses + '\''
					+ ", kubernetesWorkerInstances=" + kubernetesWorkerInstances
					+ ", authorizationMode='" + authorizationMode + '\'' + '}';
		}
	}
}
