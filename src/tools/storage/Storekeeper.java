package tools.storage;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import tools.storage.exception.IllegalStorageFormatException;
import marshal.json.MarshallerJson;
import marshal.schoolformat.MarshallerSf;
import marshal.xml.MarshallerXml;

public class Storekeeper {
	
	
	//fields
	
	private static final String JSON = "json";
	private static final String XML = "xml";
	private static final String SF = "school-format";
	
	private String format = JSON;
	//xml marshaller object
	private MarshallerXml marshallerXml = new MarshallerXml("storage-jaxb.xml");
	private MarshallerJson marshallerJson = new MarshallerJson("storage-json-gson");
	private MarshallerSf marshallerSf = new MarshallerSf();
	
	//constructors
	
	public Storekeeper() {
		super();
	}
	public Storekeeper(String format) {
		super();
		this.format = format;
	}

	//methods
	
	public <T> boolean saveState(Class<T> classObject, T instance) {
		switch (this.format) {
			case JSON:
				this.saveStateToJson(instance);
				return true;
			case XML:
				this.saveStateToXML(classObject, instance);
				return true;
			case SF:
				//not ready yet
				throw new UnsupportedOperationException();
			default:
				return false;
		}
	}
	public <T> boolean loadState(Class<T> classObject, T instance) {
		switch (this.format) {
			case JSON:
				this.loadStateFromJson(classObject);
				return true;
			case XML:
				this.loadStateFromXML(classObject);
				return true;
			case SF:
				//not ready yet
				throw new UnsupportedOperationException();
			default:
				return false;
		}
	}
	
	//xml
	public <T> boolean saveStateToXML(Class<T> classObject, T instance) {
		//write to xml
		try {
			marshallerXml.writeToXML(classObject, instance);
		} catch (IOException ioe) {
			System.err.println(ioe);
		} catch (JAXBException jaxbe) {
			System.err.println(jaxbe);
		}
		return true;
	}
	
	public <T> T loadStateFromXML(Class<T> classObject) {
		//read from xml
		T instance = null;
		try {
			instance = marshallerXml.readFromXML(classObject);
			//print to console
			System.out.println();
		    System.out.println("Output from XML File: ");
		    System.out.println(instance);
		} catch (IOException ioe) {
			System.err.println(ioe);
		} catch (JAXBException jaxbe) {
			System.err.println(jaxbe);
		}
		return instance;
	}
	
	//json
	public <T> boolean saveStateToJson(T instance) {
		//write as json
		try {
			marshallerJson.writeToFile(instance);
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
		//Print to console json
		System.out.println("\njson-storage:");
		System.out.println(marshallerJson.serialize(instance));
		
		return true;
	}
	
	public <T> T loadStateFromJson(Class<T> classObject) {
		//read from json
		T instance = null;
		try {
			instance = marshallerJson.readFromFile(classObject);
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
		System.out.println("json restored output:");
		System.out.println(instance);
		return instance;
	}
	

	//getters setters
	public void setMarshallerXmlPath(String path) {
		this.marshallerXml = new MarshallerXml(path);
	}

	public void setMarshallerJsonPath(String path) {
		this.marshallerJson = new MarshallerJson(path);
	}
	public String getFormat() {
		return format;
	}
	private boolean validatePassedFormat(String format) 
			throws IllegalStorageFormatException {
		
		format = format.trim().toLowerCase();
		switch (format) {
			case "json" :
				return true;
			case "school-format":
				return true;
			case "xml" :
				return true;
			default:
				throw new IllegalStorageFormatException(
						"Illegal format. Nothing compatible was found");
		}	
	}
	public void setFormat(String format) throws IllegalStorageFormatException {
		format = format.trim().toLowerCase();
		this.validatePassedFormat(format);
		this.format = format;
	}
}
