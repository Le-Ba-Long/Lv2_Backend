package com.globits.da.validate;

import com.globits.da.common.ErrorMessage;

public class ValidateProvince extends ValidateBase {

    public static String resultErrorMessageCode(String code) {
        String resultErrorMessage = "";
        if (checkCodeIsNull(code)) {
            resultErrorMessage += ErrorMessage.CODE_IS_NULL.getMessage();
        } else if (checkValidCodeContainSpace(code)) {
            resultErrorMessage += ErrorMessage.CHARACTER_CODE_INVALID.getMessage();
        } else if (checkValidLengthOfCode(code)) {
            resultErrorMessage += ErrorMessage.LENGTH_CODE_INVALID.getMessage();
        }
        return resultErrorMessage;
    }

    public static String resultErrorMessageName(String name) {
        String resultErrorMessage = "";
        if (checkNameIsNull(name)) {
            resultErrorMessage += ErrorMessage.NAME_IS_NULL.getMessage();

        }
        return resultErrorMessage;
    }

    public static int resultStatusCode(ErrorMessage resultErrorMessage) {
        switch (resultErrorMessage) {
            case ID_IS_EXIST:
                return ErrorMessage.ID_IS_EXIST.getCode();
            case CODE_IS_EXIST:
                return ErrorMessage.CODE_IS_EXIST.getCode();
            case CODE_IS_NULL:
                return ErrorMessage.CODE_IS_NULL.getCode();
            case CHARACTER_CODE_INVALID:
                return ErrorMessage.CHARACTER_CODE_INVALID.getCode();
            case LENGTH_CODE_INVALID:
                return ErrorMessage.LENGTH_CODE_INVALID.getCode();
            case NAME_IS_EXIST:
                return ErrorMessage.NAME_IS_EXIST.getCode();
            case NAME_IS_NULL:
                return ErrorMessage.NAME_IS_NULL.getCode();
        }
        return 0;
//        switch (resultErrorMessage) {
//            case "ID Already Exists":
//                return ErrorMessage.ID_IS_EXIST.getCode();
//            case "Code Already Exists":
//                return ErrorMessage.CODE_IS_EXIST.getCode();
//            case "Code Can Not Null":
//                return ErrorMessage.CODE_IS_NULL.getCode();
//            case "Code Must Not Contain Spaces":
//                return ErrorMessage.CHARACTER_CODE_INVALID.getCode();
//            case "The Code Must Be Between 6 And 10 Characters Long":
//                return ErrorMessage.LENGTH_CODE_INVALID.getCode();
//            case "Name Already Exists":
//                return ErrorMessage.NAME_IS_EXIST.getCode();
//            case "Name Cannot Be Blank":
//                return ErrorMessage.NAME_IS_NULL.getCode();
//        }
//        return 0;
    }

}
