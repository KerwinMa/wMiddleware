
package com.witbooking.middleware.integration.google.model.feed;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for language.content.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="language.content">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}language">
 *     &lt;enumeration value="aa"/>
 *     &lt;enumeration value="ab"/>
 *     &lt;enumeration value="af"/>
 *     &lt;enumeration value="am"/>
 *     &lt;enumeration value="ar"/>
 *     &lt;enumeration value="as"/>
 *     &lt;enumeration value="ay"/>
 *     &lt;enumeration value="az"/>
 *     &lt;enumeration value="ba"/>
 *     &lt;enumeration value="be"/>
 *     &lt;enumeration value="bg"/>
 *     &lt;enumeration value="bh"/>
 *     &lt;enumeration value="bi"/>
 *     &lt;enumeration value="bn"/>
 *     &lt;enumeration value="bo"/>
 *     &lt;enumeration value="br"/>
 *     &lt;enumeration value="ca"/>
 *     &lt;enumeration value="co"/>
 *     &lt;enumeration value="cs"/>
 *     &lt;enumeration value="cy"/>
 *     &lt;enumeration value="da"/>
 *     &lt;enumeration value="de"/>
 *     &lt;enumeration value="dz"/>
 *     &lt;enumeration value="el"/>
 *     &lt;enumeration value="en"/>
 *     &lt;enumeration value="eo"/>
 *     &lt;enumeration value="es"/>
 *     &lt;enumeration value="et"/>
 *     &lt;enumeration value="eu"/>
 *     &lt;enumeration value="fa"/>
 *     &lt;enumeration value="fi"/>
 *     &lt;enumeration value="fj"/>
 *     &lt;enumeration value="fo"/>
 *     &lt;enumeration value="fr"/>
 *     &lt;enumeration value="fy"/>
 *     &lt;enumeration value="ga"/>
 *     &lt;enumeration value="gd"/>
 *     &lt;enumeration value="gl"/>
 *     &lt;enumeration value="gn"/>
 *     &lt;enumeration value="gu"/>
 *     &lt;enumeration value="ha"/>
 *     &lt;enumeration value="he"/>
 *     &lt;enumeration value="hi"/>
 *     &lt;enumeration value="hr"/>
 *     &lt;enumeration value="hu"/>
 *     &lt;enumeration value="hy"/>
 *     &lt;enumeration value="ia"/>
 *     &lt;enumeration value="id"/>
 *     &lt;enumeration value="ie"/>
 *     &lt;enumeration value="ik"/>
 *     &lt;enumeration value="in"/>
 *     &lt;enumeration value="is"/>
 *     &lt;enumeration value="it"/>
 *     &lt;enumeration value="iw"/>
 *     &lt;enumeration value="ja"/>
 *     &lt;enumeration value="ji"/>
 *     &lt;enumeration value="jw"/>
 *     &lt;enumeration value="ka"/>
 *     &lt;enumeration value="kk"/>
 *     &lt;enumeration value="kl"/>
 *     &lt;enumeration value="km"/>
 *     &lt;enumeration value="kn"/>
 *     &lt;enumeration value="ko"/>
 *     &lt;enumeration value="ks"/>
 *     &lt;enumeration value="ku"/>
 *     &lt;enumeration value="ky"/>
 *     &lt;enumeration value="la"/>
 *     &lt;enumeration value="ln"/>
 *     &lt;enumeration value="lo"/>
 *     &lt;enumeration value="lt"/>
 *     &lt;enumeration value="lv"/>
 *     &lt;enumeration value="mg"/>
 *     &lt;enumeration value="mi"/>
 *     &lt;enumeration value="mk"/>
 *     &lt;enumeration value="ml"/>
 *     &lt;enumeration value="mn"/>
 *     &lt;enumeration value="mo"/>
 *     &lt;enumeration value="mr"/>
 *     &lt;enumeration value="ms"/>
 *     &lt;enumeration value="mt"/>
 *     &lt;enumeration value="my"/>
 *     &lt;enumeration value="na"/>
 *     &lt;enumeration value="ne"/>
 *     &lt;enumeration value="nl"/>
 *     &lt;enumeration value="no"/>
 *     &lt;enumeration value="oc"/>
 *     &lt;enumeration value="om"/>
 *     &lt;enumeration value="or"/>
 *     &lt;enumeration value="pa"/>
 *     &lt;enumeration value="pl"/>
 *     &lt;enumeration value="ps"/>
 *     &lt;enumeration value="pt"/>
 *     &lt;enumeration value="pt-BR"/>
 *     &lt;enumeration value="pt-PT"/>
 *     &lt;enumeration value="qu"/>
 *     &lt;enumeration value="rm"/>
 *     &lt;enumeration value="rn"/>
 *     &lt;enumeration value="ro"/>
 *     &lt;enumeration value="ru"/>
 *     &lt;enumeration value="rw"/>
 *     &lt;enumeration value="sa"/>
 *     &lt;enumeration value="sd"/>
 *     &lt;enumeration value="sg"/>
 *     &lt;enumeration value="sh"/>
 *     &lt;enumeration value="si"/>
 *     &lt;enumeration value="sk"/>
 *     &lt;enumeration value="sl"/>
 *     &lt;enumeration value="sm"/>
 *     &lt;enumeration value="sn"/>
 *     &lt;enumeration value="so"/>
 *     &lt;enumeration value="sq"/>
 *     &lt;enumeration value="sr"/>
 *     &lt;enumeration value="ss"/>
 *     &lt;enumeration value="st"/>
 *     &lt;enumeration value="su"/>
 *     &lt;enumeration value="sv"/>
 *     &lt;enumeration value="sw"/>
 *     &lt;enumeration value="ta"/>
 *     &lt;enumeration value="te"/>
 *     &lt;enumeration value="tg"/>
 *     &lt;enumeration value="th"/>
 *     &lt;enumeration value="ti"/>
 *     &lt;enumeration value="tk"/>
 *     &lt;enumeration value="tl"/>
 *     &lt;enumeration value="tn"/>
 *     &lt;enumeration value="to"/>
 *     &lt;enumeration value="tr"/>
 *     &lt;enumeration value="ts"/>
 *     &lt;enumeration value="tt"/>
 *     &lt;enumeration value="tw"/>
 *     &lt;enumeration value="uk"/>
 *     &lt;enumeration value="ur"/>
 *     &lt;enumeration value="uz"/>
 *     &lt;enumeration value="vi"/>
 *     &lt;enumeration value="vo"/>
 *     &lt;enumeration value="wo"/>
 *     &lt;enumeration value="xh"/>
 *     &lt;enumeration value="yo"/>
 *     &lt;enumeration value="zh"/>
 *     &lt;enumeration value="zh-CN"/>
 *     &lt;enumeration value="zh-TW"/>
 *     &lt;enumeration value="zu"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "language.content")
@XmlEnum
public enum LanguageContent {

    @XmlEnumValue("aa")
    AA("aa"),
    @XmlEnumValue("ab")
    AB("ab"),
    @XmlEnumValue("af")
    AF("af"),
    @XmlEnumValue("am")
    AM("am"),
    @XmlEnumValue("ar")
    AR("ar"),
    @XmlEnumValue("as")
    AS("as"),
    @XmlEnumValue("ay")
    AY("ay"),
    @XmlEnumValue("az")
    AZ("az"),
    @XmlEnumValue("ba")
    BA("ba"),
    @XmlEnumValue("be")
    BE("be"),
    @XmlEnumValue("bg")
    BG("bg"),
    @XmlEnumValue("bh")
    BH("bh"),
    @XmlEnumValue("bi")
    BI("bi"),
    @XmlEnumValue("bn")
    BN("bn"),
    @XmlEnumValue("bo")
    BO("bo"),
    @XmlEnumValue("br")
    BR("br"),
    @XmlEnumValue("ca")
    CA("ca"),
    @XmlEnumValue("co")
    CO("co"),
    @XmlEnumValue("cs")
    CS("cs"),
    @XmlEnumValue("cy")
    CY("cy"),
    @XmlEnumValue("da")
    DA("da"),
    @XmlEnumValue("de")
    DE("de"),
    @XmlEnumValue("dz")
    DZ("dz"),
    @XmlEnumValue("el")
    EL("el"),
    @XmlEnumValue("en")
    EN("en"),
    @XmlEnumValue("eo")
    EO("eo"),
    @XmlEnumValue("es")
    ES("es"),
    @XmlEnumValue("et")
    ET("et"),
    @XmlEnumValue("eu")
    EU("eu"),
    @XmlEnumValue("fa")
    FA("fa"),
    @XmlEnumValue("fi")
    FI("fi"),
    @XmlEnumValue("fj")
    FJ("fj"),
    @XmlEnumValue("fo")
    FO("fo"),
    @XmlEnumValue("fr")
    FR("fr"),
    @XmlEnumValue("fy")
    FY("fy"),
    @XmlEnumValue("ga")
    GA("ga"),
    @XmlEnumValue("gd")
    GD("gd"),
    @XmlEnumValue("gl")
    GL("gl"),
    @XmlEnumValue("gn")
    GN("gn"),
    @XmlEnumValue("gu")
    GU("gu"),
    @XmlEnumValue("ha")
    HA("ha"),
    @XmlEnumValue("he")
    HE("he"),
    @XmlEnumValue("hi")
    HI("hi"),
    @XmlEnumValue("hr")
    HR("hr"),
    @XmlEnumValue("hu")
    HU("hu"),
    @XmlEnumValue("hy")
    HY("hy"),
    @XmlEnumValue("ia")
    IA("ia"),
    @XmlEnumValue("id")
    ID("id"),
    @XmlEnumValue("ie")
    IE("ie"),
    @XmlEnumValue("ik")
    IK("ik"),
    @XmlEnumValue("in")
    IN("in"),
    @XmlEnumValue("is")
    IS("is"),
    @XmlEnumValue("it")
    IT("it"),
    @XmlEnumValue("iw")
    IW("iw"),
    @XmlEnumValue("ja")
    JA("ja"),
    @XmlEnumValue("ji")
    JI("ji"),
    @XmlEnumValue("jw")
    JW("jw"),
    @XmlEnumValue("ka")
    KA("ka"),
    @XmlEnumValue("kk")
    KK("kk"),
    @XmlEnumValue("kl")
    KL("kl"),
    @XmlEnumValue("km")
    KM("km"),
    @XmlEnumValue("kn")
    KN("kn"),
    @XmlEnumValue("ko")
    KO("ko"),
    @XmlEnumValue("ks")
    KS("ks"),
    @XmlEnumValue("ku")
    KU("ku"),
    @XmlEnumValue("ky")
    KY("ky"),
    @XmlEnumValue("la")
    LA("la"),
    @XmlEnumValue("ln")
    LN("ln"),
    @XmlEnumValue("lo")
    LO("lo"),
    @XmlEnumValue("lt")
    LT("lt"),
    @XmlEnumValue("lv")
    LV("lv"),
    @XmlEnumValue("mg")
    MG("mg"),
    @XmlEnumValue("mi")
    MI("mi"),
    @XmlEnumValue("mk")
    MK("mk"),
    @XmlEnumValue("ml")
    ML("ml"),
    @XmlEnumValue("mn")
    MN("mn"),
    @XmlEnumValue("mo")
    MO("mo"),
    @XmlEnumValue("mr")
    MR("mr"),
    @XmlEnumValue("ms")
    MS("ms"),
    @XmlEnumValue("mt")
    MT("mt"),
    @XmlEnumValue("my")
    MY("my"),
    @XmlEnumValue("na")
    NA("na"),
    @XmlEnumValue("ne")
    NE("ne"),
    @XmlEnumValue("nl")
    NL("nl"),
    @XmlEnumValue("no")
    NO("no"),
    @XmlEnumValue("oc")
    OC("oc"),
    @XmlEnumValue("om")
    OM("om"),
    @XmlEnumValue("or")
    OR("or"),
    @XmlEnumValue("pa")
    PA("pa"),
    @XmlEnumValue("pl")
    PL("pl"),
    @XmlEnumValue("ps")
    PS("ps"),
    @XmlEnumValue("pt")
    PT("pt"),
    @XmlEnumValue("pt-BR")
    PT_BR("pt-BR"),
    @XmlEnumValue("pt-PT")
    PT_PT("pt-PT"),
    @XmlEnumValue("qu")
    QU("qu"),
    @XmlEnumValue("rm")
    RM("rm"),
    @XmlEnumValue("rn")
    RN("rn"),
    @XmlEnumValue("ro")
    RO("ro"),
    @XmlEnumValue("ru")
    RU("ru"),
    @XmlEnumValue("rw")
    RW("rw"),
    @XmlEnumValue("sa")
    SA("sa"),
    @XmlEnumValue("sd")
    SD("sd"),
    @XmlEnumValue("sg")
    SG("sg"),
    @XmlEnumValue("sh")
    SH("sh"),
    @XmlEnumValue("si")
    SI("si"),
    @XmlEnumValue("sk")
    SK("sk"),
    @XmlEnumValue("sl")
    SL("sl"),
    @XmlEnumValue("sm")
    SM("sm"),
    @XmlEnumValue("sn")
    SN("sn"),
    @XmlEnumValue("so")
    SO("so"),
    @XmlEnumValue("sq")
    SQ("sq"),
    @XmlEnumValue("sr")
    SR("sr"),
    @XmlEnumValue("ss")
    SS("ss"),
    @XmlEnumValue("st")
    ST("st"),
    @XmlEnumValue("su")
    SU("su"),
    @XmlEnumValue("sv")
    SV("sv"),
    @XmlEnumValue("sw")
    SW("sw"),
    @XmlEnumValue("ta")
    TA("ta"),
    @XmlEnumValue("te")
    TE("te"),
    @XmlEnumValue("tg")
    TG("tg"),
    @XmlEnumValue("th")
    TH("th"),
    @XmlEnumValue("ti")
    TI("ti"),
    @XmlEnumValue("tk")
    TK("tk"),
    @XmlEnumValue("tl")
    TL("tl"),
    @XmlEnumValue("tn")
    TN("tn"),
    @XmlEnumValue("to")
    TO("to"),
    @XmlEnumValue("tr")
    TR("tr"),
    @XmlEnumValue("ts")
    TS("ts"),
    @XmlEnumValue("tt")
    TT("tt"),
    @XmlEnumValue("tw")
    TW("tw"),
    @XmlEnumValue("uk")
    UK("uk"),
    @XmlEnumValue("ur")
    UR("ur"),
    @XmlEnumValue("uz")
    UZ("uz"),
    @XmlEnumValue("vi")
    VI("vi"),
    @XmlEnumValue("vo")
    VO("vo"),
    @XmlEnumValue("wo")
    WO("wo"),
    @XmlEnumValue("xh")
    XH("xh"),
    @XmlEnumValue("yo")
    YO("yo"),
    @XmlEnumValue("zh")
    ZH("zh"),
    @XmlEnumValue("zh-CN")
    ZH_CN("zh-CN"),
    @XmlEnumValue("zh-TW")
    ZH_TW("zh-TW"),
    @XmlEnumValue("zu")
    ZU("zu");
    private final String value;

    LanguageContent(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LanguageContent fromValue(String v) {
        for (LanguageContent c: LanguageContent.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
