package za.co.security.Spring3.Security6.Demo.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import za.co.security.Spring3.Security6.Demo.models.UserInfo;
import za.co.security.Spring3.Security6.Demo.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailServiceImpl() {}

    public UserDetailServiceImpl(UserRepository repository) {
        this.userRepository = repository;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Entering in loadUserByUsername Method...");
        UserInfo user = userRepository.findByUsername(username);
        if (user == null) {
            logger.error("Username not found..." + username);
            throw new UsernameNotFoundException("Could not find user...");
        }
        logger.info("User authenticated successfully...");
        return new CustomUserDetails(user);
    }
}
