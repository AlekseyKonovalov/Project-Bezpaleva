package com.bezPalevaServer.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MarkRepository extends CrudRepository<Mark, Long> {


}
