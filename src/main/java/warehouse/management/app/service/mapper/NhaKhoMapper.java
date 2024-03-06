package warehouse.management.app.service.mapper;

import java.util.stream.Collectors;
import org.mapstruct.*;
import warehouse.management.app.domain.ChiTietKho;
import warehouse.management.app.domain.NhaKho;
import warehouse.management.app.service.dto.ChiTietKhoDTO;
import warehouse.management.app.service.dto.NhaKhoDTO;

/**
 * Mapper for the entity {@link NhaKho} and its DTO {@link NhaKhoDTO}.
 */
@Mapper(componentModel = "spring")
public interface NhaKhoMapper extends EntityMapper<NhaKhoDTO, NhaKho> {}
