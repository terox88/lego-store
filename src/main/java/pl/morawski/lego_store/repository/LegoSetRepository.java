package pl.morawski.lego_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.morawski.lego_store.domain.LegoSet;

import java.util.Optional;

public interface LegoSetRepository extends JpaRepository<LegoSet, Long> {

    Optional<LegoSet> findBySetNumber(String setNumber);

    boolean existsBySetNumber(String setNumber);


}
