package com.study.basic.api;

import com.study.basic.dto.CoffeeDto;
import com.study.basic.entity.Coffee;
import com.study.basic.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoffeeApiController {

    @Autowired
    private CoffeeRepository coffeeRepository;

    // GET =============================================================================================================
    @GetMapping("/api/coffees")
    public List<Coffee> index() {
        return coffeeRepository.findAll();
    }

    @GetMapping("/api/coffees/{id}")
    public Coffee show(@PathVariable Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    // POST ============================================================================================================
    @PostMapping("/api/coffees")
    public Coffee create(@RequestBody CoffeeDto coffeeDto){
        Coffee coffee = coffeeDto.toEntity();
        return coffeeRepository.save(coffee);
    }

    // PATCH ===========================================================================================================
    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> update(@PathVariable Long id,@RequestBody CoffeeDto coffeeDto) {
        Coffee coffee = coffeeDto.toEntity();
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target == null || coffee.getId() != id) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        target.patch(coffee);
        Coffee updated = coffeeRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // DELETE ==========================================================================================================
    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable long id) {
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target == null){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        coffeeRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
