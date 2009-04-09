/*==============================================================*/
/* DBMS name:      Sybase SQL Anywhere 10                       */
/* Created on:     8.3.2009 17:33:02                            */
/*==============================================================*/

use scrumtpo;

alter table Administrative_days drop foreign key FK_ADMINIST_RELATIONS_ABSENCE_;
alter table Administrative_days drop foreign key FK_ADMINIST_RELATIONS_EMPLOYEE;
alter table Impediment drop foreign key FK_IMPEDIME_RELATIONS_SPRINT_T;
alter table Impediment drop foreign key FK_IMPEDIME_RELATIONS_EMPLOYEE;
alter table PBI drop foreign key FK_PBI_RELATIONS_PROJECT;
alter table PBI_measurement_result drop foreign key FK_PBI_MEAS_RELATIONS_MEASURE;
alter table PBI_measurement_result drop foreign key FK_PBI_MEAS_RELATIONS_PBI;
alter table Release_PBI drop foreign key FK_RELEASE__RELATIONS_PBI;
alter table Release_PBI drop foreign key FK_RELEASE__RELATIONS_RELEASE;
alter table Release_measurement_result drop foreign key FK_RELEASE__RELATIONS_MEASURE;
alter table Release_measurement_result drop foreign key FK_RELEASE__RELATIONS_RELEASE;
alter table Sprint drop foreign key FK_SPRINT_TEAM;
alter table Sprint drop foreign key FK_SPRINT_RELATIONS_PROJECT;
alter table Sprint_PBI drop foreign key FK_SPRINT_P_REFERENCE_TASK;
alter table Sprint_PBI drop foreign key FK_SPRINT_P_RELATIONS_PBI;
alter table Sprint_PBI drop foreign key FK_SPRINT_P_RELATIONS_SPRINT;
alter table Sprint_measurement_result drop foreign key FK_SPRINT_M_RELATIONS_SPRINT;
alter table Sprint_measurement_result drop foreign key FK_SPRINT_M_RELATIONS_MEASURE;
alter table Sprint_team drop foreign key FK_SPRINT_T_RELATIONS_TEAM;
alter table Sprint_team drop foreign key FK_SPRINT_T_RELATIONS_SPRINT;
alter table Task drop foreign key FK_TASK_RELATIONS_TASK_STA;
alter table Task drop foreign key FK_TASK_RELATIONS_TASK_TYP;
alter table Task drop foreign key FK_TASK_RELATIONS_TEAM_MEM;
alter table Task_measurement_result drop foreign key FK_TASK_MEA_RELATIONS_MEASURE;
alter table Task_measurement_result drop foreign key FK_TASK_MEA_RELATIONS_TASK;
alter table Team_member drop foreign key FK_TEAM_MEM_RELATIONS_EMPLOYEE;
alter table Team_member drop foreign key FK_TEAM_MEM_RELATIONS_TEAM;

drop table if exists Absence_type;
drop table if exists Administrative_days;
drop table if exists Employee;
drop table if exists Impediment;
drop table if exists Measure;
drop table if exists PBI;
drop table if exists PBI_measurement_result;
drop table if exists Project;
drop table if exists FinalRelease;
drop table if exists Release_PBI;
drop table if exists Release_measurement_result;
drop table if exists Sprint;
drop table if exists Sprint_PBI;
drop table if exists Sprint_measurement_result;
drop table if exists Sprint_team;
drop table if exists Task;
drop table if exists Task_measurement_result;
drop table if exists Task_status;
drop table if exists Task_type;
drop table if exists Team;
drop table if exists Team_member;

/*==============================================================*/
/* Table: Absence_type                                          */
/*==============================================================*/
create table Absence_type 
(
   Absence_type_id      integer	AUTO_INCREMENT             not null,
   Absence_type_description text                           null,
   constraint PK_ABSENCE_TYPE primary key (Absence_type_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Administrative_days                                   */
/*==============================================================*/
create table Administrative_days 
(
   Employee_id          integer                        not null,
   Absence_type_id      integer                        not null,
   Hours_not_worked     decimal                        not null,
   constraint PK_ADMINISTRATIVE_DAYS primary key (Employee_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Employee                                              */
/*==============================================================*/
create table Employee 
(
   Employee_id          integer	AUTO_INCREMENT	not null,
   Employee_name		varchar(255)	not null,
   Employee_surname		varchar(255)	not null,
   Employee_address		varchar(255)   	not null,
   constraint PK_EMPLOYEE primary key (Employee_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Impediment                                            */
/*==============================================================*/
create table Impediment 
(
   Impediment_id        integer	AUTO_INCREMENT not null,
   Team_id              integer                not null,
   Sprint_id            integer                not null,
   Employee_id          integer                not null,
   Task_id				integer				   not null,
   Impediment_description text                 null,
   Impediment_type		
   ENUM('Specification problems', 'Hardware problems', 'Software problems', 'Security problems', 'Teamwork problems', 'Other') not null,
   Impediment_status	
   ENUM('Open', 'Pending', 'In Progress', 'Closed', 'Other') not null,
   Impediment_start		DATE				   null,
   Impediment_end		DATE				   null,
   Impediment_age		integer				   null,
   constraint PK_IMPEDIMENT primary key (Impediment_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Measure                                               */
/*==============================================================*/
create table Measure 
(
   Measure_id	integer	AUTO_INCREMENT	not null,
   Measure_name	text	null,
   Measure_description	text	null,
   constraint PK_MEASURE primary key(Measure_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: PBI                                                   */
/*==============================================================*/
create table PBI 
(
   PBI_id               integer	AUTO_INCREMENT	not null,
   Project_id           integer	not null,
   Sprint_id			integer,
   PBI_description      text not null,
   PBI_priority         int                           null,
   PBI_initial_estimate	decimal	null,
   PBI_adjustment_factor decimal null,
   constraint PK_PBI primary key (PBI_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: PBI_measurement_result                                */
/*==============================================================*/
create table PBI_measurement_result 
(
   Measure_id           integer                        not null,
   PBI_id               integer                        not null,
   Measurement_result   long varchar                   null,
   Datum                DATE		                   not null,
   constraint PK_PBI_MEASUREMENT_RESULT primary key (Measure_id, PBI_id, Datum)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Project                                               */
/*==============================================================*/
create table Project 
(
   Project_id           integer	AUTO_INCREMENT	not null,
   Project_name			VARCHAR(255) not null unique,
   Project_description	text(1024) not null,
   constraint PK_PROJECT primary key (Project_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: "FinalRelease"                                             */
/*==============================================================*/
create table FinalRelease
(
   Release_id           integer	AUTO_INCREMENT         not null,
   Release_description  text                           null,
   constraint PK_RELEASE primary key (Release_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Release_PBI                                           */
/*==============================================================*/
create table Release_PBI 
(
   PBI_id               integer                        not null,
   Release_id           integer                        not null,
   constraint PK_RELEASE_PBI primary key (PBI_id, Release_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Release_measurement_result                            */
/*==============================================================*/
create table Release_measurement_result 
(
   Measure_id           integer                        not null,
   Release_id           integer                        not null,
   Measurement_result   text                           null,
   Datum                DATE                           not null,
   constraint PK_RELEASE_MEASUREMENT_RESULT primary key (Measure_id, Release_id, Datum)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Sprint                                                */
/*==============================================================*/
create table Sprint 
(
   Sprint_id            integer	AUTO_INCREMENT         not null,
   Project_id           integer                        not null,
   Team_id              integer                        not null,
   Sprint_description   text                           null,
   Sprint_begin_date    date                           not null,
   Sprint_end_date      date                           null,
   Sprint_length        integer                        not null,
   Sprint_estimated_date date                          null,
   constraint PK_SPRINT primary key (Sprint_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Sprint_PBI                                            */
/*==============================================================*/
create table Sprint_PBI 
(
   Measure_day	integer	not null,
   PBI_id	integer	not null,
   Task_id	integer not null,
   Sprint_id	integer	not null,
   Employee_id	integer	not null,
   Hours_spent	integer	default '0' null,
   Hours_remaining integer default '0' null,
   NbOpenImped	integer	default '0' null,
   NbClosedImped	integer	default '0'	null,
   constraint PK_MEASURE primary key (Task_id, Measure_day)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Sprint_measurement_result                             */
/*==============================================================*/
create table Sprint_measurement_result 
(
   Sprint_id            integer                        not null,
   Measure_id           integer                        not null,
   Measurement_result   text                           null,
   Datum                date                           not null,
   constraint PK_SPRINT_MEASUREMENT_RESULT primary key (Sprint_id, Measure_id, Datum)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Sprint_team                                           */
/*==============================================================*/
create table Sprint_team 
(
   Team_id              integer                        not null,
   Sprint_id            integer                        not null,
   constraint PK_SPRINT_TEAM primary key (Team_id, Sprint_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Task                                                  */
/*==============================================================*/
create table Task 
(
   Task_id              integer  	AUTO_INCREMENT 	   not null,
   Employee_id          integer                        null,
   Team_id              integer                        null,
   Task_status_id       integer                        not null,
   Task_type_id         integer                        not null,
   Task_description     text                           not null,
   Task_date            text                           null,
   Task_active          text			   not null,
   constraint PK_TASK primary key (Task_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Task_measurement_result                               */
/*==============================================================*/
create table Task_measurement_result 
(
   Measure_id           integer                        not null,
   Task_id              integer                        not null,
   Measurement_result   text                           null,
   Datum                date                           not null,
   constraint PK_TASK_MEASUREMENT_RESULT primary key (Measure_id, Task_id, Datum)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Task_status                                           */
/*==============================================================*/
create table Task_status 
(
   Task_status_id       integer	AUTO_INCREMENT                        not null,
   Task_status_description text                           null,
   constraint PK_TASK_STATUS primary key (Task_status_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Task_type                                             */
/*==============================================================*/
create table Task_type 
(
   Task_type_id         integer	AUTO_INCREMENT                        not null,
   Task_type_description text                           null,
   constraint PK_TASK_TYPE primary key (Task_type_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Team                                                  */
/*==============================================================*/
create table Team 
(
   Team_id              integer	AUTO_INCREMENT                        not null,
   Team_description     text                           null,
   constraint PK_TEAM primary key (Team_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Team_member                                           */
/*==============================================================*/
create table Team_member 
(
   Employee_id          integer                        not null,
   Team_id              integer                        not null,
   constraint PK_TEAM_MEMBER primary key (Employee_id, Team_id)
) CHARACTER SET utf8;

alter table Administrative_days
   add constraint FK_ADMINIST_RELATIONS_ABSENCE_ foreign key (Absence_type_id)
      references Absence_type (Absence_type_id)
      on update restrict
      on delete restrict;

alter table Administrative_days
   add constraint FK_ADMINIST_RELATIONS_EMPLOYEE foreign key (Employee_id)
      references Employee (Employee_id)
      on update restrict
      on delete restrict;

alter table Impediment
   add constraint FK_IMPEDIME_RELATIONS_SPRINT_T foreign key (Team_id, Sprint_id)
      references Sprint_team (Team_id, Sprint_id)
      on update restrict
      on delete restrict;

alter table Impediment
   add constraint FK_IMPEDIME_RELATIONS_EMPLOYEE foreign key (Employee_id)
      references Employee (Employee_id)
      on update restrict
      on delete restrict;
	  
alter table Impediment
   add constraint FK_IMPEDIME_RELATIONS_TASK foreign key (Task_id)
      references Task (Task_id)
      on update restrict
      on delete restrict;

alter table PBI
   add constraint FK_PBI_RELATIONS_PROJECT foreign key (Project_id)
      references Project (Project_id)
      on update restrict
      on delete restrict;
	  
alter table PBI
   add constraint FK_PBI_RELATIONS_SPRINT foreign key (Sprint_id)
      references Sprint (Sprint_id)
      on update restrict
      on delete restrict;

alter table PBI_measurement_result
   add constraint FK_PBI_MEAS_RELATIONS_MEASURE foreign key (Measure_id)
      references Measure (Measure_id)
      on update restrict
      on delete restrict;

alter table PBI_measurement_result
   add constraint FK_PBI_MEAS_RELATIONS_PBI foreign key (PBI_id)
      references PBI (PBI_id)
      on update restrict
      on delete restrict;

alter table Release_PBI
   add constraint FK_RELEASE__RELATIONS_PBI foreign key (PBI_id)
      references PBI (PBI_id)
      on update restrict
      on delete restrict;

alter table Release_PBI
   add constraint FK_RELEASE__RELATIONS_RELEASE foreign key (Release_id)
      references FinalRelease (Release_id)
      on update restrict
      on delete restrict;

alter table Release_measurement_result
   add constraint FK_RELEASE__RELATIONS_MEASURE foreign key (Measure_id)
      references Measure (Measure_id)
      on update restrict
      on delete restrict;

alter table Release_measurement_result
   add constraint FK_RELEASE__RELATIONS_RELEASE foreign key (Release_id)
      references FinalRelease (Release_id)
      on update restrict
      on delete restrict;

alter table Sprint
   add constraint FK_SPRINT_TEAM foreign key (Team_id)
      references Team (Team_id)
      on update restrict
      on delete restrict;

alter table Sprint
   add constraint FK_SPRINT_RELATIONS_PROJECT foreign key (Project_id)
      references Project (Project_id)
      on update restrict
      on delete restrict;

alter table Sprint_PBI
   add constraint FK_SPRINT_P_REFERENCE_TASK foreign key (Task_id)
      references Task (Task_id)
      on update restrict
      on delete restrict;

alter table Sprint_PBI
   add constraint FK_SPRINT_P_RELATIONS_PBI foreign key (PBI_id)
      references PBI (PBI_id)
      on update restrict
      on delete restrict;

alter table Sprint_PBI
   add constraint FK_SPRINT_P_RELATIONS_SPRINT foreign key (Sprint_id)
      references Sprint (Sprint_id)
      on update restrict
      on delete restrict;

alter table Sprint_PBI
   add constraint FK_SPRINT_P_RELATONS_EMPLOYEE foreign key (Employee_id)
	  references Employee (Employee_id)
	  on update restrict
	  on delete restrict;

alter table Sprint_measurement_result
   add constraint FK_SPRINT_M_RELATIONS_SPRINT foreign key (Sprint_id)
      references Sprint (Sprint_id)
      on update restrict
      on delete restrict;

alter table Sprint_measurement_result
   add constraint FK_SPRINT_M_RELATIONS_MEASURE foreign key (Measure_id)
      references Measure (Measure_id)
      on update restrict
      on delete restrict;

alter table Sprint_team
   add constraint FK_SPRINT_T_RELATIONS_TEAM foreign key (Team_id)
      references Team (Team_id)
      on update restrict
      on delete restrict;

alter table Sprint_team
   add constraint FK_SPRINT_T_RELATIONS_SPRINT foreign key (Sprint_id)
      references Sprint (Sprint_id)
      on update restrict
      on delete restrict;

alter table Task
   add constraint FK_TASK_RELATIONS_TASK_STA foreign key (Task_status_id)
      references Task_status (Task_status_id)
      on update restrict
      on delete restrict;

alter table Task
   add constraint FK_TASK_RELATIONS_TASK_TYP foreign key (Task_type_id)
      references Task_type (Task_type_id)
      on update restrict
      on delete restrict;

alter table Task
   add constraint FK_TASK_RELATIONS_TEAM_MEM foreign key (Employee_id, Team_id)
      references Team_member (Employee_id, Team_id)
      on update restrict
      on delete restrict;

alter table Task_measurement_result
   add constraint FK_TASK_MEA_RELATIONS_MEASURE foreign key (Measure_id)
      references Measure (Measure_id)
      on update restrict
      on delete restrict;

alter table Task_measurement_result
   add constraint FK_TASK_MEA_RELATIONS_TASK foreign key (Task_id)
      references Task (Task_id)
      on update restrict
      on delete restrict;

alter table Team_member
   add constraint FK_TEAM_MEM_RELATIONS_EMPLOYEE foreign key (Employee_id)
      references Employee (Employee_id)
      on update restrict
      on delete restrict;

alter table Team_member
   add constraint FK_TEAM_MEM_RELATIONS_TEAM foreign key (Team_id)
      references Team (Team_id)
      on update restrict
      on delete restrict;
	  
insert into Absence_type (Absence_type_description) values ('weather');
insert into Absence_type (Absence_type_description) values ('transportation');
insert into Absence_type (Absence_type_description) values ('change of schedule');
insert into Absence_type (Absence_type_description) values ('health');
insert into Absence_type (Absence_type_description) values ('other');

insert into Task_status (Task_status_description) values('not started');
insert into Task_status (Task_status_description) values('in progress');
insert into Task_status (Task_status_description) values('completed');
insert into Task_status (Task_status_description) values('omitted');
insert into Task_status (Task_status_description) values('moved into next sprint');
insert into Task_status (Task_status_description) values('split/divided');
insert into Task_status (Task_status_description) values('not completed due to incorrect feasibility assumptions');
insert into Task_status (Task_status_description) values('other');

insert into Task_type (Task_type_description) values ('analysis');
insert into Task_type (Task_type_description) values ('design');
insert into Task_type (Task_type_description) values ('coding');
insert into Task_type (Task_type_description) values ('testing');
insert into Task_type (Task_type_description) values ('documentation');
insert into Task_type (Task_type_description) values ('rework due to error reported by the customer');
insert into Task_type (Task_type_description) values ('rework due to change in requirements');
insert into Task_type (Task_type_description) values ('rework due to inaccurat specifications');
insert into Task_type (Task_type_description) values ('rework due to incomplete impact assessment');
insert into Task_type (Task_type_description) values ('rework due to inadequate change specifications');
insert into Task_type (Task_type_description) values ('rework due to inadequate testing');
insert into Task_type (Task_type_description) values ('other');

insert into Employee (Employee_name, Employee_surname, Employee_address) values ('Katja', 'Cetinski', 'Kocevje');
insert into Employee (Employee_name, Employee_surname, Employee_address) values ('Ursa', 'Levec', 'Lozine');
insert into Employee (Employee_name, Employee_surname, Employee_address) values ('Tadej', 'Certanc', 'Ljubljana');
insert into Employee (Employee_name, Employee_surname, Employee_address) values ('Simon', 'Mihevc', 'Logatec');
insert into Employee (Employee_name, Employee_surname, Employee_address) values ('Dasa', 'Gelze', 'Ribnica');
insert into Employee (Employee_name, Employee_surname, Employee_address) values ('Miha', 'Mikulic', 'Celje');

insert into Project (Project_name, Project_description) values ('Scrumer', 'aplikacija za vodenje projektov po metodi scrum');
insert into Project (Project_name, Project_description) values ('Testni projekt', 'testna aplikacija');
insert into Project (Project_name, Project_description) values ('Bolnišnica', 'aplikacija za vodenje bolnišnice');
insert into Project (Project_name, Project_description) values ('Igrica', 'igrica hexagon');

insert into Sprint (Project_id, Team_id, Sprint_description, Sprint_begin_date, Sprint_end_date, Sprint_length, Sprint_estimated_date) values (1, 1, 'prvi sprint', '01.01.2009', '31.01.2009', 30, '31.01.2009');
insert into Sprint (Project_id, Team_id, Sprint_description, Sprint_begin_date, Sprint_end_date, Sprint_length, Sprint_estimated_date) values (1, 1, 'drugi sprint', '01.02.2009', '21.02.2009', 20, '21.02.2009');
insert into Sprint (Project_id, Team_id, Sprint_description, Sprint_begin_date, Sprint_end_date, Sprint_length, Sprint_estimated_date) values (2, 2, 'prvi sprint', '01.03.2009', '31.03.2009', 30, '31.03.2009');
insert into Sprint (Project_id, Team_id, Sprint_description, Sprint_begin_date, Sprint_end_date, Sprint_length, Sprint_estimated_date) values (2, 2, 'drugi sprint', '01.04.2009', '30.04.2009', 29, '30.04.2009');

insert into Team (Team_description) values ('lisice');
insert into Team (Team_description) values ('medvedi');

insert into Team_member (Employee_id, Team_id) values (1, 1);
insert into Team_member (Employee_id, Team_id) values (2, 1);
insert into Team_member (Employee_id, Team_id) values (3, 2);
insert into Team_member (Employee_id, Team_id) values (4, 2);

insert into PBI (Project_id, Sprint_id, PBI_description, PBI_priority, PBI_initial_estimate, PBI_adjustment_factor) values(1, 1, 'prvi pbi', 1, 20, 1.5);
insert into PBI (Project_id, Sprint_id, PBI_description, PBI_priority, PBI_initial_estimate, PBI_adjustment_factor) values(1, 1, 'drugi pbi', 1, 20, 1.5);
insert into PBI (Project_id, Sprint_id, PBI_description, PBI_priority, PBI_initial_estimate, PBI_adjustment_factor) values(1, 1, 'tretji pbi', 2, 40, 1);
insert into PBI (Project_id, Sprint_id, PBI_description, PBI_priority, PBI_initial_estimate, PBI_adjustment_factor) values(1, 1, 'cetrti pbi', 2, 10, 2);

insert into Task (Employee_id, Team_id, Task_status_id, Task_type_id, Task_description, Task_active) values (1, 1, 1, 1, 'obrazec', 'yes');
insert into Task (Employee_id, Team_id, Task_status_id, Task_type_id, Task_description, Task_active) values (2, 1, 2, 1, 'meni', 'yes');
insert into Task (Employee_id, Team_id, Task_status_id, Task_type_id, Task_description, Task_active) values (1, 1, 3, 2, 'ikone', 'yes');
insert into Task (Employee_id, Team_id, Task_status_id, Task_type_id, Task_description, Task_active) values (2, 1, 2, 3, 'modeli', 'yes');
