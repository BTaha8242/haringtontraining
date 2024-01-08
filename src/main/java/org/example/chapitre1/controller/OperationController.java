package org.example.chapitre1.controller;


import lombok.RequiredArgsConstructor;
import org.example.chapitre1.dto.OperationDto;
import org.example.chapitre1.exception.AccountNotFoundException;
import org.example.chapitre1.exception.OperationNotFoundException;
import org.example.chapitre1.exception.UnsupportedOperationTypeException;
import org.example.chapitre1.service.OperationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/operations")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<OperationDto> createOperation(@RequestBody OperationDto operationDto) throws AccountNotFoundException, UnsupportedOperationTypeException {
        OperationDto operationDtoSaved = operationService.save(operationDto);
        HttpStatus status = (operationDtoSaved != null) ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
        return new ResponseEntity<>(operationDtoSaved, status);

    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OperationDto>> findAll() {
        List<OperationDto> operationDtos = operationService.findAll();
        HttpStatus status = (operationDtos != null) ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
        return new ResponseEntity<>(operationDtos, status);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OperationDto> findById(@PathVariable Long id) throws OperationNotFoundException {
        OperationDto operationDto = operationService.findById(id);
        HttpStatus status = (operationDto != null) ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
        return new ResponseEntity<>(operationDto, status);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteById(@PathVariable Long id) throws OperationNotFoundException {
        operationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
