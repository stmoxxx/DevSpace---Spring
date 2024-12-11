package devspace.devspaceback.services;

import devspace.devspaceback.models.PostEntity;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {

    public static Specification<PostEntity> withAuthorId(Long authorId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("author").get("id"), authorId);
    }

}
