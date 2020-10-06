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

import static com.notes.notes.constants.ExceptionConstants.*;
import static com.notes.notes.constants.ResponseConstants.NOTES_DELETED;
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
        log.info("transactionId: {}", transactionId);
        UserCredentials userCredentials = checkIfNull(userService.getUserByTransactionId(transactionId), NOT_LOGGED_IN_TO_CREATE);
        checkIfNotNull(notesRepository.findOneByUserIdAndTitle(userCredentials.getId(), reqCreateNote.getTitle()), NOT_LOGGED_IN_TO_CREATE + reqCreateNote.getTitle());
        Notes notes = Notes.builder()
                .userId(userCredentials.getId())
                .title(reqCreateNote.getTitle())
                .description(reqCreateNote.getDescription())
                .build();
        return notesRepository.save(notes);
    }

    public Notes updateNote(ReqCreateNote reqCreateNote, String transactionId) {
        if (StringUtils.isEmpty(reqCreateNote.getDescription())) {
            log.error(DESCRIPTION_EMPTY);
            throw new RuntimeException(DESCRIPTION_EMPTY);
        }
        UserCredentials userCredentials = checkIfNull(userService.getUserByTransactionId(transactionId), NOT_LOGGED_IN_TO_EDIT);
        Notes notes = checkIfNull(notesRepository.findOneByUserIdAndTitle(userCredentials.getId(), reqCreateNote.getTitle()), NOTES_NOT_EXIST_WITH_TITLE + reqCreateNote.getTitle());
        notes.setDescription(reqCreateNote.getDescription());
        return notesRepository.save(notes);
    }

    public List<Notes> getAllUserNotes(Pageable page, String transactionId) {
        UserCredentials userCredentials = checkIfNull(userService.getUserByTransactionId(transactionId), NOT_LOGGED_IN_TO_VIEW);
        int size = Math.min(page.getPageSize(), 10);
        Pageable pageable = PageRequest.of(page.getPageNumber(), size);
        Page<Notes> notesPage = notesRepository.findAllByUserId(userCredentials.getId(), pageable);
        checkIfListNull(notesPage.getContent(), NOTES_NOT_EXISTS);
        return notesPage.getContent();
    }

    public Notes getUserNotes(String title, String transactionId) {
        UserCredentials userCredentials = checkIfNull(userService.getUserByTransactionId(transactionId), NOT_LOGGED_IN_TO_VIEW);
        return checkIfNull(notesRepository.findOneByUserIdAndTitle(userCredentials.getId(), title), NOTES_NOT_EXIST_WITH_TITLE + title);
    }

    public String deleteUserNotes(String title, String transactionId) {
        UserCredentials userCredentials = checkIfNull(userService.getUserByTransactionId(transactionId), NOT_LOGGED_IN_TO_VIEW);
        Notes notes = checkIfNull(notesRepository.findOneByUserIdAndTitle(userCredentials.getId(), title), NOTES_NOT_EXIST_WITH_TITLE + title);
        notesRepository.delete(notes);
        return NOTES_DELETED;
    }

    public String deleteAllUserNotes(String transactionId) {
        UserCredentials userCredentials = checkIfNull(userService.getUserByTransactionId(transactionId), NOT_LOGGED_IN_TO_VIEW);
        List<Notes> notesList = notesRepository.findAllNotesByUserId(userCredentials.getId());
        notesRepository.deleteAll(notesList);
        return NOTES_DELETED;
    }
}
