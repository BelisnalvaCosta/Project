package Dio.NTTData.BeerStock.repository;

import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

import static net.bytebuddy.build.HashCodeAndEqualsPlugin.*;

@NoRepositoryBean
public interface JpaRepository<T, ID> extends PagingAndSortingRepository<T, ID> QueryByExampleEcecution {
    List<T> findAll();

    List<T> findAll(Sort var1);

    List<T> findAllById(Iterable<ID> var1);

    <S extends T> List<S> saveAll(Iterable<S> var1);

    void flush();

    <S extends T> S saveAndFlush();

    void deleteInBatch(Iterable<T> var1);

    void deleteAllInBatch();

    T getOne(ID var1);

    <S extends T> List<S> findAll(Example<S> var1);

    <S extends T> List<S> findAll(Example<S> var1, sort, var2);

}
