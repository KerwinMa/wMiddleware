/*
 *  UtilsResource.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.internal;

import com.witbooking.middleware.beans.MailingBean;
import com.witbooking.middleware.beans.MailingBeanLocal;
import org.apache.log4j.Logger;
import org.matheclipse.core.eval.EvalUtilities;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.form.output.OutputFormFactory;
import org.matheclipse.core.form.output.StringBufferWriter;
import org.matheclipse.core.interfaces.IExpr;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;

//matheclipse

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @date 23-may-2013
 * @version 1.0
 * @since
 */
@Path("internal/utils")
@RequestScoped
public class UtilsResource {

   private static final Logger logger = Logger.getLogger(UtilsResource.class);
    @EJB
    private MailingBeanLocal mailingBeanLocal;

   /**
    * Creates a new instance of
    * <code>UtilsResource</code> without params.
    */
   public UtilsResource() {
   }

   @POST
   @Produces("application/json")
   @Path("/getSimpleExpression")
   public String getSimpleExpressionByPost(@FormParam("exp") String expression) {
      logger.debug("getSimpleExpression");
      try {
         F.initSymbols();
         EvalUtilities util = new EvalUtilities();
         StringBufferWriter buf = null;
         try {
            buf = new StringBufferWriter();
            String input = "Expand[" + expression + "]";

//            logger.debug("expression = " + expression);
            IExpr result = util.evaluate(input);

            OutputFormFactory.get().convert(buf, result);
            String output = buf.toString();
//            logger.debug("SimpleExpression= " + output);
            return output;
         } catch (Exception ex) {
            throw ex;
         } finally {
             if (buf != null)
                 buf.close();
         }
      } catch (Exception ex) {
         ex.printStackTrace();
         logger.error(ex.getMessage());
         logger.error("ERROR_FORMULA: '" + expression + "'");
         return "ERROR_FORMULA: '" + expression + "'";
      }
   }

   @GET
   @Produces("application/json")
   @Path("/getSimpleExpression")
   public String getSimpleExpressionByGet(@QueryParam("exp") String expression) {
      return getSimpleExpressionByPost(expression);
   }

    @POST
    @Path("/sendMailPostStay")
    public String sendMailPostStay(@FormParam("hotelTicker") String hotelTicker,
                                   @FormParam("daysBefore") int daysBefore) {
        logger.debug("sendMailPostStay");
        logger.debug("{hotelTicker:'" + hotelTicker + "', daysBefore:" + daysBefore + "}");
        try {
            if (mailingBeanLocal == null) {
                logger.error("mailingBeanLocal is null");
                mailingBeanLocal = new MailingBean();
            }
            mailingBeanLocal.sendMailPreOrPostStay(hotelTicker, daysBefore, true);
        } catch (Exception e) {
            logger.error(e);
            String fail = "Fail {hotelTicker:'" + hotelTicker + "', daysBefore:" + daysBefore + "} " + e;
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                fail = fail + "\n     " + stackTraceElement;
            }
            return fail;
        }
        return "Success!";
    }

    @POST
    @Path("/sendMailPreStay")
    public String sendMailPreStay(@FormParam("hotelTicker") String hotelTicker,
                                   @FormParam("daysAfter") int daysAfter) {
        logger.debug("sendMailPreStay");
        logger.debug("{hotelTicker:'" + hotelTicker + "', daysAfter:" + daysAfter + "}");
        try {
            if (mailingBeanLocal == null) {
                logger.error("mailingBeanLocal is null");
                mailingBeanLocal = new MailingBean();
            }
            mailingBeanLocal.sendMailPreOrPostStay(hotelTicker, daysAfter, false);
        } catch (Exception e) {
            logger.error(e);
            String fail = "Fail {hotelTicker:'" + hotelTicker + "', daysBefore:" + daysAfter + "} " + e;
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                fail = fail + "\n     " + stackTraceElement;
            }
            return fail;
        }
        return "Success!";
    }
}
