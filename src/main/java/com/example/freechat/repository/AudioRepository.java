package com.example.freechat.repository;

import com.example.freechat.model.Audio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AudioRepository extends JpaRepository<Audio, Long> {

    Optional<Audio> findById(Long id);
}
