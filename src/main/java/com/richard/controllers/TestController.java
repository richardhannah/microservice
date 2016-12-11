package com.richard.controllers;

import com.richard.domain.AuthLink;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static com.richard.helpers.LinkHelper.linkDirectlyTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * Created by highl on 04/07/2016.
 */

@RestController
public class TestController {


    @RequestMapping("/greeting")
    public String index(){
        return "Greetings from Spring boot!!!!";
    }

    @RequestMapping(value = "/postauth",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> postAuth() throws URISyntaxException{
        final UUID authenticationId = UUID.randomUUID();
        final HttpHeaders responseHeaders = new HttpHeaders();

        //responseHeaders.setLocation(linkDirectlyTo(methodOn(TestController.class, authenticationId).getAuth(authenticationId)).toUri());
        responseHeaders.setLocation(new URI("http://localhost:8080/getauth/"+authenticationId));

        return new ResponseEntity<Void>(responseHeaders, CREATED);
    }

    @RequestMapping(value = "/getauth/{uuid}",method = RequestMethod.GET)
    @ResponseBody
    public  ResponseEntity<AuthLink> getAuth(@PathVariable UUID uuid){

        AuthLink authLink = new AuthLink();


        return new ResponseEntity<AuthLink>(authLink, HttpStatus.OK);
    }


}

