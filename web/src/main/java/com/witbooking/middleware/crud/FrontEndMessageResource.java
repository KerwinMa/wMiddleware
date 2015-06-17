package com.witbooking.middleware.crud;

import com.witbooking.middleware.crud.util.PaginationUtil;
import com.witbooking.middleware.db.repository.FrontEndMessageRepository;
import com.witbooking.middleware.db.router.BookingEngineContextHolder;
import com.witbooking.middleware.db.router.BookingEngineData;
import com.witbooking.middleware.model.FrontEndMessage;
import com.witbooking.middleware.model.trash.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;



/*
 REST controller for managing FrontEndMessage.
*/


@RestController
@RequestMapping("/apix/establishment/{hotelTicker}")
public class FrontEndMessageResource {

    private final Logger log = LoggerFactory.getLogger(FrontEndMessageResource.class);

    /*TODO: FIGURE OUT WHY INJECT IS NOT WORKING*/
    @Autowired
    private FrontEndMessageRepository frontEndMessageRepository;


    @RequestMapping(value = "/frontEndMessages",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody FrontEndMessage frontEndMessage) throws URISyntaxException {
        log.debug("REST request to save FrontEndMessage : {}", frontEndMessage);
        if (frontEndMessage.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new frontEndMessage cannot already have an ID").build();
        }
        com.witbooking.middleware.db.model.FrontEndMessage dbFrontEndMessage = new com.witbooking.middleware.db.model.FrontEndMessage(frontEndMessage);
        frontEndMessageRepository.save(dbFrontEndMessage);

        return ResponseEntity.created(new URI("/api/frontEndMessages/" + frontEndMessage.getId())).build();
    }

    @RequestMapping(value = "/frontEndMessages",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@Valid @RequestBody FrontEndMessage frontEndMessage) throws URISyntaxException {
        log.debug("REST request to update FrontEndMessage : {}", frontEndMessage);
        if (frontEndMessage.getId() == null) {
            return create(frontEndMessage);
        }
        com.witbooking.middleware.db.model.FrontEndMessage dbFrontEndMessage = new com.witbooking.middleware.db.model.FrontEndMessage(frontEndMessage);
        frontEndMessageRepository.save(dbFrontEndMessage);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/frontEndMessages",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FrontEndMessage>> getAll(@PathVariable String hotelTicker, @RequestParam(value = "page", required = false) Integer offset,
                                                        @RequestParam(value = "per_page", required = false) Integer limit)
            throws URISyntaxException {
        BookingEngineContextHolder.setBookingEngineData(new BookingEngineData(hotelTicker));
        Page<com.witbooking.middleware.db.model.FrontEndMessage> dbPage = frontEndMessageRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        List<FrontEndMessage> frontEndMessages = new ArrayList<>();
        for (com.witbooking.middleware.db.model.FrontEndMessage frontEndMessage : dbPage) {
            frontEndMessages.add(frontEndMessage.convertToBusinessObject());
        }
        Page<FrontEndMessage> page = new PageImpl<FrontEndMessage>(frontEndMessages);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/frontEndMessages", offset, limit);
        return new ResponseEntity<List<FrontEndMessage>>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/frontEndMessages/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FrontEndMessage> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get FrontEndMessage : {}", id);
        com.witbooking.middleware.db.model.FrontEndMessage dbFrontEndMessage = frontEndMessageRepository.findOne(id);
        if (dbFrontEndMessage == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dbFrontEndMessage.convertToBusinessObject(), HttpStatus.OK);
    }

    @RequestMapping(value = "/frontEndMessages/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete FrontEndMessage : {}", id);
        frontEndMessageRepository.delete(id);
    }
}
