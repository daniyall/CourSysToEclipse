package ca.sfu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EclipseProjectRenamer {
	private File folder;
	
	public EclipseProjectRenamer(File folder) {
		this.folder = folder;
	}
	
	
	public void renameProjects() {
		assert folder.isDirectory();
		
		for(File dir : folder.listFiles()) {
			if(dir.isDirectory()) {
				for(File projFile : dir.listFiles()) {
					if(projFile.getName().equalsIgnoreCase(".project")) {
						changeProjectName(projFile, dir.getName());
					}
				}
			}
		}
	}

	private void changeProjectName(File projFile, String name) {
		DocumentBuilderFactory builderFactory =
		        DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
		    builder = builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
		    e.printStackTrace();  
		}
		
		try {
		    Document document = builder.parse(
		            new FileInputStream(projFile.getAbsolutePath()));
		    changeContent(document, name);
		    
		    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        DOMSource source = new DOMSource(document);
		    StreamResult result =  new StreamResult(projFile);
	        transformer.transform(source, result);
		} catch (SAXException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	private void changeContent(Document doc, String newName) {
		Element root = doc.getDocumentElement();
		NodeList rootList = root.getChildNodes();
		for (int i = 0; i < rootList.getLength(); i++) {
			Node element = (Node) rootList.item(i);
			if (element.getNodeName().equalsIgnoreCase("name")) {
//				System.out.print("Changing " + element.getTextContent() + " to " + newName + " ...");
				element.setTextContent(newName);
			}
		}
	}
}
