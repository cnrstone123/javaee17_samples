package com.humble.gongxi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class HttpURLClient {
    public static String getResponseFromCocha(String firstLine){
    	String result = "";
    	try {
    		ArrayList<String> lst = new HttpURLClient().getFromCocha(firstLine);
    		result = lst.toString();
    		PubLogger.info(result.replaceAll("\\], \\[", "], \n["));
    	} catch (Exception ex){
    		PubLogger.info("can't getting response.\n%s", ex);
    	}
    	return result;
    }
    private void checkSystemProps(String propName, String propValue){
		if(null == System.getProperty(propName)) {
			// java-v6 default is TLSv1.0
			// java-v7/8 default is TLSv1.2
			// from: https://stackoverflow.com/questions/16541627/javax-net-ssl-sslexception-received-fatal-alert-protocol-version/47717547#47717547
			System.setProperty(propName, propValue);
			System.out.println("//-- changed protocols to "+System.getProperty(propName));
		}
    }
    private ArrayList<String> getFromCocha(String firstLine) throws Exception{
    	final String USER_AGENT    = "Mozilla/5.0";
    	final String COCHA_URL     = "https://www.korcham.net/nCham/Service/Event/appl/CompanySearch_1.asp";
    	final String COCHA_CHARSET = "EUC-KR";
    	final String COCHA_METHOD  = "POST";
    	final String COCHA_CONTENT_TYPE = "application/x-www-form-urlencoded"; //"multipart/form-data"
    	
    	checkSystemProps("https.protocols", "TLSv1.2");
    	String compName = "";
    	String keyStr = "cocha";
    	compName = firstLine.substring(firstLine.indexOf(keyStr)+keyStr.length()).replace("HTTP/1.1", "").trim();
    	if (compName.startsWith("/")) compName = compName.substring(1);
        if (compName.equals("")) compName = "아테나";
        String postStr = "searchField=COMPANY_NAME&searchText=%BE%C6%C5%D7%B3%AA&x=0&y=0"; // 아테나
        				// "searchField=COMPANY_NAME&searchText="+URLEncoder.encode(compName, "UTF-8")+"&x=0&y=0";
        				// %EC%95%84%ED%85%8C%EB%82%98
        byte[] postStrBytes = postStr.toString().getBytes("UTF-8");

		HttpsURLConnection conn = (HttpsURLConnection) new URL(COCHA_URL).openConnection();
        conn.setRequestMethod(COCHA_METHOD);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Content-Type", COCHA_CONTENT_TYPE);
        conn.setRequestProperty("Content-Length", String.valueOf(postStrBytes.length));
        conn.getOutputStream().write(postStrBytes);

        BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream(),COCHA_CHARSET));

        String inputLine;
        Pattern ptrn = Pattern.compile("javascript:f_setCompany\\((.*)\\);");
        List<String> dataLines = new ArrayList<String>();
        int line = 0; boolean fromHere = false;
        while ((inputLine = bReader.readLine()) != null) {
        	if(!fromHere && !inputLine.startsWith("<form name=\"consultForm\" method=\"post\" >")){
        		continue;
        	} else { 
        		fromHere = true;
        	}
        	if(line % 2 == 0){
	            Matcher m = ptrn.matcher(inputLine);
	            if(m.find()){ // skips the duplication of line.
	            	dataLines.add("["+m.group(1)+"]");
	            }
            }
            line++;
        }
        bReader.close();
        return new ArrayList<String>(dataLines);
    }
}
