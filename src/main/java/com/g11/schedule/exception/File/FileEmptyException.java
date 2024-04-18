package com.g11.schedule.exception.File;

import com.g11.schedule.exception.base.NotFoundException;

public class FileEmptyException extends NotFoundException {
    public FileEmptyException() {
        setMessage("File is empty");
    }
}
