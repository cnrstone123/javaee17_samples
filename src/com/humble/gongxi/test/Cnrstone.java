package com.humble.gongxi.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class Cnrstone {
	private static final String f0 = ".";
	private static final String f1 = "D:/projFile/EclipseSPACE/eclipse45w0/AdressSupporter/src/com/humble/adress/WebMiniServer.java";

	public static void main(String[] args) throws BadLocationException {

		try (BufferedReader br = new BufferedReader(new FileReader(f1))) {

			String sCurrentLine;
			File fi0 = new File(f0);
			System.out.println(
					String.format("//-- curr-loc ={%s}, filename ={%s}", fi0.getCanonicalPath(), fi0.getName()));
			int num = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				if (num > 10) break;
				System.out.println(sCurrentLine);
				num++;
			}

			Cnrstone.urlconnTest();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void urlconnTest() throws IOException, BadLocationException {
		URL url = new URL("https://api.github.com/users/jeresig");
//		URL url = new URL("http://www.java2s.com");
		HttpURLConnection urlconn = (HttpURLConnection) url.openConnection();
		InputStream in = new BufferedInputStream(urlconn.getInputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		try {
			String scurr;
			while ((scurr = br.readLine()) != null) {
				System.out.println(scurr);
			}
		} finally {
			if (urlconn != null){
				urlconn.disconnect();
			}
		}

	    HTMLEditorKit htmlKit = new HTMLEditorKit();
	    HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
	    HTMLEditorKit.Parser parser = new ParserDelegator();
	    HTMLEditorKit.ParserCallback callback = htmlDoc.getReader(0);
	    parser.parse(br, callback, true);

	    for (HTMLDocument.Iterator iterator = htmlDoc.getIterator(HTML.Tag.A); 
	    		iterator.isValid(); iterator.next()) {

	      AttributeSet attributes = iterator.getAttributes();
	      String srcString = (String) attributes.getAttribute(HTML.Attribute.HREF);
	      System.out.print(srcString);
	      int startOffset = iterator.getStartOffset();
	      int endOffset = iterator.getEndOffset();
	      int length = endOffset - startOffset;
	      String text = htmlDoc.getText(startOffset, length);
	      System.out.println(" - " + text);
	    }
	}
}
