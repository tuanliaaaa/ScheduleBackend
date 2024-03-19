package com.g11.schedule.exception.base;


import static com.g11.schedule.constants.PassbookManagerConstants.StatusException.*;

public class BadRequestException extends BaseException {
  public BadRequestException() {
    setCode("com.ncsgroup.profiling.exception.base.BadRequestException");
    setStatus(BAD_REQUEST);
  }
}
