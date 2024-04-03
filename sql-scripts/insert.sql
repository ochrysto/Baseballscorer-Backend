INSERT INTO association
VALUES (1, 'DBV');

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

INSERT INTO manager(id, first_name, last_name, email)
VALUES (1, 'Klaus', 'Eckle', 'klaus.eckle@managermail.com');
INSERT INTO manager(id, first_name, last_name, email)
VALUES (2, 'Peter', 'Meyer', 'peter.meyer@managermail.com');
INSERT INTO manager(id, first_name, last_name, email)
VALUES (3, 'Franz', 'Müller', 'franz.mueller@managermail.com');
INSERT INTO manager(id, first_name, last_name, email)
VALUES (4, 'Herbert', 'Zacharias', 'herbert.zacharias@managermail.com');
INSERT INTO manager(id, first_name, last_name, email)
VALUES (5, 'Susanne', 'Eberts', 'susanne.eberts@managermail.com');
INSERT INTO manager(id, first_name, last_name, email)
VALUES (6, 'Tina', 'Turner', 'tina.turner@managermail.com');
INSERT INTO manager(id, first_name, last_name, email)
VALUES (7, 'Erika', 'Schuft', 'erika.schuft@managermail.com');

INSERT INTO player(passnumber, first_name, last_name, jersey_nr, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (1, 'Harald', 'Schmidt', 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, jersey_nr, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (2, 'James', 'Bond', 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, jersey_nr, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (3, 'Harry', 'Potter', 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, jersey_nr, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (4, 'Hermine', 'Granger', 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, jersey_nr, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (5, 'Ron', 'Weasley', 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, jersey_nr, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (6, 'Lord', 'Voldemort', 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, jersey_nr, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (7, 'Fritz', 'Meinecke', 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, jersey_nr, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (8, 'Knossi', 'Knossala', 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, jersey_nr, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (9, 'Peter', 'Zwegat', 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, jersey_nr, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (10, 'Horst', 'Schlemmer', 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, jersey_nr, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (11, 'Elfriede', 'Mainz', 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, jersey_nr, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (12, 'Bat', 'Man', 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, jersey_nr, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (13, 'Iron', 'Man', 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, jersey_nr, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (14, 'Newt', 'Scamander', 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

INSERT INTO scorer(passnumber, first_name, last_name)
VALUES (1, 'Klaus', 'Büchner');
INSERT INTO scorer(passnumber, first_name, last_name)
VALUES (2, 'Thorsten', 'Legat');

INSERT INTO umpire(passnumber, first_name, last_name)
VALUES (1, 'Dieter', 'Bohlen');
INSERT INTO umpire(passnumber, first_name, last_name)
VALUES (2, 'Sebastian', 'Fitzek');

INSERT INTO game(game_nr, date, end_time, start_time, time_of_game, location, scorer_passnumber, innings, attendance,
                 association_id)
VALUES (1, '2023-08-18', '10:00:00', '12:00:00', 2.0, 'Bremen', 1, 9, 500, 1);

INSERT INTO game_umpire(id, game_nr, umpire_passnumber)
VALUES (1, 1, 2);

INSERT INTO team(id, name, club_id, league_id, manager_id)
VALUES (1, 'Mannheimer Maulwürfe', 1, 1, 2);
INSERT INTO team(id, name, club_id, league_id, manager_id)
VALUES (2, 'Paderborner Pinguine', 2, 1, 3);
INSERT INTO team(id, name, club_id, league_id, manager_id)
VALUES (3, 'Mainzer Mäuse', 3, 2, 4);
INSERT INTO team(id, name, club_id, league_id, manager_id)
VALUES (4, 'Berliner Bären', 4, 2, 5);
INSERT INTO team(id, name, club_id, league_id, manager_id)
VALUES (5, 'Bonner Blindschleichen', 5, 3, 6);
INSERT INTO team(id, name, club_id, league_id, manager_id)
VALUES (6, 'Solinger Schnabeltiere', 6, 3, 7);
INSERT INTO team(id, name, club_id, league_id, manager_id)
VALUES (7, 'Heidenheimer Hasen', 7, 1, 1);

INSERT INTO team_player(id, team_id, player_passnumber)
VALUES (1, 1, 1);
INSERT INTO team_player(id, team_id, player_passnumber)
VALUES (2, 1, 2);
INSERT INTO team_player(id, team_id, player_passnumber)
VALUES (3, 2, 3);
INSERT INTO team_player(id, team_id, player_passnumber)
VALUES (4, 2, 4);
INSERT INTO team_player(id, team_id, player_passnumber)
VALUES (5, 3, 5);
INSERT INTO team_player(id, team_id, player_passnumber)
VALUES (6, 3, 6);
INSERT INTO team_player(id, team_id, player_passnumber)
VALUES (7, 4, 7);
INSERT INTO team_player(id, team_id, player_passnumber)
VALUES (8, 4, 8);
INSERT INTO team_player(id, team_id, player_passnumber)
VALUES (9, 5, 9);
INSERT INTO team_player(id, team_id, player_passnumber)
VALUES (10, 5, 10);
INSERT INTO team_player(id, team_id, player_passnumber)
VALUES (11, 6, 11);
INSERT INTO team_player(id, team_id, player_passnumber)
VALUES (12, 6, 12);
INSERT INTO team_player(id, team_id, player_passnumber)
VALUES (13, 7, 13);
INSERT INTO team_player(id, team_id, player_passnumber)
VALUES (14, 7, 14);