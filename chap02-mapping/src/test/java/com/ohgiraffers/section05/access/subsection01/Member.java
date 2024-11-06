package com.ohgiraffers.section05.access.subsection01;

import jakarta.persistence.*;

@Entity(name = "member_section05_subsection01")
@Table(name = "tbl_member_section05_subsection01")
@Access(AccessType.FIELD) // 기본값 필드, 펄시스트 혹은 파인드 접근 기준.. 필드접근이 있고, 게터 접근이 있다..
public class Member {

    /*
    필드 접근은 기본값이므로 해당 설정은 제거해도 동일하게 동작한다.
    또한 필드 레벨과 프로퍼티 레벨 모두 선언하면 프로퍼티 레벨을 우선으로 사용한다.

    @Access 필드단에서도 쓸 수 있도 메도스단에서도 쓸 수 있다.

    jpa는 캡술화 원칙을 깬다..
    * */

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMemberPwd() {
        return memberPwd;
    }

    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    public String getMemberId() {
        System.out.println("getMemberNameNo로 access되는지 확인");
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getMemberNo() {
        System.out.println("getMemberNameNo로 access되는지 확인");
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
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
