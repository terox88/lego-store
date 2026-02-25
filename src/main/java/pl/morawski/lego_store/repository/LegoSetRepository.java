package pl.morawski.lego_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.morawski.lego_store.domain.LegoSet;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface LegoSetRepository extends
        JpaRepository<LegoSet, Long>,
        JpaSpecificationExecutor<LegoSet> {

    Optional<LegoSet> findBySetNumber(String setNumber);

    boolean existsBySetNumber(String setNumber);
}
