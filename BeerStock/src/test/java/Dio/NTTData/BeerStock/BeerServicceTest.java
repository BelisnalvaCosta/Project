package Dio.NTTData.BeerStock;

import Dio.NTTData.BeerStock.BeerNotFoundException.BeerNotFoundException;
import Dio.NTTData.BeerStock.builder.BeerDTOBuilder;
import Dio.NTTData.BeerStock.dto.BeerDTO;
import Dio.NTTData.BeerStock.entity.Beer;
import Dio.NTTData.BeerStock.exception.BeerAlreadyRegisteredException;
import Dio.NTTData.BeerStock.mapper.BeerMapper;
import Dio.NTTData.BeerStock.repository.BeerRepository;
import Dio.NTTData.BeerStock.service.BeerService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BeerServicceTest {

    private static final long INVALID_BEER_ID = 1L;

    @Mock
    private BeerRepository beerRepository;

    private BeerMapper beerMapper = BeerMapper.INSTANCE;

    @InjectMocks
    private BeerService beerService;

    @Test
    void whenBeerInformedThenItShuldBeCreated() throws BeerAlreadyRegisteredException {
        //given
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerTDO();
        Beer expectedSavedBeer = beerMapper.toModel(expectedBeerDTO);

        //when
        when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.empty());
        when(beerRepository.save(expectedSavedBeer)).thenReturn(expectedSavedBeer);

        //then
        BeerDTO createBeerDTO = beerService.createBeer(expectedBeerDTO);

        assertThat(createBeerDTO.getId(), is(equalTo(expectedBeerDTO.getId())));
        assertThat(createBeerDTO.getName(), is(equalTo(expectedBeerDTO.getName())));
        assertThat(createBeerDTO.getQuantity(), is(equalTo(expectedBeerDTO.getQuantity())));

        assertThat(createBeerDTO.getQuantity(), is(greaterThan(2)));

    }

    @Test
    void whenAlreadyRegisteredBeerInformedThenAnExceptionShouldBeThrown() {
        //given
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerTDO();
        Beer duplicatedBeer = beerMapper.toModel(expectedBeerDTO);

        //when
        when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.of(duplicatedBeer));

        //then
        assertThrows(BeerAlreadyRegisteredException.class, () -> beerService.createBeer(expectedBeerDTO));
    }

    @Test
    void whenValidBeerNameIsGivenThenReturnABeer() {
        //given
        BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerTDO();
        Beer duplicatedFoundBeer = beerMapper.toModel(expectedFoundBeerDTO);

        //when
        when(beerRepository.findByName(expectedFoundBeerDTO.getName())).thenReturn(Optional.of(expectedFoundBeerDTO));

        //then
        BeerDTO FoundBeerDTO = beerService.findByName(expectedFoundBeerDTO.getName());

        assertThat(foundbeerDTO, is(equalTo(expectedFoundBeerDTO)));
    }

    @Test
    void whenNotRegisteredBeerNameIsGivenThenThrowAnException() {
        //given
        BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerTDO();

        //when
        when(beerRepository.findByName(expectedFoundBeerDTO.getName())).thenReturn(Optional.empty());

        //then
        assertThrows(BeerNotFoundException.class, () -> beerService.findByName(expectedFoundBeerDTO.getName())))

    }

    @Test
    void whenListBeerIsCalledThenReturnAListOfBeers() {
        //given
        BeerDTO expectFoundBeerDTO = Dio.NTTData.BeerStock.dto.BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedFoundBeer = beerMapper.toModel(expectFoundBeerDTO);

        //when
        when(beerRepository.findAll()).thenReturn(Collections.singletonList(expectFoundBeerDTO));

        //then
        List<BeerDTO> foundBeerDTO = beerService.listAll();

        assertThat(foundListBeersDTO, is(Not(empty())));
        assertThat(foundListBeerDTO.get(0), is(equalto(expectFoundBeerDTO));
    }

    @Test
    void whenListBeerIsCalledThenReturnAnEmptyListOfBeers() {
        //when
        when(beerRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //then
        List<BeerDTO> foundBeerDTO = beerService.listAll();

        assertThat(foundListBeersDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenABeerShouldBeDeleted() throws BeerNotFoundException{
        //given
        BeerDTO expectDeletedBeerDTO = Dio.NTTData.BeerStock.dto.BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedDeletedBeer = beerMapper.toModel(expectDeletedBeerDTO);

        //when
        when(beerRepository).findById(expectedDeletedBeer.getId())).thenReturn(Optional.of(expectDeletedBeerDTO));
        doNothing().when(beerRepository).deleteById(expectDeletedBeerDTO.getId());

        //then
        beerService.deleteById(expectDeletedBeerDTO.getId());

        verify(beerRepository.times(1)).findById(expectDeletedBeerDTO.getId());
        verify(beerRepository.times(1)).deleteById(expectDeletedBeerDTO.getId());
    }

    @Test
    void whenIncrementIsCalledThenIncrement() throws BeerNotFoundException, BeerStockExpectedExcception {
        //given
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerTDO();
        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

        //when
        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));
        when(beerRepository.save(expectedBeer)).thenReturn(expectedBeer);

        int quantityToIncrement = 10;
        int expectedQuantityAfterIncrement = expectedBeerDTO.getQuantity() + quantityToIncrement;

        //then
        BeerDTO incrementBeerDTO = beerService.increment(expectedBeerDTO.getId(), quantityToIncrement);

        assertThat(expectedQuantityAfterIncrement, equalTo(incrementBeerDTO.getQuantity()));
        assertThat(expectedQuantityAfterIncrement, lessThan(expectedBeerDTO.getMax()));
    }
}
