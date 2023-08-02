package com.asset.dailyapplookupservice.service;

import com.asset.dailyapplookupservice.database.dao.imp.TariffModelDAOImp;
import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.model.response.tariff.TariffModelsResponse;
import com.asset.dailyapplookupservice.model.response.tariff.TariffModelResponse;
import com.asset.dailyapplookupservice.model.shared.TariffModel;
import com.asset.dailyapplookupservice.utils.TariffModelUtil;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TariffModelService {

    @Autowired
    TariffModelDAOImp tariffModelDAOImp;

    public TariffModelsResponse getAllTariffModels() throws LookupException {
        List<TariffModel> tariffModels = tariffModelDAOImp.getAllTariffModel();
        DailyAppLogger.DEBUG_LOGGER.debug("#Retrieved TariffModels = " + tariffModels.size());
        if (tariffModels == null || tariffModels.isEmpty()) {
            DailyAppLogger.DEBUG_LOGGER.error("No tariff were found");
            throw new LookupException(ErrorCodes.ERROR.NO_TARIFF_MODELS_FOUND, Defines.SEVERITY.ERROR, "tariffModel");
        }
        List<TariffModelResponse> tariffModelResponses = TariffModelUtil.mapTariffModelsResponse(tariffModels);
        return new TariffModelsResponse(tariffModelResponses);
    }


    public Integer updateTariffModel(TariffModelResponse tariffModelResponse) throws LookupException {
        TariffModel tariffModel = TariffModelUtil.mapTariffModel(tariffModelResponse);
        tariffModel.setActivationSourceFlag(Optional.ofNullable(tariffModel.getActivationSourceFlag()).orElse(0));
        tariffModel.setDeacSourceFlag(Optional.ofNullable(tariffModel.getDeacSourceFlag()).orElse(0));

        Integer rowsAffected = tariffModelDAOImp.updateTariffModel(tariffModel);
        if (rowsAffected == 0 || rowsAffected == null) {
            DailyAppLogger.DEBUG_LOGGER.error("Tariff code not updated");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "tariffModel");
        }
        DailyAppLogger.DEBUG_LOGGER.debug("Updated tariff model successfully with row affected [" + rowsAffected + "}");
        return rowsAffected;
    }

    public void updateTariffModelBatch(List<TariffModelResponse> tariffModelResponses) throws LookupException {
        List<TariffModel> tariffModels = TariffModelUtil.mapTariffModelList(tariffModelResponses);
        tariffModels.stream().filter(t -> t.getDeacSourceFlag() == null)
                .forEach(t -> t.setDeacSourceFlag(0));
        tariffModels.stream().filter(t -> t.getActivationSourceFlag() == null)
                .forEach(t -> t.setActivationSourceFlag(0));

        int[] rowsAffected = tariffModelDAOImp.UpdateTariffModelBatch(tariffModels);
        DailyAppLogger.DEBUG_LOGGER.debug("Updated tariff model batch successfully with row affected [" + Arrays.asList(rowsAffected) + "}");
    }

}
