package com.bezPalevaServer.db;


import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Query("delete from Mark m where m.irrelevanceLevel >= :irrelLevelMax or m.deathTime <= :currentTime")
    public void deleteOutdatedMarks(@Param("currentTime") Timestamp currentTime, @Param("irrelLevelMax") int irrelLevelMax);

    @Query("select m.photoPath from Mark m where m.photoPath is not null and (m.irrelevanceLevel >= :irrelLevelMax or m.deathTime <= :currentTime)")
    public List<String> getPhotosPaths(@Param("currentTime") Timestamp currentTime, @Param("irrelLevelMax") int irrelLevelMax);
}
