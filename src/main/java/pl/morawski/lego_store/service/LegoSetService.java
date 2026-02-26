package pl.morawski.lego_store.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.morawski.lego_store.domain.LegoSet;
import pl.morawski.lego_store.dto.LegoSetCreateRequest;
import pl.morawski.lego_store.dto.LegoSetFilter;
import pl.morawski.lego_store.dto.LegoSetResponse;
import pl.morawski.lego_store.dto.LegoSetUpdateRequest;
import pl.morawski.lego_store.exception.DuplicateSetNumberException;
import pl.morawski.lego_store.exception.ResourceNotFoundException;
import pl.morawski.lego_store.mapper.LegoSetMapper;
import pl.morawski.lego_store.repository.LegoSetRepository;
import pl.morawski.lego_store.specification.LegoSetSpecification;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LegoSetService {

    private final LegoSetRepository repository;
    private final LegoSetMapper mapper;

    public LegoSetResponse create(LegoSetCreateRequest request) {

        if (repository.existsBySetNumber(request.setNumber())) {
            throw new DuplicateSetNumberException(
                    "Lego set with number " + request.setNumber() + " already exists"
            );
        }

        LegoSet legoSet = mapper.toEntity(request);

        LegoSet saved = repository.save(legoSet);

        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public LegoSetResponse getById(Long id) {
        LegoSet legoSet = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lego set not found with id: " + id)
                );

        return mapper.toResponse(legoSet);
    }

    @Transactional(readOnly = true)
    public List<LegoSetResponse> getAll(LegoSetFilter filter, Sort sort) {

        Specification<LegoSet> specification =
                LegoSetSpecification.withFilters(filter);

        return repository.findAll(specification, sort)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public LegoSetResponse update(Long id, LegoSetUpdateRequest request) {

        LegoSet legoSet = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lego set not found with id: " + id)
                );

        legoSet.changeName(request.name());
        legoSet.changePrice(request.basePrice());
        legoSet.changeCondition(request.condition());

        if (request.discontinued()) {
            legoSet.markAsDiscontinued();
        }

        return mapper.toResponse(legoSet);
    }

    public void delete(Long id) {

        LegoSet legoSet = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Lego set not found with id: " + id)
                );

        repository.delete(legoSet);
    }
}
