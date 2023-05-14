package com.sumit.r2dbc.db2.controller;


import com.sumit.r2dbc.db2.entities.Teacher;
import com.sumit.r2dbc.db2.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/teacher")
public class TeacherController {



    @Autowired
    private TeacherService teacherService;


    @GetMapping("/{id}")
    public Mono<ResponseEntity<Teacher>> getTeacher(@PathVariable long id) {
        return teacherService.findTeacherById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<Teacher> listTeacher(@RequestParam(name = "name", required = false) String name) {
        return teacherService.findStudentsByName(name);
    }

    @PostMapping("/add")
    public Mono<Teacher> addNewTeacher(@RequestBody Teacher teacher) {
        return teacherService.addNewTeacher(teacher);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Teacher>> updateTeacher(@PathVariable long id, @RequestBody Teacher teacher) {
        return teacherService.updateTeacher(id, teacher)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTeacher(@PathVariable long id) {
        return teacherService.findTeacherById(id)
                .flatMap(s ->
                        teacherService.deleteTeacher(s)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
