package src.config;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLConfigReader {

    public static Config readConfig(String filePath) {
        Config config = new Config();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new File(filePath));
            document.getDocumentElement().normalize();

            // Client config reading
            NodeList clientList = document.getElementsByTagName("client");
            if (clientList.getLength() > 0) {
                Node clientNode = clientList.item(0);
                if (clientNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element clientElement = (Element) clientNode;
                    ClientConfig clientConfig = new ClientConfig();
                    clientConfig.setUsername(clientElement.getElementsByTagName("username").item(0).getTextContent());
                    clientConfig.setPassword(clientElement.getElementsByTagName("password").item(0).getTextContent());
                    clientConfig.setEmail(clientElement.getElementsByTagName("email").item(0).getTextContent());
                    clientConfig.setTimeRefresh(Integer.parseInt(clientElement.getElementsByTagName("autoLoad").item(0).getTextContent()));
                    config.setClientConfig(clientConfig);
                }
            }

            // Server config reading
            NodeList serverList = document.getElementsByTagName("server");
            if (serverList.getLength() > 0) {
                Node serverNode = serverList.item(0);
                if (serverNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element serverElement = (Element) serverNode;
                    ServerConfig serverConfig = new ServerConfig();
                    serverConfig.setSmtpHost(serverElement.getElementsByTagName("smtphost").item(0).getTextContent());
                    serverConfig.setPop3Host(serverElement.getElementsByTagName("pop3host").item(0).getTextContent());
                    serverConfig.setSmtpPort(Integer.parseInt(serverElement.getElementsByTagName("smtp").item(0).getTextContent()));
                    serverConfig.setPop3Port(Integer.parseInt(serverElement.getElementsByTagName("pop3").item(0).getTextContent()));
                    config.setServerConfig(serverConfig);
                }
            }

            // Filter configs reading
            NodeList filterList = document.getElementsByTagName("filter");
            List<FilterConfig> filters = new ArrayList<>();
            for (int i = 0; i < filterList.getLength(); i++) {
                Node filterNode = filterList.item(i);
                if (filterNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element filterElement = (Element) filterNode;
                    FilterConfig filterConfig = new FilterConfig(filterElement.getElementsByTagName("type").item(0).getTextContent());
                    filterConfig.setFilterInfo(filterElement.getElementsByTagName("info").item(0).getTextContent());
                    filterConfig.setFolder(filterElement.getElementsByTagName("folder").item(0).getTextContent());
                    filters.add(filterConfig);
                }
            }
            config.setFilterConfigs(filters);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return config;
    }
}

