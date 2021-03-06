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
package org.jboss.aerogear.connectivity.rest.registry.applications;

import org.jboss.aerogear.connectivity.api.MobileVariant;
import org.jboss.aerogear.connectivity.model.MobileVariantInstanceImpl;
import org.jboss.aerogear.connectivity.service.MobileVariantInstanceService;
import org.jboss.aerogear.connectivity.service.MobileVariantService;
import org.jboss.aerogear.security.authz.Secure;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@TransactionAttribute
@Path("/applications/{variantID}/instances/")
@Secure("developer")
public class VariantInstanceEndpoint {

    @Inject
    private MobileVariantService mobileApplicationService;

    @Inject
    private MobileVariantInstanceService mobileApplicationInstanceService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findVariantInstances(@PathParam("variantID") String variantId){

        //Find the variant using the variantID
        MobileVariant mobileVariant =  mobileApplicationService.findByVariantID(variantId);

        if(mobileVariant == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Could not find requested Mobile Variant").build();
        }

        return Response.ok(mobileVariant.getInstances()).build();
    }

    @GET
    @Path("/{instanceID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findVariantInstances(@PathParam("variantID") String variantId, @PathParam("instanceID") String instanceId){

        MobileVariantInstanceImpl mobileVariantInstance = mobileApplicationInstanceService.findById(instanceId);

        if(mobileVariantInstance == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Could not find requested Mobile Variant Instance").build();
        }

        return Response.ok(mobileVariantInstance).build();
    }

    @PUT
    @Path("/{instanceID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateVariantInstance(MobileVariantInstanceImpl entity, @PathParam("variantID") String variantId, @PathParam("instanceID") String instanceId){

        MobileVariantInstanceImpl mobileVariantInstance = mobileApplicationInstanceService.findById(instanceId);

        if(mobileVariantInstance == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Could not find requested Mobile Variant Instance").build();
        }

        updateMobileApplicationInstance(entity, mobileVariantInstance);

        return Response.noContent().build();

    }

    @DELETE
    @Path("/{instanceID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeVariantInstance(@PathParam("variantID") String variantId, @PathParam("instanceID") String instanceId){

        MobileVariantInstanceImpl mobileVariantInstance = mobileApplicationInstanceService.findById(instanceId);

        if(mobileVariantInstance == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Could not find requested Mobile Variant Instance").build();
        }

        // remove it
        mobileApplicationInstanceService.removeMobileVariantInstance(mobileVariantInstance);

        return Response.noContent().build();
    }

    private void updateMobileApplicationInstance(
            MobileVariantInstanceImpl toUpdate,
            MobileVariantInstanceImpl postedVariant) {
        toUpdate.setCategory(postedVariant.getCategory());
        toUpdate.setDeviceToken(postedVariant.getDeviceToken());
        toUpdate.setAlias(postedVariant.getAlias());
        toUpdate.setDeviceType(postedVariant.getDeviceType());
        toUpdate.setMobileOperatingSystem(postedVariant
                .getMobileOperatingSystem());
        toUpdate.setOsVersion(postedVariant.getOsVersion());

        // update
        mobileApplicationInstanceService
                .updateMobileVariantInstance(toUpdate);
    }

}
