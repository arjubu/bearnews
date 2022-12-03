package com.baylor.se.project.bearnews.Controller.dto;

import java.util.List;

public class UserProfileDto {

    public String reqUserEmail;

    public String reqFirstName;

    public String reqLastName;

    public String reqSocialLink;

    public List<String> reqInterestList;

    public String getReqUserEmail() {
        return reqUserEmail;
    }

    public String getReqFirstName() {
        return reqFirstName;
    }

    public void setReqFirstName(String reqFirstName) {
        this.reqFirstName = reqFirstName;
    }

    public void setReqUserEmail(String reqUserEmail) {
        this.reqUserEmail = reqUserEmail;
    }

    public String getReqLastName() {
        return reqLastName;
    }

    public void setReqLastName(String reqLastName) {
        this.reqLastName = reqLastName;
    }

    public String getReqSocialLink() {
        return reqSocialLink;
    }

    public void setReqSocialLink(String reqSocialLink) {
        this.reqSocialLink = reqSocialLink;
    }

    public List<String> getReqInterestList() {
        return reqInterestList;
    }

    public void setReqInterestList(List<String> reqInterestList) {
        this.reqInterestList = reqInterestList;
    }
}
