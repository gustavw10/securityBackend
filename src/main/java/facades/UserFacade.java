package facades;

import dtos.UserDTO;
import dtos.UsersDTO;
import entities.Role;
import entities.User;
import errorhandling.MissingInputException;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import securityerrorhandling.AuthenticationException;

public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;
    private static final String COMPLEX_PASSWORD_REGEX
            = "^(?:(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])|"
            + "(?=.*\\d)(?=.*[^A-Za-z0-9])(?=.*[a-z])|"
            + "(?=.*[^A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z])|"
            + "(?=.*\\d)(?=.*[A-Z])(?=.*[^A-Za-z0-9]))(?!.*(.)\\1{2,})"
            + "[A-Za-z0-9!~<>,;:_=?*+#.\"&§%°()\\|\\[\\]\\-\\$\\^\\@\\/]"
            + "{8,32}$";

    private static final Pattern PASSWORD_PATTERN
            = Pattern.compile(COMPLEX_PASSWORD_REGEX);

    private UserFacade() {

    }

    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public UsersDTO getAllUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            return new UsersDTO(em.createNamedQuery("Users.getAll").getResultList());
        } finally {
            em.close();

        }
    }

    public UserDTO createUser(UserDTO u) throws MissingInputException {
     

            if (!PASSWORD_PATTERN.matcher(u.getUserPass()).matches()) {
                throw new MissingInputException();
            }

        EntityManager em = emf.createEntityManager();
        User user = new User(u.getUserName(), u.getUserPass());

        try {
            em.getTransaction().begin();
            Role userRole = new Role("user");
            user.addRole(userRole);
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }
    
        public UserDTO deleteUser(String name) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, name);
        try {
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
            
            } finally {
            em.close();
        } return new UserDTO(user);
    }
    
}
