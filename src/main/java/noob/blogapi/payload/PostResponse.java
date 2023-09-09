package noob.blogapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    List<PostDto> content;
    int pageNumber;
    int pageSize;
    long totalElements;
    int totalPages;
    boolean last;

}
