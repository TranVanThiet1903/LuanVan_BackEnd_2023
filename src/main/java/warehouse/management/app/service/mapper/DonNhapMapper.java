package warehouse.management.app.service.mapper;

import org.mapstruct.*;
import warehouse.management.app.domain.DonNhap;
import warehouse.management.app.domain.NhaCungCap;
import warehouse.management.app.domain.NhaKho;
import warehouse.management.app.domain.PhieuNhap;
import warehouse.management.app.domain.TaiKhoan;
import warehouse.management.app.service.dto.DonNhapDTO;
import warehouse.management.app.service.dto.NhaCungCapDTO;
import warehouse.management.app.service.dto.NhaKhoDTO;
import warehouse.management.app.service.dto.PhieuNhapDTO;
import warehouse.management.app.service.dto.TaiKhoanDTO;

/**
 * Mapper for the entity {@link DonNhap} and its DTO {@link DonNhapDTO}.
 */
@Mapper(componentModel = "spring")
public interface DonNhapMapper extends EntityMapper<DonNhapDTO, DonNhap> {
    //    @Mapping(target = "nhaCungCap", source = "nhaCungCap", qualifiedByName = "nhaCungCapId")
    //    @Mapping(target = "phieuNhap", source = "phieuNhap", qualifiedByName = "phieuNhapId")
    //    @Mapping(target = "nhaKho", source = "nhaKho", qualifiedByName = "nhaKhoId")
    //    @Mapping(target = "taiKhoan", source = "taiKhoan", qualifiedByName = "taiKhoanId")
    //    DonNhapDTO toDto(DonNhap s);
    //
    //    @Named("nhaCungCapId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    NhaCungCapDTO toDtoNhaCungCapId(NhaCungCap nhaCungCap);
    //
    //    @Named("phieuNhapId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    PhieuNhapDTO toDtoPhieuNhapId(PhieuNhap phieuNhap);
    //
    //    @Named("nhaKhoId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    NhaKhoDTO toDtoNhaKhoId(NhaKho nhaKho);
    //
    //    @Named("taiKhoanId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    TaiKhoanDTO toDtoTaiKhoanId(TaiKhoan taiKhoan);
}
