/*==============================================================*/
/* Created on:      1/3/2016                                    */
/* updated on:      1/3/2016                                    */
/*==============================================================*/
drop table if exists TROLE_PERMISSION;
drop table if exists TUSER_ROLE;
drop table if exists TPERMISSION;
drop table if exists TROLE;
drop table if exists TAPP;
drop table if exists TUSER;
drop table if exists TSEQUENCE;

create table TUSER (
  USER_ID int not null primary key,
  USER_NAME varchar(50) not null,
  USER_PASSWORD varchar(100) not null,
  USER_EMAIL varchar(50),
  USER_LOGINTIME datetime,
  USER_RESTPASSUUID  varchar(50),
  USER_ISSYS char(1) default '0' not null
)ENGINE=InnoDB;
alter table TUSER add unique (USER_NAME);

create table TAPP (
  APP_ID int not null primary key,
  APP_NAME varchar(50) not null
)ENGINE=InnoDB;

create table TROLE (
  ROLE_ID int not null primary key,
  ROLE_NAME varchar(50) not null,
  IS_DEFAULT char(1) default 'N' not null,
  APP_ID int not null
)ENGINE=InnoDB;
alter table TROLE add constraint FK_TROLE_TAPP foreign key (APP_ID) references TAPP (APP_ID) on delete cascade;
alter table TROLE add unique (ROLE_NAME,APP_ID);

create table TPERMISSION (
  PERMISSION_ID int not null primary key,
  PERMISSION_NAME varchar(50) not null,
  APP_ID int not null,
  CN_NAME varchar(50) not null,
  IS_DEFAULT char(1) default 'N' not null
)ENGINE=InnoDB;
alter table TPERMISSION add constraint FK_TPERMISSION_TAPP foreign key (APP_ID) references TAPP (APP_ID) on delete cascade;
alter table TPERMISSION add unique (PERMISSION_NAME,APP_ID);

create table TUSER_ROLE (
  USER_ID int not null,
  ROLE_ID int not null
)ENGINE=InnoDB;
alter table TUSER_ROLE add constraint pk_tuser_role primary key(USER_ID,ROLE_ID);
alter table TUSER_ROLE add constraint FK_TUSER_ROLE_TUSER foreign key (USER_ID) references TUSER (USER_ID) on delete cascade;
alter table TUSER_ROLE add constraint FK_TUSER_ROLE_TROLE foreign key (ROLE_ID) references TROLE (ROLE_ID) on delete cascade;

create table TROLE_PERMISSION (
  ROLE_ID int not null,
  PERMISSION_ID int not null
)ENGINE=InnoDB;
alter table TROLE_PERMISSION add constraint pk_tole_permission primary key(ROLE_ID,PERMISSION_ID);
alter table TROLE_PERMISSION add constraint FK_TROLE_PERMISSION_TROLE foreign key (ROLE_ID) references TROLE (ROLE_ID) on delete cascade;
alter table TROLE_PERMISSION add constraint FK_TROLE_PERMISSION_TPERMISSION foreign key (PERMISSION_ID) references TPERMISSION (PERMISSION_ID) on delete cascade;

create table TSEQUENCE (
  SEQ_NAME varchar(60) not null primary key,
  NEXT_VALUE int not null
)ENGINE=InnoDB;

/* init app */
insert into TAPP(APP_ID, APP_NAME) values (1, 'UMS-Admin');
insert into TAPP(APP_ID, APP_NAME) values (2, 'H5Designer');

/* init user */
insert into TUSER(USER_ID, USER_NAME, USER_PASSWORD, USER_EMAIL, USER_LOGINTIME, USER_RESTPASSUUID,USER_ISSYS) 
          values (1, 'admin', '36045b4295b9f7b9eed5b6a6b3eb44545b566858a0b2058450782782fdb156f5',null,null,null,1);
		  
/* init role */
insert into TROLE(ROLE_ID,ROLE_NAME,APP_ID,IS_DEFAULT) values(1,'administrator',1,'N');
insert into TROLE(ROLE_ID,ROLE_NAME,APP_ID,IS_DEFAULT) values(2,'default',1,'Y');
insert into TROLE(ROLE_ID,ROLE_NAME,APP_ID,IS_DEFAULT) values(3,'administrator',2,'N');


/* init user and role relationship */
insert into TUSER_ROLE(USER_ID,ROLE_ID) values (1,1);
insert into TUSER_ROLE(USER_ID,ROLE_ID) values (1,3);
/* init permissions */
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (1,'user:add', 1,'添加用户','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (2,'user:delete', 1,'删除用户','N'); 
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (3,'user:edit', 1,'编辑用户','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (4,'user:view', 1,'查看用户','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (5,'user:query', 1,'查询用户','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (6,'user:viewOwn', 1,'查看个人信息','Y');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (7,'user:editOwn', 1,'编辑个人信息','Y');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (8,'user:changeOwnPassword', 1,'修改个人密码','Y');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (9,'user:changeUserPassword', 1,'修改用户密码','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (10,'role:query', 1,'查询角色','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (11,'role:add', 1,'添加角色','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (12,'role:delete', 1,'删除角色','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (13,'role:view', 1,'查看角色','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (14,'role:edit', 1,'编辑角色','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (15,'project:getProjects', 2,'查看项目','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (16,'project:goPackagePage ', 2,'编译项目','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (17,'project:getTemplateProjectList', 2,'新增项目','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (18,'proejct:goEditProject', 2,'编辑项目','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (19,'project:deleteProject', 2,'删除项目','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (20,'project:releaseProject ', 2,'释放项目','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (21,'project:isExistProjectCache', 2,'打开项目','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (22,'template:business:getTemplateBusinesses', 2,'查看业务模板','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (23,'template:business:deleteTemplateBusiness', 2,'删除业务模板','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (24,'template:page:getTemplatePages', 2,'查看页面模板','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (25,'template:page:deleteTemplatePage', 2,'删除页面模板','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (26,'template:project:getTemplateProjects', 2,'查看项目模板','N');
insert into TPERMISSION(PERMISSION_ID,PERMISSION_NAME,APP_ID,CN_NAME,IS_DEFAULT) values (27,'template:project:deleteTemplateProject', 2,'删除项目模板','N');

/* init role permissions relationship */
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (1,1);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (1,2);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (1,3);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (1,4);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (1,5);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (1,6);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (1,7);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (1,8);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (1,9);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (1,10);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (1,11);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (1,12);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (1,13);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (1,14);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (3,15);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (3,16);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (3,17);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (3,18);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (3,19);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (3,20);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (3,21);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (3,22);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (3,23);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (3,24);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (3,25);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (3,26);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (3,27);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (2,6);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (2,7);
insert into TROLE_PERMISSION(ROLE_ID,PERMISSION_ID) values (2,8);


/* init sequence */
insert into TSEQUENCE(SEQ_NAME, NEXT_VALUE) values ('USER_ID', 2);
insert into TSEQUENCE(SEQ_NAME, NEXT_VALUE) values ('ROLE_ID', 2);
