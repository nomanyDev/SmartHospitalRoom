package nom.nci.client;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServiceDiscovery {

    private static class SampleListener implements ServiceListener {
        @Override
        public void serviceAdded(ServiceEvent event) {
            System.out.println("Service added: " + event.getInfo());
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            System.out.println("Service removed: " + event.getInfo());
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            ServiceInfo info = event.getInfo();
            int port = info.getPort();
            String name = info.getName();
            String type = info.getType();
            System.out.println("Service resolved: Name=" + name + ", Type=" + type + ", Port=" + port);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            // Create a JmDNS instance
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            System.out.println("Discovery: InetAddress.getLocalHost():" + InetAddress.getLocalHost());

            // Add service listeners for different types of services
            jmdns.addServiceListener("_smartbed._tcp.local.", new SampleListener());
            jmdns.addServiceListener("_temperaturecontrol._tcp.local.", new SampleListener());
            jmdns.addServiceListener("_lighting._tcp.local.", new SampleListener());

            // Wait a bit to discover services
            Thread.sleep(30000);

        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}