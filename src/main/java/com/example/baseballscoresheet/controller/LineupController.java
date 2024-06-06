package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.Utility;
import com.example.baseballscoresheet.exceptionHandling.PlayerIsNotAvailableException;
import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.entities.*;
import com.example.baseballscoresheet.model.dtos.lineup.LineupAddDto;
import com.example.baseballscoresheet.model.dtos.lineup.LineupGetDto;
import com.example.baseballscoresheet.model.dtos.player.PlayerForLineupDto;
import com.example.baseballscoresheet.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/lineup")
public class LineupController {

    private final MappingService mappingService;
    private final LineupService lineupService;
    private final LineupTeamPlayerService lineupTeamPlayerService;
    private final TeamService teamService;
    private final TeamPlayerService teamPlayerService;
    private final PositionService positionService;
    private final GameService gameService;

    public LineupController(MappingService mappingService, LineupService lineupService,
                            LineupTeamPlayerService lineupTeamPlayerService, TeamService teamService,
                            TeamPlayerService teamPlayerService, PositionService positionService, GameService gameService) {
        this.mappingService = mappingService;
        this.lineupService = lineupService;
        this.lineupTeamPlayerService = lineupTeamPlayerService;
        this.teamService = teamService;
        this.teamPlayerService = teamPlayerService;
        this.positionService = positionService;
        this.gameService = gameService;
    }

    // Endpoint for saving lineups
    @Operation(summary = "saves lineup(s)")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "created lineup(s)", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LineupGetDto.class))}), @ApiResponse(responseCode = "400", description = "invalid JSON posted", content = @Content), @ApiResponse(responseCode = "401", description = "not authorized", content = @Content), @ApiResponse(responseCode = "500", description = "server error", content = @Content)})
    @RolesAllowed("user")
    @PostMapping
    public ResponseEntity<List<LineupGetDto>> createLineups(@RequestBody @Valid List<LineupAddDto> newLineups) {
        List<LineupGetDto> addedLineup = new ArrayList<>();
        List<LineupTeamPlayerEntity> addedLineupTeamPlayers = new ArrayList<>();

        // both lineups are mapped and saved
        for (LineupAddDto lineupAddDto : newLineups) {
            TeamEntity teamEntity = this.teamService.findTeamById(lineupAddDto.getTeamId());
            GameEntity gameEntity = this.gameService.findGameById(lineupAddDto.getGameId());
            LineupEntity lineupEntity = this.mappingService.mapTeamEntityToLineupEntity(teamEntity, gameEntity);
            this.lineupService.saveLineup(lineupEntity);
        }

        // a new LineupTeamPlayerEntity object is created for each player that can be found in the lineup
        // for this, we iterate over each transferred lineup once
        for (LineupAddDto lineupAddDto : newLineups) {
            for (PlayerForLineupDto playerForLineupDto : lineupAddDto.getPlayerList()) {
                if (!Utility.checkIfPlayerIsAlreadyAssignedToLineup(playerForLineupDto.getPlayerId())) {
                    LineupEntity lineupEntity2 = lineupService.findLineupByTeamId(lineupAddDto.getTeamId());
                    TeamPlayerEntity teamPlayer = this.teamPlayerService.findTeamPlayerEntityByTeamIdAndPlayerId(lineupEntity2.getTeam().getId(), playerForLineupDto.getPlayerId());
                    PositionEntity position = this.positionService.findById(playerForLineupDto.getPositionId());
                    LineupTeamPlayerEntity lineupTeamPlayerEntity = this.mappingService.mapToLineupTeamPlayerEntity(
                            playerForLineupDto, lineupEntity2, teamPlayer, position);
                    addedLineupTeamPlayers.add(lineupTeamPlayerEntity);
                    this.lineupTeamPlayerService.saveLineupTeamPlayerEntity(lineupTeamPlayerEntity);
                } else {
                    throw new PlayerIsNotAvailableException("Player with id " + playerForLineupDto.getPlayerId() + " already assigned to lineup");
                }
            }
        }
        LineupGetDto lineupGetDto1 = new LineupGetDto();
        LineupGetDto lineupGetDto2 = new LineupGetDto();
        lineupGetDto1.setPlayerList(new ArrayList<>());
        lineupGetDto2.setPlayerList(new ArrayList<>());
        for (LineupTeamPlayerEntity lineupTeamPlayer : addedLineupTeamPlayers) {
            if (Objects.equals(lineupTeamPlayer.getTeamPlayer().getTeam().getId(), newLineups.get(0).getTeamId())) {
                lineupGetDto1.setTeamId(lineupTeamPlayer.getTeamPlayer().getTeam().getId());
                lineupGetDto1.getPlayerList().add(this.mappingService.mapLineupTeamPlayerEntityToGetPlayerFromLineUpDto(lineupTeamPlayer));
            } else {
                lineupGetDto2.setTeamId(lineupTeamPlayer.getTeamPlayer().getTeam().getId());
                lineupGetDto2.getPlayerList().add(this.mappingService.mapLineupTeamPlayerEntityToGetPlayerFromLineUpDto(lineupTeamPlayer));
            }
        }
        addedLineup.add(lineupGetDto1);
        addedLineup.add(lineupGetDto2);
        return new ResponseEntity<>(addedLineup, HttpStatus.CREATED);
    }
}