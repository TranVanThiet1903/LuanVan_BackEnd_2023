package warehouse.management.app.service.mapper;

import org.mapstruct.*;
import warehouse.management.app.domain.ChiTietKho;
import warehouse.management.app.domain.NguyenLieu;
import warehouse.management.app.domain.NhaKho;
import warehouse.management.app.service.dto.ChiTietKhoDTO;
import warehouse.management.app.service.dto.NguyenLieuDTO;
import warehouse.management.app.service.dto.NhaKhoDTO;

/**
 * Mapper for the entity {@link ChiTietKho} and its DTO {@link ChiTietKhoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChiTietKhoMapper extends EntityMapper<ChiTietKhoDTO, ChiTietKho> {
    //    @Mapping(target = "nhaKho", source = "nhaKho", qualifiedByName = "nhaKhoId")
    //    @Mapping(target = "nguyenLieu", source = "nguyenLieu", qualifiedByName = "nguyenLieuId")
    //    ChiTietKhoDTO toDto(ChiTietKho s);
    //
    //    @Named("nhaKhoId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    NhaKhoDTO toDtoNhaKhoId(NhaKho nhaKho);
    //
    //    @Named("nguyenLieuId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    NguyenLieuDTO toDtoNguyenLieuId(NguyenLieu nguyenLieu);
}
