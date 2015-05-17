/*
 *  DBHandler.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.db.handlers;

import com.mysql.jdbc.JDBC4PreparedStatement;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.exceptions.db.DBAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 06-feb-2013
 */
public abstract class DBHandler {

    private DBConnection dbConnection;
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DBHandler.class);
    protected static final Boolean WITH_GENERATED_KEYS = true, WITHOUT_GENERATED_KEYS = false;

    /**
     * Creates a new instance of
     * <code>DBHandler</code> without params.
     */
    public DBHandler() {
    }

    public DBHandler(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public DBConnection getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void closeDbConnection() throws DBAccessException {
        this.dbConnection.closeConnection();
    }

    protected void addBatch(PreparedStatement statement, List values) throws DBAccessException {
        try {
            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }
            statement.addBatch();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }

    }

    protected int[] executeBatch(PreparedStatement statement) throws DBAccessException {
        try {
//            logger.info(("executeBatch: " + statement));
            return statement.executeBatch();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            logger.error("Error in '" + dbConnection.getDbCredentials().getTicker() + "' executeBatch: " + statement);
            throw new DBAccessException(ex);
        }
    }

    public void clearDBCache() throws DBAccessException {
        final String sqlCommand = "RESET QUERY CACHE;";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(sqlCommand);
            resultSet = execute(statement);
        } catch (DBAccessException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected ResultSet execute(PreparedStatement statement) throws DBAccessException {
        try {

            ResultSet result;
//            logger.info(("execute: " + statement));
            if (this.dbConnection.profilingMode) {
                long start;
                long end;
                start = System.nanoTime();
                result = statement.executeQuery();
                end = System.nanoTime();
//                logger.info(("execute: " + statement));
                Long time = (end - start);
//                time=TimeUnit.MILLISECONDS.convert(time, TimeUnit.NANOSECONDS);
                String queryName = ((JDBC4PreparedStatement) statement).getPreparedSql();
                if (this.dbConnection.queryCounter.containsKey(queryName)) {
                    Map<String, Long> data = this.dbConnection.queryCounter.get(queryName);
                    data.put("totaltime", data.get("totaltime") + time);
                    Long c = data.get("counter");
                    data.put("counter", ++c);
                    data.put("avg", data.get("totaltime") / data.get("counter"));
                } else {
                    Map<String, Long> queryData = new HashMap<String, Long>();
                    queryData.put("time", time);
                    queryData.put("totaltime", time);
                    queryData.put("counter", (long) 1);
                    queryData.put("avg", time);
                    this.dbConnection.queryCounter.put(queryName, queryData);
                }
            } else {
                result = statement.executeQuery();
            }
            return result;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            logger.error("Error in '" + dbConnection.getDbCredentials().getTicker() + "' executeStatement: " + statement);
            throw new DBAccessException(ex);
        }
    }

/*    protected ResultSet execute(PreparedStatement statement) throws DBAccessException {
        try {
//            logger.info(("execute: " + statement));
            return statement.executeQuery();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }*/

    protected int executeUpdate(PreparedStatement statement) throws DBAccessException {
        try {
            return statement.executeUpdate();
        } catch (Exception ex) {
            logger.error(ex);
            logger.error("Error in '" + dbConnection.getDbCredentials().getTicker() + "' executeUpdate: " + statement);
            throw new DBAccessException(ex);
        }
    }

    protected PreparedStatement prepareStatement(final String tableName, List values) throws DBAccessException {
        try {
            PreparedStatement statement = getDbConnection().getConnection().prepareStatement(tableName);
            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    statement.setObject(i + 1, values.get(i));
                }
            }
            return statement;
        } catch (Exception ex) {
            logger.error(ex.getMessage() + " sql:" + tableName);
            throw new DBAccessException(ex);
        }
    }

    protected PreparedStatement prepareStatement(final String tableName, List values, Boolean returnGeneratedKeys) throws DBAccessException {
        try {
            PreparedStatement statement = returnGeneratedKeys
                    ? getDbConnection().getConnection().prepareStatement(tableName, PreparedStatement.RETURN_GENERATED_KEYS)
                    : getDbConnection().getConnection().prepareStatement(tableName);
            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    statement.setObject(i + 1, values.get(i));
                }
            }
            return statement;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected PreparedStatement prepareStatement(final String tableName) throws DBAccessException {
        try {
            return getDbConnection().getConnection().prepareStatement(tableName);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected PreparedStatement prepareStatement(final String tableName, HashMap<Integer, String> values) throws DBAccessException {
        try {
            PreparedStatement statement = getDbConnection().getConnection().prepareStatement(tableName);
            if (values != null) {
                for (Entry<Integer, String> item : values.entrySet()) {
                    statement.setObject(item.getKey(), item.getValue());
                }
            }
            return statement;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }

    }

    protected boolean next(ResultSet resultSet) throws DBAccessException {
        try {
            return resultSet.next();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected boolean last(ResultSet resultSet) throws DBAccessException {
        try {
            return resultSet.last();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected int getRow(ResultSet resultSet) throws DBAccessException {
        try {
            return resultSet.getRow();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected int getInt(ResultSet resultSet, int index) throws DBAccessException {
        try {
            return resultSet.getInt(index);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected int getInt(ResultSet resultSet, String columnName) throws DBAccessException {
        try {
            return resultSet.getInt(columnName);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected String getString(ResultSet resultSet, int index) throws DBAccessException {
        try {
            return resultSet.getString(index);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected String getString(ResultSet resultSet, String columnName) throws DBAccessException {
        try {
            return resultSet.getString(columnName);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected Float getFloat(ResultSet resultSet, String columnName) throws DBAccessException {
        try {
            return resultSet.getFloat(columnName);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected Float getFloat(ResultSet resultSet, int index) throws DBAccessException {
        try {
            return resultSet.getFloat(index);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected Date getDate(ResultSet resultSet, int index) throws DBAccessException {
        try {
            return resultSet.getDate(index);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected Date getDate(ResultSet resultSet, String columnName) throws DBAccessException {
        try {
            return resultSet.getDate(columnName);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected Boolean getBoolean(ResultSet resultSet, String columnName) throws DBAccessException {
        try {
            return resultSet.getBoolean(columnName);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected Boolean getBoolean(ResultSet resultSet, int index) throws DBAccessException {
        try {
            return resultSet.getBoolean(index);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected Byte getByte(ResultSet resultSet, String columnName) throws DBAccessException {
        try {
            return resultSet.getByte(columnName);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected Byte getByte(ResultSet resultSet, int index) throws DBAccessException {
        try {
            return resultSet.getByte(index);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected Timestamp getTimestamp(ResultSet resultSet, String columnName) throws DBAccessException {
        try {
            return resultSet.getTimestamp(columnName);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected Timestamp getTimestamp(ResultSet resultSet, int index) throws DBAccessException {
        try {
            return resultSet.getTimestamp(index);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DBAccessException(ex);
        }
    }

    protected Timestamp tryToGetTimestamp(ResultSet resultSet, int index) {
        try {
//            logger.debug(index);
            return resultSet.getTimestamp(index);
        } catch (SQLException ex) {
//            for(StackTraceElement elem: ex.getStackTrace()){
//            logger.error("StackTraceElement: "+elem);
//            }
//            logger.error(ex);
        }
        return null;
    }

    protected Timestamp tryToGetTimestamp(ResultSet resultSet, String att) {
        try {
//            logger.debug(att);
            return resultSet.getTimestamp(att);
        } catch (SQLException ex) {
//            for (StackTraceElement elem : ex.getStackTrace()) {
//                logger.error("StackTraceElement: " + elem);
//            }
            logger.error(ex);
        }
        return null;
    }


    protected ResultSet getGeneratedKeys(PreparedStatement statement) throws DBAccessException {
        try {
            return statement.getGeneratedKeys();
        } catch (SQLException ex) {
            logger.error(ex);
            throw new DBAccessException(ex);
        }
    }

    protected String getTranslation(ResultSet resultSet, int translatedIndex, int normalIndex) throws DBAccessException {
        String translatedText = getString(resultSet, translatedIndex);
        String normalText = getString(resultSet, normalIndex);
        return (translatedText != null && !translatedText.trim().isEmpty()) ? translatedText : normalText;
    }

    protected String getTranslation(ResultSet resultSet, String translatedColumn, String normalColumn) throws DBAccessException {
        String translatedText = getString(resultSet, translatedColumn);
        String normalText = getString(resultSet, normalColumn);
        return (translatedText != null && !translatedText.trim().isEmpty()) ? translatedText : normalText;
    }
}