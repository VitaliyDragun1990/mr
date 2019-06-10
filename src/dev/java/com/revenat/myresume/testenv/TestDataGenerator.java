package com.revenat.myresume.testenv;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.revenat.myresume.domain.entity.LanguageLevel;
import com.revenat.myresume.domain.entity.LanguageType;
import com.revenat.myresume.domain.entity.SkillCategory;

import net.coobird.thumbnailator.Thumbnails;

/**
 * Add postgresql JDBC driver to classpath before launch this generqator
 * 
 * @author Vitaliy Dragun
 *
 */
public class TestDataGenerator {

	// JDBC settings for database
	private static final String JDBC_URL = "jdbc:postgresql://192.168.99.100:5433/";
	private static final String JDBC_USERNAME = "myresume";
	private static final String JDBC_PASSWORD = "myresume";
	
	private static final String PHOTO_PATH = "external/test-data/photos/";
	private static final String CERTIFICATE_PATH = "external/test-data/certificates/";
	private static final String MEDIA_DIR = "E:/java/eclipse_workspace_02/my-resume/src/main/webapp/media";
	private static final String COUNTRY = "Ukraine";
	private static final String[] CITIES = {"Kharkiv", "Kiyv", "Odessa"};
	private static final String[] FOREIGN_LANGUAGES = {"Spanish", "French", "German", "Italian"};
	// password
	private static final String PASSWORD_HASH = "$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq";
	
	private static final String[] HOBBIES = { "Cycling", "Handball", "Football", "Basketball", "Bowling", "Boxing", "Volleyball", "Baseball", "Skating", "Skiing", "Table tennis", "Tennis",
			"Weightlifting", "Automobiles", "Book reading", "Cricket", "Photo", "Shopping", "Cooking", "Codding", "Animals", "Traveling", "Movie", "Painting", "Darts", "Fishing", "Kayak slalom",
			"Games of chance", "Ice hockey", "Roller skating", "Swimming", "Diving", "Golf", "Shooting", "Rowing", "Camping", "Archery", "Pubs", "Music", "Computer games", "Authorship", "Singing",
			"Foreign lang", "Billiards", "Skateboarding", "Collecting", "Badminton", "Disco" };
	
	private static final Map<Short, LanguageType> LANGUAGE_TYPES = getLanguageTypes();
	private static final Map<Short,LanguageLevel> LANGUAGE_LEVELS = getLanguageLevels();
	private static final Map<Short, SkillCategory> SKILL_CATEGORIES = getSkillCategories();
	
	private static final String DUMMY_CONTENT_PATH = "external/test-data/dummy-content.txt";
	private static final String DUMMY_CONTENT = readDummyText();
	private static final List<String> SENTENCES;
	
	static {
		String[] sentences = DUMMY_CONTENT.split("\\.");
		List<String> sentencesList = new ArrayList<>(sentences.length);
		for (String sentence : sentences) {
			sentence = sentence.trim();
			if (sentence.length() > 0) {
				sentencesList.add(sentence + ".");
			}
		}
		SENTENCES = Collections.unmodifiableList(sentencesList);
	}
	
	private static final ThreadLocalRandom r = ThreadLocalRandom.current();
	private static int profileId = 0;
	private static LocalDate birthDay = null;
	
	public static void main(String[] args) throws Exception {
		clearMedia();
		List<Certificate> certificates = loadCertificates();
		List<Profile> profiles = loadProfiles();
		List<ProfileConfig> profileConfigs = getProfileConfigs();
		try (Connection c = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
			c.setAutoCommit(false);
			clearDb(c);
			insertSkillCategories(c);
			insertLanguageTypes(c);
			insertLanguageLevels(c);
			for (Profile p : profiles) {
				ProfileConfig profileConfig = profileConfigs.get(r.nextInt(profileConfigs.size()));
				createProfile(c, p, profileConfig, certificates);
				System.out.println("Created profile for " + p.firstName + " " + p.lastName);
			}
			c.commit();
			System.out.println("Data generated successfully");
		}
	}

	private static void clearMedia() throws IOException {
		if (Files.exists(Paths.get(MEDIA_DIR))) {
			Files.walkFileTree(Paths.get(MEDIA_DIR), new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}
			});
		}
		System.out.println("Media dir cleared");
	}
	
	private static List<Certificate> loadCertificates() {
		File[] files = new File(CERTIFICATE_PATH).listFiles();
		List<Certificate> list = new ArrayList<>(files.length);
		for (File f : files) {
			String name = f.getName().replace("-", " ");
			name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
			list.add(new Certificate(name, f.getAbsolutePath()));
		}
		return list;
	}
	
	private static List<Profile> loadProfiles() {
		File[] photos = new File(PHOTO_PATH).listFiles();
		List<Profile> list = new ArrayList<>(photos.length);
		for (File f : photos) {
			String[] names = f.getName().replace(".jpg", "").split(" ");
			list.add(new Profile(names[0], names[1], f.getAbsolutePath()));
		}
		Collections.sort(list, (p1, p2) -> {
			int firstNameCompare = p1.firstName.compareTo(p2.firstName);
			if (firstNameCompare != 0) {
				return firstNameCompare;
			} else {
				return p1.lastName.compareTo(p2.lastName);
			}
		});
		return list;
	}
	
	private static List<ProfileConfig> getProfileConfigs() {
		List<ProfileConfig> res = new ArrayList<>();
		res.add(new ProfileConfig("Junior java trainee position",
				"Java core course with developing one simple console application",
				new Course[] {Course.createCoreCourse()}, 0));
		res.add(new ProfileConfig("Junior java trainee position",
				"One Java professional course with developing web application blog (Link to demo is provided)",
				new Course[] {Course.createBaseCourse()}, 0));
		res.add(new ProfileConfig("Junior java developer position",
				"One Java professional course with developing web application resume (Link to demo is provided)",
				new Course[] {Course.createAdvancedCourse()}, 0));
		res.add(new ProfileConfig("Junior java developer position",
				"One Java professional course with developing web application resume (Link to demo is provided)",
				new Course[] {Course.createAdvancedCourse()}, 1));
		res.add(new ProfileConfig("Junior java developer position",
				"Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)",
				new Course[] {Course.createAdvancedCourse(), Course.createBaseCourse() }, 1));
		res.add(new ProfileConfig("Junior java developer position",
				"Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)",
				new Course[] {Course.createAdvancedCourse(), Course.createBaseCourse() }, 1));
		res.add(new ProfileConfig("Junior java developer position",
				"Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)",
				new Course[] {Course.createAdvancedCourse(), Course.createBaseCourse() }, 1));
		res.add(new ProfileConfig("Junior java developer position",
				"Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)",
				new Course[] {Course.createAdvancedCourse(), Course.createBaseCourse() }, 2));
		res.add(new ProfileConfig("Junior java developer position",
				"Three Java professional courses with developing one console application and two web applications: blog and resume (Links to demo are provided)",
				new Course[] { Course.createAdvancedCourse(), Course.createBaseCourse(), Course.createCoreCourse()  }, 2));
		
		return res;
	}
	
	private static void clearDb(Connection c) throws SQLException {
		Statement st = c.createStatement();
		st.executeUpdate("DELETE FROM certificate");
		st.executeUpdate("DELETE FROM course");
		st.executeUpdate("DELETE FROM education");
		st.executeUpdate("DELETE FROM experience");
		st.executeUpdate("DELETE FROM f_language");
		st.executeUpdate("DELETE FROM f_skill");
		st.executeUpdate("DELETE FROM f_language_level");
		st.executeUpdate("DELETE FROM f_language_type");
		st.executeUpdate("DELETE FROM f_skill_category");
		st.executeUpdate("DELETE FROM hobby");
		st.executeUpdate("DELETE FROM profile");
		st.execute("SELECT setval('certificate_seq', 1, false)");
		st.execute("SELECT setval('course_seq', 1, false)");
		st.execute("SELECT setval('education_seq', 1, false)");
		st.execute("SELECT setval('experience_seq', 1, false)");
		st.execute("SELECT setval('hobby_seq', 1, false)");
		st.execute("SELECT setval('language_seq', 1, false)");
		st.execute("SELECT setval('profile_seq', 1, false)");
		st.execute("SELECT setval('skill_seq', 1, false)");
		
		System.out.println("Db cleared");
		
	}

	private static void insertSkillCategories(Connection c) throws SQLException {
		PreparedStatement ps = c.prepareStatement("INSERT INTO f_skill_category VALUES (?,?)");
		for (Map.Entry<Short, SkillCategory> entry : SKILL_CATEGORIES.entrySet()) {
			ps.setShort(1, entry.getKey());
			ps.setString(2, entry.getValue().getCategory());
			ps.addBatch();
		}
		ps.executeBatch();
		ps.close();
		
	}
	
	private static void insertLanguageTypes(Connection c) throws SQLException {
		PreparedStatement ps = c.prepareStatement("INSERT INTO f_language_type VALUES (?,?)");
		for (Map.Entry<Short, LanguageType> entry : LANGUAGE_TYPES.entrySet()) {
			ps.setShort(1, entry.getKey());
			ps.setString(2, entry.getValue().getType());
			ps.addBatch();
		}
		ps.executeBatch();
		ps.close();
	}
	
	private static void insertLanguageLevels(Connection c) throws SQLException {
		PreparedStatement ps = c.prepareStatement("INSERT INTO f_language_level VALUES (?,?)");
		for (Map.Entry<Short, LanguageLevel> entry : LANGUAGE_LEVELS.entrySet()) {
			ps.setShort(1, entry.getKey());
			ps.setString(2, entry.getValue().getLevel());
			ps.addBatch();
		}
		ps.executeBatch();
		ps.close();
	}
	
	private static void createProfile(Connection c, Profile profile, ProfileConfig profileConfig, List<Certificate> certificates)
		throws SQLException, IOException {
		insertProfileData(c, profile, profileConfig);
		if (profileConfig.certificates > 0) {
			insertCertificates(c, profileConfig.certificates, certificates);
		}
		insertEducation(c);
		insertHobbies(c);
		insertLanguages(c);
		insertExperience(c, profileConfig);
		insertSkills(c, profileConfig);
		insertCourses(c);
	}
	
	private static void insertProfileData(Connection c, Profile profile, ProfileConfig profileConfig)
		throws SQLException, IOException {
		PreparedStatement ps = 
				c.prepareStatement("INSERT INTO profile VALUES (nextval('profile_seq'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,true,?,?,?,?,?,?,?)");
		ps.setString(1, (profile.firstName + "-" + profile.lastName).toLowerCase());
		ps.setString(2, profile.firstName);
		ps.setString(3, profile.lastName);
		birthDay = randomBirthDate();
		ps.setObject(4, birthDay);
		ps.setString(5, generatePhone());
		ps.setString(6, (profile.firstName + "-" + profile.lastName).toLowerCase() + "@gmail.com");
		ps.setString(7, COUNTRY);
		ps.setString(8, CITIES[r.nextInt(CITIES.length)]);
		ps.setString(9, profileConfig.objective);
		ps.setString(10, profileConfig.summary);
		
		String uid = UUID.randomUUID().toString() + ".jpg";
		File photo = new File(MEDIA_DIR + "/avatar/" + uid);
		if (!photo.getParentFile().exists()) {
			photo.getParentFile().mkdirs();
		}
		Files.copy(Paths.get(profile.photo), Paths.get(photo.getAbsolutePath()));
		
		ps.setString(11, "/media/avatar/" + uid);
		
		String smallUid = uid.replace(".jpg", "-sm.jpg");
		Thumbnails.of(photo).size(110, 110).toFile(new File(MEDIA_DIR + "/avatar/" + smallUid));
		
		ps.setString(12, "/media/avatar/" + smallUid);
		if (r.nextBoolean()) {
			ps.setString(13, getInfo());
		} else {
			ps.setNull(13, Types.VARCHAR);
		}
		
		ps.setString(14, PASSWORD_HASH);
		
		ps.setObject(15, LocalDateTime.now());
		
		if (r.nextBoolean()) {
			ps.setString(16, (profile.firstName + "-" + profile.lastName).toLowerCase());
		} else {
			ps.setNull(16, Types.VARCHAR);
		}
		if (r.nextBoolean()) {
			ps.setString(17, "https://vk.com/" + (profile.firstName + "-" + profile.lastName).toLowerCase());
		} else {
			ps.setNull(17, Types.VARCHAR);
		}
		if (r.nextBoolean()) {
			ps.setString(18, "https://facebook.com/" + (profile.firstName + "-" + profile.lastName).toLowerCase());
		} else {
			ps.setNull(18, Types.VARCHAR);
		}
		if (r.nextBoolean()) {
			ps.setString(19, "https://linkedin.com/" + (profile.firstName + "-" + profile.lastName).toLowerCase());
		} else {
			ps.setNull(19, Types.VARCHAR);
		}
		if (r.nextBoolean()) {
			ps.setString(20, "https://github.com/" + (profile.firstName + "-" + profile.lastName).toLowerCase());
		} else {
			ps.setNull(20, Types.VARCHAR);
		}
		
		if (r.nextBoolean()) {
			ps.setString(21, "https://stackoverflow.com/" + (profile.firstName + "-" + profile.lastName).toLowerCase());
		} else {
			ps.setNull(21, Types.VARCHAR);
		}
		
		ps.executeUpdate();
		ps.close();
		profileId++;
	}
	
	private static void insertCertificates(Connection c, int certificatesCount, List<Certificate> certificates)
				throws SQLException, IOException {
		Collections.shuffle(certificates);
		PreparedStatement ps = c.prepareStatement("INSERT INTO certificate VALUES (nextval('certificate_seq'),?,?,?,?)");
		for (int i = 0; i < certificatesCount && i < certificates.size(); i++) {
			Certificate certificate = certificates.get(i);
			ps.setLong(1, profileId);
			ps.setString(2, certificate.name);
			String uid = UUID.randomUUID().toString() + ".jpg";
			File photo = new File(MEDIA_DIR + "/certificates/" + uid);
			if (!photo.getParentFile().exists()) {
				photo.getParentFile().mkdirs();
			}
			String smallUid = uid.replace(".jpg", "-sm.jpg");
			Files.copy(Paths.get(certificate.largeImg), Paths.get(photo.getAbsolutePath()));
			ps.setString(3, "/media/certificates/" + uid);
			Thumbnails.of(photo).size(100, 100).toFile(Paths.get(photo.getAbsolutePath().replace(".jpg", "-sm.jpg")).toFile());
			ps.setString(4, "/media/certificates/" + smallUid);
			
			ps.addBatch();
		}
		ps.executeBatch();
		ps.close();
	}
	
	private static void insertEducation(Connection c) throws SQLException {
		PreparedStatement ps = c.prepareStatement("INSERT INTO education values (nextval('education_seq'),?,?,?,?,?,?)");
		ps.setLong(1, profileId);
		ps.setString(2, "The specialist degree in Electronic Engineering");
		Date finish = randomFinishEducation();
		Date begin = addField(finish, Calendar.YEAR, -5, true);
		ps.setInt(3, new java.util.Date(begin.getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear());
		if (finish.getTime() > System.currentTimeMillis()) {
			ps.setNull(4, Types.INTEGER);
		} else {
			ps.setInt(4, new java.util.Date(finish.getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear());
		}
		ps.setString(5, "Kharkiv National Technical University, Ukraine");
		ps.setString(6, "Computer Science");
		ps.executeUpdate();
		ps.close();
	}
	
	private static void insertHobbies(Connection c) throws SQLException {
		PreparedStatement ps = c.prepareStatement("INSERT INTO hobby VALUES (nextval('hobby_seq'),?,?)");
		List<String> hobbies = new ArrayList<>(Arrays.asList(HOBBIES));
		Collections.shuffle(hobbies);
		for (int i = 0; i < 5; i++) {
			ps.setLong(1, profileId);
			ps.setString(2, hobbies.remove(0));
			ps.addBatch();
		}
		ps.executeBatch();
		ps.close();
	}
	
	private static void insertLanguages(Connection c) throws SQLException {
		List<String> languages = new ArrayList<>();
		languages.add("English");
		if (r.nextBoolean()) {
			int cnt = r.nextInt(1) + 1;
			List<String> otherLng = new ArrayList<>(Arrays.asList(FOREIGN_LANGUAGES));
			Collections.shuffle(otherLng);
			for (int i = 0; i < cnt; i++) {
				languages.add(otherLng.remove(0));
			}
		}
		
		PreparedStatement ps = c.prepareStatement("INSERT INTO f_language VALUES (nextval('language_seq'),?,?,?,?)");
		for (String language : languages) {
			Map.Entry<Short, LanguageType> languageType = getRandomLanguageType();
			Map.Entry<Short, LanguageLevel> languageLevel = getRandomLanguageLevel();
			ps.setLong(1, profileId);
			ps.setString(2, language);
			ps.setShort(3, languageType.getKey());
			ps.setShort(4, languageLevel.getKey());
			
			ps.addBatch();
			if (languageType.getValue() != LanguageType.ALL) {
				ps.setLong(1, profileId);
				ps.setString(2, language);
				Map.Entry<Short, LanguageLevel> newLanguageLevel = getRandomLanguageLevel();
				while (newLanguageLevel.getValue() == languageLevel.getValue()) {
					newLanguageLevel = getRandomLanguageLevel();
				}
				ps.setShort(3, getReverseType(languageType));
				ps.setShort(4, newLanguageLevel.getKey());
				ps.addBatch();
			}
		}
		ps.executeBatch();
		ps.close();
	}
	
	private static void insertExperience(Connection c, ProfileConfig profileConfig) throws SQLException {
		PreparedStatement ps =
				c.prepareStatement("INSERT INTO experience VALUES (nextval('experience_seq'),?,?,?,?,?,?,?,?)");
		boolean currentCourse = r.nextBoolean();
		Date finish = addField(new Date(System.currentTimeMillis()), Calendar.MONTH, -(r.nextInt(3) + 1), false);
		for (Course course : profileConfig.courses) {
			ps.setLong(1, profileId);
			ps.setString(2, course.name);
			ps.setString(3, course.company);
			if (currentCourse) {
				ps.setDate(4, addField(new Date(System.currentTimeMillis()), Calendar.MONTH, -1, false));
				ps.setNull(5, Types.DATE);
			} else {
				ps.setDate(4, addField(finish, Calendar.MONTH, -1, false));
				ps.setDate(5, finish);
				finish = addField(finish, Calendar.MONTH, -(r.nextInt(3) + 1), false);
			}
			ps.setString(6, course.responsibilities);
			if (course.demo == null) {
				ps.setNull(7, Types.VARCHAR);
			} else {
				ps.setString(7, course.demo);
			}
			if (course.github == null) {
				ps.setNull(8, Types.VARCHAR);
			} else {
				ps.setString(8, course.github);
			}
			ps.addBatch();
		}
		ps.executeBatch();
		ps.close();
	}
	
	private static void insertSkills(Connection c, ProfileConfig profileConfig) throws SQLException {
		PreparedStatement ps = c.prepareStatement("INSERT INTO f_skill VALUES (nextval('skill_seq'),?,?,?)");
		Map<SkillCategory, Set<String>> skillMap = createSkillMap();
		for (Course course : profileConfig.courses) {
			for (SkillCategory category : skillMap.keySet()) {
				skillMap.get(category).addAll(course.skills.get(category));
			}
		}
		for (Map.Entry<SkillCategory, Set<String>> entry : skillMap.entrySet()) {
			if (!entry.getValue().isEmpty()) {
				ps.setLong(1, profileId);
				ps.setShort(2, getCategory(entry.getKey()));
				ps.setString(3, StringUtils.join(entry.getValue().toArray(), ","));
				
				ps.addBatch();
			}
		}
		ps.executeBatch();
		ps.close();
	}
	
	private static void insertCourses(Connection c) throws SQLException {
		if (r.nextBoolean()) {
			PreparedStatement ps = c.prepareStatement("INSERT INTO course VALUES (nextval('course_seq'),?,?,?,?)");
			ps.setLong(1, profileId);
			ps.setString(2, "Java Advanced Course");
			ps.setString(3, "SourceIt");
			Date finish = randomFinishEducation();
			if (finish.getTime() > System.currentTimeMillis()) {
				ps.setNull(4, Types.DATE);
			} else {
				ps.setDate(4, finish);
			}
			ps.executeUpdate();
			ps.close();
		}
	}

	private static short getCategory(SkillCategory key) {
		for (Map.Entry<Short, SkillCategory> entry : SKILL_CATEGORIES.entrySet()) {
			if (entry.getValue() == key) {
				return entry.getKey();
			}
		}
		throw new IllegalStateException("Can not category id for category: " + key);
	}

	private static short getReverseType(Entry<Short, LanguageType> languageType) {
		if (languageType.getValue() == LanguageType.SPOKEN) {
			return 2;
		}
		return 1;
	}

	private static Entry<Short, LanguageLevel> getRandomLanguageLevel() {
		int rand = r.nextInt(LANGUAGE_TYPES.size());
		int idx = 0;
		for (Entry<Short, LanguageLevel> entry : LANGUAGE_LEVELS.entrySet()) {
			if (rand == idx) {
				return entry;
			}
			idx++;
		}
		throw new IllegalStateException("Can not choose random LanguageLevel");
	}

	private static Entry<Short, LanguageType> getRandomLanguageType() {
		int rand = r.nextInt(LANGUAGE_TYPES.size());
		int idx = 0;
		for (Entry<Short, LanguageType> entry : LANGUAGE_TYPES.entrySet()) {
			if (rand == idx) {
				return entry;
			}
			idx++;
		}
		throw new IllegalStateException("Can not choose random LanguageType");
	}

	private static Date randomFinishEducation() {
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(birthDay.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli());
		cl.set(Calendar.DAY_OF_MONTH, 30);
		cl.set(Calendar.MONTH, Calendar.JUNE);
		int year = cl.get(Calendar.YEAR) + 21;
		cl.set(Calendar.YEAR, year + r.nextInt(3));
		return new Date(cl.getTimeInMillis());
	}
	
	private static Date addField(Date finish, int field, int value, boolean isBeginEducation) {
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(finish.getTime());
		cl.add(field, value);
		if (isBeginEducation) {
			cl.set(Calendar.DAY_OF_MONTH, 1);
			cl.set(Calendar.MONTH, Calendar.SEPTEMBER);
		}
		return new Date(cl.getTimeInMillis());
	}

	private static String getInfo() {
		int endIndex = r.nextInt(SENTENCES.size());
		int startIndex = r.nextInt(endIndex);
		if (endIndex - startIndex > 4) {
			endIndex = startIndex + 3;
		}
		return StringUtils.join(SENTENCES.subList(startIndex, endIndex), " ");
	}

	private static String generatePhone() {
		StringBuilder phone = new StringBuilder("+38050");
		for (int i = 0; i < 7; i++) {
			int code = '1' + r.nextInt(9);
			phone.append((char)code);
		}
		return phone.toString();
	}

	private static LocalDate randomBirthDate() {
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.DAY_OF_MONTH, r.nextInt(30));
		cl.set(Calendar.MONTH, r.nextInt(12));
		int year = cl.get(Calendar.YEAR) - 30;
		cl.set(Calendar.YEAR, year + r.nextInt(10));
		return cl.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	private static Map<Short, LanguageType> getLanguageTypes() {
		Map<Short, LanguageType> types = new LinkedHashMap<>();
		types.put((short) 0, LanguageType.ALL);
		types.put((short) 1, LanguageType.SPOKEN);
		types.put((short) 2, LanguageType.WRITTEN);
		return types;
	}
	
	private static Map<Short, LanguageLevel> getLanguageLevels() {
		Map<Short, LanguageLevel> levels = new LinkedHashMap<>();
		levels.put((short) 0, LanguageLevel.BEGINNER);
		levels.put((short) 1, LanguageLevel.ELEMENTARY);
		levels.put((short) 2, LanguageLevel.PRE_INTERMEDIATE);
		levels.put((short) 3, LanguageLevel.INTERMEDIATE);
		levels.put((short) 4, LanguageLevel.UPPER_INTERMEDIATE);
		levels.put((short) 5, LanguageLevel.ADVANCED);
		levels.put((short) 6, LanguageLevel.PROFICIENCY);
		
		return levels;
	}
	
	private static Map<Short, SkillCategory> getSkillCategories() {
		Map<Short, SkillCategory> categories = new LinkedHashMap<>();
		categories.put((short) 0, SkillCategory.LANGUAGES);
		categories.put((short) 1, SkillCategory.DBMS);
		categories.put((short) 2, SkillCategory.FRONTEND);
		categories.put((short) 3, SkillCategory.BACKEND);
		categories.put((short) 4, SkillCategory.IDE);
		categories.put((short) 5, SkillCategory.CVS);
		categories.put((short) 6, SkillCategory.WEB_SERVERS);
		categories.put((short) 7, SkillCategory.BUILD_SYSTEMS);
		categories.put((short) 8, SkillCategory.CLOUD);
		
		return categories;
	}
	
	private static Map<SkillCategory, Set<String>> createSkillMap() {
		Map<SkillCategory, Set<String>> skills = new LinkedHashMap<>();
		skills.put(SkillCategory.LANGUAGES, new LinkedHashSet<>());
		skills.put(SkillCategory.DBMS, new LinkedHashSet<>());
		skills.put(SkillCategory.FRONTEND, new LinkedHashSet<>());
		skills.put(SkillCategory.BACKEND, new LinkedHashSet<>());
		skills.put(SkillCategory.IDE, new LinkedHashSet<>());
		skills.put(SkillCategory.CVS, new LinkedHashSet<>());
		skills.put(SkillCategory.WEB_SERVERS, new LinkedHashSet<>());
		skills.put(SkillCategory.BUILD_SYSTEMS, new LinkedHashSet<>());
		skills.put(SkillCategory.CLOUD, new LinkedHashSet<>());
		
		return skills;
	}

	private static String readDummyText() {
		try {
			return FileUtils.readFileToString(new File(DUMMY_CONTENT_PATH), Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static final class Certificate {
		private final String name;
		private final String largeImg;

		private Certificate(String name, String largeImg) {
			this.name = name;
			this.largeImg = largeImg;
		}
	}
	
	private static final class Profile {
		private final String firstName;
		private final String lastName;
		private final String photo;
		
		private Profile(String firstName, String lastName, String photo) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.photo = photo;
		}
		
		@Override
		public String toString() {
			return String.format("Profile [firstName=%s, lastName=%s]", firstName, lastName);
		}
	}
	
	private static final class ProfileConfig {
		private final String objective;
		private final String summary;
		private final Course[] courses;
		private final int certificates;
		
		private ProfileConfig(String objective, String summary, Course[] courses, int certificates) {
			this.objective = objective;
			this.summary = summary;
			this.courses = courses;
			this.certificates = certificates;
		}
	}
	
	private static final class Course {
		private final String name;
		private final String company;
		private final String github;
		private final String responsibilities;
		private final String demo;
		private final Map<SkillCategory, Set<String>> skills;
		
		private Course(String name, String company, String github, String responsibilities, String demo,
				Map<SkillCategory, Set<String>> skills) {
			this.name = name;
			this.company = company;
			this.github = github;
			this.responsibilities = responsibilities;
			this.demo = demo;
			this.skills = skills;
		}
		
		static Course createCoreCourse() {
			Map<SkillCategory, Set<String>> skills = createSkillMap();
			skills.get(SkillCategory.LANGUAGES).add("Java");
			
			skills.get(SkillCategory.DBMS).add("Mysql");
			
			skills.get(SkillCategory.BACKEND).add("Java Threads");
			skills.get(SkillCategory.BACKEND).add("IO");
			skills.get(SkillCategory.BACKEND).add("JAXB");
			skills.get(SkillCategory.BACKEND).add("GSON");
			
			skills.get(SkillCategory.IDE).add("Eclipse for JEE Developer");
			
			skills.get(SkillCategory.CVS).add("Git");
			skills.get(SkillCategory.CVS).add("GitHub");
			
			skills.get(SkillCategory.BUILD_SYSTEMS).add("Maven");
			
			return new Course("Java Core Course", "DevStude.net", null,
					"Developing the java console application which imports XML, JSON, Properties, CVS to Db via JDBC",
					null, skills);
		}
		
		static Course createBaseCourse() {
			Map<SkillCategory, Set<String>> skills = createSkillMap();
			skills.get(SkillCategory.LANGUAGES).add("Java");
			skills.get(SkillCategory.LANGUAGES).add("SQL");
			
			skills.get(SkillCategory.DBMS).add("Postgresql");
			
			skills.get(SkillCategory.FRONTEND).add("HTML");
			skills.get(SkillCategory.FRONTEND).add("CSS");
			skills.get(SkillCategory.FRONTEND).add("JS");
			skills.get(SkillCategory.FRONTEND).add("Boostrap");
			skills.get(SkillCategory.FRONTEND).add("JQuery");
			
			skills.get(SkillCategory.BACKEND).add("Java Servlets");
			skills.get(SkillCategory.BACKEND).add("Logback");
			skills.get(SkillCategory.BACKEND).add("JSTL");
			skills.get(SkillCategory.BACKEND).add("JDBC");
			skills.get(SkillCategory.BACKEND).add("Apache Commons");
			skills.get(SkillCategory.BACKEND).add("Google+ Social API");
			
			skills.get(SkillCategory.IDE).add("Eclipse for JEE Developer");
			
			skills.get(SkillCategory.CVS).add("Git");
			skills.get(SkillCategory.CVS).add("GitHub");
			
			skills.get(SkillCategory.WEB_SERVERS).add("Tomcat");
			
			skills.get(SkillCategory.BUILD_SYSTEMS).add("Maven");
			
			skills.get(SkillCategory.CLOUD).add("OpenShift");
			
			return new Course("Java Base Course",
					"DevStude.net",
					"https://github.com/TODO",
					"Developing the web application 'blog' using free HTML template, downloaded from intenet."
					+ " Populating database by test data and uploading web project to OpenShift free hosting",
					"http://LINK_TO_DEMO_SITE",
					skills);
		}
		
		static Course createAdvancedCourse() {
			Map<SkillCategory, Set<String>> skills = createSkillMap();
			skills.get(SkillCategory.LANGUAGES).add("Java");
			skills.get(SkillCategory.LANGUAGES).add("SQL");
			skills.get(SkillCategory.LANGUAGES).add("PLSQL");
			
			skills.get(SkillCategory.DBMS).add("Postgresql");
			
			skills.get(SkillCategory.FRONTEND).add("HTML");
			skills.get(SkillCategory.FRONTEND).add("CSS");
			skills.get(SkillCategory.FRONTEND).add("JS");
			skills.get(SkillCategory.FRONTEND).add("Boostrap");
			skills.get(SkillCategory.FRONTEND).add("JQuery");
			
			skills.get(SkillCategory.BACKEND).add("Spring MVC");
			skills.get(SkillCategory.BACKEND).add("Logback");
			skills.get(SkillCategory.BACKEND).add("JSP");
			skills.get(SkillCategory.BACKEND).add("JSTL");
			skills.get(SkillCategory.BACKEND).add("SPring Data JPA");
			skills.get(SkillCategory.BACKEND).add("Apache Commons");
			skills.get(SkillCategory.BACKEND).add("Spring Security");
			skills.get(SkillCategory.BACKEND).add("Hibernate JPA");
			skills.get(SkillCategory.BACKEND).add("Facebook Social API");
			
			skills.get(SkillCategory.IDE).add("Eclipse for JEE Developer");
			
			skills.get(SkillCategory.CVS).add("Git");
			skills.get(SkillCategory.CVS).add("GitHub");
			
			skills.get(SkillCategory.WEB_SERVERS).add("Tomcat");
			skills.get(SkillCategory.WEB_SERVERS).add("Nginx");
			
			skills.get(SkillCategory.BUILD_SYSTEMS).add("Maven");
			
			skills.get(SkillCategory.CLOUD).add("AWS");
			
			return new Course("Java Advanced Course",
					"DevStude.net",
					"https://github.com/TODO",
					"Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet."
					+ " Populating database by test data and uploading web project to AWS EC2 instance",
					"http://LINK_TO_DEMO_SITE",
					skills);
		}
	}
}
