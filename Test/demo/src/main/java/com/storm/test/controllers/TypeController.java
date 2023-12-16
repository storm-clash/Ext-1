package com.storm.test.controllers;

import com.storm.test.entities.Type;
import com.storm.test.services.TypeServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/type")
public class TypeController extends BaseControllerImpl<Type, TypeServiceImpl> {
}
