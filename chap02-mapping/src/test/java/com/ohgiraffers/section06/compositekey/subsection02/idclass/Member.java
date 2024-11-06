package com.ohgiraffers.section06.compositekey.subsection02.idclass;

import jakarta.persistence.*;

@Entity(name = "member_section06_subsection02")
@Table(name = "tbl_member_section06_subsection02")
@IdClass(MemberPK.class)
public class Member {

    @Id
    @Column(name = "member_no")
    private int memberNo;

    @Id
    @Column(name = "member_email")
    private String memberEmail;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    public Member() {
    }

    public Member(int memberNo, String memberEmail, String phone, String address) {
        this.memberNo = memberNo;
        this.memberEmail = memberEmail;
        this.phone = phone;
        this.address = address;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "Member{" +
                "memberNo=" + memberNo +
                ", memberEmail='" + memberEmail + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
