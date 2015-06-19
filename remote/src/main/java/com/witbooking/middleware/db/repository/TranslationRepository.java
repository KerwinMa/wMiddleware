package com.witbooking.middleware.db.repository;

import com.witbooking.middleware.db.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
Spring Data JPA repository for the FrontEndMessage entity.
*/

@Repository
public interface TranslationRepository extends JpaRepository<Translation,Long> {

}