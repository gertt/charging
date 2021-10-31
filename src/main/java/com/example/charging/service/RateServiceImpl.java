package com.example.charging.service;

import com.example.charging.payload.request.Cdr;
import com.example.charging.payload.request.Rate;
import com.example.charging.payload.request.RateRequest;
import com.example.charging.payload.response.Components;
import com.example.charging.payload.response.CalculatedData;
import com.example.charging.payload.response.RateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

import static com.example.charging.util.AppConstants.*;
import static com.example.charging.util.DateUtils.diffDate;

@Service
public class RateServiceImpl implements RateService {

    public final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * This service method executes all the logic of the process.
     * It calculates the overall and creates the Component object energyPrice calculated,
     * timePrice calculated and the transaction.
     * Method returns a json containing the overall and Component object.
     **/
    @Override
    public RateResponse calculateRate(RateRequest rateRequest) throws Exception {

        DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_FORMAT);
        Rate rate = rateRequest.getRate();
        Cdr cdr = rateRequest.getCdr();

        float measureDifference = cdr.getMeterStop().floatValue() - cdr.getMeterStart().floatValue();
        float timeDifference = diffDate(cdr.getTimestampStart(), cdr.getTimestampStop());

        double energyPrice = Double.parseDouble(decimalFormat.format((measureDifference / 1000f) * rate.getEnergy()));
        double timePrice = Double.parseDouble(decimalFormat.format(timeDifference * rate.getTime()));
        double overall = Double.parseDouble(decimalFormat.format(energyPrice + timePrice + rate.getTransaction()));
        log.info("Start charging process rating with energyPrice :{} and timePrice :{} and overall:{}", energyPrice, timePrice, overall);

        CalculatedData responseCalculatedData = new CalculatedData();
        responseCalculatedData.setOverall(overall);

        Components components = new Components();
        components.setEnergy(energyPrice);
        components.setTime(timePrice);
        components.setTransaction(rate.getTransaction());
        responseCalculatedData.setComponents(components);
        log.info("Stop charging process rating with calculatedData :{}", responseCalculatedData);
        return new RateResponse(true, RATE_ENERGY_OUTPUT, null, responseCalculatedData);
    }
}
