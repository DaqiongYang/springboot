package com.github.xuybin.springboot.model;

public class FamilyInfo {
    String family_id;//'户唯一标识',
    String person_name;//'户主名字'
    String person_card;//'户主名字身份证'
    String family_total;//'户人数'
    String house_status;//'住房情况'
    String house_area;//'住房面积
    String poor_status;//'贫困状态',
    String household_where_code;//'户籍地'
    String operator;//'操作者账号'

    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }

    public void setCode_id(String code_id) {
        this.family_id = code_id;
    }


    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPerson_card() {
        return person_card;
    }

    public void setPerson_card(String person_card) {
        this.person_card = person_card;
    }

    public String getFamily_total() {
        return family_total;
    }

    public void setFamily_total(String family_total) {
        this.family_total = family_total;
    }

    public String getHouse_status() {
        return house_status;
    }

    public void setHouse_status(String house_status) {
        this.house_status = house_status;
    }

    public String getHouse_area() {
        return house_area;
    }

    public void setHouse_area(String house_area) {
        this.house_area = house_area;
    }

    public String getPoor_status() {
        return poor_status;
    }

    public void setPoor_status(String poor_status) {
        this.poor_status = poor_status;
    }

    public String getHousehold_where_code() {
        return household_where_code;
    }

    public void setHousehold_where_code(String household_where_code) {
        this.household_where_code = household_where_code;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setAccount(String account) {
        this.operator = account;
    }


    @Override
    public String toString() {
        return "FamilyInfo{" +
                "family_id='" + family_id + '\'' +
                ", person_name='" + person_name + '\'' +
                ", person_card='" + person_card + '\'' +
                ", family_total='" + family_total + '\'' +
                ", house_status='" + house_status + '\'' +
                ", house_area='" + house_area + '\'' +
                ", poor_status='" + poor_status + '\'' +
                ", household_where_code='" + household_where_code + '\'' +
                ", operator='" + operator + '\'' +
                '}';
    }
}
