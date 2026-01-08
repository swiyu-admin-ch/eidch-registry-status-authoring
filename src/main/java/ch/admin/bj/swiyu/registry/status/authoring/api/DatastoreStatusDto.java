/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.status.authoring.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DatastoreStatus", enumAsRef = true)
public enum DatastoreStatusDto {
    ACTIVE,
    DEACTIVATED,
    DISABLED,
    SETUP,
}
