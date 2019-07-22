package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.FriendsBO;
import com.soapboxrace.jaxb.http.PersonaFriendsList;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/getfriendlistfromuserid")
public class GetFriendListFromUserId {

    @EJB
    private FriendsBO friendsBO;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public PersonaFriendsList getFriendsList(@HeaderParam("userId") Long userId) {
        return friendsBO.getFriendsList(userId);
    }
}