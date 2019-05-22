package mesclasses;

import org.json.*;
import java.io.*;
import java.util.Scanner;

public class collection {

	public static String getJSONStringFromFile(String path) throws IOException{
		Scanner scanner;
		InputStream in = FileHandle.inputStreamFromFile(path);
		scanner = new Scanner(in);
		String json = scanner.useDelimiter("\\Z").next();
		scanner.close();
		return json;
	}
	
	public static JSONObject getJSONObjectFromFile(String path) throws JSONException,IOException {
		return new JSONObject(getJSONStringFromFile(path));
	}
	
	public static boolean objectExists(JSONObject jsonObject, String key) {
		Object o;
		try {
			o = jsonObject.get(key);
		} catch(Exception e) {
			return false;
		}
		return o != null;
	}
	
}