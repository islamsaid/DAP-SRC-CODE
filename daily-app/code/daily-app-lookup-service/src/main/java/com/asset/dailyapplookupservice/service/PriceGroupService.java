package com.asset.dailyapplookupservice.service;

import com.asset.dailyapplookupservice.database.dao.imp.PriceGroupDAOImp;
import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.model.request.priceGroup.*;
import com.asset.dailyapplookupservice.model.response.pricegroup.GetAllGroupGroups;
import com.asset.dailyapplookupservice.model.response.pricegroup.GetPgGroupModel;
import com.asset.dailyapplookupservice.model.shared.PgGroupModel;
import com.asset.dailyapplookupservice.model.shared.PriceGroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PriceGroupService {

    @Autowired
    PriceGroupDAOImp priceGroupDAOImp;

    public GetAllPriceGroupResponse getAll() throws LookupException {
        List<PriceGroupModel> getAllPriceGroup = priceGroupDAOImp.getAll();
        DailyAppLogger.DEBUG_LOGGER.debug("#Retrieved PriceGroups = " + getAllPriceGroup.size());
        if (getAllPriceGroup == null || getAllPriceGroup.isEmpty()) {
            DailyAppLogger.DEBUG_LOGGER.error("No groups found");
            DailyAppLogger.ERROR_LOGGER.error("No groups found");
            throw new LookupException(ErrorCodes.ERROR.GROUPS_NOT_FOUND, Defines.SEVERITY.ERROR);
        }
        return new GetAllPriceGroupResponse(getAllPriceGroup);
    }

    public GetAllGroupGroups getAllPriceGroupGroups() {
        List<PgGroupModel> getAllPriceGroupGroups = priceGroupDAOImp.getAllPriceGroupGroups();
        DailyAppLogger.DEBUG_LOGGER.debug("#Retrieved PGGroups = " + getAllPriceGroupGroups.size());
        if (getAllPriceGroupGroups == null || getAllPriceGroupGroups.isEmpty()) {
            DailyAppLogger.DEBUG_LOGGER.error("No price group groups found");
            throw new LookupException(ErrorCodes.ERROR.GROUPS_NOT_FOUND, Defines.SEVERITY.ERROR);
        }
        return new GetAllGroupGroups(getAllPriceGroupGroups);
    }

    public void update(UpdatePriceGroupResponse updateRequest) {
        IfGroupExist(updateRequest.getPgGroupKey());
        int updated;
        updated = priceGroupDAOImp.UpdatePriceGroup(buildPriceGroupModelToUpdateIt(updateRequest));
        if (updated <= 0) {
            DailyAppLogger.DEBUG_LOGGER.error("Failed to update Price Group No PGroup id Found");
            DailyAppLogger.ERROR_LOGGER.error("Failed to update Price Group No PGroup id Found");
            throw new LookupException(ErrorCodes.ERROR.PG_NOT_FOUND, Defines.SEVERITY.ERROR, "priceGroup");
        }
    }


    private PriceGroupModel buildPriceGroupModelToUpdateIt(UpdatePriceGroupResponse updateRequest) {
        PriceGroupModel priceGroupModel = new PriceGroupModel();
        priceGroupModel.setPriceGroupCode(updateRequest.getPriceGroupCode());
        PgGroupModel pgGroupModel = new PgGroupModel();
        pgGroupModel.setPgGroupKey(updateRequest.getPgGroupKey());
        priceGroupModel.setPgGroupKey(pgGroupModel);
        return priceGroupModel;
    }


    public int addPriceGroupGroups(AddPriceGroupGroupRequest addPriceGroupGroupRequest) {
        Integer priceGroupGroupId = priceGroupDAOImp.addPGGroups(addPriceGroupGroupRequest);
        DailyAppLogger.DEBUG_LOGGER.debug("Added Pg Group id = " + priceGroupGroupId);
        if (priceGroupGroupId == null) {
            DailyAppLogger.DEBUG_LOGGER.error("Failed to add Price Group Groups");
            throw new LookupException(ErrorCodes.ERROR.UNKNOWN_ERROR, Defines.SEVERITY.ERROR, "Price Group Groups");
        }

        DailyAppLogger.DEBUG_LOGGER.debug("updating #price group models assigned to the pg-group = " + addPriceGroupGroupRequest.getPriceGroupModels().stream().count());
        PriceGroupModel priceGroupModel;
        for (int i = 0; i < addPriceGroupGroupRequest.getPriceGroupModels().stream().count(); i++) {
            priceGroupModel = addPriceGroupGroupRequest.BuildPriceGroupUsingPgGroup(addPriceGroupGroupRequest.getPriceGroupModels().get(i), addPriceGroupGroupRequest.getPgGroupModel()); // TODO enhance
            int updated = priceGroupDAOImp.UpdatePriceGroup(priceGroupModel);
            if (updated <= 0) {
                DailyAppLogger.DEBUG_LOGGER.error("Failed to update Price Group");
                DailyAppLogger.ERROR_LOGGER.error("Failed to update Price Group");
                throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "priceGroup");
            }
        }
        return priceGroupGroupId;
    }

    private void IfGroupExist(String PgGroupKeyId) {
        int updated = priceGroupDAOImp.getPgGroup(PgGroupKeyId);
        if (updated <= 0) {
            DailyAppLogger.DEBUG_LOGGER.error("Failed to update Price Group No PGroup id Found");
            throw new LookupException(ErrorCodes.ERROR.PG_NOT_FOUND, Defines.SEVERITY.ERROR, "priceGroup");
        }
    }

    @Transactional
    public void updatePriceGroupGroups(AddPriceGroupGroupRequest addPriceGroupGroupRequest) {
        int updated = priceGroupDAOImp.updatePgGroup(addPriceGroupGroupRequest.getPgGroupModel());
        if (updated <= 0) {
            DailyAppLogger.DEBUG_LOGGER.error("Failed to update Price Group No PGroup id Found");
            throw new LookupException(ErrorCodes.ERROR.PG_NOT_FOUND, Defines.SEVERITY.ERROR, "priceGroup");
        }
        DailyAppLogger.DEBUG_LOGGER.debug("Removing the assigned Price-Groups if exists");
        priceGroupDAOImp.setPgGroupKeyNullInPriceGroup(addPriceGroupGroupRequest.getPgGroupModel().getPgGroupKey());

        DailyAppLogger.DEBUG_LOGGER.debug("[PGG] number of new assigned #PriceGroups = {}", addPriceGroupGroupRequest.getPriceGroupModels().size());
        PriceGroupModel priceGroupModel;
        for (int i = 0; i < addPriceGroupGroupRequest.getPriceGroupModels().stream().count(); i++) {
            priceGroupModel = addPriceGroupGroupRequest.BuildPriceGroupUsingPgGroup(addPriceGroupGroupRequest.getPriceGroupModels().get(i), addPriceGroupGroupRequest.getPgGroupModel()); // TODO enhance
            updated = priceGroupDAOImp.UpdatePriceGroup(priceGroupModel);
            if (updated <= 0) {
                DailyAppLogger.DEBUG_LOGGER.error("Failed to update Price Group");
                throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "priceGroup");
            }
        }
        ifPgGroupDoesNotHavePriceGroup(addPriceGroupGroupRequest);

        priceGroupDAOImp.assertPgGroupKeyExistenceForPriceGroup(addPriceGroupGroupRequest.getPgGroupModel().getPgGroupKey());

    }

    private void ifPgGroupDoesNotHavePriceGroup(AddPriceGroupGroupRequest addPriceGroupGroupRequest) {
        int updated;
        PriceGroupModel priceGroupModel;
        DailyAppLogger.DEBUG_LOGGER.debug("if pg group doesn't have any price group ");
        if (addPriceGroupGroupRequest.getPriceGroupModels().stream().count() == 0) {
            DailyAppLogger.DEBUG_LOGGER.debug("Start retrieving price group groups ");
            GetPgGroupRequestModel getPgGroupRequestModel = new GetPgGroupRequestModel();
            getPgGroupRequestModel.setPgGroupId(addPriceGroupGroupRequest.getPgGroupModel().getPgGroupKey());
            GetPgGroupModel getAllPriceGroupGroups = priceGroupDAOImp.getPriceGroupGroupById(getPgGroupRequestModel);
            if (getAllPriceGroupGroups == null) {
                DailyAppLogger.DEBUG_LOGGER.error("No price group groups found");
                throw new LookupException(ErrorCodes.ERROR.GROUPS_NOT_FOUND, Defines.SEVERITY.ERROR);
            }
            DailyAppLogger.DEBUG_LOGGER.debug("Start to set price group null ");

            for (int i = 0; i < getAllPriceGroupGroups.getPriceGroupModelList().size(); i++) {
                getAllPriceGroupGroups.getPgGroupModel().setPgGroupKey(null);
                priceGroupModel = addPriceGroupGroupRequest.BuildPriceGroupUsingPgGroup(getAllPriceGroupGroups.getPriceGroupModelList().get(i),
                        getAllPriceGroupGroups.getPgGroupModel()); // TODO enhance
                updated = priceGroupDAOImp.UpdatePriceGroup(priceGroupModel);
                if (updated <= 0) {
                    DailyAppLogger.DEBUG_LOGGER.error("Failed to update Price Group");
                    throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "priceGroup");
                }
            }
        }
    }

    public GetPgGroupModel getPriceGroupGroups(GetPgGroupRequestModel pgGroup) {
        GetPgGroupModel getAllPriceGroupGroups = priceGroupDAOImp.getPriceGroupGroupById(pgGroup);
        DailyAppLogger.DEBUG_LOGGER.debug("Retrieved pg-group: " + getAllPriceGroupGroups);
        if (getAllPriceGroupGroups == null) {
            DailyAppLogger.DEBUG_LOGGER.error("No price group groups found");
            throw new LookupException(ErrorCodes.ERROR.GROUPS_NOT_FOUND, Defines.SEVERITY.ERROR);
        }
        return getAllPriceGroupGroups;
    }

    public void UpdateBatchPriceGroup(UpdateBatchPriceGroupResponse updateRequest) {
        DailyAppLogger.DEBUG_LOGGER.info("[PG] Update batch of price groups #pg=" + updateRequest.getPriceGroupList().size());
        priceGroupDAOImp.UpdatePriceGroupBatch(updateRequest.getPriceGroupList());
    }

    public void deletePgGroup(String pgGroupId) {
        DailyAppLogger.DEBUG_LOGGER.debug("Validate PgGroupKey usability in the ConfigurationSystem ");
        priceGroupDAOImp.pgGroupExistInPriceGroup(pgGroupId);

        DailyAppLogger.DEBUG_LOGGER.debug("Removing the assigned Price-Groups if exists");
        priceGroupDAOImp.setPgGroupKeyNullInPriceGroup(pgGroupId);

        DailyAppLogger.DEBUG_LOGGER.debug("Deleting pg group ");
        priceGroupDAOImp.deletePgGroup(pgGroupId);
    }
}
