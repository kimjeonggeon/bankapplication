package shop.mtcoding.bank.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.account.AccountReqDto;
import shop.mtcoding.bank.dto.account.AccountResDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class AccountServiceTest extends DummyObject {
    //given
    @InjectMocks//모든 mock들이 injectMocks로 주입됨
    private AccountService accountService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountRepository accountRepository;
    @Spy // 진짜 객체를 InjectMocks에 주입한다.
    private ObjectMapper om;
    @Test
    public void 계좌등록_test() throws Exception{
        Long userId= 1L;
        AccountReqDto.AccountSaveReqDto accountSaveReqDto = new AccountReqDto.AccountSaveReqDto();
        accountSaveReqDto.setNumber(1111L);
        accountSaveReqDto.setPassword(1234L);

    //stub1
        User ssar = newMockUser(userId, "ssar", "쌀");
        when(userRepository.findById(any())).thenReturn(Optional.of(ssar));
    //stub2
        when(accountRepository.findByNumber(any())).thenReturn(Optional.empty());
    //stub3
        Account ssarAccount = newMockAccount(1L,1111L,1000L,ssar);
        when(accountRepository.save(any())).thenReturn(ssarAccount);

        //when
        AccountResDto.AccountSaveRespDto accountSaveRespDto= accountService.계좌등록(accountSaveReqDto,userId);
        String responseBody = om.writeValueAsString(accountSaveRespDto);
        System.out.println("테스트"+responseBody);

        //then
        assertThat(accountSaveRespDto.getNumber()).isEqualTo(1111L);
    }
}
