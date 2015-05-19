package com.witbooking.middleware.db.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
Spring Data JPA repository for the FrontEndMessage entity.
*/

@Repository
public interface FrontEndMessageRepository extends JpaRepository<FrontEndMessage,Long> {

}
