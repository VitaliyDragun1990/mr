package com.revenat.myresume.infrastructure.gateway.filesystem.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.revenat.myresume.infrastructure.exception.FileSystemGatewayException;
import com.revenat.myresume.infrastructure.gateway.filesystem.FileSystemGateway;

@Component
class FileSystemGatewayImpl implements FileSystemGateway {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemGatewayImpl.class);
	
	private final String mediaDirParent;
	
	@Autowired
	public FileSystemGatewayImpl(@Value("${app.root.dir}") String mediaDirParent) {
		this.mediaDirParent = normalizeDirPath(mediaDirParent);
	}

	@Override
	public String saveFile(InputStream in, String fileName) {
		String normalizePath = mediaDirParent + normalizeFilePath(fileName);
		File file = new File(normalizePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdir();
		}
		InputStream input = in;
		try {
			Files.copy(input, Paths.get(normalizePath), StandardCopyOption.REPLACE_EXISTING);
			LOGGER.debug("File {} has been saved", file);
			return normalizePath;
		} catch (IOException e) {
			throw new FileSystemGatewayException("Error during saving file: " + e.getMessage(), e);
		}
	}

	@Override
	public boolean deleteFileIfExists(String fileName) {
		if (fileName != null) {
			File file = new File(mediaDirParent + normalizeFilePath(fileName));
			if (file.exists()) {
				if (file.delete()) {
					LOGGER.debug("File {} has been deleted.", file);
					return true;
				} else {
					LOGGER.error("Can not delete file: {}", file.getAbsolutePath());
				}
			}
		}
		return false;
	}
	
	private String normalizeDirPath(String path) {
		return path.replace("\\", "/");
	}
	
	private String normalizeFilePath(String path) {
		String normalizedPath = path.replace("\\", "/");
		if (normalizedPath.startsWith("/")) {
			normalizedPath = normalizedPath.substring(1, normalizedPath.length());
		}
		if (normalizedPath.endsWith("/")) {
			normalizedPath = normalizedPath.substring(0, normalizedPath.length() - 1);
		}
		return normalizedPath;
	}

}
