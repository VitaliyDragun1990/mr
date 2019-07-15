package com.revenat.myresume.infrastructure.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.revenat.myresume.infrastructure.exception.ImageStorageException;
import com.revenat.myresume.infrastructure.service.ImageStorageService;
import com.revenat.myresume.infrastructure.util.CommonUtils;
import com.revenat.myresume.presentation.image.model.ImageType;

@Service
class FileImageStorageService implements ImageStorageService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileImageStorageService.class);

	private final String root;

	@Autowired
	public FileImageStorageService(@Value("${media.storage.root.path}") String root) {
		this.root = normalizeRootPath(root);
	}

	@Override
	public String save(String imageName, ImageType imageType, Path tempImageFile) {
		try {
			String imageLink = getImageLink(imageType.getFolderName(), imageName);
			saveImageFile(tempImageFile, getDestinationImageFile(imageLink));
			return imageLink;
		} catch (IOException e) {
			throw new ImageStorageException("Can't save image: " + e.getMessage(), e);
		}
	}

	@Override
	public void remove(String... imageLinks) {
		if (imageLinks != null) {
			removeImages(imageLinks);
		}
	}

	private void removeImages(String[] imageLinks) {
		for (String imageLink : imageLinks) {
			if (CommonUtils.isNotBlank(imageLink)) {
				removeImageFile(getDestinationImageFile(imageLink));
			}
		}
	}

	private void removeImageFile(Path path) {
		try {
			if (Files.exists(path)) {
				Files.delete(path);
				LOGGER.debug("Image file {} removed successfully", path);
			}
		} catch (IOException e) {
			LOGGER.error("Can't remove file: " + path, e);
		}
	}

	private void saveImageFile(Path srcImageFile, Path destinationImageFile) throws IOException {
		Files.move(srcImageFile, destinationImageFile);
	}

	private Path getDestinationImageFile(String imageLink) {
		return Paths.get(root + imageLink);
	}

	private String getImageLink(String folderName, String imageName) {
		return "/media/" + folderName + "/" + imageName;
	}

	private String normalizeRootPath(String path) {
		String normalizedPath = path.replace("\\", "/");
		if (normalizedPath.endsWith("/")) {
			normalizedPath = normalizedPath.substring(0, normalizedPath.length() - 1);
		}
		return normalizedPath;
	}

}
