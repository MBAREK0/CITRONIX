package org.mbarek0.web.citronix.web.VM.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mbarek0.web.citronix.domain.entities.Tree;
import org.mbarek0.web.citronix.web.VM.Tree.TreeCreationVm;
import org.mbarek0.web.citronix.web.VM.Tree.TreeResponseVM;

@Mapper(componentModel = "spring")
public interface TreeVMMapper {
    @Mapping(target = "field.id", source = "fieldId")
    Tree toTree(TreeCreationVm treeCreationVm);

    @Mapping(target = "age", expression = "java(tree.getAge())")
    @Mapping(target = "productivity", expression = "java(tree.getProductivity())")
    TreeResponseVM toResponseVM(Tree tree);
}
