package com.notes.notes.contollers;

import com.notes.notes.models.Notes;
import com.notes.notes.pojos.requests.ReqCreateNote;
import com.notes.notes.services.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
public class NotesController {

    private final NotesService notesService;

    @Autowired
    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @PostMapping(value = "/notes")
    public ResponseEntity<Notes> createNote(@Valid @RequestBody ReqCreateNote reqCreateNote, @RequestHeader("transaction_id") String transactionId) {
        Notes notes = notesService.createNote(reqCreateNote, transactionId);
        return new ResponseEntity<>(notes, HttpStatus.CREATED);
    }

    @PutMapping(value = "/notes")
    public ResponseEntity<Notes> updateNote(@Valid @RequestBody ReqCreateNote reqCreateNote, @RequestHeader("transaction_id") String transactionId) {
        Notes notes = notesService.updateNote(reqCreateNote, transactionId);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping(value = "/notes")
    public ResponseEntity<List<Notes>> getAllUserNotes(Pageable pageable, @RequestHeader("transaction_id") String transactionId) {
        List<Notes> notesList = notesService.getAllUserNotes(pageable, transactionId);
        return new ResponseEntity<>(notesList, HttpStatus.OK);
    }

    @GetMapping(value = "/notes/{title}")
    public ResponseEntity<Notes> getUserNotes(@RequestHeader("transaction_id") String transactionId, @NotBlank(message = "title can not be blank") @PathVariable("title") String title) {
        Notes notes = notesService.getUserNotes(title, transactionId);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @DeleteMapping(value = "/notes/{title}")
    public ResponseEntity<String> deleteUserNotes(@RequestHeader("transaction_id") String transactionId, @NotBlank(message = "title can not be blank") @PathVariable("title") String title) {
        String message = notesService.deleteUserNotes(title, transactionId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping(value = "/notes")
    public ResponseEntity<String> deleteAllUserNotes(@RequestHeader("transaction_id") String transactionId) {
        String message = notesService.deleteAllUserNotes(transactionId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
