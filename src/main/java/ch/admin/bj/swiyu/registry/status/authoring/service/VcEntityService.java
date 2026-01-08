/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.status.authoring.service;

import static ch.admin.bj.swiyu.registry.status.authoring.domain.VcType.TokenStatusListJWT;
import static ch.admin.bj.swiyu.registry.status.authoring.domain.datastore.DatastoreEntityDomainService.validateCanEdit;
import static ch.admin.bj.swiyu.registry.status.authoring.service.VcEntityMapper.*;
import static java.util.stream.Collectors.toMap;

import ch.admin.bj.swiyu.registry.status.authoring.api.DatastoreEntityRequestDto;
import ch.admin.bj.swiyu.registry.status.authoring.api.DatastoreEntityResponseDto;
import ch.admin.bj.swiyu.registry.status.authoring.api.VcEntityResponseDto;
import ch.admin.bj.swiyu.registry.status.authoring.common.config.StatusRegistryProperties;
import ch.admin.bj.swiyu.registry.status.authoring.domain.VcEntity;
import ch.admin.bj.swiyu.registry.status.authoring.domain.VcEntityRepository;
import ch.admin.bj.swiyu.registry.status.authoring.domain.VcType;
import ch.admin.bj.swiyu.registry.status.authoring.domain.datastore.DatastoreEntityDomainService;
import jakarta.validation.Valid;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class VcEntityService {

    private final DatastoreEntityDomainService datastoreEntityDomainService;
    private final VcEntityRepository vcEntityRepository;
    private final StatusRegistryProperties statusRegistryProperties;

    @Transactional(readOnly = true)
    public DatastoreEntityResponseDto getDataStoreEntity(UUID datastoreEntryId) {
        var datastoreEntity = this.datastoreEntityDomainService.getDatastoreEntity(datastoreEntryId);
        var files = this.getAllDatastoreFileEntity(datastoreEntryId);
        return toDatastoreEntityResponseDto(datastoreEntity, files);
    }

    @Transactional
    public DatastoreEntityResponseDto createDataStoreEntity() {
        var datastoreEntity = this.datastoreEntityDomainService.createEmptyDatastoreEntity();
        log.info("created datastore entry with id: {}", datastoreEntity.getId());
        return toDatastoreEntityResponseDto(datastoreEntity, this.getAllDatastoreFileEntity(datastoreEntity.getId()));
    }

    @Transactional
    public DatastoreEntityResponseDto updateDatastoreEntity(
        @Valid UUID datastoreEntryId,
        DatastoreEntityRequestDto request
    ) {
        if (request.status() != null) { // we don't allow setting the status to null
            this.datastoreEntityDomainService.updateDatastoreEntity(
                    datastoreEntryId,
                    toDatastoreStatus(request.status())
                );
        }
        var datastoreEntity = this.datastoreEntityDomainService.getDatastoreEntity(datastoreEntryId);
        log.info("updated datastore entry with id: {}", datastoreEntryId);
        return toDatastoreEntityResponseDto(datastoreEntity, getAllDatastoreFileEntity(datastoreEntryId));
    }

    @Transactional
    public DatastoreEntityResponseDto updateVcEntity(UUID datastoreEntryId, String encodedVc) {
        var datastoreEntity = this.datastoreEntityDomainService.getDatastoreEntity(datastoreEntryId);
        validateCanEdit(datastoreEntity);
        var vcType = TokenStatusListJWT;
        var vcPayload = this.extractVcPayload(encodedVc, vcType);
        var existing = this.vcEntityRepository.findByBase_IdAndVcType(datastoreEntity.getId(), vcType);
        if (existing.isEmpty()) {
            var vcEntity = new VcEntity(datastoreEntity, vcType, encodedVc, vcPayload, buildReadUri(datastoreEntryId));
            this.vcEntityRepository.save(vcEntity);
        } else {
            var vcEntity = existing.get();
            vcEntity.updateVc(encodedVc, vcPayload);
            vcEntityRepository.save(vcEntity);
        }
        this.datastoreEntityDomainService.activateDatastoreEntity(datastoreEntity);
        log.info("Updated vc for datastore entry with id: {}", datastoreEntity.getId());
        return toDatastoreEntityResponseDto(datastoreEntity, this.getAllDatastoreFileEntity(datastoreEntryId));
    }

    private Map<String, VcEntityResponseDto> getAllDatastoreFileEntity(UUID id) {
        var vcEntities = this.vcEntityRepository.findByBase_Id(id);
        var result = vcEntities
            .stream()
            .collect(toMap(e -> e.getVcType().name(), VcEntityMapper::toVcEntityResponseDto));
        // add vc entities for missing types
        for (VcType type : VcType.values()) {
            if (result.get(type.name()) == null) {
                result.put(type.name(), toNotConfiguredVcEntityResponseDto(buildReadUri(id)));
            }
        }
        return result;
    }

    private String extractVcPayload(String encodedVc, VcType expectedType) {
        return switch (expectedType) { // NOSONAR: we want the code being ready for other types
            case TokenStatusListJWT -> extractJwtPayload(encodedVc);
        };
    }

    private String extractJwtPayload(String encodedVc) {
        return new String(Base64.getDecoder().decode(encodedVc.split("\\.")[1]));
    }

    private String buildReadUri(UUID datastoreEntityId) {
        return MessageFormat.format(this.statusRegistryProperties.getDataUrlTemplate(), datastoreEntityId, "jwt");
    }
}
