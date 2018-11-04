package pl.basistam.wloczykij.common.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.basistam.wloczykij.common.exceptions.BadCredentialsException;
import pl.basistam.wloczykij.database.model.User;
import pl.basistam.wloczykij.database.repository.UserRepository;

import javax.transaction.Transactional;

@Service
@Primary
public class UserCredentialsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserCredentialsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository
                .findUserByLogin(login)
                .orElseThrow(BadCredentialsException::new);
        user.getRoles().size(); // we have to load it in session context, because of LAZY fetch
        return new UserCredentials(user);
    }
}
