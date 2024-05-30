package com.example.baseballscoresheet.mapping;

import com.example.baseballscoresheet.model.dtos.association.GetAssociationDto;
import com.example.baseballscoresheet.model.dtos.club.GetClubDto;
import com.example.baseballscoresheet.model.dtos.game.*;
import com.example.baseballscoresheet.model.dtos.league.GetLeagueDto;
import com.example.baseballscoresheet.model.dtos.player.PlayerForLineupDto;
import com.example.baseballscoresheet.model.dtos.player.GetPlayerFromLineupDto;
import com.example.baseballscoresheet.model.dtos.manager.GetManagerDto;
import com.example.baseballscoresheet.model.dtos.player.AddPlayerDto;
import com.example.baseballscoresheet.model.dtos.player.GetPlayerDto;
import com.example.baseballscoresheet.model.dtos.position.GetPositionDto;
import com.example.baseballscoresheet.model.dtos.scorer.GetScorerDto;
import com.example.baseballscoresheet.model.dtos.team.GetTeamWithPlayerListDto;
import com.example.baseballscoresheet.model.dtos.team.GetTeamWithoutPlayerListDto;
import com.example.baseballscoresheet.model.dtos.team.AddTeamWithoutPlayerListDto;
import com.example.baseballscoresheet.model.dtos.umpire.GetUmpireDto;
import com.example.baseballscoresheet.model.entities.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service
public class MappingService {

    public MappingService() {
    }

    // AddTeamInfoDto + ManagerEntity + ClubEntity + LeagueEntity -> TeamEntity
    public TeamEntity mapToTeamEntity(AddTeamWithoutPlayerListDto addTeamWithoutPlayerListDto, ManagerEntity managerEntity, ClubEntity clubEntity, LeagueEntity leagueEntity) {
        var teamEntity = new TeamEntity();
        teamEntity.setName(addTeamWithoutPlayerListDto.getName());
        teamEntity.setTeamLogo(addTeamWithoutPlayerListDto.getTeamLogo());
        teamEntity.setClub(clubEntity);
        teamEntity.setLeague(leagueEntity);
        teamEntity.setManager(managerEntity);
        return teamEntity;
    }

    // TeamEntity -> GetTeamWithoutPlayerListDto
    public GetTeamWithoutPlayerListDto mapTeamToGetTeamWithoutPlayerListDto(TeamEntity teamEntity) {
        GetTeamWithoutPlayerListDto teamInfoDto = new GetTeamWithoutPlayerListDto();
        teamInfoDto.setTeamId(teamEntity.getId());
        teamInfoDto.setName(teamEntity.getName());
        teamInfoDto.setTeamLogo(teamEntity.getTeamLogo());
        if (teamEntity.getManager() != null)
            teamInfoDto.setManager(mapManagerEntityToGetManagerDto(teamEntity.getManager()));
        teamInfoDto.setLeague(mapLeagueEntityToGetLeagueDto(teamEntity.getLeague()));
        teamInfoDto.setClub(mapClubEntityToGetClubDto(teamEntity.getClub()));
        return teamInfoDto;
    }

    // ClubEntity -> GetClubDto
    public GetClubDto mapClubEntityToGetClubDto(ClubEntity clubEntity) {
        GetClubDto getClubDto = new GetClubDto();
        getClubDto.setName(clubEntity.getName());
        getClubDto.setCity(clubEntity.getCity());
        getClubDto.setLogo(clubEntity.getClubLogo());
        getClubDto.setEmail(clubEntity.getEmail());
        getClubDto.setAssociation(mapAssociationEntityToGetAssociationDto(clubEntity.getAssociation()));
        return getClubDto;
    }

    // ManagerEntity -> GetManagerDto
    public GetManagerDto mapManagerEntityToGetManagerDto(ManagerEntity managerEntity) {
        GetManagerDto managerDto = new GetManagerDto();
        managerDto.setId(managerEntity.getId());
        managerDto.setFirstName(managerEntity.getFirstName());
        managerDto.setLastName(managerEntity.getLastName());
        managerDto.setEmail(managerEntity.getEmail());
        return managerDto;
    }

    // LeagueEntity -> GetLeagueDto
    public GetLeagueDto mapLeagueEntityToGetLeagueDto(LeagueEntity leagueEntity) {
        GetLeagueDto getLeagueDto = new GetLeagueDto();
        getLeagueDto.setId(leagueEntity.getId());
        getLeagueDto.setName(leagueEntity.getName());
        getLeagueDto.setAssociation(mapAssociationEntityToGetAssociationDto(leagueEntity.getAssociation()));
        return getLeagueDto;
    }

    // AssociationEntity -> GetAssociationDto
    public GetAssociationDto mapAssociationEntityToGetAssociationDto(AssociationEntity associationEntity) {
        GetAssociationDto getAssociationDto = new GetAssociationDto();
        getAssociationDto.setId(associationEntity.getId());
        getAssociationDto.setName(associationEntity.getName());
        return getAssociationDto;
    }

    // AddPlayerDto -> PlayerEntity
    public PlayerEntity mapAddPlayerDtoToPlayerEntity(AddPlayerDto newPlayer) {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setFirstName(newPlayer.getFirstName());
        playerEntity.setLastName(newPlayer.getLastName());
        playerEntity.setPassnumber(newPlayer.getPassnumber());
        return playerEntity;
    }

    // PlayerEntity -> GetPlayerInfoDto
    public GetPlayerDto mapPlayerEntityToGetPlayerDto(PlayerEntity playerEntity) {
        GetPlayerDto getPlayerDto = new GetPlayerDto();
        getPlayerDto.setId(playerEntity.getId());
        getPlayerDto.setFirstName(playerEntity.getFirstName());
        getPlayerDto.setLastName(playerEntity.getLastName());
        getPlayerDto.setPassnumber(playerEntity.getPassnumber());
        return getPlayerDto;
    }

    // TeamPlayerEntity -> GetPlayerDto
    public GetPlayerDto mapTeamPlayerEntityToGetPlayerDto(TeamPlayerEntity teamPlayerEntity) {
        GetPlayerDto playerInfoDto = new GetPlayerDto();
        playerInfoDto.setId(teamPlayerEntity.getPlayer().getId());
        playerInfoDto.setFirstName(teamPlayerEntity.getPlayer().getFirstName());
        playerInfoDto.setLastName(teamPlayerEntity.getPlayer().getLastName());
        playerInfoDto.setPassnumber(teamPlayerEntity.getPlayer().getPassnumber());
        return playerInfoDto;
    }

    // PositionEntity -> GetPositionDto
    public GetPositionDto mapPositionEntityToGetPositionDto(PositionEntity positionEntity) {
        GetPositionDto getPositionDto = new GetPositionDto();
        getPositionDto.setId(positionEntity.getId());
        getPositionDto.setDescription(positionEntity.getDescription());
        return getPositionDto;
    }


    // TeamEntity -> LineupEntity
    public LineupEntity mapTeamEntityToLineupEntity(TeamEntity teamEntity) {
        LineupEntity lineupEntity = new LineupEntity();
        lineupEntity.setTeam(teamEntity);
        return lineupEntity;
    }

    // PlayerForLineupDto + LineupPlayerDto + TeamPlayerEntity + PositionEntity -> LineupTeamPlayerEntity
    public LineupTeamPlayerEntity mapToLineupTeamPlayerEntity(PlayerForLineupDto playerForLineupDto, LineupEntity lineupEntity, TeamPlayerEntity teamPlayer, PositionEntity position) {
        LineupTeamPlayerEntity lineupTeamPlayerEntity = new LineupTeamPlayerEntity();
        lineupTeamPlayerEntity.setLineup(lineupEntity);
        lineupTeamPlayerEntity.setTeamPlayer(teamPlayer);
        lineupTeamPlayerEntity.setJerseyNr(playerForLineupDto.getJerseyNr());
        lineupTeamPlayerEntity.setPosition(position);
        return lineupTeamPlayerEntity;
    }

    // LineupTeamPlayerEntity -> GetPlayerFromLineupDto
    public GetPlayerFromLineupDto mapLineupTeamPlayerEntityToGetPlayerFromLineUpDto(LineupTeamPlayerEntity lineupTeamPlayer) {
        GetPlayerFromLineupDto getPlayerFromLineupDto = new GetPlayerFromLineupDto();
        getPlayerFromLineupDto.setJerseyNr(lineupTeamPlayer.getJerseyNr());
        getPlayerFromLineupDto.setPosition(lineupTeamPlayer.getPosition().getDescription());
        getPlayerFromLineupDto.setPlayerName(lineupTeamPlayer.getTeamPlayer().getPlayer().getFirstName(), lineupTeamPlayer.getTeamPlayer().getPlayer().getLastName());
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

    // AddGameDto + AssociationEntity + LeagueEntity + LineupEntity + LineupEntity + ScorerEntity-> GameEntity
    public GameEntity mapToGameEntity(AddGameDto addGameDto, AssociationEntity associationEntity, LeagueEntity leagueEntity, ScorerEntity scorerEntity) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setGameUmpire(new HashSet<>());
        gameEntity.setGameNr(addGameDto.getGameNr());
        gameEntity.setDate(addGameDto.getDate());
        gameEntity.setLocation(addGameDto.getLocation());
        gameEntity.setInnings(addGameDto.getInnings());
        gameEntity.setAssociation(associationEntity);
        gameEntity.setLeague(leagueEntity);
        gameEntity.setScorer(scorerEntity);
        return gameEntity;
    }

    // GameEntity + List<GameUmpireEntity> -> GetGameDto
    public GetGameWithoutLineupsDto mapToGetGameDto(GameEntity gameEntity, List<GameUmpireEntity> gameUmpireEntityList) {
        GetGameWithoutLineupsDto getGameDto = new GetGameWithoutLineupsDto();
        getGameDto.setGameNr(gameEntity.getGameNr());
        getGameDto.setDate(gameEntity.getDate());
        getGameDto.setLocation(gameEntity.getLocation());
        getGameDto.setInnings(gameEntity.getInnings());
        getGameDto.setAssociation(mapAssociationEntityToGetAssociationDto(gameEntity.getAssociation()));
        getGameDto.setLeague(mapLeagueEntityToGetLeagueDto(gameEntity.getLeague()));
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
    public GameUmpireEntity mapToGameUmpireEntity(GameEntity gameEntity, UmpireEntity umpire) {
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

    public GetTeamWithoutPlayerListDto mapTeamEntityToGetTeamWithoutPlayerListDto(TeamEntity teamEntity) {
        GetTeamWithoutPlayerListDto getTeamWithoutPlayerListDto = new GetTeamWithoutPlayerListDto();
        getTeamWithoutPlayerListDto.setTeamId(teamEntity.getId());
        getTeamWithoutPlayerListDto.setName(teamEntity.getName());
        getTeamWithoutPlayerListDto.setClub(mapClubEntityToGetClubDto(teamEntity.getClub()));
        getTeamWithoutPlayerListDto.setManager(mapManagerEntityToGetManagerDto(teamEntity.getManager()));
        getTeamWithoutPlayerListDto.setLeague(mapLeagueEntityToGetLeagueDto(teamEntity.getLeague()));
        getTeamWithoutPlayerListDto.setTeamLogo(teamEntity.getTeamLogo());
        return getTeamWithoutPlayerListDto;
    }

    // TeamEntity -> GetTeamWithPlayerListDto
    public GetTeamWithPlayerListDto mapTeamEntityToGetTeamWithPlayerListDto(TeamEntity teamEntity) {
        List<GetPlayerDto> players = new ArrayList<>();
        GetTeamWithPlayerListDto getTeamWithPlayerListDto = new GetTeamWithPlayerListDto();
        getTeamWithPlayerListDto.setName(teamEntity.getName());
        getTeamWithPlayerListDto.setClub(mapClubEntityToGetClubDto(teamEntity.getClub()));
        getTeamWithPlayerListDto.setManager(mapManagerEntityToGetManagerDto(teamEntity.getManager()));
        getTeamWithPlayerListDto.setLeague(mapLeagueEntityToGetLeagueDto(teamEntity.getLeague()));
        getTeamWithPlayerListDto.setTeamLogo(teamEntity.getTeamLogo());
        getTeamWithPlayerListDto.setPlayerList(players);
        for (TeamPlayerEntity teamPlayerEntity : teamEntity.getTeamplayer()) {
            getTeamWithPlayerListDto.getPlayerList().add(mapTeamPlayerEntityToGetPlayerDto(teamPlayerEntity));
        }
        return getTeamWithPlayerListDto;
    }

    public GetFinishedGameDto mapToGetFinishedGameDto(GameEntity gameEntity, List<GameUmpireEntity> gameUmpireEntities) {
        GetFinishedGameDto getFinishedGameDto = new GetFinishedGameDto();
        getFinishedGameDto.setGameNr(gameEntity.getGameNr());
        getFinishedGameDto.setDate(gameEntity.getDate());
        getFinishedGameDto.setLocation(gameEntity.getLocation());
        getFinishedGameDto.setInnings(gameEntity.getInnings());
        getFinishedGameDto.setAssociation(mapAssociationEntityToGetAssociationDto(gameEntity.getAssociation()));
        getFinishedGameDto.setLeague(mapLeagueEntityToGetLeagueDto(gameEntity.getLeague()));
        getFinishedGameDto.setScorer(mapScorerEntityToGetScorerDto(gameEntity.getScorer()));
        getFinishedGameDto.setUmpireList(new ArrayList<>());

        for (GameUmpireEntity gameUmpire : gameUmpireEntities) {
            GetUmpireDto getUmpireDto = new GetUmpireDto();
            getUmpireDto.setUmpireId(gameUmpire.getUmpire().getId());
            getUmpireDto.setPassnumber(gameUmpire.getUmpire().getPassnumber());
            getUmpireDto.setName(gameUmpire.getUmpire().getFirstName(), gameUmpire.getUmpire().getLastName());
            getFinishedGameDto.getUmpireList().add(getUmpireDto);
        }

        GetTeamWithPlayerListDto guestTeam = new GetTeamWithPlayerListDto();
        guestTeam.setTeamId(gameEntity.getGuest().getTeam().getId());
        guestTeam.setName(gameEntity.getGuest().getTeam().getName());
        guestTeam.setClub(mapClubEntityToGetClubDto(gameEntity.getGuest().getTeam().getClub()));
        guestTeam.setManager(mapManagerEntityToGetManagerDto(gameEntity.getGuest().getTeam().getManager()));
        guestTeam.setLeague(mapLeagueEntityToGetLeagueDto(gameEntity.getGuest().getTeam().getLeague()));
        guestTeam.setTeamLogo(gameEntity.getGuest().getTeam().getTeamLogo());
        guestTeam.setPlayerList(new ArrayList<>());
        for (TeamPlayerEntity teamPlayer : gameEntity.getGuest().getTeam().getTeamplayer()) {
            guestTeam.getPlayerList().add(mapPlayerEntityToGetPlayerDto(teamPlayer.getPlayer()));
        }

        GetTeamWithPlayerListDto hostTeam = new GetTeamWithPlayerListDto();
        hostTeam.setTeamId(gameEntity.getHost().getTeam().getId());
        hostTeam.setName(gameEntity.getHost().getTeam().getName());
        hostTeam.setClub(mapClubEntityToGetClubDto(gameEntity.getHost().getTeam().getClub()));
        hostTeam.setManager(mapManagerEntityToGetManagerDto(gameEntity.getHost().getTeam().getManager()));
        hostTeam.setLeague(mapLeagueEntityToGetLeagueDto(gameEntity.getHost().getTeam().getLeague()));
        hostTeam.setTeamLogo(gameEntity.getHost().getTeam().getTeamLogo());
        hostTeam.setPlayerList(new ArrayList<>());
        for (TeamPlayerEntity teamPlayer : gameEntity.getHost().getTeam().getTeamplayer()) {
            hostTeam.getPlayerList().add(mapPlayerEntityToGetPlayerDto(teamPlayer.getPlayer()));
        }

        getFinishedGameDto.setGuestTeam(hostTeam);
        getFinishedGameDto.setHostTeam(hostTeam);

        getFinishedGameDto.setAttendance(gameEntity.getAttendance());
        getFinishedGameDto.setStartTime(gameEntity.getStartTime());
        getFinishedGameDto.setEndTime(gameEntity.getEndTime());
        getFinishedGameDto.setDurationInMinutes(gameEntity.getDurationInMinutes());
        return getFinishedGameDto;
    }

    // GameEntity + List<GetLineupDto> -> GetGameWithLineupsDto
    public GetGameWithLineupsDto mapToGetGameWithLineupsDto(GameEntity gameEntity, LineupEntity hostLineup, LineupEntity guestLineup) {
        GetGameWithLineupsDto getGameWithLineupsDto = new GetGameWithLineupsDto();
        getGameWithLineupsDto.setGameNr(gameEntity.getGameNr());
        getGameWithLineupsDto.setDate(gameEntity.getDate());
        getGameWithLineupsDto.setLocation(gameEntity.getLocation());
        getGameWithLineupsDto.setInnings(gameEntity.getInnings());
        getGameWithLineupsDto.setAssociation(mapAssociationEntityToGetAssociationDto(gameEntity.getAssociation()));
        getGameWithLineupsDto.setLeague(mapLeagueEntityToGetLeagueDto(gameEntity.getLeague()));
        getGameWithLineupsDto.setScorer(mapScorerEntityToGetScorerDto(gameEntity.getScorer()));
        getGameWithLineupsDto.setUmpireList(new ArrayList<>());

        for (GameUmpireEntity gameUmpire : gameEntity.getGameUmpire()) {
            getGameWithLineupsDto.getUmpireList().add(mapUmpireEntityToGetUmpireDto(gameUmpire.getUmpire()));
        }

        GetTeamWithPlayerListDto guestTeam = new GetTeamWithPlayerListDto();
        guestTeam.setTeamId(guestLineup.getTeam().getId());
        guestTeam.setName(guestLineup.getTeam().getName());
        guestTeam.setClub(mapClubEntityToGetClubDto(guestLineup.getTeam().getClub()));
        guestTeam.setManager(mapManagerEntityToGetManagerDto(guestLineup.getTeam().getManager()));
        guestTeam.setLeague(mapLeagueEntityToGetLeagueDto(guestLineup.getTeam().getLeague()));
        guestTeam.setTeamLogo(guestLineup.getTeam().getTeamLogo());
        guestTeam.setPlayerList(new ArrayList<>());
        for (TeamPlayerEntity teamPlayer : guestLineup.getTeam().getTeamplayer()) {
            guestTeam.getPlayerList().add(mapPlayerEntityToGetPlayerDto(teamPlayer.getPlayer()));
        }

        GetTeamWithPlayerListDto hostTeam = new GetTeamWithPlayerListDto();
        hostTeam.setTeamId(hostLineup.getTeam().getId());
        hostTeam.setName(hostLineup.getTeam().getName());
        hostTeam.setClub(mapClubEntityToGetClubDto(hostLineup.getTeam().getClub()));
        hostTeam.setManager(mapManagerEntityToGetManagerDto(hostLineup.getTeam().getManager()));
        hostTeam.setLeague(mapLeagueEntityToGetLeagueDto(hostLineup.getTeam().getLeague()));
        hostTeam.setTeamLogo(hostLineup.getTeam().getTeamLogo());
        hostTeam.setPlayerList(new ArrayList<>());
        for (TeamPlayerEntity teamPlayer : hostLineup.getTeam().getTeamplayer()) {
            hostTeam.getPlayerList().add(mapPlayerEntityToGetPlayerDto(teamPlayer.getPlayer()));
        }

        getGameWithLineupsDto.setGuestTeam(hostTeam);
        getGameWithLineupsDto.setHostTeam(hostTeam);
        return getGameWithLineupsDto;
    }
}