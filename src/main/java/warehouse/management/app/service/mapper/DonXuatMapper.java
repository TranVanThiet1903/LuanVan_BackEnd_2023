package warehouse.management.app.service.mapper;

import org.mapstruct.*;
import warehouse.management.app.domain.DonXuat;
import warehouse.management.app.domain.NhaCungCap;
import warehouse.management.app.domain.NhaKho;
import warehouse.management.app.domain.TaiKhoan;
import warehouse.management.app.service.dto.DonXuatDTO;
import warehouse.management.app.service.dto.NhaCungCapDTO;
import warehouse.management.app.service.dto.NhaKhoDTO;
import warehouse.management.app.service.dto.TaiKhoanDTO;

/**
 * Mapper for the entity {@link DonXuat} and its DTO {@link DonXuatDTO}.
 */
@Mapper(componentModel = "spring")
public interface DonXuatMapper extends EntityMapper<DonXuatDTO, DonXuat> {
    //    @Mapping(target = "nhaCungCap", source = "nhaCungCap", qualifiedByName = "nhaCungCapId")
    //    @Mapping(target = "nhaKho", source = "nhaKho", qualifiedByName = "nhaKhoId")
    //    @Mapping(target = "taiKhoan", source = "taiKhoan", qualifiedByName = "taiKhoanId")
    //    DonXuatDTO toDto(DonXuat s);
    //
    //    @Named("nhaCungCapId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    NhaCungCapDTO toDtoNhaCungCapId(NhaCungCap nhaCungCap);
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
