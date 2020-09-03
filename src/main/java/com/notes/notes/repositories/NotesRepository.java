package com.notes.notes.repositories;

import com.notes.notes.models.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {

    @Query(value = "select * from notes.notes where user_id = ?1 and title = ?2", nativeQuery = true)
    Notes findOneByUserIdAndTitle(Integer id, String title);

    @Query(value = "select * from notes.notes where user_id = ?1", nativeQuery = true)
    Page<Notes> findAllByUserId(Integer id, Pageable pageable);

    @Query(value = "select * from notes.notes where user_id = ?1", nativeQuery = true)
    List<Notes> findAllNotesByUserId(Integer id);
}
