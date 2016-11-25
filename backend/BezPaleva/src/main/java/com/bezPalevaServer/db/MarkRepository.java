package com.bezPalevaServer.db;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends CrudRepository<Mark, Long> {
    @Query("select m from Mark m where m.x <= :x+:rad and m.x >= :x-:rad and m.y <= :y+:rad and m.y >=:y-:rad")
    public List<Mark> getAllMarksFromDB(@Param("x") double x, @Param("y") double y, @Param("rad") int rad);
}
