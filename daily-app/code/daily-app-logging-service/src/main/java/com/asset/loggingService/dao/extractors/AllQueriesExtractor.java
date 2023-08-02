package com.asset.loggingService.dao.extractors;

import com.asset.loggingService.defines.DatabaseStructs;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Component
public class AllQueriesExtractor implements ResultSetExtractor<HashMap<Integer, String>> {
    @Override
    public HashMap<Integer, String> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<Integer, String> Queries = new HashMap();
        while (resultSet.next()) {
            Queries.put(resultSet.getInt(DatabaseStructs.DAILY_QUERIES.ID),resultSet.getString(DatabaseStructs.DAILY_QUERIES.SQL));
        }
        return Queries;
    }
}
