--
-- PostgreSQL database dump
--

-- Dumped from database version 16.6
-- Dumped by pg_dump version 16.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: person_room; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person_room (
    person_id bigint NOT NULL,
    room_id bigint NOT NULL,
    id bigint NOT NULL,
    role character varying(255)
);


ALTER TABLE public.person_room OWNER TO postgres;

--
-- Name: person_room_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.person_room ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.person_room_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: persons; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persons (
    id bigint NOT NULL,
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    phone character varying(255)
);


ALTER TABLE public.persons OWNER TO postgres;

--
-- Name: persons_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.persons ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.persons_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: rooms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rooms (
    id bigint NOT NULL,
    capacity integer,
    description character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.rooms OWNER TO postgres;

--
-- Name: rooms_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.rooms ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.rooms_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: task_assignees; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.task_assignees (
    task_id bigint NOT NULL,
    person_id bigint NOT NULL
);


ALTER TABLE public.task_assignees OWNER TO postgres;

--
-- Name: task_person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.task_person (
    task_id bigint NOT NULL,
    person_id bigint NOT NULL,
    id bigint NOT NULL,
    assignment_date date,
    status character varying(255)
);


ALTER TABLE public.task_person OWNER TO postgres;

--
-- Name: task_person_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.task_person ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.task_person_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: tasks; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tasks (
    id bigint NOT NULL,
    completed boolean,
    description character varying(255),
    name character varying(255) NOT NULL,
    creator_id bigint,
    room_id bigint NOT NULL
);


ALTER TABLE public.tasks OWNER TO postgres;

--
-- Name: tasks_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.tasks ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.tasks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: person_room; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.person_room (person_id, room_id, id, role) FROM stdin;
7	6	6	admin
7	7	7	member
\.


--
-- Data for Name: persons; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.persons (id, email, first_name, last_name, phone) FROM stdin;
4	post@mailcompany.com	John	Doe	047 12345
5	post@mailcompany.com	Jane	Smith	047 54321
6	poster@mailcompany.com	Adam	Smith	047 14865
7	mailin@emailcompany.com	Alina	Lopez	322 14785
8	mail@company.com	John	Doe	047 12345
9	email@anothercompany.com	Jane	Doe	322 54321
10	email2@electronicmailcompany.com	Jane	Austen	987 65432
11	AllThatMatters@SoulProvider.com	Michael	Bolton	155 68732
12	complicated@nobodyshome.com	Avril	Lavigne	471 74114
13	gh294tgjkg49@passmail.net	John	Carpenter	00000000
14	g54jg894j@passmail.net	John	Smith	987 21589
15	massa.non@icloud.ca	Josiah	Dominguez	1-711-861-3352
16	dignissim.maecenas.ornare@yahoo.ca	Glenna	Blair	(257) 234-6832
17	dolor.dapibus@outlook.couk	Walker	Conner	1-508-962-6863
18	id.libero.donec@aol.edu	Cedric	Lopez	(542) 419-7867
19	primis.in@yahoo.net	Raymond	Brady	(313) 239-3340
20	nunc.ut@yahoo.edu	Genevieve	Hughes	1-396-751-0574
21	dignissim.pharetra.nam@outlook.ca	Ayanna	Strong	(628) 362-2934
22	arcu@hotmail.net	Sharon	Alford	(818) 728-5367
23	augue.porttitor.interdum@google.net	Halee	Garrison	1-517-271-1924
\.


--
-- Data for Name: rooms; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.rooms (id, capacity, description, name) FROM stdin;
7	100	Dark academia styled dining hall	Dormitory dining hall
8	500	The town hall of Ringerike	Ringerike Rådhus
9	550	Rural cultural house	Meløy kulturhus
1	1	The default room	Room 1
6	6	Default room	Room 6
10	5	Administrasjon, våte permer, lukter klor og redningvest	Svømmehallkontoret
11	1	Kull, kjeledress og svart kaffe	Vaktmesterkontoret
12	2	500.000 i lav-, middels- og høykultur	Musikkinstrumentrommet
\.


--
-- Data for Name: task_assignees; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.task_assignees (task_id, person_id) FROM stdin;
7	6
7	7
8	5
8	6
9	7
9	8
9	9
10	10
\.


--
-- Data for Name: task_person; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.task_person (task_id, person_id, id, assignment_date, status) FROM stdin;
\.


--
-- Data for Name: tasks; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tasks (id, completed, description, name, creator_id, room_id) FROM stdin;
1	f	det er fullt og lukter stygt på kjøkkenet, ta ut søpla please	Ta ut søpla	7	1
5	f	de er fulle av støv og trafikkfilm	Vaske vinduene	8	8
6	f	Vaske badet. Vasken, toalettet, dusjen.	Vaske badet	4	7
7	f	Vaske badet. Vasken, toalettet, dusjen.	Vaske badet	4	7
8	f	This is a task	Task 1	4	7
9	f	Kjeledressene er fulle av kull og må renses	Vaske kjeledresser	10	11
10	f	Bruke Endpoints-verktøyet i IntelliJ IDEA til å POSTe en task i /tasks	Teste API endpoints	10	10
\.


--
-- Name: person_room_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_room_id_seq', 7, true);


--
-- Name: persons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.persons_id_seq', 23, true);


--
-- Name: rooms_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.rooms_id_seq', 12, true);


--
-- Name: task_person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.task_person_id_seq', 1, false);


--
-- Name: tasks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tasks_id_seq', 10, true);


--
-- Name: person_room person_room_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person_room
    ADD CONSTRAINT person_room_pkey PRIMARY KEY (person_id, room_id);


--
-- Name: persons persons_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persons
    ADD CONSTRAINT persons_pkey PRIMARY KEY (id);


--
-- Name: rooms rooms_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT rooms_pkey PRIMARY KEY (id);


--
-- Name: task_assignees task_assignees_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.task_assignees
    ADD CONSTRAINT task_assignees_pkey PRIMARY KEY (task_id, person_id);


--
-- Name: task_person task_person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.task_person
    ADD CONSTRAINT task_person_pkey PRIMARY KEY (task_id, person_id);


--
-- Name: tasks tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_pkey PRIMARY KEY (id);


--
-- Name: task_person fk31vkwe681ovc1ghlsk39pc9dh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.task_person
    ADD CONSTRAINT fk31vkwe681ovc1ghlsk39pc9dh FOREIGN KEY (person_id) REFERENCES public.persons(id);


--
-- Name: tasks fk3pn9s1qcgv6ym6flrtaaa5jy4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT fk3pn9s1qcgv6ym6flrtaaa5jy4 FOREIGN KEY (creator_id) REFERENCES public.persons(id);


--
-- Name: task_assignees fk4aux04a1o4frnn4xhwwp30pwk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.task_assignees
    ADD CONSTRAINT fk4aux04a1o4frnn4xhwwp30pwk FOREIGN KEY (person_id) REFERENCES public.persons(id);


--
-- Name: person_room fkjq2dceotor1rabtped5wbrd68; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person_room
    ADD CONSTRAINT fkjq2dceotor1rabtped5wbrd68 FOREIGN KEY (person_id) REFERENCES public.persons(id);


--
-- Name: task_person fkmv06r8c1kbmkwe7v3b4g5hax9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.task_person
    ADD CONSTRAINT fkmv06r8c1kbmkwe7v3b4g5hax9 FOREIGN KEY (task_id) REFERENCES public.tasks(id);


--
-- Name: task_assignees fks0jy5sv972lpa2wfx95m7xebb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.task_assignees
    ADD CONSTRAINT fks0jy5sv972lpa2wfx95m7xebb FOREIGN KEY (task_id) REFERENCES public.tasks(id);


--
-- Name: person_room fkt5adgbtjjnakgsk7htcdv3kej; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person_room
    ADD CONSTRAINT fkt5adgbtjjnakgsk7htcdv3kej FOREIGN KEY (room_id) REFERENCES public.rooms(id);


--
-- Name: tasks tasks_room_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_room_id_fkey FOREIGN KEY (room_id) REFERENCES public.rooms(id);


--
-- PostgreSQL database dump complete
--

