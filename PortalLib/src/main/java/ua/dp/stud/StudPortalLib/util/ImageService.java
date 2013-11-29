package ua.dp.stud.StudPortalLib.util;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ua.dp.stud.StudPortalLib.model.BaseImagesSupport;
import ua.dp.stud.StudPortalLib.model.ImageImpl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.StringUtils.join;

/**
 * Created with IntelliJ IDEA.
 * 
 * @author Vladislav Pikus
 * @author: Josby
 */
@Component("imageService")
public class ImageService {

	private static final String FOLDER_SEPARATOR;
	private static final String TOMCAT_FOLDER;
	private static final int TO_CALENDAR_WIDTH = 56;
	private static final int TO_CALENDAR_HEIGHT = 56;
	private static final int IMAGE_MAX_WIDTH = 443;
	private static final int IMAGE_MAX_HEIGHT = 253;
	private static final int SMALL_IMAGE_HEIGHT = 68;
	private static final String MICROBLOG_IMAGE_PREFIX;
	private static final int MICROBLOG_IMAGE_HEIGHT = 100;
	private static final int MICROBLOG_IMAGE_WIDTH = 100;
	private static final String CALENDAR_IMAGE_PREFIX;
	private static final String SMALL_IMAGE_PREFIX;
	private static final String IMAGES_FOLDER;
	private static final String IMAGES_FOLDER_ABS_PATH;
	static final String SLASH = "/";
	static final String IMAGE_URL_PREFIX = "/mediastuff/";
	static final String SPLIT_BY;

	static {
		CALENDAR_IMAGE_PREFIX = "toCalendar_";
		SMALL_IMAGE_PREFIX = "small_";
		MICROBLOG_IMAGE_PREFIX = "microblog_";
		IMAGES_FOLDER = "PROJECT_DATA";
		FOLDER_SEPARATOR = File.separator;
		TOMCAT_FOLDER = System.getProperty("catalina.base");

		IMAGES_FOLDER_ABS_PATH = TOMCAT_FOLDER + FOLDER_SEPARATOR
				+ IMAGES_FOLDER + FOLDER_SEPARATOR;

		if (File.separator.equals("\\")) {
			SPLIT_BY = "\\\\";
		} else {
			SPLIT_BY = "/";
		}
	}

	/**
	 * Method for saving MAIN IMAGE
	 * 
	 * @param file
	 * @param base
	 * @throws java.io.IOException
	 */
	public void saveMainImage(CommonsMultipartFile file, BaseImagesSupport base)
			throws IOException {
		String pathToImagesFolder = checkPathToImagesFolder(base);
		Image sourceImage = ImageIO.read(file.getInputStream());
		if (file.getSize() != 0) {
			saveToDiskScaled(sourceImage, pathToImagesFolder, "",
					IMAGE_MAX_WIDTH, -1,file.getOriginalFilename());
			saveToDiskScaled(sourceImage, pathToImagesFolder,
					CALENDAR_IMAGE_PREFIX, TO_CALENDAR_WIDTH,
					TO_CALENDAR_HEIGHT,file.getOriginalFilename());
			saveToDiskScaled(sourceImage, pathToImagesFolder,
					MICROBLOG_IMAGE_PREFIX, MICROBLOG_IMAGE_WIDTH,
					MICROBLOG_IMAGE_HEIGHT,file.getOriginalFilename());

			ImageImpl image = new ImageImpl();
			// image.setBase(base);
			image.setOriginalImageName(file.getOriginalFilename());
			base.setMainImage(image);
		}

	}

	public void saveMemberImage(File file, BaseImagesSupport base)
			throws IOException {
		String pathToImagesFolder = checkPathToImagesFolder(base);
		String filename = file.getName();
		Image image = ImageIO.read(file);
			saveToDiskScaled(image, pathToImagesFolder, "",
					IMAGE_MAX_WIDTH, -1,filename);
			saveToDiskScaled(image, pathToImagesFolder,
					CALENDAR_IMAGE_PREFIX, TO_CALENDAR_WIDTH,
					TO_CALENDAR_HEIGHT,filename);
			saveToDiskScaled(image, pathToImagesFolder,
					MICROBLOG_IMAGE_PREFIX, MICROBLOG_IMAGE_WIDTH,
					MICROBLOG_IMAGE_HEIGHT,filename);

			ImageImpl images = new ImageImpl();
			// image.setBase(base);
			images.setOriginalImageName(filename);
			base.setMainImage(images);

	}

	/**
	 * method for saving ADDITIONAL IMAGES
	 * 
	 * @param file
	 *            CommonsMultipartFile image
	 * @param base
	 * @throws IOException
	 */
	public void saveAdditionalImages(CommonsMultipartFile file,
			BaseImagesSupport base) throws IOException {
		String pathToImagesFolder = checkPathToImagesFolder(base);
		Image sourceImage = ImageIO.read(file.getInputStream());
		if (file.getSize() != 0) {
			saveToDiskScaled(sourceImage, pathToImagesFolder, "",
					IMAGE_MAX_WIDTH, -1,file.getOriginalFilename());
			saveToDiskScaled(sourceImage, pathToImagesFolder,
					SMALL_IMAGE_PREFIX, -1, SMALL_IMAGE_HEIGHT,file.getOriginalFilename());

			ImageImpl image = new ImageImpl();
			image.setBase(base);
			image.setOriginalImageName(file.getOriginalFilename());
			if (base.getAdditionalImages() == null) {
				base.setAdditionalImages(new ArrayList<ImageImpl>());
			}
			base.getAdditionalImages().add(image);
		}
	}

	/**
	 * method for saving ADDITIONAL IMAGES
	 * 
	 * @param files
	 *            - list of CommonsMultipartFile images
	 * @param base
	 * @throws java.io.IOException
	 */
	public void saveAdditionalImages(List<CommonsMultipartFile> files,
			BaseImagesSupport base) throws IOException {
		String pathToImagesFolder = checkPathToImagesFolder(base);

		for (CommonsMultipartFile file : files) {
			Image sourceImage = ImageIO.read(file.getInputStream());
			if (file.getSize() != 0) {
				saveToDiskScaled(sourceImage, pathToImagesFolder, "",
						IMAGE_MAX_WIDTH, -1,file.getOriginalFilename());
				saveToDiskScaled(sourceImage, pathToImagesFolder,
						SMALL_IMAGE_PREFIX, -1, SMALL_IMAGE_HEIGHT,file.getOriginalFilename());

				ImageImpl image = new ImageImpl();
				image.setBase(base);
				image.setOriginalImageName(file.getOriginalFilename());
				if (base.getAdditionalImages() == null) {
					base.setAdditionalImages(new ArrayList<ImageImpl>());
				}
				base.getAdditionalImages().add(image);
			}

		}
	}

	/**
	 * generates absolute path to images folder like: /home/tomcat/PROJECT_DATA/
	 * 
	 * @return abs path to images
	 */
	public String getImagesFolderAbsolutePath() {
		return IMAGES_FOLDER_ABS_PATH;
	}

	/**
	 * method for fixing WINDOWS like paths to UNIX paths needed to create url
	 * for image gonna convert 2012\11\12 => 2012/11/12
	 * 
	 * @param yearMonthUniqueFolder
	 * @return
	 */
	private String correctFolderSeparator(String yearMonthUniqueFolder) {
		if (yearMonthUniqueFolder == null) {
			// to prevent null pointer exception
			// for images, where news wasn't set
			return null;
		}
		String[] splittedByFolderSeparator = yearMonthUniqueFolder
				.split(SPLIT_BY);
		return join(splittedByFolderSeparator, SLASH);
	}

	/**
	 * method returns scaled original image( acts like a main image)
	 * /mediastuff/2012/12/31/asdasdas.jpg
	 * 
	 * @return
	 */
	public String getPathToLargeImage(ImageImpl img, BaseImagesSupport base) {
		StringBuilder sb = new StringBuilder();
		String joinedYearMonthUniqueFolder = correctFolderSeparator(base
				.getYearMonthUniqueFolder());
		if (joinedYearMonthUniqueFolder == null) {
			return null;
		}
		return sb.append(IMAGE_URL_PREFIX).append(joinedYearMonthUniqueFolder)
				.append(SLASH).append(img.getOriginalImageName()).toString();
	}

	/**
	 * method returns url of small image
	 * /mediastuff/2012/12/31/microblog_asdasdas.jpg
	 * 
	 * @return
	 */
	public String getPathToSmallImage(ImageImpl img, BaseImagesSupport base) {
		StringBuilder sb = new StringBuilder();
		String joinedYearMonthUniqueFolder = correctFolderSeparator(base
				.getYearMonthUniqueFolder());
		if (joinedYearMonthUniqueFolder == null) {
			return null;
		}
		return sb.append(IMAGE_URL_PREFIX).append(joinedYearMonthUniqueFolder)
				.append(SLASH).append(SMALL_IMAGE_PREFIX)
				.append(img.getOriginalImageName()).toString();
	}

	/**
	 * method returns url of microblog image
	 * /mediastuff/2012/12/31/small_asdasdas.jpg
	 * 
	 * @return
	 */
	public String getPathToMicroblogImage(ImageImpl img, BaseImagesSupport base) {
		StringBuilder sb = new StringBuilder();
		String joinedYearMonthUniqueFolder = correctFolderSeparator(base
				.getYearMonthUniqueFolder());
		if (joinedYearMonthUniqueFolder == null) {
			return null;
		}
		return sb.append(IMAGE_URL_PREFIX).append(joinedYearMonthUniqueFolder)
				.append(SLASH).append(MICROBLOG_IMAGE_PREFIX)
				.append(img.getOriginalImageName()).toString();
	}

	/**
	 * method returns url of image for calendar
	 * /mediastuff/2012/12/31/toCalendar_asdasdas.jpg
	 * 
	 * @return
	 */
	public String getPathToCalendarImage(ImageImpl img, BaseImagesSupport base) {
		StringBuilder sb = new StringBuilder();
		String joinedYearMonthUniqueFolder = correctFolderSeparator(base
				.getYearMonthUniqueFolder());
		if (joinedYearMonthUniqueFolder == null) {
			return null;
		}
		return sb.append(IMAGE_URL_PREFIX).append(joinedYearMonthUniqueFolder)
				.append(SLASH).append(CALENDAR_IMAGE_PREFIX)
				.append(img.getOriginalImageName()).toString();
	}

	private String checkPathToImagesFolder(BaseImagesSupport base) {
		String path;
		if (base.getYearMonthUniqueFolder() == null) {
			DateTime dt = new DateTime();
			StringBuilder sb = new StringBuilder();
			String uniqueFolderId = String.valueOf(System.currentTimeMillis());

			String yearMonthUniqueFolder = sb
					.append(dt.yearOfEra().getAsString())
					.append(FOLDER_SEPARATOR)
					.append(dt.monthOfYear().getAsString())
					.append(FOLDER_SEPARATOR).append(uniqueFolderId).toString();
			base.setYearMonthUniqueFolder(yearMonthUniqueFolder);

			sb = new StringBuilder();
			sb.append(getImagesFolderAbsolutePath()).append(
					yearMonthUniqueFolder);
			path = sb.toString();
			File folderForImages = new File(path);
			folderForImages.mkdirs();

			return path;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(getImagesFolderAbsolutePath()).append(
				base.getYearMonthUniqueFolder());

		return sb.toString();
	}

	private void saveToDiskScaled(Image image, String pathToImagesFolder,
			String outputFilePrefix, Integer width, Integer height, String filename)
			throws IOException {

		StringBuilder sb;
		Image sourceImage = image;
		Image thumbnail = sourceImage.getScaledInstance(width, height,
				Image.SCALE_SMOOTH);
		BufferedImage bufferedThumbnail = new BufferedImage(
				thumbnail.getWidth(null), thumbnail.getHeight(null),
				BufferedImage.TYPE_INT_RGB);
		bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
		sb = new StringBuilder();
		sb.append(pathToImagesFolder).append(FOLDER_SEPARATOR)
				.append(outputFilePrefix).append(filename);
		String fileLocation = sb.toString();
		ImageIO.write(bufferedThumbnail, "jpeg", new File(fileLocation));
	}

	/**
	 * Class need to resize images
	 */
	public BufferedImage resize(BufferedImage imageToResize, int width,
			int height) {
		float dx = ((float) width) / imageToResize.getWidth();
		float dy = ((float) height) / imageToResize.getHeight();

		int genX, genY;
		int startX, startY;

		if (imageToResize.getWidth() <= width
				&& imageToResize.getHeight() <= height) {
			genX = imageToResize.getWidth();
			genY = imageToResize.getHeight();
		} else {
			if (dx <= dy) {
				genX = width;
				genY = (int) (dx * imageToResize.getHeight());
			} else {
				genX = (int) (dy * imageToResize.getWidth());
				genY = height;
			}
		}

		startX = (width - genX) / 2;
		startY = (height - genY) / 2;

		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = null;

		try {
			graphics2D = bufferedImage.createGraphics();
			graphics2D.fillRect(0, 0, width, height);
			graphics2D.drawImage(imageToResize, startX, startY, genX, genY,
					null);
		} finally {
			if (graphics2D != null) {
				graphics2D.dispose();
			}
		}

		return bufferedImage;
	}

	public void deleteImage(ImageImpl img, BaseImagesSupport base) {
		StringBuilder imagePath = new StringBuilder();
		imagePath.append(this.getImagesFolderAbsolutePath())
				.append(base.getYearMonthUniqueFolder())
				.append(FOLDER_SEPARATOR);
		if (base.getMainImage().getOriginalImageName()
				.equals(img.getOriginalImageName())) {
			this.deleteImage(imagePath.toString() + CALENDAR_IMAGE_PREFIX
					+ img.getOriginalImageName());
			this.deleteImage(imagePath.toString() + MICROBLOG_IMAGE_PREFIX
					+ img.getOriginalImageName());
			this.deleteImage(imagePath.toString() + img.getOriginalImageName());
		} else {
			this.deleteImage(imagePath.toString() + SMALL_IMAGE_PREFIX
					+ img.getOriginalImageName());
			this.deleteImage(imagePath.toString() + img.getOriginalImageName());
		}
		File dir = new File(imagePath.toString());
		if (dir.list().length == 0) {
			dir.delete();
		}
	}

	public void deleteDirectory(BaseImagesSupport base) {
		StringBuilder path = new StringBuilder();
		path.append(this.getImagesFolderAbsolutePath())
				.append(base.getYearMonthUniqueFolder())
				.append(FOLDER_SEPARATOR);
		this.deleteDirectory(new File(path.toString()));
	}

	/**
	 * Deletes directory with subdirs and subfolders
	 * 
	 * @param dir
	 *            Directory to delete
	 */
	private void deleteDirectory(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				File f = new File(dir, children[i]);
				this.deleteDirectory(f);
			}
			dir.delete();
		}
	}

	private void deleteImage(String path) {
		try {
			File file = new File(path);
			file.delete();
		} catch (Exception e) {
		}
	}

	public CommonsMultipartFile cropImage(CommonsMultipartFile image,
			Integer t, Integer l, Integer w, Integer h) {
		CommonsMultipartFile croppedImage;
		try {
			BufferedImage sourceImage = ImageIO.read(image.getInputStream());
			// Compressing an image to produce the necessary proportions
			sourceImage = this.resize(sourceImage, IMAGE_MAX_WIDTH,
					IMAGE_MAX_HEIGHT);
			// cut the selected user area
			sourceImage = sourceImage.getSubimage(t, l, w, h);
			File tempFile = new File(this.getImagesFolderAbsolutePath()
					+ image.getOriginalFilename());
			ImageIO.write(sourceImage, "jpg", tempFile);
			DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory()
					.createItem("fileData", "image/jpeg", true,
							tempFile.getName());
			InputStream input = new FileInputStream(tempFile);
			OutputStream os = fileItem.getOutputStream();
			int ret = input.read();
			while (ret != -1) {
				os.write(ret);
				ret = input.read();
			}
			os.flush();
			croppedImage = new CommonsMultipartFile(fileItem);
		} catch (IOException e) {
			return null;
		}

		return croppedImage;
	}

	public CommonsMultipartFile getDefaultImage(String portletPath) {
		CommonsMultipartFile Image = null;
		try {
			File tempFile = new File(portletPath + File.separator + "images"
					+ File.separator + "defaultImage.png");
			DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory()
					.createItem("fileData", "image/jpeg", true,
							tempFile.getName());
			InputStream input = new FileInputStream(tempFile);
			OutputStream os = fileItem.getOutputStream();
			int ret = input.read();
			while (ret != -1) {
				os.write(ret);
				ret = input.read();
			}
			os.flush();
			Image = new CommonsMultipartFile(fileItem);
		} catch (IOException ex) {
		}
		return Image;
	}
}
