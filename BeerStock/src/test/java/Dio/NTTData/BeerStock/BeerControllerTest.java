package Dio.NTTData.BeerStock;

import Dio.NTTData.BeerStock.dto.BeerDTO;
import Dio.NTTData.BeerStock.service.BeerService;
import com.google.common.net.MediaType;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.JsonPath;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BeerControllerTest {

    private static final String BEER_API_URL_PATH = "/api/v1/beers";
    private static final long VALID_BEER_ID = 1L;
    private static final long VALID_BEER_ID = 2L;
    private static final String BEER_API_SUBPATH_INCREMENT_URL = "/increment";
    private static final String BEER_API_SUBPATH_DECREMENT_URL = "/decrement";

    private MockMvc;

    @Mock
    private BeerService beerService;

    @InjectMocks
    private BeerController beerController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(beerController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView()))
                .build();

    @Test
    void  whenPOSTIsCalledThenABeerIsCreated() {
        //given
        BeerDTO beerDTO = BeerTDOBuilder.builder().build().toBeerTDO();

        //when
            Mockito.when(beerService.createBeer(beerDTO)).thenReturn(beerDTO);

            //then
            MockMvc.perform(post(BEER_API_URL_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(beerDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(JsonPath("$.name", is(beerDTO.getName())))
                    .andExpect(JsonPath( "$.brand", is(beerDTO.getBrand())))
                    .andExpect(JsonPath( "$.type", is(beerDTO.getType().toString())));
    }
    private void build() {
    }
}

    private Object asJsonString(BeerDTO beerDTO) {}

    private Object post(String beerApiUrlPath) {}
