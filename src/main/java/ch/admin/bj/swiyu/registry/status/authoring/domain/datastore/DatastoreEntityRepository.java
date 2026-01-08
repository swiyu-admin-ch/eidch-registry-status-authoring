/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.status.authoring.domain.datastore;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatastoreEntityRepository extends JpaRepository<DatastoreEntity, UUID> {}
