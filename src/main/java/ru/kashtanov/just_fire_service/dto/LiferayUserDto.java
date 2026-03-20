package ru.kashtanov.just_fire_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author Viktor Кashtanov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiferayUserDto {
    private boolean agreedToTermsOfUse;
    private String comments;
    private String companyId;
    private String contactId;
    private long createDate;
    private String ctCollectionId;
    private boolean defaultUser;
    private String emailAddress;
    private boolean emailAddressVerified;
    private String externalReferenceCode;
    private String facebookId;
    private int failedLoginAttempts;
    private String firstName;
    private String fullName;
    private String googleUserId;
    private int graceLoginCount;
    private String greeting;
    private String jobTitle;
    private String languageId;
    private Long lastFailedLoginDate;
    private long lastLoginDate;
    private String lastLoginIP;
    private String lastName;
    private String ldapServerId;
    private boolean lockout;
    private Long lockoutDate;
    private long loginDate;
    private String loginIP;
    private String middleName;
    private long modifiedDate;
    private String mvccVersion;
    private String openId;
    private String portraitId;
    private String reminderQueryAnswer;
    private String reminderQueryQuestion;
    private String screenName;
    private int status;
    private String timeZoneId;
    private String userId;
    private String uuid;
}