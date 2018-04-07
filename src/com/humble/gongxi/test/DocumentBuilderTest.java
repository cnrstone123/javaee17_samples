package com.humble.gongxi.test;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DocumentBuilderTest {
	static public void main(String[] arg) {
		new DocumentBuilderTest().testIt();
	}
	private void testIt(){
		boolean validate = false;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(validate);
		dbf.setNamespaceAware(true);
		dbf.setIgnoringElementContentWhitespace(true);

		Document doc = null;
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			doc = builder.parse(new InputSource(new StringReader(xmlString)));
		} catch (SAXException e) {
			System.exit(1);
		} catch (ParserConfigurationException e) {
			System.err.println(e);
			System.exit(1);
		} catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		}

		TreeDumper td = new TreeDumper();
		td.dump(doc);
	}

	static String xmlString = "<PHONEBOOK>" + "  <PERSON>" + "   <NAME >Joe Wang</NAME>"
			+ "   <EMAIL property=\"working\">joe@yourserver.com</EMAIL>" + "   <TELEPHONE>202-999-9999</TELEPHONE>"
			+ "   <WEB>www.java2s.com</WEB>" + "  </PERSON>" + "  <PERSON>   " + "<NAME>Karol</NAME>"
			+ "   <EMAIL>karol@yourserver.com</EMAIL>" + "   <TELEPHONE>306-999-9999</TELEPHONE>"
			+ "   <WEB>www.java2s.com</WEB>" + "  </PERSON>" + "  <PERSON>" + "   <NAME>Green</NAME>"
			+ "   <EMAIL>green@yourserver.com</EMAIL>" + "   <TELEPHONE>202-414-9999</TELEPHONE>"
			+ "   <WEB>www.java2s.com</WEB>" + "  </PERSON>" + "  </PHONEBOOK>";
}

class TreeDumper {
	public void dump(Document doc) {
		dumpLoop((Node) doc, "");
	}

	private void dumpLoop(Node node, String indent) {
		switch (node.getNodeType()) {
		case Node.CDATA_SECTION_NODE:
			System.out.println(indent + "CDATA_SECTION_NODE");
			break;
		case Node.COMMENT_NODE:
			System.out.println(indent + "COMMENT_NODE");
			break;
		case Node.DOCUMENT_FRAGMENT_NODE:
			System.out.println(indent + "DOCUMENT_FRAGMENT_NODE");
			break;
		case Node.DOCUMENT_NODE:
			System.out.println(indent + "DOCUMENT_NODE");
			break;
		case Node.DOCUMENT_TYPE_NODE:
			System.out.println(indent + "DOCUMENT_TYPE_NODE");
			break;
		case Node.ELEMENT_NODE:
			System.out.println(indent + "ELEMENT_NODE");
			break;
		case Node.ENTITY_NODE:
			System.out.println(indent + "ENTITY_NODE");
			break;
		case Node.ENTITY_REFERENCE_NODE:
			System.out.println(indent + "ENTITY_REFERENCE_NODE");
			break;
		case Node.NOTATION_NODE:
			System.out.println(indent + "NOTATION_NODE");
			break;
		case Node.PROCESSING_INSTRUCTION_NODE:
			System.out.println(indent + "PROCESSING_INSTRUCTION_NODE");
			break;
		case Node.TEXT_NODE:
			System.out.println(indent + "TEXT_NODE");
			break;
		default:
			System.out.println(indent + "Unknown node");
			break;
		}
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++)
			dumpLoop(list.item(i), indent + "   ");
	}
}
