/*==============================================================*/
/* DBMS name:      Sybase SQL Anywhere 10                       */
/* Created on:     8.3.2009 17:33:02                            */
/*==============================================================*/

use scrumtpo;

alter table Administrative_days drop foreign key FK_ADMINIST_RELATIONS_ABSENCE_;
alter table Administrative_days drop foreign key FK_ADMINIST_RELATIONS_EMPLOYEE;
alter table Impediment drop foreign key FK_IMPEDIME_RELATIONS_SPRINT_T;
alter table Impediment drop foreign key FK_IMPEDIME_RELATIONS_TYPE;
alter table Impediment drop foreign key FK_IMPEDIME_RELATIONS_STATUS;
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
drop table if exists Impediment_type;
drop table if exists Impediment_status;
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
drop table if exists CustomerPoll_measurement_result;
drop table if exists DeveloperPoll_measurement_result;
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
   Hours_not_worked     integer                        not null,
   Measure_day 			DATE 						   not null,
   constraint PK_ADMINISTRATIVE_DAYS primary key (Employee_id, Measure_day)
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
   Impediment_type_id   integer                not null,
   Impediment_status_id integer                not null,
   Impediment_start		DATE				   null,
   Impediment_end		DATE				   null,
   Impediment_age		integer				   null,
   constraint PK_IMPEDIMENT primary key (Impediment_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Impediment_type                                             */
/*==============================================================*/
create table Impediment_type 
(
   Impediment_type_id          integer	AUTO_INCREMENT                        not null,
   Impediment_type_description text                           null,
   constraint PK_TASK_TYPE primary key (Impediment_type_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Impediment_status                                             */
/*==============================================================*/
create table Impediment_status 
(
   Impediment_status_id         integer	AUTO_INCREMENT                        not null,
   Impediment_status_description text                           null,
   constraint PK_TASK_TYPE primary key (Impediment_status_id)
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
   Measure_day	DATE	not null,
   Task_id	integer	not null,
   Sprint_id	integer	not null,
   Employee_id	integer	not null,
   Hours_spent	integer	default '0' null,
   Hours_remaining integer default '0' null,
   NbOpenImped	integer	default '0' null,
   NbClosedImped	integer	default '0'	null,
   constraint PK_MEASURE primary key (Sprint_id, Task_id, Measure_day, Employee_id)
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
   PBI_id				integer						   not null,
   Team_id              integer                        null,
   Task_parent_id		integer						   null,
   Task_status_id       integer                        not null,
   Task_type_id         integer                        not null,
   Task_description     text                           not null,
   Task_engineering_hour DECIMAL					   not null,
   Task_date            DATE                           not null,
   Task_active          BOOLEAN			   not null,
   constraint PK_TASK primary key (Task_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: Task_measurement_result                               */
/*==============================================================*/
create table Task_measurement_result 
(
   Measure_id			integer                        not null,
   Task_id              integer                        not null,
   Measurement_result   text                           null,
   Datum                date                           not null,
   constraint PK_TASK_MEASUREMENT_RESULT primary key (Measure_id, Task_id, Datum)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: CustomerPoll_measurement_result                       */
/*==============================================================*/
create table CustomerPoll_measurement_result
(
	Measure_id			integer                         not null,
	Customer_name		VARCHAR(255)					null,
	Measurement_result	text							null,
	Datum				date							not null,
	Sprint_id			integer							not null,
	constraint PK_CUSTOMERPOLL_MEASUREMENT_RESULT primary key(Measure_id, Customer_name, Datum, Sprint_id)
) CHARACTER SET utf8;

/*==============================================================*/
/* Table: DeveloperPoll_measurement_result                      */
/*==============================================================*/
create table DeveloperPoll_measurement_result
(
	Measure_id           integer                        not null,
	Employee_id			integer							not null,
	Measurement_result	text							null,
	Datum				date							not null,
	Sprint_id			integer							not null,
	constraint PK_DEVELOPERPOLL_MEASUREMENT_RESULT primary key(Measure_id, Employee_id, Datum, Sprint_id)
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
   add constraint FK_IMPEDIME_RELATIONS_TYPE foreign key (Impediment_type_id)
      references Impediment_type (Impediment_type_id)
      on update restrict
      on delete restrict;

alter table Impediment
   add constraint FK_IMPEDIME_RELATIONS_STATUS foreign key (Impediment_status_id)
      references Impediment_status (Impediment_status_id)
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
   add constraint FK_SPRINT_P_RELATIONS_TASK_ID foreign key (Task_id)
      references Task (Task_id)
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

alter table Task
   add constraint FK_TASK_PROJECT_ID foreign key (PBI_id)
      references PBI (PBI_id)
      on update restrict
      on delete restrict;

alter table Task
   add constraint FK_TASK_PARENT_ID foreign key (Task_parent_id)
      references Task (Task_id)
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
	  
alter table CustomerPoll_measurement_result
	add constraint FK_CUSPOLL_MEA_RELATIONS_MEASURE foreign key (Measure_id)
		references Measure (Measure_id)
		on update restrict
		on delete restrict;
		
alter table CustomerPoll_measurement_result
	add constraint FK_CUSPOLL_MEA_RELATIONS_SPRINT foreign key (Sprint_id)
		references Sprint (Sprint_id)
		on update restrict
		on delete restrict;
		
alter table DeveloperPoll_measurement_result
	add constraint FK_DEVPOLL_MEA_RELATIONS_MEASURE foreign key (Measure_id)
		references Measure (Measure_id)
		on update restrict
		on delete restrict;
		
alter table DeveloperPoll_measurement_result
	add constraint FK_DEVPOLL_MEA_RELATIONS_EMPLOYEE foreign key (Employee_id)
		references Employee (Employee_id)
		on update restrict
		on delete restrict;
		
alter table DeveloperPoll_measurement_result
	add constraint FK_DEVPOLL_MEA_RELATIONS_SPRINT foreign key (Sprint_id)
		references Sprint (Sprint_id)
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

insert into Impediment_type (Impediment_type_description) values ('Specification problems');
insert into Impediment_type (Impediment_type_description) values ('Hardware problems');
insert into Impediment_type (Impediment_type_description) values ('Software problems');
insert into Impediment_type (Impediment_type_description) values ('Security problems');
insert into Impediment_type (Impediment_type_description) values ('Teamwork problems');
insert into Impediment_type (Impediment_type_description) values ('Other');

insert into Impediment_status (Impediment_status_description) values ('Open');
insert into Impediment_status (Impediment_status_description) values ('Pending');
insert into Impediment_status (Impediment_status_description) values ('In Progress');
insert into Impediment_status (Impediment_status_description) values ('Closed');
insert into Impediment_status (Impediment_status_description) values ('Other');

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

insert into Sprint (Project_id, Team_id, Sprint_description, Sprint_begin_date, Sprint_end_date, Sprint_length, Sprint_estimated_date) values (1, 1, 'prvi sprint', '2009-01-01', '2009-01-01', 30, '2009-03-01');
insert into Sprint (Project_id, Team_id, Sprint_description, Sprint_begin_date, Sprint_end_date, Sprint_length, Sprint_estimated_date) values (1, 1, 'drugi sprint', '2009-01-02', '2009-01-02', 20, '2009-02-02');
insert into Sprint (Project_id, Team_id, Sprint_description, Sprint_begin_date, Sprint_end_date, Sprint_length, Sprint_estimated_date) values (2, 2, 'prvi sprint', '2009-01-03', '2009-04-03', 30, '2009-03-03');
insert into Sprint (Project_id, Team_id, Sprint_description, Sprint_begin_date, Sprint_end_date, Sprint_length, Sprint_estimated_date) values (2, 2, 'drugi sprint', '2009-01-04', '2009-03-04', 29, '2009-03-04');

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

insert into Task (Employee_id, Team_id, Task_status_id, Task_type_id, Task_description, Task_date, Task_active) values (1, 1, 1, 1, 'obrazec', '2009-04-30', 'yes');
insert into Task (Employee_id, Team_id, Task_status_id, Task_type_id, Task_description, Task_date, Task_active) values (2, 1, 2, 1, 'meni', '2009-04-30', 'yes');
insert into Task (Employee_id, Team_id, Task_status_id, Task_type_id, Task_description, Task_date, Task_active) values (1, 1, 3, 2, 'ikone', '2009-04-30', 'yes');
insert into Task (Employee_id, Team_id, Task_status_id, Task_type_id, Task_description, Task_date, Task_active) values (2, 1, 2, 3, 'modeli', '2009-04-30', 'yes');

insert into Measure (Measure_id, Measure_name, Measure_description) values(1,'q1_customer','Sodelovanje z razvojno skupino v èasu razvoja');
insert into Measure (Measure_id, Measure_name, Measure_description) values(2,'q2_customer','Odzivnost razvijalcev na želje, spremembe v zahtevah');
insert into Measure (Measure_id, Measure_name, Measure_description) values(3,'q3_customer','Popolnost izdelane rešitve');
insert into Measure (Measure_id, Measure_name, Measure_description) values(4,'q4_customer','Skladnost izdelane rešitve s prièakovanji');
insert into Measure (Measure_id, Measure_name, Measure_description) values(5,'q5_customer','Intuitivnost uporabniškega vmesnika');
insert into Measure (Measure_id, Measure_name, Measure_description) values(6,'q6_customer','Uporabnost/Koristnost izdelanih programov');
insert into Measure (Measure_id, Measure_name, Measure_description) values(7,'q7_customer','Odsotnost napak, zanesljivost delovanja, robustnost');
insert into Measure (Measure_id, Measure_name, Measure_description) values(8,'q8_customer','Kakovost oddanih Sprint Backlog-ov, pravoèasnost oddaje');
insert into Measure (Measure_id, Measure_name, Measure_description) values(9,'q9_customer','Splošna ocena zadovoljstva z delom razvojne skupine');
insert into Measure (Measure_id, Measure_name, Measure_description) values(10,'q10_customer','Splošna ocena kakovosti izdelanih programov');
insert into Measure (Measure_id, Measure_name, Measure_description) values(11,'q10_customer_comment','Komentar k splošni oceni kakovosti izdelanih programov');
insert into Measure (Measure_id, Measure_name, Measure_description) values(12,'q11_customer','Funkcionalnost, ki ni bila realizirana, oziroma je bila realizirana neustrezno. Poleg vsake
funkcionalnosti navedite prioriteto (1 – najvišja prioriteta):');

insert into Measure (Measure_id, Measure_name, Measure_description) values(13,'q1_developer','Jasnost zastavljenega Product Backlog-a');
insert into Measure (Measure_id, Measure_name, Measure_description) values(14,'q1_developer_comment','Komentar k jasnosti zastavljenega Product Backlog-a');
insert into Measure (Measure_id, Measure_name, Measure_description) values(15,'q2_developer','Ocena èasa za posamezno zahtevo v Product Backlogu');
insert into Measure (Measure_id, Measure_name, Measure_description) values(16,'q2_developer_comment','Komentar k oceni èasa za posamezno zahtevo v Product Backlogu');
insert into Measure (Measure_id, Measure_name, Measure_description) values(17,'q3_developer','Administracija pri metodologiji Scrum');
insert into Measure (Measure_id, Measure_name, Measure_description) values(18,'q3_developer_comment','Komentar k administraciji pri metodologiji Scrum');
insert into Measure (Measure_id, Measure_name, Measure_description) values(19,'q4_developer','Obremenjenost z administracijo');
insert into Measure (Measure_id, Measure_name, Measure_description) values(20,'q4_developer_comment','Komentar k obremenjenosti z administracijo');
insert into Measure (Measure_id, Measure_name, Measure_description) values(21,'q5_developer','Tehniène težave na zaèetku Sprinta');
insert into Measure (Measure_id, Measure_name, Measure_description) values(22,'q5_developer_comment','Komentar k tehniènim težavam na zaèetku Sprinta');
insert into Measure (Measure_id, Measure_name, Measure_description) values(23,'q6_developer','Vsebinske težave (razumevanje zahtevane funkcionalnosti) na zaèetku Sprinta');
insert into Measure (Measure_id, Measure_name, Measure_description) values(24,'q6_developer_comment','Komentar k vsebinskim težavam (razumevanje zahtevane funkcionalnosti) na zaèetku Sprinta');
insert into Measure (Measure_id, Measure_name, Measure_description) values(25,'q7_developer','Tehniène težave na koncu Sprinta');
insert into Measure (Measure_id, Measure_name, Measure_description) values(26,'q7_developer_comment','Komentar k tehniènim težavam na koncu Sprinta');
insert into Measure (Measure_id, Measure_name, Measure_description) values(27,'q8_developer','Vsebinske težave (razumevanje zahtevane funkcionalnosti) na koncu Sprinta');
insert into Measure (Measure_id, Measure_name, Measure_description) values(28,'q8_developer_comment','Komentar k vsebinskim težavam (razumevanje zahtevane funkcionalnosti) na koncu Sprinta');
insert into Measure (Measure_id, Measure_name, Measure_description) values(29,'q9_developer','Sodelovanje s Scrum Master-jem');
insert into Measure (Measure_id, Measure_name, Measure_description) values(30,'q9_developer_comment','Komentar k sodelovanju s Scrum Master-jem');
insert into Measure (Measure_id, Measure_name, Measure_description) values(31,'q10_developer','Sodelovanje s Product Owner-jem');
insert into Measure (Measure_id, Measure_name, Measure_description) values(32,'q10_developer_comment','Komentar k sodelovanju s Product Owner-jem');
insert into Measure (Measure_id, Measure_name, Measure_description) values(33,'q11_developer','Sodelovanje znotraj razvojne skupine');
insert into Measure (Measure_id, Measure_name, Measure_description) values(34,'q11_developer_comment','Komentar k sodelovanju znotraj razvojne skupine');
insert into Measure (Measure_id, Measure_name, Measure_description) values(35,'q12_developer','Primernost obsega dela na projektu');
insert into Measure (Measure_id, Measure_name, Measure_description) values(36,'q12_developer_comment','Komentar k primernosti obsega dela na projektu');
insert into Measure (Measure_id, Measure_name, Measure_description) values(37,'q13_developer','Splošna ocena vašega zadovoljstva s potekom dela na projektu');
insert into Measure (Measure_id, Measure_name, Measure_description) values(38,'q13_developer_comment','Komentar k splošni oceni vašega zadovoljstva s potekom dela na projektu');
insert into Measure (Measure_id, Measure_name, Measure_description) values(39,'q14_developer','Splošna ocena metodologije Scrum');
insert into Measure (Measure_id, Measure_name, Measure_description) values(40,'q14_developer_comment','Komentar k splošni oceni metodologije Scrum');
