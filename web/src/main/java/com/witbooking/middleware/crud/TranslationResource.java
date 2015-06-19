package com.witbooking.middleware.crud;

import com.witbooking.middleware.crud.util.PaginationUtil;
import com.witbooking.middleware.db.model.Translation;
import com.witbooking.middleware.db.repository.TranslationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing Translation.
 */
@RestController
@RequestMapping("/apix/establishment/{hotelTicker}")
public class TranslationResource {

    private final Logger log = LoggerFactory.getLogger(TranslationResource.class);

    @Autowired
    private TranslationRepository translationRepository;

    /**
     * POST  /translations -> Create a new translation.
     */
    @RequestMapping(value = "/translations",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Translation translation) throws URISyntaxException {
        log.debug("REST request to save Translation : {}", translation);
        if (translation.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new translation cannot already have an ID").build();
        }
        translationRepository.save(translation);
        return ResponseEntity.created(new URI("/api/translations/" + translation.getId())).build();
    }

    /**
     * PUT  /translations -> Updates an existing translation.
     */
    @RequestMapping(value = "/translations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Translation translation) throws URISyntaxException {
        log.debug("REST request to update Translation : {}", translation);
        if (translation.getId() == null) {
            return create(translation);
        }
        translationRepository.save(translation);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /translations -> get all the translations.
     */
    @RequestMapping(value = "/translations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
    public ResponseEntity<List<Translation>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Translation> page = translationRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/translations", offset, limit);
        return new ResponseEntity<List<Translation>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /translations/:id -> get the "id" translation.
     */
    @RequestMapping(value = "/translations/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
    public ResponseEntity<Translation> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Translation : {}", id);
        Translation translation = translationRepository.findOne(id);
        if (translation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(translation, HttpStatus.OK);
    }

    /**
     * DELETE  /translations/:id -> delete the "id" translation.
     */
    @RequestMapping(value = "/translations/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Translation : {}", id);
        translationRepository.delete(id);
    }
}
