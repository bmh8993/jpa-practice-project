package jpabook.jpashop

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class) // 스프링과 관련된 것을 테스트한다고 junit5에 알려
@SpringBootTest // spring boot로 테스트를 돌리기 위함
internal class MemberRepositoryTest(
    @Autowired private val memberRepository: MemberRepository
) {
    @Test
    @Transactional // @Transactional이 있으면 테스트코드 종료 후 롤백을 해버린다. 그래서 DB에 들어가면 테스트코드에서 사용된 데이터가 없다. 다음 로그는 롤백을 했다는 흔적을 확인할 수 있다. o.s.t.c.transaction.TransactionContext   : Rolled back transaction for test ...
    @Rollback(false) // Transactional을 통해서 rollback이 발생하는데 이를 false로 하면 데이터가 삭제되지 않는다.
    fun testMember() {
        // given
        val member = Member(username = "memberA")

        // when
        val memberId = memberRepository.save(member)
        val findMember = memberRepository.find(memberId)

        // then
        Assertions.assertThat(findMember.id).isEqualTo(member.id)
        Assertions.assertThat(findMember.username).isEqualTo(member.username)
    }
}
