/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.status.authoring.domain.datastore;

import ch.admin.bj.swiyu.registry.status.authoring.common.exception.ResourceNotFoundException;
import ch.admin.bj.swiyu.registry.status.authoring.common.exception.ResourceNotReadyException;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Slf4j
public class DatastoreEntityDomainService {

    private final DatastoreEntityRepository datastoreEntityRepository;

    public static void validateCanEdit(DatastoreEntity entry) throws ResourceNotReadyException {
        if (entry.getStatus() == DatastoreStatus.DISABLED) throw new ResourceNotReadyException(
            entry.getId().toString(),
            DatastoreEntity.class
        );
    }

    @Transactional(propagation = Propagation.MANDATORY) // mandatory since domain service (transaction handled by invoking service)
    public DatastoreEntity getDatastoreEntity(UUID id) {
        return this.datastoreEntityRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(id.toString(), DatastoreEntity.class)
            );
    }

    @Transactional(propagation = Propagation.MANDATORY) // mandatory since domain service (transaction handled by invoking service)
    public DatastoreEntity createEmptyDatastoreEntity() {
        return this.datastoreEntityRepository.save(new DatastoreEntity());
    }

    @Transactional(propagation = Propagation.MANDATORY) // mandatory since domain service (transaction handled by invoking service)
    public void updateDatastoreEntity(UUID id, DatastoreStatus status) {
        var entity = this.getDatastoreEntity(id);
        entity.changeStatus(status);
        this.datastoreEntityRepository.save(entity);
    }

    @Transactional(propagation = Propagation.MANDATORY) // mandatory since domain service (transaction handled by invoking service)
    public void activateDatastoreEntity(DatastoreEntity base) {
        base.changeStatus(DatastoreStatus.ACTIVE);
        this.datastoreEntityRepository.save(base);
    }
}
