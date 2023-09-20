package com.java.assignment.loanstore.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AggregateResponse {
    private List<Integer> aggregateAmount;

    @Override
    public String toString() {
        String response = null;
        for (int result : aggregateAmount) {
            response = "{" +
                    "aggregateAmount= " + result +
                    '}';
        }
        return response;
    }
}
