package com.bezPalevaServer.db;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select u from User u where vk_id = :vkID")
    public User getUserByVkID(@Param("vkID") String vkID);

    @Transactional
    @Modifying
    @Query("update User set number_marks_per_day = 0")
    public void resetNumberMarks ();
}
