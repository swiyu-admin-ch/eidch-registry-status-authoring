/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.status.authoring.service;

import ch.admin.bj.swiyu.registry.status.authoring.api.DatastoreEntityResponseDto;
import ch.admin.bj.swiyu.registry.status.authoring.api.DatastoreStatusDto;
import ch.admin.bj.swiyu.registry.status.authoring.api.VcEntityResponseDto;
import ch.admin.bj.swiyu.registry.status.authoring.domain.VcEntity;
import ch.admin.bj.swiyu.registry.status.authoring.domain.datastore.DatastoreEntity;
import ch.admin.bj.swiyu.registry.status.authoring.domain.datastore.DatastoreStatus;
import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class VcEntityMapper {

    public static DatastoreEntityResponseDto toDatastoreEntityResponseDto(
        DatastoreEntity entity,
        Map<String, VcEntityResponseDto> files
    ) {
        return new DatastoreEntityResponseDto(entity.getId(), toDatastoreStatusDto(entity.getStatus()), files);
    }

    public static DatastoreStatus toDatastoreStatus(DatastoreStatusDto source) {
        if (source == null) {
            return null;
        }
        return switch (source) {
            case ACTIVE -> DatastoreStatus.ACTIVE;
            case DEACTIVATED -> DatastoreStatus.DEACTIVATED;
            case DISABLED -> DatastoreStatus.DISABLED;
            case SETUP -> DatastoreStatus.SETUP;
        };
    }

    public static VcEntityResponseDto toVcEntityResponseDto(VcEntity entity) {
        return new VcEntityResponseDto(entity.getRawVc() != null, entity.getReadUri());
    }

    public static VcEntityResponseDto toNotConfiguredVcEntityResponseDto(String readUri) {
        return new VcEntityResponseDto(false, readUri);
    }

    private static DatastoreStatusDto toDatastoreStatusDto(DatastoreStatus source) {
        if (source == null) {
            return null;
        }
        return switch (source) {
            case ACTIVE -> DatastoreStatusDto.ACTIVE;
            case DEACTIVATED -> DatastoreStatusDto.DEACTIVATED;
            case DISABLED -> DatastoreStatusDto.DISABLED;
            case SETUP -> DatastoreStatusDto.SETUP;
        };
    }
}
