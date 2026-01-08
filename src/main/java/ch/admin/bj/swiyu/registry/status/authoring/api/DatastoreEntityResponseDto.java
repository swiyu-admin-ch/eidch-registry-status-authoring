/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.status.authoring.api;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import java.util.UUID;

@Schema(name = "DatastoreEntityResponse")
public record DatastoreEntityResponseDto(UUID id, DatastoreStatusDto status, Map<String, VcEntityResponseDto> files) {}
