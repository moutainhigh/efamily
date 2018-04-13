package com.winterframework.efamily.entity;



public class AddressComponentGaoDe {

	/*
	{
    "status": "1",
    "info": "OK",
    "infocode": "10000",
    "regeocode": {
        "formatted_address": "北京市海淀区燕园街道北京大学",
        "addressComponent": {
            "country": "中国",
            "province": "北京市",
            "city": [],
            "citycode": "010",
            "district": "海淀区",
            "adcode": "110108",
            "township": "燕园街道",
            "towncode": "110108015000",
            "neighborhood": {   //
                "name": "北京大学",
                "type": "科教文化服务;学校;高等院校"
            },
            "building": {
                "name": "北京大学",
                "type": "科教文化服务;学校;高等院校"
            },
            "streetNumber": {
                "street": "颐和园路",
                "number": "5号",
                "location": "116.310518,39.9918931",
                "direction": "东",
                "distance": "44.4465"
            },
            "businessAreas": [
                {
                    "location": "116.29522008325625,39.99426090286774",
                    "name": "颐和园",
                    "id": "110108"
                }
            ]
        }
    }
}
*/
private String country;
private String province;
private String city;
private String citycode;
private String district;
private String direction;
private String adcode;
private String township;
private String towncode;
//private Neighborhood neighborhood;
//private Building building;
//private StreetNumber streetNumber;
public String getCountry() {
return country;
}
public void setCountry(String country) {
this.country = country;
}
public String getProvince() {
return province;
}
public void setProvince(String province) {
this.province = province;
}
public String getCitycode() {
return citycode;
}
public void setCitycode(String citycode) {
this.citycode = citycode;
}
public String getDistrict() {
return district;
}
public void setDistrict(String district) {
this.district = district;
}
public String getDirection() {
return direction;
}
public void setDirection(String direction) {
this.direction = direction;
}
public String getAdcode() {
return adcode;
}
public void setAdcode(String adcode) {
this.adcode = adcode;
}
public String getTownship() {
return township;
}
public void setTownship(String township) {
this.township = township;
}
public String getTowncode() {
return towncode;
}
public void setTowncode(String towncode) {
this.towncode = towncode;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
@Override
public String toString() {
	return "AddressComponentGaoDe [country=" + country + ", province="
			+ province + ", city=" + city + ", citycode=" + citycode
			+ ", district=" + district + ", direction=" + direction
			+ ", adcode=" + adcode + ", township=" + township + ", towncode="
			+ towncode + "]";
}
}
