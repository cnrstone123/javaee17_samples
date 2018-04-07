package com.humble.gongxi.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLTest {
	public static void main(String[] args) throws Exception {
		
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { 
				new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					}
					public void checkServerTrusted(X509Certificate[] certs, String authType) {
					}
		}};
		// Install the all-trusting trust manager
		final SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		
		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				System.out.println("//-- hostname = "+hostname+", sessId ="+session.getId());
				return true;
			}
		};

		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		
		if(null == System.getProperty("https.protocols")) {
			// java-v6 default is TLSv1.0
			// java-v7/8 default is TLSv1.2
			// from: https://stackoverflow.com/questions/16541627/javax-net-ssl-sslexception-received-fatal-alert-protocol-version/47717547#47717547
			System.setProperty("https.protocols", "TLSv1.2");
			System.out.println("//-- changed protocols to "+System.getProperty("https.protocols"));
		}
		
		for(String urlStr :SSLTest.makeTargetList()){
			System.out.println("\n//-- GET "+urlStr);
			try {
				URLConnection urlconn = (new URL(urlStr)).openConnection();
				SSLTest.prtTarget(urlconn.getInputStream());
			} catch(SSLException ssle){
				System.out.println("//-- SSLException, "+ssle.getMessage()+"\n\t cause, "+ssle.getCause());
			}
		}
		try {
			String urlStr = "https://www.korcham.net/nCham/Service/Event/appl/CompanySearch_1.asp";
			SSLTest.getResponseFrom(urlStr);
		} catch(SSLException ssle){
			System.out.println("//-- SSLException, "+ssle.getMessage()+"\n\t cause, "+ssle.getCause());
		}

	} // End of main
	private static List<String> makeTargetList(){
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
	/**
	 * prints specified target-page
	 * @param urlconn
	 * @throws Exception
	 */
	private static void prtTarget(InputStream is){
		
		final Reader reader = new InputStreamReader(is);
		final BufferedReader br = new BufferedReader(reader);
		
		String line = "";
		int lineNo = 0;
		try {
			while ((line = br.readLine()) != null) {
				System.out.println("\t"+line);
				if (lineNo > 10) break;
				lineNo++;
				
				break; // just 1-line..
			}
			br.close();
		} catch(IOException ioe){
			System.out.println("\t .... Ooooops.");
		} finally {
			if (br != null){
			}
		}
	}
    private static void getResponseFrom(String url) throws Exception{
    	System.out.println("\n//-- POST "+url+" HTTPS");
    	
    	final String USER_AGENT = "Mozilla/5.0";

        String compName = "아테나";
        String postStr = "searchField=COMPANY_NAME&searchText="+URLEncoder.encode(compName, "UTF-8")+"&x=0&y=0";
        System.out.println(postStr);
        postStr = "searchField=COMPANY_NAME&searchText=%BE%C6%C5%D7%B3%AA&x=0&y=0";
        System.out.println(postStr);
        byte[] postStrBytes = postStr.toString().getBytes("UTF-8");

		HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
		//add reuqest header
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        conn.setRequestProperty("Content-Type", "multipart/form-data");
        conn.setRequestProperty("Content-Length", String.valueOf(postStrBytes.length));
        conn.getOutputStream().write(postStrBytes);

        BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"EUC-KR"));
        String inputLine; int lineNo = 0;
        Pattern ptrn = Pattern.compile("javascript:f_setCompany\\((.*)\\);");
        List<String> dataLines = new ArrayList<String>();
        System.out.println("//-- dataLines...");
        
        while ((inputLine = bReader.readLine()) != null) {
            Matcher m = ptrn.matcher(inputLine);
            if(m.find() && (lineNo%2 == 0)){ // skips the duplication of line.
            	dataLines.add("["+m.group(1)+"]");
            	System.out.println("\t"+m.group(1));
            }
            lineNo++;
        }
        bReader.close();
    }

} // End of the class //
