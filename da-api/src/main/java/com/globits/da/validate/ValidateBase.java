package com.globits.da.validate;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.Constants;

import java.util.regex.Matcher;

public abstract class ValidateBase extends BaseObjectDto {


    public static Boolean checkIdIsNull(Integer id) {
        return id == null;
    }

    public static Boolean checkCodeIsNull(String code) {
        return code == null || code.isEmpty();
    }

    public static Boolean checkValidLengthOfCode(String code) {
        return (code.length() < Constants.MIN_LENGTH_CODE || code.length() > Constants.MAX_LENGTH_CODE);
    }

    public static Boolean checkValidCodeContainSpace(String code) {
        return code.contains(Constants.SPACE);
    }

    public static Boolean checkNameIsNull(String name) {
        return name == null || name.isEmpty();

    }

    public static Boolean checkEmailIsNull(String email) {
        return email == null;
    }

    public static Boolean checkValidEmail(String email) {
        Matcher matcher = Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static Boolean checkPhoneIsNull(String phone) {
        return phone == null;
    }

    public static Boolean checkValidLengthOfPhone(String phone) {
        return (checkValidPhoneIsNumber(phone)) && (phone.length() < Constants.MAX_LENGTH_PHONE);
    }

    public static Boolean checkValidPhoneIsNumber(String phone) {
        return phone.matches(Constants.REGEX_VALID_PHONE);
    }

    public static Boolean checkDistrictIsNull(String district) {
        return district == null;
    }

    public static Boolean checkCommuneIsNull(String commune) {
        return commune == null;
    }
}
