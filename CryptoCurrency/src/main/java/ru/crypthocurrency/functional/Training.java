package ru.crypthocurrency.functional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.crypthocurrency.services.CrypthoCurrencyServices;
import ru.crypthocurrency.tables.CrypthoCurrencyEntity;
import smile.classification.SVM;
import smile.math.kernel.GaussianKernel;

import java.util.ArrayList;
import java.util.List;

@Service
public class Training {
    @Autowired
    private CrypthoCurrencyServices crypthoCurrencyServices;

    @Autowired
    private ExctractParam exctractParam;

    private SVM<double[]> svm;

    private boolean trained = false;

    private void trainSVM() {

        List<CrypthoCurrencyEntity> trainData = new ArrayList<>(100);

        List<CrypthoCurrencyEntity> entityList = crypthoCurrencyServices.findAll();

        for (CrypthoCurrencyEntity entity : entityList) {

            if (entity.getTrainingData() != null && (entity.getTrainingData().equals("Да, хорошая валюта") || entity.getTrainingData().equals("Да, плохая валюта"))){
                trainData.add(entity);
            }

        }
        ParamsForTraing params=exctractParam.extractTrainDataAttributes(trainData);

        svm = new SVM<double[]>(new GaussianKernel(8.0), 5.0);
        svm.learn(params.getX(), params.getY());
        svm.finish();
    }

    private List<Integer> getPredict(List<CrypthoCurrencyEntity> predictValues){

        ParamsForTraing params=exctractParam.extractTrainDataAttributes(predictValues);
        List<Integer> responses=new ArrayList<>(30);

        for (int i=0; i<params.getX().length; i++){
            responses.add(svm.predict(params.getX()[i]));
        }


        return responses;
    }

}
