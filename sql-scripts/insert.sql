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

INSERT INTO player(id, passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb,
                   cs, sh, sf, a, po, e, dp, ip)
VALUES (1, 000001, 'Harald', 'Schmidt', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(id, passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (2, 000002, 'James', 'Bond', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(id, passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (3, 000003, 'Harry', 'Potter', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(id, passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (4, 000004, 'Hermine', 'Granger', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(id, passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (5, 000005, 'Ron', 'Weasley', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(id, passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (6, 000006, 'Lord', 'Voldemort', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(id, passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (7, 000007, 'Fritz', 'Meinecke', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(id, passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (8, 000008, 'Knossi', 'Knossala', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(id, passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (9, 000009, 'Peter', 'Zwegat', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(id, passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (10, 000010, 'Horst', 'Schlemmer', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(id, passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (11, 000011, 'Elfriede', 'Mainz', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(id, passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (12, 000012, 'Bat', 'Man', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(id, passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (13, 000013, 'Iron', 'Man', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(id, passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (14, 000014, 'Newt', 'Scamander', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

INSERT INTO scorer(id, passnumber, first_name, last_name)
VALUES (1, 000001, 'Klaus', 'Büchner');
INSERT INTO scorer(id, passnumber, first_name, last_name)
VALUES (2, 000002, 'Thorsten', 'Legat');

INSERT INTO umpire(id, passnumber, first_name, last_name)
VALUES (1, 000001, 'Dieter', 'Bohlen');
INSERT INTO umpire(id, passnumber, first_name, last_name)
VALUES (2, 000002, 'Sebastian', 'Fitzek');

-- INSERT INTO team(id, name, club_id, league_id, manager_id)
-- VALUES (1, 'Mannheimer Maulwürfe', 1, 1, 2);
-- INSERT INTO team(id, name, club_id, league_id, manager_id)
-- VALUES (2, 'Paderborner Pinguine', 2, 1, 3);
-- INSERT INTO team(id, name, club_id, league_id, manager_id)
-- VALUES (3, 'Mainzer Mäuse', 3, 2, 4);
-- INSERT INTO team(id, name, club_id, league_id, manager_id)
-- VALUES (4, 'Berliner Bären', 4, 2, 5);
-- INSERT INTO team(id, name, club_id, league_id, manager_id)
-- VALUES (5, 'Bonner Blindschleichen', 5, 3, 6);
-- INSERT INTO team(id, name, club_id, league_id, manager_id)
-- VALUES (6, 'Solinger Schnabeltiere', 6, 3, 7);
-- INSERT INTO team(id, name, club_id, league_id, manager_id)
-- VALUES (7, 'Heidenheimer Hasen', 7, 1, 1);

INSERT INTO lineup(id, team_id)
VALUES(1,1);
INSERT INTO lineup(id, team_id)
VALUES(2,2);
INSERT INTO lineup(id, team_id)
VALUES(3,3);
INSERT INTO lineup(id, team_id)
VALUES(4,4);

INSERT INTO team_player(id, team_id, player_id)
VALUES (1, 1, 1);
INSERT INTO team_player(id, team_id, player_id)
VALUES (2, 1, 2);
INSERT INTO team_player(id, team_id, player_id)
VALUES (3, 2, 3);
INSERT INTO team_player(id, team_id, player_id)
VALUES (4, 2, 4);
INSERT INTO team_player(id, team_id, player_id)
VALUES (5, 3, 5);
INSERT INTO team_player(id, team_id, player_id)
VALUES (6, 3, 6);
INSERT INTO team_player(id, team_id,player_id)
VALUES (7, 4, 7);
INSERT INTO team_player(id, team_id, player_id)
VALUES (8, 4, 8);
INSERT INTO team_player(id, team_id, player_id)
VALUES (9, 5, 9);
INSERT INTO team_player(id, team_id, player_id)
VALUES (10, 5, 10);
INSERT INTO team_player(id, team_id, player_id)
VALUES (11, 6, 11);
INSERT INTO team_player(id, team_id, player_id)
VALUES (12, 6, 12);
INSERT INTO team_player(id, team_id, player_id)
VALUES (13, 7, 13);
INSERT INTO team_player(id, team_id, player_id)
VALUES (14, 7, 14);

INSERT INTO lineup_team_player(id, team_player_id,position,jersey_nr, lineup_id)
VALUES(1,1,5,75393,1);

INSERT INTO game(id, date, end_time, start_time, time_of_game, location, scorer_id, innings,
                 attendance, association_id, guest_lineup_id, host_lineup_id)
VALUES (1, '2023-08-18', '10:00:00', '12:00:00', 2.0, 'Bremen', 1, 9, 500, 1, 1, 2);

INSERT INTO game_umpire(id, game_id, umpire_id)
VALUES (1, 1, 1);

