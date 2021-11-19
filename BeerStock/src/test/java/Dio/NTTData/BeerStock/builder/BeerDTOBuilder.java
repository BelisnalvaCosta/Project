package Dio.NTTData.BeerStock.builder;

import Dio.NTTData.BeerStock.dto.BeerDTO;
import lombok.Builder;

@Builder
public class BeerDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Brahma";

    @Builder.Default
    private String brand = "Ambev";

    @Builder.Default
    private Int max = 50;

    @Builder.Default
    private int quantity = 10;

    @Builder.Default
    private BeerType type = BeerType.LAGER;

    public BeerDTO toBeerTDO() {
        return new BeerDTO(id,
                name,
                brand,
                max,
                quantity,
                type);
    }

}
