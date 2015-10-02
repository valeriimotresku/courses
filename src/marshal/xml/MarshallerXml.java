package marshal.xml;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class MarshallerXml {
	
	//fields
	private  String pathXml = "marshalled-jaxb.xml";
	
	//constructors
	public MarshallerXml() { }
	
	public MarshallerXml(String pathXml) {
		this.pathXml = pathXml;
	}



	//methods
	
	//marshaller
	public Marshaller getMarshaller(Class<?> classObject) throws JAXBException {
		// create JAXB context and instantiate marshaller
	    JAXBContext context = JAXBContext.newInstance(classObject);
	    Marshaller m = context.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    return m;
	}
	//unmarshaller
	public Unmarshaller getUnmarshaller(Class<?> classObject) throws JAXBException {
		// create JAXB context and instantiate marshaller
	    JAXBContext context = JAXBContext.newInstance(classObject);
	    Unmarshaller um = context.createUnmarshaller();
	    return um;
	}
	
	//write - read
	public <T> void writeToXML(Class<T> classObject, T instance) 
			throws JAXBException, IOException {
		// instantiate marshaller
	    Marshaller m = this.getMarshaller(classObject);		

	    // Write to System.out
	    System.out.println("XML-storage:");
	    m.marshal(instance, System.out);

	    // Write to File
	    m.marshal(instance, new File(this.pathXml));
	}
	public <T> T readFromXML(Class<T> classObject) 
			throws JAXBException, IOException {
		// instantiate unmarshaller
	    Unmarshaller um = this.getUnmarshaller(classObject);
	    return classObject.cast(um.unmarshal(new FileReader(this.pathXml)));
	}
	
	//getters setters
	public String getPathXml() {
		return pathXml;
	}

	public void setPathXml(String destinationXml) {
		this.pathXml = destinationXml;
	}
}
