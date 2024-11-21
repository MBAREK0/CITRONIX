package org.mbarek0.web.citronix.service;

import org.springframework.data.domain.Page;
import org.mbarek0.web.citronix.DTO.TreeDetailsDTO;
import org.mbarek0.web.citronix.domain.entities.Tree;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface TreeService {
    Tree saveTree(Tree tree);
    Page<Tree> getAllTreesPaginated(int page, int size);
    Page<Tree> getAllTreesByFieldId(UUID fieldId,int page, int size);
    void delete(UUID id);
    Tree findById(UUID id);
    TreeDetailsDTO getTreeDetails(UUID id);

    @Transactional
    void deleteAllTreesByFieldId(UUID fieldId);
}
