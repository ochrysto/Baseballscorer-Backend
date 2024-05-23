package com.example.baseballscoresheet.mapping;

import com.example.baseballscoresheet.model.dtos.association.GetAssociationDto;
import com.example.baseballscoresheet.model.dtos.club.GetClubDto;
import com.example.baseballscoresheet.model.dtos.game.AddGameDto;
import com.example.baseballscoresheet.model.dtos.game.GetGameDto;
import com.example.baseballscoresheet.model.dtos.game.UpdateGameDto;
import com.example.baseballscoresheet.model.dtos.league.GetLeagueDto;
import com.example.baseballscoresheet.model.dtos.lineup.AddLineupDto;
import com.example.baseballscoresheet.model.dtos.lineup.AddPlayerToLineupDto;
import com.example.baseballscoresheet.model.dtos.lineup.GetPlayerFromLineupDto;
import com.example.baseballscoresheet.model.dtos.manager.GetManagerDto;
import com.example.baseballscoresheet.model.dtos.player.AddPlayerInfoDto;
import com.example.baseballscoresheet.model.dtos.player.GetPlayerInfoDto;
import com.example.baseballscoresheet.model.dtos.player.GetPlayerInfoForLineUpDto;
import com.example.baseballscoresheet.model.dtos.position.GetPositionDto;
import com.example.baseballscoresheet.model.dtos.scorer.GetScorerDto;
import com.example.baseballscoresheet.model.dtos.team.GetTeamDto;
import com.example.baseballscoresheet.model.dtos.team.GetTeamInfoDto;
import com.example.baseballscoresheet.model.dtos.team.AddTeamInfoDto;
import com.example.baseballscoresheet.model.dtos.umpire.GetUmpireDto;
import com.example.baseballscoresheet.model.entities.*;
import com.example.baseballscoresheet.services.PositionService;
import com.example.baseballscoresheet.services.TeamPlayerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class MappingService {
    private final ModelMapper mapper;
    TeamPlayerService teamPlayerService;
    PositionService positionService;

    public MappingService(TeamPlayerService teamPlayerService, PositionService positionService) {
        this.mapper = new ModelMapper();
        this.teamPlayerService = teamPlayerService;
        this.positionService = positionService;
    }

    // AddTeamInfoDto + ManagerEntity + ClubEntity + LeagueEntity -> TeamEntity
    public TeamEntity mapAddTeamInfoDtoToTeamEntity(AddTeamInfoDto addTeamInfoDto, ManagerEntity managerEntity,
                                                    ClubEntity clubEntity, LeagueEntity leagueEntity) {
        var teamEntity = new TeamEntity();
        teamEntity.setName(addTeamInfoDto.getName());
        teamEntity.setTeamLogo(addTeamInfoDto.getTeamLogo());
        teamEntity.setClub(clubEntity);
        teamEntity.setLeague(leagueEntity);
        teamEntity.setManager(managerEntity);
        return teamEntity;
    }

    // TeamEntity -> GetTeamInfoDto
    public GetTeamInfoDto mapTeamToGetTeamInfoDto(TeamEntity teamEntity) {
        GetTeamInfoDto teamInfoDto = new GetTeamInfoDto();
        teamInfoDto.setTeamId(teamEntity.getId());
        teamInfoDto.setName(teamEntity.getName());
        teamInfoDto.setTeamLogo(teamEntity.getTeamLogo());
        teamInfoDto.setGetManagerDto(mapManagerEntityToGetManagerDto(teamEntity.getManager()));
        teamInfoDto.setGetLeagueDto(mapLeagueEntityToGetLeagueDto(teamEntity.getLeague()));
        teamInfoDto.setGetClubDto(mapClubEntityToGetClubDto(teamEntity.getClub()));
        return teamInfoDto;
    }

    // ClubEntity -> GetClubDto
    public GetClubDto mapClubEntityToGetClubDto(ClubEntity clubEntity) {
        GetClubDto getClubDto;
        getClubDto = this.mapper.map(clubEntity, GetClubDto.class);
        getClubDto.setAssociationDto(mapAssociationEntityToGetAssociationDto(clubEntity.getAssociation()));
        return getClubDto;
    }

    // ManagerEntity -> GetManagerDto
    public GetManagerDto mapManagerEntityToGetManagerDto(ManagerEntity managerEntity) {
        return this.mapper.map(managerEntity, GetManagerDto.class);
    }

    // ManagerEntity -> GetLeagueDto
    public GetLeagueDto mapLeagueEntityToGetLeagueDto(LeagueEntity leagueEntity) {
        GetLeagueDto getLeagueDto;
        getLeagueDto = this.mapper.map(leagueEntity, GetLeagueDto.class);
        getLeagueDto.setAssociation(mapAssociationEntityToGetAssociationDto(leagueEntity.getAssociation()));
        return getLeagueDto;
    }

    // AssociationEntity -> GetAssociationDto
    public GetAssociationDto mapAssociationEntityToGetAssociationDto(AssociationEntity associationEntity) {
        return this.mapper.map(associationEntity, GetAssociationDto.class);
    }

    // AddPlayerInfoDto -> PlayerEntity
    public PlayerEntity mapAddPlayerInfoDtoToPlayerEntity(AddPlayerInfoDto newPlayer) {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setFirstName(newPlayer.getFirstName());
        playerEntity.setLastName(newPlayer.getLastName());
        playerEntity.setPassnumber(newPlayer.getPassnumber());
        return playerEntity;
    }

    // PlayerEntity -> GetPlayerInfoDto
    public GetPlayerInfoDto mapPlayerEntityToGetPlayerInfoDto(PlayerEntity playerEntity) {
        GetPlayerInfoDto getPlayerInfoDto = new GetPlayerInfoDto();
        getPlayerInfoDto.setId(playerEntity.getId());
        getPlayerInfoDto.setFirstName(playerEntity.getFirstName());
        getPlayerInfoDto.setLastName(playerEntity.getLastName());
        getPlayerInfoDto.setPassnumber(playerEntity.getPassnumber());
        return getPlayerInfoDto;
    }

    // TeamEntity -> GetTeamDto
    public GetTeamDto mapTeamEntityToGetTeamDto(TeamEntity teamEntity) {
        Set<GetPlayerInfoDto> players = new HashSet<>();
        GetTeamDto getTeamDto = new GetTeamDto();
        getTeamDto.setName(teamEntity.getName());
        getTeamDto.setGetClubDto(mapClubEntityToGetClubDto(teamEntity.getClub()));
        getTeamDto.setGetManagerDto(mapManagerEntityToGetManagerDto(teamEntity.getManager()));
        getTeamDto.setGetLeagueDto(mapLeagueEntityToGetLeagueDto(teamEntity.getLeague()));
        getTeamDto.setTeamLogo(teamEntity.getTeamLogo());
        getTeamDto.setPlayers(players);
        for (TeamPlayerEntity teamPlayerEntity : teamEntity.getTeamplayer()) {
            getTeamDto.getPlayers().add(mapTeamPlayerEntityToGetPlayerInfoDto(teamPlayerEntity));
        }
        return getTeamDto;
    }

    // TeamPlayerEntity -> GetPlayerInfoDto
    public GetPlayerInfoDto mapTeamPlayerEntityToGetPlayerInfoDto(TeamPlayerEntity teamPlayerEntity) {
        GetPlayerInfoDto playerInfoDto = new GetPlayerInfoDto();
        playerInfoDto.setId(teamPlayerEntity.getPlayer().getId());
        playerInfoDto.setFirstName(teamPlayerEntity.getPlayer().getFirstName());
        playerInfoDto.setLastName(teamPlayerEntity.getPlayer().getLastName());
        playerInfoDto.setPassnumber(teamPlayerEntity.getPlayer().getPassnumber());
        return playerInfoDto;
    }

    // PositionEntity -> GetPositionDto
    public GetPositionDto mapPositionEntityToGetPositionDto(PositionEntity positionEntity) {
        return this.mapper.map(positionEntity, GetPositionDto.class);
    }

    // PlayerEntity -> GetPlayerInfoForLineUpDto
    public GetPlayerInfoForLineUpDto mapPlayerEntityToGetPlayerInfoForLineUpDto(PlayerEntity player) {
        GetPlayerInfoForLineUpDto playerDto = new GetPlayerInfoForLineUpDto();
        playerDto.setId(player.getId());
        playerDto.setName(player.getFirstName() + " " + player.getLastName());
        playerDto.setPassnumber(player.getPassnumber());
        return playerDto;
    }

    // AddLineupDto -> LineupEntity
    public LineupEntity mapAddLineupDtoToLineupEntity(TeamEntity teamEntity) {
        LineupEntity lineupEntity = new LineupEntity();
        lineupEntity.setTeam(teamEntity);
        return lineupEntity;
    }

    // LineupPlayerDto + AddLineupDto -> LineupTeamPlayerEntity
    public LineupTeamPlayerEntity mapToLineupTeamPlayerEntity(AddPlayerToLineupDto addPlayerToLineupDto, LineupEntity lineupEntity) {
        LineupTeamPlayerEntity lineupTeamPlayerEntity = new LineupTeamPlayerEntity();
        TeamPlayerEntity teamPlayer = this.teamPlayerService.findTeamPlayerEntityByTeamIdAndPlayerId(lineupEntity.getTeam().getId(), addPlayerToLineupDto.getPlayerId());
        lineupTeamPlayerEntity.setLineup(lineupEntity);
        lineupTeamPlayerEntity.setTeamPlayer(teamPlayer);
        lineupTeamPlayerEntity.setJerseyNr(addPlayerToLineupDto.getJerseyNr());
        lineupTeamPlayerEntity.setPosition(this.positionService.findById(addPlayerToLineupDto.getPosition()));

        return lineupTeamPlayerEntity;
    }

    // LineupTeamPlayerEntity -> GetPlayerFromLineupDto
    public GetPlayerFromLineupDto mapTeamPlayerEntityToGetPlayerFromLineUpDto(LineupTeamPlayerEntity lineupTeamPlayer) {
        GetPlayerFromLineupDto getPlayerFromLineupDto = new GetPlayerFromLineupDto();
        getPlayerFromLineupDto.setJerseyNr(lineupTeamPlayer.getJerseyNr());
        getPlayerFromLineupDto.setPosition(lineupTeamPlayer.getPosition().getDescription());
        getPlayerFromLineupDto.setPlayerName(lineupTeamPlayer.getTeamPlayer().getPlayer().getFirstName(),
                lineupTeamPlayer.getTeamPlayer().getPlayer().getLastName());
        return getPlayerFromLineupDto;
    }

    // UmpireEntity -> GetUmpireDto
    public GetUmpireDto mapUmpireEntityToGetUmpireDto(UmpireEntity umpireEntity) {
        GetUmpireDto getUmpireDto = new GetUmpireDto();
        getUmpireDto.setUmpireId(umpireEntity.getId());
        getUmpireDto.setPassnumber(umpireEntity.getPassnumber());
        getUmpireDto.setName(umpireEntity.getFirstName(), umpireEntity.getLastName());
        return getUmpireDto;
    }

    // ScorerEntity -> GetScorerDto
    public GetScorerDto mapScorerEntityToGetScorerDto(ScorerEntity scorerEntity) {
        GetScorerDto getScorerDto = new GetScorerDto();
        getScorerDto.setScorerId(scorerEntity.getId());
        getScorerDto.setPassnumber(scorerEntity.getPassnumber());
        getScorerDto.setName(scorerEntity.getFirstName(), scorerEntity.getLastName());
        return getScorerDto;
    }

    // AddGameDto -> GameEntity
    public GameEntity mapInformationToGameEntity(AddGameDto addGameDto, AssociationEntity associationEntity,
                                                 LeagueEntity leagueEntity, TeamEntity hostTeam, TeamEntity guestTeam,
                                                 ScorerEntity scorerEntity) {
        GameEntity gameEntity = new GameEntity();

        gameEntity.setGameUmpire(new HashSet<>());
        gameEntity.setGameNr(addGameDto.getGameNr());
        gameEntity.setDate(addGameDto.getDate());
        gameEntity.setLocation(addGameDto.getLocation());
        gameEntity.setInnings(addGameDto.getInnings());
        gameEntity.setAssociation(associationEntity);
        gameEntity.setLeague(leagueEntity);
        gameEntity.setHost(hostTeam);
        gameEntity.setGuest(guestTeam);
        gameEntity.setScorer(scorerEntity);
        return gameEntity;
    }

    // GameEntity -> GetGameDto
    public GetGameDto mapToGetGameDto(GameEntity gameEntity, List<GameUmpireEntity> gameUmpireEntityList) {
        GetGameDto getGameDto = new GetGameDto();
        getGameDto.setGameNr(gameEntity.getGameNr());
        getGameDto.setDate(gameEntity.getDate());
        getGameDto.setLocation(gameEntity.getLocation());
        getGameDto.setInnings(gameEntity.getInnings());
        getGameDto.setAssociation(mapAssociationEntityToGetAssociationDto(gameEntity.getAssociation()));
        getGameDto.setLeague(mapLeagueEntityToGetLeagueDto(gameEntity.getLeague()));
        getGameDto.setHostTeam(mapTeamEntityToGetTeamDto(gameEntity.getHost()));
        getGameDto.setGuestTeam(mapTeamEntityToGetTeamDto(gameEntity.getGuest()));
        getGameDto.setScorer(mapScorerEntityToGetScorerDto(gameEntity.getScorer()));
        getGameDto.setUmpireList(new ArrayList<>());

        for (GameUmpireEntity gameUmpire : gameUmpireEntityList) {
            GetUmpireDto getUmpireDto = new GetUmpireDto();
            getUmpireDto.setUmpireId(gameUmpire.getUmpire().getId());
            getUmpireDto.setPassnumber(gameUmpire.getUmpire().getPassnumber());
            getUmpireDto.setName(gameUmpire.getUmpire().getFirstName(), gameUmpire.getUmpire().getLastName());
            getGameDto.getUmpireList().add(getUmpireDto);
        }
        return getGameDto;
    }

    // GameEntity + UmpireEntity -> GameUmpireEntity
    public GameUmpireEntity mapUmpireEntityToGameUmpireEntity(GameEntity gameEntity, UmpireEntity umpire) {
        GameUmpireEntity gameUmpireEntity = new GameUmpireEntity();
        gameUmpireEntity.setGame(gameEntity);
        gameUmpireEntity.setUmpire(umpire);
        return gameUmpireEntity;
    }

    // UpdateGameDto -> GameEntity
    public GameEntity mapUpdateGameDtoToGameEntity(UpdateGameDto updateGameDto) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setInnings(updateGameDto.getInnings());
        gameEntity.setAttendance(updateGameDto.getAttendance());
        gameEntity.setStartTime(updateGameDto.getStartTime());
        gameEntity.setEndTime(updateGameDto.getEndTime());
        gameEntity.setDurationInMinutes(updateGameDto.getDurationInMinutes());
        return gameEntity;
    }
}