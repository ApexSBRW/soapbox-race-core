package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.PersonaBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.jaxb.http.BadgeBundle;
import com.soapboxrace.jaxb.util.UnmarshalXML;

import javax.ejb.EJB;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.io.InputStream;

@Path("/badges")
public class Badges {

    @EJB
    private TokenSessionBO tokenSessionBO;

    @EJB
    private PersonaBO personaBO;

    @Path("/set")
    @PUT
    @Secured
    public String set(InputStream inputStream, @HeaderParam("securityToken") String securityToken) {
        Long activePersonaId = tokenSessionBO.getActivePersonaId(securityToken);

        if (activePersonaId != 0L)
            personaBO.updateBadges(activePersonaId, UnmarshalXML.unMarshal(inputStream, BadgeBundle.class));

        return "";
    }
}
