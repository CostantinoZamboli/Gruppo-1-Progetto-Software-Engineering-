drop table if exists Skill cascade;
drop table if exists Typologies cascade;
drop table if exists Site cascade;
drop table if exists Maintenance_Skill cascade;
drop table if exists Maintenance_Procedures cascade;
drop table if exists Workspace_Notes cascade;
drop table if exists Maintainer cascade;
drop table if exists Maintainer_Skill cascade;
drop table if exists Week cascade;
drop table if exists Maintainer_Availability cascade;
drop table if exists Prova cascade;
drop table if exists Time_Slot cascade;
create table Prova(
	
	id_maintainer integer,
	nome varchar(20),
	week integer,
	avail_lun integer default 0,
	avail_mart integer default 0,
	avail_merc integer default 0,
	avail_giov integer default 0,
	avail_ven integer default 0,
	avail_sab integer default 0,
	avail_dom integer default 0,
	primary key (id_maintainer, week)
);

create table Time_Slot(
	id_maintainer integer,
	nome varchar(20),
	week integer default 0,
	giorno varchar(10) default '0',
	avail_8_9 varchar(10) default 0,
	avail_9_10 varchar(10) default 0,
	avail_10_11 varchar(10) default 0,
	avail_11_12 varchar(10) default 0,
	avail_12_13 varchar(10) default 0,
	avail_13_14 varchar(10) default 0,
	avail_14_15 varchar(10) default 0
);
create table Skill(
	competenza varchar(50),
	id_skill integer primary key
);

create table Typologies(
	tipologia varchar(20),
	id_tipologia integer primary key
);

create table Workspace_Notes(
	id_workspace_notes integer primary key,
	description varchar(50)
);

create table Site(
	nome varchar(20),
	area varchar(20),
	id_site integer primary key,
	id_workspace_notes integer references Workspace_Notes(id_workspace_notes)
);


create table Maintenance_Procedures(
	id_procedure integer primary key,
	intervention_description varchar(50),
	intervention_time integer,
	interruptible_activity boolean,
	week integer,
	giorno integer,
	smp varchar(100),
	tipologia integer references Typologies(id_tipologia)
	on update restrict on delete restrict,
	sito integer references Site(id_site)
	on update restrict on delete restrict

);


create table Maintenance_Skill(
	id_procedure integer references Maintenance_Procedures(id_procedure)
	on update restrict on delete restrict,
	competencies integer references Skill(id_skill)
	on update restrict on delete restrict,
	primary key(id_procedure,competencies)
);



insert into Skill values('PAV certification',1);
insert into Skill values('Electrical maintenance',2);
insert into Skill values('Knowledge of cable types',3);
insert into Skill values('Robot knowledge',4);
insert into Skill values('Knowledge of robot workstation',5);

insert into Typologies values('Electrical',1);
insert into Typologies values('Electronic',2);
insert into Typologies values('Hydraulic',3);
insert into Typologies values('Mechanical',4);

insert into Workspace_Notes values(1,'The plant is closed from 00 to 7');
insert into Workspace_Notes values(7,'The plant is closed from 00 to 7');
insert into Workspace_Notes values(2,'The plant is closed from 00 to 6');
insert into Workspace_Notes values(3,'The plant is closed from 00 to 5');
insert into Workspace_Notes values(4,'The plant is closed from 00 to 4');
insert into Workspace_Notes values(5,'The plant is closed from 00 to 3');
insert into Workspace_Notes values(6,'The plant is closed from 00 to 2');

select * from Workspace_Notes;

insert into Site values('Fisciano','Molding',1,6);
insert into Site values('Fisciano','Painting',2,5);
insert into Site values('Nusco','Carpentry',3,4);
insert into Site values('Morra','Painting',4,7);
insert into Site values('Morra','Molding',5,3);

insert into Maintenance_Procedures values(1,'replacement of robot',120,TRUE,49,15,'OverviewLinguaggioSQJDBCeApplicazioni.pdf',1,3);
insert into Maintenance_Procedures values(2,'replacement of cables',30,TRUE,50,NULL,'OverviewLinguaggioSQJDBCeApplicazioni.pdf',2,4);
insert into Maintenance_Procedures values(3,'replacement of pc',70,TRUE,50,15,'OverviewLinguaggioSQJDBCeApplicazioni.pdf',3,5);
insert into Maintenance_Procedures values(4,'replacement of workstation',20,FALSE,49,NULL,'OverviewLinguaggioSQJDBCeApplicazioni.pdf',4,1);
insert into Maintenance_Procedures values(5,'replacement of robot',90,TRUE,49,15,'OverviewLinguaggioSQJDBCeApplicazioni.pdf',2,3);
insert into Maintenance_Procedures values(6,'check workstation 7',30,TRUE,49,NULL,'Ingegneriainformatica_Magistrale_1_Comune_06227.pdf',2,4);
insert into Maintenance_Procedures values(7,'check workstation 8',30,TRUE,49,NULL,'Ingegneriainformatica_Magistrale_1_Comune_06227.pdf',2,4);
insert into Maintenance_Procedures values(8,'check workstation 9',30,TRUE,50,NULL,'Ingegneriainformatica_Magistrale_1_Comune_06227.pdf',2,4);
insert into Maintenance_Procedures values(9,'check workstation 10',30,TRUE,49,NULL,'Ingegneriainformatica_Magistrale_1_Comune_06227.pdf',2,3);


insert into Maintenance_Skill values (1,1);
insert into Maintenance_Skill values (1,2);
insert into Maintenance_Skill values (1,3);
insert into Maintenance_Skill values (2,2);
insert into Maintenance_Skill values (2,3);
insert into Maintenance_Skill values(2,4);
insert into Maintenance_Skill values(3,1);
insert into Maintenance_Skill values(3,3);
insert into Maintenance_Skill values(3,5);
insert into Maintenance_Skill values(4,2);
insert into Maintenance_Skill values(4,4);
insert into Maintenance_Skill values(5,5);
insert into Maintenance_Skill values(5,1);
insert into Maintenance_Skill values(6,1);
insert into Maintenance_Skill values(6,5);
insert into Maintenance_Skill values(7,1);
insert into Maintenance_Skill values(7,2);
insert into Maintenance_Skill values(7,3);
insert into Maintenance_Skill values(7,4);
insert into Maintenance_Skill values(8,1);
insert into Maintenance_Skill values(8,2);
insert into Maintenance_Skill values(8,3);


select * from Maintenance_Skill;
select * from Site;


create or replace view Planned_activity (id_activity,site,area,typology,intervention_time,week, intervention_description, smp,workspace_notes,id_workspace_notes) as
select Maintenance_Procedures.id_procedure, Site.nome, Site.area, Typologies.tipologia, Maintenance_Procedures.intervention_time,Maintenance_Procedures.week, 
	   Maintenance_Procedures.intervention_description, smp, Workspace_Notes.description, Workspace_Notes.id_workspace_notes
from Maintenance_Procedures join Site on Maintenance_Procedures.sito = Site.id_site join Typologies on Typologies.id_tipologia =Maintenance_Procedures.tipologia
							join Workspace_Notes on Workspace_Notes.id_workspace_notes = Site.id_workspace_notes
where Maintenance_Procedures.giorno is NULL;

select * from Planned_Activity;
select * from Maintenance_Procedures;

create or replace view Activity_Selection_Skill(id_activity,skill, id_skill) as
select Maintenance_Procedures.id_procedure, Skill.competenza, Skill.id_skill
from Maintenance_Procedures join Maintenance_Skill on Maintenance_Procedures.id_procedure = 
Maintenance_Skill.id_procedure join Skill on Maintenance_Skill.competencies = Skill.id_skill;


select * from Activity_Selection_Skill;



create table Maintainer(
	nome varchar(20),
	id_maintainer integer primary key,
	email varchar(50)
);

select * from Maintainer;

create table Maintainer_Skill(
	id_maintainer integer references Maintainer(id_maintainer)
	on update restrict on delete restrict,
	competencies integer references Skill(id_skill)
	on update restrict on delete restrict,
	primary key(id_maintainer,competencies)
);

create table Week(
id_week integer,
giorno varchar(10),
primary key (id_week,giorno)
);

create table Maintainer_Availability(
	id_availability integer primary key,
	giorno varchar(10),
	disponibilita integer default 0,
	ora_inizio time not null,
	ora_fine  time not null,
	id_week integer,
	id_maintainer integer references Maintainer(id_maintainer)
	on update restrict on delete restrict,
	constraint  fk_to_Week foreign key (id_week,giorno)  references Week(id_week,giorno)
	on update restrict on delete restrict
);


create or replace function insert_maintainer_week() returns trigger as $$
begin
	if((select count(*) from Prova 
		where id_maintainer = NEW.id_maintainer and week = NEW.id_week) = 0)
	then 
		insert into Prova(id_maintainer, week) values (NEW.id_maintainer, NEW.id_week);
		update Prova set nome = (select Maintainer.nome 
										from Maintainer_Availability join Maintainer on Maintainer.id_maintainer = Maintainer_Availability.id_maintainer
										where Maintainer_Availability.id_maintainer = NEW.id_maintainer) where Prova.id_maintainer = NEW.id_maintainer and week = NEW.id_week;
	end if;
	return null;
end;
$$ language plpgsql;

create trigger trig_insert_maintainer
after insert
on Maintainer_Availability
for each row
execute procedure insert_maintainer_week();


select * from Maintainer_Availability;

insert into Maintainer values ('Pippo',1,'c.villani17@studenti.unisa.it');
insert into Maintainer values ('Pluto',2,'ftroiano9933@gmail.com');
insert into Maintainer values ('Paperino',3,'ciostantino1999@gmail.com');
insert into Maintainer values ('Zimeo',4,'c.zamboli@studenti.unisa.it');

select * from Maintainer;

insert into Week values(49,'Monday');
insert into Week values(49,'Tuesday');
insert into Week values(49,'Wednesday');
insert into Week values(49,'Thursday');
insert into Week values(49,'Friday');
insert into Week values(49,'Saturday');
insert into Week values(49,'Sunday');
insert into Week values(50,'Monday');
insert into Week values(50,'Tuesday');
insert into Week values(50,'Wednesday');
insert into Week values(50,'Thursday');
insert into Week values(50,'Friday');
insert into Week values(50,'Saturday');
insert into Week values(50,'Sunday');

select * from Week;

insert into Maintainer_Skill values (1,1);
insert into Maintainer_Skill values (1,2);
insert into Maintainer_Skill values (2,1);
insert into Maintainer_Skill values (2,2);
insert into Maintainer_Skill values (1,3);
insert into Maintainer_Skill values (3,4);
insert into Maintainer_Skill values (1,4);
insert into Maintainer_Skill values (3,1);
insert into Maintainer_Skill values (4,1);
insert into Maintainer_Skill values (1,5);
insert into Maintainer_Skill values (2,5);
insert into Maintainer_Skill values (3,5);
insert into Maintainer_Skill values (4,5);

select * from Maintainer_Skill;

create or replace function insert_availability() returns trigger as $$
begin
	update Prova set week = NEW.id_week where id_maintainer = NEW.id_maintainer;
	if NEW.giorno = 'Monday'
	then update Prova set avail_lun = (select avg(disponibilita)/60 * 100 
									   from Maintainer_Availability
									   where id_maintainer = NEW.id_maintainer and giorno = NEW.giorno and week = NEW.id_week) 
		 where id_maintainer = NEW.id_maintainer;
	end if;
	
	if NEW.giorno = 'Tuesday'
	then update Prova set avail_mart = (select avg(disponibilita)/60 * 100 
									   from Maintainer_Availability
									   where id_maintainer = NEW.id_maintainer and giorno = NEW.giorno and week = NEW.id_week) 
		 where id_maintainer = NEW.id_maintainer;
	end if;
	
	if NEW.giorno = 'Wednesday'
	then update Prova set avail_merc = (select avg(disponibilita)/60 * 100 
									   from Maintainer_Availability
									   where id_maintainer = NEW.id_maintainer and giorno = NEW.giorno and week = NEW.id_week) 
		 where id_maintainer = NEW.id_maintainer;
	end if;
	
	if NEW.giorno = 'Thursday'
	then update Prova set avail_giov = (select avg(disponibilita)/60 * 100 
									   from Maintainer_Availability
									   where id_maintainer = NEW.id_maintainer and giorno = NEW.giorno and week = NEW.id_week) 
		 where id_maintainer = NEW.id_maintainer;
	end if;
	
	if NEW.giorno = 'Friday'
	then update Prova set avail_ven = (select avg(disponibilita)/60 * 100 
									   from Maintainer_Availability
									   where id_maintainer = NEW.id_maintainer and giorno = NEW.giorno and week = NEW.id_week) 
		 where id_maintainer = NEW.id_maintainer;
	end if;
	
	if NEW.giorno = 'Saturday'
	then update Prova set avail_sab = (select avg(disponibilita)/60 * 100 
									   from Maintainer_Availability
									   where id_maintainer = NEW.id_maintainer and giorno = NEW.giorno and week = NEW.id_week) 
		 where id_maintainer = NEW.id_maintainer;
	end if;
	
	if NEW.giorno = 'Sunday'
	then update Prova set avail_dom = (select avg(disponibilita)/60 * 100 
									   from Maintainer_Availability
									   where id_maintainer = NEW.id_maintainer and giorno = NEW.giorno and week = NEW.id_week) 
		 where id_maintainer = NEW.id_maintainer;
	end if;
	
	return null;
end;
$$ language plpgsql;

create trigger trig_insert_availability
after insert
on Maintainer_Availability
for each row
execute procedure insert_availability();


insert into Maintainer_Availability values(1,'Monday',50,'08:00:00', '09:00:00',49,1);
insert into Maintainer_Availability values(2,'Monday',10,'09:00:00', '10:00:00',49,1);
insert into Maintainer_Availability values(3,'Monday',15,'10:00:00', '11:00:00',49,1);
insert into Maintainer_Availability values(4,'Monday',60,'11:00:00', '12:00:00',49,1);
insert into Maintainer_Availability values(5,'Monday',25,'12:00:00', '13:00:00',49,1);
insert into Maintainer_Availability values(6,'Monday',35,'13:00:00', '14:00:00',49,1);
insert into Maintainer_Availability values(7,'Monday',50,'14:00:00', '15:00:00',49,1);

insert into Maintainer_Availability values(8,'Tuesday',25,'08:00:00', '09:00:00',49,1);
insert into Maintainer_Availability values(9,'Tuesday',45,'09:00:00', '10:00:00',49,1);
insert into Maintainer_Availability values(10,'Tuesday',55,'10:00:00', '11:00:00',49,1);
insert into Maintainer_Availability values(11,'Tuesday',60,'11:00:00', '12:00:00',49,1);
insert into Maintainer_Availability values(12,'Tuesday',25,'12:00:00', '13:00:00',49,1);
insert into Maintainer_Availability values(13,'Tuesday',15,'13:00:00', '14:00:00',49,1);
insert into Maintainer_Availability values(14,'Tuesday',20,'14:00:00', '15:00:00',49,1);

insert into Maintainer_Availability values(15,'Wednesday',20,'08:00:00', '09:00:00',49,1);
insert into Maintainer_Availability values(16,'Wednesday',30,'09:00:00', '10:00:00',49,1);
insert into Maintainer_Availability values(17,'Wednesday',50,'10:00:00', '11:00:00',49,1);
insert into Maintainer_Availability values(18,'Wednesday',60,'11:00:00', '12:00:00',49,1);
insert into Maintainer_Availability values(19,'Wednesday',35,'12:00:00', '13:00:00',49,1);
insert into Maintainer_Availability values(20,'Wednesday',55,'13:00:00', '14:00:00',49,1);
insert into Maintainer_Availability values(21,'Wednesday',60,'14:00:00', '15:00:00',49,1);

insert into Maintainer_Availability values(22,'Thursday',20,'08:00:00', '09:00:00',49,1);
insert into Maintainer_Availability values(23,'Thursday',30,'09:00:00', '10:00:00',49,1);
insert into Maintainer_Availability values(24,'Thursday',40,'10:00:00', '11:00:00',49,1);
insert into Maintainer_Availability values(25,'Thursday',50,'11:00:00', '12:00:00',49,1);
insert into Maintainer_Availability values(26,'Thursday',60,'12:00:00', '13:00:00',49,1);
insert into Maintainer_Availability values(27,'Thursday',10,'13:00:00', '14:00:00',49,1);
insert into Maintainer_Availability values(28,'Thursday',45,'14:00:00', '15:00:00',49,1);

insert into Maintainer_Availability values(29,'Friday',20,'08:00:00', '09:00:00',49,1);
insert into Maintainer_Availability values(30,'Friday',50,'09:00:00', '10:00:00',49,1);
insert into Maintainer_Availability values(31,'Friday',60,'10:00:00', '11:00:00',49,1);
insert into Maintainer_Availability values(32,'Friday',60,'11:00:00', '12:00:00',49,1);
insert into Maintainer_Availability values(33,'Friday',35,'12:00:00', '13:00:00',49,1);
insert into Maintainer_Availability values(34,'Friday',25,'13:00:00', '14:00:00',49,1);
insert into Maintainer_Availability values(35,'Friday',15,'14:00:00', '15:00:00',49,1);

insert into Maintainer_Availability values(36,'Saturday',25,'08:00:00', '09:00:00',49,1);
insert into Maintainer_Availability values(37,'Saturday',55,'09:00:00', '10:00:00',49,1);
insert into Maintainer_Availability values(38,'Saturday',60,'10:00:00', '11:00:00',49,1);
insert into Maintainer_Availability values(39,'Saturday',40,'11:00:00', '12:00:00',49,1);
insert into Maintainer_Availability values(40,'Saturday',25,'12:00:00', '13:00:00',49,1);
insert into Maintainer_Availability values(41,'Saturday',15,'13:00:00', '14:00:00',49,1);
insert into Maintainer_Availability values(42,'Saturday',10,'14:00:00', '15:00:00',49,1);

insert into Maintainer_Availability values(43,'Sunday',55,'08:00:00', '09:00:00',49,1);
insert into Maintainer_Availability values(44,'Sunday',60,'09:00:00', '10:00:00',49,1);
insert into Maintainer_Availability values(45,'Sunday',40,'10:00:00', '11:00:00',49,1);
insert into Maintainer_Availability values(46,'Sunday',20,'11:00:00', '12:00:00',49,1);
insert into Maintainer_Availability values(47,'Sunday',30,'12:00:00', '13:00:00',49,1);
insert into Maintainer_Availability values(48,'Sunday',35,'13:00:00', '14:00:00',49,1);
insert into Maintainer_Availability values(49,'Sunday',25,'14:00:00', '15:00:00',49,1);

insert into Maintainer_Availability values(50,'Monday',35,'08:00:00', '09:00:00',49,2);
insert into Maintainer_Availability values(51,'Monday',20,'09:00:00', '10:00:00',49,2);
insert into Maintainer_Availability values(52,'Monday',60,'10:00:00', '11:00:00',49,2);
insert into Maintainer_Availability values(53,'Monday',40,'11:00:00', '12:00:00',49,2);
insert into Maintainer_Availability values(54,'Monday',15,'12:00:00', '13:00:00',49,2);
insert into Maintainer_Availability values(55,'Monday',20,'13:00:00', '14:00:00',49,2);
insert into Maintainer_Availability values(56,'Monday',25,'14:00:00', '15:00:00',49,2);

insert into Maintainer_Availability values(57,'Tuesday',55,'08:00:00', '09:00:00',49,2);
insert into Maintainer_Availability values(58,'Tuesday',20,'09:00:00', '10:00:00',49,2);
insert into Maintainer_Availability values(59,'Tuesday',30,'10:00:00', '11:00:00',49,2);
insert into Maintainer_Availability values(60,'Tuesday',60,'11:00:00', '12:00:00',49,2);
insert into Maintainer_Availability values(61,'Tuesday',45,'12:00:00', '13:00:00',49,2);
insert into Maintainer_Availability values(62,'Tuesday',30,'13:00:00', '14:00:00',49,2);
insert into Maintainer_Availability values(63,'Tuesday',15,'14:00:00', '15:00:00',49,2);

insert into Maintainer_Availability values(64,'Wednesday',10,'08:00:00', '09:00:00',49,2);
insert into Maintainer_Availability values(65,'Wednesday',15,'09:00:00', '10:00:00',49,2);
insert into Maintainer_Availability values(66,'Wednesday',10,'10:00:00', '11:00:00',49,2);
insert into Maintainer_Availability values(67,'Wednesday',50,'11:00:00', '12:00:00',49,2);
insert into Maintainer_Availability values(68,'Wednesday',55,'12:00:00', '13:00:00',49,2);
insert into Maintainer_Availability values(69,'Wednesday',30,'13:00:00', '14:00:00',49,2);
insert into Maintainer_Availability values(70,'Wednesday',35,'14:00:00', '15:00:00',49,2);

insert into Maintainer_Availability values(71,'Thursday',20,'08:00:00', '09:00:00',49,2);
insert into Maintainer_Availability values(72,'Thursday',25,'09:00:00', '10:00:00',49,2);
insert into Maintainer_Availability values(73,'Thursday',35,'10:00:00', '11:00:00',49,2);
insert into Maintainer_Availability values(74,'Thursday',50,'11:00:00', '12:00:00',49,2);
insert into Maintainer_Availability values(75,'Thursday',60,'12:00:00', '13:00:00',49,2);
insert into Maintainer_Availability values(76,'Thursday',15,'13:00:00', '14:00:00',49,2);
insert into Maintainer_Availability values(77,'Thursday',20,'14:00:00', '15:00:00',49,2);

insert into Maintainer_Availability values(78,'Friday',30,'08:00:00', '09:00:00',49,2);
insert into Maintainer_Availability values(79,'Friday',50,'09:00:00', '10:00:00',49,2);
insert into Maintainer_Availability values(80,'Friday',15,'10:00:00', '11:00:00',49,2);
insert into Maintainer_Availability values(81,'Friday',20,'11:00:00', '12:00:00',49,2);
insert into Maintainer_Availability values(82,'Friday',25,'12:00:00', '13:00:00',49,2);
insert into Maintainer_Availability values(83,'Friday',30,'13:00:00', '14:00:00',49,2);
insert into Maintainer_Availability values(84,'Friday',25,'14:00:00', '15:00:00',49,2);

insert into Maintainer_Availability values(85,'Saturday',60,'08:00:00', '09:00:00',49,2);
insert into Maintainer_Availability values(86,'Saturday',15,'09:00:00', '10:00:00',49,2);
insert into Maintainer_Availability values(87,'Saturday',20,'10:00:00', '11:00:00',49,2);
insert into Maintainer_Availability values(88,'Saturday',30,'11:00:00', '12:00:00',49,2);
insert into Maintainer_Availability values(89,'Saturday',35,'12:00:00', '13:00:00',49,2);
insert into Maintainer_Availability values(90,'Saturday',45,'13:00:00', '14:00:00',49,2);
insert into Maintainer_Availability values(91,'Saturday',15,'14:00:00', '15:00:00',49,2);

insert into Maintainer_Availability values(92,'Sunday',20,'08:00:00', '09:00:00',49,2);
insert into Maintainer_Availability values(93,'Sunday',30,'09:00:00', '10:00:00',49,2);
insert into Maintainer_Availability values(94,'Sunday',25,'10:00:00', '11:00:00',49,2);
insert into Maintainer_Availability values(95,'Sunday',15,'11:00:00', '12:00:00',49,2);
insert into Maintainer_Availability values(96,'Sunday',55,'12:00:00', '13:00:00',49,2);
insert into Maintainer_Availability values(97,'Sunday',60,'13:00:00', '14:00:00',49,2);
insert into Maintainer_Availability values(98,'Sunday',45,'14:00:00', '15:00:00',49,2);

insert into Maintainer_Availability values(100,'Monday',60,'08:00:00','09:00:00',50,3);
insert into Maintainer_Availability values(101,'Monday',50,'09:00:00','10:00:00',50,3);
insert into Maintainer_Availability values(102,'Monday',40,'10:00:00','11:00:00',50,3);
insert into Maintainer_Availability values(103,'Monday',60,'11:00:00','12:00:00',50,3);
insert into Maintainer_Availability values(104,'Monday',20,'12:00:00','13:00:00',50,3);
insert into Maintainer_Availability values(105,'Monday',0,'13:00:00','14:00:00',50,3);
insert into Maintainer_Availability values(106,'Monday',10,'14:00:00','15:00:00',50,3);

insert into Maintainer_Availability values(107,'Tuesday',20,'08:00:00','09:00:00',50,3);
insert into Maintainer_Availability values(108,'Tuesday',10,'09:00:00','10:00:00',50,3);
insert into Maintainer_Availability values(109,'Tuesday',60,'10:00:00','11:00:00',50,3);
insert into Maintainer_Availability values(110,'Tuesday',50,'11:00:00','12:00:00',50,3);
insert into Maintainer_Availability values(111,'Tuesday',60,'12:00:00','13:00:00',50,3);
insert into Maintainer_Availability values(112,'Tuesday',40,'13:00:00','14:00:00',50,3);
insert into Maintainer_Availability values(113,'Tuesday',0,'14:00:00','15:00:00',50,3);

insert into Maintainer_Availability values(114,'Wednesday',40,'08:00:00','09:00:00',50,3);
insert into Maintainer_Availability values(115,'Wednesday',30,'09:00:00','10:00:00',50,3);
insert into Maintainer_Availability values(116,'Wednesday',20,'10:00:00','11:00:00',50,3);
insert into Maintainer_Availability values(117,'Wednesday',60,'11:00:00','12:00:00',50,3);
insert into Maintainer_Availability values(118,'Wednesday',10,'12:00:00','13:00:00',50,3);
insert into Maintainer_Availability values(119,'Wednesday',0,'13:00:00','14:00:00',50,3);
insert into Maintainer_Availability values(120,'Wednesday',30,'14:00:00','15:00:00',50,3);

insert into Maintainer_Availability values(121,'Thursday',10,'08:00:00','09:00:00',50,3);
insert into Maintainer_Availability values(122,'Thursday',20,'09:00:00','10:00:00',50,3);
insert into Maintainer_Availability values(123,'Thursday',30,'10:00:00','11:00:00',50,3);
insert into Maintainer_Availability values(124,'Thursday',40,'11:00:00','12:00:00',50,3);
insert into Maintainer_Availability values(125,'Thursday',50,'12:00:00','13:00:00',50,3);
insert into Maintainer_Availability values(126,'Thursday',60,'13:00:00','14:00:00',50,3);
insert into Maintainer_Availability values(127,'Thursday',0,'14:00:00','15:00:00',50,3);

insert into Maintainer_Availability values(128,'Friday',60,'08:00:00','09:00:00',50,3);
insert into Maintainer_Availability values(129,'Friday',50,'09:00:00','10:00:00',50,3);
insert into Maintainer_Availability values(130,'Friday',20,'10:00:00','11:00:00',50,3);
insert into Maintainer_Availability values(131,'Friday',10,'11:00:00','12:00:00',50,3);
insert into Maintainer_Availability values(132,'Friday',60,'12:00:00','13:00:00',50,3);
insert into Maintainer_Availability values(133,'Friday',0,'13:00:00','14:00:00',50,3);
insert into Maintainer_Availability values(134,'Friday',30,'14:00:00','15:00:00',50,3);

insert into Maintainer_Availability values(135,'Saturday',0,'08:00:00','09:00:00',50,3);
insert into Maintainer_Availability values(136,'Saturday',10,'09:00:00','10:00:00',50,3);
insert into Maintainer_Availability values(137,'Saturday',20,'10:00:00','11:00:00',50,3);
insert into Maintainer_Availability values(138,'Saturday',30,'11:00:00','12:00:00',50,3);
insert into Maintainer_Availability values(139,'Saturday',40,'12:00:00','13:00:00',50,3);
insert into Maintainer_Availability values(140,'Saturday',50,'13:00:00','14:00:00',50,3);
insert into Maintainer_Availability values(141,'Saturday',60,'14:00:00','15:00:00',50,3);

insert into Maintainer_Availability values(142,'Sunday',20,'08:00:00','09:00:00',50,3);
insert into Maintainer_Availability values(143,'Sunday',30,'09:00:00','10:00:00',50,3);
insert into Maintainer_Availability values(144,'Sunday',60,'10:00:00','11:00:00',50,3);
insert into Maintainer_Availability values(145,'Sunday',50,'11:00:00','12:00:00',50,3);
insert into Maintainer_Availability values(146,'Sunday',40,'12:00:00','13:00:00',50,3);
insert into Maintainer_Availability values(147,'Sunday',20,'13:00:00','14:00:00',50,3);
insert into Maintainer_Availability values(148,'Sunday',0,'14:00:00','15:00:00',50,3);

insert into Maintainer_Availability values(151,'Monday',35,'08:00:00', '09:00:00',50,4);
insert into Maintainer_Availability values(152,'Monday',20,'09:00:00', '10:00:00',50,4);
insert into Maintainer_Availability values(153,'Monday',60,'10:00:00', '11:00:00',50,4);
insert into Maintainer_Availability values(154,'Monday',40,'11:00:00', '12:00:00',50,4);
insert into Maintainer_Availability values(155,'Monday',15,'12:00:00', '13:00:00',50,4);
insert into Maintainer_Availability values(156,'Monday',20,'13:00:00', '14:00:00',50,4);
insert into Maintainer_Availability values(157,'Monday',25,'14:00:00', '15:00:00',50,4);

insert into Maintainer_Availability values(158,'Tuesday',55,'08:00:00', '09:00:00',50,4);
insert into Maintainer_Availability values(159,'Tuesday',20,'09:00:00', '10:00:00',50,4);
insert into Maintainer_Availability values(160,'Tuesday',30,'10:00:00', '11:00:00',50,4);
insert into Maintainer_Availability values(161,'Tuesday',60,'11:00:00', '12:00:00',50,4);
insert into Maintainer_Availability values(162,'Tuesday',45,'12:00:00', '13:00:00',50,4);
insert into Maintainer_Availability values(163,'Tuesday',35,'13:00:00', '14:00:00',50,4);
insert into Maintainer_Availability values(164,'Tuesday',15,'14:00:00', '15:00:00',50,4);

insert into Maintainer_Availability values(165,'Wednesday',10,'08:00:00', '09:00:00',50,4);
insert into Maintainer_Availability values(166,'Wednesday',15,'09:00:00', '10:00:00',50,4);
insert into Maintainer_Availability values(167,'Wednesday',10,'10:00:00', '11:00:00',50,4);
insert into Maintainer_Availability values(168,'Wednesday',50,'11:00:00', '12:00:00',50,4);
insert into Maintainer_Availability values(169,'Wednesday',55,'12:00:00', '13:00:00',50,4);
insert into Maintainer_Availability values(170,'Wednesday',30,'13:00:00', '14:00:00',50,4);
insert into Maintainer_Availability values(171,'Wednesday',35,'14:00:00', '15:00:00',50,4);

insert into Maintainer_Availability values(172,'Thursday',20,'08:00:00', '09:00:00',50,4);
insert into Maintainer_Availability values(173,'Thursday',25,'09:00:00', '10:00:00',50,4);
insert into Maintainer_Availability values(174,'Thursday',35,'10:00:00', '11:00:00',50,4);
insert into Maintainer_Availability values(175,'Thursday',55,'11:00:00', '12:00:00',50,4);
insert into Maintainer_Availability values(176,'Thursday',60,'12:00:00', '13:00:00',50,4);
insert into Maintainer_Availability values(177,'Thursday',15,'13:00:00', '14:00:00',50,4);
insert into Maintainer_Availability values(178,'Thursday',20,'14:00:00', '15:00:00',50,4);

insert into Maintainer_Availability values(179,'Friday',30,'08:00:00', '09:00:00',50,4);
insert into Maintainer_Availability values(180,'Friday',50,'09:00:00', '10:00:00',50,4);
insert into Maintainer_Availability values(181,'Friday',15,'10:00:00', '11:00:00',50,4);
insert into Maintainer_Availability values(182,'Friday',20,'11:00:00', '12:00:00',50,4);
insert into Maintainer_Availability values(183,'Friday',25,'12:00:00', '13:00:00',50,4);
insert into Maintainer_Availability values(184,'Friday',30,'13:00:00', '14:00:00',50,4);
insert into Maintainer_Availability values(185,'Friday',25,'14:00:00', '15:00:00',50,4);

insert into Maintainer_Availability values(186,'Saturday',60,'08:00:00', '09:00:00',50,4);
insert into Maintainer_Availability values(187,'Saturday',15,'09:00:00', '10:00:00',50,4);
insert into Maintainer_Availability values(188,'Saturday',20,'10:00:00', '11:00:00',50,4);
insert into Maintainer_Availability values(189,'Saturday',30,'11:00:00', '12:00:00',50,4);
insert into Maintainer_Availability values(190,'Saturday',35,'12:00:00', '13:00:00',50,4);
insert into Maintainer_Availability values(191,'Saturday',45,'13:00:00', '14:00:00',50,4);
insert into Maintainer_Availability values(192,'Saturday',15,'14:00:00', '15:00:00',50,4);

insert into Maintainer_Availability values(193,'Sunday',20,'08:00:00', '09:00:00',50,4);
insert into Maintainer_Availability values(194,'Sunday',30,'09:00:00', '10:00:00',50,4);
insert into Maintainer_Availability values(195,'Sunday',25,'10:00:00', '11:00:00',50,4);
insert into Maintainer_Availability values(196,'Sunday',15,'11:00:00', '12:00:00',50,4);
insert into Maintainer_Availability values(197,'Sunday',55,'12:00:00', '13:00:00',50,4);
insert into Maintainer_Availability values(198,'Sunday',60,'13:00:00', '14:00:00',50,4);
insert into Maintainer_Availability values(199,'Sunday',45,'14:00:00', '15:00:00',50,4);


select * from Maintainer_Availability;


create or replace view Maintainer_Name_Week(giorno,disponibilita,id_maintainer,nome,settimana) as
select Maintainer_Availability.giorno,Maintainer_Availability.disponibilita,Maintainer.id_maintainer,Maintainer.nome, Maintainer_Availability.id_week
from Maintainer_Availability join Maintainer on Maintainer_Availability.id_maintainer = Maintainer.id_maintainer;

select * from Maintainer_Name_Week;
select * from Prova;


select count(*)
from(
	select Maintenance_Skill.competencies
	from Maintenance_Skill
	where id_procedure = 9
	intersect 
	select Maintainer_Skill.competencies
	from Maintainer_Skill
	where id_maintainer = 1)as skill_match;

select * from Maintenance_Skill;
select * from Maintainer_Skill;



/*
create table Maintainer_Availability(
	giorno varchar(10),
	disponibilita integer default 0,
	ora_inizio time not null,
	ora_fine  time not null,
	id_week integer,
	id_maintainer integer references Maintainer(id_maintainer)
	on update restrict on delete restrict,
	constraint  fk_to_Week foreign key (id_week,giorno)  references Week(id_week,giorno)
	on update restrict on delete restrict,
	primary key(id_week,giorno,id_maintainer)
);*/


select disponibilita  from Maintainer_Availability where Maintainer_Availability.id_maintainer = 1 and id_week = 49 and giorno ='Monday' order by id_availability;

select * from Prova;

SELECT * FROM Activity_Selection_Skill where id_activity = 9;

select * from Workspace_Notes;

select nome from Maintainer where id_maintainer = 1;

select * from Planned_Activity;