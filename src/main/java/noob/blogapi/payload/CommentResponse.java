package noob.blogapi.payload;

import java.util.List;

public class CommentResponse {
    List<CommentDto> content;
    int pageNumber;
    int pageSize;
    long totalElements;
    int totalPages;
    boolean last;
}
