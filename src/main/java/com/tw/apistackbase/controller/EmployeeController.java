package com.tw.apistackbase.controller;

import com.tw.apistackbase.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private static Map<Integer, Employee> result = new HashMap<>();

    static {
        result.put(1, new Employee(1, "John", 45, "Male"));
    }

    @GetMapping()
    public ResponseEntity getAll() {
//        List<Employee> employees = Arrays.asList(new Employee[]{new Employee(1, "John", 45, "Male")});

        return ResponseEntity.ok().body(result);
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody Employee employee) {
        result.put(employee.getId(), employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Integer id) {
        Employee remove = result.remove(id);
        return ResponseEntity.ok().body(String.format("you delete %1$s OK", remove.toString()));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody Employee employee){
        Employee employee1 = result.get(id);
        employee1.setAge(employee.getAge());
        employee1.setGender(employee.getGender());
        employee1.setName(employee.getName());
        return ResponseEntity.ok().build();
    }
}
