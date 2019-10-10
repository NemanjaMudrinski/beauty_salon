package beautySalon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import beautySalon.models.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>{

}
