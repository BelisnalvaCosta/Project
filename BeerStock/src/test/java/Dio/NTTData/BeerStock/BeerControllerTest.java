package Dio.NTTData.BeerStock;

import Dio.NTTData.BeerStock.BeerNotFoundException.BeerNotFoundException;
import Dio.NTTData.BeerStock.dto.BeerDTO;
import Dio.NTTData.BeerStock.dto.BeerDTOBuilder;
import Dio.NTTData.BeerStock.entity.Beer;
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

import java.util.Collections;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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
        when(beerService.createBeer(beerDTO)).thenReturn(beerDTO);

        //then
        MockMvc.perform(post(BEER_API_URL_PATH)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(beerDTO)))
               .andExpect(status().isCreated())
               .andExpect(JsonPath("$.name", is(beerDTO.getName())))
               .andExpect(JsonPath( "$.brand", is(beerDTO.getBrand())))
               .andExpect(JsonPath( "$.type", is(beerDTO.getType().toString())));
    }

    @Test
    void  whenPOSTIsCalledWithoutRequiredFieldThenAnErroIsReturned() throws Exception {
        //given
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerTDO();
        beerDTO.setBrand(null);

        //when
        when(beerService.createBeer(beerDTO)).thenReturn(beerDTO);

        //then
        MockMvc.perform(post(BEER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(beerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(JsonPath("$.name", is(beerDTO.getName())))
                .andExpect(JsonPath( "$.brand", is(beerDTO.getBrand())))
                .andExpect(JsonPath( "$.type", is(beerDTO.getType().toString())));
    }

    @Test
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
        //given
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerTDO();

        //when
        when(beerService.findByName(beerDTO.getName())).thenReturn(beerDTO);

        //then
        mockMvc.perform(MockitoMcvRequestBuilders.get(BEER_API_URL_PATH + "/" beerDTO.getName()))
                .contentType(MediaType.APPLICCATION_JSON))
                .andExpect(status().isOk());
                .andExpect(JsonPath("$.name", is(beerDTO.getName())))
                .andExpect(JsonPath( "$.brand", is(beerDTO.getBrand()))
                .andExpect(JsonPath( "$.type", is(beerDTO.getType().toString())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception {
        //given
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerTDO();

        //when
        when(beerService.findByName(beerDTO.getName())).thenThrow(BeerNotFoundException.class);

        //then
        mockMvc.perform(MockitoMcvRequestBuilders.get(BEER_API_URL_PATH + "/" beerDTO.getName()))
                .contentType(MediaType.APPLICCATION_JSON))
                .andExpect(status().isNotFound());
    }

        @Test
        void whenGETListWithBeersIsCalledThenOkStatusIsReturned() throws Exception {
            //given
            BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerTDO();

            //when
            when(beerService.listAll()).thenReturn(Collections.singleton(List(beerDTO));

            //then
            mockMvc.perform(MockitoMcvRequestBuilders.get(BEER_API_URL_PATH))
                    .contentType(MediaType.APPLICCATION_JSON))
                .andExpect(status().isOk());
                .andExpect(JsonPath("$[0].name", is(beerDTO.getName())))
                    .andExpect(JsonPath( "$[0].brand", is(beerDTO.getBrand()))
                            .andExpect(JsonPath( "$[0].type", is(beerDTO.getType().toString())));
    }

        @Test
        void whenDELETEIsCalledWithValidThenNoContentStatusIsReturned() throws Exception {
            //given
            BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerTDO();

            //when
            doNothing().when(beerService.deleteById(beerDTO.getId();

            //then
            mockMvc.perform(MockitoMcvRequestBuilders.delete(BEER_API_URL_PATH + "/" beerDTO.getName()))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotContent());
        }

        @Test
        void whenDELETEIsCalledWithInvalidThenNotFoundStatusIsReturned() throws Exception {
            //when
            doThrow(BeerNotFoundException.class).when(beerService.deleteById(INVALID_BEER_ID);

            //then
            mockMvc.perform(MockitoMcvRequestBuilders.delete(BEER_API_URL_PATH + "/" + INVALID_BEER_ID)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

}

    private Mockito doThrow(Class<BeerNotFoundException> beerNotFoundExceptionClass) {
    }
