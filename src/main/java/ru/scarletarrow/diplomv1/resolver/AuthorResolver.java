//package ru.scarletarrow.diplomv1.resolver;
//
//import graphql.kickstart.tools.GraphQLQueryResolver;
//import graphql.kickstart.tools.GraphQLResolver;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import ru.scarletarrow.diplomv1.Author;
//import ru.scarletarrow.diplomv1.AuthorRepository;
//import ru.scarletarrow.diplomv1.BookRepository;
//import ru.scarletarrow.diplomv1.exception.UserNotFoundException;
//
//@Component
//public class AuthorResolver implements GraphQLQueryResolver {
//
//    @Autowired
//    AuthorRepository authorRepository;
//    public Author authorById(Long id) {
//        return authorRepository.findById(id).orElseThrow(UserNotFoundException::new);
//    }
//
//}
