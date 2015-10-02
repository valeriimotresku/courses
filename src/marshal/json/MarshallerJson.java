package marshal.json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MarshallerJson {
	//fields
	private  String pathJson = "json-gson";
	
	//constructors
	public MarshallerJson() { }
	
	public MarshallerJson(String pathJson) {
		this.pathJson = pathJson;
	}
	
	//methods
	//read-write
	public <T> String serialize(T instance) {
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		return gson.toJson(instance);
		
	}
	public <T> void writeToFile(T instance) throws IOException {
		String json = this.serialize(instance);
		try (BufferedWriter out = 
				new BufferedWriter(new FileWriter(this.pathJson))) {
			out.write(json);
		}
	}
	
	public <T> T deserialize(Class<T> classObject, String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, classObject);
	}
	public <T> T readFromFile(Class<T> classObject) throws IOException {
		String json;
		try (Scanner scanner = new Scanner(new File(this.pathJson))) {
			json = scanner.useDelimiter("\\Z").next();
		}
		return this.deserialize(classObject, json);
	}
	
	
	//setters getters
	public String getPathJson() {
		return pathJson;
	}

	public void setPathJson(String pathJson) {
		this.pathJson = pathJson;
	}
	
	
}
