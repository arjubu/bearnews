package com.baylor.se.project.bearnews.Controller.dto;

import java.util.List;

public class UserProfileDto {

    public String reqUserEmail;

    public String reqName;


    public String reqSocialLink;

    public List<String> reqInterestList;

    public String getReqUserEmail() {
        return reqUserEmail;
    }

    public void setReqUserEmail(String reqUserEmail){
        this.reqUserEmail = reqUserEmail;
    }
    public String getReqName() {
        return reqName;
    }
    public void setReqName(String reqName) {
        this.reqName = reqName;
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
