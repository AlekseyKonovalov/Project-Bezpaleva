package com.bezPalevaServer.db;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.security.PermitAll;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface MarkRepository extends CrudRepository<Mark, Long> {
    @Query("select m from Mark m where m.x <= :x+:rad and m.x >= :x-:rad and m.y <= :y+:rad and m.y >=:y-:rad")
    public List<Mark> getAllMarksFromDB(@Param("x") double x, @Param("y") double y, @Param("rad") int rad);

    @Query("select m from Mark m where m.irrelevance_level >= :irrelLevelMax or m.death_time <= :currentTime")
    public List<Mark> getMarksForDeletion(@Param("currentTime") Timestamp currentTime, @Param("irrelLevelMax") int irrelLevelMax);
}
