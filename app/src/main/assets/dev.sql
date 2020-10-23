insert into acww_sep1_fish values (24), (25);
insert into acww_sep2_fish values (24), (25);
insert into acww_oct_fish values (24), (25);
insert into acww_nov_fish values (24), (25);
update acww_fish set "Times" = 'March through June and September through November, all day (best time is March and April)' where "Index" = 23;
update acww_fish set "Times" = 'March through June and September through November, 4am to 9am and 4pm to 9pm' where "Index" = 24;
update acww_fish set "Times" = 'March through June and September through November, all day (best time is March through May)' where "Index" = 25;