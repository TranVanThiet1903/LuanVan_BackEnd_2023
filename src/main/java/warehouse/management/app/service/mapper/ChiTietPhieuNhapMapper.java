package warehouse.management.app.service.mapper;

import org.mapstruct.*;
import warehouse.management.app.domain.ChiTietPhieuNhap;
import warehouse.management.app.domain.NguyenLieu;
import warehouse.management.app.domain.PhieuNhap;
import warehouse.management.app.service.dto.ChiTietPhieuNhapDTO;
import warehouse.management.app.service.dto.NguyenLieuDTO;
import warehouse.management.app.service.dto.PhieuNhapDTO;

/**
 * Mapper for the entity {@link ChiTietPhieuNhap} and its DTO {@link ChiTietPhieuNhapDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChiTietPhieuNhapMapper extends EntityMapper<ChiTietPhieuNhapDTO, ChiTietPhieuNhap> {
    //    @Mapping(target = "phieuNhap", source = "phieuNhap", qualifiedByName = "phieuNhapId")
    //    @Mapping(target = "nguyenLieu", source = "nguyenLieu", qualifiedByName = "nguyenLieuId")
    //    ChiTietPhieuNhapDTO toDto(ChiTietPhieuNhap s);
    //
    //    @Named("phieuNhapId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    PhieuNhapDTO toDtoPhieuNhapId(PhieuNhap phieuNhap);
    //
    //    @Named("nguyenLieuId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    NguyenLieuDTO toDtoNguyenLieuId(NguyenLieu nguyenLieu);
}
