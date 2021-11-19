package Dio.NTTData.BeerStock.mapper;

import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    BeerMapper INSTANCE = Mapper.getMapper(BeerMapper.class);

    Beer toModel(BeeDTO beeDTO);

    BeerDTO toDTO(Beer beer);
}
