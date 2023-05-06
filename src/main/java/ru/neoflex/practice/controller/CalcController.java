package ru.neoflex.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CalcController {
    @Autowired
    private Repos repository;
    @GetMapping("/plus/{a}/{b}")
    public int Sum(@PathVariable("a") int a,@PathVariable("b") int b){
        int result = a + b;
        Calculation calculation = new Calculation(a, "+", b, result);
        repository.save(calculation);
        return  result;
    }
    @GetMapping("/minus/{a}/{b}")
    public int Dif(@PathVariable("a") int a,@PathVariable("b") int b){
        int result = a - b;
        Calculation calculation = new Calculation(a, "-", b, result);
        repository.save(calculation);
        return  result;
    }
    @GetMapping("/calculations")
    public List <Calculation> getAllCalculations() {
        return repository.findAll();
    }
    @GetMapping("/calculations/{id}")
    public ResponseEntity< Calculation > getCalculationById(@PathVariable(value = "id") Long calculationId)
            throws ResourceNotFoundException {
        Calculation calculation = repository.findById(calculationId)
                .orElseThrow(() -> new ResourceNotFoundException("Calculation not found for this id :: " + calculationId));
        return ResponseEntity.ok().body(calculation);
    }
    @PutMapping("/calculations/{id}")
    public ResponseEntity < Calculation > updateCalculation(@PathVariable(value = "id") Long calculationId,
                                                      @Valid @RequestBody Calculation calculationDetails) throws ResourceNotFoundException {
        Calculation calculation = repository.findById(calculationId)
                .orElseThrow(() -> new ResourceNotFoundException("Calculation not found for this id :: " + calculationId));

        calculation.setOperation(calculationDetails.getOperation());
        calculation.setA(calculationDetails.getA());
        calculation.setB(calculationDetails.getB());
        calculation.setResult(calculationDetails.getResult());
        final Calculation updatedCalculation = repository.save(calculation);
        return ResponseEntity.ok(updatedCalculation);
    }
    @DeleteMapping("/calculations/{id}")
    public Map < String, Boolean > deleteCalculation(@PathVariable(value = "id") Long calculationId)
            throws ResourceNotFoundException {
        Calculation calculation = repository.findById(calculationId)
                .orElseThrow(() -> new ResourceNotFoundException("Calculation not found for this id :: " + calculationId));
        repository.delete(calculation);
        Map< String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
