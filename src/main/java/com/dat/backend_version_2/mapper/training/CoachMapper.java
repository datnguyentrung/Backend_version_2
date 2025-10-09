package com.dat.backend_version_2.mapper.training;

import com.dat.backend_version_2.domain.training.Coach;
import com.dat.backend_version_2.dto.authentication.UserRes;
import com.dat.backend_version_2.dto.training.Coach.CoachReq;
import com.dat.backend_version_2.dto.training.Coach.CoachRes;

public class CoachMapper {
    public static void personalInfoToCoach(Coach coach, CoachReq.PersonalInfo personalInfo) {
        if (personalInfo == null) {
            return;
        }
        coach.setName(personalInfo.getName());
        coach.setBirthDate(personalInfo.getBirthDate());
    }

    public static void contactInfoToCoach(Coach coach, CoachReq.ContactInfo contactInfo) {
        if (contactInfo == null) {
            return;
        }
        coach.setPhone(contactInfo.getPhone());
    }

    public static void jobInfoToCoach(Coach coach, CoachReq.JobInfo jobInfo) {
        if (jobInfo == null) {
            return;
        }
        coach.setPosition(jobInfo.getPosition());
        coach.setBeltLevel(jobInfo.getBeltLevel());
        coach.setIsActive(jobInfo.getIsActive());
    }

    public static CoachRes.PersonalInfo coachToPersonalInfo(Coach coach) {
        if (coach == null) {
            return null;
        }
        CoachRes.PersonalInfo personalInfo = new CoachRes.PersonalInfo();
        personalInfo.setName(coach.getName());
        personalInfo.setBirthDate(coach.getBirthDate());
        personalInfo.setIsActive(coach.getIsActive());
        return personalInfo;
    }

    public static UserRes.UserInfo coachToUserInfo(Coach coach) {
        if (coach == null) {
            return null;
        }
        UserRes.UserInfo userInfo = new UserRes.UserInfo();
        userInfo.setIdUser(coach.getIdUser());
        userInfo.setEmail(coach.getEmail());
        userInfo.setIdRole(coach.getRole().getIdRole());
        userInfo.setIdAccount(coach.getIdAccount());
        return userInfo;
    }

    public static UserRes.UserProfile coachToUserProfile(Coach coach) {
        if (coach == null) {
            return null;
        }
        UserRes.UserProfile userProfile = new UserRes.UserProfile();
        userProfile.setBeltLevel(coach.getBeltLevel());
        userProfile.setBirthDate(coach.getBirthDate());
        userProfile.setPhone(coach.getPhone());
        userProfile.setName(coach.getName());
        userProfile.setIsActive(coach.getIsActive());
        return userProfile;
    }

    public static UserRes coachToUserRes(Coach coach) {
        if (coach == null) {
            return null;
        }
        UserRes userRes = new UserRes();
        userRes.setUserInfo(coachToUserInfo(coach));
        userRes.setUserProfile(coachToUserProfile(coach));
        return userRes;
    }
}