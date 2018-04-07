package com.humble.gongxi.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.List;
import java.io.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class HttpsURLClientSSLTest {
	public static void main(String[] args) {
		if(null == System.getProperty("https.protocols")) {
			// java-v6 default is TLSv1.0
			// java-v7/8 default is TLSv1.2
			// from: https://stackoverflow.com/questions/16541627/javax-net-ssl-sslexception-received-fatal-alert-protocol-version/47717547#47717547
			System.setProperty("https.protocols", "TLSv1.2");
			System.out.println("//-- changed protocols to "+System.getProperty("https.protocols"));
		}
		new HttpsURLClientSSLTest().testIt();
	}
	private List<String> makeTargetList(){
		String[] urls = {
			"https://www.google.com/ncr",
			"https://www.naver.com",
			"https://stackoverflow.com/questions/163552/jdk-jre-source-code-with-matching-jsse-ssl-source-code-and-matching-runnable-j",
			"https://www.korcham.net/nCham/Service/Event/appl/CompanySearch_1.asp",
			"https://api.github.com/users/jeresig",
			"https://dart.fss.or.kr/dsab001/main.do?autoSearch=true"
		};
		return Arrays.asList(urls);
	}
	private void testIt() {
		List<String> https_urls = makeTargetList();
		URL url;
		for(String https_url :https_urls){
			System.out.println("//-- "+https_url);
			try {
				url = new URL(https_url);
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				// dumpl all cert info
				print_https_cert(conn);
				// dump all the content
				print_content(conn);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void print_https_cert(HttpsURLConnection conn) {
		if (conn != null) {
			try {
				System.out.println("\tResponse Code : " + conn.getResponseCode());
				System.out.println("\tCipher Suite : " + conn.getCipherSuite());
				Certificate[] certs = conn.getServerCertificates();
				for (Certificate cert : certs) {
					System.out.println("\tCert Type : " + cert.getType());
					System.out.println("\tCert Hash Code : " + cert.hashCode());
					System.out.println("\tCert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
					System.out.println("\tCert Public Key Format : " + cert.getPublicKey().getFormat());
				}
			} catch (SSLPeerUnverifiedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void print_content(HttpsURLConnection con) {
		if (con != null) {
			try {
				System.out.println("****** Content of the URL ********");
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String input;
				while ((input = br.readLine()) != null) {
					System.out.println(input);
					
					// just 1-line..stop..!!
					break;
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}