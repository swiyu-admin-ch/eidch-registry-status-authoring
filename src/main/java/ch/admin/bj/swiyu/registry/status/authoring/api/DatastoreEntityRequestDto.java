/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.status.authoring.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DatastoreEntityRequest")
public record DatastoreEntityRequestDto(DatastoreStatusDto status) {}
