package com.xuleyan.finals.dal.pojo;

public class Account {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.id
     *
     * @mbg.generated Fri Jul 16 22:59:30 CST 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.name
     *
     * @mbg.generated Fri Jul 16 22:59:30 CST 2021
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.age
     *
     * @mbg.generated Fri Jul 16 22:59:30 CST 2021
     */
    private Integer age;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.phone
     *
     * @mbg.generated Fri Jul 16 22:59:30 CST 2021
     */
    private String phone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.address
     *
     * @mbg.generated Fri Jul 16 22:59:30 CST 2021
     */
    private String address;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.goods
     *
     * @mbg.generated Fri Jul 16 22:59:30 CST 2021
     */
    private Integer goods;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account.id
     *
     * @return the value of t_account.id
     *
     * @mbg.generated Fri Jul 16 22:59:30 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_account.id
     *
     * @param id the value for t_account.id
     *
     * @mbg.generated Fri Jul 16 22:59:30 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account.name
     *
     * @return the value of t_account.name
     *
     * @mbg.generated Fri Jul 16 22:59:30 CST 2021
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_account.name
     *
     * @param name the value for t_account.name
     *
     * @mbg.generated Fri Jul 16 22:59:30 CST 2021
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account.age
     *
     * @return the value of t_account.age
     *
     * @mbg.generated Fri Jul 16 22:59:30 CST 2021
     */
    public Integer getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_account.age
     *
     * @param age the value for t_account.age
     *
     * @mbg.generated Fri Jul 16 22:59:30 CST 2021
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account.phone
     *
     * @return the value of t_account.phone
     *
     * @mbg.generated Fri Jul 16 22:59:30 CST 2021
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_account.phone
     *
     * @param phone the value for t_account.phone
     *
     * @mbg.generated Fri Jul 16 22:59:30 CST 2021
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account.address
     *
     * @return the value of t_account.address
     *
     * @mbg.generated Fri Jul 16 22:59:30 CST 2021
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_account.address
     *
     * @param address the value for t_account.address
     *
     * @mbg.generated Fri Jul 16 22:59:30 CST 2021
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_account.goods
     *
     * @return the value of t_account.goods
     *
     * @mbg.generated Fri Jul 16 22:59:30 CST 2021
     */
    public Integer getGoods() {
        return goods;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_account.goods
     *
     * @param goods the value for t_account.goods
     *
     * @mbg.generated Fri Jul 16 22:59:30 CST 2021
     */
    public void setGoods(Integer goods) {
        this.goods = goods;
    }
}