package com.dat.backend_version_2.specification.Helper;

import org.springframework.data.jpa.domain.Specification;

public class FieldSpecification {
    public static <T> Specification<T> hasNestedFieldEqual(
            String outerField, String innerField, Object value) {

        return (root, query, cb) -> cb.equal(
                root.get(outerField).get(innerField), value
        );
    }

    public static <T> Specification<T> hasFieldEqual(
            String field, Object value){
        return (root, query, cb) -> cb.equal(
                root.get(field), value);
    }
}
