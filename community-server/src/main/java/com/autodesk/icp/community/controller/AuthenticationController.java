//
// Copyright (C) 2015 by Autodesk, Inc. All Rights Reserved.
//
// The information contained herein is confidential, proprietary
// to Autodesk, Inc., and considered a trade secret as defined
// in section 499C of the penal code of the State of California.
// Use of this information by anyone other than authorized
// employees of Autodesk, Inc. is granted only under a written
// non-disclosure agreement, expressly prescribing the scope
// and manner of such use.
//
// AUTODESK MAKES NO WARRANTIES, EXPRESS OR IMPLIED, AS TO THE
// CORRECTNESS OF THIS CODE OR ANY DERIVATIVE WORKS WHICH INCORPORATE
// IT. AUTODESK PROVIDES THE CODE ON AN "AS-IS" BASIS AND EXPLICITLY
// DISCLAIMS ANY LIABILITY, INCLUDING CONSEQUENTIAL AND INCIDENTAL
// DAMAGES FOR ERRORS, OMISSIONS, AND OTHER PROBLEMS IN THE CODE.
//
// Use, duplication, or disclosure by the U.S. Government is subject
// to restrictions set forth in FAR 52.227-19 (Commercial Computer
// Software Restricted Rights) and DFAR 252.227-7013(c)(1)(ii)
// (Rights in Technical Data and Computer Software), as applicable.
//
package com.autodesk.icp.community.controller;

import java.security.Security;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.autodesk.icp.community.common.model.MessageResponse;
import com.autodesk.icp.community.common.model.User;
import com.autodesk.icp.community.common.util.Consts;
import com.autodesk.icp.community.exception.UnauthenticatedException;
import com.autodesk.icp.community.service.AuthenticationService;
import com.autodesk.icp.community.util.WSUtils;

/**
 * @author Oliver Wu
 */
@Controller
public class AuthenticationController extends BaseController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private AuthenticationService authService;

    @MessageMapping(value = "/login")
    @SendToUser("/queue/login")
    public MessageResponse login(Message<?> message, @Payload final User user) {
        if (user.getLoginId().equals("ads\\wuol")) {            
            
            MessageResponse mr = new MessageResponse();
            mr.setStatus(Consts.MESSAGE_STATUS_OK);
            mr.setPayload(user);
            
            WSUtils.saveUser(message, user);
            
            SecurityContextHolder.getContext().setAuthentication(new Authentication() {
                
                @Override
                public String getName() {
                    // TODO Auto-generated method stub
                    return user.getDisplayName();
                }
                
                @Override
                public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

                    
                }
                
                @Override
                public boolean isAuthenticated() {
                    // TODO Auto-generated method stub
                    return true;
                }
                
                @Override
                public Object getPrincipal() {

                    return user;
                }
                
                @Override
                public Object getDetails() {
                    // TODO Auto-generated method stub
                    return null;
                }
                
                @Override
                public Object getCredentials() {
                    // TODO Auto-generated method stub
                    return null;
                }
                
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    // TODO Auto-generated method stub
                    return null;
                }
            });
            
            return mr;
        } else {
            throw new UnauthenticatedException();
        }
//        User authedUser = authService.login(user.getLoginId(), user.getPassword());
//        
//        if (authedUser!= null && authedUser.isAuthed()) {
//            HttpSession session = (HttpSession)(((Map)headerAccessor.getMessageHeaders().get("simpSessionAttributes")).get(Consts.SESSION_ATTR_SESSION));
//            session.setAttribute(Consts.SESSION_ATTR_USER, authedUser);
//            MessageResponse mr = new MessageResponse();
//            mr.setStatus(Consts.MESSAGE_STATUS_OK);
//            mr.setPayload(authedUser);
//            return mr;
//        } else {
//            throw new UnauthenticatedException();
//        }      
    }
    

    @MessageExceptionHandler(value = UnauthenticatedException.class)
    @SendToUser(value = "/queue/authError", broadcast = false)
    public MessageResponse handleUnauthenticationException(Exception exception) {
        return handleException(exception);
    }
}
