package warehouse.management.app.service.mapper;

import org.mapstruct.*;
import warehouse.management.app.domain.NguyenLieu;
import warehouse.management.app.domain.NhaCungCap;
import warehouse.management.app.domain.NhomNguyenLieu;
import warehouse.management.app.service.dto.NguyenLieuDTO;
import warehouse.management.app.service.dto.NhaCungCapDTO;
import warehouse.management.app.service.dto.NhomNguyenLieuDTO;

/**
 * Mapper for the entity {@link NguyenLieu} and its DTO {@link NguyenLieuDTO}.
 */
@Mapper(componentModel = "spring")
public interface NguyenLieuMapper extends EntityMapper<NguyenLieuDTO, NguyenLieu> {
    //    @Mapping(target = "nhomNguyenLieu", source = "nhomNguyenLieu", qualifiedByName = "nhomNguyenLieuId")
    //    @Mapping(target = "nhaCungCap", source = "nhaCungCap", qualifiedByName = "nhaCungCapId")
    //    NguyenLieuDTO toDto(NguyenLieu s);
    //
    //    @Named("nhomNguyenLieuId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    NhomNguyenLieuDTO toDtoNhomNguyenLieuId(NhomNguyenLieu nhomNguyenLieu);
    //
    //    @Named("nhaCungCapId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    NhaCungCapDTO toDtoNhaCungCapId(NhaCungCap nhaCungCap);
}
