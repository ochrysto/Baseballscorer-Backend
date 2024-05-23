package com.example.baseballscoresheet.controller;

import com.example.baseballscoresheet.Utility;
import com.example.baseballscoresheet.mapping.MappingService;
import com.example.baseballscoresheet.model.entities.*;
import com.example.baseballscoresheet.model.dtos.lineup.AddLineupDto;
import com.example.baseballscoresheet.model.dtos.lineup.GetLineupDto;
import com.example.baseballscoresheet.model.dtos.lineup.AddPlayerToLineupDto;
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
import org.springframework.web.server.ResponseStatusException;

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

    public LineupController(MappingService mappingService, LineupService lineupService,
                            LineupTeamPlayerService lineupTeamPlayerService, TeamService teamService,
                            TeamPlayerService teamPlayerService, PositionService positionService) {
        this.mappingService = mappingService;
        this.lineupService = lineupService;
        this.lineupTeamPlayerService = lineupTeamPlayerService;
        this.teamService = teamService;
        this.teamPlayerService = teamPlayerService;
        this.positionService = positionService;
    }

    // Endpoint for saving lineups
    @Operation(summary = "saves lineup(s)")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "created lineup(s)", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GetLineupDto.class))}), @ApiResponse(responseCode = "400", description = "invalid JSON posted", content = @Content), @ApiResponse(responseCode = "401", description = "not authorized", content = @Content), @ApiResponse(responseCode = "500", description = "server error", content = @Content)})
    @RolesAllowed("user")
    @PostMapping
    public ResponseEntity<List<GetLineupDto>> createLineups(@RequestBody @Valid List<AddLineupDto> newLineups) {
        List<GetLineupDto> addedLineup = new ArrayList<>();
        List<LineupTeamPlayerEntity> addedLineupTeamPlayers = new ArrayList<>();

        // both lineups are mapped and saved
        for (AddLineupDto addLineupDto : newLineups) {
            TeamEntity teamEntity = this.teamService.findTeamById(addLineupDto.getTeamId());
            LineupEntity lineupEntity = this.mappingService.mapAddLineupDtoToLineupEntity(teamEntity);
            this.lineupService.saveLineup(lineupEntity);
        }

        // a new LineupTeamPlayerEntity object is created for each player that can be found in the lineup
        // for this, we iterate over each transferred lineup once
        for (AddLineupDto addLineupDto : newLineups) {
            for (AddPlayerToLineupDto addPlayerToLineupDto : addLineupDto.getPlayerDtoSet()) {
                if (!Utility.checkIfPlayerIsAlreadyAssignedToLineup(addPlayerToLineupDto.getPlayerId())) {
                    LineupEntity lineupEntity2 = lineupService.findLineupByTeamId(addLineupDto.getTeamId());
                    TeamPlayerEntity teamPlayer = this.teamPlayerService.findTeamPlayerEntityByTeamIdAndPlayerId(lineupEntity2.getTeam().getId(), addPlayerToLineupDto.getPlayerId());
                    PositionEntity position = this.positionService.findById(addPlayerToLineupDto.getPosition());
                    LineupTeamPlayerEntity lineupTeamPlayerEntity = this.mappingService.mapToLineupTeamPlayerEntity(
                            addPlayerToLineupDto, lineupEntity2, teamPlayer, position);
                    addedLineupTeamPlayers.add(lineupTeamPlayerEntity);
                    this.lineupTeamPlayerService.saveLineupTeamPlayerEntity(lineupTeamPlayerEntity);
                } else {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Player with id " + addPlayerToLineupDto.getPlayerId() + " already assigned to lineup");
                }
            }
        }
        GetLineupDto getLineupDto1 = new GetLineupDto();
        GetLineupDto getLineupDto2 = new GetLineupDto();
        getLineupDto1.setPlayerList(new ArrayList<>());
        getLineupDto2.setPlayerList(new ArrayList<>());
        for (LineupTeamPlayerEntity lineupTeamPlayer : addedLineupTeamPlayers) {
            if (Objects.equals(lineupTeamPlayer.getTeamPlayer().getTeam().getId(), newLineups.getFirst().getTeamId())) {
                getLineupDto1.setTeamId(lineupTeamPlayer.getTeamPlayer().getTeam().getId());
                getLineupDto1.getPlayerList().add(this.mappingService.mapTeamPlayerEntityToGetPlayerFromLineUpDto(lineupTeamPlayer));
            } else {
                getLineupDto2.setTeamId(lineupTeamPlayer.getTeamPlayer().getTeam().getId());
                getLineupDto2.getPlayerList().add(this.mappingService.mapTeamPlayerEntityToGetPlayerFromLineUpDto(lineupTeamPlayer));
            }
        }
        addedLineup.add(getLineupDto1);
        addedLineup.add(getLineupDto2);
        return new ResponseEntity<>(addedLineup, HttpStatus.CREATED);
    }
}