
package com.witbooking.middleware.integration.google.model.feed;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.witbooking.middleware.integration.google.model.feed package.
 * <p>An ObjectFactory allows you to programmatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MenuItemDescription_QNAME = new QName("", "description");
    private final static QName _MenuItemName_QNAME = new QName("", "name");
    private final static QName _Spiciness_QNAME = new QName("", "spiciness");
    private final static QName _CODE39_QNAME = new QName("", "CODE_39");
    private final static QName _Body_QNAME = new QName("", "body");
    private final static QName _Website_QNAME = new QName("", "website");
    private final static QName _CODE128_QNAME = new QName("", "CODE_128");
    private final static QName _Link_QNAME = new QName("", "link");
    private final static QName _Offer_QNAME = new QName("", "offer");
    private final static QName _Datum_QNAME = new QName("", "datum");
    private final static QName _RelationType_QNAME = new QName("", "relation_type");
    private final static QName _Country_QNAME = new QName("", "country");
    private final static QName _Id_QNAME = new QName("", "id");
    private final static QName _BusinessName_QNAME = new QName("", "business_name");
    private final static QName _UPCA_QNAME = new QName("", "UPC_A");
    private final static QName _Title_QNAME = new QName("", "title");
    private final static QName _EAN13_QNAME = new QName("", "EAN_13");
    private final static QName _Email_QNAME = new QName("", "email");
    private final static QName _UCCEAN128_QNAME = new QName("", "UCC_EAN_128");
    private final static QName _Longitude_QNAME = new QName("", "longitude");
    private final static QName _Latitude_QNAME = new QName("", "latitude");
    private final static QName _MerchantOfferId_QNAME = new QName("", "merchant_offer_id");
    private final static QName _ExpiryPeriod_QNAME = new QName("", "expiry_period");
    private final static QName _Language_QNAME = new QName("", "language");
    private final static QName _ParentId_QNAME = new QName("", "parent_id");
    private final static QName _DetailsDetail_QNAME = new QName("", "detail");
    private final static QName _MenuOptionAllergenAbsent_QNAME = new QName("", "allergen_absent");
    private final static QName _MenuOptionDietaryRestriction_QNAME = new QName("", "dietary_restriction");
    private final static QName _MenuOptionCalories_QNAME = new QName("", "calories");
    private final static QName _MenuOptionAllergenPresent_QNAME = new QName("", "allergen_present");
    private final static QName _CouponImageUrl_QNAME = new QName("", "image_url");
    private final static QName _CouponSearchable_QNAME = new QName("", "searchable");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.witbooking.middleware.integration.google.model.feed
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Phone }
     */
    public Phone createPhone() {
        return new Phone();
    }

    /**
     * Create an instance of {@link Rate }
     */
    public Rate createRate() {
        return new Rate();
    }

    /**
     * Create an instance of {@link Redeem }
     */
    public Redeem createRedeem() {
        return new Redeem();
    }

    /**
     * Create an instance of {@link Locationinfo }
     */
    public Locationinfo createLocationinfo() {
        return new Locationinfo();
    }

    /**
     * Create an instance of {@link Component }
     */
    public Component createComponent() {
        return new Component();
    }

    /**
     * Create an instance of {@link MenuItem }
     */
    public MenuItem createMenuItem() {
        return new MenuItem();
    }

    /**
     * Create an instance of {@link LanguageAndText }
     */
    public LanguageAndText createLanguageAndText() {
        return new LanguageAndText();
    }

    /**
     * Create an instance of {@link MenuOption }
     */
    public MenuOption createMenuOption() {
        return new MenuOption();
    }

    /**
     * Create an instance of {@link Price }
     */
    public Price createPrice() {
        return new Price();
    }

    /**
     * Create an instance of {@link Text }
     */
    public Text createText() {
        return new Text();
    }

    /**
     * Create an instance of {@link Author }
     */
    public Author createAuthor() {
        return new Author();
    }

    /**
     * Create an instance of {@link Date }
     */
    public Date createDate() {
        return new Date();
    }

    /**
     * Create an instance of {@link Hiddenattr }
     */
    public Hiddenattr createHiddenattr() {
        return new Hiddenattr();
    }

    /**
     * Create an instance of {@link Menu }
     */
    public Menu createMenu() {
        return new Menu();
    }

    /**
     * Create an instance of {@link MenuSection }
     */
    public MenuSection createMenuSection() {
        return new MenuSection();
    }

    /**
     * Create an instance of {@link Relation }
     */
    public Relation createRelation() {
        return new Relation();
    }

    /**
     * Create an instance of {@link Image }
     */
    public Image createImage() {
        return new Image();
    }

    /**
     * Create an instance of {@link Barcode }
     */
    public Barcode createBarcode() {
        return new Barcode();
    }

    /**
     * Create an instance of {@link Attr }
     */
    public Attr createAttr() {
        return new Attr();
    }

    /**
     * Create an instance of {@link Listings }
     */
    public Listings createListings() {
        return new Listings();
    }

    /**
     * Create an instance of {@link SharedContent }
     */
    public SharedContent createSharedContent() {
        return new SharedContent();
    }

    /**
     * Create an instance of {@link Coupon }
     */
    public Coupon createCoupon() {
        return new Coupon();
    }

    /**
     * Create an instance of {@link Details }
     */
    public Details createDetails() {
        return new Details();
    }

    /**
     * Create an instance of {@link StartDate }
     */
    public StartDate createStartDate() {
        return new StartDate();
    }

    /**
     * Create an instance of {@link EndDate }
     */
    public EndDate createEndDate() {
        return new EndDate();
    }

    /**
     * Create an instance of {@link ExpiryDate }
     */
    public ExpiryDate createExpiryDate() {
        return new ExpiryDate();
    }

    /**
     * Create an instance of {@link ClubCard }
     */
    public ClubCard createClubCard() {
        return new ClubCard();
    }

    /**
     * Create an instance of {@link ProviderInfo }
     */
    public ProviderInfo createProviderInfo() {
        return new ProviderInfo();
    }

    /**
     * Create an instance of {@link Listing }
     */
    public Listing createListing() {
        return new Listing();
    }

    /**
     * Create an instance of {@link Name }
     */
    public Name createName() {
        return new Name();
    }

    /**
     * Create an instance of {@link Address }
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link Category }
     */
    public Category createCategory() {
        return new Category();
    }

    /**
     * Create an instance of {@link Content }
     */
    public Content createContent() {
        return new Content();
    }

    /**
     * Create an instance of {@link Review }
     */
    public Review createReview() {
        return new Review();
    }

    /**
     * Create an instance of {@link Rating }
     */
    public Rating createRating() {
        return new Rating();
    }

    /**
     * Create an instance of {@link Attributes }
     */
    public Attributes createAttributes() {
        return new Attributes();
    }

    /**
     * Create an instance of {@link HotelData }
     */
    public HotelData createHotelData() {
        return new HotelData();
    }

    /**
     * Create an instance of {@link Amenities }
     */
    public Amenities createAmenities() {
        return new Amenities();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LanguageAndText }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "description", scope = MenuItem.class)
    public JAXBElement<LanguageAndText> createMenuItemDescription(LanguageAndText value) {
        return new JAXBElement<LanguageAndText>(_MenuItemDescription_QNAME, LanguageAndText.class, MenuItem.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LanguageAndText }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "name", scope = MenuItem.class)
    public JAXBElement<LanguageAndText> createMenuItemName(LanguageAndText value) {
        return new JAXBElement<LanguageAndText>(_MenuItemName_QNAME, LanguageAndText.class, MenuItem.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "spiciness")
    public JAXBElement<String> createSpiciness(String value) {
        return new JAXBElement<String>(_Spiciness_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "CODE_39")
    public JAXBElement<String> createCODE39(String value) {
        return new JAXBElement<String>(_CODE39_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "body")
    public JAXBElement<String> createBody(String value) {
        return new JAXBElement<String>(_Body_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "website")
    public JAXBElement<String> createWebsite(String value) {
        return new JAXBElement<String>(_Website_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "CODE_128")
    public JAXBElement<String> createCODE128(String value) {
        return new JAXBElement<String>(_CODE128_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "link")
    public JAXBElement<String> createLink(String value) {
        return new JAXBElement<String>(_Link_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "offer")
    public JAXBElement<String> createOffer(String value) {
        return new JAXBElement<String>(_Offer_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "datum")
    public JAXBElement<String> createDatum(String value) {
        return new JAXBElement<String>(_Datum_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "relation_type")
    public JAXBElement<String> createRelationType(String value) {
        return new JAXBElement<String>(_RelationType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "country")
    public JAXBElement<String> createCountry(String value) {
        return new JAXBElement<String>(_Country_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "id")
    public JAXBElement<String> createId(String value) {
        return new JAXBElement<String>(_Id_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "business_name")
    public JAXBElement<String> createBusinessName(String value) {
        return new JAXBElement<String>(_BusinessName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "UPC_A")
    public JAXBElement<String> createUPCA(String value) {
        return new JAXBElement<String>(_UPCA_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "title")
    public JAXBElement<String> createTitle(String value) {
        return new JAXBElement<String>(_Title_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "EAN_13")
    public JAXBElement<String> createEAN13(String value) {
        return new JAXBElement<String>(_EAN13_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "email")
    public JAXBElement<String> createEmail(String value) {
        return new JAXBElement<String>(_Email_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "UCC_EAN_128")
    public JAXBElement<String> createUCCEAN128(String value) {
        return new JAXBElement<String>(_UCCEAN128_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "longitude")
    public JAXBElement<Float> createLongitude(Float value) {
        return new JAXBElement<Float>(_Longitude_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "latitude")
    public JAXBElement<Float> createLatitude(Float value) {
        return new JAXBElement<Float>(_Latitude_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "merchant_offer_id")
    public JAXBElement<String> createMerchantOfferId(String value) {
        return new JAXBElement<String>(_MerchantOfferId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "expiry_period")
    public JAXBElement<Integer> createExpiryPeriod(Integer value) {
        return new JAXBElement<Integer>(_ExpiryPeriod_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LanguageContent }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "language")
    public JAXBElement<LanguageContent> createLanguage(LanguageContent value) {
        return new JAXBElement<LanguageContent>(_Language_QNAME, LanguageContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "parent_id")
    public JAXBElement<String> createParentId(String value) {
        return new JAXBElement<String>(_ParentId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LanguageAndText }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "description", scope = Menu.class)
    public JAXBElement<LanguageAndText> createMenuDescription(LanguageAndText value) {
        return new JAXBElement<LanguageAndText>(_MenuItemDescription_QNAME, LanguageAndText.class, Menu.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LanguageAndText }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "name", scope = Menu.class)
    public JAXBElement<LanguageAndText> createMenuName(LanguageAndText value) {
        return new JAXBElement<LanguageAndText>(_MenuItemName_QNAME, LanguageAndText.class, Menu.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "detail", scope = Details.class)
    public JAXBElement<String> createDetailsDetail(String value) {
        return new JAXBElement<String>(_DetailsDetail_QNAME, String.class, Details.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LanguageAndText }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "description", scope = MenuSection.class)
    public JAXBElement<LanguageAndText> createMenuSectionDescription(LanguageAndText value) {
        return new JAXBElement<LanguageAndText>(_MenuItemDescription_QNAME, LanguageAndText.class, MenuSection.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LanguageAndText }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "name", scope = MenuSection.class)
    public JAXBElement<LanguageAndText> createMenuSectionName(LanguageAndText value) {
        return new JAXBElement<LanguageAndText>(_MenuItemName_QNAME, LanguageAndText.class, MenuSection.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "allergen_absent", scope = MenuOption.class)
    public JAXBElement<String> createMenuOptionAllergenAbsent(String value) {
        return new JAXBElement<String>(_MenuOptionAllergenAbsent_QNAME, String.class, MenuOption.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "dietary_restriction", scope = MenuOption.class)
    public JAXBElement<String> createMenuOptionDietaryRestriction(String value) {
        return new JAXBElement<String>(_MenuOptionDietaryRestriction_QNAME, String.class, MenuOption.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LanguageAndText }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "description", scope = MenuOption.class)
    public JAXBElement<LanguageAndText> createMenuOptionDescription(LanguageAndText value) {
        return new JAXBElement<LanguageAndText>(_MenuItemDescription_QNAME, LanguageAndText.class, MenuOption.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LanguageAndText }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "name", scope = MenuOption.class)
    public JAXBElement<LanguageAndText> createMenuOptionName(LanguageAndText value) {
        return new JAXBElement<LanguageAndText>(_MenuItemName_QNAME, LanguageAndText.class, MenuOption.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "calories", scope = MenuOption.class)
    public JAXBElement<String> createMenuOptionCalories(String value) {
        return new JAXBElement<String>(_MenuOptionCalories_QNAME, String.class, MenuOption.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "allergen_present", scope = MenuOption.class)
    public JAXBElement<String> createMenuOptionAllergenPresent(String value) {
        return new JAXBElement<String>(_MenuOptionAllergenPresent_QNAME, String.class, MenuOption.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "image_url", scope = Coupon.class)
    public JAXBElement<String> createCouponImageUrl(String value) {
        return new JAXBElement<String>(_CouponImageUrl_QNAME, String.class, Coupon.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "searchable", scope = Coupon.class, defaultValue = "true")
    public JAXBElement<Boolean> createCouponSearchable(Boolean value) {
        return new JAXBElement<Boolean>(_CouponSearchable_QNAME, Boolean.class, Coupon.class, value);
    }

}
