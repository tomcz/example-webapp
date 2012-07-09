package example.spring;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class StatsdPerformanceMonitorInterceptor extends AbstractMonitoringInterceptor {

    private final DatagramSocket socket;
    private final InetAddress host;
    private final int port;

    public StatsdPerformanceMonitorInterceptor(String host, int port) throws Exception {
        this.host = InetAddress.getByName(host);
        this.socket = new DatagramSocket();
        this.port = port;
    }

    @Override
    protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {
        String name = createInvocationTraceName(invocation);
        long start = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } finally {
            long end = System.currentTimeMillis();
            notifyStatsdCollector(name, end - start);
        }
    }

    private void notifyStatsdCollector(String name, long durationMillis) {
        try {
            byte[] data = String.format("%s:%d|ms", name, durationMillis).getBytes("UTF-8");
            socket.send(new DatagramPacket(data, data.length, host, port));
        } catch (Exception e) {
            defaultLogger.error("Cannot notify statsd", e);
        }
    }
}
