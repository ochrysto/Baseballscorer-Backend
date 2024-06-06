package com.example.baseballscoresheet.mapping;

import com.example.baseballscoresheet.model.dtos.association.AssociationGetDto;
import com.example.baseballscoresheet.model.dtos.club.ClubGetDto;
import com.example.baseballscoresheet.model.dtos.game.GameAddDto;
import com.example.baseballscoresheet.model.dtos.game.GameFinishedGetDto;
import com.example.baseballscoresheet.model.dtos.game.GameGetDto;
import com.example.baseballscoresheet.model.dtos.game.GamePutDto;
import com.example.baseballscoresheet.model.dtos.league.LeagueGetDto;
import com.example.baseballscoresheet.model.dtos.player.LineupPlayerGetDto;
import com.example.baseballscoresheet.model.dtos.player.PlayerAddDto;
import com.example.baseballscoresheet.model.dtos.player.PlayerForLineupDto;
import com.example.baseballscoresheet.model.dtos.manager.ManagerGetDto;
import com.example.baseballscoresheet.model.dtos.player.PlayerGetDto;
import com.example.baseballscoresheet.model.dtos.position.PositionGetDto;
import com.example.baseballscoresheet.model.dtos.scorer.ScorerGetDto;
import com.example.baseballscoresheet.model.dtos.team.TeamGetDto;
import com.example.baseballscoresheet.model.dtos.team.TeamWithPlayersGetDto;
import com.example.baseballscoresheet.model.dtos.team.TeamAddDto;
import com.example.baseballscoresheet.model.dtos.umpire.UmpireGetDto;
import com.example.baseballscoresheet.model.entities.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service
public class MappingService {

    // AddTeamInfoDto + ManagerEntity + ClubEntity + LeagueEntity -> TeamEntity
    public TeamEntity mapToTeamEntity(TeamAddDto teamAddDto,
                                      ManagerEntity managerEntity, ClubEntity clubEntity,
                                      LeagueEntity leagueEntity) {
        var teamEntity = new TeamEntity();
        teamEntity.setName(teamAddDto.getName());
        teamEntity.setTeamLogo(teamAddDto.getTeamLogo());
        teamEntity.setClub(clubEntity);
        teamEntity.setLeague(leagueEntity);
        teamEntity.setManager(managerEntity);
        return teamEntity;
    }

    // TeamEntity -> TeamGetDto
    public TeamGetDto mapTeamToGetTeamWithoutPlayerListDto(TeamEntity teamEntity) {
        TeamGetDto teamInfoDto = new TeamGetDto();
        teamInfoDto.setTeamId(teamEntity.getId());
        teamInfoDto.setName(teamEntity.getName());
        teamInfoDto.setTeamLogo(teamEntity.getTeamLogo());
        teamInfoDto.setManager(mapManagerEntityToGetManagerDto(teamEntity.getManager()));
        teamInfoDto.setLeague(mapLeagueEntityToGetLeagueDto(teamEntity.getLeague()));
        teamInfoDto.setClub(mapClubEntityToGetClubDto(teamEntity.getClub()));
        return teamInfoDto;
    }

    // ClubEntity -> GetClubDto
    public ClubGetDto mapClubEntityToGetClubDto(ClubEntity clubEntity) {
        ClubGetDto clubGetDto = new ClubGetDto();
        clubGetDto.setId(clubEntity.getId());
        clubGetDto.setName(clubEntity.getName());
        clubGetDto.setCity(clubEntity.getCity());
        clubGetDto.setLogo(clubEntity.getClubLogo());
        clubGetDto.setEmail(clubEntity.getEmail());
        clubGetDto.setAssociation(mapAssociationEntityToGetAssociationDto(clubEntity.getAssociation()));
        return clubGetDto;
    }

    // ManagerEntity -> ManagerGetDto
    public ManagerGetDto mapManagerEntityToGetManagerDto(ManagerEntity managerEntity) {
        ManagerGetDto managerDto = new ManagerGetDto();
        managerDto.setId(managerEntity.getId());
        managerDto.setFirstName(managerEntity.getFirstName());
        managerDto.setLastName(managerEntity.getLastName());
        managerDto.setEmail(managerEntity.getEmail());
        return managerDto;
    }

    // LeagueEntity -> LeagueGetDto
    public LeagueGetDto mapLeagueEntityToGetLeagueDto(LeagueEntity leagueEntity) {
        LeagueGetDto leagueGetDto = new LeagueGetDto();
        leagueGetDto.setId(leagueEntity.getId());
        leagueGetDto.setName(leagueEntity.getName());
        leagueGetDto.setAssociation(mapAssociationEntityToGetAssociationDto(leagueEntity.getAssociation()));
        return leagueGetDto;
    }

    // AssociationEntity -> AssociationGetDto
    public AssociationGetDto mapAssociationEntityToGetAssociationDto(AssociationEntity associationEntity) {
        AssociationGetDto associationGetDto = new AssociationGetDto();
        associationGetDto.setId(associationEntity.getId());
        associationGetDto.setName(associationEntity.getName());
        return associationGetDto;
    }

    // PlayerAddDto -> PlayerEntity
    public PlayerEntity mapAddPlayerDtoToPlayerEntity(PlayerAddDto newPlayer) {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setFirstName(newPlayer.getFirstName());
        playerEntity.setLastName(newPlayer.getLastName());
        playerEntity.setPassnumber(newPlayer.getPassnumber());
        return playerEntity;
    }

    // PlayerEntity -> GetPlayerInfoDto
    public PlayerGetDto mapPlayerEntityToGetPlayerDto(PlayerEntity playerEntity) {
        PlayerGetDto playerGetDto = new PlayerGetDto();
        playerGetDto.setId(playerEntity.getId());
        playerGetDto.setFirstName(playerEntity.getFirstName());
        playerGetDto.setLastName(playerEntity.getLastName());
        playerGetDto.setPassnumber(playerEntity.getPassnumber());
        return playerGetDto;
    }

    // TeamPlayerEntity -> PlayerGetDto
    public PlayerGetDto mapTeamPlayerEntityToGetPlayerDto(TeamPlayerEntity teamPlayerEntity) {
        PlayerGetDto playerInfoDto = new PlayerGetDto();
        playerInfoDto.setId(teamPlayerEntity.getPlayer().getId());
        playerInfoDto.setFirstName(teamPlayerEntity.getPlayer().getFirstName());
        playerInfoDto.setLastName(teamPlayerEntity.getPlayer().getLastName());
        playerInfoDto.setPassnumber(teamPlayerEntity.getPlayer().getPassnumber());
        return playerInfoDto;
    }

    // PositionEntity -> PositionGetDto
    public PositionGetDto mapPositionEntityToGetPositionDto(PositionEntity positionEntity) {
        PositionGetDto positionGetDto = new PositionGetDto();
        positionGetDto.setId(positionEntity.getId());
        positionGetDto.setPosition(positionEntity.getPosition());
        positionGetDto.setDescription(positionEntity.getDescription());
        return positionGetDto;
    }


    // TeamEntity -> LineupEntity
    public LineupEntity mapTeamEntityToLineupEntity(TeamEntity teamEntity, GameEntity gameEntity) {
        LineupEntity lineupEntity = new LineupEntity();
        lineupEntity.setTeam(teamEntity);
        lineupEntity.setGame(gameEntity);
        return lineupEntity;
    }

    // PlayerForLineupDto + LineupPlayerDto + TeamPlayerEntity + PositionEntity -> LineupTeamPlayerEntity
    public LineupTeamPlayerEntity mapToLineupTeamPlayerEntity(PlayerForLineupDto playerForLineupDto,
                                                              LineupEntity lineupEntity, TeamPlayerEntity teamPlayer,
                                                              PositionEntity position) {
        LineupTeamPlayerEntity lineupTeamPlayerEntity = new LineupTeamPlayerEntity();
        lineupTeamPlayerEntity.setLineup(lineupEntity);
        lineupTeamPlayerEntity.setTeamPlayer(teamPlayer);
        lineupTeamPlayerEntity.setJerseyNr(playerForLineupDto.getJerseyNr());
        lineupTeamPlayerEntity.setPosition(position);
        return lineupTeamPlayerEntity;
    }

    // LineupTeamPlayerEntity -> LineupPlayerGetDto
    public LineupPlayerGetDto mapLineupTeamPlayerEntityToGetPlayerFromLineUpDto(LineupTeamPlayerEntity lineupTeamPlayer) {
        LineupPlayerGetDto lineupPlayerGetDto = new LineupPlayerGetDto();
        lineupPlayerGetDto.setId(lineupTeamPlayer.getId());
        lineupPlayerGetDto.setFirstName(lineupTeamPlayer.getTeamPlayer().getPlayer().getFirstName());
        lineupPlayerGetDto.setLastName(lineupTeamPlayer.getTeamPlayer().getPlayer().getLastName());
        lineupPlayerGetDto.setPassnumber(lineupTeamPlayer.getTeamPlayer().getPlayer().getPassnumber());
        lineupPlayerGetDto.setJerseyNr(lineupTeamPlayer.getJerseyNr());
        lineupPlayerGetDto.setPosition(lineupTeamPlayer.getPosition().getPosition());
        return lineupPlayerGetDto;
    }

    // UmpireEntity -> UmpireGetDto
    public UmpireGetDto mapUmpireEntityToGetUmpireDto(UmpireEntity umpireEntity) {
        UmpireGetDto umpireGetDto = new UmpireGetDto();
        umpireGetDto.setUmpireId(umpireEntity.getId());
        umpireGetDto.setPassnumber(umpireEntity.getPassnumber());
        umpireGetDto.setName(umpireEntity.getFirstName(), umpireEntity.getLastName());
        return umpireGetDto;
    }

    // ScorerEntity -> ScorerGetDto
    public ScorerGetDto mapScorerEntityToGetScorerDto(ScorerEntity scorerEntity) {
        ScorerGetDto scorerGetDto = new ScorerGetDto();
        scorerGetDto.setScorerId(scorerEntity.getId());
        scorerGetDto.setPassnumber(scorerEntity.getPassnumber());
        scorerGetDto.setName(scorerEntity.getFirstName(), scorerEntity.getLastName());
        return scorerGetDto;
    }

    // GameAddDto + AssociationEntity + LeagueEntity + TeamEntity + TeamEntity + ScorerEntity-> GameEntity
    public GameEntity mapToGameEntity(GameAddDto gameAddDto, AssociationEntity associationEntity,
                                      LeagueEntity leagueEntity, TeamEntity hostTeam, TeamEntity guestTeam,
                                      ScorerEntity scorerEntity) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setGameUmpire(new HashSet<>());
        gameEntity.setGameNr(gameAddDto.getGameNr());
        gameEntity.setDate(gameAddDto.getDate());
        gameEntity.setLocation(gameAddDto.getLocation());
        gameEntity.setInnings(gameAddDto.getInnings());
        gameEntity.setAssociation(associationEntity);
        gameEntity.setHost(hostTeam);
        gameEntity.setGuest(guestTeam);
        gameEntity.setLeague(leagueEntity);
        gameEntity.setScorer(scorerEntity);
        return gameEntity;
    }

    // GameEntity + List<GameUmpireEntity> -> GetGameDto
    public GameGetDto mapToGetGameDto(GameEntity gameEntity, List<GameUmpireEntity> gameUmpireEntityList) {
        GameGetDto gameGetDto = new GameGetDto();
        gameGetDto.setId(gameEntity.getId());
        gameGetDto.setGameNr(gameEntity.getGameNr());
        gameGetDto.setDate(gameEntity.getDate());
        gameGetDto.setLocation(gameEntity.getLocation());
        gameGetDto.setInnings(gameEntity.getInnings());
        if (gameEntity.getAssociation() != null)
            gameGetDto.setAssociation(mapAssociationEntityToGetAssociationDto(gameEntity.getAssociation()));
        if (gameEntity.getLeague() != null)
            gameGetDto.setLeague(mapLeagueEntityToGetLeagueDto(gameEntity.getLeague()));
        if (gameEntity.getHost() != null)
            gameGetDto.setHostTeam(mapTeamEntityToGetTeamWithPlayerListDto(gameEntity.getHost()));
        if (gameEntity.getGuest() != null)
            gameGetDto.setGuestTeam(mapTeamEntityToGetTeamWithPlayerListDto(gameEntity.getGuest()));
        if (gameEntity.getScorer() != null)
            gameGetDto.setScorer(mapScorerEntityToGetScorerDto(gameEntity.getScorer()));
        gameGetDto.setUmpireList(new ArrayList<>());

        for (GameUmpireEntity gameUmpire : gameUmpireEntityList) {
            UmpireGetDto umpireGetDto = new UmpireGetDto();
            umpireGetDto.setUmpireId(gameUmpire.getUmpire().getId());
            umpireGetDto.setPassnumber(gameUmpire.getUmpire().getPassnumber());
            umpireGetDto.setName(gameUmpire.getUmpire().getFirstName(), gameUmpire.getUmpire().getLastName());
            gameGetDto.getUmpireList().add(umpireGetDto);
        }
        return gameGetDto;
    }

    // GameEntity + UmpireEntity -> GameUmpireEntity
    public GameUmpireEntity mapToGameUmpireEntity(GameEntity gameEntity, UmpireEntity umpire) {
        GameUmpireEntity gameUmpireEntity = new GameUmpireEntity();
        gameUmpireEntity.setGame(gameEntity);
        gameUmpireEntity.setUmpire(umpire);
        return gameUmpireEntity;
    }

    // GamePutDto -> GameEntity
    public GameEntity mapUpdateGameDtoToGameEntity(GamePutDto gamePutDto) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setInnings(gamePutDto.getInnings());
        gameEntity.setAttendance(gamePutDto.getAttendance());
        gameEntity.setStartTime(gamePutDto.getStartTime());
        gameEntity.setEndTime(gamePutDto.getEndTime());
        gameEntity.setDurationInMinutes(gamePutDto.getDurationInMinutes());
        return gameEntity;
    }

    public TeamGetDto mapTeamEntityToGetTeamWithoutPlayerListDto(TeamEntity teamEntity) {
        TeamGetDto teamGetDto = new TeamGetDto();
        teamGetDto.setTeamId(teamEntity.getId());
        teamGetDto.setName(teamEntity.getName());
        teamGetDto.setClub(mapClubEntityToGetClubDto(teamEntity.getClub()));
        teamGetDto.setManager(mapManagerEntityToGetManagerDto(teamEntity.getManager()));
        teamGetDto.setLeague(mapLeagueEntityToGetLeagueDto(teamEntity.getLeague()));
        teamGetDto.setTeamLogo(teamEntity.getTeamLogo());
        return teamGetDto;
    }

    // TeamEntity -> TeamWithPlayersGetDto
    public TeamWithPlayersGetDto mapTeamEntityToGetTeamWithPlayerListDto(TeamEntity teamEntity) {
        List<PlayerGetDto> players = new ArrayList<>();
        TeamWithPlayersGetDto teamWithPlayersGetDto = new TeamWithPlayersGetDto();
        teamWithPlayersGetDto.setTeamId(teamEntity.getId());
        teamWithPlayersGetDto.setName(teamEntity.getName());
        if (teamEntity.getClub() != null)
            teamWithPlayersGetDto.setClub(mapClubEntityToGetClubDto(teamEntity.getClub()));
        if (teamEntity.getManager() != null)
            teamWithPlayersGetDto.setManager(mapManagerEntityToGetManagerDto(teamEntity.getManager()));
        if (teamEntity.getLeague() != null)
            teamWithPlayersGetDto.setLeague(mapLeagueEntityToGetLeagueDto(teamEntity.getLeague()));
        teamWithPlayersGetDto.setTeamLogo(teamEntity.getTeamLogo());
        teamWithPlayersGetDto.setPlayerList(players);
        if (teamEntity.getTeamplayer() != null)
            for (TeamPlayerEntity teamPlayerEntity : teamEntity.getTeamplayer()) {
                teamWithPlayersGetDto.getPlayerList().add(mapTeamPlayerEntityToGetPlayerDto(teamPlayerEntity));
            }
        return teamWithPlayersGetDto;
    }

    public GameFinishedGetDto mapToGetFinishedGameDto(GameEntity gameEntity, List<GameUmpireEntity> gameUmpireEntities) {
        GameFinishedGetDto gameFinishedGetDto = new GameFinishedGetDto();
        gameFinishedGetDto.setGameNr(gameEntity.getGameNr());
        gameFinishedGetDto.setDate(gameEntity.getDate());
        gameFinishedGetDto.setLocation(gameEntity.getLocation());
        gameFinishedGetDto.setInnings(gameEntity.getInnings());
        gameFinishedGetDto.setAssociation(mapAssociationEntityToGetAssociationDto(gameEntity.getAssociation()));
        gameFinishedGetDto.setLeague(mapLeagueEntityToGetLeagueDto(gameEntity.getLeague()));
        gameFinishedGetDto.setScorer(mapScorerEntityToGetScorerDto(gameEntity.getScorer()));
        gameFinishedGetDto.setUmpireList(new ArrayList<>());

        for (GameUmpireEntity gameUmpire : gameUmpireEntities) {
            UmpireGetDto umpireGetDto = new UmpireGetDto();
            umpireGetDto.setUmpireId(gameUmpire.getUmpire().getId());
            umpireGetDto.setPassnumber(gameUmpire.getUmpire().getPassnumber());
            umpireGetDto.setName(gameUmpire.getUmpire().getFirstName(), gameUmpire.getUmpire().getLastName());
            gameFinishedGetDto.getUmpireList().add(umpireGetDto);
        }
        gameFinishedGetDto.setAttendance(gameEntity.getAttendance());
        gameFinishedGetDto.setStartTime(gameEntity.getStartTime());
        gameFinishedGetDto.setEndTime(gameEntity.getEndTime());
        gameFinishedGetDto.setDurationInMinutes(gameEntity.getDurationInMinutes());
        return gameFinishedGetDto;
    }
}