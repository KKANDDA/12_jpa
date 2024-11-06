package com.ohgiraffers.section06.compositekey.subsection01.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

// 임베드 될 수 있는 복합키 타입을 지정할 때 사용하는 어노테이션
@Embeddable
public class MemberPK implements Serializable/*복합키를 아이디로 사용할 때 필요한 조치*/ {
                        // 객체 자체를 필드에 넣을 수 없으니.. 바이트로 직렬화하여 풀어썼다..

    @Column(name = "member_no")
    private int memberNo;

    @Column(name = "member_email")
    private String memberEmail;

    public MemberPK() {
    }

    public MemberPK(int memberNo, String memberEmail) {
        this.memberNo = memberNo;
        this.memberEmail = memberEmail;
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

    @Override
    public String toString() {
        return "MemberPK{" +
                "memberNo=" + memberNo +
                ", memberEmail='" + memberEmail + '\'' +
                '}';
    }


    // 복합키를 사용하면 같은 값 같은 객체임을 따로 구현해 주어야 한다.
    @Override
    public int hashCode() { // 객체 비교
        return Objects.hash(memberNo, memberEmail); // 값이 같으면 같은 해쉬코드를 반환한다.
    }

    @Override
    public boolean equals(Object obj) { // 인터페이스 비교
        // 현재 비교할 객체가 현재 객체와 동일한 경우 true
        if (this == obj) return true;
        // 비교할 객체가 null이거나, 두 객체의 클래스가 다르면 false
        if (obj == null || getClass() != obj.getClass()) return false;
        MemberPK memberPK = (MemberPK) obj;
        // memberEmail 이 동일하고, memberNo가 동일하면 true 반환
        return memberNo == memberPK.memberNo && Objects.equals(memberEmail, memberPK.memberEmail);
    }
}
