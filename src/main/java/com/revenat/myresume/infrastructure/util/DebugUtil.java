package com.revenat.myresume.infrastructure.util;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class DebugUtil {
	
	public static void turnOnShowMongoQuery() {
		Logger mongoLogger = (Logger) LoggerFactory.getLogger("org.springframework.data.mongodb.core.MongoTemplate");
		mongoLogger.setLevel(Level.DEBUG);
	}
	
	public static void turnOffShowMongoQuery() {
		Logger mongoLogger = (Logger) LoggerFactory.getLogger("org.springframework.data.mongodb.core.MongoTemplate");
		mongoLogger.setLevel(Level.INFO);
	}
	
	public static void turnOnShowSQL() {
		Logger sqlLogger = (Logger) LoggerFactory.getLogger("org.hibernate.SQL");
		sqlLogger.setLevel(Level.DEBUG);
		Logger descLogger = (Logger) LoggerFactory.getLogger("org.hibernate.type.descriptor.sql.BasicBinder");
		descLogger.setLevel(Level.TRACE);
	}
	
	public static void turnOffShowSQL() {
		Logger sqlLogger = (Logger) LoggerFactory.getLogger("org.hibernate.SQL");
		sqlLogger.setLevel(Level.INFO);
		Logger descLogger = (Logger) LoggerFactory.getLogger("org.hibernate.type.descriptor.sql.BasicBinder");
		descLogger.setLevel(Level.INFO);
	}

	private DebugUtil() {}
}
