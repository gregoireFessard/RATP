package mesclasses;

import java.io.InputStream;

public class FileHandle {

public static InputStream inputStreamFromFile(String path) {
	InputStream inputStream = null;
	try {
		inputStream = FileHandle.class.getResourceAsStream(path);
	} catch(Exception e) {
		e.printStackTrace();
	}
	return inputStream;
}

}