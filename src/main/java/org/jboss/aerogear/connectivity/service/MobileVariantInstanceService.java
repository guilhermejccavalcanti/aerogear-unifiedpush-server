/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.connectivity.service;

import java.util.List;
import org.jboss.aerogear.connectivity.model.MobileVariantInstanceImpl;

public interface MobileVariantInstanceService {

    MobileVariantInstanceImpl addMobileVariantInstance(MobileVariantInstanceImpl mobileApplicationInstance);

    MobileVariantInstanceImpl updateMobileVariantInstance(MobileVariantInstanceImpl mobileApplicationInstance);

    MobileVariantInstanceImpl findById(String primaryKey);

    void removeMobileVariantInstance(MobileVariantInstanceImpl instance);

    /**
     * Used for "Device Registration": loads all installations for one variant, containing the same token
     */
    List<MobileVariantInstanceImpl> findMobileVariantInstancesForVariantByToken(String variantID, String deviceToken);

    /**
     * Used for Broadcast. Query for all IDs on a certain variant
     */
    List<String> findAllDeviceTokenForVariantID(String variantID);

    // =========================== SELECTIVE SENDER =================================
    List<String> findAllDeviceTokenForVariantIDByAliasAndDeviceType(String variantID, List<String> aliases, List<String> deviceTypes);

    List<String> findAllDeviceTokenForVariantIDByCategoryAndAlias(String variantID, String category, List<String> aliases);

    List<String> findAllDeviceTokenForVariantIDByCategory(String variantID, String category);

    void removeMobileVariantInstances(List<MobileVariantInstanceImpl> instances);
}
