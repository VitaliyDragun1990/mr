package com.revenat.myresume.infrastructure.repository.media.impl;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.revenat.myresume.infrastructure.exception.PersistenceException;
import com.revenat.myresume.infrastructure.gateway.filesystem.FileSystemGateway;
import com.revenat.myresume.infrastructure.repository.media.ImagePair;
import com.revenat.myresume.infrastructure.repository.media.ImageRepository;

import net.coobird.thumbnailator.Thumbnails;

@Repository
class FileSystemPhotoRepository implements ImageRepository {
	
	private final int photoSmallSize;
	private final int certificateSmallSize;
	private final String photoDir;
	private final String certificateDir;
	
	private final FileSystemGateway fileSystemGateway;

	@Autowired
	public FileSystemPhotoRepository(
			@Value("${photo.size.sm}") int photoSmallSize,
			@Value("${certificate.size.sm}") int certificateSmallSize,
			@Value("${app.photo.dir}") String photoDir,
			@Value("${app.certificate.dir}") String certificateDir,
			FileSystemGateway fileSystemGateway) {
		this.photoSmallSize = photoSmallSize;
		this.certificateSmallSize = certificateSmallSize;
		this.photoDir = photoDir;
		this.certificateDir = certificateDir;
		this.fileSystemGateway = fileSystemGateway;
	}

	@Override
	public ImagePair savePhoto(InputStream in) {
		return saveImage(in, photoDir, photoSmallSize);
	}
	
	@Override
	public ImagePair saveCertificate(InputStream in) {
		return saveImage(in, certificateDir, certificateSmallSize);
	}
	
	private ImagePair saveImage(InputStream in, String imageDirectory, int smallImageSize) {
		if (in != null) {
			try {
				String uid = UUID.randomUUID().toString() + ".jpg";
				String imagePath = imageDirectory + uid;
				String largeImagePath = fileSystemGateway.saveFile(in, imagePath);
				
				String smallImagePath= largeImagePath.replace(".jpg", "-sm.jpg");
				Thumbnails.of(new File(largeImagePath)).size(smallImageSize, smallImageSize).toFile(new File(smallImagePath));
				
				return new ImagePair(largeImagePath, smallImagePath);
			} catch (Exception e) {
				throw new PersistenceException(e);
			}
		}
		return null;
	}

	@Override
	public boolean deleteIfExists(String photPath) {
		return fileSystemGateway.deleteFileIfExists(photPath);
	}

}
