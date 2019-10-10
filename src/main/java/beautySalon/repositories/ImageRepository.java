package beautySalon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import beautySalon.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
