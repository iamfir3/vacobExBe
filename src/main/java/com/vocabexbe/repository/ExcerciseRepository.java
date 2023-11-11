package com.vocabexbe.repository;

import com.vocabexbe.entity.Excercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcerciseRepository extends JpaRepository<Excercise,Long> {
}
