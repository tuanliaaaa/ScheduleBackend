package com.g11.schedule.exception.base;


import static com.g11.schedule.constants.PassbookManagerConstants.StatusException.*;

public class ConflictException extends BaseException {
  public ConflictException(String id, String objectName) {
    setStatus(CONFLICT);
    setCode("com.ncsgroup.profiling.exception.base.ConflictException");
    addParam("id", id);
    addParam("objectName", objectName);
  }

  public ConflictException(){
    setStatus(CONFLICT);
    setCode("com.ncsgroup.profiling.exception.base.ConflictException");
  }
}
