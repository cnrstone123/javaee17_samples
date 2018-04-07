package com.humble.gongxi;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CompInfoCli {
    protected static final String VERSION = "0.0.1";
    public final ReceivedDatas dataModel = new ReceivedDatas();

    /**
     * The default port should not be used by any other application. 
     * 
     * See <a href="https://en.wikipedia.org/wiki/List_of_TCP_and_UDP_port_numbers">
     * https://en.wikipedia.org/wiki/List_of_TCP_and_UDP_port_numbers </a>
     * for standard port usage. 
     */
    private static int port = 8082;
    
    private static void parseComandLine(String[] args) {
        if (1 == args.length) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                PubLogger.warn("The port must be a number.\n%s", e);
            }
        }
    }
    
    private void outputOfLocalHostName() {
        String hostname = "localhost";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            //hostname = addr.getCanonicalHostName();
            PubLogger.info("InetAddress.getLocalHost =%s", addr);
        } catch (UnknownHostException e) {
            PubLogger.error("Could not resolve local host name into an address \n%s", e);
        }
        PubLogger.info("Start server at http://" + hostname + ':' + port + "/lihumb21 ");
    }
    
    private void startHTTPServerThread() {
        final Thread serverThread = new WebMini(port, dataModel);
        serverThread.setPriority(Thread.NORM_PRIORITY);
        serverThread.start();
    }
    
    public static void main(final String[] args) {
    	PubLogger.info("CompInfoCli - (c) 2018-2023 by lihumb21, v" + VERSION);
        
        // Change the default port (8082)
        parseComandLine(args);
        
        // Start analyzer
        CompInfoCli cli = new CompInfoCli();
        cli.outputOfLocalHostName();
        cli.startHTTPServerThread();
    }
}
