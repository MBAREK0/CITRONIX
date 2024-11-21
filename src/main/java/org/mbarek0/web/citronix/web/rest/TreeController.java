package org.mbarek0.web.citronix.web.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.mbarek0.web.citronix.DTO.TreeDetailsDTO;
import org.mbarek0.web.citronix.domain.entities.Tree;
import org.mbarek0.web.citronix.service.TreeService;
import org.mbarek0.web.citronix.web.VM.Tree.TreeCreationVm;
import org.mbarek0.web.citronix.web.VM.Tree.TreeResponseVM;
import org.mbarek0.web.citronix.web.VM.mapper.TreeVMMapper;

import java.util.UUID;

@RestController
@RequestMapping("v1/api/trees")
@AllArgsConstructor
public class TreeController {
    private final TreeService treeService;
    private final TreeVMMapper treeVMMapper;

    @PostMapping("/save")
    public ResponseEntity<TreeResponseVM> saveTree(@RequestBody @Valid TreeCreationVm treeCreationVm) {
        Tree tree = treeVMMapper.toTree(treeCreationVm);
        Tree savedTree = treeService.saveTree(tree);
        TreeResponseVM treeResponseVM = treeVMMapper.toResponseVM(savedTree);
        return ResponseEntity.status(HttpStatus.CREATED).body(treeResponseVM);
    }

    @GetMapping
    public ResponseEntity<Page<Tree>> getTreesWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Tree> treePage = treeService.getAllTreesPaginated(page,size);
        return ResponseEntity.ok(treePage);
    }

    @GetMapping("/{fieldId}")
    public ResponseEntity<Page<Tree>> getTreesByFieldId(@PathVariable UUID fieldId,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Page<Tree> treePage = treeService.getAllTreesByFieldId(fieldId,page,size);
        return ResponseEntity.ok(treePage);
    }

    @DeleteMapping("/delete/{treeId}")
    public ResponseEntity<String> deleteTree(@PathVariable UUID treeId) {
        treeService.delete(treeId);
        return ResponseEntity.ok("Tree deleted successfully");
    }

    @GetMapping("/{treeId}/details")
    public ResponseEntity<TreeDetailsDTO> getTreeDetails(@PathVariable UUID treeId) {
        TreeDetailsDTO treeDetails = treeService.getTreeDetails(treeId);
        return ResponseEntity.ok(treeDetails);
    }
}
