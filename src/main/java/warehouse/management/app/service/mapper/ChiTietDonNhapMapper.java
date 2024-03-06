package warehouse.management.app.service.mapper;

import org.mapstruct.*;
import warehouse.management.app.domain.ChiTietDonNhap;
import warehouse.management.app.domain.DonNhap;
import warehouse.management.app.domain.NguyenLieu;
import warehouse.management.app.service.dto.ChiTietDonNhapDTO;
import warehouse.management.app.service.dto.DonNhapDTO;
import warehouse.management.app.service.dto.NguyenLieuDTO;

/**
 * Mapper for the entity {@link ChiTietDonNhap} and its DTO {@link ChiTietDonNhapDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChiTietDonNhapMapper extends EntityMapper<ChiTietDonNhapDTO, ChiTietDonNhap> {
    //    @Mapping(target = "donNhap", source = "donNhap", qualifiedByName = "donNhapId")
    //    @Mapping(target = "nguyenLieu", source = "nguyenLieu", qualifiedByName = "nguyenLieuId")
    //    ChiTietDonNhapDTO toDto(ChiTietDonNhap s);
    //
    //    @Named("donNhapId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    DonNhapDTO toDtoDonNhapId(DonNhap donNhap);
    //
    //    @Named("nguyenLieuId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    NguyenLieuDTO toDtoNguyenLieuId(NguyenLieu nguyenLieu);
}
