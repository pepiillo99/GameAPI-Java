package me.pepe.GameAPI.Utils;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMUtils {
	// abre el archivo xml y devuelve el Document.
	public static Document abrirDOM (File file) {
		try {
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			fact.setIgnoringComments(true);
			fact.setIgnoringElementContentWhitespace(true);
			DocumentBuilder b = fact.newDocumentBuilder();
			return b.parse(file);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	// recoge todos los hijos de un nodo
	public static List<Node> getChildrens(Node node) {
		if (node.hasChildNodes()) {
			List<Node> nodes = new ArrayList<Node>();
			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				if (!node.getChildNodes().item(i).getNodeName().equals("#text")) {
					nodes.add(node.getChildNodes().item(i));
				}
			}
			return nodes;
		} else {
			return new ArrayList<Node>();
		}
	}
	// coge un hijo en especifico de un nodo
	public static Node getChild(Node node, String childName) {
		if (node.hasChildNodes()) {
			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				if (node.getChildNodes().item(i).getNodeName().equals(childName)) {
					return node.getChildNodes().item(i);
				}
			}
		}
		return null;
	}
	public static Color getColor(Node node) {
		return new Color(Integer.valueOf(getChild(node, "r").getTextContent()), Integer.valueOf(getChild(node, "g").getTextContent()), Integer.valueOf(getChild(node, "b").getTextContent()), Integer.valueOf(getChild(node, "a").getTextContent()));
	}
	// coge los nombres de los atributos de un nodo
	public static String[] getAttributes(Node node) {
		String[] result = new String[node.getAttributes().getLength()];
		for (int  i = 0; i < result.length; i++) {
			result[i] = node.getAttributes().item(i).getNodeName();
		}
		return result;
	}
	// devuelve si un nodo tiene un atributo en especifico
	public static boolean hasAttribute(Node node, String attribute) {
		String[] att = getAttributes(node);
		for (int i = 0; i < att.length; i++) {
			if (att[i].equals(attribute)) {
				return true;
			}
		}
		return false;
	}
	// devuelve el value  de un atributo del nodo
	public static String getAttribute(Node node, String attribute) {
		if (hasAttribute(node, attribute)) {
			return node.getAttributes().getNamedItem(attribute).getNodeValue();
		} else {
			return null;
		}
	}
	// devuelve el XML en bruto (toString())
	public static String getXML(Node rootNode) {
		String print = "";
		if(rootNode.getNodeType()==Node.ELEMENT_NODE) {
			print += "<"+rootNode.getNodeName();
			for (String atribute : getAttributes(rootNode)) {
				print += " " + atribute + "=\"" + getAttribute(rootNode, atribute) + "\"";
			}
			print += ">";
		}
		NodeList nl = rootNode.getChildNodes();
		if(nl.getLength()>0) {
			for (int i = 0; i < nl.getLength(); i++) {
				print += getXML(nl.item(i));
			}
		} else {
			if(rootNode.getNodeValue()!=null) {
				print = rootNode.getNodeValue();
			}
		}
		if(rootNode.getNodeType()==Node.ELEMENT_NODE) {
			print += "</"+rootNode.getNodeName()+">";
		}
		return(print);
	}
	// guarda el archivo XMl en la hubicacion
	public static void saveXML(Document doc, String path) {
		try {
			File file = new File(path);
			file.delete();
			file.createNewFile();
			writeFile(path, getXML(doc.getFirstChild()));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	// escribe el archivo
	private static void writeFile(String path, String text) throws IOException {
		try (FileWriter writer = new FileWriter(new File(path))) {
            writer.write(text);
        }
	}
}
