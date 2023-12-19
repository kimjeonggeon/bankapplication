package shop.mtcoding.bank.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.user.UserReqDto;
import shop.mtcoding.bank.dto.user.UserRespDto;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//spring 관련 빈들이 하나도 없는 환경
@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends DummyObject {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Spy
    private BCryptPasswordEncoder passwordEncoder;
    @Test
    public void 회원가입_test() throws Exception {
        UserReqDto.JoinReqDto joinReqDto = new UserReqDto.JoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("ssar@nate.com");
        joinReqDto.setFullname("썰");

        //유저 존재하는지에 대한 stub 1
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        //stub 2 save 실행 되면 정상적 유저 리턴 시키기 유저객체 만들기
        User ssar = newMockUser(1L, "ssar","쌀");
        when(userRepository.save(any())).thenReturn(ssar);

        UserRespDto.JoinRespDto joinRespDto = userService.회원가입(joinReqDto);
        System.out.println("테스트" + joinRespDto);


        assertThat(joinRespDto.getId()).isEqualTo(1L);
        assertThat(joinRespDto.getUsername()).isEqualTo("ssar");

    }
}