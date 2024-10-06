package spring.framework.beerworks.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import spring.framework.beerworks.Exceptions.NotFoundException;

//@ControllerAdvice
public class ExceptionController {


   // @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(){
        return ResponseEntity.notFound().build();
    }
}
