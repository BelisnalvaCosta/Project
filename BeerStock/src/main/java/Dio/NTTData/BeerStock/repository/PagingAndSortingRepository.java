package Dio.NTTData.BeerStock.repository;

import Dio.NTTData.BeerStock.Sort.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagingAndSortingRepository <T, ID> extends CrudRepository<T, ID>{
    Iterable<T> findAll(Sort var1);

    Page<T> findAll(Pageable var1);
}
