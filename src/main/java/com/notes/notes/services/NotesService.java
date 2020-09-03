package com.notes.notes.services;

import com.notes.notes.models.Notes;
import com.notes.notes.models.UserCredentials;
import com.notes.notes.pojos.requests.ReqCreateNote;
import com.notes.notes.repositories.NotesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.notes.notes.utils.NotesUtils.*;

@Service
@Slf4j
public class NotesService {

    private final NotesRepository notesRepository;
    private final UserService userService;

    @Autowired
    public NotesService(NotesRepository notesRepository, UserService userService) {
        this.notesRepository = notesRepository;
        this.userService = userService;
    }

    public Notes createNote(ReqCreateNote reqCreateNote, String transactionId) {
        UserCredentials userCredentials = checkIfNull(userService.getUserByTransactionId(transactionId), "please login to create the notes");
        checkIfNotNull(notesRepository.findOneByUserIdAndTitle(userCredentials.getId(), reqCreateNote.getTitle()), "Notes already exist with title: " + reqCreateNote.getTitle());
        Notes notes = Notes.builder()
                .userId(userCredentials.getId())
                .title(reqCreateNote.getTitle())
                .description(reqCreateNote.getDescription())
                .build();
        return notesRepository.save(notes);
    }

    public Notes updateNote(ReqCreateNote reqCreateNote, String transactionId) {
        if (StringUtils.isEmpty(reqCreateNote.getDescription())) {
            log.error("Description can not be empty");
            throw new RuntimeException("Description can not be empty");
        }
        UserCredentials userCredentials = checkIfNull(userService.getUserByTransactionId(transactionId), "please login to edit the notes");
        Notes notes = checkIfNull(notesRepository.findOneByUserIdAndTitle(userCredentials.getId(), reqCreateNote.getTitle()), "Notes does not exist with title: " + reqCreateNote.getTitle());
        notes.setDescription(reqCreateNote.getDescription());
        return notesRepository.save(notes);
    }

    public List<Notes> getAllUserNotes(Pageable page, String transactionId) {
        UserCredentials userCredentials = checkIfNull(userService.getUserByTransactionId(transactionId), "please login to view the notes");
        int size = Math.min(page.getPageSize(), 10);
        Pageable pageable = PageRequest.of(page.getPageNumber(), size);
        Page<Notes> notesPage = notesRepository.findAllByUserId(userCredentials.getId(), pageable);
        checkIfListNull(notesPage.getContent(), "No notes exist ");
        return notesPage.getContent();
    }

    public Notes getUserNotes(String title, String transactionId) {
        UserCredentials userCredentials = checkIfNull(userService.getUserByTransactionId(transactionId), "please login to view the notes");
        return checkIfNull(notesRepository.findOneByUserIdAndTitle(userCredentials.getId(), title), "Notes does not exist with title: " + title);
    }

    public String deleteUserNotes(String title, String transactionId) {
        UserCredentials userCredentials = checkIfNull(userService.getUserByTransactionId(transactionId), "please login to view the notes");
        Notes notes = checkIfNull(notesRepository.findOneByUserIdAndTitle(userCredentials.getId(), title), "Notes does not exist with title: " + title);
        notesRepository.delete(notes);
        return "Notes deleted successfully";
    }

    public String deleteAllUserNotes(String transactionId) {
        UserCredentials userCredentials = checkIfNull(userService.getUserByTransactionId(transactionId), "please login to view the notes");
        List<Notes> notesList = notesRepository.findAllNotesByUserId(userCredentials.getId());
        notesRepository.deleteAll(notesList);
        return "Notes deleted successfully";
    }
}
