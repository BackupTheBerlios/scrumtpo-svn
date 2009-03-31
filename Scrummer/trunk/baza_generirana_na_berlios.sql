-- phpMyAdmin SQL Dump
-- version 2.11.9.4
-- http://www.phpmyadmin.net
--
-- Gostitelj: db.berlios.de
-- Èas nastanka: 30 Mar 2009 ob 06:22 PM
-- Razlièica strežnika: 4.1.22
-- Razlièica PHP: 5.2.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Podatkovna baza: 'scrumtpo'
--

-- --------------------------------------------------------

--
-- Struktura tabele 'Absence_type'
--

-- if exists(select 1 from sys.sysforeignkey where role='FK_ADMINIST_RELATIONS_ABSENCE_') then
--    alter table Administrative_days
--       delete foreign key FK_ADMINIST_RELATIONS_ABSENCE_
-- end if;

use scrumtpo;

drop table Absence_type;

CREATE TABLE Absence_type (
  Absence_type_id MEDIUMINT NOT NULL,
  Absence_type_description mediumtext,
  PRIMARY KEY  (Absence_type_id)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Odloži podatke za tabelo 'Absence_type'
--

INSERT INTO Absence_type (Absence_type_id, Absence_type_description) VALUES
(1, 'weather'),
(2, 'transportation'),
(3, 'change of schedule'),
(4, 'health'),
(5, 'other');

-- --------------------------------------------------------

--
-- Struktura tabele 'Administrative_days'
--

drop table Administrative_days;

CREATE TABLE Administrative_days (
  Employee_id MEDIUMINT AUTO_INCREMENT NOT NULL,
  Datum date NOT NULL default '0000-00-00',
  Absence_type_id MEDIUMINT AUTO_INCREMENT default NULL,
  Hours_not_worked text,
  PRIMARY KEY  (Employee_id,Datum),
  KEY FK_ADMINIST_RELATIONS_ABSENCE_ (Absence_type_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Odloži podatke za tabelo 'Administrative_days'
--


-- --------------------------------------------------------

--
-- Struktura tabele 'Employee'
--

drop table Employee;

CREATE TABLE Employee (
  Employee_id MEDIUMINT NOT NULL auto_increment,
  Employee_name text,
  Employee_surname text,
  Employee_address text,
  PRIMARY KEY  (Employee_id)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Odloži podatke za tabelo 'Employee'
--

INSERT INTO Employee (Employee_id, Employee_name, Employee_surname, Employee_address) VALUES
(1, 'Katja', 'Cetinski', 'Brestova pot 4, Kocevje'),
(2, 'Simon', 'Mihevc', 'Logatec'),
(3, 'Matej', 'Klun', 'Obirska 23, Ljubljana'),
(4, 'Rok', 'Resnik', 'Vrhnika'),
(5, 'Anja', 'Cahuk', 'Trata XIV/20, Kocevje');

-- --------------------------------------------------------

--
-- Struktura tabele 'Impediment'
--

drop table Impediment;

CREATE TABLE Impediment (
  Impediment_id MEDIUMINT NOT NULL auto_increment,
  Team_id MEDIUMINT NOT NULL default '0',
  Sprint_id MEDIUMINT NOT NULL default '0',
  Employee_id MEDIUMINT NOT NULL default '0',
  Task_id MEDIUMINT NOT NULL default '0',
  Impediment_description text character set ucs2 collate ucs2_slovenian_ci,
  Impediment_type text character set ucs2 collate ucs2_slovenian_ci,
  Impediment_status text character set ucs2 collate ucs2_slovenian_ci,
  Impediment_start text character set ucs2 collate ucs2_slovenian_ci,
  Impediment_end text character set ucs2 collate ucs2_slovenian_ci,
  Impediment_age MEDIUMINT default NULL,
  PRIMARY KEY  (Impediment_id),
  KEY FK_IMPEDIME_RELATIONS_SPRINT_T (Team_id,Sprint_id),
  KEY FK_IMPEDIME_RELATIONS_EMPLOYEE (Employee_id),
  KEY FK_IMPEDIME_RELATIONS_TASK (Task_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Odloži podatke za tabelo 'Impediment'
--


-- --------------------------------------------------------

--
-- Struktura tabele 'Measure'
--

drop table Measure;

CREATE TABLE Measure (
  Measure_id MEDIUMINT NOT NULL auto_increment,
  Measure_name text,
  Measure_description text,
  PRIMARY KEY  (Measure_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Odloži podatke za tabelo 'Measure'
--


-- --------------------------------------------------------

--
-- Struktura tabele 'PBI'
--

drop table PBI;

CREATE TABLE PBI (
  PBI_id MEDIUMINT NOT NULL auto_increment,
  Project_id MEDIUMINT AUTO_INCREMENT default NULL,
  PBI_description text,
  PBI_priority text,
  PBI_initial_estimate text,
  PBI_adjustment_factor text,
  PBI_adjusted_estimate text NOT NULL,
  PRIMARY KEY  (PBI_id),
  KEY FK_PBI_RELATIONS_PROJECT (Project_id)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Odloži podatke za tabelo 'PBI'
--

INSERT INTO PBI (PBI_id, Project_id, PBI_description, PBI_priority, PBI_initial_estimate, PBI_adjustment_factor, PBI_adjusted_estimate) VALUES
(1, 1, 'krnekej....', '1', '20', '1.5', '30'),
(2, 1, 'krnekej', '1', '20', '1.5', '30'),
(3, 1, 'test', '2', '40', '1', '40');

-- --------------------------------------------------------

--
-- Struktura tabele 'PBI_measurement_result'
--

drop table PBI_measurement_result;

CREATE TABLE PBI_measurement_result (
  Measure_id MEDIUMINT AUTO_INCREMENT NOT NULL default '0',
  PBI_id MEDIUMINT AUTO_INCREMENT NOT NULL default '0',
  Measurement_result mediumtext,
  Datum date NOT NULL default '0000-00-00',
  PRIMARY KEY  (Measure_id,PBI_id,Datum),
  KEY FK_PBI_MEAS_RELATIONS_PBI (PBI_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Odloži podatke za tabelo 'PBI_measurement_result'
--


-- --------------------------------------------------------

--
-- Struktura tabele 'Project'
--

drop table Project;

CREATE TABLE Project (
  Project_id MEDIUMINT NOT NULL auto_increment,
  Project_name varchar(20) default NULL,
  Project_description text,
  PRIMARY KEY  (Project_id)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Odloži podatke za tabelo 'Project'
--

INSERT INTO Project (Project_id, Project_name, Project_description) VALUES
(1, 'Test', 'opis testa...'),
(2, 'Scrumer', 'aplikacija za vodenje projektov po metodi scrum');

-- --------------------------------------------------------

--
-- Struktura tabele 'Release'
--

drop table Release;

CREATE TABLE Release (
  Release_id MEDIUMINT,
--  Release_description varchar(255),
  PRIMARY KEY  (Release_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Odloži podatke za tabelo 'Release'
--


-- --------------------------------------------------------

--
-- Struktura tabele 'Release_measurement_result'
--

drop table Release_measurement_result;

CREATE TABLE Release_measurement_result (
  Measure_id MEDIUMINT AUTO_INCREMENT NOT NULL,
  Release_id MEDIUMINT NOT NULL,
  Measurement_result text,
  Datum date NOT NULL default '0000-00-00',
  PRIMARY KEY  (Measure_id,Release_id,Datum),
  KEY FK_RELEASE__RELATIONS_RELEASE (Release_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Odloži podatke za tabelo 'Release_measurement_result'
--


-- --------------------------------------------------------

--
-- Struktura tabele 'Release_PBI'
--

drop table Release_PBI;

CREATE TABLE Release_PBI (
  PBI_id MEDIUMINT AUTO_INCREMENT NOT NULL,
  Release_id MEDIUMINT AUTO_INCREMENT NOT NULL default '0',
  PRIMARY KEY  (PBI_id,Release_id),
  KEY FK_RELEASE__RELATIONS_RELEASE (Release_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Odloži podatke za tabelo 'Release_PBI'
--


-- --------------------------------------------------------

--
-- Struktura tabele 'Sprint'
--

drop table Sprint;

CREATE TABLE Sprint (
  Sprint_id MEDIUMINT NOT NULL auto_increment,
  Project_id MEDIUMINT default NULL,
  Team_id MEDIUMINT default NULL,
  Sprint_description text,
  Sprint_begin_date text,
  Sprint_end_date text,
  Sprint_length text,
  Sprint_estimated_date text,
  PRIMARY KEY  (Sprint_id),
  KEY FK_SPRINT_RELATIONS_TEAM (Team_id),
  KEY FK_SPRINT_RELATIONS_PROJECT (Project_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Odloži podatke za tabelo 'Sprint'
--


-- --------------------------------------------------------

--
-- Struktura tabele 'Sprint_measurement_result'
--

drop table Sprint_measurement_result;

CREATE TABLE Sprint_measurement_result (
  Sprint_id MEDIUMINT AUTO_INCREMENT NOT NULL default '0',
  Measure_id MEDIUMINT AUTO_INCREMENT NOT NULL default '0',
  Measurement_result text,
  Datum date NOT NULL default '0000-00-00',
  PRIMARY KEY  (Sprint_id,Measure_id,Datum),
  KEY FK_SPRINT_M_RELATIONS_MEASURE (Measure_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Odloži podatke za tabelo 'Sprint_measurement_result'
--

-- --------------------------------------------------------

--
-- Struktura tabele 'Sprint_PBI'
--

drop table Sprint_PBI;

CREATE TABLE Sprint_PBI (
  PBI_id MEDIUMINT AUTO_INCREMENT NOT NULL default '0',
  Sprint_id MEDIUMINT AUTO_INCREMENT NOT NULL default '0',
  Task_id MEDIUMINT AUTO_INCREMENT default NULL,
  Sprint_PBI_priority text,
  Sprint_PBI_status text,
  PRIMARY KEY  (PBI_id,Sprint_id),
  KEY FK_SPRINT_P_REFERENCE_TASK (Task_id),
  KEY FK_SPRINT_P_RELATIONS_SPRINT (Sprint_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Odloži podatke za tabelo 'Sprint_PBI'
--


-- --------------------------------------------------------

--
-- Struktura tabele 'Sprint_team'
--

drop table Sprint_team;

CREATE TABLE Sprint_team (
  Team_id MEDIUMINT AUTO_INCREMENT NOT NULL default '0',
  Sprint_id MEDIUMINT AUTO_INCREMENT NOT NULL default '0',
  PRIMARY KEY  (Team_id,Sprint_id),
  KEY FK_SPRINT_T_RELATIONS_SPRINT (Sprint_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Odloži podatke za tabelo 'Sprint_team'
--


-- --------------------------------------------------------

--
-- Struktura tabele 'Task'
--

drop table Task;

CREATE TABLE Task (
  Task_id MEDIUMINT AUTO_INCREMENT NOT NULL auto_increment,
  Employee_id MEDIUMINT default NULL,
  Team_id MEDIUMINT default NULL,
  Task_status_id MEDIUMINT default NULL,
  Task_type_id MEDIUMINT default NULL,
  Task_description text,
  Task_cost_of_engineering_hour text,
  Task_date text,
  Task_active text,
  PRIMARY KEY  (Task_id),
  KEY FK_TASK_RELATIONS_TASK_STA (Task_status_id),
  KEY FK_TASK_RELATIONS_TASK_TYP (Task_type_id),
  KEY FK_TASK_RELATIONS_TEAM_MEM (Employee_id,Team_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Odloži podatke za tabelo 'Task'
--


-- --------------------------------------------------------

--
-- Struktura tabele 'Task_measurement_result'
--

drop table Task_measurement_result;

CREATE TABLE Task_measurement_result (
  Measure_id MEDIUMINT AUTO_INCREMENT NOT NULL default '0',
  Task_id MEDIUMINT AUTO_INCREMENT NOT NULL default '0',
  Measurement_result text,
  Datum date NOT NULL default '0000-00-00',
  PRIMARY KEY  (Measure_id,Task_id,Datum),
  KEY FK_TASK_MEA_RELATIONS_TASK (Task_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Odloži podatke za tabelo 'Task_measurement_result'
--


-- --------------------------------------------------------

--
-- Struktura tabele 'Task_status'
--

drop table Task_status;

CREATE TABLE Task_status (
  Task_status_id MEDIUMINT NOT NULL auto_increment,
  Task_status_description text,
  PRIMARY KEY  (Task_status_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Odloži podatke za tabelo 'Task_status'
--


-- --------------------------------------------------------

--
-- Struktura tabele 'Task_type'
--

drop table Task_type;

CREATE TABLE Task_type (
  Task_type_id MEDIUMINT NOT NULL auto_increment,
  Task_type_description text,
  PRIMARY KEY  (Task_type_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Odloži podatke za tabelo 'Task_type'
--


-- --------------------------------------------------------

--
-- Struktura tabele 'Team'
--

drop table Team;

CREATE TABLE Team (
  Team_id MEDIUMINT NOT NULL auto_increment,
  Team_description text,
  PRIMARY KEY  (Team_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Odloži podatke za tabelo 'Team'
--


-- --------------------------------------------------------

--
-- Struktura tabele 'Team_member'
--

drop table Team_member;

CREATE TABLE Team_member (
  Employee_id MEDIUMINT AUTO_INCREMENT NOT NULL default '0',
  Team_id MEDIUMINT AUTO_INCREMENT NOT NULL default '0',
  Percentage_of_engagement_in_project MEDIUMINT AUTO_INCREMENT default NULL,
  PRIMARY KEY  (Employee_id,Team_id),
  KEY FK_TEAM_MEM_RELATIONS_TEAM (Team_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Odloži podatke za tabelo 'Team_member'
--

