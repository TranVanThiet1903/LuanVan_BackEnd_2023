package warehouse.management.app.service.mapper;

import org.mapstruct.*;
import warehouse.management.app.domain.NhomNguyenLieu;
import warehouse.management.app.service.dto.NhomNguyenLieuDTO;

/**
 * Mapper for the entity {@link NhomNguyenLieu} and its DTO {@link NhomNguyenLieuDTO}.
 */
@Mapper(componentModel = "spring")
public interface NhomNguyenLieuMapper extends EntityMapper<NhomNguyenLieuDTO, NhomNguyenLieu> {}
