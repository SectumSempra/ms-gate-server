package com.be.gateserver;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
public class GateServerApplication {

    public static Map<String, Object> getMap() throws Exception {
        Map<String, Object> map = new HashMap<>();
        String ip = getIpAdressBy10();
        map.put("ip", ip);
        return map;

    }

    private static String getIpAdressBy10() throws SocketException {
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        while (e.hasMoreElements()) {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements()) {
                InetAddress i = (InetAddress) ee.nextElement();
                if (i.getHostAddress().startsWith("10."))
                    return i.getHostAddress();
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(GateServerApplication.class);
        application.setDefaultProperties(getMap());
        application.run(args);

    }
}
