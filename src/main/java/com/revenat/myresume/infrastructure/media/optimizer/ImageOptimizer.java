package com.revenat.myresume.infrastructure.media.optimizer;

import java.nio.file.Path;

/**
 * Applies some kind of optimization on specified image.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface ImageOptimizer {

	void optimize(Path imagePath);
}
