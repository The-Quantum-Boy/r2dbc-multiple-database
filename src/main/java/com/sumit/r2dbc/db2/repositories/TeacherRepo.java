package com.sumit.r2dbc.db2.repositories;

import com.sumit.r2dbc.db2.entities.Teacher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TeacherRepo extends ReactiveCrudRepository<Teacher,Long> {

    public Flux<Teacher> findByName(String name);
}
