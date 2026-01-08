/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.status.authoring.infrastructure.web.controller;

import ch.admin.bj.swiyu.registry.status.authoring.api.ApiErrorDto;
import ch.admin.bj.swiyu.registry.status.authoring.api.DatastoreEntityRequestDto;
import ch.admin.bj.swiyu.registry.status.authoring.api.DatastoreEntityResponseDto;
import ch.admin.bj.swiyu.registry.status.authoring.service.VcEntityService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/entry")
@AllArgsConstructor
@Tag(name = "Datastore", description = "Manages entries in the Datastore.")
public class DatastoreEntityController {

    private final VcEntityService vcEntityService;

    @Timed
    @GetMapping(value = "/{datastoreEntryId}")
    @Operation(
        summary = "IF-006.001 - Get an entry configuration from the Datastore.",
        description = "Get an entry configuration from the Datastore."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Success", useReturnTypeSchema = true),
            @ApiResponse(
                responseCode = "404",
                description = "Not Found",
                content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            ),
        }
    )
    public DatastoreEntityResponseDto getDatastoreEntry(
        @Valid @PathVariable(name = "datastoreEntryId") UUID datastoreEntryId
    ) {
        return this.vcEntityService.getDataStoreEntity(datastoreEntryId);
    }

    @Timed
    @PostMapping(value = "/")
    @Operation(
        summary = "IF-006.002 - Create a new entry in the Datastore.",
        description = "Create a new entry in the Datastore."
    )
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Success", useReturnTypeSchema = true) })
    @ResponseStatus(code = HttpStatus.CREATED)
    public DatastoreEntityResponseDto createDatastoreEntry() {
        return this.vcEntityService.createDataStoreEntity();
    }

    @Timed
    @PatchMapping(value = "/{datastoreEntryId}")
    @Operation(
        summary = "IF-006.003 - Update an entry configuration from the Datastore.",
        description = "Update an entry configuration from the Datastore."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Success", useReturnTypeSchema = true),
            @ApiResponse(
                responseCode = "404",
                description = "Not Found",
                content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            ),
        }
    )
    public DatastoreEntityResponseDto updateDatastoreEntry(
        @Valid @PathVariable(name = "datastoreEntryId") UUID datastoreEntryId,
        @RequestBody DatastoreEntityRequestDto request
    ) {
        return this.vcEntityService.updateDatastoreEntity(datastoreEntryId, request);
    }
}
