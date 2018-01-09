package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Utils {

	 public static final Path WORKING_DIR = Paths.get("").toAbsolutePath();
//	public static final Path WORKING_DIR = Paths.get("c:\\Users\\Celo\\Desktop\\");
	public static final Path IMG_DIR = WORKING_DIR.resolve("_");
	public static final Path EXCEPTIONS_FILE = WORKING_DIR.resolve("_exceptions.txt");
	private static Map<String, String> SPECIAL_IMAGES = new HashMap<>();

	public static void createImagesFolder() {
		try {
			Files.createDirectories(IMG_DIR);
			Files.createFile(EXCEPTIONS_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createCSVFile() {
		try (FileWriter writer = new FileWriter(EXCEPTIONS_FILE.toFile())) {
			writer.write("File,Exception");
			writer.write(System.lineSeparator());
			for (Map.Entry<String, String> entry : SPECIAL_IMAGES.entrySet()) {
				writer.write(entry.getKey() + "," + entry.getValue());
				writer.write(System.lineSeparator());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void renameAllImages() {
		try (Stream<Path> paths = Files.walk(WORKING_DIR, 1)) {
			paths.filter(Files::isRegularFile).forEach(Image::renameToOriginalDate);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static String getExtension(String filename) {
		return filename.substring(filename.lastIndexOf('.') + 1);
	}

	public static void printError(Exception e, String string) {
		System.out.println(string);
		System.out.println(e.getClass());
	}

	public static void addImageToSpecials(String image, Exception e) {
		SPECIAL_IMAGES.put(image, e.toString());
	}

	public static Map<String, String> getSpecialImages() {
		return SPECIAL_IMAGES;
	}

}