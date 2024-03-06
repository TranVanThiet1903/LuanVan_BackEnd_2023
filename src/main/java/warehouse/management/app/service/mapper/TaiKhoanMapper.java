package warehouse.management.app.service.mapper;

import org.mapstruct.*;
import org.springframework.stereotype.Component;
import warehouse.management.app.domain.TaiKhoan;
import warehouse.management.app.service.dto.TaiKhoanDTO;

/**
 * Mapper for the entity {@link TaiKhoan} and its DTO {@link TaiKhoanDTO}.
 */
@Component
@Mapper(componentModel = "spring")
public interface TaiKhoanMapper extends EntityMapper<TaiKhoanDTO, TaiKhoan> {}
