/* fill association table with values */
INSERT INTO association
VALUES (1, 'DBV');

/* fill club table with values */
INSERT INTO club(id, name, city, association_id)
VALUES (1, 'Mannheim Tornados', 'Mannheim', 1);
INSERT INTO club(id, name, city, association_id)
VALUES (2, 'Untouchables Paderborn', 'Paderborn', 1);
INSERT INTO club(id, name, city, association_id)
VALUES (3, 'Mainz Athletics', 'Mainz', 1);
INSERT INTO club(id, name, city, association_id)
VALUES (4, 'Berlin Challengers', 'Berlin', 1);
INSERT INTO club(id, name, city, association_id)
VALUES (5, 'Bonn Capitals', 'Bonn', 1);
INSERT INTO club(id, name, city, association_id)
VALUES (6, 'Solingen Alligators', 'Solingen', 1);
INSERT INTO club(id, name, city, association_id)
VALUES (7, 'Heidenheim Heideköpfe', 'Heidenheim', 1);

/* fill game table with values */
INSERT INTO game(game_nr, date, end_time, start_time, time_of_game, location, scorer_passnumber, innings, attendance,
                 association_id, gamehistory_id)
VALUES (value1, value2, value3, ...);

/* fill game_history table with values */
INSERT INTO game_history(id, player_passnumber, position, position_counter,
                         pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs, sh,
                         sf, a, po, e, dp, ip)
VALUES (value1, value2, value3, ...);

/* fill league table with values */
INSERT INTO game_umpire(id, game_nr, umpire_passnumber)
VALUES (value1, value2, value3, ...);

/* fill game_umpire table with values */
INSERT INTO league (id, name, association_id)
VALUES (1, '1. Bundesliga', 1);
INSERT INTO league (id, name, association_id)
VALUES (2, '2. Bundesliga', 1);
INSERT INTO league (id, name, association_id)
VALUES (3, 'Verbandsliga', 1);
INSERT INTO league (id, name, association_id)
VALUES (4, '1. Landesliga', 1);
INSERT INTO league (id, name, association_id)
VALUES (5, '1. Bezirksliga', 1);

/* fill manager table with values */
INSERT INTO manager(id, first_name, last_name, email)
VALUES (1, 'Klaus', 'Eckle', 'klaus.eckle@managermail.com');
/*Heideköpfe*/

/* fill player table with values */
INSERT INTO player(passnumber, first_name, last_name, jersey_nr,
                   pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs, sh,
                   sf, a, po, e, dp, ip)
VALUES (value1, value2, value3, ...);

/* fill scorer table with values */
INSERT INTO scorer(passnumber, first_name, last_name)
VALUES (value1, value2, value3, ...);

/* fill team table with values */
INSERT INTO team(id, name, club_id, league_id, manager_id, game_nr)
VALUES (value1, value2, value3, ...);

/* fill team_player table with values */
INSERT INTO team_player(id, team_id, player_passnumber)
VALUES (value1, value2, value3, ...);

/* fill umpire table with values */
INSERT INTO umpire(passnumber, first_name, last_name)
VALUES (value1, value2, value3, ...);