package com.humble.gongxi.test;
/*
 * Copyright (C) 2012-2016, Markus Sprunck <sprunck.markus@gmail.com>
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * - The name of its contributor may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
/*
 * Copyright (C) 2018-2022, Lee YS <lihumb21@gmail.com>
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * - The name of its contributor may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kr.go.ads.client.ReceiveDatas;

/**
 * The main class of the application
 */
public class AdressSupporterAgent {
    
    private static final Log LOGGER = LogFactory.getLog(AdressSupporterAgent.class);
    
    protected static final String VERSION = "0.0.1";
    
    /**
     * The model holds all data of the call stack analysis
     */
    public final ReceiveDatas dataModel = new ReceiveDatas();
        
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
                LOGGER.warn("The port must be a number.", e);
            }
        }
    }
    
    private void outputOfLocalHostName() {
        String hostname = "localhost";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            //hostname = addr.getCanonicalHostName();
        } catch (UnknownHostException e) {
            LOGGER.error("Could not resolve local host name into an address ", e);
        }
        LOGGER.info("Start server at http://" + hostname + ':' + port + "/lihumb21 ");
    }
    
    private void startHTTPServerThread() {
        final Thread serverThread = new WebMini(port, dataModel);
        serverThread.setPriority(Thread.NORM_PRIORITY);
        serverThread.start();
    }
    
    public static void main(final String[] args) {
        LOGGER.info("AdressSupporterAgent - (c) 2018-2023 by lihumb21, v" + VERSION);
        
        // Change the default port (8082)
        parseComandLine(args);
        
        // Start analyzer
        AdressSupporterAgent agent = new AdressSupporterAgent();
        agent.outputOfLocalHostName();
        agent.startHTTPServerThread();
    }
}
