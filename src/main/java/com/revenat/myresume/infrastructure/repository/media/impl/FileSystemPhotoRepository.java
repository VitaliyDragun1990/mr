package com.revenat.myresume.infrastructure.repository.media.impl;

import static com.revenat.myresume.infrastructure.config.Constants.PATH_SEPARATOR;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revenat.myresume.infrastructure.exception.PersistenceException;
import com.revenat.myresume.infrastructure.gateway.filesystem.FileSystemGateway;
import com.revenat.myresume.infrastructure.media.resizer.ImageResizer;
import com.revenat.myresume.infrastructure.repository.media.ImageLinkPair;
import com.revenat.myresume.infrastructure.repository.media.ImageRepository;
import com.revenat.myresume.infrastructure.repository.media.ImageType;

@Repository
class FileSystemPhotoRepository implements ImageRepository {
	private static final String TEMP_IMAGE_NAME_SUFFIX = "-tmp.jpg";
	private final FileSystemGateway fileSystemGateway;
	private final ImageResizer imageResizer;

	@Autowired
	public FileSystemPhotoRepository(FileSystemGateway fileSystemGateway, ImageResizer imageResize) {
		this.fileSystemGateway = fileSystemGateway;
		this.imageResizer = imageResize;
	}
	
	@Override
	public ImageLinkPair saveImage(InputStream in, ImageType imageType) {
		if (in != null) {
			try {
				String tmpFullImagePath = saveTempImage(in, imageType);
				
				String largeFullImagePath = saveLargeImage(tmpFullImagePath, imageType);
				
				String smallFullImagePath = saveSmallImage(tmpFullImagePath, imageType);
				
				deleteTempImage(tmpFullImagePath);
				
				return new ImageLinkPair(largeFullImagePath, smallFullImagePath);
			} catch (Exception e) {
				throw new PersistenceException(e);
			}
		}
		return null;
	}

	private boolean deleteTempImage(String tmpFullImagePath) {
		return fileSystemGateway.deleteFileIfExists(tmpFullImagePath);
	}
	
	private String saveTempImage(InputStream in, ImageType imageType) {
		String tmpFileName = UUID.randomUUID().toString() + TEMP_IMAGE_NAME_SUFFIX;
		String tmpImagePath = imageType.getFolderName() + PATH_SEPARATOR + tmpFileName;
		return fileSystemGateway.saveFile(in, tmpImagePath);
	}
	
	private String saveLargeImage(String tempImagePath, ImageType imageType) throws IOException {
		String largeFullImagePath = tempImagePath.replace(TEMP_IMAGE_NAME_SUFFIX, ".jpg");
		imageResizer.resizeImage(tempImagePath, largeFullImagePath,
				imageType.getLargeWidth(), imageType.getLargeHeight());
		return largeFullImagePath;
	}
	
	private String saveSmallImage(String tempImagePath, ImageType imageType) throws IOException {
		String smallFullImagePath = tempImagePath.replace(TEMP_IMAGE_NAME_SUFFIX, "-sm.jpg");
		imageResizer.resizeImage(tempImagePath, smallFullImagePath,
				imageType.getSmallWidth(), imageType.getSmallHeight());
		return smallFullImagePath;
	}

	@Override
	public boolean deleteIfExists(String photPath) {
		return deleteTempImage(photPath);
	}

}
