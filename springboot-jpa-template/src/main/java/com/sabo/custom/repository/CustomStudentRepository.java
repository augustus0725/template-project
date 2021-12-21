package com.sabo.custom.repository;

import com.sabo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CustomStudentRepository {
    @Autowired  EntityManager em;

    public List<Student> findStudents() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);

        Root<Student> student = cq.from(Student.class);
        // add filter
//        List<Predicate> predicates = new ArrayList<>();
//
//        if (authorName != null) {
//            predicates.add(cb.equal(book.get("author"), authorName));
//        }
//        if (title != null) {
//            predicates.add(cb.like(book.get("title"), "%" + title + "%"));
//        }
//        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Student> query = em.createQuery(cq);
        return query.getResultList();
    }
}

// JPA的方法
//static Specification<Book> hasAuthor(String author) {
//    return (book, cq, cb) -> cb.equal(book.get("author"), author);
//}
//
//static Specification<Book> titleContains(String title) {
//        return (book, cq, cb) -> cb.like(book.get("title"), "%" + title + "%");
//}

// interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {}
// bookRepository.findAll(hasAuthor(author));
// bookRepository.findAll(where(hasAuthor(author)).and(titleContains(title)));