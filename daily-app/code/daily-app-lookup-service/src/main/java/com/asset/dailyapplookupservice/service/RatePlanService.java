package com.asset.dailyapplookupservice.service;

import com.asset.dailyapplookupservice.database.dao.imp.RatePlanDao;
import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.model.rateplan.RatePlanGroupModel;
import com.asset.dailyapplookupservice.model.rateplan.RatePlanModel;
import com.asset.dailyapplookupservice.model.response.rateplan.RatePlanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Component
public class RatePlanService {
    @Autowired
    RatePlanDao ratePlanDao;

    public List<RatePlanResponse> getAllRatePlans(){
        List<RatePlanResponse> ratePlans = ratePlanDao.getAllRatePlans();
        DailyAppLogger.DEBUG_LOGGER.debug("#Retrieved RatePlans = " + ratePlans.size());

        if(ratePlans == null || ratePlans.isEmpty()) {
            DailyAppLogger.DEBUG_LOGGER.error("No Rate Plans Found in db");
            DailyAppLogger.ERROR_LOGGER.error("No Rate Plans Found in db");
            throw new LookupException(ErrorCodes.ERROR.NO_RATE_PLANS_FOUND, Defines.SEVERITY.ERROR);
        }
        return ratePlans;
    }

    public void updateRatePlan(RatePlanModel ratePlanModel){
        if (getRatePlanByCode(ratePlanModel.getRatePlanCode()) == null) {
            DailyAppLogger.DEBUG_LOGGER.error("Rate Plan Code Does NOT Exist");
            DailyAppLogger.ERROR_LOGGER.error("Rate Plan Code Does NOT Exist");
            throw new LookupException(ErrorCodes.ERROR.RATE_PLAN_DOES_NOT_EXIST, Defines.SEVERITY.ERROR);
        }
        ratePlanModel.setActivationSourceFlag(Optional.ofNullable(ratePlanModel.getActivationSourceFlag()).orElse(0));
        ratePlanModel.setDeactivationFlag(Optional.ofNullable(ratePlanModel.getDeactivationFlag()).orElse(0));

        ratePlanDao.updateRatePlan(ratePlanModel);
    }

    public void ratePlansBatchUpdate(List<RatePlanModel> ratePlanModelList){
        DailyAppLogger.DEBUG_LOGGER.info("Update {} RatePlans Request " + ratePlanModelList, ratePlanModelList.size());
        ratePlanModelList.stream()
                .filter(rp -> rp.getDeactivationFlag() == null)
                .forEach(rp -> rp.setDeactivationFlag(0));
        ratePlanModelList.stream()
                .filter(rp -> rp.getActivationSourceFlag() == null)
                .forEach(rp -> rp.setActivationSourceFlag(0));
        ratePlanDao.ratePlansBatchUpdate(ratePlanModelList);
    }

    private RatePlanModel getRatePlanByCode(String ratePlanCode){
        RatePlanModel ratePlanModel = ratePlanDao.getRatePlanByCode(ratePlanCode);
        if (ratePlanModel == null) {
            DailyAppLogger.DEBUG_LOGGER.error("Rate Plan Code Does NOT Exist");
            DailyAppLogger.ERROR_LOGGER.error("Rate Plan Code Does NOT Exist");
            throw new LookupException(ErrorCodes.ERROR.RATE_PLAN_DOES_NOT_EXIST, Defines.SEVERITY.ERROR);
        }
        DailyAppLogger.DEBUG_LOGGER.info("Retrieved rate-plan by Code: " +  ratePlanModel);
        return ratePlanModel;
    }

        /*-----------Rate Plan Groups--------------*/
    public List<RatePlanGroupModel> getAllRatePlanGroups(){
        List<RatePlanGroupModel> groups = ratePlanDao.getAllRatePlanGroups();
        if(groups == null)
        {
            DailyAppLogger.DEBUG_LOGGER.error("Rate Plan GROUP Does NOT Exist");
            DailyAppLogger.ERROR_LOGGER.error("Rate Plan GROUP Does NOT Exist");
            throw new LookupException(ErrorCodes.ERROR.GROUPS_NOT_FOUND, Defines.SEVERITY.ERROR);
        }
        DailyAppLogger.DEBUG_LOGGER.debug("#Retrieved RPG = " + groups.size() + " groups");
        return groups;
    }

    public RatePlanGroupModel getRatePlanGroupByKey(Integer key){
        RatePlanGroupModel group = ratePlanDao.getRatePlanGroupByKey(key);
        if (group == null) {
            DailyAppLogger.DEBUG_LOGGER.error("GROUP KEY DOES NOT EXIST");
            DailyAppLogger.ERROR_LOGGER.error("GROUP KEY DOES NOT EXIST");
            throw new LookupException(ErrorCodes.ERROR.GROUPS_NOT_FOUND, Defines.SEVERITY.ERROR);
        }
        DailyAppLogger.DEBUG_LOGGER.info(group);
        DailyAppLogger.DEBUG_LOGGER.debug("RatePlanGroup[{}] has {} rate-plans assigned", key, group.getRatePlans().size());
        return group;
    }

    public Integer addRatePlanGroup(RatePlanGroupModel groupModel){
        Integer key = ratePlanDao.addRatePlanGroup(groupModel);
        if(key == null) {
            DailyAppLogger.DEBUG_LOGGER.error("Rate Plan Group Cannot Be Added");
            DailyAppLogger.ERROR_LOGGER.error("Rate Plan Group Cannot Be Added");
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR);
        }
        groupModel.setRatePlanGroupKey(key);

        DailyAppLogger.DEBUG_LOGGER.debug("Start Assigning RPG Key for each RP -> size = {} rate-plans", groupModel.getRatePlans().size());
        IntStream.range(0, groupModel.getRatePlans().size()).forEach(i -> groupModel.getRatePlans().get(i).setRatePlanGroupKey(key));

        DailyAppLogger.DEBUG_LOGGER.debug("Start Updating {} Rate Plans...", groupModel.getRatePlans().size());
        ratePlanDao.ratePlansGroupKeysBatchUpdate(groupModel.getRatePlans());

        return key;
    }

    public void updateRatePlanGroupAndRatePlans(RatePlanGroupModel groupModel){
        DailyAppLogger.DEBUG_LOGGER.debug("RPG key = {}", groupModel.getRatePlanGroupKey());
        getRatePlanGroupByKey(groupModel.getRatePlanGroupKey());

        DailyAppLogger.DEBUG_LOGGER.debug("Removing the assigned Rate-Plans if exists");
        ratePlanDao.setRatePlanGroupKeyInRatePlanByNull(groupModel.getRatePlanGroupKey());

        DailyAppLogger.DEBUG_LOGGER.debug("Updating Rate Plan Group");
        ratePlanDao.updateRatePlanGroup(groupModel);

        DailyAppLogger.DEBUG_LOGGER.debug("[RPG] number of new assigned #RatePlans = {}", groupModel.getRatePlans().size());
        IntStream.range(0, groupModel.getRatePlans().size()).forEach(i -> groupModel.getRatePlans().get(i).setRatePlanGroupKey(groupModel.getRatePlanGroupKey()));
        ratePlanDao.ratePlansGroupKeysBatchUpdate(groupModel.getRatePlans());
    }

    public void deleteRatePlanGroup(Integer ratePlanGroupKey){
        DailyAppLogger.DEBUG_LOGGER.debug("RPG key = {}", ratePlanGroupKey);
        getRatePlanGroupByKey(ratePlanGroupKey);

        DailyAppLogger.DEBUG_LOGGER.debug("Removing the assigned Rate-Plans if exists");
        ratePlanDao.setRatePlanGroupKeyInRatePlanByNull(ratePlanGroupKey);

        DailyAppLogger.DEBUG_LOGGER.debug("Deleting Rate Plan Group...");
        ratePlanDao.deleteRatePlanGroup(ratePlanGroupKey);
    }
}
