package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Utils {

//	public static final Path WORKING_DIR = Paths.get("").toAbsolutePath();
	public static final Path WORKING_DIR = Paths.get("c:\\Users\\Celo\\Desktop\\");
	public static final Path IMG_DIR = WORKING_DIR.resolve("_");

	public static void createImagesFolder() {
		try {
			Files.createDirectories(IMG_DIR);
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

}