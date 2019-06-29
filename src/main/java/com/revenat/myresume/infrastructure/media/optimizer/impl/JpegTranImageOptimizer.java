package com.revenat.myresume.infrastructure.media.optimizer.impl;

import java.io.IOException;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.revenat.myresume.infrastructure.media.optimizer.ImageOptimizer;

/**
 * Optimizes image using jpegtran tool.
 * 
 * @author Vitaliy Dragun
 *
 */
@Component
class JpegTranImageOptimizer implements ImageOptimizer {
	private static final Logger LOGGER = LoggerFactory.getLogger(JpegTranImageOptimizer.class);
	private static final String JPEGTRAN_OPTIONS = " -copy none -optimize -progressive ";

	private final String jpegtran;

	@Autowired
	public JpegTranImageOptimizer(@Value("${media.optimization.jpegtran}") String jpegtran) {
		this.jpegtran = jpegtran;
	}

	@Override
	public void optimize(Path imagePath) {
		try {
			optimizeImageFile(imagePath);
		} catch (Exception e) {
			LOGGER.error("Can't optimize imageFile: " + e.getMessage(), e);
		}
	}

	private void optimizeImageFile(Path imagePath) throws IOException, InterruptedException {
		Process process = null;
		try {
			String toolCmd = getToolCmd(imagePath);
			process = Runtime.getRuntime().exec(toolCmd);
			int code = process.waitFor();
			LOGGER.debug("Optimize cmd {} completed with code {}", toolCmd, code);
		} finally {
			if (process != null) {
				process.destroy();
			}
		}
	}

	private String getToolCmd(Path imagePath) {
		return jpegtran + JPEGTRAN_OPTIONS + " -outfile " + imagePath + " " + imagePath;
	}

}
