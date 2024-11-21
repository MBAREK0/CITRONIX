package org.mbarek0.web.citronix.service.Implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.mbarek0.web.citronix.domain.entities.Field;
import org.mbarek0.web.citronix.domain.entities.Harvest;
import org.mbarek0.web.citronix.domain.entities.HarvestDetail;
import org.mbarek0.web.citronix.domain.entities.Tree;
import org.mbarek0.web.citronix.domain.enums.Season;
import org.mbarek0.web.citronix.repository.HarvestDetailRepository;
import org.mbarek0.web.citronix.repository.HarvestRepository;
import org.mbarek0.web.citronix.service.FieldService;
import org.mbarek0.web.citronix.service.HarvestService;
import org.mbarek0.web.citronix.util.SeasonUtils;
import org.mbarek0.web.citronix.exception.Harvest.HarvestNotFoundException;
import org.mbarek0.web.citronix.exception.InvalidCredentialsException;
import org.mbarek0.web.citronix.exception.Tree.TreeNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;
    private final FieldService fieldService;
    private final HarvestDetailRepository harvestDetailRepository;

    @Transactional
    @Override
    public Harvest createHarvest(UUID fieldId, Harvest harvest) {

        Field fieldToHarvest = fieldService.getFieldById(fieldId);
        Season harvestSeason = SeasonUtils.getSeasonFromDate(harvest.getDate());
        int harvestYear = harvest.getDate().getYear();


        List<Tree> trees = fieldToHarvest.getTrees().stream()
                .filter(tree -> !harvestDetailRepository.existsByTreeAndSeasonAndYear(tree, harvestSeason,harvestYear))
                .toList();

        if (trees.isEmpty()){
            throw new TreeNotFoundException("All trees has been harvested this season.");
        }

        double totalQuantity = trees.stream()
                .mapToDouble(Tree::getProductivity)
                .sum();

        Harvest harvestToSave = Harvest.builder()
                .date(harvest.getDate())
                .season(harvestSeason)
                .totalQuantity(totalQuantity)
                .build();

        Harvest savedHarvest = harvestRepository.save(harvestToSave);

        List<HarvestDetail> harvestDetails = trees.stream()
                .map(tree -> HarvestDetail.builder()
                        .harvest(savedHarvest)
                        .tree(tree)
                        .quantity(tree.getProductivity())
                        .build())
                .toList();
        harvestDetailRepository.saveAll(harvestDetails);
        return savedHarvest;
    }

    @Override
    public Harvest findById(UUID id) {
        if (id == null){
            throw new InvalidCredentialsException("harvest ID is Required");
        }
        return harvestRepository.findById(id)
                .orElseThrow(()-> new HarvestNotFoundException("harvest Not Found"));
    }

    @Override
    public void delete(UUID id) {
        Harvest harvestToDelete = findById(id);
        harvestRepository.delete(harvestToDelete);
    }

    @Override
    public List<Harvest> getHarvestsBySeason(Season season) {
        return harvestRepository.findBySeason(season);
    }

    @Transactional
    @Override
    public void deleteAllHarvestDetailsByTreeId(UUID treeId) {
        if (treeId == null){
            throw new InvalidCredentialsException("tree Id is required");
        }
        List<HarvestDetail> harvestDetails = harvestDetailRepository.findAllByTreeId(treeId);
        harvestDetails.forEach(harvestDetailRepository::delete);
    }


}
