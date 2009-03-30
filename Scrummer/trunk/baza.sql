/*==============================================================*/
/* DBMS name:      Sybase SQL Anywhere 10                       */
/* Created on:     8.3.2009 17:33:02                            */
/*==============================================================*/


if exists(select 1 from sys.sysforeignkey where role='FK_ADMINIST_RELATIONS_ABSENCE_') then
    alter table Administrative_days
       delete foreign key FK_ADMINIST_RELATIONS_ABSENCE_
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_ADMINIST_RELATIONS_EMPLOYEE') then
    alter table Administrative_days
       delete foreign key FK_ADMINIST_RELATIONS_EMPLOYEE
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_IMPEDIME_RELATIONS_SPRINT_T') then
    alter table Impediment
       delete foreign key FK_IMPEDIME_RELATIONS_SPRINT_T
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_IMPEDIME_RELATIONS_EMPLOYEE') then
    alter table Impediment
       delete foreign key FK_IMPEDIME_RELATIONS_EMPLOYEE
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_PBI_RELATIONS_PROJECT') then
    alter table PBI
       delete foreign key FK_PBI_RELATIONS_PROJECT
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_PBI_MEAS_RELATIONS_MEASURE') then
    alter table PBI_measurement_result
       delete foreign key FK_PBI_MEAS_RELATIONS_MEASURE
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_PBI_MEAS_RELATIONS_PBI') then
    alter table PBI_measurement_result
       delete foreign key FK_PBI_MEAS_RELATIONS_PBI
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_RELEASE__RELATIONS_PBI') then
    alter table Release_PBI
       delete foreign key FK_RELEASE__RELATIONS_PBI
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_RELEASE__RELATIONS_RELEASE') then
    alter table Release_PBI
       delete foreign key FK_RELEASE__RELATIONS_RELEASE
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_RELEASE__RELATIONS_MEASURE') then
    alter table Release_measurement_result
       delete foreign key FK_RELEASE__RELATIONS_MEASURE
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_RELEASE__RELATIONS_RELEASE') then
    alter table Release_measurement_result
       delete foreign key FK_RELEASE__RELATIONS_RELEASE
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_SPRINT_..._TEAM') then
    alter table Sprint
       delete foreign key "FK_SPRINT_..._TEAM"
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_SPRINT_RELATIONS_PROJECT') then
    alter table Sprint
       delete foreign key FK_SPRINT_RELATIONS_PROJECT
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_SPRINT_P_REFERENCE_TASK') then
    alter table Sprint_PBI
       delete foreign key FK_SPRINT_P_REFERENCE_TASK
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_SPRINT_P_RELATIONS_PBI') then
    alter table Sprint_PBI
       delete foreign key FK_SPRINT_P_RELATIONS_PBI
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_SPRINT_P_RELATIONS_SPRINT') then
    alter table Sprint_PBI
       delete foreign key FK_SPRINT_P_RELATIONS_SPRINT
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_SPRINT_M_RELATIONS_SPRINT') then
    alter table Sprint_measurement_result
       delete foreign key FK_SPRINT_M_RELATIONS_SPRINT
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_SPRINT_M_RELATIONS_MEASURE') then
    alter table Sprint_measurement_result
       delete foreign key FK_SPRINT_M_RELATIONS_MEASURE
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_SPRINT_T_RELATIONS_TEAM') then
    alter table Sprint_team
       delete foreign key FK_SPRINT_T_RELATIONS_TEAM
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_SPRINT_T_RELATIONS_SPRINT') then
    alter table Sprint_team
       delete foreign key FK_SPRINT_T_RELATIONS_SPRINT
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_TASK_RELATIONS_TASK_STA') then
    alter table Task
       delete foreign key FK_TASK_RELATIONS_TASK_STA
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_TASK_RELATIONS_TASK_TYP') then
    alter table Task
       delete foreign key FK_TASK_RELATIONS_TASK_TYP
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_TASK_RELATIONS_TEAM_MEM') then
    alter table Task
       delete foreign key FK_TASK_RELATIONS_TEAM_MEM
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_TASK_MEA_RELATIONS_MEASURE') then
    alter table Task_measurement_result
       delete foreign key FK_TASK_MEA_RELATIONS_MEASURE
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_TASK_MEA_RELATIONS_TASK') then
    alter table Task_measurement_result
       delete foreign key FK_TASK_MEA_RELATIONS_TASK
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_TEAM_MEM_RELATIONS_EMPLOYEE') then
    alter table Team_member
       delete foreign key FK_TEAM_MEM_RELATIONS_EMPLOYEE
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_TEAM_MEM_RELATIONS_TEAM') then
    alter table Team_member
       delete foreign key FK_TEAM_MEM_RELATIONS_TEAM
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Absence_type'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Absence_type
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Administrative_days'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Administrative_days
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Employee'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Employee
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Impediment'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Impediment
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Measure'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Measure
end if;

if exists(
   select 1 from sys.systable 
   where table_name='PBI'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table PBI
end if;

if exists(
   select 1 from sys.systable 
   where table_name='PBI_measurement_result'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table PBI_measurement_result
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Project'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Project
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Release'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table "Release"
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Release_PBI'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Release_PBI
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Release_measurement_result'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Release_measurement_result
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Sprint'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Sprint
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Sprint_PBI'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Sprint_PBI
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Sprint_measurement_result'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Sprint_measurement_result
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Sprint_team'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Sprint_team
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Task'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Task
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Task_measurement_result'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Task_measurement_result
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Task_status'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Task_status
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Task_type'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Task_type
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Team'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Team
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Team_member'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Team_member
end if;

/*==============================================================*/
/* Table: Absence_type                                          */
/*==============================================================*/
create table Absence_type 
(
   Absence_type_id      integer                        not null,
   Absence_type_description text                           null,
   constraint PK_ABSENCE_TYPE primary key (Absence_type_id)
);

/*==============================================================*/
/* Table: Administrative_days                                   */
/*==============================================================*/
create table Administrative_days 
(
   Employee_id          integer                        not null,
   Datum                text                           not null,
   Absence_type_id      integer                        null,
   Hours_not_worked     text                           null,
   constraint PK_ADMINISTRATIVE_DAYS primary key (Employee_id, Datum)
);

/*==============================================================*/
/* Table: Employee                                              */
/*==============================================================*/
create table Employee 
(
   Employee_id          integer	AUTO_INCREMENT	not null,
   Employee_name	text	null,
   Employee_surname	text	null,
   Employee_address	text	null,
   constraint PK_EMPLOYEE primary key (Employee_id)
);

/*==============================================================*/
/* Table: Impediment                                            */
/*==============================================================*/
create table Impediment 
(
   Impediment_id        integer                        not null,
   Team_id              integer                        null,
   Sprint_id            integer                        null,
   Employee_id          integer                        null,
   Impediment_description text                           null,
   Impediment_occurrence_date text                           null,
   Impediment_resolution_date text                           null,
   constraint PK_IMPEDIMENT primary key (Impediment_id)
);

/*==============================================================*/
/* Table: Measure                                               */
/*==============================================================*/
create table Measure 
(
   Measure_id           integer                        not null,
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
   Project_id           integer                        null,
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
   Datum                long varchar                   not null,
   constraint PK_PBI_MEASUREMENT_RESULT primary key (Measure_id, PBI_id, Datum)
);

/*==============================================================*/
/* Table: Project                                               */
/*==============================================================*/
create table Project 
(
   Project_id           integer	AUTO_INCREMENT	not null,
   Project_name	text	null,
   Project_description	text	null,
   constraint PK_PROJECT primary key (Project_id)
);

/*==============================================================*/
/* Table: "Release"                                             */
/*==============================================================*/
create table "Release" 
(
   Release_id           integer                        not null,
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
   Datum                text                           not null,
   constraint PK_RELEASE_MEASUREMENT_RESULT primary key (Measure_id, Release_id, Datum)
);

/*==============================================================*/
/* Table: Sprint                                                */
/*==============================================================*/
create table Sprint 
(
   Sprint_id            integer                        not null,
   Project_id           integer                        null,
   Team_id              integer                        null,
   Sprint_description   text                           null,
   Sprint_begin_date    text                           null,
   Sprint_end_date      text                           null,
   Sprint_length        text                           null,
   Sprint_estimated_date text                           null,
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
   Datum                text                           not null,
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
   Task_id              integer                        not null,
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
   Datum                text                           not null,
   constraint PK_TASK_MEASUREMENT_RESULT primary key (Measure_id, Task_id, Datum)
);

/*==============================================================*/
/* Table: Task_status                                           */
/*==============================================================*/
create table Task_status 
(
   Task_status_id       integer                        not null,
   Task_status_description text                           null,
   constraint PK_TASK_STATUS primary key (Task_status_id)
);

/*==============================================================*/
/* Table: Task_type                                             */
/*==============================================================*/
create table Task_type 
(
   Task_type_id         integer                        not null,
   Task_type_description text                           null,
   constraint PK_TASK_TYPE primary key (Task_type_id)
);

/*==============================================================*/
/* Table: Team                                                  */
/*==============================================================*/
create table Team 
(
   Team_id              integer                        not null,
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

alter table PBI
   add constraint FK_PBI_RELATIONS_PROJECT foreign key (Project_id)
      references Project (Project_id)
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
      references "Release" (Release_id)
      on update restrict
      on delete restrict;

alter table Release_measurement_result
   add constraint FK_RELEASE__RELATIONS_MEASURE foreign key (Measure_id)
      references Measure (Measure_id)
      on update restrict
      on delete restrict;

alter table Release_measurement_result
   add constraint FK_RELEASE__RELATIONS_RELEASE foreign key (Release_id)
      references "Release" (Release_id)
      on update restrict
      on delete restrict;

alter table Sprint
   add constraint "FK_SPRINT_..._TEAM" foreign key (Team_id)
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

