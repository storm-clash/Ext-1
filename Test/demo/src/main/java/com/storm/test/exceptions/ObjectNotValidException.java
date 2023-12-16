package com.storm.test.exceptions;


import lombok.*;

import java.util.Set;
@Data
public class ObjectNotValidException extends RuntimeException{

    private final Set<String> errorMessages;



}
