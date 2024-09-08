package src.config;

import java.util.List;

public class Config {
    private ClientConfig clientConfig;
    private ServerConfig serverConfig;
    private List<FilterConfig> filterConfigs;

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public List<FilterConfig> getFilterConfigs() {
        return filterConfigs;
    }

    public void setClientConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public void setFilterConfigs(List<FilterConfig> filterConfigs) {
        this.filterConfigs = filterConfigs;
    }
}
