package com.revenat.myresume.infrastructure.media.optimizer;

import java.nio.file.Path;

import javax.annotation.Nonnull;

/**
 * Applies some kind of optimization to specified image.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface ImageOptimizer {

	/**
	 * Optimazes image located with provided {@code imagePath}
	 * 
	 * @param imagePath
	 */
	void optimize(@Nonnull Path imagePath);
}
