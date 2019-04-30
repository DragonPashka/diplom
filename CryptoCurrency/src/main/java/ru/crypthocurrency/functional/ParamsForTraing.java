package ru.crypthocurrency.functional;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ParamsForTraing {

    private double[][] x;
    private int[] y;

}
