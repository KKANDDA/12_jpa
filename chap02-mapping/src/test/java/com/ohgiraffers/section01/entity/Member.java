package com.ohgiraffers.section01.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
어노테이션은 jpa에서 사용되는 클래스임을 표시한다.
이 어노테이션을 사용하면 해당 클래스가 데이터 베이스의 테이블과 매핑된다.

프로젝트 내에 다른 패키지에도 동일한 엔티티가 존재하는 경우 해당 엔티티를 식별하기 위한 중복 되지 않는 name을 지정해 주어야 한다.
enum, inreface에서는 사용할 수 없다.
저장할 필드에 final을 사용하면 안 된다.
* */
@Entity(name = "member_section01")
@Table(name = "tbl_member_section01") // 이름이 같은 테이블이 있으면.. 날리고 새것을 만든다.. 무시무시하다..
public class Member {

    @Id
    @Column(name = "member_no")
    private int memberNo;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_pwd")
    private String memberPwd;

    @Column(name = "nickname")
    private String nickname;


    @Column(name = "phone")
    private String phone;

    public Member() {
    }

    public Member(int memberNo, String memberId, String memberPwd, String nickname, String phone) {
        this.memberNo = memberNo;
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.nickname = nickname;
        this.phone = phone;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberPwd() {
        return memberPwd;
    }

    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberNo=" + memberNo +
                ", memberId='" + memberId + '\'' +
                ", memberPwd='" + memberPwd + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
