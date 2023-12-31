package shop.mtcoding.bank.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.handler.ex.CustomApiException;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
//서비스는 dto로 응답 받고  dto로 응답한다.
public class UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Transactional// 트랜젝션이 메서드 시작될 때 시작되고 종료될떄 함께 종료
    public JonRespDto 회원가입(JoinReqDto joinReqDto){
        Optional<User> userOP= userRepository.findByUsername(joinReqDto.getUsername());
        //1. 동일 유저 네임 존재 검사
        if(userOP.isPresent()){
            throw new CustomApiException("동일한 username이 존재합니다.");
        }
        //2. 패스워드 인코딩 + 회원가입
        User userPS = userRepository.save(joinReqDto.toEntity(passwordEncoder));

        return new JonRespDto(userPS);
    }
    //3. responsedto응답
    @Getter
    @Setter
    @ToString
    public static class JonRespDto{
        private Long id;
        private String username;
        private String fullname;
        public JonRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.fullname = user.getFullname();
        }


    }
    @Setter
    @Getter
    public static class JoinReqDto{
        //유효성 검사
        private String username;
        private String password;
        private String email;
        private String fullname;

        //위에 dto값으로 엔티티 만들수 있음
        public User toEntity(BCryptPasswordEncoder passwordEncoder){
            return User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .fullname(fullname)
                    .role(UserEnum.CUSTOMER)
                    .build();
        }
    }


}
