package com.asset.loggingService.dao.extractors;

import com.asset.loggingService.defines.DatabaseStructs;
import com.asset.loggingService.model.TransactionActionModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class AllTransactionsActionExtractor implements ResultSetExtractor<HashMap<Integer,TransactionActionModel>> {
    @Override
    public HashMap<Integer, TransactionActionModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<Integer,TransactionActionModel> transactionsAction =null;
        TransactionActionModel  sqlIdActionNameColumns =null;
        while (resultSet.next()) {
            if (transactionsAction== null){
                transactionsAction = new HashMap<>();
            }
            sqlIdActionNameColumns = new TransactionActionModel(resultSet.getString(DatabaseStructs.TX_ACTIONS.ACTION_NAME),resultSet.getInt(DatabaseStructs.TX_ACTIONS.SQL_ID));
            transactionsAction.put(resultSet.getInt(DatabaseStructs.TX_ACTIONS.ID),sqlIdActionNameColumns);
        }

        return transactionsAction;
    }
}
