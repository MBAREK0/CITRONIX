package org.mbarek0.web.citronix.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.mbarek0.web.citronix.domain.entities.HarvestDetail;
import org.mbarek0.web.citronix.domain.entities.Tree;
import org.mbarek0.web.citronix.domain.enums.Season;

import java.net.ContentHandler;
import java.util.List;
import java.util.UUID;

public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, UUID> {


    boolean existsByTreeAndHarvestSeason(Tree tree, Season season);

    @Query("SELECT CASE WHEN COUNT(hd) > 0 THEN TRUE ELSE FALSE END " +
            "FROM HarvestDetail hd " +
            "JOIN hd.harvest h " +
            "WHERE hd.tree = :tree AND h.season = :season AND YEAR(h.date) = :year")
    boolean existsByTreeAndSeasonAndYear(@Param("tree") Tree tree,
                                         @Param("season") Season season,
                                         @Param("year") int year);


    List<HarvestDetail> findAllByTreeId(UUID treeId);
}
