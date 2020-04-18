package com.videolibrary.zipcode.fullstackapp.repositories;

import com.videolibrary.zipcode.fullstackapp.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    Video getVideoById(Long id);

    boolean deleteVideoById(Long id);

    List<Video> findAll();
}
