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
package com.autodesk.icp.community.common.model;

/**
 * @author Oliver Wu
 */
public class ServiceResponse<T> {
    private ServiceStatus status;
    private ServiceError error;
    private T payload;

    public ServiceResponse(ServiceStatus status, ServiceError error) {
        super();
        this.status = status;
        this.error = error;
    }

    public ServiceResponse(ServiceStatus status, T payload) {
        super();
        this.status = status;
        this.payload = payload;
    }

    public ServiceResponse() {
        super();
    }

    /**
     * @return the status
     */
    public ServiceStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    /**
     * @return the payload
     */
    public T getPayload() {
        return payload;
    }

    /**
     * @param payload
     *            the payload to set
     */
    public void setPayload(T payload) {
        this.payload = payload;
    }

    /**
     * @return the error
     */
    public ServiceError getError() {
        return error;
    }

    /**
     * @param error
     *            the error to set
     */
    public void setError(ServiceError error) {
        this.error = error;
    }
}
