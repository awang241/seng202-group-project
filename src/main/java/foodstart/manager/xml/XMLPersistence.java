package foodstart.manager.xml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import foodstart.manager.Persistence;
import foodstart.manager.exceptions.ImportFailureException;
import foodstart.model.DataType;

/**
 * Parses an XML file with a given DataType
 * 
 * @author Alex Hobson
 * @date 22/08/2019
 */
public class XMLPersistence extends Persistence {

	/**
	 * Map of datatypes to XML parsers
	 */
	Map<DataType, XMLParser> parsers;
	
	public XMLPersistence() {
		parsers = new HashMap<DataType, XMLParser>();
		parsers.put(DataType.INGREDIENT, new XMLIngredientParser());
		parsers.put(DataType.MENU, new XMLMenuParser());
	}
	
	/**
	 * Imports a XML file of a given data type
	 */
	@Override
	public void importFile(File file, DataType dataType) {

		Document doc = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(file);
		} catch (ParserConfigurationException e) {
			throw new ImportFailureException("Parser had a configuration exception: " + e.getMessage());
		} catch (SAXException e) {
			throw new ImportFailureException("Parser threw a SAX Exception: " + e.getMessage());
		} catch (IOException e) {
			throw new ImportFailureException("Parser threw an IO Exception: " + e.getMessage());
		}

		parsers.get(dataType).parse(doc);
	}

	@Override
	public void exportFile(File file, DataType dataType) {
		// TODO
	}
}