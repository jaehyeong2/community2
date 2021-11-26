package jaefactory.newboard.controller.api;


import jaefactory.newboard.domain.user.User;
import jaefactory.newboard.dto.CommonResDto;
import jaefactory.newboard.service.AuthService;
import jaefactory.newboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final AuthService authService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/api/user/join")
    public CommonResDto<?> save(@RequestBody User user) {
        authService.join(user);
        return new CommonResDto<Integer>(HttpStatus.OK.value(),"ok", 1);
    }

    @PutMapping("/user")
    public CommonResDto<?> update(@RequestBody User user) { // key=value, x-www-form-urlencoded
        userService.userUpdate(user);
        // 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음.
        // 하지만 세션값은 변경되지 않은 상태이기 때문에 우리가 직접 세션값을 변경해줄 것임.
        // 세션 등록

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new CommonResDto<Integer>(HttpStatus.OK.value(),"ok", 1);
    }
}
