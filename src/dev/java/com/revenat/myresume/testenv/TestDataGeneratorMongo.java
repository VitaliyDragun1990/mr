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
import java.sql.Date;
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
import org.bson.Document;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.revenat.myresume.domain.document.LanguageLevel;
import com.revenat.myresume.domain.document.LanguageType;
import com.revenat.myresume.domain.document.SkillCategory;

import net.coobird.thumbnailator.Thumbnails;

/**
 * Add postgresql JDBC driver to classpath before launch this generqator
 * 
 * @author Vitaliy Dragun
 *
 */
public class TestDataGeneratorMongo {

	private static final String COLLECTION_PROFILE_RESTORE = "profileRestore";
	private static final String COLLECTION_REMEMBER_ME_TOKEN = "rememberMeToken";
	private static final String COLLECTION_PROFILE = "profile";
	// Settings for MongoDB
	private static final String MONGO_URL = "127.0.0.1";
	private static final int MONGO_PORT = 27117;
	private static final String MONGO_DB = "resume";

	private static final String PHOTO_PATH = "external/test-data/photos/";
	private static final String CERTIFICATE_PATH = "external/test-data/certificates/";
	private static final String MEDIA_DIR = "E:/java/eclipse_workspace_02/my-resume/src/main/webapp/media";
	private static final String COUNTRY = "Ukraine";
	private static final String[] CITIES = { "Kharkiv", "Kiyv", "Odessa" };
	private static final String[] FOREIGN_LANGUAGES = { "Spanish", "French", "German", "Italian" };
	// password
	private static final String PASSWORD_HASH = "$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq";

	private static final String[] HOBBIES = { "Cycling", "Handball", "Football", "Basketball", "Bowling", "Boxing",
			"Volleyball", "Baseball", "Skating", "Skiing", "Table tennis", "Tennis", "Weightlifting", "Automobiles",
			"Book reading", "Cricket", "Photo", "Shopping", "Cooking", "Codding", "Animals", "Traveling", "Movie",
			"Painting", "Darts", "Fishing", "Kayak slalom", "Games of chance", "Ice hockey", "Roller skating",
			"Swimming", "Diving", "Golf", "Shooting", "Rowing", "Camping", "Archery", "Pubs", "Music", "Computer games",
			"Authorship", "Singing", "Foreign lang", "Billiards", "Skateboarding", "Collecting", "Badminton", "Disco" };

	private static final Map<Short, LanguageType> LANGUAGE_TYPES = getLanguageTypes();
	private static final Map<Short, LanguageLevel> LANGUAGE_LEVELS = getLanguageLevels();

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
	private static LocalDate birthDay = null;

	public static void main(String[] args) throws Exception {
		clearMedia();
		List<Certificate> certificates = loadCertificates();
		List<Profile> profiles = loadProfiles();
		List<ProfileConfig> profileConfigs = getProfileConfigs();
		
		MongoClient mongo = null;
		try {
			mongo = new MongoClient(MONGO_URL, MONGO_PORT);
			clearDb(mongo);
			
			for (Profile p : profiles) {
				ProfileConfig profileConfig = profileConfigs.get(r.nextInt(profileConfigs.size()));
				createProfile(mongo, p, profileConfig, certificates);
				System.out.println("Created profile for " + p.firstName + " " + p.lastName);
			}
			createProfileIndexes(mongo);
			createRememberMeTokenIndexes(mongo);
			System.out.println("Data generated successfully");
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
	}

	private static void createRememberMeTokenIndexes(MongoClient mongo) {
		getDb(mongo)
			.getCollection(COLLECTION_REMEMBER_ME_TOKEN)
			.createIndex(new BasicDBObject("series", 1), new IndexOptions().unique(true).name("series_idx"));
		getDb(mongo)
			.getCollection(COLLECTION_REMEMBER_ME_TOKEN)
			.createIndex(new BasicDBObject("username", 1), new IndexOptions().unique(false).name("username_idx"));
		
		System.out.println("Created indexes for rememberMeToken collection");
	}

	private static void createProfileIndexes(MongoClient mongo) {
		getDb(mongo).getCollection(COLLECTION_PROFILE)
			.createIndex(new BasicDBObject("email", 1), new IndexOptions().unique(true).name("email_idx"));
		getDb(mongo).getCollection(COLLECTION_PROFILE)
			.createIndex(new BasicDBObject("phone", 1), new IndexOptions().unique(true).name("phone_idx"));
		getDb(mongo).getCollection(COLLECTION_PROFILE)
			.createIndex(new BasicDBObject("uid", 1), new IndexOptions().unique(true).name("uid_idx"));
		getDb(mongo).getCollection(COLLECTION_PROFILE)
			.createIndex(new BasicDBObject("completed", 1), new IndexOptions().unique(false).name("completed_idx"));
		getDb(mongo).getCollection(COLLECTION_PROFILE)
			.createIndex(new BasicDBObject("created", 1), new IndexOptions().unique(false).name("created_idx"));
		
		getDb(mongo).getCollection(COLLECTION_PROFILE_RESTORE)
			.createIndex(new BasicDBObject("token", 1), new IndexOptions().unique(true).name("token_idx"));
		
		System.out.println("Created indexes for profile collection");
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
				new Course[] { Course.createCoreCourse() }, 0));
		res.add(new ProfileConfig("Junior java trainee position",
				"One Java professional course with developing web application blog (Link to demo is provided)",
				new Course[] { Course.createBaseCourse() }, 0));
		res.add(new ProfileConfig("Junior java developer position",
				"One Java professional course with developing web application resume (Link to demo is provided)",
				new Course[] { Course.createAdvancedCourse() }, 0));
		res.add(new ProfileConfig("Junior java developer position",
				"One Java professional course with developing web application resume (Link to demo is provided)",
				new Course[] { Course.createAdvancedCourse() }, 1));
		res.add(new ProfileConfig("Junior java developer position",
				"Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)",
				new Course[] { Course.createAdvancedCourse(), Course.createBaseCourse() }, 1));
		res.add(new ProfileConfig("Junior java developer position",
				"Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)",
				new Course[] { Course.createAdvancedCourse(), Course.createBaseCourse() }, 1));
		res.add(new ProfileConfig("Junior java developer position",
				"Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)",
				new Course[] { Course.createAdvancedCourse(), Course.createBaseCourse() }, 1));
		res.add(new ProfileConfig("Junior java developer position",
				"Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)",
				new Course[] { Course.createAdvancedCourse(), Course.createBaseCourse() }, 2));
		res.add(new ProfileConfig("Junior java developer position",
				"Three Java professional courses with developing one console application and two web applications: blog and resume (Links to demo are provided)",
				new Course[] { Course.createAdvancedCourse(), Course.createBaseCourse(), Course.createCoreCourse() },
				2));

		return res;
	}

	private static MongoDatabase getDb(MongoClient mongo) {
		return mongo.getDatabase(MONGO_DB);
	}

	private static void clearDb(MongoClient mongo) {
		getDb(mongo).getCollection(COLLECTION_PROFILE).drop();
		getDb(mongo).getCollection(COLLECTION_REMEMBER_ME_TOKEN).drop();
		getDb(mongo).getCollection(COLLECTION_PROFILE_RESTORE).drop();
		System.out.println("Db cleared");
	}

	private static void createProfile(MongoClient mongo, Profile profile, ProfileConfig profileConfig,
			List<Certificate> certificates) throws IOException {
		Document newProfile = insertProfileData(mongo, profile, profileConfig);
		if (profileConfig.certificates > 0) {
			putList(newProfile, "certificates",
					insertCertificates(mongo, profileConfig.certificates, certificates));
		}
		putList(newProfile, "education", insertEducation(mongo));
		putList(newProfile, "hobbies", insertHobbies(mongo));
		putList(newProfile, "languages", insertLanguages(mongo));
		putList(newProfile, "experience", insertExperience(mongo, profileConfig));
		putList(newProfile, "skills", insertSkills(mongo, profileConfig));
		putList(newProfile, "courses", insertCourses(mongo));
		
		getDb(mongo).getCollection(COLLECTION_PROFILE).insertOne(newProfile);
	}

	private static void putList(Document insertedProfile, String propertyName, BasicDBList list) {
		if (list != null && !list.isEmpty()) {
			insertedProfile.put(propertyName, list);
		}
	}

	private static Document insertProfileData(MongoClient mongo, Profile profile, ProfileConfig profileConfig)
			throws IOException {
		Document insertedProfile = new Document();

		insertedProfile.put("uid", (profile.firstName + "-" + profile.lastName).toLowerCase());
		insertedProfile.put("firstName", profile.firstName);
		insertedProfile.put("lastName", profile.lastName);
		birthDay = randomBirthDate();
		insertedProfile.put("birthDay", birthDay);
		insertedProfile.put("phone", generatePhone());
		insertedProfile.put("email", (profile.firstName + "-" + profile.lastName).toLowerCase() + "@gmail.com");
		insertedProfile.put("country", COUNTRY);
		insertedProfile.put("city", CITIES[r.nextInt(CITIES.length)]);
		insertedProfile.put("objective", profileConfig.objective);
		insertedProfile.put("summary", profileConfig.summary);

		String uid = UUID.randomUUID().toString() + ".jpg";

		File photo = new File(MEDIA_DIR + "/avatar/" + uid);
		if (!photo.getParentFile().exists()) {
			photo.getParentFile().mkdirs();
		}
		Files.copy(Paths.get(profile.photo), Paths.get(photo.getAbsolutePath()));

		insertedProfile.put("largePhoto", "/media/avatar/" + uid);

		String smallUid = uid.replace(".jpg", "-sm.jpg");
		Thumbnails.of(photo).size(110, 110).toFile(new File(MEDIA_DIR + "/avatar/" + smallUid));

		insertedProfile.put("smallPhoto", "/media/avatar/" + smallUid);

		if (r.nextBoolean()) {
			insertedProfile.put("info", getInfo());
		}

		insertedProfile.put("password", PASSWORD_HASH);
		insertedProfile.put("created", LocalDateTime.now());

		BasicDBObject contacts = new BasicDBObject();
		insertedProfile.put("contacts", contacts);

		if (r.nextBoolean()) {
			contacts.put("skype", (profile.firstName + "-" + profile.lastName).toLowerCase());
		}
		if (r.nextBoolean()) {
			contacts.put("vkontakte", "https://vk.com/" + (profile.firstName + "-" + profile.lastName).toLowerCase());
		}
		if (r.nextBoolean()) {
			contacts.put("facebook",
					"https://facebook.com/" + (profile.firstName + "-" + profile.lastName).toLowerCase());
		}
		if (r.nextBoolean()) {
			contacts.put("linkedin",
					"https://linkedin.com/" + (profile.firstName + "-" + profile.lastName).toLowerCase());
		}
		if (r.nextBoolean()) {
			contacts.put("github", "https://github.com/" + (profile.firstName + "-" + profile.lastName).toLowerCase());
		}

		if (r.nextBoolean()) {
			contacts.put("stackoverflow",
					"https://stackoverflow.com/" + (profile.firstName + "-" + profile.lastName).toLowerCase());
		}

		insertedProfile.put("completed", Boolean.TRUE);
		return insertedProfile;
	}

	private static BasicDBList insertCertificates(MongoClient mongo, int certificatesCount,
			List<Certificate> certificates) throws IOException {
		Collections.shuffle(certificates);
		BasicDBList list = new BasicDBList();

		for (int i = 0; i < certificatesCount && i < certificates.size(); i++) {
			Certificate certificate = certificates.get(i);
			BasicDBObject insertedCertificate = new BasicDBObject();
			insertedCertificate.put("name", certificate.name);

			String uid = UUID.randomUUID().toString() + ".jpg";
			File photo = new File(MEDIA_DIR + "/certificates/" + uid);
			if (!photo.getParentFile().exists()) {
				photo.getParentFile().mkdirs();
			}
			String smallUid = uid.replace(".jpg", "-sm.jpg");
			Files.copy(Paths.get(certificate.largeImg), Paths.get(photo.getAbsolutePath()));

			insertedCertificate.put("largeUrl", "/media/certificates/" + uid);
			Thumbnails.of(photo).size(100, 100)
					.toFile(Paths.get(photo.getAbsolutePath().replace(".jpg", "-sm.jpg")).toFile());
			insertedCertificate.put("smallUrl", "/media/certificates/" + smallUid);

			list.add(insertedCertificate);
		}
		return list;
	}

	private static BasicDBList insertEducation(MongoClient mongo) {
		BasicDBList list = new BasicDBList();
		BasicDBObject education = new BasicDBObject();
		education.put("summary", "The specialist degree in Electronic Engineering");
		Date finish = randomFinishEducation();
		Date begin = addField(finish, Calendar.YEAR, -5, true);
		education.put("startYear",
				new java.util.Date(begin.getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear());
		if (finish.getTime() <= System.currentTimeMillis()) {
			education.put("endYear", new java.util.Date(finish.getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
					.getYear());
		}
		education.put("university", "Kharkiv National Technical University, Ukraine");
		education.put("faculty", "Computer Science");
		
		list.add(education);
		return list;
	}

	private static BasicDBList insertHobbies(MongoClient mongo) {
		BasicDBList list = new BasicDBList();
		List<String> hobbies = new ArrayList<>(Arrays.asList(HOBBIES));
		Collections.shuffle(hobbies);
		for (int i = 0; i < 5; i++) {
			list.add(new BasicDBObject("name", hobbies.remove(0)));
		}
		return list;
	}

	private static BasicDBList insertLanguages(MongoClient mongo) {
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

		BasicDBList list = new BasicDBList();
		
		for (String language : languages) {
			Map.Entry<Short, LanguageType> languageType = getRandomLanguageType();
			Map.Entry<Short, LanguageLevel> languageLevel = getRandomLanguageLevel();
			BasicDBObject lang = new BasicDBObject();
			
			lang.put("name", language);
			lang.put("type", languageType.getValue().name());
			lang.put("level", languageLevel.getValue().name());
			
			list.add(lang);

			if (languageType.getValue() != LanguageType.ALL) {
				lang = new BasicDBObject();
				lang.put("name", language);
				Map.Entry<Short, LanguageLevel> newLanguageLevel = getRandomLanguageLevel();
				while (newLanguageLevel.getValue() == languageLevel.getValue()) {
					newLanguageLevel = getRandomLanguageLevel();
				}
				lang.put("type", getReverseType(languageType).name());
				lang.put("level", newLanguageLevel.getValue().name());
				list.add(lang);
			}
		}
		return list;
	}

	private static BasicDBList insertExperience(MongoClient mongo, ProfileConfig profileConfig) {
		BasicDBList list = new BasicDBList();
		boolean currentCourse = r.nextBoolean();
		Date finish = addField(new Date(System.currentTimeMillis()), Calendar.MONTH, -(r.nextInt(3) + 1), false);
		
		for (Course course : profileConfig.courses) {
			BasicDBObject experience = new BasicDBObject();
			experience.put("position", course.name);
			experience.put("company", course.company);
			if (currentCourse) {
				experience.put("startDate", addField(new Date(System.currentTimeMillis()), Calendar.MONTH, -1, false).toLocalDate());
			} else {
				experience.put("startDate", addField(finish, Calendar.MONTH, -1, false).toLocalDate());
				experience.put("endDate", finish.toLocalDate());
				finish = addField(finish, Calendar.MONTH, -(r.nextInt(3) + 1), false);
			}
			experience.put("responsibilities", course.responsibilities);
			experience.put("demo", course.demo);
			experience.put("sourceCode", course.github);
			
			list.add(experience);
		}
		return list;
	}

	private static BasicDBList insertSkills(MongoClient mongo, ProfileConfig profileConfig) {
		BasicDBList list = new BasicDBList();
		Map<SkillCategory, Set<String>> skillMap = createSkillMap();
		
		for (Course course : profileConfig.courses) {
			for (SkillCategory category : skillMap.keySet()) {
				skillMap.get(category).addAll(course.skills.get(category));
			}
		}
		
		for (Map.Entry<SkillCategory, Set<String>> entry : skillMap.entrySet()) {
			if (!entry.getValue().isEmpty()) {
				BasicDBObject skill = new BasicDBObject();
				skill.put("category", entry.getKey().name());
				skill.put("value", StringUtils.join(entry.getValue().toArray(), ", "));

				list.add(skill);
			}
		}
		return list;
	}

	private static BasicDBList insertCourses(MongoClient mongo) {
		if (r.nextBoolean()) {
			BasicDBList list = new BasicDBList();
			BasicDBObject course = new BasicDBObject();
			course.put("name", "Java Advanced Course");
			course.put("school", "SourceIt");
			Date finish = randomFinishEducation();
			if (finish.getTime() <= System.currentTimeMillis()) {
				course.put("endDate", finish.toLocalDate());
			}
			list.add(course);
			return list;
		}
		return null;
	}

	private static LanguageType getReverseType(Entry<Short, LanguageType> languageType) {
		if (languageType.getValue() == LanguageType.SPOKEN) {
			return LanguageType.WRITTEN;
		}
		return LanguageType.SPOKEN;
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
			phone.append((char) code);
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

			return new Course("Java Base Course", "DevStude.net", "https://github.com/TODO",
					"Developing the web application 'blog' using free HTML template, downloaded from intenet."
							+ " Populating database by test data and uploading web project to OpenShift free hosting",
					"http://LINK_TO_DEMO_SITE", skills);
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

			return new Course("Java Advanced Course", "DevStude.net", "https://github.com/TODO",
					"Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet."
							+ " Populating database by test data and uploading web project to AWS EC2 instance",
					"http://LINK_TO_DEMO_SITE", skills);
		}
	}
}
