/*
 *  SQLInstructions.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.db.handlers.sqlinstructions;

import com.google.common.base.Joiner;
import com.witbooking.middleware.resources.MiddlewareProperties;
import org.apache.log4j.Logger;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 31-may-2013
 */
public class SQLInstructions {

    //queries
    //InventoryDBHandler
    public static final class InventoryDBHandler {

        public static final String SELECT_INVENTORY_TICKER_FROM_DISCOUNT_ID = "SELECT t.codigo FROM tiposhabs as t " +
                "LEFT JOIN promocionestiposhabs as p ON t.id=p.tiposhab_id WHERE p.promocione_id=?; ";

        public static final String SELECT_INVENTORIES_AND_DISCOUNTS_LINKED = "SELECT DISTINCT p.promocione_id, " +
                "p.tiposhab_id FROM promocionestiposhabs as p ";

        public static final String SELECT_INVENTORIES_AND_SERVICES_LINKED = "SELECT DISTINCT ex.extra_id, " +
                "ex.tiposhab_id FROM extrastiposhabs as ex ";

        public static final String SELECT_CONDITIONS_AND_PAYMENTS_LINKED = "SELECT DISTINCT ds.documento_id, " +
                "ds.sistemapago_id FROM documentossistemaspagos as ds ";

        public static final String SELECT_INVENTORY =
                "SELECT h.id, h.codigo, h.name, h.establecimiento_id, h.fcreacion, "
                        + " h.lastmodified, h.activa, h.codigo_acceso, h.ftarifa, h.ftarifa_tip,"
                        + " h.fdisponibilidad, h.fdisponibilidad_tip, h.festancia_minima, h.festancia_minima_tip, h.festancia_maxima, "
                        + " h.festancia_maxima_tip, h.frelease_minimo, h.frelease_minimo_tip, h.frelease_maximo, h.frelease_maximo_tip,"
                        + " h.fbloqueo, h.fbloqueo_tip, h.dias_entrada, h.dias_salida, h.impuesto_id, "
                        + " h.alojamientotipo_id, h.alojamientoconfiguracion_id, h.complemento_id, h.condiciones_id, trans.nombre_trans ,"
                        + " h.fechaini , h.fechafin , h.rack, h.deleted "
                        + " FROM tiposhabs as h "
                        + " LEFT JOIN "
                + "       (SELECT i.foreign_key as foreign_key, i.content AS nombre_trans "
                + "       FROM i18n AS i "
                + "       WHERE i.locale=? "
                + "       AND i.model='Tiposhab' "
                + "       AND i.field = 'name') AS trans "
                        + " ON trans.foreign_key=h.id ";
        public static final String SELECT_DISCOUNT = "SELECT p.id, p.codigo, p.name, p.descripcion, p.timestamp, "
                + "p.porcentaje, p.fbloqueo, p.fbloqueo_tip, p.fechaini, p.fechafin, "
                + "p.fechainicontratacion, p.fechafincontratacion, p.festancia_minima, p.festancia_minima_tip, p.festancia_maxima, "
                + "p.festancia_maxima_tip, p.frelease_minimo, p.frelease_minimo_tip, p.frelease_maximo, p.frelease_maximo_tip, "
                + "p.codigopromocional, p.activa, trans.name_trans, trans.desc_trans, p.orden, "
                + "p.precio "
                + "FROM promociones as p "
                + "LEFT JOIN "
                + "	(SELECT i.foreign_key, "
                + "	MAX(CASE WHEN i.field = 'name' THEN i.content ELSE NULL END) AS name_trans, "
                + "      MAX(CASE WHEN i.field = 'descripcion' THEN i.content ELSE NULL END) AS desc_trans "
                + "	FROM i18n AS i "
                + "	WHERE i.locale=? AND i.model='Promocione' "
                + "	GROUP BY i.foreign_key) AS trans "
                + "ON trans.foreign_key=p.id ";
        public static final String SELECT_TAXES = "SELECT c.id, c.nombre, c.valor, t.nombre AS ticker, "
                + "trans.nombre_trans "
                + "FROM impuestos AS c "
                + "LEFT JOIN "
                + "impuestos_tipos AS t "
                + "ON c.tipo_id=t.id "
                + "LEFT JOIN "
                + "(SELECT i.foreign_key as foreign_key, i.content AS nombre_trans "
                + "FROM i18n AS i "
                + "WHERE i.locale=? "
                + "AND i.model='Impuesto' "
                + "AND i.field = 'nombre') AS trans "
                + "ON trans.foreign_key=c.id ";

        public static final class TableAlojamiento_tipos {
            public static final String CREACION = "creacion";
        }

        public static final String SELECT_ACCOMMODATION = "SELECT "
                + "at.id, at.ticker, at.nombre, at.descripcion, at.creacion as " + TableAlojamiento_tipos.CREACION
                + " , at.modificacion, at.color, at.visible, trans.nombre_trans, trans.desc_trans, "
                + " at.orden, at.deleted "
                + "FROM alojamiento_tipos AS at "
                + "LEFT JOIN "
                + "(SELECT i.foreign_key as foreign_key, "
                + "MAX(CASE WHEN i.field = 'descripcion' THEN i.content ELSE NULL END) AS desc_trans, "
                + "MAX(CASE WHEN i.field = 'nombre' THEN i.content ELSE NULL END) AS nombre_trans "
                + "FROM i18n AS i "
                + "WHERE i.locale=? AND i.model='AlojamientoTipo' "
                + "GROUP BY i.foreign_key) AS trans "
                + "ON trans.foreign_key=at.id ";
        public static final String SELECT_CONFIGURATION = "SELECT ac.id, ac.ticker, ac.nombre, ac.creacion, ac.modificacion, "
                + "ac.adultos, ac.ninos, ac.bebes, trans.nombre_trans "
                + "FROM alojamiento_configuraciones AS ac "
                + "LEFT JOIN "
                + "(SELECT i.foreign_key as foreign_key, i.content AS nombre_trans "
                + "FROM i18n AS i "
                + "WHERE i.locale=? "
                + "AND i.model='AlojamientoConfiguracione' "
                + "AND i.field = 'nombre') AS trans "
                + "ON trans.foreign_key=ac.id ";
        public static final String SELECT_MEAL_PLAN = "SELECT c.id, c.ticker, c.nombre, c.ultimamodif, trans.nombre_trans, "
                + "c.orden "
                + "FROM complementos as c "
                + "LEFT JOIN "
                + "(SELECT i.foreign_key as foreign_key, i.content AS nombre_trans "
                + "FROM i18n AS i "
                + "WHERE i.locale=? "
                + "AND i.model='Complemento' "
                + "AND i.field = 'nombre') AS trans "
                + "ON trans.foreign_key=c.id ";
        public static final String SELECT_CONDITION = "SELECT d.id , d.codigo , d.cobroanticipado , d.importeminimo , d.timestamp, "
                + " d.color, d.name, trans.name_trans, d.texto, trans.texto_trans, "
                + " d.html_entrada, trans.html_entrada_trans, d.html_salida, trans.html_salida_trans, d.html_cancelaciones, "
                + " trans.html_cancelaciones_trans, d.html_cond_ninos, trans.html_cond_ninos_trans, d.html_mascotas , trans.html_mascotas_trans, "
                + " d.html_grupos, trans.html_grupos_trans, d.html_info_adicional, trans.html_info_adicional_trans, d.orden, "
                + " d.esCobrarPrimeraNoche "
                //+ "trans., trans. , trans. , trans. , trans. , "
                + " FROM documentos AS d "
                + "LEFT JOIN	"
                + "(SELECT i.foreign_key as foreign_key, "
                + "MAX(CASE WHEN i.field = 'html_entrada' THEN i.content ELSE NULL END) AS html_entrada_trans, "
                + "MAX(CASE WHEN i.field = 'html_salida' THEN i.content ELSE NULL END) AS html_salida_trans, "
                + "MAX(CASE WHEN i.field = 'html_cancelaciones' THEN i.content ELSE NULL END) AS html_cancelaciones_trans, "
                + "MAX(CASE WHEN i.field = 'html_cond_ninos' THEN i.content ELSE NULL END) AS html_cond_ninos_trans, "
                + "MAX(CASE WHEN i.field = 'html_mascotas' THEN i.content ELSE NULL END) AS html_mascotas_trans, "
                + "MAX(CASE WHEN i.field = 'html_grupos' THEN i.content ELSE NULL END) AS html_grupos_trans, "
                + "MAX(CASE WHEN i.field = 'html_info_adicional' THEN i.content ELSE NULL END) AS html_info_adicional_trans, "
                + "MAX(CASE WHEN i.field = 'texto' THEN i.content ELSE NULL END) AS texto_trans, "
                + "MAX(CASE WHEN i.field = 'name' THEN i.content ELSE NULL END) AS name_trans "
                + "FROM i18n AS i "
                + "WHERE i.locale=? AND i.model='Documento' "
                + "GROUP BY i.foreign_key) AS trans "
                + "ON trans.foreign_key=d.id ";
        public static final String SELECT_PAYMENT_TYPE = "SELECT pt.id , pt.ticker , pt.nombre , pt.textodescriptivo , pt.prcjpago, "
                + " pt.escobroanticipado, pt.activo, pt.creado, pt.modificado "
                + " FROM sistemaspago AS pt ";
        public static final String SELECT_SERVICE = "SELECT ex.id, ex.codigo, ex.name, ex.descripcion, ex.xdia, "
                + "ex.xpersona, ex.eleccionnumerica, ex.maxeleccionnumerica, ex.activa, ex.fechaini, "
                + "ex.fechafin, ex.ftarifa, ex.ftarifa_tip, ex.fbloqueo, ex.fbloqueo_tip, "
                + "ex.festancia_minima, ex.festancia_minima_tip, ex.festancia_maxima, ex.festancia_maxima_tip, ex.frelease_minimo, "
                + "ex.frelease_minimo_tip, ex.frelease_maximo, ex.frelease_maximo_tip, trans.name_trans, trans.descripcion_trans, "
                + "ex.orden, ex.obligatorio, ex.codigopromocional  "
                + "FROM extras AS ex "
                + "LEFT JOIN	"
                + "(SELECT i.foreign_key as foreign_key, "
                + "MAX(CASE WHEN i.field = 'descripcion' THEN i.content ELSE NULL END) AS descripcion_trans, "
                + "MAX(CASE WHEN i.field = 'name' THEN i.content ELSE NULL END) AS name_trans "
                + "FROM i18n AS i "
                + "WHERE i.locale=? AND i.model='Extra' "
                + "GROUP BY i.foreign_key) AS trans "
                + "ON trans.foreign_key=ex.id ";
        public static final String SELECT_ESTABLISHMENT = "SELECT es.id, es.ticker, es.nombre, es.descripcion, es.ciudad, "
                + "es.zona, es.pais, es.latitud, es.longitud, es.direccion, "
                + "es.telefono, es.emailadmin, es.emailsreservas, es.visible, es.valoracion, "
                + "es.tipo,  trans.nombre_trans, trans.desc_trans "
                + "FROM establecimientos AS es "
                + "LEFT JOIN "
                + "(SELECT i.foreign_key as foreign_key, "
                + "MAX(CASE WHEN i.field = 'descripcion' THEN i.content ELSE NULL END) AS desc_trans, "
                + "MAX(CASE WHEN i.field = 'nombre' THEN i.content ELSE NULL END) AS nombre_trans "
                + "FROM i18n AS i "
                + "WHERE i.locale=? AND i.model='Establecimiento' "
                + "GROUP BY i.foreign_key) AS trans "
                + "ON trans.foreign_key=es.id ";

        public static final String SELECT_MULTIMEDIA =
                "SELECT " + HotelConfigurationDBHandler.TableMultimedia.ATT_FILE_NAME +
                        " , " + HotelConfigurationDBHandler.TableMultimedia.ATT_TITLE +
                        " , " + HotelConfigurationDBHandler.TableMultimedia.ATT_DESCRIPTION +
                        " , " + HotelConfigurationDBHandler.TableMultimedia.ATT_ORDER +
                        " , trans.title_trans as title_trans, trans.description_trans as description_trans " +
                        " FROM " + HotelConfigurationDBHandler.TableMultimedia.TBL_NAME + " as m " +
                        "LEFT JOIN "
                        + "(SELECT i.foreign_key as foreign_key, "
                        + "MAX(CASE WHEN i.field = 'description' THEN i.content ELSE NULL END) AS description_trans, "
                        + "MAX(CASE WHEN i.field = 'title' THEN i.content ELSE NULL END) AS title_trans "
                        + "FROM i18n AS i "
                        + "WHERE i.locale=? AND i.model='Multimedia' "
                        + "GROUP BY i.foreign_key) AS trans "
                        + "ON trans.foreign_key=m.id " +
                        " WHERE m." + HotelConfigurationDBHandler.TableMultimedia.ATT_ENTITY + "=?" +
                        " AND m." + HotelConfigurationDBHandler.TableMultimedia.ATT_ID_ENTITY + "=?";

        public static final String SELECT_VARIABLES =
                "SELECT id, ticker, inventario_tipo_id, obsoleto FROM inventario_elementos " +
                        " WHERE inventario_tipo_id=5 AND obsoleto=0 ";
    }

    public static final class DailyValuesDBHandler {

        public static final String SELECT_VALUES =
                "SELECT inventario.valor, inventario_elementos.ticker, inventario_elementos.inventario_tipo_id, fechas.fecha  "
                        + "FROM inventario, inventario_elementos, fechas ";

        public static final String UPDATE_DAILY_VALUE_BY_TICKER = "UPDATE inventario i, inventario_elementos e, fechas f "
                + "SET i.valor=?, i.modificacion = ? , i.inicializado=0 "
                + "WHERE i.elemento_id = e.id "
                + "AND i.fecha_id = f.id "
                + "AND e.ticker=? "
                + "AND e.inventario_tipo_id=? "
                + "AND e.obsoleto=0 "
                + "AND f.fecha = ?;";
        public static final String INSERT_DAILY_VALUE_BY_TICKER = "INSERT INTO inventario (fecha_id, elemento_id, valor, modificacion,inicializado) "
                + "SELECT fechas.id, inventario_elementos.id, ?, ?, 0 "
                + "FROM fechas, inventario_elementos "
                + "WHERE inventario_elementos.ticker = ? "
                + "AND inventario_elementos.inventario_tipo_id=? "
                + "AND inventario_elementos.obsoleto =0 "
                + "AND fechas.fecha = ?";
        public static final String INSERT_NEW_DAYS = "INSERT INTO fechas (fecha) "
                + "SELECT alldate.Date "
                + "FROM (SELECT ? + INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY AS Date "
                + "from (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 "
                + "UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS a "
                + "cross join (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 "
                + "UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS b "
                + "cross join (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 "
                + "UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS c ) AS alldate "
                + "WHERE alldate.Date BETWEEN ? and ? "
                + "AND alldate.Date NOT IN (SELECT fecha FROM fechas WHERE fechas.fecha BETWEEN ? and ?) "
                + "ORDER BY alldate.Date ASC;";
        public static final String VALIDATE_IF_INVENTORY_EXISTS = "SELECT id FROM inventario_elementos "
                + "WHERE inventario_elementos.ticker=? "
                + "AND inventario_elementos.obsoleto=0 "
                + "AND inventario_elementos.inventario_tipo_id=?;";
        public static final String VALIDATE_IF_ALL_DAYS_IN_RANGE_EXISTS = "SELECT id, fecha FROM fechas "
                + "WHERE fechas.fecha BETWEEN ? AND ?"
                + "ORDER BY fechas.fecha ASC;";
    }

    public static final class ReservationDBHandler {

        public static final String TBL_RESERVATION = "reservas";
        public static final String ATT_STATUS_TBL_RESERVATION = "estado";
        public static final String ATT_ID_CONFIRMACION_TBL_RESERVATION = "idgeneradomulti";
        /**
         * Represents the room stay. In our database table is idgenerado.
         */

        public static final String RESERVATION_ID = "id";
        public static final String USER_RESERVATION_EMAIL_STATUS = "userReservationEmailStatus";
        public static final String HOTEL_RESERVATION_EMAIL_STATUS = "hotelReservationEmailStatus";




        public static final String ATT_ROOM_STAY_ID_CONFIRMATION_TBL_RESERVATION = "idgenerado";
        public static final String ATT_ULTIMA_MODIFICACION_TBL_RESERVATION = "ultimamodificacion";
        public static final String VAL_CANCEL_ATT_STATUS_TBL_RESERVATION = "cancelada";
        public static final String VAL_APPROVE_ATT_STATUS_TBL_RESERVATION = "reserva";
        public static final String SELECT_RESERVATION_CODES = "SELECT "
                + "idgeneradomulti, idgenerado "
                + "FROM " + TBL_RESERVATION + " ";
        public static final String SELECT_RESERVATION = "SELECT "
                + "id, idgenerado, idgeneradomulti, timestamp, ultimamodificacion, "
                + "estado, importe, idioma, fenvio_encuesta_post_estancia, motivocancelacion, "
                + "f_cancelacion, email, nombre, telefono, emailing, "
                + "cckind, ccholder, ccnumber, ccvalidto, ccsecuritycode, "
                + "fechaentrada, fechasalida, tiposhabs_id, habitaciones, resumen, "
                + "ip, pais, dni, fenvio_pre_stay, idafiliadoreducido, "
                + "agente_id, es_consultada_ws, importe_aloj_sin_impuestos, capacidad, depositofijo, "
                + "prcjpago, estadoampliado, idafiliado, COALESCE(ultimamodificacion, f_cancelacion, timestamp) AS ultima_fecha, commission, "
                + "es_solicita_cancelacion, codigo_aplicado, googleanalyticsok, tracking_trivago, reserva_cancelar_release, "
                + "importepago, sistemapago_id, control_confirmadas, referer "
                + "FROM " + TBL_RESERVATION + " ";
        public static final String INSERT_RESERVATION = "INSERT INTO reservas "
                + "(id, fechaentrada, fechasalida, nombre, email, "
                + "telefono, estado, estadoampliado, habitaciones, noches, "
                + "capacidad, bebes, importe, resumen, idgeneradomulti, "
                + "idgenerado, ccnumber, ccholder, ccvalidto, cckind, "
                + "ccsecuritycode, timestamp, tipohab, tiposhabs_id, tid, "
                + "idafiliado, idafiliadoreducido, importepago, prcjpago, depositofijo, "
                + "sistemapago_id, ip, googleanalyticsok, idioma, pais, "
                + "ultimamodificacion, agente_id, motivocancelacion, f_cancelacion, emailing, "
                + "fenvio_encuesta_post_estancia, es_consultada_ws, es_solicita_cancelacion, control_confirmadas, prcj_impuestos, "
                + "importe_promos_sin_impuestos, prcj_promos, importe_aloj_sin_impuestos, codigo_aplicado, tracking_trivago, "
                + "reserva_cancelar_release, dni, fenvio_pre_stay, commission, referer) " +
                "VALUES (?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?)";
        public static final String UPDATE_RESERVATION_BY_ROOM_STAY_ID_CONFIRMATION = "UPDATE " + TBL_RESERVATION + " SET "
                + "            fechaentrada = ? , fechasalida = ?, nombre = ?, email = ?, "
                + "telefono = ? , estado = ? , estadoampliado = ?, habitaciones = ? , noches = ?, "
                + "capacidad = ? , bebes = ? , importe =?, resumen = ? , idgeneradomulti = ? , "
                + "idgenerado = ?, ccnumber = ?, ccholder = ?, ccvalidto = ?, cckind =?, "
                + "ccsecuritycode = ?, timestamp = ?, tipohab = ?, tiposhabs_id = ?, tid = ?, "
                + "idafiliado = ?, idafiliadoreducido = ?, importepago = ?, prcjpago = ?, depositofijo = ?, "
                + "sistemapago_id = ?, ip = ?, googleanalyticsok = ?, idioma = ?, pais = ?, "
                + "ultimamodificacion = ?, agente_id =?, motivocancelacion = ?, f_cancelacion = ?, emailing = ?, "
                + "fenvio_encuesta_post_estancia = ?, es_consultada_ws = ?, es_solicita_cancelacion = ?, control_confirmadas = ?, prcj_impuestos = ?, "
                + "importe_promos_sin_impuestos = ?, prcj_promos = ?, importe_aloj_sin_impuestos = ?, codigo_aplicado = ?, tracking_trivago = ?, "
                + "reserva_cancelar_release = ?, dni = ?, fenvio_pre_stay = ?, commission = ? "
                + " WHERE " + ATT_ROOM_STAY_ID_CONFIRMATION_TBL_RESERVATION + " =? ";

        public static final String UPDATE_USER_RESERVATION_EMAIL_INFO = "UPDATE " + TBL_RESERVATION + " SET "
                +   USER_RESERVATION_EMAIL_STATUS +" =  ? "
                + " WHERE " + ATT_ID_CONFIRMACION_TBL_RESERVATION + " = ? ";

        public static final String UPDATE_HOTEL_RESERVATION_EMAIL_INFO = "UPDATE " + TBL_RESERVATION + " SET "
                +   HOTEL_RESERVATION_EMAIL_STATUS +" =  ? "
                + " WHERE " + ATT_ID_CONFIRMACION_TBL_RESERVATION + " = ? ";


        public static final String UPDATE_RESERVATION_EMAIL_INFO = "UPDATE " + TBL_RESERVATION + " SET "
                +   USER_RESERVATION_EMAIL_STATUS +" =  ? ,"
                +   HOTEL_RESERVATION_EMAIL_STATUS +" =  ? "
                + " WHERE " + ATT_ID_CONFIRMACION_TBL_RESERVATION + " = ? ";

        public static final String UPDATE_CANCELLATION_BY_ID_CONFIRMATION = "UPDATE " + TBL_RESERVATION
                + " SET "
                + ATT_STATUS_TBL_RESERVATION + "=? , "
                + ATT_ULTIMA_MODIFICACION_TBL_RESERVATION + "= NOW() "
                + " WHERE "
                + ATT_ID_CONFIRMACION_TBL_RESERVATION + "= ? ;";
        public static final String UPDATE_CANCELLATION_BY_ROOM_STAY_ID_CONFIRMATION = "UPDATE " + TBL_RESERVATION
                + " SET "
                + ATT_STATUS_TBL_RESERVATION + "=? , "
                + ATT_ULTIMA_MODIFICACION_TBL_RESERVATION + "= NOW() "
                + " WHERE "
                + ATT_ROOM_STAY_ID_CONFIRMATION_TBL_RESERVATION + "= ?";
    }
//   public static final String SELECT_DISCOUNT =
/*
    public static final class EmailDataDBHandler {
        public static final Logger logger = Logger.getLogger(com.witbooking.middleware.db.handlers.EmailDataDBHandler.class);

        public static final String DB_EMAILDATA_TICKER = "email_data";

        public static final String GET_EMAIL_DATA = "SELECT ed.reservationID,ed.hotelTicker,ed.emailID,ed.messageType," +
                "FROM email_data AS ed "
                + "WHERE ed.emailID = ? " ;

        public static final String INSERT_EMAIL_DATA =
                "INSERT INTO email_data (reservationID, hotelTicker, emailID, messageType) " +
                        " VALUES (?, ?, ?, ?);";

        public static final String INSERT_EMAIL_EVENT =
                "INSERT INTO email_data (reservationID, hotelTicker, emailID, messageType, eventType, eventData, eventCounter, timestamp, addedToReservation) " +
                        " VALUES (?, ?, ?, ?, ?);";

    }*/



    public static final class WitMetaDataDBHandler {

        public static final String DB_WITMETADATA_TICKER = "witmetadata";

        public static final String TBL_EMAIL_DATA="email_data";

        public static final String GET_EMAIL_DATA = "SELECT ed.reservationID,ed.hotelTicker,ed.emailID,ed.messageType,ed.lastEmailStatus " +
                "FROM email_data AS ed "
                + "WHERE ed.emailID = ? " ;

        public static final String INSERT_EMAIL_DATA =
                "INSERT INTO email_data (reservationID, hotelTicker, emailID, messageType, lastEmailStatus) " +
                        " VALUES (?, ?, ?, ?, ?);";

        public static final String INSERT_EMAIL_EVENT =
                "INSERT INTO email_event ( eventType, eventData, eventCounter, timestamp, addedToReservation, emailID) " +
                        " VALUES (?, ?, ?, ?, ?, ?);";

        public static final String UPDATE_EMAIL_DATA_STATUS = "UPDATE " + TBL_EMAIL_DATA + " SET "
                + " lastEmailStatus =  ? "
                + " WHERE emailID  = ? ";


        public static final String INSERT_DEEP_LINK_LOG = "INSERT INTO deeplinks "
                + "(direccion, url, hotel_id, check_in, check_out, adults, "
                + "language, currency, room_type, tracking_id, bucket) "
                + "VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";
        public static final String HOTEL_TICKER = "hotelTicker";
        public static final String CHANNEL_HOTEL_TICKER = "channel_hotel_ticker";
        public static final String GET_HOTEL_TICKER_FROM_CHANNEL_HOTEL_TICKER =
                "SELECT ch.hotel_ticker as " + HOTEL_TICKER + " FROM channels_hotels AS ch, "
                        + " channels AS c WHERE ch.channel = c.ticker AND c.ticker= ? AND ch.channel_hotel_ticker=?;";

        public static final String GET_CHANNEL_HOTEL_TICKER_FROM_HOTEL_TICKER =
                "SELECT ch.channel_hotel_ticker as " + CHANNEL_HOTEL_TICKER + " FROM channels_hotels AS ch, "
                        + " channels AS c WHERE ch.channel = c.ticker AND c.ticker= ?  AND ch.hotel_ticker=?;";


        public static class TableTripAdvisorReviewExpress {
            public static final String TBL_NAME = "trip_advisor_review_express";
            public static final String HOTEL_TICKER = "hotel_ticker";
            public static final String RESERVATION_ID = "idgeneradomulti";
            public static final String TRIP_ADVISOR_ID = "trip_advisor_id";
            public static final String ID = "id";
        }

        public static final class TableChannels {
            public static final String TBL_NAME = "channels";
            public static final String COMMISSION = "commission";
            public static final String TICKER = "ticker";
        }

        public static final class TableInstanciasAplicaciones {
            public static final String TBL_NAME = "instanciasaplicaciones";
            public static final String ATT_ID = "id";
            public static final String ATT_TICKER = "identificador";
            public static final String ATT_CHECK_AGREGADOR = "es_agregador_dummy";
            public static final String ATT_CHECK_DEMO = "es_demo";
            public static final String ATT_HAS_TRANSFERS = "active_transfers";
        }

        public static final class TableChain {
            public static final String TBL_NAME = "chain";
            public static final String ATT_CHAIN = "chain";
            public static final String ATT_HOTEL = "hotel";
            public static final String ATT_ID = "id";
        }

        public static final class GoogleBids {
            public static final String TBL_NAME = "google_bids";
            public static final String ATT_HOTEL_TICKER = "hotelTicker";
            public static final String ATT_BID = "bid";
            public static final String ATT_ACTIVE = "active";

        }

        public static final String GET_HOTELS_FROM_CHAIN = "SELECT ia.identificador AS " + TableChain.ATT_HOTEL +
                " FROM " + TableInstanciasAplicaciones.TBL_NAME + " as ia " +
                " WHERE ia.id IN ( " +
                "   SELECT c.hotel" +
                "       FROM " + TableInstanciasAplicaciones.TBL_NAME + " as ia , chain as c " +
                "           WHERE ia.identificador = ? AND c.chain = ia.id" +
                "   )";


        public static final String CHECK_IF_IS_CHAIN =
                "SELECT " + TableInstanciasAplicaciones.ATT_CHECK_AGREGADOR + " FROM " + TableInstanciasAplicaciones.TBL_NAME +
                        " WHERE " + TableInstanciasAplicaciones.ATT_TICKER + "=? ";

        public static final String CHECK_IF_IS_DEMO =
                "SELECT " + TableInstanciasAplicaciones.ATT_CHECK_DEMO + " FROM " + TableInstanciasAplicaciones.TBL_NAME +
                        " WHERE " + TableInstanciasAplicaciones.ATT_TICKER + "=? ";

        public static final String CHECK_IF_HAS_TRANSFERS =
                "SELECT " + TableInstanciasAplicaciones.ATT_HAS_TRANSFERS + " FROM " + TableInstanciasAplicaciones.TBL_NAME +
                        " WHERE " + TableInstanciasAplicaciones.ATT_TICKER + "=? ";

        public static final String GET_HOTEL_LIST_ACTIVE_TRANSFERS = "SELECT ia.identificador " +
                "FROM instanciasaplicaciones as ia " +
                "WHERE ia.active_transfers=1 " +
                "AND ia.version = 6 " +
                "ORDER BY ia.identificador ASC;";

        public static final String GET_HOTEL_LIST_ACTIVE_V6 = "SELECT ia.identificador " +
                "FROM instanciasaplicaciones as ia " +
                "WHERE ia.es_demo=0 " +
                "AND ia.es_agregador_dummy=0 " +
                "AND ia.ffin_actividad IS NULL " +
                "AND ia.version = 6 " +
                "ORDER BY ia.identificador ASC;";

        public static final String GET_HOTEL_LIST_POST_STAY_MAIL_ACTIVE = "SELECT ea.hotel_ticker, ea.dias_previos " +
                "FROM encuestas_activas as ea " +
                "WHERE ea.activa=1 " +
                "ORDER BY ea.hotel_ticker ASC;";
        public static final String GET_HOTEL_LIST_PRE_STAY_MAIL_ACTIVE = "SELECT ea.hotel_ticker, ea.dias_previos_pre " +
                "FROM encuestas_activas as ea " +
                "WHERE ea.activa_pre=1 " +
                "ORDER BY ea.hotel_ticker ASC;";

        public static final String INSERT_OR_REPLACE_TRIP_ADVISOR_REVIEW_EXPRESS_ID =
                "INSERT INTO " + TableTripAdvisorReviewExpress.TBL_NAME + " ("
                        + TableTripAdvisorReviewExpress.HOTEL_TICKER + ","
                        + TableTripAdvisorReviewExpress.RESERVATION_ID + ","
                        + TableTripAdvisorReviewExpress.TRIP_ADVISOR_ID
                        + ") VALUES (?,?,?) ON DUPLICATE KEY UPDATE " + TableTripAdvisorReviewExpress.TRIP_ADVISOR_ID + "=? ;";

        public static final String GET_TRIP_ADVISOR_REVIEW_EXPRESS_ID = "SELECT " +
                TableTripAdvisorReviewExpress.TRIP_ADVISOR_ID +
                " FROM " + TableTripAdvisorReviewExpress.TBL_NAME +
                " WHERE " + TableTripAdvisorReviewExpress.HOTEL_TICKER + "=? AND " + TableTripAdvisorReviewExpress.RESERVATION_ID + "=? ;";

        public static final String GET_GOOGLE_BIDS = "SELECT " + GoogleBids.ATT_HOTEL_TICKER + " , " + GoogleBids.ATT_BID + " , "
                + GoogleBids.ATT_ACTIVE + " FROM " + GoogleBids.TBL_NAME + " ";
        public static final String GET_GOOGLE_BIDS_FOR_HOTEL = GET_GOOGLE_BIDS + " WHERE " + GoogleBids.ATT_HOTEL_TICKER + "=? ";

        public static final String GET_COUNTRIES_MAP = "SELECT * FROM countries AS c " +
                "ORDER BY c.alpha2 ASC;";

        public static final String GET_TRANSFERS = "SELECT t.ticker, t.message, t.releaseMin, t.lockHours, trans.message_trans "
                + " FROM transfers AS t "
                + " LEFT JOIN "
                + "	(SELECT i.foreign_key, "
                + "      MAX(CASE WHEN i.field = 'message' THEN i.content ELSE NULL END) AS message_trans "
                + "	FROM i18n AS i "
                + "	WHERE i.locale=? AND i.model='Transfer' "
                + "	GROUP BY i.foreign_key) AS trans "
                + " ON trans.foreign_key=t.id ";

        public static final String INSERT_TRACKING_QUERY =
                "INSERT INTO trackings (fecha, checkin, checkout, dominio, agente, lang, codigotracking, ip) " +
                        " VALUES (CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?);";
    }

    public static final class HotelConfigurationDBHandler {

        /**
         * Object used to report on the Server's Log using Apache log4j.
         */
        public static final String GET_ACTIVE_LANGUAGES_CODE_SQL_COMMAND = "SELECT code, locale FROM l10ncatalogs WHERE visible=1 AND active=1;";
        public static final String GET_LANGUAGES_FROM_CODE_SQL_COMMAND = "SELECT id, name, code, locale, charset "
                + "locale FROM l10ncatalogs WHERE visible=1 AND active=1 AND code=?;";
        public static final String GET_ACTIVE_LANGUAGES_SQL_COMMAND = "SELECT id, name, code, locale, charset "
                + "FROM l10ncatalogs "
                + "WHERE visible=1 "
                + "AND active=1 "
                + "AND soportado=1 "
                + "ORDER BY name ASC;";
        public static final String GET_CONFIGURACION_PROPIEDADES = "SELECT clave , valor FROM configuracionpropiedades ";
        public static final String IS_CANCELLEATION_ALLOWED_SQL_COMMAND = "SELECT p.valor FROM configuracionpropiedades as p "
                + "WHERE clave='" + MiddlewareProperties.PROPERTY_RESERVATION_ALLOWED + "';";
        public static final String IS_VERSION_6_SQL_COMMAND = "SELECT p.es_version6_activa FROM configurations as p;";
        public static final String GET_ACTIVES_CURRENCIES_SQL_COMMAND = "SELECT confp.clave, confp.valor "
                + "FROM configuracionpropiedades AS confp "
                + "WHERE confp.clave = 'listCurrency';";
        public static final String GET_SMTP_PROPERTIES_SQL_COMMAND =
                "SELECT nombre, cuenta, contraseña, puerto, tipo, seguridad "
                        + " FROM configuracionessmtps ";
        public static final String GET_POLL_SQL_COMMAND = "SELECT e.id, e.titulo, e.contenido, " +
                "trans.locale, trans.titulo_trans, trans.contenido_trans " +
                "FROM encuestas AS e " +
                "LEFT JOIN " +
                "(SELECT i.foreign_key AS foreign_key, i.locale AS locale, " +
                "MAX(CASE WHEN i.field = 'titulo' THEN i.content ELSE NULL END) AS titulo_trans, " +
                "MAX(CASE WHEN i.field = 'contenido' THEN i.content ELSE NULL END) AS contenido_trans " +
                "FROM i18n AS i " +
                "WHERE i.locale=? AND i.model='Encuesta' " +
                "GROUP BY i.foreign_key) AS trans " +
                "ON trans.foreign_key=e.id " +
                "WHERE e.id=?;";
        public static final String GET_DEFAULT_CURRENCY_SQL_COMMAND = "SELECT c.defaultcurrency FROM configurations as c;";
        public static final String GET_MAX_PERSONS = "SELECT COALESCE(MAX(alojamiento_configuraciones.adultos),0) AS adults, " +
                "COALESCE(MAX(alojamiento_configuraciones.ninos),0) AS children, " +
                "COALESCE(MAX(alojamiento_configuraciones.bebes),0) AS babies " +
                "FROM tiposhabs, alojamiento_configuraciones " +
                "WHERE tiposhabs.alojamientoconfiguracion_id = alojamiento_configuraciones.id " +
                "AND tiposhabs.deleted <>1 " +
                "AND tiposhabs.activa=1; ";
        public static final String GET_DEFAULT_URL_WEBHOTEL = "SELECT c.urlwebhotel FROM configurations as c;";
        public static final String GET_NEWSLETTER_LANGUAGE_SQL_COMMAND = "SELECT id, email, language, active " +
                "FROM newsletter " +
                "WHERE language =? " +
                "AND active=1;";
        public static final String GET_USER_AND_PASSWORD_OF_CHANNEL = "SELECT "
                + TableChannelsIntegrations.ATT_USER + " , "
                + TableChannelsIntegrations.ATT_PASSWORD
                + " FROM " + TableChannelsIntegrations.TBL_NAME + " WHERE " + TableChannelsIntegrations.ATT_CHANNEL_TICKER + "=? ;";
        public static final String GET_HOTEL_NAME = "SELECT " + TableEstablishment.ATT_NOMBRE + " FROM " + TableEstablishment.TBL_NAME;
        public static final String GET_ACTIVE_CREDIT_CARDS = "SELECT codigo, nombre, activa FROM tarjetascredito; ";

        public static final class TableChannelsIntegrations {
            public static final String TBL_NAME = "channels_integrations";
            public static final String ATT_USER = "user";
            public static final String ATT_PASSWORD = "password";
            public static final String ATT_CHANNEL_TICKER = "channel_ticker";
        }

        public static final class TableEstablishment {
            public static final String TBL_NAME = "establecimientos";
            public static final String ATT_NOMBRE = "nombre";
        }

        public static final class TableMultimedia {
            public static final String TBL_NAME = "multimedia";
            public static final String ATT_ID = "id";
            public static final String ATT_TITLE = "title";
            public static final String ATT_DESCRIPTION = "description";
            public static final String ATT_ENTITY = "entity";
            public static final String ATT_ID_ENTITY = "id_entity";
            public static final String ATT_FILE_NAME = "file";
            public static final String ATT_ORDER = "`order`";
        }

        public static final class TableFrontEndMessage {
            public static final String TBL_NAME = "mensajes";
            public static final String ATT_ID = "id";
            public static final String ATT_USERNAME = "username";
            public static final String ATT_EDITNAME = "editedname";
            public static final String ATT_HIDDEN = "visible";
            public static final String ATT_POSITION = "seccion";
            public static final String ATT_TYPE = "tipo";
            public static final String ATT_TITLE = "titulo";
            public static final String ATT_DESCRIPTION = "descripcion";
            public static final String ATT_CREATION_DATE = "fcreacion";
            public static final String ATT_MODIFICATION_DATE = "fmodificacion";
            public static final String ATT_INITIAL_DATE = "fechainicio";
            public static final String ATT_END_DATE = "fechafin";
            public static final String ATT_ALOJAMIENTOS = "alojamientos";
        }

        public static final String GET_FRONT_END_MESSAGES = " SELECT * , trans.descripcion_trans FROM " + TableFrontEndMessage.TBL_NAME + " as m LEFT JOIN " +
                "(SELECT i.foreign_key AS foreign_key, i.locale AS locale, " +
                "MAX(CASE WHEN i.field = '" + TableFrontEndMessage.ATT_DESCRIPTION + "' THEN i.content ELSE NULL END) AS descripcion_trans " +
                "FROM i18n AS i " +
                "WHERE i.locale=? AND i.model='Mensaje' " +
                "GROUP BY i.foreign_key) AS trans " +
                "ON trans.foreign_key=m.id ";

        private static final String WHERE_MULTIMEDIA = " WHERE " + TableMultimedia.ATT_ENTITY + "=? AND " +
                TableMultimedia.ATT_ID_ENTITY + "=? AND " +
                TableMultimedia.ATT_FILE_NAME + "=? ";

        public static final String CHECK_IF_EXISTS_MULTIMEDIA =
                " SELECT " + TableMultimedia.ATT_ID + " FROM " + TableMultimedia.TBL_NAME + WHERE_MULTIMEDIA;

        public static final String UPDATE_MULTIMEDIA = "UPDATE " + TableMultimedia.TBL_NAME +
                " SET " + TableMultimedia.ATT_TITLE + "=? , " + TableMultimedia.ATT_DESCRIPTION + "=?" +
                WHERE_MULTIMEDIA;

        public static final String INSERT_MULTIMEDIA = "INSERT INTO " + TableMultimedia.TBL_NAME +
                "(" + Joiner.on(",").join(new String[]{TableMultimedia.ATT_TITLE, TableMultimedia.ATT_DESCRIPTION,
                TableMultimedia.ATT_ENTITY, TableMultimedia.ATT_ID_ENTITY, TableMultimedia.ATT_FILE_NAME}) + ") VALUES (?,?,?,?,?)";

        public static final String GET_AMENITIES =
                "SELECT servicio_id as id FROM establecimientos_servicios";

        public static final String INSERT_CONVERSION_QUERY =
                "INSERT INTO consultas (ip, datein, dateout, nights, rooms, proom, adultos, ninos, bebes,  lang, esNoDisp, isChain, timestamp) " +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP);";


    }

    public static final class CurrencyDBHandler {
        /**
         * Object used to report on the Server's Log using Apache log4j.
         */
        public static final Logger logger = Logger.getLogger(com.witbooking.middleware.db.handlers.CurrencyDBHandler.class);
        public static final String DB_CURRENCY_TICKER = "conversor";
        public static final String GET_CURRENCY_EXCHANGE_SQL_COMMAND = "SELECT c.convertTo, c.change FROM cotizaciondivisas AS c "
                + "WHERE c.base=? "
                + "AND c.convertTo!=?;";
        public static final String GET_CURRENCY_SQL_COMMAND = "SELECT d.code, d.name FROM divisas AS d "
                + "WHERE d.code=? "
                + "AND d.active=1;";
        public static final String GET_ALL_CURRENCIES_ACTIVES_SQL_COMMAND = "SELECT code , name FROM divisas WHERE active=1;";
    }

    public static final class MarkupDBHandler {
        public static final String GET_MARKUP_SQL_COMMAND = "SELECT s.id, s.name, s.code, s.position, s.place, s.active FROM markup AS s ";
    }

    public static final class WitHotelDBHandler {

        public static final String DB_WITMETADATA_TICKER = "withotel";
        // Nombre de las tablas
        public static final String HOTEL_TBL = "hotels";
        public static final String CONFIGURATIONS_TBL = "configurations";
        public static final String LOCATIONS_POINT_TBL = "places";
        public static final String LOCATIONS_POINT_TYPES_TBL = "place_types";
        public static final String REVIEWS_TBL = "reviews";
        public static final String GET_HOTEL_SQL_COMMAND = "SELECT id , ticker, url FROM " + HOTEL_TBL + " WHERE ticker=? LIMIT 1;";
        public static final String GET_CONFIGURATIONS_SQL_COMMAND = "SELECT a.clave, a.valor FROM "
                + CONFIGURATIONS_TBL + " as a , " + HOTEL_TBL + " as b WHERE a.id_hotel=b.id AND b.ticker=? ";
        public static final String GET_LOCATIONS_POINT_SQL_COMMAND = " SELECT a.id, a.title, a.description, a.address, a.latitude, "
                + "a.longitude, a.distance, b.ticker, a.title, trans.title_trans, "
                + " a.description, trans.description_trans " +
                "FROM " + LOCATIONS_POINT_TBL + " as a, "
                + LOCATIONS_POINT_TYPES_TBL + " as b, " + HOTEL_TBL + " as c , " +
                "(SELECT i.foreign_key AS foreign_key, " +
                "MAX(CASE WHEN i.field = 'title' THEN i.content ELSE NULL END) AS title_trans, " +
                "MAX(CASE WHEN i.field = 'description' THEN i.content ELSE NULL END) AS description_trans " +
                "FROM i18n AS i " +
                "WHERE i.locale=? AND i.model='Place' " +
                "GROUP BY i.foreign_key) AS trans " +
                "WHERE a.id_place_type = b.id " +
                "AND a.id_hotel=c.id " +
                "AND c.ticker=? " +
                "AND trans.foreign_key=a.id " +
                "ORDER BY a.id_place_type;";
        public static final String GET_REVIEWS_SQL_COMMAND = "SELECT r.id, r.id_hotel, r.title, r.comment, r.name, "
                + "r.language, r.address, r.source, r.visible "
                + "FROM " + REVIEWS_TBL + " AS r, " + HOTEL_TBL + " AS h "
                + "WHERE r.id_hotel = h.id "
                + "AND h.ticker=? "
                + "AND r.language=? "
                + "AND r.visible=1;";
        public static final String GET_LOCATIONS_POINT_TYPES_SQL_COMMAND = "SELECT ticker FROM " + LOCATIONS_POINT_TYPES_TBL + ";";

        public static final String GET_PAGES = "SELECT id,ticker,title,description,seo,parent,trans.name_trans, trans.desc_trans "
                + " FROM pages as p "
                + " LEFT JOIN "
                + "	(SELECT i.foreign_key, "
                + "	MAX(CASE WHEN i.field = 'title' THEN i.content ELSE NULL END) AS name_trans, "
                + "      MAX(CASE WHEN i.field = 'description' THEN i.content ELSE NULL END) AS desc_trans "
                + "	FROM i18n AS i "
                + "	WHERE i.locale=? AND i.model='Page' "
                + "	GROUP BY i.foreign_key) AS trans "
                + " ON trans.foreign_key=p.id "
                + " ORDER BY p.parent, p.order;";
    }

    public static final class ChannelsHotelDBHandler {
        public static final String ID = "id",
                CHANNEL_TICKER = "channel_ticker",
                USER = "user",
                PASSWORD = "password",
                MULTIPLIER = "multiplier",
                AGENT_ID = "agent_id",
                ADDRESS_PUSH = "address_push",
                PROTOCOL_PUSH = "protocol_push",
                ACTIVE = "active",
                KEY = "codeKey",
                VALUE = "codeValue",
                TBL_CHANNELS_INTEGRATIONS = "channels_integrations",
                TBL_CHANNELS_MAPPINGS = "channels_mappings",
                TBL_CHANNELS_MAPPINGS_CODES_TYPES = "channels_mappings_code_types",
                TBL_CHANNELS_MAPPINGS_CODES = "channels_mappings_codes";
        public static final String SELECT_CHANNELS = "SELECT "
                + ID + "," + CHANNEL_TICKER + "," + USER + " , " + PASSWORD + " ," + MULTIPLIER
                + "," + AGENT_ID + "," + PROTOCOL_PUSH + "," + ADDRESS_PUSH + "," + ACTIVE
                + " FROM " + TBL_CHANNELS_INTEGRATIONS + " ";
        //SELECT mct.name AS codeKey, mc.value AS codeValue FROM channels_mappings_code_types as mct , channels_mappings_codes as mc , channels_mappings as m , channels_integrations as i WHERE i.id = m.channels_integrations AND mc.channels_mappings = m.id AND mct.id = mc.channels_mappings_code_types AND i.channel_ticker="BOOKING" AND m.tiposhabs_ticker="stand_1a1n_SA_c";
        public static final String SELECT_CODES_FROM_INVENTORY_TICKER =
                "SELECT mct.name AS " + KEY + " , mc.value AS " + VALUE + " FROM "
                        + TBL_CHANNELS_MAPPINGS_CODES_TYPES + " as mct , "
                        + TBL_CHANNELS_MAPPINGS_CODES + " as mc , " + TBL_CHANNELS_MAPPINGS + " as m ,"
                        + TBL_CHANNELS_INTEGRATIONS + " as i "
                        + " WHERE i.id = m.channels_integrations AND mc.channels_mappings = m.id AND "
                        + " mct.id = mc.channels_mappings_code_types AND "
                        + " i.channel_ticker=? AND m.tiposhabs_ticker=?;";
        public static final String SELECT_CHANNEL_MAPPING =
                "SELECT mct.name AS " + KEY + " , mc.value AS " + VALUE
                        + " , m.tiposhabs_ticker AS " + CHANNEL_TICKER + " FROM "
                        + TBL_CHANNELS_MAPPINGS_CODES_TYPES + " as mct , "
                        + TBL_CHANNELS_MAPPINGS_CODES + " as mc , " + TBL_CHANNELS_MAPPINGS + " as m ,"
                        + TBL_CHANNELS_INTEGRATIONS + " as i "
                        + " WHERE i.id = m.channels_integrations AND mc.channels_mappings = m.id AND "
                        + " mct.id = mc.channels_mappings_code_types AND "
                        + " i.channel_ticker=?;";

        public static String getSelectChannels(String where) {
            return where == null ? SELECT_CHANNELS + ";" : SELECT_CHANNELS + where + ";";
        }
    }
}
