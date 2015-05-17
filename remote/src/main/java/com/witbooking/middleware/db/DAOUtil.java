/*
 *  DAOUtil.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.db;

import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Utility class for DAO's. This class contains commonly used DAO logic which is
 * been refactored in single static methods. As far it contains a
 * PreparedStatement values setter and several quiet close methods.
 *
 * @author BalusC, Christian Delgado
 * @version 1.0
 * @date 11-jun-2013
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public final class DAOUtil {

    /**
     * Object used to report on the Server's Log using Apache log4j.
     */
    private static final Logger logger = Logger.getLogger(DAOUtil.class);

    private DAOUtil() {
        // Utility class, hide constructor.
    }

    /**
     * Returns a PreparedStatement of the given connection, set with the given
     * SQL query and the given parameter values.
     *
     * @param connection The Connection to create the PreparedStatement from.
     * @param sql The SQL query to construct the PreparedStatement with.
     * @param returnGeneratedKeys Set whether to return generated keys or not.
     * @param values The parameter values to be set in the created
     * PreparedStatement.
     * @throws SQLException If something fails during creating the
     * PreparedStatement.
     */
    //TODO: adapt to JDK 1.6
//   public static PreparedStatement prepareStatement(Connection connection, String sql, boolean returnGeneratedKeys, Object... values)
//           throws SQLException {
//      PreparedStatement preparedStatement = connection.prepareStatement(sql,
//              returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
//      setValues(preparedStatement, values);
//      return preparedStatement;
//   }
    /**
     * Set the given parameter values in the given PreparedStatement.
     *
     * @param connection The PreparedStatement to set the given parameter values
     * in.
     * @param values The parameter values to be set in the created
     * PreparedStatement.
     * @throws SQLException If something fails during setting the
     * PreparedStatement values.
     */
    //TODO: adapt to JDK 1.6
//   public static void setValues(PreparedStatement preparedStatement, Object... values)
//           throws SQLException {
//      for (int i = 0; i < values.length; i++) {
//         preparedStatement.setObject(i + 1, values[i]);
//      }
//   }

    /**
     * Converts the given java.util.Date to java.sql.Date.
     *
     * @param date The java.util.Date to be converted to java.sql.Date.
     * @return The converted java.sql.Date.
     */
    public static Date toSqlDate(java.util.Date date) {
        return (date != null) ? new Date(date.getTime()) : null;
    }

    /**
     * Converts the given to java.sql.Date to java.util.Date.
     *
     * @param date The java.util.Date to be converted to java.sql.Date.
     * @return The converted java.sql.Date.
     */
    public static java.util.Date toUtilDate(Date date) {
        return (date != null) ? new java.util.Date(date.getTime()) : null;
    }

    /**
     * Quietly close the Connection. Any errors will be printed to the stderr.
     *
     * @param connection The Connection to be closed quietly.
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                logger.error("[DBConnection] JDBC Closing Connection Errors. " + ex.getMessage());
            }
        }
    }

    /**
     * Quietly close the Statement. Any errors will be printed to the stderr.
     *
     * @param statement The Statement to be closed quietly.
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                logger.error("[DBConnection] JDBC Closing Statement Errors. " + ex.getMessage());
            }
        }
    }

    /**
     * Quietly close the ResultSet. Any errors will be printed to the stderr.
     *
     * @param resultSet The ResultSet to be closed quietly.
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                logger.error("[DBConnection] JDBC ResultSet Statement Errors. " + ex.getMessage());
            }
        }
    }

    /**
     * Quietly close the Connection and Statement. Any errors will be printed to
     * the stderr.
     *
     * @param connection The Connection to be closed quietly.
     * @param statement  The Statement to be closed quietly.
     */
    public static void close(Connection connection, Statement statement) {
        close(statement);
        close(connection);
    }

    /**
     * Quietly close the Statement and ResultSet. Any errors will be printed to
     * the stderr.
     *
     * @param statement The Statement to be closed quietly.
     * @param resultSet The ResultSet to be closed quietly.
     */
    public static void close(Statement statement, ResultSet resultSet) {
        close(resultSet);
        close(statement);
    }

    /**
     * Quietly close the Connection, Statement and ResultSet. Any errors will be
     * printed to the stderr.
     *
     * @param connection The Connection to be closed quietly.
     * @param statement  The Statement to be closed quietly.
     * @param resultSet  The ResultSet to be closed quietly.
     */
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        close(resultSet);
        close(statement);
        close(connection);
    }

    public static void close(DBConnection dbConnection) {
        if (dbConnection != null) {
            try {
                dbConnection.closeConnection();
            } catch (Exception ex) {
                logger.debug(ex);
            }
        }
    }
}
