package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

public class Image {

	private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh.mm.ss");
	
	public static void renameToOriginalDate(Path path) {
		if (!isImage(path.toString())) {
			return;
		}
		createCopy(path);
	}

	private static void createCopy(Path path) {
		String name = getImageFutureName(path);
		if (name != null) {
			try {
				Files.copy(path, Utils.IMG_DIR.resolve(name));
			} catch (IOException e) {
				Utils.printError(e, "Problem while copying file <" + path.toAbsolutePath() + ">");
			}
		}
	}
	
	private static String getImageFutureName(Path path) {
		String fullname = null;
		Date d = getOriginalDate(path);
		if (d != null) {
			LocalDateTime timestamp = LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault());
			String name = timestamp.format(FORMATTER);
			String extension = Utils.getExtension(path.toString());
			fullname = name + "." + extension;
		}
		return fullname;
	}

	private static Date getOriginalDate(Path path) {
		File file = path.toFile();
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(file);
			ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
			return directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
		} catch (Exception e) {
			Utils.printError(e, "File <" + path.toAbsolutePath() + "> does not have EXIF metadata!");
			//TODO: add image to special file ...
		}
		return null;
	}

	public static boolean isImage(String path) {
		String extension = Utils.getExtension(path);
		return extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg");

	}
}
