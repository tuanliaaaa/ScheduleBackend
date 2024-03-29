package com.g11.schedule.exception.base;

import static com.g11.schedule.constants.PassbookManagerConstants.StatusException.BAD_REQUEST;

public class AccessDeniedException extends BaseException{
    public AccessDeniedException() {
        setCode("com.ncsgroup.profiling.exception.base.AccessDeniedException");
        setStatus(403);
    }
}
