package com.storm.test.services;

import com.storm.test.dtos.RequestDTO;
import com.storm.test.dtos.SearchRequestDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FiltersSpecifications <T> {

    public Specification<T> getSearch(SearchRequestDTO searchRequestDTO){

        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(searchRequestDTO.getColumn()),searchRequestDTO.getValue());
            }
        };
    }

    public Specification<T> getSearch(List<SearchRequestDTO> searchRequestDTOS, RequestDTO.GlobalOperator globalOperator){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for(SearchRequestDTO requestDTO : searchRequestDTOS){


                switch (requestDTO.getOperation()){
                    case EQUAL:
                            Predicate equal = criteriaBuilder.equal(root.get(requestDTO.getColumn()),requestDTO.getValue());
                    predicates.add(equal);
                    break;

                    case LIKE:
                        Predicate like = criteriaBuilder.like(root.get(requestDTO.getColumn()),"%"+ requestDTO.getValue()+"%");
                        predicates.add(like);
                        break;

                    case IN:
                        String[] split = requestDTO.getValue().split(",");
                        Predicate in = root.get(requestDTO.getColumn()).in(Arrays.asList(split));
                        predicates.add(in);
                        break;

                    case LESS_THAN:
                        Predicate less = criteriaBuilder.lessThan(root.get(requestDTO.getColumn()),requestDTO.getValue());
                        predicates.add(less);
                        break;

                    case GREATER_THAN:
                        Predicate greater = criteriaBuilder.greaterThan(root.get(requestDTO.getColumn()),requestDTO.getValue());
                        predicates.add(greater);
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + "");

                }
            }
            if(globalOperator.equals(RequestDTO.GlobalOperator.AND)) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }else{
                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
