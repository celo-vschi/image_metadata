package image_metadata;

import utils.Utils;

public class Application {

	public static void main(String[] args) {
		Utils.createImagesFolder();
		Utils.renameAllImages();
		Utils.createCSVFile();
	}
}