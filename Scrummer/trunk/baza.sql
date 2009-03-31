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
alter table uask_measurement_result drop foreign key FK_TASK_MEA_RELATIONS_MEASURE;
alter table Task_measurement_result drop foreign key FK_TASK_MEA_RELATIONS_TASK;
alter table Team_member drop foreign key FK_TEAM_MEM_RELATIONS_EMPLOYEE;
alter table Team_member drop foreign key FK_TEAM_MEM_RELATIONS_TEAM;

drop table Absence_type;
drop table Administrative_days;
drop table Employee;
drop table Impediment;
drop table Measure;
drop table PBI;
drop table PBI_measurement_result;
drop table Project;
drop table FinalRelease;
drop table Release_PBI;
drop table Release_measurement_result;
drop table Sprint;
drop table Sprint_PBI;
drop table Sprint_measurement_result;
drop table Sprint_team;
drop table Task;
drop table Task_measurement_result;
drop table Task_status;
drop table Task_type;
drop table Team;
drop table Team_member;

/*==============================================================*/
/* Table: Absence_type                                          */
/*==============================================================*/
create table Absence_type 
(
   Absence_type_id      integer	AUTO_INCREMENT             not null,
   Absence_type_description text                           null,
   constraint PK_ABSENCE_TYPE primary key (Absence_type_id)
);

/*==============================================================*/
/* Table: Administrative_days                                   */
/*==============================================================*/
create table Administrative_days 
(
   Employee_id          integer                        not null,
   Absence_type_id      integer                        not null,
   Hours_not_worked     decimal                        not null,
   constraint PK_ADMINISTRATIVE_DAYS primary key (Employee_id)
);

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
);

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
);

/*==============================================================*/
/* Table: Measure                                               */
/*==============================================================*/
create table Measure 
(
   Measure_id           integer	AUTO_INCREMENT                     not null,
   Measure_name         text                           null,
   Measure_description  text                           null,
   constraint PK_MEASURE primary key (Measure_id)
);

/*==============================================================*/
/* Table: PBI                                                   */
/*==============================================================*/
create table PBI 
(
   PBI_id               integer	AUTO_INCREMENT	not null,
   Project_id           integer	not null,
   Sprint_id	integer	not null,
   PBI_description      text                           null,
   PBI_priority         text                           null,
   PBI_initial_estimate	text	null,
   PBI_adjustment_factor	text	null,
   PBI_adjusted_estimate	text	null,
   constraint PK_PBI primary key (PBI_id)
);

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
);

/*==============================================================*/
/* Table: Project                                               */
/*==============================================================*/
create table Project 
(
   Project_id           integer	AUTO_INCREMENT	not null,
   Project_name			text					not null,
   Project_description	text					not null,
   constraint PK_PROJECT primary key (Project_id)
);

/*==============================================================*/
/* Table: "FinalRelease"                                             */
/*==============================================================*/
create table FinalRelease
(
   Release_id           integer	AUTO_INCREMENT         not null,
   Release_description  text                           null,
   constraint PK_RELEASE primary key (Release_id)
);

/*==============================================================*/
/* Table: Release_PBI                                           */
/*==============================================================*/
create table Release_PBI 
(
   PBI_id               integer                        not null,
   Release_id           integer                        not null,
   constraint PK_RELEASE_PBI primary key (PBI_id, Release_id)
);

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
);

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
);

/*==============================================================*/
/* Table: Sprint_PBI                                            */
/*==============================================================*/
create table Sprint_PBI 
(
   PBI_id               integer                        not null,
   Sprint_id            integer                        not null,
   Task_id              integer                        null,
   Sprint_PBI_priority  text                           null,
   Sprint_PBI_status    text                           null,
   constraint PK_SPRINT_PBI primary key (PBI_id, Sprint_id)
);

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
);

/*==============================================================*/
/* Table: Sprint_team                                           */
/*==============================================================*/
create table Sprint_team 
(
   Team_id              integer                        not null,
   Sprint_id            integer                        not null,
   constraint PK_SPRINT_TEAM primary key (Team_id, Sprint_id)
);

/*==============================================================*/
/* Table: Task                                                  */
/*==============================================================*/
create table Task 
(
   Task_id              integer  	AUTO_INCREMENT                      not null,
   Employee_id          integer                        null,
   Team_id              integer                        null,
   Task_status_id       integer                        null,
   Task_type_id         integer                        null,
   Task_description     text                           null,
   Task_cost_of_engineering_hour text                           null,
   Task_date            text                           null,
   Task_active          text                           null,
   constraint PK_TASK primary key (Task_id)
);

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
);

/*==============================================================*/
/* Table: Task_status                                           */
/*==============================================================*/
create table Task_status 
(
   Task_status_id       integer	AUTO_INCREMENT                        not null,
   Task_status_description text                           null,
   constraint PK_TASK_STATUS primary key (Task_status_id)
);

/*==============================================================*/
/* Table: Task_type                                             */
/*==============================================================*/
create table Task_type 
(
   Task_type_id         integer	AUTO_INCREMENT                        not null,
   Task_type_description text                           null,
   constraint PK_TASK_TYPE primary key (Task_type_id)
);

/*==============================================================*/
/* Table: Team                                                  */
/*==============================================================*/
create table Team 
(
   Team_id              integer	AUTO_INCREMENT                        not null,
   Team_description     text                           null,
   constraint PK_TEAM primary key (Team_id)
);

/*==============================================================*/
/* Table: Team_member                                           */
/*==============================================================*/
create table Team_member 
(
   Employee_id          integer                        not null,
   Team_id              integer                        not null,
   Percentage_of_engagement_in_project integer                        null,
   constraint PK_TEAM_MEMBER primary key (Employee_id, Team_id)
);

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

