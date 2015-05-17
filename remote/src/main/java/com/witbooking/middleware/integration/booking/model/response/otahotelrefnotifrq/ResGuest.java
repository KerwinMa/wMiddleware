package com.witbooking.middleware.integration.booking.model.response.otahotelrefnotifrq;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Jose Francisco Fiorillo
 * @author jffiorillo@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ResGuest implements Serializable {

    @XmlAttribute(name = "ResGuestRPH")
    private int resGuestRPH;
    @XmlElement(name = "Profiles")
    private Profiles profiles;

    public ResGuest() {
    }

    public int getResGuestRPH() {
        return resGuestRPH;
    }

    public void setResGuestRPH(int resGuestRPH) {
        this.resGuestRPH = resGuestRPH;
    }

    public Profiles getProfiles() {
        return profiles;
    }

    public void setProfiles(Profiles profiles) {
        this.profiles = profiles;
    }


    public String getSurname() {
        return profiles == null ? "" : profiles.getSurname();
    }


    public static class Profiles implements Serializable {

        @XmlElement(name = "ProfileInfo")
        private List<ProfileInfo> profile;

        public Profiles() {
        }

        @Override
        public String toString() {
            return "Profiles{" + "profile=" + profile + '}';
        }

        public String getEmail() {
            return (profile == null || profile.isEmpty()) ? "" : profile.get(0).getEmail();
        }

        public String getAddress() {
            return (profile == null || profile.isEmpty()) ? "" : profile.get(0).getAddress();
        }
        public String getCountry() {
            return (profile == null || profile.isEmpty()) ? "" : profile.get(0).getCountry();
        }

        public String getTelephone() {
            return (profile == null || profile.isEmpty()) ? "" : profile.get(0).getTelephone();
        }

        public String getGivenName() {
            return (profile == null || profile.isEmpty()) ? "" : profile.get(0).getGivenName();
        }

        public String getSurname() {
            return (profile == null || profile.isEmpty()) ? "" : profile.get(0).getSurname();
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        private static class ProfileInfo implements Serializable {

            @XmlElement(name = "Profile")
            private Profile profile;

            public String getEmail() {
                return (profile == null) ? "" : profile.getEmail();
            }

            public String getAddress() {
                return (profile == null) ? "" : profile.getAddress();
            }

            public String getCountry() {
                return (profile == null) ? "" : profile.getCountry();
            }

            public String getTelephone() {
                return (profile == null) ? "" : profile.getTelephone();
            }

            public String getGivenName() {
                return (profile == null) ? "" : profile.getGivenName();
            }

            public String getSurname() {
                return (profile == null) ? "" : profile.getSurname();
            }

            public ProfileInfo() {
            }

            private static class Profile implements Serializable {

                @XmlElement(name = "Customer")
                private List<Customer> personalName;

                public Profile() {
                }

                @Override
                public String toString() {
                    return "Profile{" + "personalName=" + personalName + '}';
                }

                public String getEmail() {
                    return (personalName == null || personalName.isEmpty())
                            ? ""
                            : personalName.get(0).email;
                }

                public String getAddress() {
                    return (personalName == null || personalName.isEmpty()) ? "" : personalName.get(0).getAddress();
                }

                public String getCountry() {
                    return (personalName == null || personalName.isEmpty()) ? "" : personalName.get(0).getCountry();
                }

                public String getTelephone() {
                    return (personalName == null || personalName.isEmpty()) ? "" : personalName.get(0).getTelephone();
                }

                public String getGivenName() {
                    return (personalName == null) ? "" : personalName.get(0).getGivenName();
                }

                public String getSurname() {
                    return (personalName == null) ? "" : personalName.get(0).getSurname();
                }

                private static class Customer implements Serializable {

                    @XmlElement(name = "PersonName")
                    private PersonName personName;
                    @XmlElement(name = "Telephone")
                    private Telephone telephone;
                    /**
                     * Email: email address supplied by the customer. Used by
                     * BOOKING.COM to send the reservation confirmation.
                     */
                    @XmlElement(name = "Email")
                    private String email;
                    @XmlElement(name = "Address")
                    private Address address;

                    public Customer() {
                    }

                    public String getAddress() {
                        return (address == null)
                                ? ""
                                : address.getAddressWithoutCountry();
                    }

                    public String getCountry() {
                        return (address == null)
                                ? ""
                                : address.getCountry();
                    }

                    public String getTelephone() {
                        return (telephone == null) ? "" : telephone.phoneNumber;
                    }

                    public String getGivenName() {
                        return (personName == null) ? "" : personName.givenName;
                    }

                    public String getSurname() {
                        return (personName == null) ? "" : personName.surname;
                    }

                    private static class Telephone implements Serializable {

                        /**
                         * PhoneNumber: telephone number as supplied by the
                         * customer.
                         */
                        @XmlAttribute(name = "PhoneNumber")
                        private String phoneNumber;

                        public Telephone() {
                        }

                        @Override
                        public String toString() {
                            return phoneNumber;
                        }
                    }

                    private static class PersonName implements Serializable {

                        /**
                         * GivenName: first name of the booker as supplied by
                         * the customer. Note that this doesn't have to be the
                         * same as the guestname(s).
                         */
                        @XmlElement(name = "GivenName")
                        private String givenName;
                        /**
                         * Surname: last name of the booker as supplied by the
                         * customer. Note that this doesn't have to be the same
                         * as the guestname(s).
                         */
                        @XmlElement(name = "Surname")
                        private String surname;

                        @Override
                        public String toString() {
                            return "PersonName{" + "givenName=" + givenName + ", surname=" + surname + '}';
                        }
                    }

                    @XmlAccessorType(XmlAccessType.FIELD)
                    private static class Address implements Serializable {

                        /**
                         * AddressLine (can be empty): home address supplied by
                         * the customer.
                         */
                        @XmlElement(name = "AddressLine")
                        private String addressLine;
                        /**
                         * CityName (can be empty): city of residence as
                         * supplied by the customer.
                         */
                        @XmlElement(name = "CityName")
                        private String cityName;
                        /**
                         * PostalCode (can be empty): zip / post code as
                         * supplied by the customer.
                         */
                        @XmlElement(name = "PostalCode")
                        private String postalCode;
                        /**
                         * CountryName Code: countrycode of residence as
                         * supplied by the customer.
                         */
                        @XmlElement(name = "CountryName")
                        private CountryName countryName;
                        /**
                         * CompanyName (can be empty): company name as supplied
                         * by the customer.
                         */
                        @XmlElement(name = "CompanyName")
                        private String companyName;

                        @XmlAccessorType(XmlAccessType.FIELD)
                        private static class CountryName implements Serializable {

                            @XmlAttribute(name = "Code")
                            private String code;

                            public CountryName() {
                            }

                            @Override
                            public String toString() {
                                return "CountryName{" + "code=" + code + '}';
                            }

                            public String getCountry() {
                                return code;
                            }
                        }

                        @Override
                        public String toString() {
                            return "Address{" + "addressLine=" + addressLine + ", cityName=" + cityName + ", postalCode=" + postalCode + ", countryName=" + countryName + ", companyName=" + companyName + '}';
                        }

                        public String getAddressWithoutCountry() {
                            String ret = (addressLine == null) ? "" : addressLine;
                            String[] items = new String[]{
                                companyName,
                                cityName,
                                postalCode
                            };
                            for (String i : items) {
                                ret += (i == null || "".equals(i)) ? "" : ", "+i;
                            }
                            return ret;
                        }

                        public String getCountry() {
                            return (countryName == null) ? "" : countryName.getCountry();
                        }
                    }

                    @Override
                    public String toString() {
                        return "Customer{" + "personName=" + personName + ", telephone=" + telephone + ", email=" + email + ", address=" + address + '}';
                    }
                }
            }

            @Override
            public String toString() {
                return "ProfileInfo{" + "personalName=" + profile + '}';
            }
        }
    }

    @Override
    public String toString() {
        return "ResGuest{" + "resGuestRPH=" + resGuestRPH + ", profile=" + profiles + '}';
    }
}
