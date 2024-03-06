package warehouse.management.app.service.mapper;

import org.mapstruct.*;
import warehouse.management.app.domain.NhaCungCap;
import warehouse.management.app.service.dto.NhaCungCapDTO;

/**
 * Mapper for the entity {@link NhaCungCap} and its DTO {@link NhaCungCapDTO}.
 */
@Mapper(componentModel = "spring")
public interface NhaCungCapMapper extends EntityMapper<NhaCungCapDTO, NhaCungCap> {}
