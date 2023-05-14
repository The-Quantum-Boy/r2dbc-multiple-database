package com.sumit.r2dbc.db2.service;

import com.sumit.r2dbc.db2.entities.Teacher;
import com.sumit.r2dbc.db2.repositories.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TeacherService {

    @Autowired
    TeacherRepo teacherRepo;

    public Flux<Teacher> findStudentsByName(String name) {
        return (name != null) ? teacherRepo.findByName(name) : teacherRepo.findAll();
    }

    public Mono<Teacher> findTeacherById(long id) {
        return teacherRepo.findById(id);
    }

    public Mono<Teacher> addNewTeacher(Teacher teacher) {
        return teacherRepo.save(teacher);
    }

    public Mono<Teacher> updateTeacher(long id, Teacher teacher) {
        return teacherRepo.findById(id)
                .flatMap(s -> {
                    teacher.setId(s.getId());
                    return teacherRepo.save(teacher);
                });

    }

    public Mono<Void> deleteTeacher(Teacher teacher) {
        return teacherRepo.delete(teacher);
    }

}
