drop table if exists acnh_other_clothing;
create table acnh_other_clothing ("Index" int, Selected int, Name varchar(63), Status varchar(31), Price varchar(31), "From" varchar(63), Style varchar(31));
insert into acnh_other_clothing values(1, 0, 'horizontal-striped wet suit (Black)', 'orderable', '3000', 'Nook''s Cranny (Summer)', 'Active');
insert into acnh_other_clothing values(2, 0, 'horizontal-striped wet suit (Red)', 'orderable', '3000', 'Nook''s Cranny (Summer)', 'Active');
insert into acnh_other_clothing values(3, 0, 'horizontal-striped wet suit (Yellow)', 'orderable', '3000', 'Nook''s Cranny (Summer)', 'Active');
insert into acnh_other_clothing values(4, 0, 'horizontal-striped wet suit (Blue)', 'orderable', '3000', 'Nook''s Cranny (Summer)', 'Active');
insert into acnh_other_clothing values(5, 0, 'leaf-print wet suit (Yellow)', 'orderable', '3000', 'Nook Shopping Catalog (Summer)', 'Active');
insert into acnh_other_clothing values(6, 0, 'leaf-print wet suit (Green)', 'orderable', '3000', 'Nook Shopping Catalog (Summer)', 'Active');
insert into acnh_other_clothing values(7, 0, 'leaf-print wet suit (Light blue)', 'orderable', '3000', 'Nook Shopping Catalog (Summer)', 'Active');
insert into acnh_other_clothing values(8, 0, 'leaf-print wet suit (Purple)', 'orderable', '3000', 'Nook Shopping Catalog (Summer)', 'Active');
insert into acnh_other_clothing values(9, 0, 'Nook Inc. wet suit', 'unorderable', '4000', 'Nook Miles Shop (Summer)', 'Active');
drop table if exists acnh_sea_creature;
create table acnh_sea_creature ("Index" int, Selected int, Name varchar(63), Price varchar(15), Shadow varchar(15), "Catches to Unlock" varchar(7), "Times" varchar(127));
insert into acnh_sea_creature values(1, 0, 'seaweed', '600', 'Large', '0', 'All day');
insert into acnh_sea_creature values(2, 0, 'sea grapes', '900', 'Small', '0', 'All day');
insert into acnh_sea_creature values(3, 0, 'sea cucumber', '500', 'Medium', '0', 'All day');
insert into acnh_sea_creature values(4, 0, 'sea pig', '10000', 'Small', '80', '4 PM – 9 AM');
insert into acnh_sea_creature values(5, 0, 'sea star', '500', 'Small', '0', 'All day');
insert into acnh_sea_creature values(6, 0, 'sea urchin', '1700', 'Small', '0', 'All day');
insert into acnh_sea_creature values(7, 0, 'slate pencil urchin', '2000', 'Medium', '20', '4 PM – 9 AM');
insert into acnh_sea_creature values(8, 0, 'sea anemone', '500', 'Large', '0', 'All day');
insert into acnh_sea_creature values(9, 0, 'moon jellyfish', '600', 'Small', '0', 'All day');
insert into acnh_sea_creature values(10, 0, 'sea slug', '600', 'X-Small', '0', 'All day');
insert into acnh_sea_creature values(11, 0, 'pearl oyster', '2800', 'Small', '20', 'All day');
insert into acnh_sea_creature values(12, 0, 'mussel', '1500', 'Small', '0', 'All day');
insert into acnh_sea_creature values(13, 0, 'oyster', '1100', 'Small', '0', 'All day');
insert into acnh_sea_creature values(14, 0, 'scallop', '1200', 'Medium', '5', 'All day');
insert into acnh_sea_creature values(15, 0, 'whelk', '1000', 'Small', '0', 'All day');
insert into acnh_sea_creature values(16, 0, 'turban shell', '1000', 'Small', '0', 'All day');
insert into acnh_sea_creature values(17, 0, 'abalone', '2000', 'Medium', '20', '4 PM – 9 AM');
insert into acnh_sea_creature values(18, 0, 'gigas giant clam', '15000', 'X-Large', '80', 'All day');
insert into acnh_sea_creature values(19, 0, 'chambered nautilus', '1800', 'Medium', '20', '4 PM – 9 AM');
insert into acnh_sea_creature values(20, 0, 'octopus', '1200', 'Medium', '0', 'All day');
insert into acnh_sea_creature values(21, 0, 'umbrella octopus', '6000', 'Small', '40', 'All day');
insert into acnh_sea_creature values(22, 0, 'vampire squid', '10000', 'Medium', '80', '4 PM – 9 AM');
insert into acnh_sea_creature values(23, 0, 'firefly squid', '1400', 'X-Small', '0', '9 PM – 4 AM');
insert into acnh_sea_creature values(24, 0, 'gazami crab', '2200', 'Medium', '20', 'All day');
insert into acnh_sea_creature values(25, 0, 'Dungeness crab', '1900', 'Medium', '20', 'All day');
insert into acnh_sea_creature values(26, 0, 'snow crab', '6000', 'Large', '40', 'All day');
insert into acnh_sea_creature values(27, 0, 'red king crab', '8000', 'Large', '80', 'All day');
insert into acnh_sea_creature values(28, 0, 'acorn barnacle', '600', 'X-Small', '0', 'All day');
insert into acnh_sea_creature values(29, 0, 'spider crab', '12000', 'X-Large', '80', 'All day');
insert into acnh_sea_creature values(30, 0, 'tiger prawn', '3000', 'Small', '20', '4 PM – 9 AM');
insert into acnh_sea_creature values(31, 0, 'sweet shrimp', '1400', 'Small', '0', '4 PM – 9 AM');
insert into acnh_sea_creature values(32, 0, 'mantis shrimp', '2500', 'Small', '20', '4 PM – 9 AM');
insert into acnh_sea_creature values(33, 0, 'spiny lobster', '5000', 'Large', '40', '9 PM – 4 AM');
insert into acnh_sea_creature values(34, 0, 'lobster', '4500', 'Large', '40', 'All day');
insert into acnh_sea_creature values(35, 0, 'giant isopod', '12000', 'Medium', '80', '9 AM – 4 PM; 9 PM – 4 AM');
insert into acnh_sea_creature values(36, 0, 'horseshoe crab', '2500', 'Medium', '20', '9 PM – 4 AM');
insert into acnh_sea_creature values(37, 0, 'sea pineapple', '1500', 'Small', '0', 'All day');
insert into acnh_sea_creature values(38, 0, 'spotted garden eel', '1100', 'Small', '0', '4 AM – 9 PM');
insert into acnh_sea_creature values(39, 0, 'flatworm', '700', 'X-Small', '0', '4 PM – 9 AM');
insert into acnh_sea_creature values(40, 0, 'Venus'' flower basket', '5000', 'Medium', '40', 'All day');
drop table if exists acnh_jan_sea_creature;
drop table if exists acnh_feb_sea_creature;
drop table if exists acnh_mar_sea_creature;
drop table if exists acnh_apr_sea_creature;
drop table if exists acnh_may_sea_creature;
drop table if exists acnh_jun_sea_creature;
drop table if exists acnh_jul_sea_creature;
drop table if exists acnh_aug1_sea_creature;
drop table if exists acnh_aug2_sea_creature;
drop table if exists acnh_sep1_sea_creature;
drop table if exists acnh_sep2_sea_creature;
drop table if exists acnh_oct_sea_creature;
drop table if exists acnh_nov_sea_creature;
drop table if exists acnh_dec_sea_creature;
create table acnh_jan_sea_creature (id int);
create table acnh_feb_sea_creature (id int);
create table acnh_mar_sea_creature (id int);
create table acnh_apr_sea_creature (id int);
create table acnh_may_sea_creature (id int);
create table acnh_jun_sea_creature (id int);
create table acnh_jul_sea_creature (id int);
create table acnh_aug1_sea_creature (id int);
create table acnh_aug2_sea_creature (id int);
create table acnh_sep1_sea_creature (id int);
create table acnh_sep2_sea_creature (id int);
create table acnh_oct_sea_creature (id int);
create table acnh_nov_sea_creature (id int);
create table acnh_dec_sea_creature (id int);
insert into acnh_jan_sea_creature values (1);
insert into acnh_feb_sea_creature values (1);
insert into acnh_mar_sea_creature values (1);
insert into acnh_apr_sea_creature values (1);
insert into acnh_may_sea_creature values (1);
insert into acnh_jun_sea_creature values (1);
insert into acnh_jul_sea_creature values (1);
insert into acnh_oct_sea_creature values (1);
insert into acnh_nov_sea_creature values (1);
insert into acnh_dec_sea_creature values (1);
insert into acnh_jun_sea_creature values (2);
insert into acnh_jul_sea_creature values (2);
insert into acnh_aug1_sea_creature values (2);
insert into acnh_aug2_sea_creature values (2);
insert into acnh_sep1_sea_creature values (2);
insert into acnh_sep2_sea_creature values (2);
insert into acnh_jan_sea_creature values (3);
insert into acnh_feb_sea_creature values (3);
insert into acnh_mar_sea_creature values (3);
insert into acnh_apr_sea_creature values (3);
insert into acnh_nov_sea_creature values (3);
insert into acnh_dec_sea_creature values (3);
insert into acnh_jan_sea_creature values (4);
insert into acnh_feb_sea_creature values (4);
insert into acnh_nov_sea_creature values (4);
insert into acnh_dec_sea_creature values (4);
insert into acnh_jan_sea_creature values (5);
insert into acnh_feb_sea_creature values (5);
insert into acnh_mar_sea_creature values (5);
insert into acnh_apr_sea_creature values (5);
insert into acnh_may_sea_creature values (5);
insert into acnh_jun_sea_creature values (5);
insert into acnh_jul_sea_creature values (5);
insert into acnh_aug1_sea_creature values (5);
insert into acnh_aug2_sea_creature values (5);
insert into acnh_sep1_sea_creature values (5);
insert into acnh_sep2_sea_creature values (5);
insert into acnh_oct_sea_creature values (5);
insert into acnh_nov_sea_creature values (5);
insert into acnh_dec_sea_creature values (5);
insert into acnh_may_sea_creature values (6);
insert into acnh_jun_sea_creature values (6);
insert into acnh_jul_sea_creature values (6);
insert into acnh_aug1_sea_creature values (6);
insert into acnh_aug2_sea_creature values (6);
insert into acnh_sep1_sea_creature values (6);
insert into acnh_sep2_sea_creature values (6);
insert into acnh_may_sea_creature values (7);
insert into acnh_jun_sea_creature values (7);
insert into acnh_jul_sea_creature values (7);
insert into acnh_aug1_sea_creature values (7);
insert into acnh_aug2_sea_creature values (7);
insert into acnh_sep1_sea_creature values (7);
insert into acnh_sep2_sea_creature values (7);
insert into acnh_jan_sea_creature values (8);
insert into acnh_feb_sea_creature values (8);
insert into acnh_mar_sea_creature values (8);
insert into acnh_apr_sea_creature values (8);
insert into acnh_may_sea_creature values (8);
insert into acnh_jun_sea_creature values (8);
insert into acnh_jul_sea_creature values (8);
insert into acnh_aug1_sea_creature values (8);
insert into acnh_aug2_sea_creature values (8);
insert into acnh_sep1_sea_creature values (8);
insert into acnh_sep2_sea_creature values (8);
insert into acnh_oct_sea_creature values (8);
insert into acnh_nov_sea_creature values (8);
insert into acnh_dec_sea_creature values (8);
insert into acnh_jul_sea_creature values (9);
insert into acnh_aug1_sea_creature values (9);
insert into acnh_aug2_sea_creature values (9);
insert into acnh_sep1_sea_creature values (9);
insert into acnh_sep2_sea_creature values (9);
insert into acnh_jan_sea_creature values (10);
insert into acnh_feb_sea_creature values (10);
insert into acnh_mar_sea_creature values (10);
insert into acnh_apr_sea_creature values (10);
insert into acnh_may_sea_creature values (10);
insert into acnh_jun_sea_creature values (10);
insert into acnh_jul_sea_creature values (10);
insert into acnh_aug1_sea_creature values (10);
insert into acnh_aug2_sea_creature values (10);
insert into acnh_sep1_sea_creature values (10);
insert into acnh_sep2_sea_creature values (10);
insert into acnh_oct_sea_creature values (10);
insert into acnh_nov_sea_creature values (10);
insert into acnh_dec_sea_creature values (10);
insert into acnh_jan_sea_creature values (11);
insert into acnh_feb_sea_creature values (11);
insert into acnh_mar_sea_creature values (11);
insert into acnh_apr_sea_creature values (11);
insert into acnh_may_sea_creature values (11);
insert into acnh_jun_sea_creature values (11);
insert into acnh_jul_sea_creature values (11);
insert into acnh_aug1_sea_creature values (11);
insert into acnh_aug2_sea_creature values (11);
insert into acnh_sep1_sea_creature values (11);
insert into acnh_sep2_sea_creature values (11);
insert into acnh_oct_sea_creature values (11);
insert into acnh_nov_sea_creature values (11);
insert into acnh_dec_sea_creature values (11);
insert into acnh_jun_sea_creature values (12);
insert into acnh_jul_sea_creature values (12);
insert into acnh_aug1_sea_creature values (12);
insert into acnh_aug2_sea_creature values (12);
insert into acnh_sep1_sea_creature values (12);
insert into acnh_sep2_sea_creature values (12);
insert into acnh_oct_sea_creature values (12);
insert into acnh_nov_sea_creature values (12);
insert into acnh_dec_sea_creature values (12);
insert into acnh_jan_sea_creature values (13);
insert into acnh_feb_sea_creature values (13);
insert into acnh_sep1_sea_creature values (13);
insert into acnh_sep2_sea_creature values (13);
insert into acnh_oct_sea_creature values (13);
insert into acnh_nov_sea_creature values (13);
insert into acnh_dec_sea_creature values (13);
insert into acnh_jan_sea_creature values (14);
insert into acnh_feb_sea_creature values (14);
insert into acnh_mar_sea_creature values (14);
insert into acnh_apr_sea_creature values (14);
insert into acnh_may_sea_creature values (14);
insert into acnh_jun_sea_creature values (14);
insert into acnh_jul_sea_creature values (14);
insert into acnh_aug1_sea_creature values (14);
insert into acnh_aug2_sea_creature values (14);
insert into acnh_sep1_sea_creature values (14);
insert into acnh_sep2_sea_creature values (14);
insert into acnh_oct_sea_creature values (14);
insert into acnh_nov_sea_creature values (14);
insert into acnh_dec_sea_creature values (14);
insert into acnh_jan_sea_creature values (15);
insert into acnh_feb_sea_creature values (15);
insert into acnh_mar_sea_creature values (15);
insert into acnh_apr_sea_creature values (15);
insert into acnh_may_sea_creature values (15);
insert into acnh_jun_sea_creature values (15);
insert into acnh_jul_sea_creature values (15);
insert into acnh_aug1_sea_creature values (15);
insert into acnh_aug2_sea_creature values (15);
insert into acnh_sep1_sea_creature values (15);
insert into acnh_sep2_sea_creature values (15);
insert into acnh_oct_sea_creature values (15);
insert into acnh_nov_sea_creature values (15);
insert into acnh_dec_sea_creature values (15);
insert into acnh_mar_sea_creature values (16);
insert into acnh_apr_sea_creature values (16);
insert into acnh_may_sea_creature values (16);
insert into acnh_sep1_sea_creature values (16);
insert into acnh_sep2_sea_creature values (16);
insert into acnh_oct_sea_creature values (16);
insert into acnh_nov_sea_creature values (16);
insert into acnh_dec_sea_creature values (16);
insert into acnh_jan_sea_creature values (17);
insert into acnh_jun_sea_creature values (17);
insert into acnh_jul_sea_creature values (17);
insert into acnh_aug1_sea_creature values (17);
insert into acnh_aug2_sea_creature values (17);
insert into acnh_sep1_sea_creature values (17);
insert into acnh_sep2_sea_creature values (17);
insert into acnh_oct_sea_creature values (17);
insert into acnh_nov_sea_creature values (17);
insert into acnh_dec_sea_creature values (17);
insert into acnh_may_sea_creature values (18);
insert into acnh_jun_sea_creature values (18);
insert into acnh_jul_sea_creature values (18);
insert into acnh_aug1_sea_creature values (18);
insert into acnh_aug2_sea_creature values (18);
insert into acnh_sep1_sea_creature values (18);
insert into acnh_sep2_sea_creature values (18);
insert into acnh_mar_sea_creature values (19);
insert into acnh_apr_sea_creature values (19);
insert into acnh_may_sea_creature values (19);
insert into acnh_jun_sea_creature values (19);
insert into acnh_sep1_sea_creature values (19);
insert into acnh_sep2_sea_creature values (19);
insert into acnh_oct_sea_creature values (19);
insert into acnh_nov_sea_creature values (19);
insert into acnh_jan_sea_creature values (20);
insert into acnh_feb_sea_creature values (20);
insert into acnh_mar_sea_creature values (20);
insert into acnh_apr_sea_creature values (20);
insert into acnh_may_sea_creature values (20);
insert into acnh_jun_sea_creature values (20);
insert into acnh_jul_sea_creature values (20);
insert into acnh_aug1_sea_creature values (20);
insert into acnh_aug2_sea_creature values (20);
insert into acnh_sep1_sea_creature values (20);
insert into acnh_sep2_sea_creature values (20);
insert into acnh_oct_sea_creature values (20);
insert into acnh_nov_sea_creature values (20);
insert into acnh_dec_sea_creature values (20);
insert into acnh_mar_sea_creature values (21);
insert into acnh_apr_sea_creature values (21);
insert into acnh_may_sea_creature values (21);
insert into acnh_sep1_sea_creature values (21);
insert into acnh_sep2_sea_creature values (21);
insert into acnh_oct_sea_creature values (21);
insert into acnh_nov_sea_creature values (21);
insert into acnh_may_sea_creature values (22);
insert into acnh_jun_sea_creature values (22);
insert into acnh_jul_sea_creature values (22);
insert into acnh_aug1_sea_creature values (22);
insert into acnh_aug2_sea_creature values (22);
insert into acnh_mar_sea_creature values (23);
insert into acnh_apr_sea_creature values (23);
insert into acnh_may_sea_creature values (23);
insert into acnh_jun_sea_creature values (23);
insert into acnh_jun_sea_creature values (24);
insert into acnh_jul_sea_creature values (24);
insert into acnh_aug1_sea_creature values (24);
insert into acnh_aug2_sea_creature values (24);
insert into acnh_sep1_sea_creature values (24);
insert into acnh_sep2_sea_creature values (24);
insert into acnh_oct_sea_creature values (24);
insert into acnh_nov_sea_creature values (24);
insert into acnh_jan_sea_creature values (25);
insert into acnh_feb_sea_creature values (25);
insert into acnh_mar_sea_creature values (25);
insert into acnh_apr_sea_creature values (25);
insert into acnh_may_sea_creature values (25);
insert into acnh_nov_sea_creature values (25);
insert into acnh_dec_sea_creature values (25);
insert into acnh_jan_sea_creature values (26);
insert into acnh_feb_sea_creature values (26);
insert into acnh_mar_sea_creature values (26);
insert into acnh_apr_sea_creature values (26);
insert into acnh_nov_sea_creature values (26);
insert into acnh_dec_sea_creature values (26);
insert into acnh_jan_sea_creature values (27);
insert into acnh_feb_sea_creature values (27);
insert into acnh_mar_sea_creature values (27);
insert into acnh_nov_sea_creature values (27);
insert into acnh_dec_sea_creature values (27);
insert into acnh_jan_sea_creature values (28);
insert into acnh_feb_sea_creature values (28);
insert into acnh_mar_sea_creature values (28);
insert into acnh_apr_sea_creature values (28);
insert into acnh_may_sea_creature values (28);
insert into acnh_jun_sea_creature values (28);
insert into acnh_jul_sea_creature values (28);
insert into acnh_aug1_sea_creature values (28);
insert into acnh_aug2_sea_creature values (28);
insert into acnh_sep1_sea_creature values (28);
insert into acnh_sep2_sea_creature values (28);
insert into acnh_oct_sea_creature values (28);
insert into acnh_nov_sea_creature values (28);
insert into acnh_dec_sea_creature values (28);
insert into acnh_mar_sea_creature values (29);
insert into acnh_apr_sea_creature values (29);
insert into acnh_jun_sea_creature values (30);
insert into acnh_jul_sea_creature values (30);
insert into acnh_aug1_sea_creature values (30);
insert into acnh_aug2_sea_creature values (30);
insert into acnh_sep1_sea_creature values (30);
insert into acnh_sep2_sea_creature values (30);
insert into acnh_jan_sea_creature values (31);
insert into acnh_feb_sea_creature values (31);
insert into acnh_sep1_sea_creature values (31);
insert into acnh_sep2_sea_creature values (31);
insert into acnh_oct_sea_creature values (31);
insert into acnh_nov_sea_creature values (31);
insert into acnh_dec_sea_creature values (31);
insert into acnh_jan_sea_creature values (32);
insert into acnh_feb_sea_creature values (32);
insert into acnh_mar_sea_creature values (32);
insert into acnh_apr_sea_creature values (32);
insert into acnh_may_sea_creature values (32);
insert into acnh_jun_sea_creature values (32);
insert into acnh_jul_sea_creature values (32);
insert into acnh_aug1_sea_creature values (32);
insert into acnh_aug2_sea_creature values (32);
insert into acnh_sep1_sea_creature values (32);
insert into acnh_sep2_sea_creature values (32);
insert into acnh_oct_sea_creature values (32);
insert into acnh_nov_sea_creature values (32);
insert into acnh_dec_sea_creature values (32);
insert into acnh_oct_sea_creature values (33);
insert into acnh_nov_sea_creature values (33);
insert into acnh_dec_sea_creature values (33);
insert into acnh_jan_sea_creature values (34);
insert into acnh_apr_sea_creature values (34);
insert into acnh_may_sea_creature values (34);
insert into acnh_jun_sea_creature values (34);
insert into acnh_dec_sea_creature values (34);
insert into acnh_jul_sea_creature values (35);
insert into acnh_aug1_sea_creature values (35);
insert into acnh_aug2_sea_creature values (35);
insert into acnh_sep1_sea_creature values (35);
insert into acnh_sep2_sea_creature values (35);
insert into acnh_oct_sea_creature values (35);
insert into acnh_jul_sea_creature values (36);
insert into acnh_aug1_sea_creature values (36);
insert into acnh_aug2_sea_creature values (36);
insert into acnh_sep1_sea_creature values (36);
insert into acnh_sep2_sea_creature values (36);
insert into acnh_apr_sea_creature values (37);
insert into acnh_may_sea_creature values (37);
insert into acnh_jun_sea_creature values (37);
insert into acnh_jul_sea_creature values (37);
insert into acnh_aug1_sea_creature values (37);
insert into acnh_aug2_sea_creature values (37);
insert into acnh_may_sea_creature values (38);
insert into acnh_jun_sea_creature values (38);
insert into acnh_jul_sea_creature values (38);
insert into acnh_aug1_sea_creature values (38);
insert into acnh_aug2_sea_creature values (38);
insert into acnh_sep1_sea_creature values (38);
insert into acnh_sep2_sea_creature values (38);
insert into acnh_oct_sea_creature values (38);
insert into acnh_aug1_sea_creature values (39);
insert into acnh_aug2_sea_creature values (39);
insert into acnh_sep1_sea_creature values (39);
insert into acnh_sep2_sea_creature values (39);
insert into acnh_jan_sea_creature values (40);
insert into acnh_feb_sea_creature values (40);
insert into acnh_oct_sea_creature values (40);
insert into acnh_nov_sea_creature values (40);
insert into acnh_dec_sea_creature values (40);
drop table if exists acnh_table;
create table acnh_table ("Table" varchar(63));
insert into acnh_table values ('accessory'), ('art'), ('bag'), ('bottom'), ('dress'), ('fencing'), ('fish'), ('flooring'), ('fossil'), ('headwear'), ('houseware'), ('hybrid'), ('insect'), ('misc'), ("other_clothing"), ('photo'), ('poster'), ('recipe'), ('rug'), ("sea_creature"), ('shoe'), ('sock'), ('song'), ('tool'), ('top'), ('umbrella'), ('wall_mounted'), ('wallpaper');
