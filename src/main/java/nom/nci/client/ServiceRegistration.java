
package nom.nci.client;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;

// This code is adapted from https://github.com/jmdns/jmdns
public class ServiceRegistration {

    public static void main(String[] args) throws InterruptedException {
        try {
            // Create a JmDNS instance
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            System.out.println("Registration: InetAddress.getLocalHost():" + InetAddress.getLocalHost());

            // Register Service 1
            ServiceInfo service1 = ServiceInfo.create("_smartbed._tcp.local.", "SmartBedService", 8001, "Smart Bed Service");
            jmdns.registerService(service1);
            System.out.println("Registered Smart Bed Service");

            // Register Service 2
            ServiceInfo service2 = ServiceInfo.create("_temperaturecontrol._tcp.local.", "TemperatureControlService", 8088, "Temperature Control Service");
            jmdns.registerService(service2);
            System.out.println("Registered Temperature Control Service");

            // Register Service 3
            ServiceInfo service3 = ServiceInfo.create("_lighting._tcp.local.", "LightingService", 8000, "Lighting Control Service");
            jmdns.registerService(service3);
            System.out.println("Registered Lighting Service");

            // Wait a bit to keep services registered
            Thread.sleep(30000);

            // Optionally, unregister all services after use
            // jmdns.unregisterAllServices();
            // System.out.println("All services unregistered");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
