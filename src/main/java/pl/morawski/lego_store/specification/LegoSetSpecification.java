package pl.morawski.lego_store.specification;

import org.springframework.data.jpa.domain.Specification;
import pl.morawski.lego_store.domain.LegoSet;
import pl.morawski.lego_store.dto.LegoSetFilter;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class LegoSetSpecification {

    public static Specification<LegoSet> withFilters(LegoSetFilter filter) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.series() != null) {
                predicates.add(cb.equal(root.get("series"), filter.series()));
            }

            if (filter.condition() != null) {
                predicates.add(cb.equal(root.get("condition"), filter.condition()));
            }

            if (filter.minPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("basePrice"), filter.minPrice()));
            }

            if (filter.maxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("basePrice"), filter.maxPrice()));
            }

            if (filter.discontinued() != null) {
                predicates.add(cb.equal(root.get("discontinued"), filter.discontinued()));
            }

            if (filter.availabilityType() != null) {
                predicates.add(cb.equal(root.get("availabilityType"), filter.availabilityType()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
