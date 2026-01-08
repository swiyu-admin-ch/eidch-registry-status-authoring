/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.status.authoring.infrastructure.web.controller;

import ch.admin.bj.swiyu.registry.status.authoring.api.ApiErrorDto;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/statuslist")
@AllArgsConstructor
@Tag(name = "Status List", description = "Manages Status List entries from the datastore.")
public class StatusListController {

    private final VcEntityService vcEntityService;

    @Timed
    @PutMapping(value = "/{datastoreEntryId}.jwt")
    @Operation(
        summary = "IF-006.101 - Update a token statuslist vc entry in the datastore.",
        description = "Update a token statuslist vc entry in the datastore."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Success", useReturnTypeSchema = true),
            @ApiResponse(
                responseCode = "425",
                description = "Too Early, Resource cannot be edited.",
                content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Not Found",
                content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
            ),
        }
    )
    public DatastoreEntityResponseDto updateVcEntry(
        @Valid @PathVariable UUID datastoreEntryId,
        @RequestBody String body
    ) {
        return vcEntityService.updateVcEntity(datastoreEntryId, body);
    }
}
