package ru.crypthocurrency.functional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.crypthocurrency.services.CrypthoCurrencyHistoryServices;
import ru.crypthocurrency.services.CrypthoCurrencyServices;
import ru.crypthocurrency.tables.CrypthoCurrencyEntity;
import ru.crypthocurrency.tables.CrypthoCurrencyHistoryEntity;

import java.util.Arrays;
import java.util.List;

@Service
public class ExctractParam {

    @Autowired
    private CrypthoCurrencyHistoryServices crypthoCurrencyHistoryServices;

    public double getCurrencyVolatility(List<CrypthoCurrencyHistoryEntity> historyEntities, int type){

        double currencyVolatility;
        int size = (int)Math.ceil((double) historyEntities.size()/10);

        double [] meridianArrayPrice=medianArray(historyEntities, type);
        double [] meridianArrayProcent=new double[size];

        int count=0;

        for(int i=0; i< historyEntities.size(); i++){

            if (i>= count*10 && i<(count+1)*10){
                meridianArrayProcent[count] += Math.abs(extractValueByType(type, historyEntities.get(i))-meridianArrayPrice[count]);
            }else {
                meridianArrayProcent[count]=meridianArrayProcent[count]/meridianArrayPrice[count];
                count++;
                meridianArrayProcent[count]=Math.abs(extractValueByType(type, historyEntities.get(i))-meridianArrayPrice[count]);
            }
        }
        meridianArrayProcent[size-1] = meridianArrayProcent[size-1]/meridianArrayPrice[size-1];

        currencyVolatility = Arrays.stream(meridianArrayProcent).sum()/size;

        return currencyVolatility;


    }

    private double[] medianArray(List<CrypthoCurrencyHistoryEntity> historyEntities, int type){
        int size = (int)Math.ceil((double) historyEntities.size()/10);
        double [] array=new double[size];
        double m=0.0;
        int count=0;
        for(int i=0; i< historyEntities.size(); i++){
            if (i>= count*10 && i<(count+1)*10){
                m += extractValueByType(type, historyEntities.get(i));
            } else {
                array [count]= m/10;
                count++;
                m=extractValueByType(type, historyEntities.get(i));
            }
        }
        array[size-1] = m/(historyEntities.size()-10*(size-1));
        return array;

    }


    public ParamsForTraing extractTrainDataAttributes(List<CrypthoCurrencyEntity> trainDataAttributes){

        ParamsForTraing params = new ParamsForTraing();

        double [][] x=new double[trainDataAttributes.size()][];
        int [] y = new int[trainDataAttributes.size()];

        for (int i = 0; i < trainDataAttributes.size(); i++) {

            List<CrypthoCurrencyHistoryEntity> list=crypthoCurrencyHistoryServices.findAllHistoryByAddress(trainDataAttributes.get(i).getAddress());

            double priceVolatility=getCurrencyVolatility(list, 1);
            double tradesVolatility=getCurrencyVolatility(list, 2);
            double volumeVolatility=getCurrencyVolatility(list, 3);


            x[i]=new double[]{trainDataAttributes.get(i).getHolders().doubleValue(), trainDataAttributes.get(i).getTransfers().doubleValue(),
                    priceVolatility, tradesVolatility, volumeVolatility};
            if (trainDataAttributes.get(i).getTrainingData().equals("Да, хорошая валюта")){
                y[i]=1;
            }else
            {
                y[i]=-1;
            }

        }
        params.setX(x);
        params.setY(y);
        return params;
    }

    private double extractValueByType(int type, CrypthoCurrencyHistoryEntity entity){
        double value=-1;

        switch (type){
            case 1:  value=entity.getPriceUSD().doubleValue();
                break;
            case 2:  value=entity.getTradesCount().doubleValue();
                break;
            case 3:  value=entity.getVolumeTraded().doubleValue();
                break;
        }

        return value;
    }




}
