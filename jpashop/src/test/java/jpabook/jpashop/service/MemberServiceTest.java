package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // junit에서 spring 사용
@SpringBootTest // spring container load
@Transactional // Rollback(True) -> 영속성 컨텍스트 Flush X
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
//        em.flush(); // //강제 Flush (Insert Query 실행)
        assertEquals(member, memberRepository.findOne(saveId)); // 영속성 컨텍스트에 의해 pk가 같으면, 같은 객체 반환
    }

    @Test(expected = IllegalStateException.class) // IllegalStateException throw되면 정상으로 판단
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);

        /*try {
            memberService.join(member2); // Exception 발생
        }catch(IllegalStateException e){
            return;
        }*/
        //then
        fail("이 코드가 실행되면 안된다. 위에서 예외가 발생해야 한다.");
    }

}