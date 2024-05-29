INSERT INTO association(name)
VALUES ('DBV');

INSERT INTO position(id, description)
VALUES (1, 'Pitcher');
INSERT INTO position(id, description)
VALUES (2, 'Catcher');
INSERT INTO position(id, description)
VALUES (3, 'First Base');
INSERT INTO position(id, description)
VALUES (4, 'Second Base');
INSERT INTO position(id, description)
VALUES (5, 'Third Base');
INSERT INTO position(id, description)
VALUES (6, 'Short Stop');
INSERT INTO position(id, description)
VALUES (7, 'Leftfielder');
INSERT INTO position(id, description)
VALUES (8, 'Centerfielder');
INSERT INTO position(id, description)
VALUES (9, 'Rightfielder');
INSERT INTO position(id, description)
VALUES (10, 'Designated Hitter');

INSERT INTO club(name, city, association_id)
VALUES ('Mannheim Tornados', 'Mannheim', 1);
INSERT INTO club(name, city, association_id)
VALUES ('Untouchables Paderborn', 'Paderborn', 1);
INSERT INTO club(name, city, association_id)
VALUES ('Mainz Athletics', 'Mainz', 1);
INSERT INTO club(name, city, association_id)
VALUES ('Berlin Challengers', 'Berlin', 1);
INSERT INTO club(name, city, association_id)
VALUES ('Bonn Capitals', 'Bonn', 1);
INSERT INTO club(name, city, association_id)
VALUES ('Solingen Alligators', 'Solingen', 1);
INSERT INTO club(name, city, association_id)
VALUES ('Heidenheim Heideköpfe', 'Heidenheim', 1);

INSERT INTO league (name, association_id)
VALUES ('1. Bundesliga', 1);
INSERT INTO league (name, association_id)
VALUES ('2. Bundesliga', 1);
INSERT INTO league (name, association_id)
VALUES ('Verbandsliga', 1);
INSERT INTO league (name, association_id)
VALUES ('1. Landesliga', 1);
INSERT INTO league (name, association_id)
VALUES ('1. Bezirksliga', 1);

INSERT INTO manager(first_name, last_name, email)
VALUES ('Klaus', 'Eckle', 'klaus.eckle@managermail.com');
INSERT INTO manager(first_name, last_name, email)
VALUES ('Peter', 'Meyer', 'peter.meyer@managermail.com');
INSERT INTO manager(first_name, last_name, email)
VALUES ('Franz', 'Müller', 'franz.mueller@managermail.com');
INSERT INTO manager(first_name, last_name, email)
VALUES ('Herbert', 'Zacharias', 'herbert.zacharias@managermail.com');
INSERT INTO manager(first_name, last_name, email)
VALUES ('Susanne', 'Eberts', 'susanne.eberts@managermail.com');
INSERT INTO manager(first_name, last_name, email)
VALUES ('Tina', 'Turner', 'tina.turner@managermail.com');
INSERT INTO manager(first_name, last_name, email)
VALUES ('Erika', 'Schuft', 'erika.schuft@managermail.com');
INSERT INTO manager(first_name, last_name, email)
VALUES ('John', 'Schuft', 'j.schuft@managermail.com');
INSERT INTO manager(first_name, last_name, email)
VALUES ('Boba', 'Schuft', 'boba.schuft@managermail.com');
INSERT INTO manager(first_name, last_name, email)
VALUES ('Biba', 'Schuft', 'biba.schuft@managermail.com');

INSERT INTO player(passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb,
                   cs, sh, sf, a, po, e, dp, ip)
VALUES (000001, 'Harald', 'Schmidt', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (000002, 'James', 'Bond', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (000003, 'Harry', 'Potter', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (000004, 'Hermine', 'Granger', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (000005, 'Ron', 'Weasley', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (000006, 'Lord', 'Voldemort', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (000007, 'Fritz', 'Meinecke', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (000008, 'Knossi', 'Knossala', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (000009, 'Peter', 'Zwegat', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (000010, 'Horst', 'Schlemmer', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (000011, 'Elfriede', 'Mainz', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (000012, 'Bat', 'Man', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (000013, 'Iron', 'Man', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO player(passnumber, first_name, last_name, pa, ab, r, rbi, h, twob, threeb, hr, k, bb, hp, sb, cs,
                   sh, sf, a, po, e, dp, ip)
VALUES (000014, 'Newt', 'Scamander', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

INSERT INTO scorer(passnumber, first_name, last_name)
VALUES (000001, 'Klaus', 'Büchner');
INSERT INTO scorer(passnumber, first_name, last_name)
VALUES (000002, 'Thorsten', 'Legat');

INSERT INTO umpire(passnumber, first_name, last_name)
VALUES (000001, 'Dieter', 'Bohlen');
INSERT INTO umpire(passnumber, first_name, last_name)
VALUES (000002, 'Sebastian', 'Fitzek');

INSERT INTO team(name, club_id, league_id, manager_id)
VALUES ('Mannheimer Maulwürfe', 1, 1, 2);
INSERT INTO team(name, club_id, league_id, manager_id)
VALUES ('Paderborner Pinguine', 2, 1, 3);
INSERT INTO team(name, club_id, league_id, manager_id)
VALUES ('Mainzer Mäuse', 3, 2, 4);
INSERT INTO team(name, club_id, league_id, manager_id)
VALUES ('Berliner Bären', 4, 2, 5);
INSERT INTO team(name, club_id, league_id, manager_id)
VALUES ('Bonner Blindschleichen', 5, 3, 6);
INSERT INTO team(name, club_id, league_id, manager_id)
VALUES ('Solinger Schnabeltiere', 6, 3, 7);
INSERT INTO team(name, club_id, league_id, manager_id)
VALUES ('Heidenheimer Hasen', 7, 1, 1);

INSERT INTO team_player(team_id, player_id)
VALUES (1, 1);
INSERT INTO team_player(team_id, player_id)
VALUES (1, 2);
INSERT INTO team_player(team_id, player_id)
VALUES (2, 3);
INSERT INTO team_player(team_id, player_id)
VALUES (2, 4);
INSERT INTO team_player(team_id, player_id)
VALUES (3, 5);
INSERT INTO team_player(team_id, player_id)
VALUES (3, 6);
INSERT INTO team_player(team_id, player_id)
VALUES (4, 7);
INSERT INTO team_player(team_id, player_id)
VALUES (4, 8);
INSERT INTO team_player(team_id, player_id)
VALUES (5, 9);
INSERT INTO team_player(team_id, player_id)
VALUES (5, 10);
INSERT INTO team_player(team_id, player_id)
VALUES (6, 11);
INSERT INTO team_player(team_id, player_id)
VALUES (6, 12);
INSERT INTO team_player(team_id, player_id)
VALUES (7, 13);
INSERT INTO team_player(team_id, player_id)
VALUES (7, 14);

INSERT INTO game(game_nr, innings, association_id, league_id)
VALUES (1, 9, 1, 1);

INSERT INTO inning(game_id, inning, outs, batting_team)
VALUES (1, 1, 0, 'AWAY');

INSERT INTO turn(balls, base, strikes, inning_id, player_id, current_status)
VALUES (0, 0, 0, 1, 1, 'AT_BAT');

/*
INSERT INTO lineup(team_id)
VALUES (1);
INSERT INTO lineup(team_id)
VALUES (2);

INSERT INTO lineup_team_player(lineup_id, team_player_id, jersey_nr, position_id)
VALUES (1, 1, 1, 1);
INSERT INTO lineup_team_player(lineup_id, team_player_id, jersey_nr, position_id)
VALUES (1, 2, 2, 2);
INSERT INTO lineup_team_player(lineup_id, team_player_id, jersey_nr, position_id)
VALUES (2, 3, 3, 3);
INSERT INTO lineup_team_player(lineup_id, team_player_id, jersey_nr, position_id)
VALUES (2, 4, 4, 4)
*/