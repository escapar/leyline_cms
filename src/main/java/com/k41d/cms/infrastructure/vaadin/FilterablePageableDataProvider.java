package com.k41d.cms.infrastructure.vaadin;

import com.vaadin.flow.data.provider.Query;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * copied from https://github.com/Artur-/spring-data-provider/blob/master/src/main/java/org/vaadin/artur/spring/dataprovider/FilterablePageableDataProvider.java
 * @param <T>
 * @param <F>
 */

public abstract class FilterablePageableDataProvider<T, F>
        extends PageableDataProvider<T, F> {

    private F filter = null;

    public void setFilter(F filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Filter cannot be null");
        }
        this.filter = filter;
        refreshAll();
    }

    @Override
    public int size(Query<T, F> query) {
        return super.size(getFilterQuery(query));
    }

    @Override
    public Stream<T> fetch(Query<T, F> query) {
        return super.fetch(getFilterQuery(query));
    }

    private Query<T, F> getFilterQuery(Query<T, F> t) {
        return new Query(t.getOffset(), t.getLimit(), t.getSortOrders(),
                t.getInMemorySorting(), filter);
    }

    protected Optional<F> getOptionalFilter() {
        if (filter == null) {
            return Optional.empty();
        } else {
            return Optional.of(filter);
        }
    }
}