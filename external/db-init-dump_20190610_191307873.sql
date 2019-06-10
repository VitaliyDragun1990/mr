--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.13
-- Dumped by pg_dump version 9.6.0

-- Started on 2019-06-10 19:13:08

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12393)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2296 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 186 (class 1259 OID 16756)
-- Name: certificate; Type: TABLE; Schema: public; Owner: myresume
--

CREATE TABLE certificate (
    id bigint NOT NULL,
    profile_id bigint NOT NULL,
    name character varying(50) NOT NULL,
    large_url character varying(255) NOT NULL,
    small_url character varying(255) NOT NULL
);


ALTER TABLE certificate OWNER TO myresume;

--
-- TOC entry 195 (class 1259 OID 16867)
-- Name: certificate_seq; Type: SEQUENCE; Schema: public; Owner: myresume
--

CREATE SEQUENCE certificate_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE certificate_seq OWNER TO myresume;

--
-- TOC entry 187 (class 1259 OID 16769)
-- Name: course; Type: TABLE; Schema: public; Owner: myresume
--

CREATE TABLE course (
    id bigint NOT NULL,
    profile_id bigint NOT NULL,
    name character varying(60) NOT NULL,
    school character varying(60) NOT NULL,
    end_date date
);


ALTER TABLE course OWNER TO myresume;

--
-- TOC entry 196 (class 1259 OID 16869)
-- Name: course_seq; Type: SEQUENCE; Schema: public; Owner: myresume
--

CREATE SEQUENCE course_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE course_seq OWNER TO myresume;

--
-- TOC entry 188 (class 1259 OID 16779)
-- Name: education; Type: TABLE; Schema: public; Owner: myresume
--

CREATE TABLE education (
    id bigint NOT NULL,
    profile_id bigint NOT NULL,
    summary character varying(100) NOT NULL,
    start_year integer NOT NULL,
    end_year integer,
    university text NOT NULL,
    faculty character varying(255) NOT NULL
);


ALTER TABLE education OWNER TO myresume;

--
-- TOC entry 197 (class 1259 OID 16871)
-- Name: education_seq; Type: SEQUENCE; Schema: public; Owner: myresume
--

CREATE SEQUENCE education_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE education_seq OWNER TO myresume;

--
-- TOC entry 191 (class 1259 OID 16812)
-- Name: experience; Type: TABLE; Schema: public; Owner: myresume
--

CREATE TABLE experience (
    id bigint NOT NULL,
    profile_id bigint NOT NULL,
    "position" character varying(100) NOT NULL,
    company character varying(100) NOT NULL,
    start_date date NOT NULL,
    end_date date,
    responsibilities text NOT NULL,
    demo character varying(255),
    source_code character varying(255)
);


ALTER TABLE experience OWNER TO myresume;

--
-- TOC entry 200 (class 1259 OID 16878)
-- Name: experience_seq; Type: SEQUENCE; Schema: public; Owner: myresume
--

CREATE SEQUENCE experience_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE experience_seq OWNER TO myresume;

--
-- TOC entry 205 (class 1259 OID 16901)
-- Name: f_language; Type: TABLE; Schema: public; Owner: myresume
--

CREATE TABLE f_language (
    id bigint NOT NULL,
    profile_id bigint NOT NULL,
    name character varying(60) NOT NULL,
    type smallint NOT NULL,
    level smallint NOT NULL
);


ALTER TABLE f_language OWNER TO myresume;

--
-- TOC entry 204 (class 1259 OID 16894)
-- Name: f_language_level; Type: TABLE; Schema: public; Owner: myresume
--

CREATE TABLE f_language_level (
    id smallint NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE f_language_level OWNER TO myresume;

--
-- TOC entry 203 (class 1259 OID 16884)
-- Name: f_language_type; Type: TABLE; Schema: public; Owner: myresume
--

CREATE TABLE f_language_type (
    id smallint NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE f_language_type OWNER TO myresume;

--
-- TOC entry 207 (class 1259 OID 16934)
-- Name: f_skill; Type: TABLE; Schema: public; Owner: myresume
--

CREATE TABLE f_skill (
    id bigint NOT NULL,
    profile_id bigint NOT NULL,
    category smallint NOT NULL,
    value text NOT NULL
);


ALTER TABLE f_skill OWNER TO myresume;

--
-- TOC entry 206 (class 1259 OID 16927)
-- Name: f_skill_category; Type: TABLE; Schema: public; Owner: myresume
--

CREATE TABLE f_skill_category (
    id smallint NOT NULL,
    name character varying(60) NOT NULL
);


ALTER TABLE f_skill_category OWNER TO myresume;

--
-- TOC entry 189 (class 1259 OID 16792)
-- Name: hobby; Type: TABLE; Schema: public; Owner: myresume
--

CREATE TABLE hobby (
    id bigint NOT NULL,
    profile_id bigint NOT NULL,
    name character varying(30) NOT NULL
);


ALTER TABLE hobby OWNER TO myresume;

--
-- TOC entry 198 (class 1259 OID 16873)
-- Name: hobby_seq; Type: SEQUENCE; Schema: public; Owner: myresume
--

CREATE SEQUENCE hobby_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hobby_seq OWNER TO myresume;

--
-- TOC entry 190 (class 1259 OID 16802)
-- Name: language; Type: TABLE; Schema: public; Owner: myresume
--

CREATE TABLE language (
    id bigint NOT NULL,
    profile_id bigint NOT NULL,
    name character varying(30) NOT NULL,
    level character varying(18) NOT NULL,
    type character varying(10) NOT NULL
);


ALTER TABLE language OWNER TO myresume;

--
-- TOC entry 2297 (class 0 OID 0)
-- Dependencies: 190
-- Name: COLUMN language.level; Type: COMMENT; Schema: public; Owner: myresume
--

COMMENT ON COLUMN language.level IS 'beginner, intermediate, etc';


--
-- TOC entry 2298 (class 0 OID 0)
-- Dependencies: 190
-- Name: COLUMN language.type; Type: COMMENT; Schema: public; Owner: myresume
--

COMMENT ON COLUMN language.type IS 'written/colloquial';


--
-- TOC entry 199 (class 1259 OID 16875)
-- Name: language_seq; Type: SEQUENCE; Schema: public; Owner: myresume
--

CREATE SEQUENCE language_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE language_seq OWNER TO myresume;

--
-- TOC entry 185 (class 1259 OID 16741)
-- Name: profile; Type: TABLE; Schema: public; Owner: myresume
--

CREATE TABLE profile (
    id bigint NOT NULL,
    uid character varying(100) NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    birth_day date,
    phone character varying(20),
    email character varying(100),
    country character varying(60),
    city character varying(100),
    objective text,
    summary text,
    large_photo character varying(255),
    small_photo character varying(255),
    info text,
    password character varying(255) NOT NULL,
    completed boolean NOT NULL,
    created timestamp(0) without time zone DEFAULT now() NOT NULL,
    skype character varying(80),
    vkontakte character varying(255),
    facebook character varying(255),
    linkedin character varying(255),
    github character varying(255),
    stackoverflow character varying(255)
);


ALTER TABLE profile OWNER TO myresume;

--
-- TOC entry 193 (class 1259 OID 16838)
-- Name: profile_restore; Type: TABLE; Schema: public; Owner: myresume
--

CREATE TABLE profile_restore (
    id bigint NOT NULL,
    token character varying(255) NOT NULL
);


ALTER TABLE profile_restore OWNER TO myresume;

--
-- TOC entry 201 (class 1259 OID 16880)
-- Name: profile_seq; Type: SEQUENCE; Schema: public; Owner: myresume
--

CREATE SEQUENCE profile_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE profile_seq OWNER TO myresume;

--
-- TOC entry 192 (class 1259 OID 16825)
-- Name: skill; Type: TABLE; Schema: public; Owner: myresume
--

CREATE TABLE skill (
    id bigint NOT NULL,
    profile_id bigint NOT NULL,
    category character varying(50) NOT NULL,
    value text NOT NULL
);


ALTER TABLE skill OWNER TO myresume;

--
-- TOC entry 194 (class 1259 OID 16860)
-- Name: skill_category; Type: TABLE; Schema: public; Owner: myresume
--

CREATE TABLE skill_category (
    id bigint NOT NULL,
    category character varying(50) NOT NULL
);


ALTER TABLE skill_category OWNER TO myresume;

--
-- TOC entry 202 (class 1259 OID 16882)
-- Name: skill_seq; Type: SEQUENCE; Schema: public; Owner: myresume
--

CREATE SEQUENCE skill_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE skill_seq OWNER TO myresume;

--
-- TOC entry 2268 (class 0 OID 16756)
-- Dependencies: 186
-- Data for Name: certificate; Type: TABLE DATA; Schema: public; Owner: myresume
--

COPY certificate (id, profile_id, name, large_url, small_url) FROM stdin;
1	2	Jee certificate.jpg	/media/certificates/ba23deeb-4f41-4739-b982-2b779083881d.jpg	/media/certificates/ba23deeb-4f41-4739-b982-2b779083881d-sm.jpg
2	2	Mongo certificate.jpg	/media/certificates/f0563f3c-ad8e-42f4-9fa0-d2003a7346e5.jpg	/media/certificates/f0563f3c-ad8e-42f4-9fa0-d2003a7346e5-sm.jpg
3	3	Mongo certificate.jpg	/media/certificates/6984fb50-e638-415d-8121-f3f43d8c22d7.jpg	/media/certificates/6984fb50-e638-415d-8121-f3f43d8c22d7-sm.jpg
4	6	Mongo certificate.jpg	/media/certificates/08f9f6bf-c393-4569-ab0f-df405fc2c73b.jpg	/media/certificates/08f9f6bf-c393-4569-ab0f-df405fc2c73b-sm.jpg
5	7	Jee certificate.jpg	/media/certificates/4e0b87b8-0043-4e3c-b853-ade640b4636d.jpg	/media/certificates/4e0b87b8-0043-4e3c-b853-ade640b4636d-sm.jpg
6	8	Mongo certificate.jpg	/media/certificates/74593cd6-52a9-4e33-84b8-20fc4c7918a0.jpg	/media/certificates/74593cd6-52a9-4e33-84b8-20fc4c7918a0-sm.jpg
7	9	Mongo certificate.jpg	/media/certificates/f79323aa-ff4f-43fe-a6ca-ce02a55b81cc.jpg	/media/certificates/f79323aa-ff4f-43fe-a6ca-ce02a55b81cc-sm.jpg
8	10	Mongo certificate.jpg	/media/certificates/8ab2e7a7-2003-4454-8702-fa8bc79e9d82.jpg	/media/certificates/8ab2e7a7-2003-4454-8702-fa8bc79e9d82-sm.jpg
9	10	Jee certificate.jpg	/media/certificates/3e61f51e-e9f8-4edb-987a-ef86fc1d9987.jpg	/media/certificates/3e61f51e-e9f8-4edb-987a-ef86fc1d9987-sm.jpg
10	12	Jee certificate.jpg	/media/certificates/6a4967ce-7348-43d9-91dc-3d84c6f300db.jpg	/media/certificates/6a4967ce-7348-43d9-91dc-3d84c6f300db-sm.jpg
11	12	Mongo certificate.jpg	/media/certificates/c7db87b7-eaab-4154-ab4a-dc2495ca73d4.jpg	/media/certificates/c7db87b7-eaab-4154-ab4a-dc2495ca73d4-sm.jpg
12	13	Mongo certificate.jpg	/media/certificates/94525565-8a3e-4128-a605-cc081180074f.jpg	/media/certificates/94525565-8a3e-4128-a605-cc081180074f-sm.jpg
13	14	Mongo certificate.jpg	/media/certificates/fe947da6-861c-4c95-a0b9-0e4c650b6834.jpg	/media/certificates/fe947da6-861c-4c95-a0b9-0e4c650b6834-sm.jpg
14	14	Jee certificate.jpg	/media/certificates/9fb73556-c4bd-493b-9b9b-2575f65bb617.jpg	/media/certificates/9fb73556-c4bd-493b-9b9b-2575f65bb617-sm.jpg
15	15	Jee certificate.jpg	/media/certificates/e03e16c8-a615-4887-942b-b32801760afc.jpg	/media/certificates/e03e16c8-a615-4887-942b-b32801760afc-sm.jpg
16	16	Mongo certificate.jpg	/media/certificates/14236a1a-6354-4c76-ad76-351079ecb30e.jpg	/media/certificates/14236a1a-6354-4c76-ad76-351079ecb30e-sm.jpg
17	18	Jee certificate.jpg	/media/certificates/90674553-3103-42e5-9198-09fa29782999.jpg	/media/certificates/90674553-3103-42e5-9198-09fa29782999-sm.jpg
18	18	Mongo certificate.jpg	/media/certificates/6ecb3d05-ab25-4b43-a31d-d316552e7e51.jpg	/media/certificates/6ecb3d05-ab25-4b43-a31d-d316552e7e51-sm.jpg
19	20	Mongo certificate.jpg	/media/certificates/ca752d90-4a69-44bd-8518-1f4894eb3c82.jpg	/media/certificates/ca752d90-4a69-44bd-8518-1f4894eb3c82-sm.jpg
20	21	Jee certificate.jpg	/media/certificates/833622ee-9b71-439a-866d-35769e451dce.jpg	/media/certificates/833622ee-9b71-439a-866d-35769e451dce-sm.jpg
21	22	Mongo certificate.jpg	/media/certificates/58f4eb26-5ad1-4c89-8600-daab5cb4b3f3.jpg	/media/certificates/58f4eb26-5ad1-4c89-8600-daab5cb4b3f3-sm.jpg
22	23	Mongo certificate.jpg	/media/certificates/ec76fded-3a64-458c-b5c8-7ec432949b28.jpg	/media/certificates/ec76fded-3a64-458c-b5c8-7ec432949b28-sm.jpg
\.


--
-- TOC entry 2299 (class 0 OID 0)
-- Dependencies: 195
-- Name: certificate_seq; Type: SEQUENCE SET; Schema: public; Owner: myresume
--

SELECT pg_catalog.setval('certificate_seq', 22, true);


--
-- TOC entry 2269 (class 0 OID 16769)
-- Dependencies: 187
-- Data for Name: course; Type: TABLE DATA; Schema: public; Owner: myresume
--

COPY course (id, profile_id, name, school, end_date) FROM stdin;
1	1	Java Advanced Course	SourceIt	2017-06-30
2	7	Java Advanced Course	SourceIt	2014-06-30
3	8	Java Advanced Course	SourceIt	\N
4	11	Java Advanced Course	SourceIt	\N
5	12	Java Advanced Course	SourceIt	2018-06-30
6	13	Java Advanced Course	SourceIt	2015-06-30
7	17	Java Advanced Course	SourceIt	2014-06-30
8	18	Java Advanced Course	SourceIt	2011-06-30
9	19	Java Advanced Course	SourceIt	2013-06-30
10	21	Java Advanced Course	SourceIt	2013-06-30
11	22	Java Advanced Course	SourceIt	2012-06-30
12	24	Java Advanced Course	SourceIt	2012-06-30
\.


--
-- TOC entry 2300 (class 0 OID 0)
-- Dependencies: 196
-- Name: course_seq; Type: SEQUENCE SET; Schema: public; Owner: myresume
--

SELECT pg_catalog.setval('course_seq', 12, true);


--
-- TOC entry 2270 (class 0 OID 16779)
-- Dependencies: 188
-- Data for Name: education; Type: TABLE DATA; Schema: public; Owner: myresume
--

COPY education (id, profile_id, summary, start_year, end_year, university, faculty) FROM stdin;
1	1	The specialist degree in Electronic Engineering	2013	2018	Kharkiv National Technical University, Ukraine	Computer Science
2	2	The specialist degree in Electronic Engineering	2009	2014	Kharkiv National Technical University, Ukraine	Computer Science
3	3	The specialist degree in Electronic Engineering	2014	\N	Kharkiv National Technical University, Ukraine	Computer Science
4	4	The specialist degree in Electronic Engineering	2009	2014	Kharkiv National Technical University, Ukraine	Computer Science
5	5	The specialist degree in Electronic Engineering	2015	\N	Kharkiv National Technical University, Ukraine	Computer Science
6	6	The specialist degree in Electronic Engineering	2006	2011	Kharkiv National Technical University, Ukraine	Computer Science
7	7	The specialist degree in Electronic Engineering	2007	2012	Kharkiv National Technical University, Ukraine	Computer Science
8	8	The specialist degree in Electronic Engineering	2014	\N	Kharkiv National Technical University, Ukraine	Computer Science
9	9	The specialist degree in Electronic Engineering	2014	\N	Kharkiv National Technical University, Ukraine	Computer Science
10	10	The specialist degree in Electronic Engineering	2012	2017	Kharkiv National Technical University, Ukraine	Computer Science
11	11	The specialist degree in Electronic Engineering	2016	\N	Kharkiv National Technical University, Ukraine	Computer Science
12	12	The specialist degree in Electronic Engineering	2014	\N	Kharkiv National Technical University, Ukraine	Computer Science
13	13	The specialist degree in Electronic Engineering	2010	2015	Kharkiv National Technical University, Ukraine	Computer Science
14	14	The specialist degree in Electronic Engineering	2012	2017	Kharkiv National Technical University, Ukraine	Computer Science
15	15	The specialist degree in Electronic Engineering	2008	2013	Kharkiv National Technical University, Ukraine	Computer Science
16	16	The specialist degree in Electronic Engineering	2007	2012	Kharkiv National Technical University, Ukraine	Computer Science
17	17	The specialist degree in Electronic Engineering	2007	2012	Kharkiv National Technical University, Ukraine	Computer Science
18	18	The specialist degree in Electronic Engineering	2007	2012	Kharkiv National Technical University, Ukraine	Computer Science
19	19	The specialist degree in Electronic Engineering	2008	2013	Kharkiv National Technical University, Ukraine	Computer Science
20	20	The specialist degree in Electronic Engineering	2016	\N	Kharkiv National Technical University, Ukraine	Computer Science
21	21	The specialist degree in Electronic Engineering	2006	2011	Kharkiv National Technical University, Ukraine	Computer Science
22	22	The specialist degree in Electronic Engineering	2008	2013	Kharkiv National Technical University, Ukraine	Computer Science
23	23	The specialist degree in Electronic Engineering	2008	2013	Kharkiv National Technical University, Ukraine	Computer Science
24	24	The specialist degree in Electronic Engineering	2009	2014	Kharkiv National Technical University, Ukraine	Computer Science
\.


--
-- TOC entry 2301 (class 0 OID 0)
-- Dependencies: 197
-- Name: education_seq; Type: SEQUENCE SET; Schema: public; Owner: myresume
--

SELECT pg_catalog.setval('education_seq', 24, true);


--
-- TOC entry 2273 (class 0 OID 16812)
-- Dependencies: 191
-- Data for Name: experience; Type: TABLE DATA; Schema: public; Owner: myresume
--

COPY experience (id, profile_id, "position", company, start_date, end_date, responsibilities, demo, source_code) FROM stdin;
1	1	Java Advanced Course	DevStude.net	2019-03-10	2019-04-10	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
2	2	Java Advanced Course	DevStude.net	2019-05-10	\N	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
3	2	Java Base Course	DevStude.net	2019-05-10	\N	Developing the web application 'blog' using free HTML template, downloaded from intenet. Populating database by test data and uploading web project to OpenShift free hosting	http://LINK_TO_DEMO_SITE	https://github.com/TODO
4	2	Java Core Course	DevStude.net	2019-05-10	\N	Developing the java console application which imports XML, JSON, Properties, CVS to Db via JDBC	\N	\N
5	3	Java Advanced Course	DevStude.net	2019-05-10	\N	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
6	3	Java Base Course	DevStude.net	2019-05-10	\N	Developing the web application 'blog' using free HTML template, downloaded from intenet. Populating database by test data and uploading web project to OpenShift free hosting	http://LINK_TO_DEMO_SITE	https://github.com/TODO
7	4	Java Advanced Course	DevStude.net	2019-05-10	\N	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
8	5	Java Core Course	DevStude.net	2019-05-10	\N	Developing the java console application which imports XML, JSON, Properties, CVS to Db via JDBC	\N	\N
9	6	Java Advanced Course	DevStude.net	2019-05-10	\N	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
10	6	Java Base Course	DevStude.net	2019-05-10	\N	Developing the web application 'blog' using free HTML template, downloaded from intenet. Populating database by test data and uploading web project to OpenShift free hosting	http://LINK_TO_DEMO_SITE	https://github.com/TODO
11	7	Java Advanced Course	DevStude.net	2019-05-10	\N	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
12	8	Java Advanced Course	DevStude.net	2019-05-10	\N	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
13	8	Java Base Course	DevStude.net	2019-05-10	\N	Developing the web application 'blog' using free HTML template, downloaded from intenet. Populating database by test data and uploading web project to OpenShift free hosting	http://LINK_TO_DEMO_SITE	https://github.com/TODO
14	9	Java Advanced Course	DevStude.net	2019-05-10	\N	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
15	9	Java Base Course	DevStude.net	2019-05-10	\N	Developing the web application 'blog' using free HTML template, downloaded from intenet. Populating database by test data and uploading web project to OpenShift free hosting	http://LINK_TO_DEMO_SITE	https://github.com/TODO
16	10	Java Advanced Course	DevStude.net	2019-05-10	\N	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
17	10	Java Base Course	DevStude.net	2019-05-10	\N	Developing the web application 'blog' using free HTML template, downloaded from intenet. Populating database by test data and uploading web project to OpenShift free hosting	http://LINK_TO_DEMO_SITE	https://github.com/TODO
18	10	Java Core Course	DevStude.net	2019-05-10	\N	Developing the java console application which imports XML, JSON, Properties, CVS to Db via JDBC	\N	\N
19	11	Java Base Course	DevStude.net	2019-04-10	2019-05-10	Developing the web application 'blog' using free HTML template, downloaded from intenet. Populating database by test data and uploading web project to OpenShift free hosting	http://LINK_TO_DEMO_SITE	https://github.com/TODO
20	12	Java Advanced Course	DevStude.net	2019-05-10	\N	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
21	12	Java Base Course	DevStude.net	2019-05-10	\N	Developing the web application 'blog' using free HTML template, downloaded from intenet. Populating database by test data and uploading web project to OpenShift free hosting	http://LINK_TO_DEMO_SITE	https://github.com/TODO
22	13	Java Advanced Course	DevStude.net	2019-05-10	\N	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
23	14	Java Advanced Course	DevStude.net	2019-05-10	\N	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
24	14	Java Base Course	DevStude.net	2019-05-10	\N	Developing the web application 'blog' using free HTML template, downloaded from intenet. Populating database by test data and uploading web project to OpenShift free hosting	http://LINK_TO_DEMO_SITE	https://github.com/TODO
25	15	Java Advanced Course	DevStude.net	2019-04-10	2019-05-10	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
26	15	Java Base Course	DevStude.net	2019-02-10	2019-03-10	Developing the web application 'blog' using free HTML template, downloaded from intenet. Populating database by test data and uploading web project to OpenShift free hosting	http://LINK_TO_DEMO_SITE	https://github.com/TODO
27	16	Java Advanced Course	DevStude.net	2019-05-10	\N	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
28	17	Java Base Course	DevStude.net	2019-05-10	\N	Developing the web application 'blog' using free HTML template, downloaded from intenet. Populating database by test data and uploading web project to OpenShift free hosting	http://LINK_TO_DEMO_SITE	https://github.com/TODO
29	18	Java Advanced Course	DevStude.net	2019-05-10	\N	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
30	18	Java Base Course	DevStude.net	2019-05-10	\N	Developing the web application 'blog' using free HTML template, downloaded from intenet. Populating database by test data and uploading web project to OpenShift free hosting	http://LINK_TO_DEMO_SITE	https://github.com/TODO
31	19	Java Core Course	DevStude.net	2019-04-10	2019-05-10	Developing the java console application which imports XML, JSON, Properties, CVS to Db via JDBC	\N	\N
32	20	Java Advanced Course	DevStude.net	2019-05-10	\N	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
33	20	Java Base Course	DevStude.net	2019-05-10	\N	Developing the web application 'blog' using free HTML template, downloaded from intenet. Populating database by test data and uploading web project to OpenShift free hosting	http://LINK_TO_DEMO_SITE	https://github.com/TODO
34	21	Java Advanced Course	DevStude.net	2019-05-10	\N	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
35	21	Java Base Course	DevStude.net	2019-05-10	\N	Developing the web application 'blog' using free HTML template, downloaded from intenet. Populating database by test data and uploading web project to OpenShift free hosting	http://LINK_TO_DEMO_SITE	https://github.com/TODO
36	22	Java Advanced Course	DevStude.net	2019-05-10	\N	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
37	22	Java Base Course	DevStude.net	2019-05-10	\N	Developing the web application 'blog' using free HTML template, downloaded from intenet. Populating database by test data and uploading web project to OpenShift free hosting	http://LINK_TO_DEMO_SITE	https://github.com/TODO
38	23	Java Advanced Course	DevStude.net	2019-04-10	2019-05-10	Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance	http://LINK_TO_DEMO_SITE	https://github.com/TODO
39	23	Java Base Course	DevStude.net	2019-02-10	2019-03-10	Developing the web application 'blog' using free HTML template, downloaded from intenet. Populating database by test data and uploading web project to OpenShift free hosting	http://LINK_TO_DEMO_SITE	https://github.com/TODO
40	24	Java Core Course	DevStude.net	2019-04-10	2019-05-10	Developing the java console application which imports XML, JSON, Properties, CVS to Db via JDBC	\N	\N
\.


--
-- TOC entry 2302 (class 0 OID 0)
-- Dependencies: 200
-- Name: experience_seq; Type: SEQUENCE SET; Schema: public; Owner: myresume
--

SELECT pg_catalog.setval('experience_seq', 40, true);


--
-- TOC entry 2287 (class 0 OID 16901)
-- Dependencies: 205
-- Data for Name: f_language; Type: TABLE DATA; Schema: public; Owner: myresume
--

COPY f_language (id, profile_id, name, type, level) FROM stdin;
1	1	English	1	2
2	1	English	2	1
3	1	Spanish	2	0
4	1	Spanish	1	2
5	2	English	1	1
6	2	English	2	2
7	3	English	1	1
8	3	English	2	2
9	3	Spanish	2	0
10	3	Spanish	1	1
11	4	English	2	0
12	4	English	1	2
13	4	Spanish	1	1
14	4	Spanish	2	2
15	5	English	1	2
16	5	English	2	0
17	5	French	1	0
18	5	French	2	1
19	6	English	1	2
20	6	English	2	1
21	7	English	0	0
22	8	English	1	1
23	8	English	2	0
24	9	English	1	2
25	9	English	2	1
26	9	Italian	2	0
27	9	Italian	1	2
28	10	English	0	1
29	10	Italian	1	0
30	10	Italian	2	1
31	11	English	2	1
32	11	English	1	0
33	11	French	2	1
34	11	French	1	2
35	12	English	1	1
36	12	English	2	2
37	13	English	1	1
38	13	English	2	2
39	14	English	0	2
40	14	German	1	2
41	14	German	2	1
42	15	English	1	2
43	15	English	2	1
44	16	English	1	1
45	16	English	2	2
46	17	English	2	0
47	17	English	1	2
48	17	Italian	1	2
49	17	Italian	2	0
50	18	English	0	1
51	19	English	1	0
52	19	English	2	1
53	19	French	2	1
54	19	French	1	0
55	20	English	2	2
56	20	English	1	1
57	20	German	0	0
58	21	English	0	1
59	22	English	0	2
60	22	French	1	1
61	22	French	2	2
62	23	English	1	2
63	23	English	2	0
64	23	French	0	2
65	24	English	0	2
66	24	Italian	0	1
\.


--
-- TOC entry 2286 (class 0 OID 16894)
-- Dependencies: 204
-- Data for Name: f_language_level; Type: TABLE DATA; Schema: public; Owner: myresume
--

COPY f_language_level (id, name) FROM stdin;
0	beginner
1	elementary
2	pre_intermediate
3	intermediate
4	upper_intermediate
5	advanced
6	proficiency
\.


--
-- TOC entry 2285 (class 0 OID 16884)
-- Dependencies: 203
-- Data for Name: f_language_type; Type: TABLE DATA; Schema: public; Owner: myresume
--

COPY f_language_type (id, name) FROM stdin;
0	all
1	spoken
2	written
\.


--
-- TOC entry 2289 (class 0 OID 16934)
-- Dependencies: 207
-- Data for Name: f_skill; Type: TABLE DATA; Schema: public; Owner: myresume
--

COPY f_skill (id, profile_id, category, value) FROM stdin;
1	1	0	Java,SQL,PLSQL
2	1	1	Postgresql
3	1	2	HTML,CSS,JS,Boostrap,JQuery
4	1	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API
5	1	4	Eclipse for JEE Developer
6	1	5	Git,GitHub
7	1	6	Tomcat,Nginx
8	1	7	Maven
9	1	8	AWS
10	2	0	Java,SQL,PLSQL
11	2	1	Postgresql,Mysql
12	2	2	HTML,CSS,JS,Boostrap,JQuery
13	2	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API,Java Servlets,JDBC,Google+ Social API,Java Threads,IO,JAXB,GSON
14	2	4	Eclipse for JEE Developer
15	2	5	Git,GitHub
16	2	6	Tomcat,Nginx
17	2	7	Maven
18	2	8	AWS,OpenShift
19	3	0	Java,SQL,PLSQL
20	3	1	Postgresql
21	3	2	HTML,CSS,JS,Boostrap,JQuery
22	3	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API,Java Servlets,JDBC,Google+ Social API
23	3	4	Eclipse for JEE Developer
24	3	5	Git,GitHub
25	3	6	Tomcat,Nginx
26	3	7	Maven
27	3	8	AWS,OpenShift
28	4	0	Java,SQL,PLSQL
29	4	1	Postgresql
30	4	2	HTML,CSS,JS,Boostrap,JQuery
31	4	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API
32	4	4	Eclipse for JEE Developer
33	4	5	Git,GitHub
34	4	6	Tomcat,Nginx
35	4	7	Maven
36	4	8	AWS
37	5	0	Java
38	5	1	Mysql
39	5	3	Java Threads,IO,JAXB,GSON
40	5	4	Eclipse for JEE Developer
41	5	5	Git,GitHub
42	5	7	Maven
43	6	0	Java,SQL,PLSQL
44	6	1	Postgresql
45	6	2	HTML,CSS,JS,Boostrap,JQuery
46	6	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API,Java Servlets,JDBC,Google+ Social API
47	6	4	Eclipse for JEE Developer
48	6	5	Git,GitHub
49	6	6	Tomcat,Nginx
50	6	7	Maven
51	6	8	AWS,OpenShift
52	7	0	Java,SQL,PLSQL
53	7	1	Postgresql
54	7	2	HTML,CSS,JS,Boostrap,JQuery
55	7	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API
56	7	4	Eclipse for JEE Developer
57	7	5	Git,GitHub
58	7	6	Tomcat,Nginx
59	7	7	Maven
60	7	8	AWS
61	8	0	Java,SQL,PLSQL
62	8	1	Postgresql
63	8	2	HTML,CSS,JS,Boostrap,JQuery
64	8	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API,Java Servlets,JDBC,Google+ Social API
65	8	4	Eclipse for JEE Developer
66	8	5	Git,GitHub
67	8	6	Tomcat,Nginx
68	8	7	Maven
69	8	8	AWS,OpenShift
70	9	0	Java,SQL,PLSQL
71	9	1	Postgresql
72	9	2	HTML,CSS,JS,Boostrap,JQuery
73	9	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API,Java Servlets,JDBC,Google+ Social API
74	9	4	Eclipse for JEE Developer
75	9	5	Git,GitHub
76	9	6	Tomcat,Nginx
77	9	7	Maven
78	9	8	AWS,OpenShift
79	10	0	Java,SQL,PLSQL
80	10	1	Postgresql,Mysql
81	10	2	HTML,CSS,JS,Boostrap,JQuery
82	10	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API,Java Servlets,JDBC,Google+ Social API,Java Threads,IO,JAXB,GSON
83	10	4	Eclipse for JEE Developer
84	10	5	Git,GitHub
85	10	6	Tomcat,Nginx
86	10	7	Maven
87	10	8	AWS,OpenShift
88	11	0	Java,SQL
89	11	1	Postgresql
90	11	2	HTML,CSS,JS,Boostrap,JQuery
91	11	3	Java Servlets,Logback,JSTL,JDBC,Apache Commons,Google+ Social API
92	11	4	Eclipse for JEE Developer
93	11	5	Git,GitHub
94	11	6	Tomcat
95	11	7	Maven
96	11	8	OpenShift
97	12	0	Java,SQL,PLSQL
98	12	1	Postgresql
99	12	2	HTML,CSS,JS,Boostrap,JQuery
100	12	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API,Java Servlets,JDBC,Google+ Social API
101	12	4	Eclipse for JEE Developer
102	12	5	Git,GitHub
103	12	6	Tomcat,Nginx
104	12	7	Maven
105	12	8	AWS,OpenShift
106	13	0	Java,SQL,PLSQL
107	13	1	Postgresql
108	13	2	HTML,CSS,JS,Boostrap,JQuery
109	13	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API
110	13	4	Eclipse for JEE Developer
111	13	5	Git,GitHub
112	13	6	Tomcat,Nginx
113	13	7	Maven
114	13	8	AWS
115	14	0	Java,SQL,PLSQL
116	14	1	Postgresql
117	14	2	HTML,CSS,JS,Boostrap,JQuery
118	14	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API,Java Servlets,JDBC,Google+ Social API
119	14	4	Eclipse for JEE Developer
120	14	5	Git,GitHub
121	14	6	Tomcat,Nginx
122	14	7	Maven
123	14	8	AWS,OpenShift
124	15	0	Java,SQL,PLSQL
125	15	1	Postgresql
126	15	2	HTML,CSS,JS,Boostrap,JQuery
127	15	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API,Java Servlets,JDBC,Google+ Social API
128	15	4	Eclipse for JEE Developer
129	15	5	Git,GitHub
130	15	6	Tomcat,Nginx
131	15	7	Maven
132	15	8	AWS,OpenShift
133	16	0	Java,SQL,PLSQL
134	16	1	Postgresql
135	16	2	HTML,CSS,JS,Boostrap,JQuery
136	16	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API
137	16	4	Eclipse for JEE Developer
138	16	5	Git,GitHub
139	16	6	Tomcat,Nginx
140	16	7	Maven
141	16	8	AWS
142	17	0	Java,SQL
143	17	1	Postgresql
144	17	2	HTML,CSS,JS,Boostrap,JQuery
145	17	3	Java Servlets,Logback,JSTL,JDBC,Apache Commons,Google+ Social API
146	17	4	Eclipse for JEE Developer
147	17	5	Git,GitHub
148	17	6	Tomcat
149	17	7	Maven
150	17	8	OpenShift
151	18	0	Java,SQL,PLSQL
152	18	1	Postgresql
153	18	2	HTML,CSS,JS,Boostrap,JQuery
154	18	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API,Java Servlets,JDBC,Google+ Social API
155	18	4	Eclipse for JEE Developer
156	18	5	Git,GitHub
157	18	6	Tomcat,Nginx
158	18	7	Maven
159	18	8	AWS,OpenShift
160	19	0	Java
161	19	1	Mysql
162	19	3	Java Threads,IO,JAXB,GSON
163	19	4	Eclipse for JEE Developer
164	19	5	Git,GitHub
165	19	7	Maven
166	20	0	Java,SQL,PLSQL
167	20	1	Postgresql
168	20	2	HTML,CSS,JS,Boostrap,JQuery
169	20	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API,Java Servlets,JDBC,Google+ Social API
170	20	4	Eclipse for JEE Developer
171	20	5	Git,GitHub
172	20	6	Tomcat,Nginx
173	20	7	Maven
174	20	8	AWS,OpenShift
175	21	0	Java,SQL,PLSQL
176	21	1	Postgresql
177	21	2	HTML,CSS,JS,Boostrap,JQuery
178	21	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API,Java Servlets,JDBC,Google+ Social API
179	21	4	Eclipse for JEE Developer
180	21	5	Git,GitHub
181	21	6	Tomcat,Nginx
182	21	7	Maven
183	21	8	AWS,OpenShift
184	22	0	Java,SQL,PLSQL
185	22	1	Postgresql
186	22	2	HTML,CSS,JS,Boostrap,JQuery
187	22	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API,Java Servlets,JDBC,Google+ Social API
188	22	4	Eclipse for JEE Developer
189	22	5	Git,GitHub
190	22	6	Tomcat,Nginx
191	22	7	Maven
192	22	8	AWS,OpenShift
193	23	0	Java,SQL,PLSQL
194	23	1	Postgresql
195	23	2	HTML,CSS,JS,Boostrap,JQuery
196	23	3	Spring MVC,Logback,JSP,JSTL,SPring Data JPA,Apache Commons,Spring Security,Hibernate JPA,Facebook Social API,Java Servlets,JDBC,Google+ Social API
197	23	4	Eclipse for JEE Developer
198	23	5	Git,GitHub
199	23	6	Tomcat,Nginx
200	23	7	Maven
201	23	8	AWS,OpenShift
202	24	0	Java
203	24	1	Mysql
204	24	3	Java Threads,IO,JAXB,GSON
205	24	4	Eclipse for JEE Developer
206	24	5	Git,GitHub
207	24	7	Maven
\.


--
-- TOC entry 2288 (class 0 OID 16927)
-- Dependencies: 206
-- Data for Name: f_skill_category; Type: TABLE DATA; Schema: public; Owner: myresume
--

COPY f_skill_category (id, name) FROM stdin;
0	Languages
1	DBMS
2	Frontend
3	Backend
4	IDE
5	CVS
6	Web Servers
7	Build systems
8	Cloud
\.


--
-- TOC entry 2271 (class 0 OID 16792)
-- Dependencies: 189
-- Data for Name: hobby; Type: TABLE DATA; Schema: public; Owner: myresume
--

COPY hobby (id, profile_id, name) FROM stdin;
1	1	Cycling
2	1	Photo
3	1	Skateboarding
4	1	Skiing
5	1	Football
6	2	Foreign lang
7	2	Kayak slalom
8	2	Games of chance
9	2	Painting
10	2	Cricket
11	3	Shopping
12	3	Boxing
13	3	Football
14	3	Cooking
15	3	Rowing
16	4	Computer games
17	4	Baseball
18	4	Automobiles
19	4	Cycling
20	4	Football
21	5	Football
22	5	Camping
23	5	Boxing
24	5	Foreign lang
25	5	Pubs
26	6	Kayak slalom
27	6	Skiing
28	6	Computer games
29	6	Table tennis
30	6	Cycling
31	7	Codding
32	7	Handball
33	7	Traveling
34	7	Archery
35	7	Table tennis
36	8	Badminton
37	8	Kayak slalom
38	8	Cricket
39	8	Tennis
40	8	Book reading
41	9	Foreign lang
42	9	Volleyball
43	9	Cooking
44	9	Authorship
45	9	Camping
46	10	Boxing
47	10	Automobiles
48	10	Darts
49	10	Pubs
50	10	Weightlifting
51	11	Rowing
52	11	Cycling
53	11	Kayak slalom
54	11	Games of chance
55	11	Table tennis
56	12	Photo
57	12	Singing
58	12	Book reading
59	12	Computer games
60	12	Ice hockey
61	13	Table tennis
62	13	Tennis
63	13	Basketball
64	13	Traveling
65	13	Computer games
66	14	Disco
67	14	Traveling
68	14	Boxing
69	14	Authorship
70	14	Bowling
71	15	Skating
72	15	Traveling
73	15	Computer games
74	15	Disco
75	15	Animals
76	16	Weightlifting
77	16	Cycling
78	16	Football
79	16	Billiards
80	16	Book reading
81	17	Codding
82	17	Baseball
83	17	Rowing
84	17	Darts
85	17	Skiing
86	18	Swimming
87	18	Collecting
88	18	Fishing
89	18	Rowing
90	18	Automobiles
91	19	Weightlifting
92	19	Skating
93	19	Table tennis
94	19	Games of chance
95	19	Swimming
96	20	Book reading
97	20	Cricket
98	20	Ice hockey
99	20	Golf
100	20	Shooting
101	21	Bowling
102	21	Pubs
103	21	Badminton
104	21	Skating
105	21	Weightlifting
106	22	Pubs
107	22	Games of chance
108	22	Baseball
109	22	Cooking
110	22	Automobiles
111	23	Camping
112	23	Rowing
113	23	Swimming
114	23	Weightlifting
115	23	Badminton
116	24	Painting
117	24	Roller skating
118	24	Archery
119	24	Diving
120	24	Tennis
\.


--
-- TOC entry 2303 (class 0 OID 0)
-- Dependencies: 198
-- Name: hobby_seq; Type: SEQUENCE SET; Schema: public; Owner: myresume
--

SELECT pg_catalog.setval('hobby_seq', 120, true);


--
-- TOC entry 2272 (class 0 OID 16802)
-- Dependencies: 190
-- Data for Name: language; Type: TABLE DATA; Schema: public; Owner: myresume
--

COPY language (id, profile_id, name, level, type) FROM stdin;
\.


--
-- TOC entry 2304 (class 0 OID 0)
-- Dependencies: 199
-- Name: language_seq; Type: SEQUENCE SET; Schema: public; Owner: myresume
--

SELECT pg_catalog.setval('language_seq', 66, true);


--
-- TOC entry 2267 (class 0 OID 16741)
-- Dependencies: 185
-- Data for Name: profile; Type: TABLE DATA; Schema: public; Owner: myresume
--

COPY profile (id, uid, first_name, last_name, birth_day, phone, email, country, city, objective, summary, large_photo, small_photo, info, password, completed, created, skype, vkontakte, facebook, linkedin, github, stackoverflow) FROM stdin;
23	stuart-bloom	Stuart	Bloom	1990-10-05	+380501341899	stuart-bloom@gmail.com	Ukraine	Kiyv	Junior java developer position	Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)	/media/avatar/4e82e4ff-cac0-4b09-b37a-563a7130be1d.jpg	/media/avatar/4e82e4ff-cac0-4b09-b37a-563a7130be1d-sm.jpg	\N	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:22	stuart-bloom	\N	https://facebook.com/stuart-bloom	https://linkedin.com/stuart-bloom	\N	\N
1	aly-dutta	Aly	Dutta	1995-06-20	+380509277286	aly-dutta@gmail.com	Ukraine	Kiyv	Junior java developer position	One Java professional course with developing web application resume (Link to demo is provided)	/media/avatar/ba334213-766c-4462-b006-b374459b8beb.jpg	/media/avatar/ba334213-766c-4462-b006-b374459b8beb-sm.jpg	\N	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:19	\N	https://vk.com/aly-dutta	\N	\N	https://github.com/aly-dutta	https://stackoverflow.com/aly-dutta
2	amy-fowler	Amy	Fowler	1993-06-29	+380501897661	amy-fowler@gmail.com	Ukraine	Kiyv	Junior java developer position	Three Java professional courses with developing one console application and two web applications: blog and resume (Links to demo are provided)	/media/avatar/a84edc78-83dc-48b2-8773-b092ec2f348f.jpg	/media/avatar/a84edc78-83dc-48b2-8773-b092ec2f348f-sm.jpg	\N	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:19	amy-fowler	https://vk.com/amy-fowler	https://facebook.com/amy-fowler	https://linkedin.com/amy-fowler	https://github.com/amy-fowler	\N
3	bernadette-rostenkowski	Bernadette	Rostenkowski	1996-12-21	+380508958362	bernadette-rostenkowski@gmail.com	Ukraine	Kharkiv	Junior java developer position	Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)	/media/avatar/5edfe430-cadd-421c-99de-849f0c883c79.jpg	/media/avatar/5edfe430-cadd-421c-99de-849f0c883c79-sm.jpg	\N	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:20	\N	https://vk.com/bernadette-rostenkowski	\N	https://linkedin.com/bernadette-rostenkowski	\N	\N
4	bertram-gilfoyle	Bertram	Gilfoyle	1992-01-11	+380501399572	bertram-gilfoyle@gmail.com	Ukraine	Kiyv	Junior java developer position	One Java professional course with developing web application resume (Link to demo is provided)	/media/avatar/ba97d869-36e7-4557-a0a5-ce365fc1957b.jpg	/media/avatar/ba97d869-36e7-4557-a0a5-ce365fc1957b-sm.jpg	Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium.	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:20	\N	https://vk.com/bertram-gilfoyle	\N	\N	https://github.com/bertram-gilfoyle	\N
5	carla-walton	Carla	Walton	1997-03-29	+380504229475	carla-walton@gmail.com	Ukraine	Odessa	Junior java trainee position	Java core course with developing one simple console application	/media/avatar/25c31720-056e-488e-b236-e921cce8ba66.jpg	/media/avatar/25c31720-056e-488e-b236-e921cce8ba66-sm.jpg	\N	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:20	\N	https://vk.com/carla-walton	\N	\N	\N	https://stackoverflow.com/carla-walton
6	dinesh-chugtai	Dinesh	Chugtai	1990-08-31	+380506936126	dinesh-chugtai@gmail.com	Ukraine	Kharkiv	Junior java developer position	Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)	/media/avatar/a799b4d8-f702-4e82-a5a1-e3e85f85d228.jpg	/media/avatar/a799b4d8-f702-4e82-a5a1-e3e85f85d228-sm.jpg	Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh.	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:20	\N	\N	\N	https://linkedin.com/dinesh-chugtai	\N	https://stackoverflow.com/dinesh-chugtai
7	erlich-bachmann	Erlich	Bachmann	1991-07-23	+380505159166	erlich-bachmann@gmail.com	Ukraine	Kiyv	Junior java developer position	One Java professional course with developing web application resume (Link to demo is provided)	/media/avatar/f97961d9-55af-48bd-8abe-c5cfb0f83c35.jpg	/media/avatar/f97961d9-55af-48bd-8abe-c5cfb0f83c35-sm.jpg	Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus.	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:20	erlich-bachmann	https://vk.com/erlich-bachmann	\N	https://linkedin.com/erlich-bachmann	\N	\N
8	harold-gunderson	Harold	Gunderson	1997-03-03	+380503179199	harold-gunderson@gmail.com	Ukraine	Kharkiv	Junior java developer position	Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)	/media/avatar/59d24e12-790e-4f40-9577-31e561880756.jpg	/media/avatar/59d24e12-790e-4f40-9577-31e561880756-sm.jpg	Praesent adipiscing.	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:20	\N	\N	\N	\N	https://github.com/harold-gunderson	\N
9	howard-wolowitz	Howard	Wolowitz	1998-07-24	+380507791765	howard-wolowitz@gmail.com	Ukraine	Kharkiv	Junior java developer position	Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)	/media/avatar/8949c53c-5e6f-4e88-9dbd-76b6b8a329af.jpg	/media/avatar/8949c53c-5e6f-4e88-9dbd-76b6b8a329af-sm.jpg	Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Fusce id purus. Ut varius tincidunt libero. Phasellus dolor.	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:20	howard-wolowitz	\N	https://facebook.com/howard-wolowitz	https://linkedin.com/howard-wolowitz	\N	\N
10	jared-dunn	Jared	Dunn	1995-05-28	+380507681646	jared-dunn@gmail.com	Ukraine	Kiyv	Junior java developer position	Three Java professional courses with developing one console application and two web applications: blog and resume (Links to demo are provided)	/media/avatar/1d2e94bd-0f21-4749-926f-bc30dd8253d6.jpg	/media/avatar/1d2e94bd-0f21-4749-926f-bc30dd8253d6-sm.jpg	\N	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:20	\N	\N	https://facebook.com/jared-dunn	https://linkedin.com/jared-dunn	https://github.com/jared-dunn	https://stackoverflow.com/jared-dunn
11	jen-barber	Jen	Barber	1998-06-04	+380504132766	jen-barber@gmail.com	Ukraine	Odessa	Junior java trainee position	One Java professional course with developing web application blog (Link to demo is provided)	/media/avatar/8957b980-f507-4b5b-82df-69c420a1015d.jpg	/media/avatar/8957b980-f507-4b5b-82df-69c420a1015d-sm.jpg	\N	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:20	\N	https://vk.com/jen-barber	https://facebook.com/jen-barber	https://linkedin.com/jen-barber	https://github.com/jen-barber	https://stackoverflow.com/jen-barber
12	katrina-bennett	Katrina	Bennett	1996-03-15	+380507594248	katrina-bennett@gmail.com	Ukraine	Kharkiv	Junior java developer position	Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)	/media/avatar/b8842045-91bb-4658-99dd-d6999c4328f7.jpg	/media/avatar/b8842045-91bb-4658-99dd-d6999c4328f7-sm.jpg	Nunc nonummy metus. Vestibulum volutpat pretium libero. Cras id dui.	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:20	katrina-bennett	https://vk.com/katrina-bennett	https://facebook.com/katrina-bennett	https://linkedin.com/katrina-bennett	https://github.com/katrina-bennett	\N
13	leonard-hofstadter	Leonard	Hofstadter	1992-02-15	+380508557682	leonard-hofstadter@gmail.com	Ukraine	Kiyv	Junior java developer position	One Java professional course with developing web application resume (Link to demo is provided)	/media/avatar/c2ce2db7-81cc-4817-a53d-ae880eedc9e2.jpg	/media/avatar/c2ce2db7-81cc-4817-a53d-ae880eedc9e2-sm.jpg	\N	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:21	leonard-hofstadter	https://vk.com/leonard-hofstadter	https://facebook.com/leonard-hofstadter	https://linkedin.com/leonard-hofstadter	\N	https://stackoverflow.com/leonard-hofstadter
14	leslie-winkle	Leslie	Winkle	1995-05-03	+380502794595	leslie-winkle@gmail.com	Ukraine	Kharkiv	Junior java developer position	Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)	/media/avatar/a0041d91-86c8-42b4-9b99-269c2e7cd1ff.jpg	/media/avatar/a0041d91-86c8-42b4-9b99-269c2e7cd1ff-sm.jpg	\N	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:21	\N	https://vk.com/leslie-winkle	\N	https://linkedin.com/leslie-winkle	\N	https://stackoverflow.com/leslie-winkle
15	logan-sanders	Logan	Sanders	1992-04-14	+380507615471	logan-sanders@gmail.com	Ukraine	Kiyv	Junior java developer position	Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)	/media/avatar/ffca48b9-33ce-49a9-926e-adb6f205ad93.jpg	/media/avatar/ffca48b9-33ce-49a9-926e-adb6f205ad93-sm.jpg	Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus.	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:21	\N	https://vk.com/logan-sanders	https://facebook.com/logan-sanders	https://linkedin.com/logan-sanders	https://github.com/logan-sanders	https://stackoverflow.com/logan-sanders
16	maurice-moss	Maurice	Moss	1990-03-08	+380508435942	maurice-moss@gmail.com	Ukraine	Kharkiv	Junior java developer position	One Java professional course with developing web application resume (Link to demo is provided)	/media/avatar/a45afbd9-c787-46e0-972a-b179a7b5a9e5.jpg	/media/avatar/a45afbd9-c787-46e0-972a-b179a7b5a9e5-sm.jpg	Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet.	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:21	\N	\N	\N	\N	\N	\N
17	mike-ross	Mike	Ross	1991-04-05	+380503863676	mike-ross@gmail.com	Ukraine	Odessa	Junior java trainee position	One Java professional course with developing web application blog (Link to demo is provided)	/media/avatar/52ddff61-832d-451f-be06-582b6b0e844c.jpg	/media/avatar/52ddff61-832d-451f-be06-582b6b0e844c-sm.jpg	Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus.	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:21	\N	https://vk.com/mike-ross	\N	https://linkedin.com/mike-ross	https://github.com/mike-ross	https://stackoverflow.com/mike-ross
18	rachel-zane	Rachel	Zane	1989-11-12	+380507418579	rachel-zane@gmail.com	Ukraine	Kiyv	Junior java developer position	Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)	/media/avatar/c10f1457-0d25-4645-981c-7eb01bde69c5.jpg	/media/avatar/c10f1457-0d25-4645-981c-7eb01bde69c5-sm.jpg	\N	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:21	\N	\N	https://facebook.com/rachel-zane	https://linkedin.com/rachel-zane	\N	\N
19	rajesh-koothrappali	Rajesh	Koothrappali	1991-06-13	+380504812744	rajesh-koothrappali@gmail.com	Ukraine	Kharkiv	Junior java trainee position	Java core course with developing one simple console application	/media/avatar/5bf74993-d9b5-464f-8eb3-140cfad5a75d.jpg	/media/avatar/5bf74993-d9b5-464f-8eb3-140cfad5a75d-sm.jpg	Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt.	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:21	\N	\N	\N	\N	https://github.com/rajesh-koothrappali	\N
20	richard-hendricks	Richard	Hendricks	1998-10-13	+380509849922	richard-hendricks@gmail.com	Ukraine	Kharkiv	Junior java developer position	Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)	/media/avatar/0c3628b4-e306-4eff-8f62-29f577a811ad.jpg	/media/avatar/0c3628b4-e306-4eff-8f62-29f577a811ad-sm.jpg	Phasellus a est. Phasellus magna. In hac habitasse platea dictumst.	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:21	\N	https://vk.com/richard-hendricks	\N	\N	https://github.com/richard-hendricks	\N
21	roy-trenneman	Roy	Trenneman	1990-01-14	+380502721676	roy-trenneman@gmail.com	Ukraine	Odessa	Junior java developer position	Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)	/media/avatar/94436314-6e58-4c81-98bd-09609a9220f4.jpg	/media/avatar/94436314-6e58-4c81-98bd-09609a9220f4-sm.jpg	\N	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:21	\N	https://vk.com/roy-trenneman	https://facebook.com/roy-trenneman	https://linkedin.com/roy-trenneman	\N	\N
22	sheldon-cooper	Sheldon	Cooper	1990-11-18	+380504647872	sheldon-cooper@gmail.com	Ukraine	Kiyv	Junior java developer position	Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)	/media/avatar/c6979e53-01e5-4a8d-a5db-25e35576d468.jpg	/media/avatar/c6979e53-01e5-4a8d-a5db-25e35576d468-sm.jpg	\N	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:21	\N	https://vk.com/sheldon-cooper	https://facebook.com/sheldon-cooper	https://linkedin.com/sheldon-cooper	https://github.com/sheldon-cooper	https://stackoverflow.com/sheldon-cooper
24	trevor-evans	Trevor	Evans	1991-09-26	+380506854671	trevor-evans@gmail.com	Ukraine	Kiyv	Junior java trainee position	Java core course with developing one simple console application	/media/avatar/e1cf970e-31b5-4b7f-8670-e8da35fcfe26.jpg	/media/avatar/e1cf970e-31b5-4b7f-8670-e8da35fcfe26-sm.jpg	Pellentesque libero tortor, tincidunt et, tincidunt eget, semper nec, quam. Sed hendrerit. Morbi ac felis.	$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq	t	2019-06-10 18:24:22	trevor-evans	\N	\N	\N	https://github.com/trevor-evans	\N
\.


--
-- TOC entry 2275 (class 0 OID 16838)
-- Dependencies: 193
-- Data for Name: profile_restore; Type: TABLE DATA; Schema: public; Owner: myresume
--

COPY profile_restore (id, token) FROM stdin;
\.


--
-- TOC entry 2305 (class 0 OID 0)
-- Dependencies: 201
-- Name: profile_seq; Type: SEQUENCE SET; Schema: public; Owner: myresume
--

SELECT pg_catalog.setval('profile_seq', 24, true);


--
-- TOC entry 2274 (class 0 OID 16825)
-- Dependencies: 192
-- Data for Name: skill; Type: TABLE DATA; Schema: public; Owner: myresume
--

COPY skill (id, profile_id, category, value) FROM stdin;
\.


--
-- TOC entry 2276 (class 0 OID 16860)
-- Dependencies: 194
-- Data for Name: skill_category; Type: TABLE DATA; Schema: public; Owner: myresume
--

COPY skill_category (id, category) FROM stdin;
0	Languages
1	DBMS
2	Frontend
3	Backend
4	IDE
5	CVS
6	Web Servers
7	Build systems
8	Cloud
\.


--
-- TOC entry 2306 (class 0 OID 0)
-- Dependencies: 202
-- Name: skill_seq; Type: SEQUENCE SET; Schema: public; Owner: myresume
--

SELECT pg_catalog.setval('skill_seq', 207, true);


--
-- TOC entry 2093 (class 2606 OID 16763)
-- Name: certificate certificate_pkey; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY certificate
    ADD CONSTRAINT certificate_pkey PRIMARY KEY (id);


--
-- TOC entry 2097 (class 2606 OID 16773)
-- Name: course course_pkey; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY course
    ADD CONSTRAINT course_pkey PRIMARY KEY (id);


--
-- TOC entry 2101 (class 2606 OID 16786)
-- Name: education education_pkey; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY education
    ADD CONSTRAINT education_pkey PRIMARY KEY (id);


--
-- TOC entry 2111 (class 2606 OID 16819)
-- Name: experience experience_pkey; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY experience
    ADD CONSTRAINT experience_pkey PRIMARY KEY (id);


--
-- TOC entry 2128 (class 2606 OID 16900)
-- Name: f_language_level f_language_level_info_name_key; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY f_language_level
    ADD CONSTRAINT f_language_level_info_name_key UNIQUE (name);


--
-- TOC entry 2130 (class 2606 OID 16898)
-- Name: f_language_level f_language_level_info_pkey; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY f_language_level
    ADD CONSTRAINT f_language_level_info_pkey PRIMARY KEY (id);


--
-- TOC entry 2133 (class 2606 OID 16905)
-- Name: f_language f_language_pkey; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY f_language
    ADD CONSTRAINT f_language_pkey PRIMARY KEY (id);


--
-- TOC entry 2135 (class 2606 OID 16933)
-- Name: f_skill_category f_skill_category_name_key; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY f_skill_category
    ADD CONSTRAINT f_skill_category_name_key UNIQUE (name);


--
-- TOC entry 2137 (class 2606 OID 16931)
-- Name: f_skill_category f_skill_category_pkey; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY f_skill_category
    ADD CONSTRAINT f_skill_category_pkey PRIMARY KEY (id);


--
-- TOC entry 2139 (class 2606 OID 16941)
-- Name: f_skill f_skill_pkey; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY f_skill
    ADD CONSTRAINT f_skill_pkey PRIMARY KEY (id);


--
-- TOC entry 2104 (class 2606 OID 16796)
-- Name: hobby hobby_pkey; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY hobby
    ADD CONSTRAINT hobby_pkey PRIMARY KEY (id);


--
-- TOC entry 2107 (class 2606 OID 16806)
-- Name: language language_pkey; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY language
    ADD CONSTRAINT language_pkey PRIMARY KEY (id);


--
-- TOC entry 2124 (class 2606 OID 16893)
-- Name: f_language_type language_type_info_name_key; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY f_language_type
    ADD CONSTRAINT language_type_info_name_key UNIQUE (name);


--
-- TOC entry 2126 (class 2606 OID 16891)
-- Name: f_language_type language_type_info_pkey; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY f_language_type
    ADD CONSTRAINT language_type_info_pkey PRIMARY KEY (id);


--
-- TOC entry 2084 (class 2606 OID 16755)
-- Name: profile profile_email_key; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY profile
    ADD CONSTRAINT profile_email_key UNIQUE (email);


--
-- TOC entry 2086 (class 2606 OID 16753)
-- Name: profile profile_phone_key; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY profile
    ADD CONSTRAINT profile_phone_key UNIQUE (phone);


--
-- TOC entry 2088 (class 2606 OID 16749)
-- Name: profile profile_pkey; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY profile
    ADD CONSTRAINT profile_pkey PRIMARY KEY (id);


--
-- TOC entry 2116 (class 2606 OID 16842)
-- Name: profile_restore profile_restore_pkey; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY profile_restore
    ADD CONSTRAINT profile_restore_pkey PRIMARY KEY (id);


--
-- TOC entry 2118 (class 2606 OID 16844)
-- Name: profile_restore profile_restore_token_key; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY profile_restore
    ADD CONSTRAINT profile_restore_token_key UNIQUE (token);


--
-- TOC entry 2090 (class 2606 OID 16751)
-- Name: profile profile_uid_key; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY profile
    ADD CONSTRAINT profile_uid_key UNIQUE (uid);


--
-- TOC entry 2120 (class 2606 OID 16866)
-- Name: skill_category skill_category_category_key; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY skill_category
    ADD CONSTRAINT skill_category_category_key UNIQUE (category);


--
-- TOC entry 2122 (class 2606 OID 16864)
-- Name: skill_category skill_category_pkey; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY skill_category
    ADD CONSTRAINT skill_category_pkey PRIMARY KEY (id);


--
-- TOC entry 2114 (class 2606 OID 16832)
-- Name: skill skill_pkey; Type: CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY skill
    ADD CONSTRAINT skill_pkey PRIMARY KEY (id);


--
-- TOC entry 2091 (class 1259 OID 16850)
-- Name: certificate_idx; Type: INDEX; Schema: public; Owner: myresume
--

CREATE INDEX certificate_idx ON public.certificate USING btree (profile_id);


--
-- TOC entry 2094 (class 1259 OID 16851)
-- Name: course_idx; Type: INDEX; Schema: public; Owner: myresume
--

CREATE INDEX course_idx ON public.course USING btree (end_date);


--
-- TOC entry 2095 (class 1259 OID 16852)
-- Name: course_idx1; Type: INDEX; Schema: public; Owner: myresume
--

CREATE INDEX course_idx1 ON public.course USING btree (profile_id);


--
-- TOC entry 2098 (class 1259 OID 16853)
-- Name: education_idx; Type: INDEX; Schema: public; Owner: myresume
--

CREATE INDEX education_idx ON public.education USING btree (profile_id);


--
-- TOC entry 2099 (class 1259 OID 16854)
-- Name: education_idx1; Type: INDEX; Schema: public; Owner: myresume
--

CREATE INDEX education_idx1 ON public.education USING btree (end_year);


--
-- TOC entry 2108 (class 1259 OID 16857)
-- Name: experience_idx; Type: INDEX; Schema: public; Owner: myresume
--

CREATE INDEX experience_idx ON public.experience USING btree (profile_id);


--
-- TOC entry 2109 (class 1259 OID 16858)
-- Name: experience_idx1; Type: INDEX; Schema: public; Owner: myresume
--

CREATE INDEX experience_idx1 ON public.experience USING btree (end_date);


--
-- TOC entry 2131 (class 1259 OID 16926)
-- Name: f_language_idx; Type: INDEX; Schema: public; Owner: myresume
--

CREATE INDEX f_language_idx ON public.f_language USING btree (profile_id);


--
-- TOC entry 2102 (class 1259 OID 16855)
-- Name: hobby_idx; Type: INDEX; Schema: public; Owner: myresume
--

CREATE INDEX hobby_idx ON public.hobby USING btree (profile_id);


--
-- TOC entry 2105 (class 1259 OID 16856)
-- Name: language_idx; Type: INDEX; Schema: public; Owner: myresume
--

CREATE INDEX language_idx ON public.language USING btree (profile_id);


--
-- TOC entry 2112 (class 1259 OID 16859)
-- Name: skill_idx; Type: INDEX; Schema: public; Owner: myresume
--

CREATE INDEX skill_idx ON public.skill USING btree (profile_id);


--
-- TOC entry 2140 (class 2606 OID 16764)
-- Name: certificate certificate_fk; Type: FK CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY certificate
    ADD CONSTRAINT certificate_fk FOREIGN KEY (profile_id) REFERENCES profile(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2141 (class 2606 OID 16774)
-- Name: course course_fk; Type: FK CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY course
    ADD CONSTRAINT course_fk FOREIGN KEY (profile_id) REFERENCES profile(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2142 (class 2606 OID 16787)
-- Name: education education_fk; Type: FK CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY education
    ADD CONSTRAINT education_fk FOREIGN KEY (profile_id) REFERENCES profile(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2145 (class 2606 OID 16820)
-- Name: experience experience_fk; Type: FK CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY experience
    ADD CONSTRAINT experience_fk FOREIGN KEY (profile_id) REFERENCES profile(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2148 (class 2606 OID 16911)
-- Name: f_language f_language_fk; Type: FK CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY f_language
    ADD CONSTRAINT f_language_fk FOREIGN KEY (profile_id) REFERENCES profile(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2149 (class 2606 OID 16942)
-- Name: f_skill f_skill_fk; Type: FK CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY f_skill
    ADD CONSTRAINT f_skill_fk FOREIGN KEY (profile_id) REFERENCES profile(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2143 (class 2606 OID 16797)
-- Name: hobby hobby_fk; Type: FK CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY hobby
    ADD CONSTRAINT hobby_fk FOREIGN KEY (profile_id) REFERENCES profile(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2144 (class 2606 OID 16807)
-- Name: language language_fk; Type: FK CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY language
    ADD CONSTRAINT language_fk FOREIGN KEY (profile_id) REFERENCES profile(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2147 (class 2606 OID 16845)
-- Name: profile_restore profile_restore_fk; Type: FK CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY profile_restore
    ADD CONSTRAINT profile_restore_fk FOREIGN KEY (id) REFERENCES profile(id);


--
-- TOC entry 2146 (class 2606 OID 16833)
-- Name: skill skill_fk; Type: FK CONSTRAINT; Schema: public; Owner: myresume
--

ALTER TABLE ONLY skill
    ADD CONSTRAINT skill_fk FOREIGN KEY (profile_id) REFERENCES profile(id) ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2019-06-10 19:13:09

--
-- PostgreSQL database dump complete
--

