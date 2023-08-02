package com.asset.dailyappnotificationservice.controllers;

import com.asset.dailyappnotificationservice.defines.Defines;
import com.asset.dailyappnotificationservice.defines.ErrorCodes;
import com.asset.dailyappnotificationservice.logger.DailyAppLogger;
import com.asset.dailyappnotificationservice.models.NotificationModel;
import com.asset.dailyappnotificationservice.models.responses.BaseResponse;
import com.asset.dailyappnotificationservice.services.NotificationsService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = Defines.CONTEXT_PATHS.NOTIFICATIONS_SERVICE)
public class NotificationsController
{
    @Autowired
    NotificationsService notificationsService;

    @RequestMapping(value = Defines.WEB_ACTIONS.GET_ALL, method = RequestMethod.POST)
    public BaseResponse<List<NotificationModel>> getAllNotifications(){
        DailyAppLogger.DEBUG_LOGGER.debug("-------------------------GET-ALL Notifications Request Has Been Received-------------------------");
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        List<NotificationModel> payload = notificationsService.getAllNotifications();
        ThreadContext.remove(traceId);
        DailyAppLogger.DEBUG_LOGGER.debug("-----------------------------------------END OF REQUEST------------------------------------------");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, traceId, payload);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.UPDATE, method = RequestMethod.POST)
    public BaseResponse<List<NotificationModel>> updateActionDone(@RequestBody NotificationModel expireNotificationRequest){
        DailyAppLogger.DEBUG_LOGGER.debug("-------------------------Update Action-Done Request Has Been Received-------------------------");
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        notificationsService.expireNotification(expireNotificationRequest);
        ThreadContext.remove(traceId);
        DailyAppLogger.DEBUG_LOGGER.debug("-----------------------------------------END OF REQUEST------------------------------------------");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, traceId, null);
    }
}
