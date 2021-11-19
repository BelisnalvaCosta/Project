package Dio.NTTData.BeerStock.controller;

import Dio.NTTData.BeerStock.BeerNotFoundException.BeerNotFoundException;
import Dio.NTTData.BeerStock.dto.BeerDTO;
import Dio.NTTData.BeerStock.exception.BeerAlreadyRegisteredException;
import Dio.NTTData.BeerStock.service.BeerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/beer")
@AllArgsConstructor(onConstructor = @--(@Autowired))
public class BeeController implements BeerControllerDocs {

    private final BeerService beerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BeerDTO createBeer(@RequestBody @Valid BeerDTO beerDTO) throws BeerAlreadyRegisteredException {
        return beerService.createBeer(beerDTO);
    }

    @GetMapping("/{name}")
    public BeerDTO findByName(@PathVariable String name) throws BeerNotFoundException {
        return beerService.findByName(name);
    }

    GetMapping
    public List<BeerDTO> Listbeers() {
        return beerService.listAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws BeerNotFoundException {
        beerService.deleteById(id);
    }
}
