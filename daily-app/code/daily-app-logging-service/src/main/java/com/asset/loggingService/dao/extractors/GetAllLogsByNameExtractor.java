package com.asset.loggingService.dao.extractors;

import com.asset.loggingService.defines.DatabaseStructs;
import com.asset.loggingService.model.TransactionUserDetails;
import com.asset.loggingService.model.TrxUserDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GetAllLogsByNameExtractor implements ResultSetExtractor<List<TrxUserDto>> {
    @Override
    public List<TrxUserDto> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        TrxUserDto TrxUserDto = null;
        TransactionUserDetails TransactionUserDetails;
        ArrayList<TransactionUserDetails> priceGroupModels  = null;
        List<TrxUserDto> trxUserDtoArrayList = new ArrayList<>();
        Set<Integer> ids= new HashSet<>();
        while (resultSet.next()) {

            int txId= resultSet.getInt(DatabaseStructs.TX_USER_ACTIONS.ID) ;
        if(!ids.contains(txId)) {
            TrxUserDto = new TrxUserDto();
            priceGroupModels =new ArrayList<>();
            TrxUserDto.setId(txId);
            TrxUserDto.setActionName(resultSet.getString(DatabaseStructs.TX_USER_ACTIONS.ACTION_NAME));
            TrxUserDto.setRequestId(resultSet.getString(DatabaseStructs.TX_USER_ACTIONS.REQUEST_ID));
            TrxUserDto.setUserId(resultSet.getInt(DatabaseStructs.TX_USER_ACTIONS.USER_ID));
            TrxUserDto.setPageName(resultSet.getString(DatabaseStructs.TX_USER_ACTIONS.PAGE_NAME));
            TrxUserDto.setSessionId(resultSet.getString(DatabaseStructs.TX_USER_ACTIONS.SESSION_ID));
            TrxUserDto.setRequestBody(resultSet.getString(DatabaseStructs.TX_USER_ACTIONS.REQUEST_BODY));
            TrxUserDto.setResponseBody(resultSet.getString(DatabaseStructs.TX_USER_ACTIONS.RESPONSE_BODY));
            TrxUserDto.setDate(resultSet.getString(DatabaseStructs.TX_USER_ACTIONS.CREATION_DATE));
            TrxUserDto.setUserName(resultSet.getString(DatabaseStructs.TX_USER_ACTIONS.USER_NAME));
            TrxUserDto.setObjectIdentifier(resultSet.getString(DatabaseStructs.TX_USER_ACTIONS.OBJECT_IDENTIFIER));

            TrxUserDto.setTransactionUserDetails(priceGroupModels);
            ids.add(TrxUserDto.getId());
            trxUserDtoArrayList.add(TrxUserDto);

        }

            Integer RatePlanId = resultSet.getInt(DatabaseStructs.TX_USER_ACTIONS_DETAILS.HEADER_ID);

            if (!RatePlanId.equals(0)) {
                TransactionUserDetails = new TransactionUserDetails();
                TransactionUserDetails.setParamName(resultSet.getString(DatabaseStructs.TX_USER_ACTIONS_DETAILS.PARAM_NAME));
                TransactionUserDetails.setNewVal(resultSet.getString(DatabaseStructs.TX_USER_ACTIONS_DETAILS.NEW_VAL));
                TransactionUserDetails.setOldVal(resultSet.getString(DatabaseStructs.TX_USER_ACTIONS_DETAILS.OLD_VAL));
                priceGroupModels.add(TransactionUserDetails);

            }
        }
        return trxUserDtoArrayList;
    }
}
