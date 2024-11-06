package com.ohgiraffers.section05.access.subsection02;

import jakarta.persistence.*;

@Entity(name = "member_section05_subsection02")
@Table(name = "tbl_member_section05_subsection02")
@Access(AccessType.PROPERTY)
public class Member {

    @Column(name = "member_no")
    private int memberNo;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_pwd")
    private String memberPwd;

    @Column(name = "nickname")
    private String nickname;

    @Id
    public int getMemberNo() {
        System.out.println("getMemberNo를 이용한 access 확인");
        return memberNo; // 게터에 어노테이션을 달아서 @Id를 줄 수 있다.
        // 야는 프로퍼티 접근이 된 거시다~!!!
    }

    public Member() {
    }

    public Member(int memberNo, String memberId, String memberPwd, String nickname) {
        this.memberNo = memberNo;
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.nickname = nickname;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public String getMemberId() {
        System.out.println("getMemberId를 사용한 access 확인");
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberPwd() {
        System.out.println("getMemberPwd를 사용한 access 확인");
        return memberPwd;
    }

    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    // @Access(AccessType.PROPERTY) // 필드에서 일부분만 프로퍼티 접근을 원하면 이렇게 할 수도 있다.
    public String getNickname() {
        System.out.println("getNickname를 사용한 access 확인");
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberNo=" + memberNo +
                ", memberId='" + memberId + '\'' +
                ", memberPwd='" + memberPwd + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
