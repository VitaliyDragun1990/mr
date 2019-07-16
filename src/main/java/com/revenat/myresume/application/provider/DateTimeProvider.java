package com.revenat.myresume.application.provider;

import java.time.LocalDateTime;

import javax.annotation.Nonnull;

public interface DateTimeProvider {

	@Nonnull LocalDateTime getCurrentDateTime();
}
