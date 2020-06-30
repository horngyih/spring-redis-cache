package tech.coderonin.spring.redis.cache.data;

public class PhoneData implements IPhoneData {

    String id;
    String countryAccessCode;
    String phoneNumber;
    String extension;
    String type;

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getCountryAccessCode() {
        return countryAccessCode;
    }

    public void setCountryAccessCode(String countryAccessCode) {
        this.countryAccessCode = countryAccessCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getType() {
        return type;
    }

    public void setType(String phoneType) {
        this.type = phoneType;
    }

    @Override
    public IPhoneData clone(){
        try {
            return (IPhoneData) super.clone();
        } catch (CloneNotSupportedException e) {
        }
        return null;
    }

    public static class PhoneDataBuilder{
        String id;
        String countryAccessCode;
        String phoneNumber;
        String extension;
        String type;

        public PhoneDataBuilder(){}

        public PhoneDataBuilder id(String id){
            this.id = id;
            return this;
        }

        public PhoneDataBuilder countryAccessCode(String code){
            this.countryAccessCode = code;
            return this;
        }

        public PhoneDataBuilder phoneNumber(String number){
            this.phoneNumber = number;
            return this;
        }

        public PhoneDataBuilder extension(String ext){
            this.extension = ext;
            return this;
        }

        public PhoneDataBuilder type(String typ){
            this.type = type;
            return this;
        }

        public IPhoneData build(){
            PhoneData result = new PhoneData();
            result.setID(this.id);
            result.setCountryAccessCode(this.countryAccessCode);
            result.setPhoneNumber(this.phoneNumber);
            result.setExtension(this.extension);
            result.setType(this.type);
            return result;
        }
    }
}
