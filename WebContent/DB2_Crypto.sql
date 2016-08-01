create database crypto_proj;
use crypto_proj;
#create table
#login
create table user (email varchar(30), pass varchar(30), id int(11) auto_increment, name varchar(30), fil longblob, apass varchar(30), primary key(id));
#group
create table group_dtl (user1 varchar(30), user2 varchar(30), group_name varchar(15), user1_status varchar(1), user2_status varchar(1), is_active varchar(1), unique_id int(10) auto_increment, primary key (unique_id));
#select
select * from user;
select * from group_dtl;
#drop tables
SHOW VARIABLES LIKE 'max_allowed_packet';
SET session max_allowed_packet=16M;
SET GLOBAL max_allowed_packet=16M;
SET SESSION max_allowed_packet=16M;
drop table user;
drop table group_dtl;
truncate table user;
#insert
insert into user (email, pass, id, name) values("SR957737","India123$",1,"Shanmugar");
insert into user (email, pass, id, name) values("VN957737","India123$",2,"Varun");
#test
delete from user where id>0;
select * from group_dtl where group_name = "573" and (user1 = "SR957737" or user2 = "SR957737");
update group_dtl set user1_status = ? where group_name = ? and (user1 = ? or user2 = ?);