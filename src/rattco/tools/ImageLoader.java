
package rattco.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {

	public static BufferedImage loadBufferedImage(String nameFile) {
		BufferedImage img = null;

		try {
			img = ImageIO.read(new File(nameFile));
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return img;
	}

	public static BufferedImage[] loadImagesFromFolder(String folderName) {
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		BufferedImage[] listOfImages = new BufferedImage[listOfFiles.length];

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				try {
					listOfImages[i] = ImageIO.read(listOfFiles[i]);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return listOfImages;
	}

}
