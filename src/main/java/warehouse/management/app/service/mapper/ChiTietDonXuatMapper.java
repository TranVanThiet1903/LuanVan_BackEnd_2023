package warehouse.management.app.service.mapper;

import org.mapstruct.*;
import warehouse.management.app.domain.ChiTietDonXuat;
import warehouse.management.app.domain.NguyenLieu;
import warehouse.management.app.domain.PhieuNhap;
import warehouse.management.app.service.dto.ChiTietDonXuatDTO;
import warehouse.management.app.service.dto.NguyenLieuDTO;
import warehouse.management.app.service.dto.PhieuNhapDTO;

/**
 * Mapper for the entity {@link ChiTietDonXuat} and its DTO {@link ChiTietDonXuatDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChiTietDonXuatMapper extends EntityMapper<ChiTietDonXuatDTO, ChiTietDonXuat> {
    //    @Mapping(target = "phieuNhap", source = "phieuNhap", qualifiedByName = "phieuNhapId")
    //    @Mapping(target = "nguyenLieu", source = "nguyenLieu", qualifiedByName = "nguyenLieuId")
    //    ChiTietDonXuatDTO toDto(ChiTietDonXuat s);
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
